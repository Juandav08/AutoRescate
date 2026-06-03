package autorescate.controller;

import java.io.IOException;

import autorescate.model.SistemaAutoRescate;
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
import autorescate.model.programa.EstadoUnidad;
import autorescate.model.programa.TipoCliente;
import autorescate.model.programa.TipoIncidente;
import autorescate.model.programa.TipoServicio;
import autorescate.model.programa.TipoUnidad;


public class AutoRescateController {

    private SistemaAutoRescate sistema;


    public AutoRescateController() {
        sistema = new SistemaAutoRescate();
    }


    public SistemaAutoRescate getSistema() { return sistema; }

    /** Registra un cliente. */
    public Cliente registrarCliente(String nombre, String telefono, TipoCliente tipo) {
        return sistema.registrarCliente(nombre, telefono, tipo);
    }

    /** Registra un tecnico. */
    public Tecnico registrarTecnico(String nombre, String especialidad, String zona) {
        return sistema.registrarTecnico(nombre, especialidad, zona);
    }

    /** Registra una unidad. */
    public UnidadServicio registrarUnidad(TipoUnidad tipo, String zona, String placa) {
        return sistema.registrarUnidad(tipo, zona, placa);
    }

    /** Crea una solicitud. */
    public SolicitudServicio crearSolicitud(String clienteId, TipoServicio tipoServicio, TipoIncidente tipoIncidente,
            String descripcion, String ubicacion, int prioridad) {
        Cliente cliente = sistema.buscarCliente(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe el cliente " + clienteId);
        }
        return sistema.crearSolicitud(cliente, tipoServicio, tipoIncidente, descripcion, ubicacion, prioridad);
    }

    /** Crea una solicitud usando un cliente ya seleccionado. */
    public SolicitudServicio crearSolicitud(Cliente cliente, TipoServicio tipoServicio, TipoIncidente tipoIncidente,
            String descripcion, String ubicacion, int prioridad) {
        return sistema.crearSolicitud(cliente, tipoServicio, tipoIncidente, descripcion, ubicacion, prioridad);
    }

    /** Asigna recursos a una solicitud. */
    public void asignarRecursos(String solicitudId, String tecnicoId, String unidadUuid) {
        SolicitudServicio solicitud = sistema.buscarSolicitud(solicitudId);
        Tecnico tecnico = sistema.buscarTecnico(tecnicoId);
        UnidadServicio unidad = sistema.buscarUnidad(unidadUuid);
        if (solicitud == null) {
            throw new IllegalArgumentException("No existe la solicitud " + solicitudId);
        }
        if (tecnico == null) {
            throw new IllegalArgumentException("No existe el tecnico " + tecnicoId);
        }
        if (unidad == null) {
            throw new IllegalArgumentException("No existe la unidad " + unidadUuid);
        }
        sistema.asignarRecursos(solicitud, tecnico, unidad);
    }

    /** Asigna recursos usando objetos ya seleccionados en la vista. */
    public void asignarRecursos(SolicitudServicio solicitud, Tecnico tecnico, UnidadServicio unidad) {
        sistema.asignarRecursos(solicitud, tecnico, unidad);
    }

    /** Cambia el estado de una unidad. */
    public void cambiarEstadoUnidad(String unidadUuid, EstadoUnidad estado) {
        UnidadServicio unidad = sistema.buscarUnidad(unidadUuid);
        if (unidad == null) {
            throw new IllegalArgumentException("No existe la unidad " + unidadUuid);
        }
        sistema.cambiarEstadoUnidad(unidad, estado);
    }

    /** Cambia el estado usando una unidad seleccionada. */
    public void cambiarEstadoUnidad(UnidadServicio unidad, EstadoUnidad estado) {
        sistema.cambiarEstadoUnidad(unidad, estado);
    }

    /** Cierra una solicitud. */
    public void cerrarServicio(String solicitudId) {
        SolicitudServicio solicitud = sistema.buscarSolicitud(solicitudId);
        if (solicitud == null) {
            throw new IllegalArgumentException("No existe la solicitud " + solicitudId);
        }
        sistema.cerrarServicio(solicitud);
    }

