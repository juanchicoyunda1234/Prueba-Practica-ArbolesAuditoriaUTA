package uta.arboles.negocio;

import java.util.Locale;

public record ReporteAuditoria(
        int totalUnidades,
        int hojas,
        int alturaGeneral,
        boolean jerarquiaValida,
        int totalEstudiantes,
        Integer matriculaMenor,
        Integer matriculaMayor,
        double promedioGeneral,
        boolean abbValido) {

    public String resumen() {
        StringBuilder salida = new StringBuilder();
        salida.append("Total de unidades: ").append(totalUnidades).append('\n');
        salida.append("Hojas del árbol general: ").append(hojas).append('\n');
        salida.append("Altura general (aristas): ").append(alturaGeneral).append('\n');
        salida.append("Jerarquía válida (códigos únicos, sin ciclos): ").append(siNo(jerarquiaValida)).append('\n');
        salida.append("Total de estudiantes: ").append(totalEstudiantes).append('\n');
        salida.append("Matrícula menor: ").append(matriculaMenor == null ? "N/A" : matriculaMenor).append('\n');
        salida.append("Matrícula mayor: ").append(matriculaMayor == null ? "N/A" : matriculaMayor).append('\n');
        salida.append("Promedio general: ").append(String.format(Locale.US, "%.2f", promedioGeneral)).append('\n');
        salida.append("Invariante del ABB: ").append(abbValido ? "se cumple" : "VIOLADO").append('\n');
        return salida.toString();
    }

    private static String siNo(boolean valor) {
        return valor ? "sí" : "NO";
    }
}
