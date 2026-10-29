package uta.arboles.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Consola {

    private final Scanner scanner = new Scanner(System.in);

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Fin de la entrada.");
        }
        return scanner.nextLine().trim();
    }

    public Integer leerEntero(String mensaje) {
        String texto = leerTexto(mensaje);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida: se esperaba un número entero.");
            return null;
        }
    }

    public Double leerDecimal(String mensaje) {
        String texto = leerTexto(mensaje).replace(',', '.');
        try {
            double valor = Double.parseDouble(texto);
            if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                System.out.println("Entrada inválida: se esperaba un número.");
                return null;
            }
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida: se esperaba un número.");
            return null;
        }
    }

    public ArrayList<Double> leerNotas(String mensaje) {
        return parsearNotas(leerTexto(mensaje));
    }

    public ArrayList<Double> parsearNotas(String texto) {
        ArrayList<Double> notas = new ArrayList<>();
        if (texto.isEmpty()) {
            return notas;
        }
        for (String pieza : texto.split("[\\s;]+")) {
            try {
                double valor = Double.parseDouble(pieza.replace(',', '.'));
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    System.out.println("Entrada inválida: '" + pieza + "' no es una nota.");
                    return null;
                }
                notas.add(valor);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: '" + pieza + "' no es una nota.");
                return null;
            }
        }
        return notas;
    }
}
