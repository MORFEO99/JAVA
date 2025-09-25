/**
 * Implementación mejorada de una pila básica de enteros con redimensionamiento dinámico.
 */
public class MaximoPila {
    private int[] elementos;
    private int tope;

    /**
     * Constructor que inicializa la pila con una capacidad inicial.
     * @param capacidad Tamaño inicial de la pila.
     */
    public PilaBasica(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("Capacidad debe ser mayor que 0");
        this.elementos = new int[capacidad];
        this.tope = -1;
    }
    
    /**
     * Constructor de copia para crear una copia de la pila.
     */
    public PilaBasica(PilaBasica otra) {
        this.elementos = new int[otra.elementos.length];
        this.tope = otra.tope;
        System.arraycopy(otra.elementos, 0, this.elementos, 0, otra.elementos.length);
    }
    
    public int size() {
        return tope + 1;
    }

    /**
     * Inserta un elemento en la pila.
     * @param elemento Elemento a insertar.
     */
    public void push(int elemento) {
        if (tope == elementos.length - 1) {
            redimensionar(); // Duplica el tamaño
        }
        elementos[++tope] = elemento;
    }

    /**
     * Elimina y retorna el elemento superior de la pila.
     * @return Elemento superior.
     * @throws IllegalStateException si la pila está vacía.
     */
    public int pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Error: Pila vacía");
        }
        return elementos[tope--];
    }

    /**
     * Retorna el elemento superior sin eliminarlo.
     * @return Elemento superior.
     * @throws IllegalStateException si la pila está vacía.
     */
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Error: Pila vacía");
        }
        return elementos[tope];
    }

    /**
     * Verifica si la pila está vacía.
     * @return true si está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return tope == -1;
    }

    /**
     * Muestra los elementos actuales de la pila.
     */
    public void mostrar() {
        if (isEmpty()) {
            System.out.println("Pila vacía");
            return;
        }
        System.out.print("Pila: ");
        for (int i = 0; i <= tope; i++) {
            System.out.print(elementos[i] + " ");
        }
        System.out.println();
    }

    /**
     * Duplica el tamaño del arreglo interno.
     */
    private void redimensionar() {
        int nuevaCapacidad = elementos.length * 2;
        int[] nuevoArray = new int[nuevaCapacidad];
        System.arraycopy(elementos, 0, nuevoArray, 0, elementos.length);
        elementos = nuevoArray;
    }


    public static int encontrarMaximo(PilaBasica pila) {
        if (pila.isEmpty()) {
            System.out.println("La pila está vacía");
            return Integer.MIN_VALUE;
        }
        
        int maximo = Integer.MIN_VALUE;
        
        // Crear una copia de la pila para no modificar la original
        PilaBasica copia = new PilaBasica(pila);
        
        // Recorrer la copia de la pila y encontrar el máximo
        while (!copia.isEmpty()) {
            int elemento = copia.pop();
            if (elemento > maximo) {
                maximo = elemento;
            }
        }
        
        return maximo;
    }

    /**
     * Método que encuentra el elemento mínimo de la pila.
     * La pila original permanece intacta después de la operación.
     * @param pila Pila en la que buscar el mínimo.
     * @return El elemento mínimo de la pila.
     */
    public static int encontrarMinimo(PilaBasica pila) {
        if (pila.isEmpty()) {
            System.out.println("La pila está vacía");
            return Integer.MAX_VALUE;
        }
        
        int minimo = Integer.MAX_VALUE;
        
        // Crear una copia de la pila para no modificar la original
        PilaBasica copia = new PilaBasica(pila);
        
        // Recorrer la copia de la pila y encontrar el mínimo
        while (!copia.isEmpty()) {
            int elemento = copia.pop();
            if (elemento < minimo) {
                minimo = elemento;
            }
        }
        
        return minimo;
    }
    
    /**
     * Método alternativo para encontrar máximo sin modificar ninguna pila
     */
    public static int encontrarMaximoAlternativo(PilaBasica pila) {
        if (pila.isEmpty()) {
            System.out.println("La pila está vacía");
            return Integer.MIN_VALUE;
        }
        
        int maximo = Integer.MIN_VALUE;
        PilaBasica temporal = new PilaBasica(pila.size());
        
        // Transferir elementos a pila temporal y encontrar máximo
        while (!pila.isEmpty()) {
            int elemento = pila.pop();
            if (elemento > maximo) {
                maximo = elemento;
            }
            temporal.push(elemento);
        }
        
        // Restaurar elementos a la pila original
        while (!temporal.isEmpty()) {
            pila.push(temporal.pop());
        }
        
        return maximo;
    }
    
    /**
     * Método alternativo para encontrar mínimo sin modificar ninguna pila
     */
    public static int encontrarMinimoAlternativo(PilaBasica pila) {
        if (pila.isEmpty()) {
            System.out.println("La pila está vacía");
            return Integer.MAX_VALUE;
        }
        
        int minimo = Integer.MAX_VALUE;
        PilaBasica temporal = new PilaBasica(pila.size());
        
        // Transferir elementos a pila temporal y encontrar mínimo
        while (!pila.isEmpty()) {
            int elemento = pila.pop();
            if (elemento < minimo) {
                minimo = elemento;
            }
            temporal.push(elemento);
        }
        
        // Restaurar elementos a la pila original
        while (!temporal.isEmpty()) {
            pila.push(temporal.pop());
        }
        
        return minimo;
    }
    
    /**
     * Método main para probar la pila y las funciones de máximo/mínimo.
     */
    public static void main(String[] args) {
        // Pruebas de la pila básica
        PilaBasica pila = new PilaBasica(3);
        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.mostrar();

        System.out.println("Elemento superior: " + pila.peek());
        System.out.println("Eliminado: " + pila.pop());
        pila.mostrar();

        pila.push(40);
        pila.push(50);
        pila.push(60); // Provoca redimensionamiento
        pila.mostrar();
        
        // Pruebas de encontrar máximo y mínimo
        System.out.println("\n--- Pruebas de Máximo y Mínimo ---");
        
        PilaBasica pilaPrueba = new PilaBasica(5);
        pilaPrueba.push(15);
        pilaPrueba.push(8);
        pilaPrueba.push(23);
        pilaPrueba.push(4);
        pilaPrueba.push(17);
        
        System.out.println("Pila original:");
        pilaPrueba.mostrar();
        
        // Usar los métodos alternativos que restauran la pila original
        int maximo = encontrarMaximoAlternativo(pilaPrueba);
        int minimo = encontrarMinimoAlternativo(pilaPrueba);
        
        System.out.println("Máximo de la pila: " + maximo);
        System.out.println("Mínimo de la pila: " + minimo);
        
        // Verificar que la pila original permanece intacta
        System.out.println("Pila después de buscar máximo y mínimo:");
        pilaPrueba.mostrar();
        
        // Prueba con pila vacía
        PilaBasica pilaVacia = new PilaBasica(3);
        System.out.println("Prueba con pila vacía:");
        System.out.println("Máximo: " + encontrarMaximo(pilaVacia));
        System.out.println("Mínimo: " + encontrarMinimo(pilaVacia));
        
        // Prueba adicional para verificar que los métodos funcionan correctamente
        System.out.println("\n--- Prueba Adicional ---");
        PilaBasica pilaTest = new PilaBasica(3);
        pilaTest.push(5);
        pilaTest.push(2);
        pilaTest.push(9);
        pilaTest.push(1);
        
        System.out.println("Pila de prueba:");
        pilaTest.mostrar();
        System.out.println("Máximo (método copia): " + encontrarMaximo(pilaTest));
        System.out.println("Mínimo (método copia): " + encontrarMinimo(pilaTest));
        System.out.println("Pila después (debe estar intacta):");
        pilaTest.mostrar();
    }
}