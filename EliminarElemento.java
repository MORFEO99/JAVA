public class EliminarElemento {
    // Método que los estudiantes deben completar
    public static void eliminarOcurrencias(PilaArray pila, int elementoAEliminar) {
        if (pila.isEmpty()) {
            System.out.println("La pila está vacía");
            return;
        }
        
        // Crear una pila temporal para almacenar los elementos que NO queremos eliminar
        PilaArray pilaTemporal = new PilaArray(pila.size());
        int contadorEliminados = 0;
        
        // Paso 1: Recorrer la pila original y transferir elementos a la temporal
        // (excepto los que coinciden con elementoAEliminar)
        while (!pila.isEmpty()) {
            int elementoActual = pila.pop();
            if (elementoActual != elementoAEliminar) {
                pilaTemporal.push(elementoActual);
            } else {
                contadorEliminados++;
            }
        }
        
        // Paso 2: Transferir los elementos de vuelta a la pila original
        // (restaurando el orden original)
        while (!pilaTemporal.isEmpty()) {
            pila.push(pilaTemporal.pop());
        }
        
        if (contadorEliminados > 0) {
            System.out.println("Se eliminaron " + contadorEliminados + " ocurrencias del elemento " + elementoAEliminar);
        } else {
            System.out.println("No se encontró el elemento " + elementoAEliminar + " en la pila");
        }
    }
}

// Clase PilaArray 
class PilaArray {
    private int[] elementos;
    private int tope;
    private int capacidad;
    
    public PilaArray(int capacidad) {
        this.capacidad = capacidad;
        this.elementos = new int[capacidad];
        this.tope = -1;
    }
    
    public void push(int elemento) {
        if (tope == capacidad - 1) {
            System.out.println("Error: Pila llena");
            return;
        }
        elementos[++tope] = elemento;
    }
    
    public int pop() {
        if (isEmpty()) {
            System.out.println("Error: Pila vacía");
            return -1;
        }
        return elementos[tope--];
    }
    
    public int peek() {
        if (isEmpty()) {
            System.out.println("Error: Pila vacía");
            return -1;
        }
        return elementos[tope];
    }
    
    public boolean isEmpty() {
        return tope == -1;
    }
    
    public int size() {
        return tope + 1;
    }
    
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

// Clase para probar el método
class PruebaEliminar {
    public static void main(String[] args) {
        // Crear una pila de prueba
        PilaArray pila = new PilaArray(10);
        
        // Llenar la pila con elementos (algunos repetidos)
        pila.push(5);
        pila.push(2);
        pila.push(8);
        pila.push(2);
        pila.push(3);
        pila.push(2);
        pila.push(7);
        pila.push(2);
        
        System.out.println("=== PRUEBA DE ELIMINACIÓN DE OCURRENCIAS ===");
        System.out.print("Pila original: ");
        pila.mostrar();
        
        // Eliminar todas las ocurrencias del número 2
        EliminarElemento.eliminarOcurrencias(pila, 2);
        
        System.out.print("Pila después de eliminar 2: ");
        pila.mostrar();
        
        // Probar con elemento que no existe
        EliminarElemento.eliminarOcurrencias(pila, 100);
        System.out.print("Pila después de intentar eliminar 100: ");
        pila.mostrar();
    }
}