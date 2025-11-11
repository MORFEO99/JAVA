// EJERCICIO 2 - Árbol Binario de Búsqueda con búsqueda y eliminación (una sola clase)

public class ArbolBinarioSimple {
    // Clase interna para los nodos del árbol
    private static class Nodo {
        int valor;
        Nodo izq, der;

        Nodo(int valor) {
            this.valor = valor;
            this.izq = this.der = null;
        }
    }

    private Nodo raiz; // raíz del árbol

    // Constructor
    public ArbolBinarioSimple() {
        raiz = null;
    }

    // ------------------ MÉTODOS PRINCIPALES ------------------

    // Insertar nodo en el árbol
    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private Nodo insertarRec(Nodo actual, int valor) {
        if (actual == null) {
            return new Nodo(valor);
        }
        if (valor < actual.valor) {
            actual.izq = insertarRec(actual.izq, valor);
        } else if (valor > actual.valor) {
            actual.der = insertarRec(actual.der, valor);
        }
        return actual;
    }

    // Buscar valor en el árbol
    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(Nodo actual, int valor) {
        if (actual == null) return false;
        if (valor == actual.valor) return true;
        return valor < actual.valor
                ? buscarRec(actual.izq, valor)
                : buscarRec(actual.der, valor);
    }

    // Eliminar nodo del árbol
    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
    }

    private Nodo eliminarRec(Nodo actual, int valor) {
        if (actual == null) return null;

        if (valor == actual.valor) {
            // Caso 1: Sin hijos
            if (actual.izq == null && actual.der == null) return null;

            // Caso 2: Un solo hijo
            if (actual.izq == null) return actual.der;
            if (actual.der == null) return actual.izq;

            // Caso 3: Dos hijos
            int menorValor = encontrarMinimo(actual.der);
            actual.valor = menorValor;
            actual.der = eliminarRec(actual.der, menorValor);
            return actual;
        }

        if (valor < actual.valor) {
            actual.izq = eliminarRec(actual.izq, valor);
        } else {
            actual.der = eliminarRec(actual.der, valor);
        }
        return actual;
    }

    // Encontrar el valor mínimo de un subárbol
    private int encontrarMinimo(Nodo nodo) {
        return (nodo.izq == null) ? nodo.valor : encontrarMinimo(nodo.izq);
    }

    // Recorrido Inorden (para mostrar árbol)
    public void mostrarInorden() {
        System.out.print("Inorden: ");
        inordenRec(raiz);
        System.out.println();
    }

    private void inordenRec(Nodo nodo) {
        if (nodo != null) {
            inordenRec(nodo.izq);
            System.out.print(nodo.valor + " ");
            inordenRec(nodo.der);
        }
    }

    // ------------------ MÉTODO PRINCIPAL ------------------

    public static void main(String[] args) {
        ArbolBinarioSimple arbol = new ArbolBinarioSimple();

        int[] datos = {50, 30, 70, 20, 40, 60, 80};
        for (int v : datos) arbol.insertar(v);

        System.out.println("Árbol inicial:");
        arbol.mostrarInorden(); // 20 30 40 50 60 70 80

        // Búsqueda
        System.out.println("Buscar 40 → " + arbol.buscar(40)); // true
        System.out.println("Buscar 90 → " + arbol.buscar(90)); // false

        // Eliminación de nodos
        System.out.println("\nEliminando 20 (hoja)...");
        arbol.eliminar(20);
        arbol.mostrarInorden();

        System.out.println("Eliminando 30 (un hijo)...");
        arbol.eliminar(30);
        arbol.mostrarInorden();

        System.out.println("Eliminando 50 (dos hijos)...");
        arbol.eliminar(50);
        arbol.mostrarInorden();
    }
}