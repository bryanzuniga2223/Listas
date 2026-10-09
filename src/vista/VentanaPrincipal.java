package vista;

import estructura.ListaUnidireccional;
import estructura.ListaVaciaException;
import estructura.ResultadoBusqueda;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import modelo.Producto;

/** Capa de interfaz gráfica. Solo llama a métodos públicos del TDA. */
public class VentanaPrincipal extends JFrame {

    private final ListaUnidireccional lista = new ListaUnidireccional();
    private final ModeloTablaProductos modeloTabla = new ModeloTablaProductos(lista);

    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtPrecio = new JTextField();
    private final JTextField txtExistencia = new JTextField();
    private final JTextField txtPosicion = new JTextField();
    private final JTextField txtBuscar = new JTextField();

    private final JTable tabla = new JTable(modeloTabla);
    private final JTextArea txtCadena = new JTextArea(2, 40);
    private final JLabel lblEstado = new JLabel(" ");

    public VentanaPrincipal() {
        super("Lista Unidireccional de Productos (TDA con nodos)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);

        refrescarVista();
        setSize(950, 650);
        setLocationRelativeTo(null);
    }

    // ===================== CONSTRUCCIÓN DE LA GUI =====================

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Existencia:"));
        panel.add(txtExistencia);
        panel.add(new JLabel("Posición (0 = primero):"));
        panel.add(txtPosicion);
        panel.add(new JLabel("Código a buscar:"));
        panel.add(txtBuscar);
        return panel;
    }

    private JPanel crearPanelCentral() {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);

        txtCadena.setEditable(false);
        txtCadena.setLineWrap(true);
        txtCadena.setWrapStyleWord(true);
        txtCadena.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        JPanel panelCadena = new JPanel(new BorderLayout());
        panelCadena.setBorder(BorderFactory.createTitledBorder("Enlaces en memoria (primero → siguiente)"));
        panelCadena.add(new JScrollPane(txtCadena), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(panelCadena, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel insertar = grupo("Insertar",
                boton("Al principio", e -> ejecutar(this::insertarAlPrincipio)),
                boton("Al final", e -> ejecutar(this::insertarAlFinal)),
                boton("En posición", e -> ejecutar(this::insertarEnPosicion)));

        JPanel remover = grupo("Remover",
                boton("Al principio", e -> ejecutar(this::removerAlPrincipio)),
                boton("Al final", e -> ejecutar(this::removerAlFinal)),
                boton("En posición", e -> ejecutar(this::removerEnPosicion)));

        JPanel mostrar = grupo("Mostrar",
                boton("Al principio", e -> ejecutar(this::mostrarPrimero)),
                boton("Al final", e -> ejecutar(this::mostrarUltimo)),
                boton("En posición", e -> ejecutar(this::mostrarEnPosicion)));

        JPanel otros = grupo("Otros",
                boton("Buscar por código", e -> ejecutar(this::buscar)),
                boton("Vaciar lista", e -> ejecutar(this::vaciar)),
                boton("Limpiar campos", e -> limpiarCampos()));

        JPanel filaBotones = new JPanel(new GridLayout(1, 4, 8, 8));
        filaBotones.add(insertar);
        filaBotones.add(remover);
        filaBotones.add(mostrar);
        filaBotones.add(otros);

        lblEstado.setBorder(BorderFactory.createEmptyBorder(0, 8, 5, 8));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(filaBotones, BorderLayout.CENTER);
        panel.add(lblEstado, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel grupo(String titulo, JButton b1, JButton b2, JButton b3) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 4, 4));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        return panel;
    }

    private JButton boton(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(accion);
        return boton;
    }

    // ===================== ACCIONES =====================

    private void insertarAlPrincipio() {
        Producto p = leerProducto();
        lista.insertarAlPrincipio(p);
        limpiarProducto();
        estado("Insertado al principio: " + p.codigo());
    }

    private void insertarAlFinal() {
        Producto p = leerProducto();
        lista.insertarAlFinal(p);
        limpiarProducto();
        estado("Insertado al final: " + p.codigo());
    }

    private void insertarEnPosicion() {
        Producto p = leerProducto();
        int posicion = leerPosicion();
        lista.insertarEnPosicion(p, posicion);
        limpiarProducto();
        estado("Insertado en la posición " + posicion + ": " + p.codigo());
    }

