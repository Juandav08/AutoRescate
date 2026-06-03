package autorescate.model.entidades;

import autorescate.model.programa.*;

public class Cliente {

    /** Identificador único del cliente. */
    private String id;

    /** Nombre completo o razón social. */
    private String nombre;

    /** Teléfono de contacto. */
    private String telefono;

    /** Clasificación del cliente (afecta prioridad de solicitudes, Regla 11). */
    private TipoCliente tipo;

    /**
     * Construye un cliente con todos sus atributos.
     *
     * @param id       Identificador único.
     * @param nombre   Nombre o razón social.
     * @param telefono Teléfono de contacto.
     * @param tipo     Tipo/clasificación del cliente.
     */
    public Cliente(String id, String nombre, String telefono, TipoCliente tipo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    // ── Getters y Setters ─────────────────────────────────────────────────────

    /** @return Identificador único del cliente. */
    public String getId() { return id; }

    /** @return Nombre del cliente. */
    public String getNombre() { return nombre; }

    /** @param nombre Nuevo nombre del cliente. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return Teléfono del cliente. */
    public String getTelefono() { return telefono; }

    /** @param telefono Nuevo teléfono del cliente. */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** @return Tipo/clasificación del cliente. */
    public TipoCliente getTipo() { return tipo; }

    /** @param tipo Nuevo tipo del cliente. */
    public void setTipo(TipoCliente tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | Tel: " + telefono + " | " + tipo;
    }
}

