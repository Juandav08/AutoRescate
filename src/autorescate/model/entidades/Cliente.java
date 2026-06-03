package autorescate.model.entidades;

import autorescate.model.programa.*;

public class Cliente {
    private String id;
    private String nombre;
    private String telefono;
    private TipoCliente tipo;
    
    public Cliente(String id, String nombre, String telefono, TipoCliente tipo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public TipoCliente getTipo() { return tipo; }

    public void setTipo(TipoCliente tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | Tel: " + telefono + " | " + tipo;
    }
}

