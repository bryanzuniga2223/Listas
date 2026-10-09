package estructura;

import modelo.Producto;

/**
 * TDA Lista Unidireccional (simplemente enlazada).
 * No usa colecciones de Java ni arreglos: solo nodos enlazados
 * mediante las referencias primero y siguiente.
 * Las posiciones empiezan en 0 (0 = el nodo apuntado por primero).
 */
public class ListaUnidireccional {

    private Nodo primero;
    private int tamanio;

    public ListaUnidireccional() {
        primero = null;
        tamanio = 0;
    }

    // ===================== ESTADO =====================

    public boolean estaVacia() {
        return primero == null;
    }

    public int tamanio() {
        return tamanio;
    }

    public void vaciar() {
        primero = null; // los nodos quedan sin referencias y el recolector de basura de la JVM los libera
        tamanio = 0;
    }

    // ===================== INSERTAR =====================

    public void insertarAlPrincipio(Producto producto) {
        Nodo nuevo = new Nodo(producto);
        nuevo.setSiguiente(primero);
        primero = nuevo;
        tamanio++;
    }

    public void insertarAlFinal(Producto producto) {
        Nodo nuevo = new Nodo(producto);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    /** Posiciones válidas para insertar: 0 a tamanio (tamanio = insertar al final). */
    public void insertarEnPosicion(Producto producto, int posicion) {
        if (posicion < 0 || posicion > tamanio) {
            throw new IndexOutOfBoundsException(
                    "Posición " + posicion + " inválida para insertar. Usa un valor de 0 a " + tamanio + ".");
        }
        if (posicion == 0) {
            insertarAlPrincipio(producto);
            return;
        }
        Nodo anterior = nodoEn(posicion - 1);
        Nodo nuevo = new Nodo(producto);
        nuevo.setSiguiente(anterior.getSiguiente());
        anterior.setSiguiente(nuevo);
        tamanio++;
    }

    // ===================== REMOVER =====================

    public Producto removerAlPrincipio() {
        verificarNoVacia("remover");
        Nodo removido = primero;
        primero = removido.getSiguiente();
        removido.setSiguiente(null);
        tamanio--;
        return removido.getDato();
    }

    public Producto removerAlFinal() {
        verificarNoVacia("remover");
        if (primero.getSiguiente() == null) {
            return removerAlPrincipio(); // solo hay un nodo
        }
        Nodo penultimo = primero;
        while (penultimo.getSiguiente().getSiguiente() != null) {
            penultimo = penultimo.getSiguiente();
        }
        Nodo removido = penultimo.getSiguiente();
        penultimo.setSiguiente(null);
        tamanio--;
        return removido.getDato();
    }

    public Producto removerEnPosicion(int posicion) {
        verificarNoVacia("remover");
        verificarIndice(posicion);
        if (posicion == 0) {
            return removerAlPrincipio();
        }
        Nodo anterior = nodoEn(posicion - 1);
        Nodo removido = anterior.getSiguiente();
        anterior.setSiguiente(removido.getSiguiente());
        removido.setSiguiente(null);
        tamanio--;
        return removido.getDato();
    }

    // ===================== MOSTRAR =====================

    public Producto mostrarPrimero() {
        verificarNoVacia("mostrar");
        return primero.getDato();
    }

    public Producto mostrarUltimo() {
        verificarNoVacia("mostrar");
        Nodo actual = primero;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public Producto mostrarEnPosicion(int posicion) {
        verificarNoVacia("mostrar");
        verificarIndice(posicion);
        return nodoEn(posicion).getDato();
    }

    // ===================== BUSCAR =====================

    /** Recorre desde primero comparando el código. Devuelve null si no lo encuentra. */
    public ResultadoBusqueda buscarPorCodigo(String codigo) {
        verificarNoVacia("buscar");
        String clave = codigo.trim();
        Nodo actual = primero;
        int posicion = 0;
        while (actual != null) {
            if (actual.getDato().codigo().equalsIgnoreCase(clave)) {
                return new ResultadoBusqueda(actual.getDato(), posicion);
            }
            actual = actual.getSiguiente();
            posicion++;
        }
        return null;
    }

    // ===================== REPRESENTACIÓN =====================

    /** Texto con la cadena de enlaces, por ejemplo: primero → [A1] → [B2] → null */
    public String representacion() {
        StringBuilder sb = new StringBuilder("primero → ");
        Nodo actual = primero;
        while (actual != null) {
            sb.append('[').append(actual.getDato().codigo()).append("] → ");
            actual = actual.getSiguiente();
        }
        return sb.append("null").toString();
    }

    // ===================== AUXILIARES PRIVADOS =====================

    /** Avanza 'posicion' saltos desde primero. Supone que la posición ya fue validada. */
    private Nodo nodoEn(int posicion) {
        Nodo actual = primero;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }

    private void verificarNoVacia(String operacion) {
        if (estaVacia()) {
            throw new ListaVaciaException("No se puede " + operacion + ": la lista está vacía.");
        }
    }

    private void verificarIndice(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Posición " + posicion + " fuera de rango. Usa un valor de 0 a " + (tamanio - 1) + ".");
        }
    }
}