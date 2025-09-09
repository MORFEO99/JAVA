import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Nodo {

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
    }

    // ===================== PILA BASADA EN ARRAY =====================
    static class PilaArray {
        private String[] elementos;
        private int capacidad;
        private int cima;

        public PilaArray(int capacidad) {
            this.capacidad = capacidad;
            this.elementos = new String[capacidad];
            this.cima = -1;
        }

        public void push(String elemento) {
            if (isFull()) {
                System.out.println("Error: Pila llena");
                return;
            }
            cima++;
            elementos[cima] = elemento;
        }

        public String pop() {
            if (isEmpty()) {
                System.out.println("Error: Pila vacía");
                return null;
            }
            String elemento = elementos[cima];
            cima--;
            return elemento;
        }

        public String peek() {
            return isEmpty() ? null : elementos[cima];
        }

        public boolean isEmpty() {
            return cima == -1;
        }

        public boolean isFull() {
            return cima == capacidad - 1;
        }

        public int size() {
            return cima + 1;
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        // ==== DEMO PILA ====
        System.out.println("=== DEMO PILA ===");
        PilaArray pila = new PilaArray(5);
        pila.push("A");
        pila.push("B");
        pila.push("C");

        System.out.println("Cima: " + pila.peek()); // C
        System.out.println("Pop: " + pila.pop());   // C
        System.out.println("Cima: " + pila.peek()); // B

        pila.push("D");
        pila.push("E");
        pila.push("F"); // Error: Pila llena
        System.out.println("Tamaño de la pila: " + pila.size());

        // ==== DEMO ÁRBOL BINARIO ====
        System.out.println("\n=== DEMO ÁRBOL BINARIO ===");
        ArbolBinarioManual arbol = new ArbolBinarioManual();
        arbol.crearArbolManual(); // ingreso manual
        arbol.mostrarEstructura();
    }
}
