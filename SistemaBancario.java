import java.util.Scanner;

// Clase Nodo
class Nodo {
    int ficha;
    Nodo siguiente;

    public Nodo(int ficha) {
        this.ficha = ficha;
        this.siguiente = null;
    }
}

// Clase Lista Simple
class ListaSimple {
    Nodo cabeza;

    public ListaSimple() {
        this.cabeza = null;
    }

    // Insertar al final
    public void insertarAlFinal(int ficha) {
        Nodo nuevo = new Nodo(ficha);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    // Insertar acompañante después de una ficha específica
    public void insertarDespuesDe(int fichaBase, int fichaNueva) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.ficha == fichaBase) {
                Nodo nuevo = new Nodo(fichaNueva);
                nuevo.siguiente = actual.siguiente;
                actual.siguiente = nuevo;
                return;
            }
            actual = actual.siguiente;
        }
    }

    // Mostrar la lista completa
    public void mostrarLista() {
        Nodo actual = cabeza;
        System.out.print("🔄 Orden de atención: ");
        while (actual != null) {
            System.out.print(actual.ficha + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }

    // Atender cliente (eliminar cabeza)
    public void atenderCliente() {
        if (cabeza != null) {
            System.out.println("✅ Atendiendo cliente con ficha: " + cabeza.ficha);
            cabeza = cabeza.siguiente;
        } else {
            System.out.println("⚠️ No hay más clientes en la cola.");
        }
    }
}

public class SistemaBancario {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ListaSimple lista = new ListaSimple();

        System.out.println("=== SISTEMA DE TURNOS BANCARIOS ===");

        System.out.print("Ingrese la cantidad de clientes: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("\n👥 Ingrese número de ficha del cliente: ");
            int ficha = sc.nextInt();
            System.out.print("Edad del cliente: ");
            int edad = sc.nextInt();

            lista.insertarAlFinal(ficha);

            if (edad > 65) {
                System.out.print("¿Tiene acompañante? (s/n): ");
                char opc = sc.next().toLowerCase().charAt(0);
                if (opc == 's') {
                    int acomp = ficha * 10 + 1;
                    System.out.println("🎯 Insertando acompañante con ficha " + acomp + " después del cliente con ficha " + ficha);
                    lista.insertarDespuesDe(ficha, acomp);
                }
            }
        }

        System.out.println("\n--- Estado final de la cola ---");
        lista.mostrarLista();

        System.out.println("\n--- Proceso de atención ---");
        char seguir;
        do {
            lista.atenderCliente();
            lista.mostrarLista();
            System.out.print("¿Atender siguiente cliente? (s/n): ");
            seguir = sc.next().toLowerCase().charAt(0);
        } while (seguir == 's');

        System.out.println("🏁 Fin del proceso de atención.");
        sc.close();
    }
}