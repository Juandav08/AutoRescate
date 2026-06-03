package autorescate.model.funciones;

/**
 * Pila propia LIFO para kits y movimientos recientes.
 *
 */
public class Pila<T> {

    private static final int CAPACIDAD_INICIAL = 50;
    private Object[] elementos;
    private int tope;

    public Pila() {
        elementos = new Object[CAPACIDAD_INICIAL];
        tope = -1;
    }

    public Pila(int capacidad) {
        elementos = new Object[capacidad];
        tope = -1;
    }

    /**
     * Apila un elemento.
     */
    public void apilar(T elemento) {
        if (tope == elementos.length - 1) {
            redimensionar();
        }
        elementos[++tope] = elemento;
    }

    /**
     * Desapila el elemento del tope.
     */
    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (estaVacia()) {
            throw new RuntimeException("La pila esta vacia.");
        }
        T elemento = (T) elementos[tope];
        elementos[tope--] = null;
        return elemento;
    }

    /**
     * Consulta el elemento del tope.
     */
    
    @SuppressWarnings("unchecked")
    public T verTope() {
        if (estaVacia()) {
            throw new RuntimeException("La pila esta vacia.");
        }
        return (T) elementos[tope];
    }

    /**
     * Obtiene un elemento sin retirarlo, contando desde el tope.
     */
    @SuppressWarnings("unchecked")
    public T obtenerDesdeTope(int indiceDesdeTope) {
        if (indiceDesdeTope < 0 || indiceDesdeTope > tope) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indiceDesdeTope);
        }
        return (T) elementos[tope - indiceDesdeTope];
    }

    /**
	true si no contiene elementos.
     */
    public boolean estaVacia() {
        return tope == -1;
    }

    /**
     * Numero de elementos almacenados.
     */
    public int tamano() {
        return tope + 1;
    }

    private void redimensionar() {
        Object[] nuevo = new Object[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            nuevo[i] = elementos[i];
        }
        elementos = nuevo;
    }
}
