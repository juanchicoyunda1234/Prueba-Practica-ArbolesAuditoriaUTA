package uta.arboles.app;

import java.util.Arrays;
import uta.arboles.negocio.AnalisisBusqueda;
import uta.arboles.negocio.Auditoria;
import uta.arboles.negocio.DatosIniciales;

public class Main {

    private static final int N_MAXIMO_ANALISIS = 500;

    public static void main(String[] args) {
        Consola consola = new Consola();
        Contexto contexto = new Contexto();
        MenuArbolGeneral menuGeneral = new MenuArbolGeneral(consola, contexto);
        MenuABB menuAbb = new MenuABB(consola, contexto);
        try {
            boolean salir = false;
            while (!salir) {
                mostrarMenu();
                Integer opcion = consola.leerEntero("Opción: ");
                if (opcion == null) {
                    continue;
                }
                try {
                    switch (opcion) {
                        case 1 -> menuGeneral.ejecutar();
                        case 2 -> menuAbb.ejecutar();
                        case 3 -> System.out.print(
                                Auditoria.generar(contexto.general(), contexto.abb()).resumen());
                        case 4 -> analizar(consola);
                        case 5 -> cargarObligatorios(contexto);
                        case 6 -> EscenarioAceptacion.ejecutar(contexto);
                        case 7 -> vaciar(contexto);
                        case 0 -> salir = true;
                        default -> System.out.println("Opción no válida.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            System.out.println("Hasta luego.");
        } catch (IllegalStateException e) {
            System.out.println();
            System.out.println("Fin de la entrada. Saliendo.");
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== Sistema de auditoría jerárquica y académica UTA ===");
        System.out.println("1. Árbol general (jerarquía académica)");
        System.out.println("2. ABB de estudiantes");
        System.out.println("3. Auditoría");
        System.out.println("4. Análisis de comparaciones de búsqueda");
        System.out.println("5. Cargar datos obligatorios (reinicia ambos árboles)");
        System.out.println("6. Ejecutar escenario obligatorio completo");
        System.out.println("7. Vaciar ambos árboles");
        System.out.println("0. Salir");
    }

    private static void cargarObligatorios(Contexto contexto) {
        contexto.cargarObligatorios();
        System.out.println("Datos obligatorios cargados: "
                + contexto.general().numeroNodos() + " unidades y "
                + contexto.abb().contarNodos() + " estudiantes.");
    }

    private static void vaciar(Contexto contexto) {
        contexto.vaciar();
        System.out.println("Ambos árboles quedaron vacíos.");
    }

    private static void analizar(Consola consola) {
        String textoN = consola.leerTexto("Tamaño n de cada ABB (Enter = 12, máximo " + N_MAXIMO_ANALISIS + "): ");
        int n = 12;
        if (!textoN.isEmpty()) {
            try {
                n = Integer.parseInt(textoN);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: se esperaba un número entero.");
                return;
            }
        }
        if (n < 1 || n > N_MAXIMO_ANALISIS) {
            System.out.println("El tamaño debe estar entre 1 y " + N_MAXIMO_ANALISIS + ".");
            return;
        }
        int[] claves = generarClaves(n);
        int mayor = claves[claves.length - 1];
        Integer existente = leerClaveOpcional(consola, "Clave existente a buscar (Enter = " + mayor + "): ", mayor);
        if (existente == null) {
            return;
        }
        Integer inexistente = leerClaveOpcional(consola,
                "Clave inexistente a buscar (Enter = " + (mayor + 5) + "): ", mayor + 5);
        if (inexistente == null) {
            return;
        }
        System.out.print(AnalisisBusqueda.comparar(claves, existente, inexistente).resumen());
    }

    private static Integer leerClaveOpcional(Consola consola, String mensaje, int porDefecto) {
        String texto = consola.leerTexto(mensaje);
        if (texto.isEmpty()) {
            return porDefecto;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida: se esperaba un número entero.");
            return null;
        }
    }

    private static int[] generarClaves(int n) {
        if (n == DatosIniciales.MATRICULAS_EN_ORDEN.length) {
            int[] claves = DatosIniciales.MATRICULAS_EN_ORDEN.clone();
            Arrays.sort(claves);
            return claves;
        }
        int[] claves = new int[n];
        for (int i = 0; i < n; i++) {
            claves[i] = 10 * (i + 1);
        }
        return claves;
    }
}
