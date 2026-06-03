package autorescate.model.entidades;

import autorescate.model.programa.EstadoSolicitud;
import autorescate.model.programa.TipoServicio;

/**
 * Solicitud realizada por un cliente para recibir asistencia vehicular.
 */
public class SolicitudServicio implements Comparable<SolicitudServicio> {

    private String id;
    private Cliente cliente;
    private TipoServicio tipoServicio;
    private EstadoSolicitud estado;
    private int prioridad;
    private String descripcion;
    private String ubicacion;
    private Tecnico tecnicoAsignado;
    private UnidadServicio unidadAsignada;
    private long timestamp;
    private String fechaCierre;

    /**
     * Construye una solicitud de servicio.
     *
     * @param id Identificador unico.
     * @param cliente Cliente asociado.
     * @param tipoServicio Tipo de servicio requerido.
     * @param descripcion Descripcion del caso.
     * @param ubicacion Ubicacion del incidente.
     * @param prioridad Prioridad entre 1 y 10.
     */
    public SolicitudServicio(String id, Cliente cliente, TipoServicio tipoServicio,
            String descripcion, String ubicacion, int prioridad) {
        this.id = id;
        this.cliente = cliente;
        this.tipoServicio = tipoServicio;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.prioridad = prioridad;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * @return {@code true} si la solicitud es critica.
     */
    public boolean esCritica() {
        return prioridad > 3;
    }

    /**
     * @return {@code true} si tiene tecnico y unidad asignados.
     */
    public boolean tieneRecursosAsignados() {
        return tecnicoAsignado != null && unidadAsignada != null;
    }

    /**
     * Marca la solicitud como cerrada.
     *
     * @param fechaCierre Fecha ISO de cierre.
     */
    public void cerrar(String fechaCierre) {
        estado = EstadoSolicitud.CERRADA;
        this.fechaCierre = fechaCierre;
    }

    /**
     * Compara solicitudes para la cola de prioridad. Mayor prioridad gana; si hay
     * empate, gana la que llego primero.
     */
    @Override
    public int compareTo(SolicitudServicio otra) {
        if (prioridad != otra.prioridad) {
            return Integer.compare(prioridad, otra.prioridad);
        }
        return Long.compare(otra.timestamp, timestamp);
    }

    /** @return ID de solicitud. */
    public String getId() { return id; }

    /** @return Cliente asociado. */
    public Cliente getCliente() { return cliente; }

    /** @return Tipo de servicio. */
    public TipoServicio getTipoServicio() { return tipoServicio; }

    /** @return Estado actual. */
    public EstadoSolicitud getEstado() { return estado; }

    /** @param estado Nuevo estado. */
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    /** @return Prioridad. */
    public int getPrioridad() { return prioridad; }

    /** @param prioridad Nueva prioridad. */
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    /** @return Descripcion del caso. */
    public String getDescripcion() { return descripcion; }

    /** @return Ubicacion del incidente. */
    public String getUbicacion() { return ubicacion; }

    /** @return Tecnico asignado. */
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }

    /** @param tecnicoAsignado Tecnico asignado. */
    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    /** @return Unidad asignada. */
    public UnidadServicio getUnidadAsignada() { return unidadAsignada; }

    /** @param unidadAsignada Unidad asignada. */
    public void setUnidadAsignada(UnidadServicio unidadAsignada) {
        this.unidadAsignada = unidadAsignada;
    }

    /** @return Timestamp de creacion. */
    public long getTimestamp() { return timestamp; }

    /** @return Fecha ISO de cierre. */
    public String getFechaCierre() { return fechaCierre; }

    /** @param fechaCierre Fecha ISO de cierre. */
    public void setFechaCierre(String fechaCierre) { this.fechaCierre = fechaCierre; }

    @Override
    public String toString() {
        return id + " | " + tipoServicio + " | Cliente: " + cliente.getNombre()
                + " | Prioridad: " + prioridad + " | Estado: " + estado;
    }
}
