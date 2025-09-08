public class Main {
    
    // ==============================
    // Clase Nodo
    static class Nodo {
        private String producto;
        private int cantidad;
        private Nodo siguiente;

        public Nodo(String producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.siguiente = null;
        }

        public String getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }

    // ==============================
    // Clase ListaEnlazada
    static class ListaEnlazada {
        private Nodo cabeza;

        public ListaEnlazada() {
            this.cabeza = null;
        }

        // 1.2 Agregar producto
        public void agregarProducto(String producto, int cantidad) {
            if (cabeza == null) {
                cabeza = new Nodo(producto, cantidad);
                return;
            }
            Nodo actual = cabeza;
            Nodo anterior = null;
            while (actual != null) {
                if (actual.getProducto().equalsIgnoreCase(producto)) {
                    actual.setCantidad(actual.getCantidad() + cantidad);
                    return;
                }
                anterior = actual;
                actual = actual.getSiguiente();
            }
            anterior.setSiguiente(new Nodo(producto, cantidad));
        }

        // 1.2 Eliminar producto
        public boolean eliminarProducto(String producto) {
            if (cabeza == null) return false;

            if (cabeza.getProducto().equalsIgnoreCase(producto)) {
                cabeza = cabeza.getSiguiente();
                return true;
            }

            Nodo actual = cabeza;
            while (actual.getSiguiente() != null) {
                if (actual.getSiguiente().getProducto().equalsIgnoreCase(producto)) {
                    actual.setSiguiente(actual.getSiguiente().getSiguiente());
                    return true;
                }
                actual = actual.getSiguiente();
            }
            return false;
        }

        // 1.2 Buscar cantidad
        public int buscarCantidad(String producto) {
            Nodo actual = cabeza;
            while (actual != null) {
                if (actual.getProducto().equalsIgnoreCase(producto)) {
                    return actual.getCantidad();
                }
                actual = actual.getSiguiente();
            }
            return -1;
        }

        // 1.2 Mostrar inventario
        public void mostrarInventario() {
            if (cabeza == null) {
                System.out.println("El inventario está vacío.");
                return;
            }
            Nodo actual = cabeza;
            while (actual != null) {
                System.out.println("Producto: " + actual.getProducto() +
                                   ", Cantidad: " + actual.getCantidad());
                actual = actual.getSiguiente();
            }
        }

        // 2.2 Vender producto
        public boolean venderProducto(String producto, int cantidadVendida) {
            Nodo actual = cabeza;
            while (actual != null) {
                if (actual.getProducto().equalsIgnoreCase(producto)) {
                    if (actual.getCantidad() < cantidadVendida) {
                        return false; // stock insuficiente
                    }
                    actual.setCantidad(actual.getCantidad() - cantidadVendida);
                    if (actual.getCantidad() == 0) {
                        eliminarProducto(producto);
                    }
                    return true;
                }
                actual = actual.getSiguiente();
            }
            return false; // no existe
        }

        // 2.2 Abastecer producto
        public void abastecerProducto(String producto, int cantidadAbastecida) {
            agregarProducto(producto, cantidadAbastecida);
        }
    }

    // ==============================
    // MAIN con pruebas organizadas por puntos
    public static void main(String[] args) {
        ListaEnlazada inventario = new ListaEnlazada();

        // ==============================
        // Parte 2.1 - Cargar inventario inicial
        System.out.println("=== Parte 2.1: Inventario inicial ===");
        inventario.agregarProducto("Lápices", 50);
        inventario.agregarProducto("Cuadernos", 30);
        inventario.agregarProducto("Borradores", 20);
        inventario.agregarProducto("Reglas", 15);
        inventario.agregarProducto("Marcadores", 10);
        inventario.mostrarInventario();
        System.out.println("-------------------------------------\n");

        // ==============================
        // Parte 3.1 Escenario 1: Agregar productos duplicados
        System.out.println("=== Escenario 1: Agregar productos duplicados ===");
        inventario.agregarProducto("Lápices", 25); // debe sumar cantidades
        inventario.mostrarInventario();
        System.out.println("-------------------------------------\n");

        // ==============================
        // Parte 3.1 Escenario 2: Vender productos
        System.out.println("=== Escenario 2: Vender productos ===");
        boolean venta1 = inventario.venderProducto("Cuadernos", 10); // stock suficiente
        boolean venta2 = inventario.venderProducto("Reglas", 20);    // stock insuficiente
        System.out.println("Venta 1 (10 Cuadernos): " + (venta1 ? "Éxito" : "Fracaso"));
        System.out.println("Venta 2 (20 Reglas): " + (venta2 ? "Éxito" : "Fracaso"));
        inventario.mostrarInventario();
        System.out.println("-------------------------------------\n");

        // ==============================
        // Parte 3.1 Escenario 3: Eliminar productos
        System.out.println("=== Escenario 3: Eliminar productos ===");
        boolean elim1 = inventario.eliminarProducto("Marcadores"); // existe
        boolean elim2 = inventario.eliminarProducto("Tijeras");    // no existe
        System.out.println("Eliminar 'Marcadores': " + (elim1 ? "Eliminado" : "No encontrado"));
        System.out.println("Eliminar 'Tijeras': " + (elim2 ? "Eliminado" : "No encontrado"));
        inventario.mostrarInventario();
        System.out.println("-------------------------------------\n");

        // ==============================
        // Parte 3.1 Escenario 4: Abastecer productos
        System.out.println("=== Escenario 4: Abastecer productos ===");
        inventario.abastecerProducto("Borradores", 15); // aumenta stock existente
        inventario.abastecerProducto("Tijeras", 40);    // nuevo producto
        inventario.mostrarInventario();
        System.out.println("-------------------------------------\n");
    }
}
