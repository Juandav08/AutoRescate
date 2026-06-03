package autorescate.model.programa;

public enum EstadoKit {
    /** El kit estÃ¡ disponible para despacho. */
    DISPONIBLE,
    /** El kit estÃ¡ siendo utilizado en un servicio activo. */
    EN_USO,
    /** El kit regresÃ³ de un servicio y estÃ¡ en zona de revisiÃ³n (LIFO). */
    EN_REVISION
}