    private void removerAlPrincipio() {
        Producto p = lista.removerAlPrincipio();
        estado("Removido del principio: " + p.codigo());
    }

    private void removerAlFinal() {
        Producto p = lista.removerAlFinal();
        estado("Removido del final: " + p.codigo());
    }

    private void removerEnPosicion() {
        int posicion = leerPosicion();
        Producto p = lista.removerEnPosicion(posicion);
        estado("Removido de la posición " + posicion + ": " + p.codigo());
    }

    private void mostrarPrimero() {
        mostrarProducto("Producto al principio", lista.mostrarPrimero(), 0);
    }

    private void mostrarUltimo() {
        mostrarProducto("Producto al final", lista.mostrarUltimo(), lista.tamanio() - 1);
    }

    private void mostrarEnPosicion() {
        int posicion = leerPosicion();
        mostrarProducto("Producto en la posición " + posicion, lista.mostrarEnPosicion(posicion), posicion);
    }

    private void buscar() {
        String codigo = txtBuscar.getText().trim();
        if (codigo.isEmpty()) {
            throw new IllegalArgumentException("Escribe el código a buscar.");
        }
        ResultadoBusqueda r = lista.buscarPorCodigo(codigo);
        if (r == null) {
            tabla.clearSelection();
            JOptionPane.showMessageDialog(this,
                    "No existe ningún producto con el código \"" + codigo.toUpperCase() + "\".",
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            estado("Búsqueda sin resultados: " + codigo.toUpperCase());
            return;
        }
        mostrarProducto("Encontrado en la posición " + r.posicion(), r.producto(), r.posicion());
    }

    private void vaciar() {
        if (lista.estaVacia()) {
            JOptionPane.showMessageDialog(this, "La lista ya está vacía.",
                    "Vaciar lista", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres eliminar los " + lista.tamanio() + " productos?",
                "Vaciar lista", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            lista.vaciar();
            estado("Lista vaciada: primero = null");
        }
    }

    // ===================== LECTURA Y VALIDACIÓN DE CAMPOS =====================

    private Producto leerProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String existenciaTexto = txtExistencia.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty() || existenciaTexto.isEmpty()) {
            throw new IllegalArgumentException("Llena todos los campos: código, nombre, precio y existencia.");
        }

        double precio;
        try {
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe ser un número. Ejemplo: 25.50");
        }

        int existencia;
        try {
            existencia = Integer.parseInt(existenciaTexto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La existencia debe ser un número entero. Ejemplo: 10");
        }

        return new Producto(codigo, nombre, precio, existencia);
    }

    private int leerPosicion() {
        String texto = txtPosicion.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Escribe la posición (0 = primero).");
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La posición debe ser un número entero. Ejemplo: 2");
        }
    }

    // ===================== UTILIDADES DE LA VISTA =====================

    /** Ejecuta una acción y convierte cualquier error en un aviso amigable. */
    private void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (ListaVaciaException e) {
            aviso("Lista vacía", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            aviso("Posición fuera de rango", e.getMessage());
        } catch (IllegalArgumentException e) {
            aviso("Dato inválido", e.getMessage());
        }
        refrescarVista();
    }

    private void mostrarProducto(String titulo, Producto p, int fila) {
        refrescarVista();
        tabla.setRowSelectionInterval(fila, fila);
        tabla.scrollRectToVisible(tabla.getCellRect(fila, 0, true));
        JOptionPane.showMessageDialog(this, p.describir(), titulo, JOptionPane.INFORMATION_MESSAGE);
        estado(titulo + ": " + p.codigo());
    }

    private void refrescarVista() {
        modeloTabla.refrescar();
        txtCadena.setText(lista.representacion());
        setTitle("Lista Unidireccional de Productos — " + lista.tamanio() + " elemento(s)");
    }

    private void aviso(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
        estado(mensaje);
    }

    private void estado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    private void limpiarProducto() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtExistencia.setText("");
        txtCodigo.requestFocus();
    }

    private void limpiarCampos() {
        limpiarProducto();
        txtPosicion.setText("");
        txtBuscar.setText("");
        tabla.clearSelection();
        estado(" ");
    }
}