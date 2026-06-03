package autorescate.model.programa;

/**
 * Estados operativos de un tecnico.
 */
public enum EstadoTecnico {
    DISPONIBLE,
    /** El tecnico esta actualmente en un servicio. */
    OCUPADO,
    /** El tecnico no esta activo en el sistema. */
    INACTIVO
}
