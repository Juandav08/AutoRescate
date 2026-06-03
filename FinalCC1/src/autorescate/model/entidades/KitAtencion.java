package autorescate.model.entidades;

import autorescate.model.programa.EstadoKit;

/**
 * Kit de atencion usado por los tecnicos y revisado con comportamiento LIFO.
 */
public class KitAtencion {

    private String id;
    private String descripcion;
    private EstadoKit estado;
    private boolean requiereReposicion;

    /**
     * Construye un kit de atencion.
     *
     * Identificador del kit.
     * Descripcion del contenido.
     */
    public KitAtencion(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = EstadoKit.DISPONIBLE;
        this.requiereReposicion = false;
    }

    /** Identificador del kit. */
    public String getId() { return id; }

    /** Descripcion del kit. */
    public String getDescripcion() { return descripcion; }

    /** Estado actual del kit. */
    public EstadoKit getEstado() { return estado; }

    /** Nuevo estado del kit. */
    public void setEstado(EstadoKit estado) { this.estado = estado; }

    /** {@code true} si requiere reposicion. */
    public boolean isRequiereReposicion() { return requiereReposicion; }

    /** Indica si requiere reposicion. */
    public void setRequiereReposicion(boolean requiereReposicion) {
        this.requiereReposicion = requiereReposicion;
    }

    @Override
    public String toString() {
        return id + " | " + descripcion + " | " + estado
                + (requiereReposicion ? " | REPOSICION" : "");
    }
}
