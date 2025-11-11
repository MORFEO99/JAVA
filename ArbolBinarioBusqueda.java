// EJERCICIO 1 - Árbol Binario de Búsqueda básico (inserción y recorridos)

class Nodo {
    int valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}

public class ArbolBinarioBusqueda {
    private Nodo raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    // --- Agregar nodos al árbol ---
    public void agregar(int valor) {
        raiz = agregarRecursivo(raiz, valor);
    }

    private Nodo agregarRecursivo(Nodo actual, int valor) {
        if (actual == null) {
            return new Nodo(valor);
        }

        if (valor < actual.valor) {
            actual.izquierdo = agregarRecursivo(actual.izquierdo, valor);
        } else if (valor > actual.valor) {
            actual.derecho = agregarRecursivo(actual.derecho, valor);
        }

        return actual;
    }

    // --- Recorrido Inorden (Izquierda - Raíz - Derecha) ---
    public void inorden() {
        System.out.print("Inorden: ");
        inordenRecursivo(raiz);
        System.out.println();
    }

    private void inordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            inordenRecursivo(nodo.derecho);
        }
    }

    // --- Recorrido Preorden (Raíz - Izquierda - Derecha) ---
    public void preorden() {
        System.out.print("Preorden: ");
        preordenRecursivo(raiz);
        System.out.println();
    }

    private void preordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preordenRecursivo(nodo.izquierdo);
            preordenRecursivo(nodo.derecho);
        }
    }

    // --- Recorrido Posorden (Izquierda - Derecha - Raíz) ---
    public void posorden() {
        System.out.print("Posorden: ");
        posordenRecursivo(raiz);
        System.out.println();
    }

    private void posordenRecursivo(Nodo nodo) {
        if (nodo != null) {
            posordenRecursivo(nodo.izquierdo);
            posordenRecursivo(nodo.derecho);
            System.out.print(nodo.valor + " ");
        }
    }

    // --- Método principal ---
    public static void main(String[] args) {
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        // Agregar elementos al árbol
        int[] elementos = {50, 30, 70, 20, 40, 60, 80};
        for (int elem : elementos) {
            arbol.agregar(elem);
        }

        // Mostrar recorridos
        arbol.inorden();   // Resultado: 20 30 40 50 60 70 80
        arbol.preorden();  // Resultado: 50 30 20 40 70 60 80
        arbol.posorden();  // Resultado: 20 40 30 60 80 70 50
    }
}