package autorescate.model.programa;

/**
 * Estados operativos de una unidad de servicio.
 */
public enum EstadoServicio {
    DISPONIBLE,
    /** La unidad esta asignada a un caso activo. */
    ASIGNADA,
    /** La unidad esta en mantenimiento y no puede asignarse. */
    MANTENIMIENTO,
    /** La unidad esta fuera de servicio. */
    FUERA_DE_SERVICIO
}