    /** Cierra una solicitud seleccionada. */
    public void cerrarServicio(SolicitudServicio solicitud) {
        sistema.cerrarServicio(solicitud);
    }

    /** Registra un kit en revision. */
    public KitAtencion registrarKitEnRevision(String descripcion, boolean requiereReposicion) {
        return sistema.registrarKitEnRevision(descripcion, requiereReposicion);
    }

    /** Retira el ultimo kit revisado. */
    public KitAtencion retirarUltimoKitRevisado() {
        return sistema.retirarUltimoKitRevisado();
    }

    /** Marca el ultimo kit como reparado o repuesto. */
    public KitAtencion repararUltimoKitEnRevision() {
        return sistema.repararUltimoKitEnRevision();
    }

    /** Marca como reparado o repuesto un kit seleccionado. */
    public KitAtencion repararKitEnRevision(KitAtencion kit) {
        return sistema.repararKitEnRevision(kit);
    }

    /** Registra un movimiento informativo. */
    public void registrarMovimiento(String detalle) {
        sistema.registrarMovimientoManual(detalle);
    }

    /** Revierte la ultima operacion. */
    public String revertirUltimaOperacion() {
        return sistema.revertirUltimaOperacion();
    }

    /** Exporta casos cerrados del dia. */
    public int exportarCsv(String ruta) throws IOException {
        return sistema.exportarCasosAtendidosCsv(ruta);
    }

    /** Busca clientes por id, nombre o telefono. */
    public ArregloDinamico<Cliente> buscarClientes(String texto) {
        return sistema.buscarClientesPorTexto(texto);
    }

    /** Tecnicos disponibles para asignacion. */
    public ArregloDinamico<Tecnico> obtenerTecnicosDisponibles() {
        return sistema.obtenerTecnicosDisponibles();
    }

    /** Unidades disponibles para asignacion. */
    public ArregloDinamico<UnidadServicio> obtenerUnidadesDisponibles() {
        return sistema.obtenerUnidadesDisponibles();
    }

    /** Unidades disponibles compatibles con una solicitud. */
    public ArregloDinamico<UnidadServicio> obtenerUnidadesDisponiblesPara(SolicitudServicio solicitud) {
        if (solicitud == null) {
            return new ArregloDinamico<UnidadServicio>();
        }
        return sistema.obtenerUnidadesDisponiblesPara(solicitud.getTipoServicio());
    }

    /** Solicitudes pendientes. */
    public ArregloDinamico<SolicitudServicio> obtenerSolicitudesPendientes() {
        return sistema.obtenerSolicitudesPendientes();
    }

    /** Solicitudes en ejecucion. */
    public ArregloDinamico<SolicitudServicio> obtenerSolicitudesEnEjecucion() {
        return sistema.obtenerSolicitudesEnEjecucion();
    }

    /** Kits actualmente en revision. */
    public ArregloDinamico<KitAtencion> obtenerKitsEnRevision() {
        return sistema.obtenerKitsEnRevision();
    }

    /** Kits que requieren reparacion o reposicion. */
    public ArregloDinamico<KitAtencion> obtenerKitsPendientesDeReparacion() {
        return sistema.obtenerKitsPendientesDeReparacion();
    }

    /** Listado de clientes. */
    public String listarClientes() {
        StringBuilder sb = new StringBuilder();
        ArregloDinamico<Cliente> datos = sistema.getClientes();
        for (int i = 0; i < datos.tamano(); i++) {
            Cliente c = datos.obtener(i);
            sb.append(c.getId()).append(" | ").append(c).append(" | Tel: ")
                    .append(c.getTelefono()).append("\n");
        }
        return textoVacio(sb);
    }

    /** Listado de tecnicos. */
    public String listarTecnicos() {
        StringBuilder sb = new StringBuilder();
        ArregloDinamico<Tecnico> datos = sistema.getTecnicos();
        for (int i = 0; i < datos.tamano(); i++) {
            Tecnico t = datos.obtener(i);
            sb.append(t.getId()).append(" | ").append(t).append("\n");
        }
        return textoVacio(sb);
    }

    /** Tecnicos disponibles. */
    public String listarTecnicosDisponibles() {
        StringBuilder sb = new StringBuilder();
        ArregloDinamico<Tecnico> datos = sistema.obtenerTecnicosDisponibles();
        for (int i = 0; i < datos.tamano(); i++) {
            sb.append(datos.obtener(i)).append("\n");
        }
        return textoVacio(sb);
    }

