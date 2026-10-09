package estructura;

import modelo.Producto;

/** Resultado de buscar por valor: el record encontrado y su posición en la lista. */
public record ResultadoBusqueda(Producto producto, int posicion) {
}