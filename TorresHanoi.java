import java.util.Scanner;

public class TorresHanoi {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== TORRES DE HANOI ===");
        System.out.print("Ingrese la cantidad de discos: ");
        int discos = scanner.nextInt();
        
        if (discos <= 0) {
            System.out.println("La cantidad de discos debe ser mayor a 0.");
            return;
        }
        
        System.out.println("\nSecuencia de movimientos para " + discos + " discos:");
        System.out.println("==========================================");
        
        // Llamar a la función recursiva
        resolverHanoi(discos, 'A', 'C', 'B');
        
        // Calcular el número total de movimientos
        long totalMovimientos = (long) Math.pow(2, discos) - 1;
        System.out.println("\n==========================================");
        System.out.println("Total de movimientos requeridos: " + totalMovimientos);
        
        scanner.close();
    }
    
    /**
     * Función recursiva para resolver el problema de las Torres de Hanoi
     * @param n Número de discos a mover
     * @param origen Torre de origen (A)
     * @param destino Torre de destino (C)
     * @param auxiliar Torre auxiliar (B)
     */
    public static void resolverHanoi(int n, char origen, char destino, char auxiliar) {
        // Caso base: si solo hay un disco
        if (n == 1) {
            System.out.println("Mover disco 1 de la torre " + origen + " a la torre " + destino);
            return;
        }
        
        // Mover n-1 discos de origen a auxiliar, usando destino como auxiliar
        resolverHanoi(n - 1, origen, auxiliar, destino);
        
        // Mover el disco n (el más grande) de origen a destino
        System.out.println("Mover disco " + n + " de la torre " + origen + " a la torre " + destino);
        
        // Mover los n-1 discos de auxiliar a destino, usando origen como auxiliar
        resolverHanoi(n - 1, auxiliar, destino, origen);
    }
}