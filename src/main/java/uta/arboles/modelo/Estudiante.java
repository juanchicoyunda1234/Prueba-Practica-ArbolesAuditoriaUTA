package uta.arboles.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Estudiante {

    public static final double NOTA_MINIMA_VALIDA = 0.0;
    public static final double NOTA_MAXIMA_VALIDA = 10.0;

    private final int matricula;
    private String nombre;
    private ArrayList<Double> notas;

    public Estudiante(int matricula, String nombre, List<Double> notas) {
        if (matricula <= 0) {
            throw new IllegalArgumentException("La matrícula debe ser un entero positivo.");
        }
        this.matricula = matricula;
        this.nombre = validarNombre(nombre);
        this.notas = validarNotas(notas);
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Double> getNotas() {
        return Collections.unmodifiableList(notas);
    }

    public void actualizar(String nuevoNombre, List<Double> nuevasNotas) {
        String nombreValidado = null;
        ArrayList<Double> notasValidadas = null;
        if (nuevoNombre != null) {
            nombreValidado = validarNombre(nuevoNombre);
        }
        if (nuevasNotas != null) {
            notasValidadas = validarNotas(nuevasNotas);
        }
        if (nombreValidado != null) {
            nombre = nombreValidado;
        }
        if (notasValidadas != null) {
            notas = notasValidadas;
        }
    }

    public void agregarNota(double nota) {
        validarNota(nota);
        notas.add(nota);
    }

    public double promedio() {
        double suma = 0.0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }

    public double notaMaxima() {
        double mayor = notas.get(0);
        for (double nota : notas) {
            if (nota > mayor) {
                mayor = nota;
            }
        }
        return mayor;
    }

    public double notaMinima() {
        double menor = notas.get(0);
        for (double nota : notas) {
            if (nota < menor) {
                menor = nota;
            }
        }
        return menor;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%d | %s | notas=%s | promedio=%.2f | max=%.2f | min=%.2f",
                matricula, nombre, notas, promedio(), notaMaxima(), notaMinima());
    }

    private static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        return nombre.trim();
    }

    private static ArrayList<Double> validarNotas(List<Double> notas) {
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("Debe registrarse al menos una nota.");
        }
        ArrayList<Double> copia = new ArrayList<>();
        for (Double nota : notas) {
            if (nota == null) {
                throw new IllegalArgumentException("Nota nula no permitida.");
            }
            validarNota(nota);
            copia.add(nota);
        }
        return copia;
    }

    private static void validarNota(double nota) {
        if (Double.isNaN(nota) || nota < NOTA_MINIMA_VALIDA || nota > NOTA_MAXIMA_VALIDA) {
            throw new IllegalArgumentException("Cada nota debe estar entre 0 y 10.");
        }
    }
}
