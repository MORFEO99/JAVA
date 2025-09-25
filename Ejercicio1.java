public class Ejercicio1 {

    public static boolean estaOrdenadaAscendente(PilaBasica pila) {
        if (pila.isEmpty() || pila.size() == 1) {
            return true;
        }

        PilaBasica aux = new PilaBasica(pila.size());
        boolean ordenada = true;

        int anterior = pila.pop();
        aux.push(anterior);

        while (!pila.isEmpty()) {
            int actual = pila.pop();
            aux.push(actual);

            if (actual > anterior) {
                ordenada = false;
                break;
            }

            anterior = actual;
        }

        // Restaurar la pila original
        while (!aux.isEmpty()) {
            pila.push(aux.pop());
        }

        return ordenada;
    }

    public static void main(String[] args) {
        // Caso 1: Pila ordenada ascendentemente
        PilaBasica pila1 = new PilaBasica(5);
        pila1.push(5);
        pila1.push(4);
        pila1.push(3);
        pila1.push(2);
        pila1.push(1);

        System.out.println("Pila 1:");
        pila1.mostrar();
        System.out.println("¿Está ordenada ascendentemente? " + estaOrdenadaAscendente(pila1));
        System.out.println("Pila después de verificación:");
        pila1.mostrar();

        // Caso 2: Pila no ordenada
        PilaBasica pila2 = new PilaBasica(5);
        pila2.push(3);
        pila2.push(5);
        pila2.push(1);
        pila2.push(4);
        pila2.push(2);

        System.out.println("\nPila 2:");
        pila2.mostrar();
        System.out.println("¿Está ordenada ascendentemente? " + estaOrdenadaAscendente(pila2));
        System.out.println("Pila después de verificación:");
        pila2.mostrar();

        // Caso 3: Pila vacía
        PilaBasica pila3 = new PilaBasica(5);

        System.out.println("\nPila 3 (vacía):");
        pila3.mostrar();
        System.out.println("¿Está ordenada ascendentemente? " + estaOrdenadaAscendente(pila3));
    }
}
