package autorescate.model.funciones;

/**
 * Cola de prioridad propia ordenada de mayor a menor prioridad.
 *
 * @param <T> Tipo comparable de elementos.
 */
public class ColaPrioridad<T extends Comparable<T>> {

    private static final int CAPACIDAD_INICIAL = 50;
    private Object[] elementos;
    private int tamano;

    /**
     * Construye una cola de prioridad vacia.
     */
    public ColaPrioridad() {
        elementos = new Object[CAPACIDAD_INICIAL];
        tamano = 0;
    }

    /**
     * Inserta un elemento manteniendo el mayor al frente.
     *
     * @param elemento Elemento a insertar.
     */
    @SuppressWarnings("unchecked")
    public void insertar(T elemento) {
        if (tamano == elementos.length) {
            redimensionar();
        }
        int posicion = tamano;
        while (posicion > 0 && ((T) elementos[posicion - 1]).compareTo(elemento) < 0) {
            elementos[posicion] = elementos[posicion - 1];
            posicion--;
        }
        elementos[posicion] = elemento;
        tamano++;
    }

    /**
     * Extrae el elemento de mayor prioridad.
     *
     * @return Elemento de mayor prioridad.
     */
    @SuppressWarnings("unchecked")
    public T extraerMaximo() {
        if (estaVacia()) {
            throw new RuntimeException("La cola de prioridad esta vacia.");
        }
        T elemento = (T) elementos[0];
        for (int i = 0; i < tamano - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        elementos[--tamano] = null;
        return elemento;
    }

    /**
     * Consulta el elemento de mayor prioridad sin retirarlo.
     *
     * @return Elemento de mayor prioridad.
     */
    @SuppressWarnings("unchecked")
    public T verMaximo() {
        if (estaVacia()) {
            throw new RuntimeException("La cola de prioridad esta vacia.");
        }
        return (T) elementos[0];
    }

    /**
     * Obtiene un elemento por posicion.
     *
     * @param indice Posicion en la cola ordenada.
     * @return Elemento consultado.
     */
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
        return (T) elementos[indice];
    }

    /**
     * Elimina la primera aparicion exacta del elemento indicado.
     *
     * @param elemento Elemento a retirar.
     * @return {@code true} si fue eliminado.
     */
    public boolean eliminarElemento(T elemento) {
        for (int i = 0; i < tamano; i++) {
            if (elementos[i] == elemento) {
                for (int j = i; j < tamano - 1; j++) {
                    elementos[j] = elementos[j + 1];
                }
                elementos[--tamano] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * @return {@code true} si no contiene elementos.
     */
    public boolean estaVacia() {
        return tamano == 0;
    }

    /**
     * @return Numero de elementos almacenados.
     */
    public int tamano() {
        return tamano;
    }

    private void redimensionar() {
        Object[] nuevo = new Object[elementos.length * 2];
        for (int i = 0; i < tamano; i++) {
            nuevo[i] = elementos[i];
        }
        elementos = nuevo;
    }
}
