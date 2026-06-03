package autorescate.model.entidades;

import autorescate.model.programa.EstadoTecnico;

public class Tecnico {
    private String id;
    private String nombre;
    private String especialidad;
    private EstadoTecnico estado;
    private String zona;
    
    public Tecnico(String id, String nombre, String especialidad, String zona) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.zona = zona;
        this.estado = EstadoTecnico.DISPONIBLE; // Estado inicial.
    }
 
 
    public boolean estaDisponible() {
        return estado == EstadoTecnico.DISPONIBLE;
    }
 
    public String getId() { return id; }
 
    public String getNombre() { return nombre; }
 
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getEspecialidad() { return especialidad; }
 
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public EstadoTecnico getEstado() { return estado; }
 
    public void setEstado(EstadoTecnico estado) { this.estado = estado; }

    public String getZona() { return zona; }
 
    public void setZona(String zona) { this.zona = zona; }
 
    @Override
    public String toString() {
        return id + " | " + nombre + " | " + especialidad + " | " + estado + " | Zona: " + zona;
    }
}

