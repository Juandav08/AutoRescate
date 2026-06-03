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


    public MovimientoOperacion(String tipo, String detalle) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.timestamp = System.currentTimeMillis();
    }

 
    public String getTipo() { return tipo; }

    public String getDetalle() { return detalle; }

    public long getTimestamp() { return timestamp; }

    public SolicitudServicio getSolicitud() { return solicitud; }

    public void setSolicitud(SolicitudServicio solicitud) { this.solicitud = solicitud; }

    public Tecnico getTecnico() { return tecnico; }

    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    public UnidadServicio getUnidad() { return unidad; }

    public void setUnidad(UnidadServicio unidad) { this.unidad = unidad; }

    public EstadoSolicitud getEstadoSolicitudAnterior() { return estadoSolicitudAnterior; }

    public void setEstadoSolicitudAnterior(EstadoSolicitud estadoSolicitudAnterior) {
        this.estadoSolicitudAnterior = estadoSolicitudAnterior;
    }

    public EstadoTecnico getEstadoTecnicoAnterior() { return estadoTecnicoAnterior; }

    public void setEstadoTecnicoAnterior(EstadoTecnico estadoTecnicoAnterior) {
        this.estadoTecnicoAnterior = estadoTecnicoAnterior;
    }

    public EstadoUnidad getEstadoUnidadAnterior() { return estadoUnidadAnterior; }

    public void setEstadoUnidadAnterior(EstadoUnidad estadoUnidadAnterior) {
        this.estadoUnidadAnterior = estadoUnidadAnterior;
    }

    public String getFechaCierreAnterior() { return fechaCierreAnterior; }

    public void setFechaCierreAnterior(String fechaCierreAnterior) {
        this.fechaCierreAnterior = fechaCierreAnterior;
    }

    @Override
    public String toString() {
        return tipo + " | " + detalle;
    }
}
