package estructura;

/** Se lanza cuando se intenta remover, mostrar o buscar con primero == null. */
public class ListaVaciaException extends RuntimeException {

    public ListaVaciaException(String mensaje) {
        super(mensaje);
    }
}