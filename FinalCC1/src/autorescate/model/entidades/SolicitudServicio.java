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
     * Identificador unico.
     * Cliente asociado.
     * Tipo de servicio requerido.
     * Descripcion del caso.
     * Ubicacion del incidente.
     * Prioridad entre 1 y 10.
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
     * {@code true} si la solicitud es critica.
     */
    public boolean esCritica() {
        return prioridad > 3;
    }

    /**
     * {@code true} si tiene tecnico y unidad asignados.
     */
    public boolean tieneRecursosAsignados() {
        return tecnicoAsignado != null && unidadAsignada != null;
    }

    /**
     * Marca la solicitud como cerrada.
     *
     * Fecha ISO de cierre.
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

    /** ID de solicitud. */
    public String getId() { return id; }

    /** Cliente asociado. */
    public Cliente getCliente() { return cliente; }

    /** Tipo de servicio. */
    public TipoServicio getTipoServicio() { return tipoServicio; }

    /** Estado actual. */
    public EstadoSolicitud getEstado() { return estado; }

    /** Nuevo estado. */
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    /** Prioridad. */
    public int getPrioridad() { return prioridad; }

    /** Nueva prioridad. */
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    /** Descripcion del caso. */
    public String getDescripcion() { return descripcion; }

    /** Ubicacion del incidente. */
    public String getUbicacion() { return ubicacion; }

    /** Tecnico asignado. */
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }

    /** Tecnico asignado. */
    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    /** Unidad asignada. */
    public UnidadServicio getUnidadAsignada() { return unidadAsignada; }

    /** Unidad asignada. */
    public void setUnidadAsignada(UnidadServicio unidadAsignada) {
        this.unidadAsignada = unidadAsignada;
    }

    /** Timestamp de creacion. */
    public long getTimestamp() { return timestamp; }

    /** Fecha ISO de cierre. */
    public String getFechaCierre() { return fechaCierre; }

    /** Fecha ISO de cierre. */
    public void setFechaCierre(String fechaCierre) { this.fechaCierre = fechaCierre; }

    @Override
    public String toString() {
        return id + " | " + tipoServicio + " | Cliente: " + cliente.getNombre()
                + " | Prioridad: " + prioridad + " | Estado: " + estado;
    }
}
