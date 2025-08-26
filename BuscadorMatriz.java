import java.util.Scanner;

public class BuscadorMatriz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Matriz predefinida
        int[][] matriz = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // 2. Pedir numero a buscar
        System.out.print("Ingrese un numero a buscar: ");
        int numero = scanner.nextInt();

        boolean encontrado = false;

        // 3. Recorrer matriz con bucles anidados
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == numero) {
                    System.out.println("Numero encontrado en [" + i + "][" + j + "]");
                    encontrado = true;
                }
            }
        }

        // 4. Si no existe
        if (!encontrado) {
            System.out.println("Numero no encontrado");
        }

        scanner.close();
    }
}