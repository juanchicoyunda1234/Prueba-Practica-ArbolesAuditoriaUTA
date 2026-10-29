package uta.arboles.negocio;

import uta.arboles.modelo.Estudiante;

public final class Auditoria {

    private Auditoria() {
    }

    public static ReporteAuditoria generar(ArbolGeneral general, ArbolBinarioBusqueda abb) {
        Estudiante menor = abb.minimo();
        Estudiante mayor = abb.maximo();
        return new ReporteAuditoria(
                general.numeroNodos(),
                general.numeroHojas(),
                general.altura(),
                general.jerarquiaValida(),
                abb.contarNodos(),
                menor == null ? null : menor.getMatricula(),
                mayor == null ? null : mayor.getMatricula(),
                abb.promedioGeneral(),
                abb.esValido());
    }
}
