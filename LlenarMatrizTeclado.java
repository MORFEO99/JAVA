import java.util.Scanner;

public class LlenarMatrizTeclado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Pedir dimensiones de la matriz
        System.out.print("Ingrese el numero de filas: ");
        int filas = scanner.nextInt();

        System.out.print("Ingrese el numero de columnas: ");
        int columnas = scanner.nextInt();

        // 2. Declarar y crear la matriz
        int[][] matriz = new int[filas][columnas];

        // 3. Llenar la matriz con datos del usuario
        System.out.println("\nIngrese los valores para la matriz:");

        // Bucles anidados para recorrer filas y columnas
        for (int i = 0; i < filas; i++) { // Recorre filas
            for (int j = 0; j < columnas; j++) { // Recorre columnas
                System.out.print("Matriz[" + i + "][" + j + "]: ");
                matriz[i][j] = scanner.nextInt();
            }
        }

        // 4. Mostrar la matriz resultante
        System.out.println("\n--- Matriz Resultante ---");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + "\t"); // \t para alinear columnas
            }
            System.out.println(); // Salto de linea despues de cada fila
        }

        // Cerrar scanner
        scanner.close();
    }
}