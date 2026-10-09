package vista;

import estructura.ListaUnidireccional;
import javax.swing.table.AbstractTableModel;
import modelo.Producto;

/**
 * Adaptador entre la lista y el JTable. No copia los datos a ninguna
 * colección: cada celda se lee directamente de la lista enlazada.
 */
public class ModeloTablaProductos extends AbstractTableModel {

    private final ListaUnidireccional lista;

    public ModeloTablaProductos(ListaUnidireccional lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.tamanio();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columna) {
        return switch (columna) {
            case 0 -> "Posición";
            case 1 -> "Código";
            case 2 -> "Nombre";
            case 3 -> "Precio";
            default -> "Existencia";
        };
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        // shortcut: recorre la lista por cada celda (O(n) por celda); suficiente para decenas de registros.
        Producto p = lista.mostrarEnPosicion(fila);
        return switch (columna) {
            case 0 -> fila;
            case 1 -> p.codigo();
            case 2 -> p.nombre();
            case 3 -> String.format("$%.2f", p.precio());
            default -> p.existencia();
        };
    }

    public void refrescar() {
        fireTableDataChanged();
    }
}