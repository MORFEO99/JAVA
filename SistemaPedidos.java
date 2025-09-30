import java.util.Scanner;

public class SistemaPedidos {

    // ================================
    // Clase interna Pedido
    // ================================
    private class Pedido {
        private int id;
        private String descripcion;
        private boolean preparado;

        public Pedido(int id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
            this.preparado = false;
        }

        public int getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public boolean isPreparado() {
            return preparado;
        }

        public void setPreparado(boolean preparado) {
            this.preparado = preparado;
        }

        @Override
        public String toString() {
            String estado = preparado ? "Preparado" : "Pendiente";
            return "#" + id + " [" + estado + "] - " + descripcion;
        }
    }

    // ================================
    // Clase interna ColaSimple
    // ================================
    private class ColaSimple {
        private Pedido[] elementos;
        private int frente;
        private int fin;
        private int capacidad;
        private int tamanio;

        public ColaSimple(int capacidad) {
            this.capacidad = capacidad;
            this.elementos = new Pedido[capacidad];
            this.frente = 0;
            this.fin = -1;
            this.tamanio = 0;
        }

        public boolean enqueue(Pedido p) {
            if (isFull()) {
                System.out.println("Cola llena. No se puede agregar: " + p.getId());
                return false;
            }
            fin = (fin + 1) % capacidad;
            elementos[fin] = p;
            tamanio++;
            return true;
        }

        public Pedido dequeue() {
            if (isEmpty()) {
                return null;
            }
            Pedido p = elementos[frente];
            frente = (frente + 1) % capacidad;
            tamanio--;
            return p;
        }

        public Pedido peek() {
            if (isEmpty()) return null;
            return elementos[frente];
        }

        public boolean isEmpty() {
            return tamanio == 0;
        }

        public boolean isFull() {
            return tamanio == capacidad;
        }

        public int getTamanio() {
            return tamanio;
        }

        public void mostrarTodos() {
            if (isEmpty()) {
                System.out.println("No hay pedidos pendientes");
                return;
            }
            System.out.println("PEDIDOS PENDIENTES:");
            for (int i = 0; i < tamanio; i++) {
                int index = (frente + i) % capacidad;
                System.out.println(elementos[index]);
            }
        }
    }

    // ================================
    // Variables del sistema
    // ================================
    private ColaSimple colaPedidos;
    private int siguienteId;

    public SistemaPedidos(int capacidadMaxima) {
        this.colaPedidos = new ColaSimple(capacidadMaxima);
        this.siguienteId = 1;
    }

    // 1. Recibir nuevos pedidos
    public void nuevoPedido(String descripcion) {
        Pedido p = new Pedido(siguienteId, descripcion);
        if (colaPedidos.enqueue(p)) {
            System.out.println("Pedido #" + siguienteId + " agregado: " + descripcion);
            siguienteId++;
        }
    }

    // 2. Preparar siguiente pedido
    public void prepararSiguiente() {
        Pedido p = colaPedidos.peek();
        if (p == null) {
            System.out.println("No hay pedidos para preparar");
            return;
        }
        if (!p.isPreparado()) {
            p.setPreparado(true);
            System.out.println("Pedido #" + p.getId() + " preparado: " + p.getDescripcion());
        } else {
            System.out.println("El pedido #" + p.getId() + " ya está preparado");
        }
    }

    // 3. Entregar pedido
    public void entregarPedido() {
        Pedido p = colaPedidos.peek();
        if (p == null) {
            System.out.println("No hay pedidos para entregar");
            return;
        }
        if (!p.isPreparado()) {
            System.out.println("ADVERTENCIA: El pedido #" + p.getId() + " no está preparado");
            return;
        }
        Pedido entregado = colaPedidos.dequeue();
        System.out.println("Pedido #" + entregado.getId() + " entregado: " + entregado.getDescripcion());
    }

    // 4. Mostrar pedidos pendientes
    public void mostrarPedidosPendientes() {
        colaPedidos.mostrarTodos();
    }

    // ================================
    // MAIN con menú interactivo
    // ================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaPedidos sistema = new SistemaPedidos(5);

        int opcion;
        do {
            System.out.println("\n====== MENÚ SISTEMA DE PEDIDOS ======");
            System.out.println("1. Nuevo pedido");
            System.out.println("2. Preparar siguiente pedido");
            System.out.println("3. Entregar pedido");
            System.out.println("4. Mostrar pedidos pendientes");
            System.out.println("5. Ejecutar pruebas automáticas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            while (!sc.hasNextInt()) {
                System.out.print("Ingrese un número válido: ");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la descripción del pedido: ");
                    String desc = sc.nextLine();
                    sistema.nuevoPedido(desc);
                    break;
                case 2:
                    sistema.prepararSiguiente();
                    break;
                case 3:
                    sistema.entregarPedido();
                    break;
                case 4:
                    sistema.mostrarPedidosPendientes();
                    break;
                case 5:
                    ejecutarPruebas();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);

        sc.close();
    }

    // ================================
    // Pruebas automáticas
    // ================================
    public static void ejecutarPruebas() {
        System.out.println("\n=== Caso de Prueba 1: Flujo Completo ===");
        SistemaPedidos s1 = new SistemaPedidos(5);
        s1.nuevoPedido("Combo 1");
        s1.nuevoPedido("Combo 2");
        s1.prepararSiguiente();
        s1.entregarPedido();
        s1.prepararSiguiente();
        s1.entregarPedido();

        System.out.println("\n=== Caso de Prueba 2: Entregar sin preparar ===");
        SistemaPedidos s2 = new SistemaPedidos(5);
        s2.nuevoPedido("Combo 3");
        s2.entregarPedido();

        System.out.println("\n=== Caso de Prueba 3: Múltiples pedidos ===");
        SistemaPedidos s3 = new SistemaPedidos(5);
        s3.nuevoPedido("A");
        s3.nuevoPedido("B");
        s3.nuevoPedido("C");
        s3.mostrarPedidosPendientes();
    }
}