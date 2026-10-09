package estructura;

import modelo.Producto;

/** Nodo de la lista: guarda un record y la referencia al siguiente nodo. */
public class Nodo {

    private final Producto dato;
    private Nodo siguiente;

    public Nodo(Producto dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Producto getDato() {
        return dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}