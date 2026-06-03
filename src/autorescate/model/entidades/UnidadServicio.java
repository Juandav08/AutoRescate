package autorescate.model.entidades;

import autorescate.model.programa.EstadoUnidad;
import autorescate.model.programa.TipoUnidad;

public class UnidadServicio {
	 
    private String uuid;
    private TipoUnidad tipo;
    private EstadoUnidad estado;
    private String zona;
    private String placa;
    
    public UnidadServicio(String uuid, TipoUnidad tipo, String zona, String placa) {
        this.uuid = uuid;
        this.tipo = tipo;
        this.zona = zona;
        this.placa = placa;
        this.estado = EstadoUnidad.DISPONIBLE; // Estado inicial.
    }
 
    public boolean estaDisponible() {
        return estado == EstadoUnidad.DISPONIBLE;
    }
    public String getUuid() { return uuid; }

    public TipoUnidad getTipo() { return tipo; }

    public EstadoUnidad getEstado() { return estado; }
 
    public void setEstado(EstadoUnidad estado) { this.estado = estado; }
 
    public String getZona() { return zona; }
 
    public void setZona(String zona) { this.zona = zona; }
 
    public String getPlaca() { return placa; }
 
    @Override
    public String toString() {
        return placa + " | " + tipo + " | " + estado + " | Zona: " + zona;
    }
}
