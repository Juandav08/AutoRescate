package autorescate.model.entidades;

import autorescate.model.programa.EstadoTecnico;

public class Tecnico {
	 
    /** Identificador único del técnico. */
    private String id;
 
    /** Nombre completo del técnico. */
    private String nombre;
 
    /** Especialidad (ej: "Mecánica", "Eléctrico", "Grúas"). */
    private String especialidad;
 
    /** Estado actual del técnico (Regla 3). */
    private EstadoTecnico estado;
 
    /** Zona donde opera habitualmente. */
    private String zona;
 
    /**
     * Construye un técnico con los datos requeridos.
     *
     * @param id          Identificador único.
     * @param nombre      Nombre completo.
     * @param especialidad Especialidad del técnico.
     * @param zona        Zona de operación.
     */
    public Tecnico(String id, String nombre, String especialidad, String zona) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.zona = zona;
        this.estado = EstadoTecnico.DISPONIBLE; // Estado inicial.
    }
 
    /**
     * Indica si el técnico puede ser asignado a un nuevo servicio.
     * Regla 5: solo puede asignarse si está DISPONIBLE.
     *
     * @return {@code true} si el técnico está disponible.
     */
    public boolean estaDisponible() {
        return estado == EstadoTecnico.DISPONIBLE;
    }
 
    // ── Getters y Setters ─────────────────────────────────────────────────────
 
    /** @return Identificador del técnico. */
    public String getId() { return id; }
 
    /** @return Nombre del técnico. */
    public String getNombre() { return nombre; }
 
    /** @param nombre Nuevo nombre. */
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    /** @return Especialidad del técnico. */
    public String getEspecialidad() { return especialidad; }
 
    /** @param especialidad Nueva especialidad. */
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
 
    /** @return Estado actual del técnico. */
    public EstadoTecnico getEstado() { return estado; }
 
    /** @param estado Nuevo estado del técnico. */
    public void setEstado(EstadoTecnico estado) { this.estado = estado; }
 
    /** @return Zona de operación del técnico. */
    public String getZona() { return zona; }
 
    /** @param zona Nueva zona. */
    public void setZona(String zona) { this.zona = zona; }
 
    @Override
    public String toString() {
        return id + " | " + nombre + " | " + especialidad + " | " + estado + " | Zona: " + zona;
    }
}

