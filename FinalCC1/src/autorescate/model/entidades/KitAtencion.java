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

    public KitAtencion(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = EstadoKit.DISPONIBLE;
        this.requiereReposicion = false;
    }


    public String getId() { return id; }

    public String getDescripcion() { return descripcion; }

    public EstadoKit getEstado() { return estado; }

    public void setEstado(EstadoKit estado) { this.estado = estado; }

    public boolean isRequiereReposicion() { return requiereReposicion; }

    public void setRequiereReposicion(boolean requiereReposicion) {
        this.requiereReposicion = requiereReposicion;
    }

    @Override
    public String toString() {
        return id + " | " + descripcion + " | " + estado
                + (requiereReposicion ? " | REPOSICION" : "");
    }
}
