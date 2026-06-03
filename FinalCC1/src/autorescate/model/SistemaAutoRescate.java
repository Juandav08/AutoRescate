package autorescate.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import autorescate.model.entidades.Cliente;
import autorescate.model.entidades.KitAtencion;
import autorescate.model.entidades.MovimientoOperacion;
import autorescate.model.entidades.SolicitudServicio;
import autorescate.model.entidades.Tecnico;
import autorescate.model.entidades.UnidadServicio;
import autorescate.model.funciones.ArregloDinamico;
import autorescate.model.funciones.Cola;
import autorescate.model.funciones.ColaPrioridad;
import autorescate.model.funciones.Pila;
import autorescate.model.programa.EstadoKit;
import autorescate.model.programa.EstadoSolicitud;
import autorescate.model.programa.EstadoTecnico;
import autorescate.model.programa.EstadoUnidad;
import autorescate.model.programa.TipoCliente;
import autorescate.model.programa.TipoIncidente;
import autorescate.model.programa.TipoServicio;
import autorescate.model.programa.TipoUnidad;

/**
 * Modelo principal de AutoRescate 24/7. Administra entidades, estructuras
 * propias y reglas de negocio con arreglos, colas y pilas propias.
 */
public class SistemaAutoRescate {

    private ArregloDinamico<Cliente> clientes;
    private ArregloDinamico<Tecnico> tecnicos;
    private ArregloDinamico<UnidadServicio> unidades;
    private ArregloDinamico<SolicitudServicio> historicoSolicitudes;
    private Cola<SolicitudServicio> solicitudesOrdinarias;
    private ColaPrioridad<SolicitudServicio> solicitudesCriticas;
    private Pila<KitAtencion> kitsRevision;
    private Pila<MovimientoOperacion> movimientos;
    private int consecutivoCliente;
    private int consecutivoTecnico;
    private int consecutivoSolicitud;
    private int consecutivoKit;
    private int consecutivoUnidad;

    /**
     * Crea el sistema con sus estructuras vacias. maneja todas las reglas de negocio
     */
    public SistemaAutoRescate() {
        clientes = new ArregloDinamico<Cliente>();
        tecnicos = new ArregloDinamico<Tecnico>();
        unidades = new ArregloDinamico<UnidadServicio>();
        historicoSolicitudes = new ArregloDinamico<SolicitudServicio>();
        solicitudesOrdinarias = new Cola<SolicitudServicio>();
        solicitudesCriticas = new ColaPrioridad<SolicitudServicio>();
        kitsRevision = new Pila<KitAtencion>();
        movimientos = new Pila<MovimientoOperacion>();
        consecutivoCliente = 1;
        consecutivoTecnico = 1;
        consecutivoSolicitud = 1;
        consecutivoKit = 1;
        consecutivoUnidad = 1;
    }

    /**
     * Registra un cliente.
     *
     * Nombre del cliente.
     * Telefono.
     * Tipo de cliente.
     * Cliente creado.
     */
    public Cliente registrarCliente(String nombre, String telefono, TipoCliente tipo) {
        validarTexto(nombre, "nombre del cliente");
        Cliente cliente = new Cliente("C-" + consecutivoCliente++, nombre, telefono, tipo);
        clientes.agregar(cliente);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Cliente " + cliente.getId());
        return cliente;
    }

    /**
     * Registra un tecnico.
     *
     * Nombre del tecnico.
     * Especialidad.
     * Zona operativa.
     * Tecnico creado.
     */
    public Tecnico registrarTecnico(String nombre, String especialidad, String zona) {
        validarTexto(nombre, "nombre del tecnico");
        validarTexto(especialidad, "especialidad");
        Tecnico tecnico = new Tecnico("T-" + consecutivoTecnico++, nombre, especialidad, zona);
        tecnicos.agregar(tecnico);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Tecnico " + tecnico.getId());
        return tecnico;
    }

