package autorescate.model.funciones;

/**
 * Cola circular propia para solicitudes ordinarias.
 */
public class Cola<T> {

    private static final int CAPACIDAD_INICIAL = 50;
    private Object[] elementos;
    private int frente;
    private int fondo;
    private int tamano;

 
    public Cola() {
        elementos = new Object[CAPACIDAD_INICIAL];
        frente = 0;
        fondo = 0;
        tamano = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     *
     * Elemento a encolar.
     */
    public void encolar(T elemento) {
        if (tamano == elementos.length) {
            redimensionar();
        }
        elementos[fondo] = elemento;
        fondo = (fondo + 1) % elementos.length;
        tamano++;
    }

    /**
     * Retira el elemento del frente.
     *
     * Elemento retirado.
     */
    @SuppressWarnings("unchecked")
    public T desencolar() {
        if (estaVacia()) {
            throw new RuntimeException("La cola esta vacia.");
        }
        T elemento = (T) elementos[frente];
        elementos[frente] = null;
        frente = (frente + 1) % elementos.length;
        tamano--;
        return elemento;
    }

    /**
     * Consulta el frente sin retirar.
     *
     */
    @SuppressWarnings("unchecked")
    public T verFrente() {
        if (estaVacia()) {
            throw new RuntimeException("La cola esta vacia.");
        }
        return (T) elementos[frente];
    }

    /**
     * Obtiene un elemento por posicion logica sin retirarlo.
     */
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
        return (T) elementos[(frente + indice) % elementos.length];
    }

    /**
     * Elimina la primera aparicion exacta del elemento indicado.
  
     */
    public boolean eliminarElemento(T elemento) {
        boolean eliminado = false;
        int cantidad = tamano;
        for (int i = 0; i < cantidad; i++) {
            T actual = desencolar();
            if (!eliminado && actual == elemento) {
                eliminado = true;
            } else {
                encolar(actual);
            }
        }
        return eliminado;
    }


    public boolean estaVacia() {
        return tamano == 0;
    }

    /**
     * Numero de elementos en la cola.
     */
    public int tamano() {
        return tamano;
    }

    private void redimensionar() {
        Object[] nuevo = new Object[elementos.length * 2];
        for (int i = 0; i < tamano; i++) {
            nuevo[i] = elementos[(frente + i) % elementos.length];
        }
        elementos = nuevo;
        frente = 0;
        fondo = tamano;
    }
}
