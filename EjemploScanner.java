import java.util.Scanner; // Importación obligatoria 
public class EjemploScanner { 
public static void main(String[] args) { 
// Paso 1: Crear un objeto Scanner asociado a System.in (teclado) 
Scanner scanner = new Scanner(System.in); 
// Paso 2: Leer diferentes tipos de datos 
System.out.print("Ingrese su nombre: "); 
String nombre = scanner.nextLine(); // Lee una línea completa de texto 
System.out.print("Ingrese su edad: "); 
int edad = scanner.nextInt(); // Lee un número entero 
System.out.print("Ingrese su estatura (metros): "); 
double estatura = scanner.nextDouble(); // Lee un número decimal 
// Paso 3: Mostrar los datos leídos 
System.out.println("\n--- Datos Ingresados ---"); 
System.out.println("Nombre: " + nombre); 
System.out.println("Edad: " + edad); 
System.out.println("Estatura: " + estatura + " m"); 
// Buena práctica: Cerrar el scanner al finalizar su uso 
scanner.close(); 
} 
} 