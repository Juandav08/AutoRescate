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
     * @param tipo Tipo de movimiento.
     * @param detalle Descripcion breve.
     */
    public MovimientoOperacion(String tipo, String detalle) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.timestamp = System.currentTimeMillis();
    }

    /** @return Tipo del movimiento. */
    public String getTipo() { return tipo; }

    /** @return Detalle del movimiento. */
    public String getDetalle() { return detalle; }

    /** @return Marca de tiempo. */
    public long getTimestamp() { return timestamp; }

    /** @return Solicitud asociada. */
    public SolicitudServicio getSolicitud() { return solicitud; }

    /** @param solicitud Solicitud asociada. */
    public void setSolicitud(SolicitudServicio solicitud) { this.solicitud = solicitud; }

    /** @return Tecnico asociado. */
    public Tecnico getTecnico() { return tecnico; }

    /** @param tecnico Tecnico asociado. */
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    /** @return Unidad asociada. */
    public UnidadServicio getUnidad() { return unidad; }

    /** @param unidad Unidad asociada. */
    public void setUnidad(UnidadServicio unidad) { this.unidad = unidad; }

    /** @return Estado anterior de solicitud. */
    public EstadoSolicitud getEstadoSolicitudAnterior() { return estadoSolicitudAnterior; }

    /** @param estadoSolicitudAnterior Estado anterior de solicitud. */
    public void setEstadoSolicitudAnterior(EstadoSolicitud estadoSolicitudAnterior) {
        this.estadoSolicitudAnterior = estadoSolicitudAnterior;
    }

    /** @return Estado anterior de tecnico. */
    public EstadoTecnico getEstadoTecnicoAnterior() { return estadoTecnicoAnterior; }

    /** @param estadoTecnicoAnterior Estado anterior de tecnico. */
    public void setEstadoTecnicoAnterior(EstadoTecnico estadoTecnicoAnterior) {
        this.estadoTecnicoAnterior = estadoTecnicoAnterior;
    }

    /** @return Estado anterior de unidad. */
    public EstadoUnidad getEstadoUnidadAnterior() { return estadoUnidadAnterior; }

    /** @param estadoUnidadAnterior Estado anterior de unidad. */
    public void setEstadoUnidadAnterior(EstadoUnidad estadoUnidadAnterior) {
        this.estadoUnidadAnterior = estadoUnidadAnterior;
    }

    /** @return Fecha de cierre anterior. */
    public String getFechaCierreAnterior() { return fechaCierreAnterior; }

    /** @param fechaCierreAnterior Fecha de cierre anterior. */
    public void setFechaCierreAnterior(String fechaCierreAnterior) {
        this.fechaCierreAnterior = fechaCierreAnterior;
    }

    @Override
    public String toString() {
        return tipo + " | " + detalle;
    }
}
