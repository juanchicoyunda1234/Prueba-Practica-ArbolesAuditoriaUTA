package uta.arboles.app;

import java.util.ArrayList;
import java.util.Locale;
import uta.arboles.modelo.Estudiante;
import uta.arboles.negocio.ArbolBinarioBusqueda;

public class MenuABB {

    private final Consola consola;
    private final Contexto contexto;

    public MenuABB(Consola consola, Contexto contexto) {
        this.consola = consola;
        this.contexto = contexto;
    }

    public void ejecutar() {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            System.out.println("--- ABB de estudiantes (clave: matrícula) ---");
            System.out.println("1. Insertar estudiante");
            System.out.println("2. Buscar por matrícula");
            System.out.println("3. Modificar nombre y/o notas");
            System.out.println("4. Agregar una nota");
            System.out.println("5. Cambiar matrícula (eliminar e insertar)");
            System.out.println("6. Eliminar por matrícula");
            System.out.println("7. Recorridos preorden, inorden y posorden");
            System.out.println("8. Listar estudiantes en inorden");
            System.out.println("9. Métricas");
            System.out.println("0. Volver");
            Integer opcion = consola.leerEntero("Opción: ");
            if (opcion == null) {
                continue;
            }
            try {
                switch (opcion) {
                    case 1 -> insertar();
                    case 2 -> buscar();
                    case 3 -> modificar();
                    case 4 -> agregarNota();
                    case 5 -> cambiarMatricula();
                    case 6 -> eliminar();
                    case 7 -> recorridos();
                    case 8 -> listar();
                    case 9 -> metricas();
                    case 0 -> volver = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void insertar() {
        Integer matricula = consola.leerEntero("Matrícula: ");
        if (matricula == null) {
            return;
        }
        String nombre = consola.leerTexto("Nombre: ");
        ArrayList<Double> notas = consola.leerNotas("Notas de 0 a 10 separadas por espacios (ej. 8.5 9 7): ");
        if (notas == null) {
            return;
        }
        boolean insertado = contexto.abb().insertar(new Estudiante(matricula, nombre, notas));
        System.out.println(insertado ? "Estudiante insertado."
                : "Rechazado: la matrícula ya existe.");
    }

    private void buscar() {
        Integer matricula = consola.leerEntero("Matrícula a buscar: ");
        if (matricula == null) {
            return;
        }
        Estudiante estudiante = contexto.abb().buscar(matricula);
        System.out.println(estudiante == null ? "No existe esa matrícula." : estudiante);
        System.out.println("Comparaciones realizadas: " + contexto.abb().comparacionesBusqueda(matricula));
    }

    private void modificar() {
        Integer matricula = consola.leerEntero("Matrícula a modificar: ");
        if (matricula == null) {
            return;
        }
        String nombre = consola.leerTexto("Nuevo nombre (Enter para dejarlo igual): ");
        String textoNotas = consola.leerTexto("Nuevas notas separadas por espacios (Enter para dejarlas igual): ");
        ArrayList<Double> notas = null;
        if (!textoNotas.isEmpty()) {
            notas = consola.parsearNotas(textoNotas);
            if (notas == null) {
                return;
            }
        }
        String nombreFinal = nombre.isEmpty() ? null : nombre;
        if (nombreFinal == null && notas == null) {
            System.out.println("No se indicó ningún cambio.");
            return;
        }
        boolean modificado = contexto.abb().modificarDatos(matricula, nombreFinal, notas);
        if (modificado) {
            System.out.println("Modificado: " + contexto.abb().buscar(matricula));
        } else {
            System.out.println("Rechazado: la matrícula no existe.");
        }
    }

    private void agregarNota() {
        Integer matricula = consola.leerEntero("Matrícula: ");
        if (matricula == null) {
            return;
        }
        Double nota = consola.leerDecimal("Nota (0 a 10): ");
        if (nota == null) {
            return;
        }
        boolean agregada = contexto.abb().agregarNota(matricula, nota);
        if (agregada) {
            System.out.println("Nota agregada: " + contexto.abb().buscar(matricula));
        } else {
            System.out.println("Rechazado: la matrícula no existe.");
        }
    }

    private void cambiarMatricula() {
        Integer actual = consola.leerEntero("Matrícula actual: ");
        if (actual == null) {
            return;
        }
        Integer nueva = consola.leerEntero("Matrícula nueva: ");
        if (nueva == null) {
            return;
        }
        boolean cambiada = contexto.abb().cambiarMatricula(actual, nueva);
        System.out.println(cambiada ? "Matrícula cambiada: " + contexto.abb().buscar(nueva)
                : "Rechazado: la actual no existe, la nueva ya existe o no es válida.");
    }

    private void eliminar() {
        Integer matricula = consola.leerEntero("Matrícula a eliminar: ");
        if (matricula == null) {
            return;
        }
        boolean eliminada = contexto.abb().eliminar(matricula);
        System.out.println(eliminada ? "Estudiante eliminado."
                : "Rechazado: la matrícula no existe.");
    }

    private void recorridos() {
        ArbolBinarioBusqueda abb = contexto.abb();
        if (abb.estaVacio()) {
            System.out.println("(árbol vacío)");
            return;
        }
        System.out.println("Preorden: " + abb.preorden());
        System.out.println("Inorden:  " + abb.inorden());
        System.out.println("Posorden: " + abb.posorden());
    }

    private void listar() {
        ArrayList<Estudiante> estudiantes = contexto.abb().listarInorden();
        if (estudiantes.isEmpty()) {
            System.out.println("(árbol vacío)");
            return;
        }
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }
    }

    private void metricas() {
        ArbolBinarioBusqueda abb = contexto.abb();
        System.out.println("Nodos: " + abb.contarNodos());
        System.out.println("Hojas: " + abb.contarHojas());
        System.out.println("Altura (aristas): " + abb.altura());
        Estudiante menor = abb.minimo();
        Estudiante mayor = abb.maximo();
        System.out.println("Matrícula menor: " + (menor == null ? "N/A" : menor.getMatricula()));
        System.out.println("Matrícula mayor: " + (mayor == null ? "N/A" : mayor.getMatricula()));
        System.out.println(String.format(Locale.US, "Promedio general: %.2f", abb.promedioGeneral()));
        System.out.println("Invariante ABB: " + (abb.esValido() ? "se cumple" : "VIOLADO"));
    }
}
