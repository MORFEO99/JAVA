import java.util.Scanner;

public class SistemaAtencion {
    // ============================================================
    // ColaSimple implementada dentro de la misma clase
    // ============================================================
    private class ColaSimple {
        private int[] elementos;
        private int frente;
        private int fin;
        private int capacidad;
        private int tamanio;

        public ColaSimple(int capacidad) {
            this.capacidad = capacidad;
            this.elementos = new int[capacidad];
            this.frente = 0;
            this.fin = -1;
            this.tamanio = 0;
        }

        public boolean enqueue(int elemento) {
            if (isFull()) {
                System.out.println("⚠ Cola llena. No se puede agregar el ticket #" + elemento);
                return false;
            }
            fin = (fin + 1) % capacidad;
            elementos[fin] = elemento;
            tamanio++;
            return true;
        }

        public Integer dequeue() {
            if (isEmpty()) {
                System.out.println("⚠ La cola está vacía, no se puede atender.");
                return null;
            }
            int elemento = elementos[frente];
            frente = (frente + 1) % capacidad;
            tamanio--;
            return elemento;
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
    }

    // ============================================================
    // Variables del sistema
    // ============================================================
    private ColaSimple colaClientes;
    private int siguienteTicket;
    private Integer clienteActual;

    public SistemaAtencion(int capacidadMaxima) {
        this.colaClientes = new ColaSimple(capacidadMaxima);
        this.siguienteTicket = 1;
        this.clienteActual = null;
    }

    // Cliente toma un ticket
    public void tomarTicket() {
        if (colaClientes.enqueue(siguienteTicket)) {
            System.out.println("🎟 Ticket #" + siguienteTicket +
                    " asignado. Actualmente hay " + colaClientes.getTamanio() + " clientes en espera.");
            siguienteTicket++;
        }
    }

    // Atender al siguiente cliente
    public void atenderCliente() {
        Integer cliente = colaClientes.dequeue();
        if (cliente != null) {
            clienteActual = cliente;
            System.out.println("✅ Atendiendo al cliente con ticket #" + clienteActual);
        }
    }

    // Mostrar estado
    public void mostrarEstado() {
        System.out.println("\n===== ESTADO DEL SISTEMA =====");
        if (clienteActual != null) {
            System.out.println("👤 Cliente actual: Ticket #" + clienteActual);
        } else {
            System.out.println("Ningún cliente está siendo atendido.");
        }
        System.out.println("🕒 Clientes esperando: " + colaClientes.getTamanio());
        System.out.println("➡ Próximo ticket a asignar: #" + siguienteTicket);
        System.out.println("==============================\n");
    }

    // ========================================================
    // MAIN con menú
    // ========================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaAtencion sistema = new SistemaAtencion(5); // capacidad máxima de la cola
        int opcion;

        do {
            System.out.println("====== MENÚ SISTEMA DE ATENCIÓN ======");
            System.out.println("1. Tomar ticket");
            System.out.println("2. Atender cliente");
            System.out.println("3. Mostrar estado del sistema");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            while (!sc.hasNextInt()) {
                System.out.print("⚠ Ingrese un número válido: ");
                sc.next();
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    sistema.tomarTicket();
                    break;
                case 2:
                    sistema.atenderCliente();
                    break;
                case 3:
                    sistema.mostrarEstado();
                    break;
                case 0:
                    System.out.println("👋 Saliendo del sistema...");
                    break;
                default:
                    System.out.println("⚠ Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);

        sc.close();
    }
}