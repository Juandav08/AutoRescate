package autorescate.model.programa;

/**
 * Estados de una solicitud de servicio.
 */
public enum EstadoSolicitud {
    PENDIENTE,
    /** La solicitud tiene recursos asignados y esta siendo atendida. */
    EN_EJECUCION,
    /** La solicitud fue completamente atendida. */
    CERRADA,
    /** La solicitud fue cancelada. */
    CANCELADA
}
