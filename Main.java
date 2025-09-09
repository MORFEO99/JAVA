import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    // ===================== CLASE NODO =====================
    static class Nodo {
        int valor;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(int valor) {
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    // ===================== ÁRBOL BINARIO MANUAL =====================
    static class ArbolBinarioManual {
        private Nodo raiz;
        private Scanner scanner;

        public ArbolBinarioManual() {
            scanner = new Scanner(System.in);
        }

        // Crear árbol manualmente por niveles
        public void crearArbolManual() {
            System.out.println("=== CREACIÓN MANUAL DE ÁRBOL BINARIO ===");
            System.out.println("Ingrese los valores comenzando desde la raíz:");
            System.out.println("Use -1 para indicar que no hay hijo\n");

            if (raiz == null) {
                System.out.print("Ingrese el valor de la raíz: ");
                int valorRaiz = obtenerEntero();
                raiz = new Nodo(valorRaiz);
            }

            Queue<Nodo> cola = new LinkedList<>();
            cola.add(raiz);

            while (!cola.isEmpty()) {
                Nodo actual = cola.poll();
                System.out.println("\nNodo actual: " + actual.valor);

                // Hijo izquierdo
                System.out.print("¿Tiene hijo izquierdo? (s/n): ");
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    System.out.print("Valor del hijo izquierdo de " + actual.valor + ": ");
                    int valorIzq = obtenerEntero();
                    if (valorIzq != -1) {
                        actual.izquierdo = new Nodo(valorIzq);
                        cola.add(actual.izquierdo);
                    }
                }

                // Hijo derecho
                System.out.print("¿Tiene hijo derecho? (s/n): ");
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    System.out.print("Valor del hijo derecho de " + actual.valor + ": ");
                    int valorDer = obtenerEntero();
                    if (valorDer != -1) {
                        actual.derecho = new Nodo(valorDer);
                        cola.add(actual.derecho);
                    }
                }
                System.out.println("----------------------------");
            }
        }

        private int obtenerEntero() {
            while (true) {
                try {
                    return Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("Entrada inválida. Ingrese un número entero: ");
                }
            }
        }

        // Mostrar estructura del árbol rotado
        public void mostrarEstructura() {
            System.out.println("\n=== ESTRUCTURA DEL ÁRBOL ===");
            if (raiz == null) {
                System.out.println("Árbol vacío");
                return;
            }
            mostrarEstructuraRec(raiz, 0);
        }

        private void mostrarEstructuraRec(Nodo nodo, int nivel) {
            if (nodo == null) return;
            mostrarEstructuraRec(nodo.derecho, nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("   ");
            }
            System.out.println(nodo.valor);
            mostrarEstructuraRec(nodo.izquierdo, nivel + 1);
        }

        // ===================== RECORRIDOS (RETROCESOS) =====================
        public void mostrarRecorridos() {
            System.out.println("\n=== RECORRIDOS DEL ÁRBOL ===");
            System.out.print("Preorden: ");
            preorden(raiz);
            System.out.println();

            System.out.print("Inorden: ");
            inorden(raiz);
            System.out.println();

            System.out.print("Postorden: ");
            postorden(raiz);
            System.out.println();

            System.out.print("Por niveles: ");
            niveles();
            System.out.println();
        }

        // Preorden (Raíz - Izq - Der)
        private void preorden(Nodo nodo) {
            if (nodo != null) {
                System.out.print(nodo.valor + " ");
                preorden(nodo.izquierdo);
                preorden(nodo.derecho);
            }
        }

        // Inorden (Izq - Raíz - Der)
        private void inorden(Nodo nodo) {
            if (nodo != null) {
                inorden(nodo.izquierdo);
                System.out.print(nodo.valor + " ");
                inorden(nodo.derecho);
            }
        }

        // Postorden (Izq - Der - Raíz)
        private void postorden(Nodo nodo) {
            if (nodo != null) {
                postorden(nodo.izquierdo);
                postorden(nodo.derecho);
                System.out.print(nodo.valor + " ");
            }
        }

        // Por niveles (BFS)
        private void niveles() {
            if (raiz == null) return;
            Queue<Nodo> cola = new LinkedList<>();
            cola.add(raiz);

            while (!cola.isEmpty()) {
                Nodo actual = cola.poll();
                System.out.print(actual.valor + " ");
                if (actual.izquierdo != null) cola.add(actual.izquierdo);
                if (actual.derecho != null) cola.add(actual.derecho);
            }
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        ArbolBinarioManual arbol = new ArbolBinarioManual();
        arbol.crearArbolManual(); // ingreso manual
        arbol.mostrarEstructura();
        arbol.mostrarRecorridos(); // muestra retrocesos
    }
}
