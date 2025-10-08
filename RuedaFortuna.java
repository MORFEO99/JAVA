import java.util.Random;
import java.util.Scanner;

public class RuedaFortuna {

    // ===== Clase interna Nodo =====
    static class Nodo {
        int ficha;
        Nodo siguiente;

        public Nodo(int ficha) {
            this.ficha = ficha;
            this.siguiente = null;
        }
    }

    // ===== Atributos principales =====
    private Nodo cabeza;

    // ===== Constructor =====
    public RuedaFortuna() {
        this.cabeza = null;
    }

    // ===== Insertar jugador al final =====
    public void insertarAlFinal(int ficha) {
        Nodo nuevo = new Nodo(ficha);
        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.siguiente = cabeza; // circularidad
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != cabeza) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }
    }

    // ===== Mostrar lista circular =====
    public void mostrarLista() {
        if (cabeza == null) {
            System.out.println("Lista vacía");
            return;
        }
        Nodo actual = cabeza;
        System.out.print("Lista Circular: ");
        do {
            System.out.print(actual.ficha + " -> ");
            actual = actual.siguiente;
        } while (actual != cabeza);
        System.out.println("(vuelve a " + cabeza.ficha + ")");
    }

    // ===== Contar jugadores actuales =====
    private int contarJugadores() {
        if (cabeza == null) return 0;
        int count = 0;
        Nodo actual = cabeza;
        do {
            count++;
            actual = actual.siguiente;
        } while (actual != cabeza);
        return count;
    }

    // ===== Eliminar jugador por ficha =====
    private void eliminar(int ficha) {
        if (cabeza == null) return;

        Nodo actual = cabeza;
        Nodo anterior = null;

        do {
            if (actual.ficha == ficha) {
                if (anterior == null) { // eliminar cabeza
                    Nodo temp = cabeza;
                    while (temp.siguiente != cabeza) {
                        temp = temp.siguiente;
                    }
                    if (temp == cabeza) { // único jugador
                        cabeza = null;
                    } else {
                        temp.siguiente = cabeza.siguiente;
                        cabeza = cabeza.siguiente;
                    }
                } else {
                    anterior.siguiente = actual.siguiente;
                    if (actual == cabeza) cabeza = anterior.siguiente;
                }
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        } while (actual != cabeza);
    }

    // ===== Mostrar jugadores restantes =====
    private void mostrarRestantes() {
        if (cabeza == null) {
            System.out.println("No quedan jugadores.");
            return;
        }
        Nodo actual = cabeza;
        System.out.print("Fichas restantes: ");
        do {
            System.out.print(actual.ficha + " ");
            actual = actual.siguiente;
        } while (actual != cabeza);
        System.out.println();
    }

    // ===== Simulación del juego =====
    public void jugar() {
        if (cabeza == null) {
            System.out.println("⚠️ No hay jugadores para iniciar el juego.");
            return;
        }

        Random rand = new Random();
        Nodo actual = cabeza;
        int ronda = 1;

        System.out.println("\n=== INICIANDO JUEGO ===");

        while (contarJugadores() > 1) {
            int pasos = rand.nextInt(contarJugadores()) + 1;
            System.out.println("\n--- Ronda " + ronda + " ---");
            System.out.println("🎡 La rueda gira " + pasos + " posiciones...");

            for (int i = 1; i < pasos; i++) {
                actual = actual.siguiente;
            }

            Nodo eliminado = actual;
            System.out.println("💥 Ficha eliminada: " + eliminado.ficha);

            Nodo siguiente = eliminado.siguiente;
            eliminar(eliminado.ficha);
            mostrarRestantes();

            actual = cabeza != null ? siguiente : null;
            ronda++;
        }

        if (cabeza != null)
            System.out.println("\n🎉 ¡JUEGO TERMINADO! GANADOR: Ficha " + cabeza.ficha);
    }

    // ===== Método principal =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RuedaFortuna juego = new RuedaFortuna();

        System.out.println("=== RUEDA DE LA FORTUNA ===");
        System.out.print("Ingrese la cantidad de jugadores: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.print("🎮 Ingrese número de ficha del jugador " + i + ": ");
            int ficha = sc.nextInt();
            juego.insertarAlFinal(ficha);
        }

        System.out.println("\n--- Estado inicial ---");
        juego.mostrarLista();

        juego.jugar();

        sc.close();
    }
}