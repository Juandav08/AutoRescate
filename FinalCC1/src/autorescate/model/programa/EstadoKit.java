package autorescate.model.programa;

public enum EstadoKit {
    /** El kit está disponible para despacho. */
    DISPONIBLE,
    /** El kit está siendo utilizado en un servicio activo. */
    EN_USO,
    /** El kit regresó de un servicio y está en zona de revisión (LIFO). */
    EN_REVISION
}

