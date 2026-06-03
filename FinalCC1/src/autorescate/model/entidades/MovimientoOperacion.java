package autorescate.model.entidades;

import autorescate.model.programa.EstadoSolicitud;
import autorescate.model.programa.EstadoTecnico;
import autorescate.model.programa.EstadoUnidad;

/**
 * Movimiento reciente de la operacion, usado para auditoria y reversa simple.
 */
public class MovimientoOperacion {

    public static final String REGISTRO = "REGISTRO";
    public static final String ASIGNACION = "ASIGNACION";
    public static final String CIERRE = "CIERRE";
    public static final String CAMBIO_UNIDAD = "CAMBIO_UNIDAD";

    private String tipo;
    private String detalle;
    private long timestamp;
    private SolicitudServicio solicitud;
    private Tecnico tecnico;
    private UnidadServicio unidad;
    private EstadoSolicitud estadoSolicitudAnterior;
    private EstadoTecnico estadoTecnicoAnterior;
    private EstadoUnidad estadoUnidadAnterior;
    private String fechaCierreAnterior;

    /**
     * Crea un movimiento operativo.
     *
     * Tipo de movimiento.
     * Descripcion breve.
     */
    public MovimientoOperacion(String tipo, String detalle) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.timestamp = System.currentTimeMillis();
    }

    /** Tipo del movimiento. */
    public String getTipo() { return tipo; }

    /** Detalle del movimiento. */
    public String getDetalle() { return detalle; }

    /** Marca de tiempo. */
    public long getTimestamp() { return timestamp; }

    /** Solicitud asociada. */
    public SolicitudServicio getSolicitud() { return solicitud; }

    /** Solicitud asociada. */
    public void setSolicitud(SolicitudServicio solicitud) { this.solicitud = solicitud; }

    /** Tecnico asociado. */
    public Tecnico getTecnico() { return tecnico; }

    /** Tecnico asociado. */
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    /** Unidad asociada. */
    public UnidadServicio getUnidad() { return unidad; }

    /** Unidad asociada. */
    public void setUnidad(UnidadServicio unidad) { this.unidad = unidad; }

    /** Estado anterior de solicitud. */
    public EstadoSolicitud getEstadoSolicitudAnterior() { return estadoSolicitudAnterior; }

    /** Estado anterior de solicitud. */
    public void setEstadoSolicitudAnterior(EstadoSolicitud estadoSolicitudAnterior) {
        this.estadoSolicitudAnterior = estadoSolicitudAnterior;
    }

    /** Estado anterior de tecnico. */
    public EstadoTecnico getEstadoTecnicoAnterior() { return estadoTecnicoAnterior; }

    /** Estado anterior de tecnico. */
    public void setEstadoTecnicoAnterior(EstadoTecnico estadoTecnicoAnterior) {
        this.estadoTecnicoAnterior = estadoTecnicoAnterior;
    }

    /** Estado anterior de unidad. */
    public EstadoUnidad getEstadoUnidadAnterior() { return estadoUnidadAnterior; }

    /** Estado anterior de unidad. */
    public void setEstadoUnidadAnterior(EstadoUnidad estadoUnidadAnterior) {
        this.estadoUnidadAnterior = estadoUnidadAnterior;
    }

    /** Fecha de cierre anterior. */
    public String getFechaCierreAnterior() { return fechaCierreAnterior; }

    /** Fecha de cierre anterior. */
    public void setFechaCierreAnterior(String fechaCierreAnterior) {
        this.fechaCierreAnterior = fechaCierreAnterior;
    }

    @Override
    public String toString() {
        return tipo + " | " + detalle;
    }
}
