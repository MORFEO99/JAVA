public class MatrizPocoDensa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] matriz = new int[3][3];
        String[] posiciones = {"primer", "segundo", "tercer", "cuarto", "quinto", 
                               "sexto", "séptimo", "octavo", "noveno"};

        System.out.println("Ingrese los valores de la matriz 3x3:");

        int contador = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("Ingrese el " + posiciones[contador] + " valor: ");
                matriz[i][j] = scanner.nextInt();
                contador++;
            }
        }

        // Mostrar solo los elementos distintos de cero
        System.out.println("\n--- Elementos distintos de cero ---");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j] != 0) {
                    System.out.println("[" + i + ", " + j + "] = " + matriz[i][j]);
                }
            }
        }

        scanner.close();
    }
}