package autorescate.model.entidades;

import autorescate.model.programa.*;

public class Cliente {

    /** Identificador Ãºnico del cliente. */
    private String id;

    /** Nombre completo o razÃ³n social. */
    private String nombre;

    /** TelÃ©fono de contacto. */
    private String telefono;

    /** ClasificaciÃ³n del cliente (afecta prioridad de solicitudes, Regla 11). */
    private TipoCliente tipo;

    /**
     * Construye un cliente con todos sus atributos.
     *
     * Identificador Ãºnico.
     * Nombre o razÃ³n social.
     * TelÃ©fono de contacto.
     * Tipo/clasificaciÃ³n del cliente.
     */
    public Cliente(String id, String nombre, String telefono, TipoCliente tipo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    // â”€â”€ Getters y Setters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    /** Identificador Ãºnico del cliente. */
    public String getId() { return id; }

    /** Nombre del cliente. */
    public String getNombre() { return nombre; }

    /** Nuevo nombre del cliente. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** TelÃ©fono del cliente. */
    public String getTelefono() { return telefono; }

    /** Nuevo telÃ©fono del cliente. */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** Tipo/clasificaciÃ³n del cliente. */
    public TipoCliente getTipo() { return tipo; }

    /** Nuevo tipo del cliente. */
    public void setTipo(TipoCliente tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | Tel: " + telefono + " | " + tipo;
    }
}

