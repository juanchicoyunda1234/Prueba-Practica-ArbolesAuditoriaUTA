package uta.arboles.negocio;

import java.util.ArrayList;
import java.util.List;
import uta.arboles.modelo.Estudiante;

public final class AnalisisBusqueda {

    private AnalisisBusqueda() {
    }

    public static ResultadoAnalisis comparar(int[] clavesOrdenadas, int existente, int inexistente) {
        validar(clavesOrdenadas, existente, inexistente);
        ArbolBinarioBusqueda cadena = construir(clavesOrdenadas);
        ArbolBinarioBusqueda equilibrado = construir(ordenEquilibrado(clavesOrdenadas));
        return new ResultadoAnalisis(
                clavesOrdenadas.length,
                existente,
                inexistente,
                cadena.altura(),
                equilibrado.altura(),
                cadena.comparacionesBusqueda(existente),
                equilibrado.comparacionesBusqueda(existente),
                cadena.comparacionesBusqueda(inexistente),
                equilibrado.comparacionesBusqueda(inexistente),
                promedioComparaciones(cadena, clavesOrdenadas),
                promedioComparaciones(equilibrado, clavesOrdenadas));
    }

    public static int[] ordenEquilibrado(int[] clavesOrdenadas) {
        ArrayList<Integer> orden = new ArrayList<>();
        seleccionarMedianas(clavesOrdenadas, 0, clavesOrdenadas.length - 1, orden);
        int[] resultado = new int[orden.size()];
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = orden.get(i);
        }
        return resultado;
    }

    private static void seleccionarMedianas(int[] claves, int inicio, int fin, ArrayList<Integer> orden) {
        if (inicio > fin) {
            return;
        }
        int medio = (inicio + fin) / 2;
        orden.add(claves[medio]);
        seleccionarMedianas(claves, inicio, medio - 1, orden);
        seleccionarMedianas(claves, medio + 1, fin, orden);
    }

    private static ArbolBinarioBusqueda construir(int[] ordenInsercion) {
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();
        for (int clave : ordenInsercion) {
            arbol.insertar(new Estudiante(clave, "Estudiante " + clave, List.of(7.0)));
        }
        return arbol;
    }

    private static double promedioComparaciones(ArbolBinarioBusqueda arbol, int[] claves) {
        double suma = 0.0;
        for (int clave : claves) {
            suma += arbol.comparacionesBusqueda(clave);
        }
        return suma / claves.length;
    }

    private static void validar(int[] claves, int existente, int inexistente) {
        if (claves == null || claves.length == 0) {
            throw new IllegalArgumentException("Se necesita al menos una clave.");
        }
        boolean existeEnClaves = false;
        boolean inexistenteEnClaves = false;
        for (int i = 0; i < claves.length; i++) {
            if (claves[i] <= 0 || (i > 0 && claves[i] <= claves[i - 1])) {
                throw new IllegalArgumentException("Las claves deben ser positivas y estrictamente ascendentes.");
            }
            if (claves[i] == existente) {
                existeEnClaves = true;
            }
            if (claves[i] == inexistente) {
                inexistenteEnClaves = true;
            }
        }
        if (!existeEnClaves) {
            throw new IllegalArgumentException("La clave a buscar como existente no está en el conjunto.");
        }
        if (inexistenteEnClaves) {
            throw new IllegalArgumentException("La clave a buscar como inexistente ya está en el conjunto.");
        }
    }
}