    /**
     * Registra una unidad con UUID unico.
     *
     * Tipo de unidad.
     * Zona.
     * Placa o codigo operativo.
     * Unidad creada.
     */
    public UnidadServicio registrarUnidad(TipoUnidad tipo, String zona, String placa) {
        validarTexto(placa, "placa");
        String uuid;
        do {
            uuid = "AR24-" + Long.toHexString(System.currentTimeMillis())
                    + "-" + Long.toHexString(System.nanoTime()) + "-" + consecutivoUnidad++;
        } while (buscarUnidad(uuid) != null);
        UnidadServicio unidad = new UnidadServicio(uuid, tipo, zona, placa);
        unidades.agregar(unidad);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Unidad " + placa);
        return unidad;
    }

    /**
     * Crea una solicitud y la ubica en la estructura correspondiente.
     *
     * Cliente asociado.
     * Tipo de servicio.
     * Descripcion.
     * Ubicacion.
     * Prioridad 1 a 10.
     * Solicitud creada.
     */
    public SolicitudServicio crearSolicitud(Cliente cliente, TipoServicio tipoServicio, TipoIncidente tipoIncidente,
            String descripcion, String ubicacion, int prioridad) {
        if (cliente == null) {
            throw new IllegalArgumentException("Toda solicitud debe estar asociada a un cliente.");
        }
        if (tipoIncidente == null) {
            throw new IllegalArgumentException("Toda solicitud debe tener un tipo de incidente.");
        }
        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5.");
        }
        SolicitudServicio solicitud = new SolicitudServicio("S-" + consecutivoSolicitud++,
                cliente, tipoServicio, tipoIncidente, descripcion, ubicacion, prioridad);
        historicoSolicitudes.agregar(solicitud);
        if (solicitud.esCritica()) {
            solicitudesCriticas.insertar(solicitud);
        } else {
            solicitudesOrdinarias.encolar(solicitud);
        }
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Solicitud " + solicitud.getId());
        return solicitud;
    }

    /**
     * Asigna tecnico y unidad a una solicitud pendiente.
     *
     * Solicitud.
     * Tecnico disponible.
     * Unidad disponible.
     */
    public void asignarRecursos(SolicitudServicio solicitud, Tecnico tecnico, UnidadServicio unidad) {
        if (solicitud == null || tecnico == null || unidad == null) {
            throw new IllegalArgumentException("Solicitud, tecnico y unidad son obligatorios.");
        }
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalArgumentException("Solo se asignan solicitudes pendientes.");
        }
        if (!tecnico.estaDisponible()) {
            throw new IllegalArgumentException("El tecnico no esta disponible.");
        }
        if (!unidad.estaDisponible()) {
            throw new IllegalArgumentException("La unidad no esta disponible o esta en mantenimiento.");
        }
        if (!esUnidadCompatible(solicitud.getTipoServicio(), unidad.getTipo())) {
            throw new IllegalArgumentException("La unidad seleccionada no corresponde al recurso solicitado.");
        }

        MovimientoOperacion movimiento = new MovimientoOperacion(MovimientoOperacion.ASIGNACION,
                "Asignacion a " + solicitud.getId());
        movimiento.setSolicitud(solicitud);
        movimiento.setTecnico(tecnico);
        movimiento.setUnidad(unidad);
        movimiento.setEstadoSolicitudAnterior(solicitud.getEstado());
        movimiento.setEstadoTecnicoAnterior(tecnico.getEstado());
        movimiento.setEstadoUnidadAnterior(unidad.getEstado());

        solicitud.setTecnicoAsignado(tecnico);
        solicitud.setUnidadAsignada(unidad);
        solicitud.setEstado(EstadoSolicitud.EN_EJECUCION);
        tecnico.setEstado(EstadoTecnico.OCUPADO);
        unidad.setEstado(EstadoUnidad.ASIGNADA);
        quitarDePendientes(solicitud);
        movimientos.apilar(movimiento);
    }

    /**
     * Cambia el estado de una unidad respetando la regla de asignacion.
     *
     * Unidad a modificar.
     * Nuevo estado.
     */
    public void cambiarEstadoUnidad(UnidadServicio unidad, EstadoUnidad estado) {
        if (unidad == null || estado == null) {
            throw new IllegalArgumentException("Unidad y estado son obligatorios.");
        }
        if (unidad.getEstado() == EstadoUnidad.ASIGNADA && estado == EstadoUnidad.DISPONIBLE) {
            throw new IllegalArgumentException("Una unidad asignada no puede marcarse disponible directamente.");
        }
        MovimientoOperacion movimiento = new MovimientoOperacion(MovimientoOperacion.CAMBIO_UNIDAD,
                "Unidad " + unidad.getPlaca() + " a " + estado);
        movimiento.setUnidad(unidad);
        movimiento.setEstadoUnidadAnterior(unidad.getEstado());
        unidad.setEstado(estado);
        movimientos.apilar(movimiento);
    }

    /**
     * Cierra una solicitud atendida liberando recursos.
     *
     * Solicitud en ejecucion.
     */
    public void cerrarServicio(SolicitudServicio solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud es obligatoria.");
        }
        if (!solicitud.tieneRecursosAsignados()) {
            throw new IllegalArgumentException("Una solicitud no puede cerrarse sin recursos asignados.");
        }
        MovimientoOperacion movimiento = new MovimientoOperacion(MovimientoOperacion.CIERRE,
                "Cierre " + solicitud.getId());
        movimiento.setSolicitud(solicitud);
        movimiento.setTecnico(solicitud.getTecnicoAsignado());
        movimiento.setUnidad(solicitud.getUnidadAsignada());
        movimiento.setEstadoSolicitudAnterior(solicitud.getEstado());
        movimiento.setEstadoTecnicoAnterior(solicitud.getTecnicoAsignado().getEstado());
        movimiento.setEstadoUnidadAnterior(solicitud.getUnidadAsignada().getEstado());
        movimiento.setFechaCierreAnterior(solicitud.getFechaCierre());

        solicitud.cerrar(LocalDate.now().toString());
        solicitud.getTecnicoAsignado().setEstado(EstadoTecnico.DISPONIBLE);
        solicitud.getUnidadAsignada().setEstado(EstadoUnidad.DISPONIBLE);
        quitarDePendientes(solicitud);
        movimientos.apilar(movimiento);
    }

    /**
     * Registra un kit en revision.
     *
     * Descripcion del kit.
     * Indica si requiere reposicion.
     * Kit registrado.
     */
    public KitAtencion registrarKitEnRevision(String descripcion, boolean requiereReposicion) {
        validarTexto(descripcion, "descripcion del kit");
        KitAtencion kit = new KitAtencion("K-" + consecutivoKit++, descripcion);
        kit.setEstado(EstadoKit.EN_REVISION);
        kit.setRequiereReposicion(requiereReposicion);
        kitsRevision.apilar(kit);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Kit en revision " + kit.getId());
        return kit;
    }

    /**
     * Retira el ultimo kit ingresado a revision.
     *
     * Kit retirado.
     */
    public KitAtencion retirarUltimoKitRevisado() {
        if (kitsRevision.estaVacia()) {
            throw new RuntimeException("No hay kits en revision.");
        }
        if (kitsRevision.verTope().isRequiereReposicion()) {
            throw new IllegalArgumentException("El ultimo kit aun requiere reposicion. Primero debe repararse o reponerse.");
        }
        KitAtencion kit = kitsRevision.desapilar();
        kit.setEstado(EstadoKit.DISPONIBLE);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Kit retirado " + kit.getId());
        return kit;
    }

    /**
     * Marca como reparado o repuesto el ultimo kit en revision.
     *
     * Kit actualizado.
     */
    public KitAtencion repararUltimoKitEnRevision() {
        if (kitsRevision.estaVacia()) {
            throw new RuntimeException("No hay kits en revision.");
        }
        KitAtencion kit = kitsRevision.verTope();
        kit.setRequiereReposicion(false);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Kit reparado/repuesto " + kit.getId());
        return kit;
    }

    /**
     * Marca como reparado o repuesto un kit especifico.
     *
     * Kit a reparar.
     * Kit actualizado.
     */
    public KitAtencion repararKitEnRevision(KitAtencion kit) {
        if (kit == null) {
            throw new IllegalArgumentException("Debe seleccionar un kit.");
        }
        kit.setRequiereReposicion(false);
        registrarMovimiento(MovimientoOperacion.REGISTRO, "Kit reparado/repuesto " + kit.getId());
        return kit;
    }

    /**
     * Registra manualmente un movimiento operativo.
     *
     * Detalle del movimiento.
     */
    public void registrarMovimientoManual(String detalle) {
        registrarMovimiento(MovimientoOperacion.REGISTRO, detalle);
    }

    /**
     * Revierte la ultima operacion reversible.
     *
     * Descripcion de la reversa aplicada.
     */
    public String revertirUltimaOperacion() {
        MovimientoOperacion movimiento = movimientos.desapilar();
        if (MovimientoOperacion.ASIGNACION.equals(movimiento.getTipo())) {
            SolicitudServicio solicitud = movimiento.getSolicitud();
            solicitud.setEstado(movimiento.getEstadoSolicitudAnterior());
            solicitud.setTecnicoAsignado(null);
            solicitud.setUnidadAsignada(null);
            movimiento.getTecnico().setEstado(movimiento.getEstadoTecnicoAnterior());
            movimiento.getUnidad().setEstado(movimiento.getEstadoUnidadAnterior());
            devolverAPendientes(solicitud);
            return "Revertida asignacion de " + solicitud.getId();
        }
        if (MovimientoOperacion.CIERRE.equals(movimiento.getTipo())) {
            SolicitudServicio solicitud = movimiento.getSolicitud();
            solicitud.setEstado(movimiento.getEstadoSolicitudAnterior());
            solicitud.setFechaCierre(movimiento.getFechaCierreAnterior());
            movimiento.getTecnico().setEstado(movimiento.getEstadoTecnicoAnterior());
            movimiento.getUnidad().setEstado(movimiento.getEstadoUnidadAnterior());
            return "Revertido cierre de " + solicitud.getId();
        }
        if (MovimientoOperacion.CAMBIO_UNIDAD.equals(movimiento.getTipo())) {
            movimiento.getUnidad().setEstado(movimiento.getEstadoUnidadAnterior());
            return "Revertido cambio de unidad " + movimiento.getUnidad().getPlaca();
        }
        return "Movimiento informativo retirado: " + movimiento.getDetalle();
    }

    /**
     * Exporta a CSV los casos cerrados en la fecha actual.
     *
     * Ruta del archivo.
     * Numero de casos exportados.
     * Si falla la escritura.
     */
    public int exportarCasosAtendidosCsv(String ruta) throws IOException {
        int exportados = 0;
        String hoy = LocalDate.now().toString();
        FileWriter writer = new FileWriter(ruta);
        writer.write("id,fecha,cliente,tipo,incidente,prioridad,tecnico,unidad,ubicacion,descripcion\n");
        for (int i = 0; i < historicoSolicitudes.tamano(); i++) {
            SolicitudServicio solicitud = historicoSolicitudes.obtener(i);
            if (solicitud.getEstado() == EstadoSolicitud.CERRADA
                    && hoy.equals(solicitud.getFechaCierre())) {
                writer.write(csv(solicitud.getId()) + "," + csv(solicitud.getFechaCierre()) + ","
                        + csv(solicitud.getCliente().getNombre()) + "," + solicitud.getTipoServicio() + ","
                        + csv(String.valueOf(solicitud.getTipoIncidente())) + ","
                        + solicitud.getPrioridad() + "," + csv(solicitud.getTecnicoAsignado().getNombre()) + ","
                        + csv(solicitud.getUnidadAsignada().getPlaca()) + "," + csv(solicitud.getUbicacion()) + ","
                        + csv(solicitud.getDescripcion()) + "\n");
                exportados++;
            }
        }
        writer.close();
        return exportados;
    }

    /** Clientes registrados. */
    public ArregloDinamico<Cliente> getClientes() { return clientes; }

    /** Tecnicos registrados. */
    public ArregloDinamico<Tecnico> getTecnicos() { return tecnicos; }

    /** Unidades registradas. */
    public ArregloDinamico<UnidadServicio> getUnidades() { return unidades; }

    /** Historico de solicitudes. */
    public ArregloDinamico<SolicitudServicio> getHistoricoSolicitudes() { return historicoSolicitudes; }

    /** Cola de ordinarias pendientes. */
    public Cola<SolicitudServicio> getSolicitudesOrdinarias() { return solicitudesOrdinarias; }

    /** Cola de criticas pendientes. */
    public ColaPrioridad<SolicitudServicio> getSolicitudesCriticas() { return solicitudesCriticas; }

    /** Pila de kits en revision. */
    public Pila<KitAtencion> getKitsRevision() { return kitsRevision; }

    /** Pila de movimientos recientes. */
    public Pila<MovimientoOperacion> getMovimientos() { return movimientos; }

    /**
     * Busca cliente por ID.
     *
     * ID del cliente.
     */
    public Cliente buscarCliente(String id) {
        for (int i = 0; i < clientes.tamano(); i++) {
            if (clientes.obtener(i).getId().equalsIgnoreCase(id)) {
                return clientes.obtener(i);
            }
        }
        return null;
    }

    /**
     * Busca clientes por id, nombre o telefono.
     *
     * Texto de busqueda.
     * Coincidencias encontradas.
     */
    public ArregloDinamico<Cliente> buscarClientesPorTexto(String texto) {
        ArregloDinamico<Cliente> resultado = new ArregloDinamico<Cliente>();
        String criterio = texto == null ? "" : texto.trim().toLowerCase();
        for (int i = 0; i < clientes.tamano(); i++) {
            Cliente cliente = clientes.obtener(i);
            if (criterio.length() == 0
                    || cliente.getId().toLowerCase().contains(criterio)
                    || cliente.getNombre().toLowerCase().contains(criterio)
                    || cliente.getTelefono().toLowerCase().contains(criterio)) {
                resultado.agregar(cliente);
            }
        }
        return resultado;
    }

    /**
     * Tecnicos actualmente disponibles.
     */
    public ArregloDinamico<Tecnico> obtenerTecnicosDisponibles() {
        ArregloDinamico<Tecnico> resultado = new ArregloDinamico<Tecnico>();
        for (int i = 0; i < tecnicos.tamano(); i++) {
            Tecnico tecnico = tecnicos.obtener(i);
            if (tecnico.estaDisponible()) {
                resultado.agregar(tecnico);
            }
        }
        return resultado;
    }

    /**
     * Unidades actualmente disponibles.
     */
    public ArregloDinamico<UnidadServicio> obtenerUnidadesDisponibles() {
        ArregloDinamico<UnidadServicio> resultado = new ArregloDinamico<UnidadServicio>();
        for (int i = 0; i < unidades.tamano(); i++) {
            UnidadServicio unidad = unidades.obtener(i);
            if (unidad.estaDisponible()) {
                resultado.agregar(unidad);
            }
        }
        return resultado;
    }

    /**
     * Servicio solicitado.
     * Unidades disponibles compatibles con ese servicio.
     */
    public ArregloDinamico<UnidadServicio> obtenerUnidadesDisponiblesPara(TipoServicio tipoServicio) {
        ArregloDinamico<UnidadServicio> resultado = new ArregloDinamico<UnidadServicio>();
        for (int i = 0; i < unidades.tamano(); i++) {
            UnidadServicio unidad = unidades.obtener(i);
            if (unidad.estaDisponible() && esUnidadCompatible(tipoServicio, unidad.getTipo())) {
                resultado.agregar(unidad);
            }
        }
        return resultado;
    }

    /**
     * Solicitudes pendientes ordinarias y criticas.
     */
    public ArregloDinamico<SolicitudServicio> obtenerSolicitudesPendientes() {
        ArregloDinamico<SolicitudServicio> resultado = new ArregloDinamico<SolicitudServicio>();
        for (int i = 0; i < historicoSolicitudes.tamano(); i++) {
            SolicitudServicio solicitud = historicoSolicitudes.obtener(i);
            if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE
                    && solicitud.getPrioridad() == 5) {
                resultado.agregar(solicitud);
            }
        }
        for (int i = 0; i < historicoSolicitudes.tamano(); i++) {
            SolicitudServicio solicitud = historicoSolicitudes.obtener(i);
            if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE
                    && solicitud.getPrioridad() == 4) {
                resultado.agregar(solicitud);
            }
        }
        for (int i = 0; i < historicoSolicitudes.tamano(); i++) {
            SolicitudServicio solicitud = historicoSolicitudes.obtener(i);
            if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE
                    && solicitud.getPrioridad() < 4) {
                resultado.agregar(solicitud);
            }
        }
        return resultado;
    }

    /**
     * Solicitudes en ejecucion que pueden cerrarse.
     */
    public ArregloDinamico<SolicitudServicio> obtenerSolicitudesEnEjecucion() {
        ArregloDinamico<SolicitudServicio> resultado = new ArregloDinamico<SolicitudServicio>();
        for (int i = 0; i < historicoSolicitudes.tamano(); i++) {
            SolicitudServicio solicitud = historicoSolicitudes.obtener(i);
            if (solicitud.getEstado() == EstadoSolicitud.EN_EJECUCION) {
                resultado.agregar(solicitud);
            }
        }
        return resultado;
    }

    /**
     * Kits en revision desde el mas reciente.
     */
    public ArregloDinamico<KitAtencion> obtenerKitsEnRevision() {
        ArregloDinamico<KitAtencion> resultado = new ArregloDinamico<KitAtencion>();
        for (int i = 0; i < kitsRevision.tamano(); i++) {
            resultado.agregar(kitsRevision.obtenerDesdeTope(i));
        }
        return resultado;
    }

    /**
     * Kits en revision que aun requieren reparacion o reposicion.
     */
    public ArregloDinamico<KitAtencion> obtenerKitsPendientesDeReparacion() {
        ArregloDinamico<KitAtencion> resultado = new ArregloDinamico<KitAtencion>();
        for (int i = 0; i < kitsRevision.tamano(); i++) {
            KitAtencion kit = kitsRevision.obtenerDesdeTope(i);
            if (kit.isRequiereReposicion()) {
                resultado.agregar(kit);
            }
        }
        return resultado;
    }

    /**
     * Busca tecnico por ID.
     *
     * ID del tecnico.
     */
    public Tecnico buscarTecnico(String id) {
        for (int i = 0; i < tecnicos.tamano(); i++) {
            if (tecnicos.obtener(i).getId().equalsIgnoreCase(id)) {
                return tecnicos.obtener(i);
            }
        }
        return null;
    }

    /**
     * Busca unidad por UUID.
     *
     * UUID de unidad.
     */
    public UnidadServicio buscarUnidad(String uuid) {
        for (int i = 0; i < unidades.tamano(); i++) {
            if (unidades.obtener(i).getUuid().equalsIgnoreCase(uuid)) {
                return unidades.obtener(i);
            }
        }
        return null;
    }

    /**
     * Busca solicitud por ID.
     *
     * ID de solicitud.
     */
    public SolicitudServicio buscarSolicitud(String id) {
        for (int i = 0; i < historicoSolicitudes.tamano(); i++) {
            if (historicoSolicitudes.obtener(i).getId().equalsIgnoreCase(id)) {
                return historicoSolicitudes.obtener(i);
            }
        }
        return null;
    }

    private void quitarDePendientes(SolicitudServicio solicitud) {
        solicitudesOrdinarias.eliminarElemento(solicitud);
        solicitudesCriticas.eliminarElemento(solicitud);
    }

    private void devolverAPendientes(SolicitudServicio solicitud) {
        if (solicitud.esCritica()) {
            solicitudesCriticas.insertar(solicitud);
        } else {
            solicitudesOrdinarias.encolar(solicitud);
        }
    }

    private void registrarMovimiento(String tipo, String detalle) {
        movimientos.apilar(new MovimientoOperacion(tipo, detalle));
    }

    private boolean esUnidadCompatible(TipoServicio servicio, TipoUnidad unidad) {
        if (servicio == TipoServicio.GRUA) {
            return unidad == TipoUnidad.GRUA;
        }
        if (servicio == TipoServicio.MOTO_APOYO) {
            return unidad == TipoUnidad.MOTO_APOYO;
        }
        if (servicio == TipoServicio.CAMIONETA_ASISTENCIA) {
            return unidad == TipoUnidad.CAMIONETA;
        }
        if (servicio == TipoServicio.VEHICULO_LIVIANO) {
            return unidad == TipoUnidad.VEHICULO_LIVIANO;
        }
        return false;
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().length() == 0) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }

    private String csv(String texto) {
        if (texto == null) {
            return "";
        }
        String limpio = texto.replace("\"", "\"\"");
        return "\"" + limpio + "\"";
    }
}
