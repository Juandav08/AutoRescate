package autorescate.model.entidades;

import autorescate.model.programa.EstadoUnidad;
import autorescate.model.programa.TipoUnidad;

public class UnidadServicio {
	 
    /** Identificador único generado con UUID (Regla 1). */
    private String uuid;
 
    /** Tipo de unidad de servicio (Regla 2). */
    private TipoUnidad tipo;
 
    /** Estado operativo actual (Regla 2). */
    private EstadoUnidad estado;
 
    /** Zona de operación (Regla 2). */
    private String zona;
 
    /**
     * Placa o código de identificación del vehículo.
     * Complementa el UUID para identificación operativa.
     */
    private String placa;
 
    /**
     * Construye una unidad de servicio.
     *
     * @param uuid  UUID generado externamente.
     * @param tipo  Tipo de unidad.
     * @param zona  Zona de operación.
     * @param placa Placa o código de identificación.
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
     * Regla 4 y 6: solo puede asignarse si está DISPONIBLE.
     *
     * @return {@code true} si la unidad está disponible.
     */
    public boolean estaDisponible() {
        return estado == EstadoUnidad.DISPONIBLE;
    }
 
    // ── Getters y Setters ─────────────────────────────────────────────────────
 
    /** @return UUID de la unidad. */
    public String getUuid() { return uuid; }
 
    /** @return Tipo de unidad. */
    public TipoUnidad getTipo() { return tipo; }
 
    /** @return Estado operativo actual. */
    public EstadoUnidad getEstado() { return estado; }
 
    /**
     * Actualiza el estado de la unidad.
     * La validación de transiciones de estado se delega al controlador.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(EstadoUnidad estado) { this.estado = estado; }
 
    /** @return Zona de operación. */
    public String getZona() { return zona; }
 
    /** @param zona Nueva zona de operación. */
    public void setZona(String zona) { this.zona = zona; }
 
    /** @return Placa o código de identificación. */
    public String getPlaca() { return placa; }
 
    @Override
    public String toString() {
        return placa + " | " + tipo + " | " + estado + " | Zona: " + zona;
    }
}
