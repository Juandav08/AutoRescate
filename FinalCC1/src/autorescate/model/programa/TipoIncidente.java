package autorescate.model.programa;

public enum TipoIncidente {
    VARADO("Vehiculo varado", 3),
    PINCHADO("Llanta pinchada", 2),
    CHOQUE("Choque o accidente", 5),
    BATERIA("Bateria descargada", 2),
    SIN_COMBUSTIBLE("Sin combustible", 1),
    LLAVES_DENTRO("Llaves dentro del vehiculo", 1),
    FALLA_MECANICA("Falla mecanica", 3),
    FALLA_ELECTRICA("Falla electrica", 3),
    GRUA_URGENTE("Necesita grua urgente", 4),
    VEHICULO_BLOQUEA_VIA("Vehiculo bloquea via", 5),
    PERSONAS_EN_RIESGO("Personas en riesgo", 5),
    OTRO("Otro incidente", 3);

    private String descripcion;
    private int prioridadSugerida;

    TipoIncidente(String descripcion, int prioridadSugerida) {
        this.descripcion = descripcion;
        this.prioridadSugerida = prioridadSugerida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrioridadSugerida() {
        return prioridadSugerida;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
