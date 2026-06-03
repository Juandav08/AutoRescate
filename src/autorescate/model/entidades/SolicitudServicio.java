package autorescate.model.entidades;

import autorescate.model.programa.EstadoSolicitud;
import autorescate.model.programa.TipoIncidente;
import autorescate.model.programa.TipoServicio;

/**
 * Solicitud realizada por un cliente para recibir asistencia vehicular.
 */
public class SolicitudServicio implements Comparable<SolicitudServicio> {

    private String id;
    private Cliente cliente;
    private TipoServicio tipoServicio;
    private TipoIncidente tipoIncidente;
    private EstadoSolicitud estado;
    private int prioridad;
    private String descripcion;
    private String ubicacion;
    private Tecnico tecnicoAsignado;
    private UnidadServicio unidadAsignada;
    private long timestamp;
    private String fechaCierre;


    public SolicitudServicio(String id, Cliente cliente, TipoServicio tipoServicio, TipoIncidente tipoIncidente,
            String descripcion, String ubicacion, int prioridad) {
        this.id = id;
        this.cliente = cliente;
        this.tipoServicio = tipoServicio;
        this.tipoIncidente = tipoIncidente;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.prioridad = prioridad;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean esCritica() {
        return prioridad > 3;
    }

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

    public String getId() { return id; }

    public Cliente getCliente() { return cliente; }

    public TipoServicio getTipoServicio() { return tipoServicio; }

    public TipoIncidente getTipoIncidente() { return tipoIncidente; }

    public EstadoSolicitud getEstado() { return estado; }

    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    public int getPrioridad() { return prioridad; }
    
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    public String getDescripcion() { return descripcion; }

    public String getUbicacion() { return ubicacion; }

    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }

    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public UnidadServicio getUnidadAsignada() { return unidadAsignada; }

    public void setUnidadAsignada(UnidadServicio unidadAsignada) {
        this.unidadAsignada = unidadAsignada;
    }

    public long getTimestamp() { return timestamp; }

    public String getFechaCierre() { return fechaCierre; }

    public void setFechaCierre(String fechaCierre) { this.fechaCierre = fechaCierre; }

    @Override
    public String toString() {
        return id + " | " + tipoServicio + " | " + tipoIncidente + " | Cliente: " + cliente.getNombre()
                + " | Prioridad: " + prioridad + " | Estado: " + estado;
    }
}