    /** Listado de unidades. */
    public String listarUnidades() {
        StringBuilder sb = new StringBuilder();
        ArregloDinamico<UnidadServicio> datos = sistema.getUnidades();
        for (int i = 0; i < datos.tamano(); i++) {
            UnidadServicio u = datos.obtener(i);
            sb.append(u).append(" | UUID: ").append(u.getUuid()).append("\n");
        }
        return textoVacio(sb);
    }

    /** Unidades disponibles. */
    public String listarUnidadesDisponibles() {
        StringBuilder sb = new StringBuilder();
        ArregloDinamico<UnidadServicio> datos = sistema.obtenerUnidadesDisponibles();
        for (int i = 0; i < datos.tamano(); i++) {
            UnidadServicio u = datos.obtener(i);
            sb.append(u).append(" | UUID: ").append(u.getUuid()).append("\n");
        }
        return textoVacio(sb);
    }

    /** Solicitudes ordinarias pendientes. */
    public String listarPendientesOrdinarios() {
        StringBuilder sb = new StringBuilder();
        Cola<SolicitudServicio> datos = sistema.getSolicitudesOrdinarias();
        for (int i = 0; i < datos.tamano(); i++) {
            sb.append(datos.obtener(i)).append("\n");
        }
        return textoVacio(sb);
    }

    /** Solicitudes criticas pendientes. */
    public String listarCriticos() {
        StringBuilder sb = new StringBuilder();
        ColaPrioridad<SolicitudServicio> datos = sistema.getSolicitudesCriticas();
        for (int i = 0; i < datos.tamano(); i++) {
            sb.append(datos.obtener(i)).append("\n");
        }
        return textoVacio(sb);
    }

    /** Historico de solicitudes. */
    public String listarSolicitudes() {
        StringBuilder sb = new StringBuilder();
        ArregloDinamico<SolicitudServicio> datos = sistema.getHistoricoSolicitudes();
        for (int i = 0; i < datos.tamano(); i++) {
            SolicitudServicio s = datos.obtener(i);
            sb.append(s).append(" | Tecnico: ")
                    .append(s.getTecnicoAsignado() == null ? "-" : s.getTecnicoAsignado().getId())
                    .append(" | Unidad: ")
                    .append(s.getUnidadAsignada() == null ? "-" : s.getUnidadAsignada().getPlaca())
                    .append("\n");
        }
        return textoVacio(sb);
    }

    /** Recursos agrupados por estado. */
    public String listarRecursosPorEstado() {
        StringBuilder sb = new StringBuilder();
        sb.append("UNIDADES\n");
        ArregloDinamico<UnidadServicio> unidades = sistema.getUnidades();
        for (int i = 0; i < unidades.tamano(); i++) {
            sb.append(unidades.obtener(i)).append("\n");
        }
        sb.append("\nTECNICOS\n");
        ArregloDinamico<Tecnico> tecnicos = sistema.getTecnicos();
        for (int i = 0; i < tecnicos.tamano(); i++) {
            sb.append(tecnicos.obtener(i)).append("\n");
        }
        return textoVacio(sb);
    }

    /** Kits en revision desde el ultimo ingresado. */
    public String listarKitsRevision() {
        StringBuilder sb = new StringBuilder();
        Pila<KitAtencion> pila = sistema.getKitsRevision();
        for (int i = 0; i < pila.tamano(); i++) {
            sb.append(pila.obtenerDesdeTope(i)).append("\n");
        }
        return textoVacio(sb);
    }

    /** Movimientos recientes desde el ultimo. */
    public String listarMovimientos() {
        StringBuilder sb = new StringBuilder();
        Pila<MovimientoOperacion> pila = sistema.getMovimientos();
        for (int i = 0; i < pila.tamano(); i++) {
            sb.append(pila.obtenerDesdeTope(i)).append("\n");
        }
        return textoVacio(sb);
    }

    private String textoVacio(StringBuilder sb) {
        if (sb.length() == 0) {
            return "Sin registros.";
        }
        return sb.toString();
    }
}
