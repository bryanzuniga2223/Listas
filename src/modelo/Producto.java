package modelo;

/**
 * Entidad de dominio inmutable. Un record genera automáticamente
 * el constructor, los métodos de lectura (codigo(), nombre(), ...),
 * equals(), hashCode() y toString(). Sus campos son final.
 */
public record Producto(String codigo, String nombre, double precio, int existencia) {

    // Constructor compacto: segunda capa de validación (la GUI valida primero).
    public Producto {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (existencia < 0) {
            throw new IllegalArgumentException("La existencia no puede ser negativa.");
        }
        codigo = codigo.trim().toUpperCase();
        nombre = nombre.trim();
    }

    public String describir() {
        return String.format("Código: %s%nNombre: %s%nPrecio: $%.2f%nExistencia: %d",
                codigo, nombre, precio, existencia);
    }
}