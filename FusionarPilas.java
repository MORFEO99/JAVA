public class FusionarPilas {
    
    // Método que usa SOLO los métodos que tienes en tu PilaBasica
    public static PilaBasica fusionarPilasOrdenadas(PilaBasica pila1, PilaBasica pila2) {
        // Crear pila resultado con capacidad suficiente
        PilaBasica resultado = new PilaBasica(20);
        
        // Crear arrays temporales sin usar size()
        int[] temp1 = new int[10]; // Tamaño suficiente
        int[] temp2 = new int[10]; // Tamaño suficiente
        int count1 = 0, count2 = 0;
        
        // Extraer elementos de pila1 y contar cuántos hay
        while (!pila1.isEmpty()) {
            temp1[count1++] = pila1.pop();
        }
        
        // Extraer elementos de pila2 y contar cuántos hay
        while (!pila2.isEmpty()) {
            temp2[count2++] = pila2.pop();
        }
        
        // Combinar todos los elementos en un array
        int total = count1 + count2;
        int[] combinados = new int[total];
        
        // Copiar elementos de temp1
        for (int i = 0; i < count1; i++) {
            combinados[i] = temp1[i];
        }
        
        // Copiar elementos de temp2
        for (int i = 0; i < count2; i++) {
            combinados[count1 + i] = temp2[i];
        }
        
        // Ordenar el array combinado
        java.util.Arrays.sort(combinados);
        
        // Insertar en la pila resultado
        for (int i = 0; i < total; i++) {
            resultado.push(combinados[i]);
        }
        
        return resultado;
    }
    
    // Método de prueba
    public static void main(String[] args) {
        // Pila 1 ordenada ascendentemente
        PilaBasica pila1 = new PilaBasica(5);
        pila1.push(1);
        pila1.push(3);
        pila1.push(5);
        pila1.push(7);
        pila1.push(10);
        
        // Pila 2 ordenada ascendentemente
        PilaBasica pila2 = new PilaBasica(4);
        pila2.push(2);
        pila2.push(4);
        pila2.push(6);
        pila2.push(9);
        
        System.out.println("=== PILAS ORIGINALES ===");
        System.out.print("Pila 1: ");
        mostrarPila(pila1);
        System.out.print("Pila 2: ");
        mostrarPila(pila2);
        
        // Fusionar las pilas
        PilaBasica fusionada = fusionarPilasOrdenadas(pila1, pila2);
        
        System.out.println("=== PILA FUSIONADA ===");
        System.out.print("Resultado: ");
        mostrarPila(fusionada);
        
        // Verificar que las pilas originales quedaron vacías
        System.out.println("Pila 1 vacía: " + pila1.isEmpty());
        System.out.println("Pila 2 vacía: " + pila2.isEmpty());
    }
    
    // Método auxiliar para mostrar pila sin vaciarla
    public static void mostrarPila(PilaBasica pila) {
        if (pila.isEmpty()) {
            System.out.println("Pila vacía");
            return;
        }
        
        // Crear una pila temporal para no modificar la original
        PilaBasica temp = new PilaBasica(20);
        
        // Transferir elementos a temporal (invertir orden)
        while (!pila.isEmpty()) {
            temp.push(pila.pop());
        }
        
        System.out.print("Pila: ");
        
        // Mostrar y restaurar orden original
        while (!temp.isEmpty()) {
            int elemento = temp.pop();
            System.out.print(elemento + " ");
            pila.push(elemento);
        }
        System.out.println();
    }
}

// Clase PilaBasica (tu versión original - SOLO con los métodos que tienes)
class PilaBasica {
    private int[] elementos;
    private int tope;
    private int capacidad;
    
    public PilaBasica(int capacidad) {
        this.capacidad = capacidad;
        this.elementos = new int[capacidad];
        this.tope = -1;
    }
    
    // Operación push: insertar elemento
    public void push(int elemento) {
        if (tope == capacidad - 1) {
            System.out.println("Error: Pila llena");
            return;
        }
        elementos[++tope] = elemento;
    }
    
    // Operación pop: eliminar y retornar elemento
    public int pop() {
        if (isEmpty()) {
            System.out.println("Error: Pila vacía");
            return -1;
        }
        return elementos[tope--];
    }
    
    // Operación peek: ver elemento superior sin eliminarlo
    public int peek() {
        if (isEmpty()) {
            System.out.println("Error: Pila vacía");
            return -1;
        }
        return elementos[tope];
    }
    
    // Operación isEmpty: verificar si está vacía
    public boolean isEmpty() {
        return tope == -1;
    }
    
    // Operación adicional: mostrar pila
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
}