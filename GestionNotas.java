import java.util.Scanner;

public class GestionNotas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pedir cantidad de alumnos
        System.out.print("Ingrese la cantidad de alumnos: ");
        int n = scanner.nextInt();

        // Crear vector para almacenar notas
        int[] notas = new int[n];

        // Ingreso de notas
        for (int i = 0; i < n; i++) {
            System.out.print("Ingrese la nota del alumno " + (i + 1) + ": ");
            notas[i] = scanner.nextInt();
        }

        // Calcular promedio
        int suma = 0;
        for (int nota : notas) {
            suma += nota;
        }
        double promedio = (double) suma / n;

        // Contar aprobados y reprobados
        int aprobados = 0, reprobados = 0;
        for (int nota : notas) {
            if (nota >= 70) {
                aprobados++;
            } else {
                reprobados++;
            }
        }

        // Mostrar resultados
        System.out.println("\n--- Resultados ---");
        System.out.println("Promedio de la clase: " + promedio);
        System.out.println("Alumnos aprobados: " + aprobados);
        System.out.println("Alumnos reprobados: " + reprobados);

        // Mensaje personalizado
        if (promedio >= 90) {
            System.out.println("¡Excelente clase!");
        } else if (promedio >= 70) {
            System.out.println("Buen rendimiento.");
        } else {
            System.out.println("Necesitamos mejorar.");
        }

        scanner.close();
    }
}