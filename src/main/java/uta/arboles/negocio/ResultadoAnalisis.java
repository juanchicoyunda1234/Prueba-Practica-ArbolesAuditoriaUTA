package uta.arboles.negocio;

import java.util.Locale;

public record ResultadoAnalisis(
        int n,
        int existente,
        int inexistente,
        int alturaCadena,
        int alturaEquilibrado,
        int comparacionesExistenteCadena,
        int comparacionesExistenteEquilibrado,
        int comparacionesInexistenteCadena,
        int comparacionesInexistenteEquilibrado,
        double promedioCadena,
        double promedioEquilibrado) {

    public String resumen() {
        StringBuilder salida = new StringBuilder();
        salida.append("Tamaño de cada ABB: n = ").append(n).append('\n');
        salida.append(String.format(Locale.US, "%-42s %-12s %-12s%n", "", "Cadena", "Equilibrado"));
        salida.append(String.format(Locale.US, "%-42s %-12d %-12d%n", "Altura (aristas)", alturaCadena, alturaEquilibrado));
        salida.append(String.format(Locale.US, "%-42s %-12d %-12d%n",
                "Comparaciones buscando " + existente + " (existe)",
                comparacionesExistenteCadena, comparacionesExistenteEquilibrado));
        salida.append(String.format(Locale.US, "%-42s %-12d %-12d%n",
                "Comparaciones buscando " + inexistente + " (no existe)",
                comparacionesInexistenteCadena, comparacionesInexistenteEquilibrado));
        salida.append(String.format(Locale.US, "%-42s %-12.2f %-12.2f%n",
                "Promedio sobre las " + n + " claves existentes", promedioCadena, promedioEquilibrado));
        salida.append("Cada comparación es un nodo visitado. La búsqueda cuesta O(h): h = n - 1 en la cadena,\n");
        salida.append("y h es menor solo porque el orden de inserción se diseñó; el ABB no se equilibra solo.\n");
        return salida.toString();
    }
}
