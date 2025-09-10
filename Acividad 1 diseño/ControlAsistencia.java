import java.util.Scanner;

public class ControlAsistencia {

    // Constantes
    static final int DIAS_SEMANA = 5;
    static final int NUM_ESTUDIANTES = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Arreglo para asistencia [estudiantes][días]
        char[][] asistencia = new char[NUM_ESTUDIANTES][DIAS_SEMANA];

        boolean salir = false;

        while (!salir) {
            System.out.println("\n====================================");
            System.out.println("   SISTEMA DE CONTROL DE ASISTENCIA");
            System.out.println("====================================");
            System.out.println("1. Registrar asistencia");
            System.out.println("2. Ver asistencia individual");
            System.out.println("3. Ver resumen general");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    // Llenar asistencia
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        System.out.println("\n--- Estudiante " + (i + 1) + " ---");
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            char valor;
                            do {
                                System.out.print("Día " + (j + 1) + " (P=Presente / A=Ausente): ");
                                valor = sc.next().toUpperCase().charAt(0);
                            } while (valor != 'P' && valor != 'A');
                            asistencia[i][j] = valor;
                        }
                    }
                    System.out.println("\nRegistro de asistencia completado.");
                    break;

                case 2:
                    // Mostrar asistencia de un estudiante
                    System.out.print("Ingrese el número del estudiante (1-" + NUM_ESTUDIANTES + "): ");
                    int est = sc.nextInt() - 1;
                    if (est >= 0 && est < NUM_ESTUDIANTES) {
                        System.out.print("Asistencia del Estudiante " + (est + 1) + ": ");
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            System.out.print(asistencia[est][j] + " ");
                        }
                        System.out.println();
                    } else {
                        System.out.println("Número de estudiante inválido.");
                    }
                    break;

                case 3:
                    // Resumen general
                    int[] totalAsistencias = new int[NUM_ESTUDIANTES];
                    int[] ausenciasPorDia = new int[DIAS_SEMANA];

                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            if (asistencia[i][j] == 'P') {
                                totalAsistencias[i]++;
                            } else {
                                ausenciasPorDia[j]++;
                            }
                        }
                    }

                    System.out.println("\n--- RESUMEN GENERAL ---");

                    // Total de asistencias por estudiante
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        System.out.println("Estudiante " + (i + 1) + ": " + totalAsistencias[i] + " asistencias");
                    }

                    // Estudiantes con asistencia perfecta
                    System.out.print("Estudiantes con asistencia perfecta: ");
                    boolean encontrado = false;
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        if (totalAsistencias[i] == DIAS_SEMANA) {
                            System.out.print((i + 1) + " ");
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        System.out.print("Ninguno");
                    }
                    System.out.println();

                    // Día con más ausencias
                    int maxAusencias = 0;
                    int peorDia = -1;
                    for (int j = 0; j < DIAS_SEMANA; j++) {
                        if (ausenciasPorDia[j] > maxAusencias) {
                            maxAusencias = ausenciasPorDia[j];
                            peorDia = j;
                        }
                    }
                    if (peorDia != -1) {
                        System.out.println("Día con mayor número de ausencias: Día " + (peorDia + 1));
                    } else {
                        System.out.println("No se registraron ausencias.");
                    }
                    break;

                case 4:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }

        sc.close();
    }
}
