# Listas Simple Enlace TDA con Nodos

Actividad 2.4 · Estructura de Datos · Universidad de Guadalajara, Centro Universitario de Tonalá

Aplicación de escritorio en Java Swing que administra productos mediante una lista simplemente enlazada programada desde cero, con las referencias `primero` y `siguiente`, sin colecciones de Java ni arreglos.

## Requisitos

- JDK 17 o superior
- Apache NetBeans (proyecto Java with Ant)

## Cómo ejecutar

1. Descargar el repositorio: **Code → Download ZIP** y descomprimir.
2. En NetBeans: **File → Open Project** y seleccionar la carpeta.
3. Ejecutar con **F6**. Clase principal: `app.Main`.

## Estructura

| Paquete | Contenido |
| --- | --- |
| `modelo` | `Producto` (record inmutable) |
| `estructura` | `Nodo`, `ListaUnidireccional`, `ResultadoBusqueda`, `ListaVaciaException` |
| `vista` | `VentanaPrincipal` (JFrame), `ModeloTablaProductos` |
| `app` | `Main` |

## Operaciones

- Insertar, remover y mostrar: al principio, al final y por posición (0 = primero)
- Buscar por código
- Vaciar la lista

## Equipo

- Zuñiga De Leon Bryan
- Gonzalez Nuñez Isai
