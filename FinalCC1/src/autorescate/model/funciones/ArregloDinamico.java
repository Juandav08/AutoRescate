package autorescate.model.funciones;


public class ArregloDinamico<T> {

    private static final int CAPACIDAD_INICIAL = 20;
    private Object[] elementos;
    private int tamano;

    /**
     * Construye un arreglo dinamico con capacidad inicial por defecto.
     */
    public ArregloDinamico() {
        elementos = new Object[CAPACIDAD_INICIAL];
        tamano = 0;
    }

 
    public void agregar(T elemento) {
        if (tamano == elementos.length) {
            redimensionar();
        }
        elementos[tamano++] = elemento;
    }

    /**
     * Obtiene un elemento por indice.
     *
     * Posicion solicitada.
     * Elemento almacenado.
     */
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        verificarIndice(indice);
        return (T) elementos[indice];
    }

    /**
     * Elimina un elemento por indice.
     */
    public void eliminar(int indice) {
        verificarIndice(indice);
        for (int i = indice; i < tamano - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        elementos[--tamano] = null;
    }

    /**
     * Elimina la primera aparicion exacta del elemento indicado.
     */
    public boolean eliminarElemento(T elemento) {
        for (int i = 0; i < tamano; i++) {
            if (elementos[i] == elemento) {
                eliminar(i);
                return true;
            }
        }
        return false;
    }

 
    public int tamano() {
        return tamano;
    }

    public boolean estaVacio() {
        return tamano == 0;
    }

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
    }

    private void redimensionar() {
        Object[] nuevo = new Object[elementos.length * 2];
        for (int i = 0; i < tamano; i++) {
            nuevo[i] = elementos[i];
        }
        elementos = nuevo;
    }
}
