package autorescate.model.entidades;

import autorescate.model.programa.EstadoUnidad;
import autorescate.model.programa.TipoUnidad;

public class UnidadServicio {
	 
    /** Identificador Ãºnico generado con UUID (Regla 1). */
    private String uuid;
 
    /** Tipo de unidad de servicio (Regla 2). */
    private TipoUnidad tipo;
 
    /** Estado operativo actual (Regla 2). */
    private EstadoUnidad estado;
 
    /** Zona de operaciÃ³n (Regla 2). */
    private String zona;
 
    /**
     * Placa o cÃ³digo de identificaciÃ³n del vehÃ­culo.
     * Complementa el UUID para identificaciÃ³n operativa.
     */
    private String placa;
 
    /**
     * Construye una unidad de servicio.
     *
     * UUID generado externamente.
     * Tipo de unidad.
     * Zona de operaciÃ³n.
     * Placa o cÃ³digo de identificaciÃ³n.
     */
    public UnidadServicio(String uuid, TipoUnidad tipo, String zona, String placa) {
        this.uuid = uuid;
        this.tipo = tipo;
        this.zona = zona;
        this.placa = placa;
        this.estado = EstadoUnidad.DISPONIBLE; // Estado inicial.
    }
 
    /**
     * Indica si la unidad puede ser enviada a un nuevo servicio.
     * Regla 4 y 6: solo puede asignarse si estÃ¡ DISPONIBLE.
     *
     * {@code true} si la unidad estÃ¡ disponible.
     */
    public boolean estaDisponible() {
        return estado == EstadoUnidad.DISPONIBLE;
    }
 
    // â”€â”€ Getters y Setters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
 
    /** UUID de la unidad. */
    public String getUuid() { return uuid; }
 
    /** Tipo de unidad. */
    public TipoUnidad getTipo() { return tipo; }
 
    /** Estado operativo actual. */
    public EstadoUnidad getEstado() { return estado; }
 
    /**
     * Actualiza el estado de la unidad.
     * La validaciÃ³n de transiciones de estado se delega al controlador.
     *
     * Nuevo estado.
     */
    public void setEstado(EstadoUnidad estado) { this.estado = estado; }
 
    /** Zona de operaciÃ³n. */
    public String getZona() { return zona; }
 
    /** Nueva zona de operaciÃ³n. */
    public void setZona(String zona) { this.zona = zona; }
 
    /** Placa o cÃ³digo de identificaciÃ³n. */
    public String getPlaca() { return placa; }
 
    @Override
    public String toString() {
        return placa + " | " + tipo + " | " + estado + " | Zona: " + zona;
    }
}
