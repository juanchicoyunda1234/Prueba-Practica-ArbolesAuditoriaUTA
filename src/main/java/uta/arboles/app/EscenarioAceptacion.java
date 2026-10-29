package uta.arboles.app;

import java.util.Arrays;
import java.util.List;
import uta.arboles.modelo.Estudiante;
import uta.arboles.negocio.AnalisisBusqueda;
import uta.arboles.negocio.ArbolBinarioBusqueda;
import uta.arboles.negocio.ArbolGeneral;
import uta.arboles.negocio.Auditoria;
import uta.arboles.negocio.DatosIniciales;

public final class EscenarioAceptacion {

    private EscenarioAceptacion() {
    }

    public static void ejecutar(Contexto contexto) {
        contexto.cargarObligatorios();
        ArbolGeneral general = contexto.general();
        ArbolBinarioBusqueda abb = contexto.abb();

        titulo("1. Jerarquía inicial");
        System.out.print(general.imprimirJerarquia());
        System.out.println("Unidades: " + general.numeroNodos());
        System.out.println("Hojas: " + general.numeroHojas());
        System.out.println("Altura: " + general.altura());
        System.out.println("Grado de FISEI: " + general.grado("FISEI"));
        System.out.println("Nivel de 3B: " + general.nivel("3B"));

        titulo("2. ABB tras 12 inserciones");
        System.out.println("Inorden: " + abb.inorden());
        System.out.println("Nodos: " + abb.contarNodos());
        System.out.println("Altura: " + abb.altura());

        titulo("3. Insertar de nuevo la matrícula 40");
        boolean aceptada = abb.insertar(new Estudiante(40, "Duplicado", List.of(5.0)));
        System.out.println("Inserción aceptada: " + aceptada);
        System.out.println("Nodos: " + abb.contarNodos());

        titulo("4. Modificar la matrícula 35 sin cambiar la clave");
        System.out.println("Antes:   " + abb.buscar(35));
        abb.modificarDatos(35, "Andrés Lema Pérez", List.of(9.0, 9.5));
        System.out.println("Después: " + abb.buscar(35));
        System.out.println("Inorden: " + abb.inorden());

        titulo("5. Eliminar 20 (hoja), 60 (un hijo) y 70 (dos hijos)");
        System.out.println("Eliminar 20: " + abb.eliminar(20));
        System.out.println("Eliminar 60: " + abb.eliminar(60));
        System.out.println("Eliminar 70: " + abb.eliminar(70));
        System.out.println("Inorden: " + abb.inorden());
        System.out.println("Preorden: " + abb.preorden());
        System.out.println("Nodos: " + abb.contarNodos());
        System.out.println("Altura: " + abb.altura());
        System.out.println("Invariante ABB: " + (abb.esValido() ? "se cumple" : "VIOLADO"));

        titulo("6. Eliminar FISEI con descendencia");
        String antes = general.imprimirJerarquia();
        System.out.println("Eliminación aceptada: " + general.eliminar("FISEI"));
        System.out.println("Jerarquía sin cambios: " + antes.equals(general.imprimirJerarquia()));

        titulo("7. Transformación hijo izquierdo y hermano derecho");
        System.out.print(ArbolGeneral.describirHijoHermano(general.convertirAHijoHermano()));

        titulo("8. Árboles vacíos y de un nodo");
        ArbolGeneral generalVacio = new ArbolGeneral();
        System.out.println("General vacío: altura=" + generalVacio.altura()
                + ", nodos=" + generalVacio.numeroNodos()
                + ", hojas=" + generalVacio.numeroHojas()
                + ", buscar=" + (generalVacio.buscar("UTA") == null ? "no existe" : "existe")
                + ", eliminar=" + generalVacio.eliminar("UTA"));
        ArbolGeneral generalUnico = new ArbolGeneral();
        generalUnico.crearRaiz("UTA", "Universidad Técnica de Ambato");
        System.out.println("General con raíz única: altura=" + generalUnico.altura()
                + ", nodos=" + generalUnico.numeroNodos()
                + ", hojas=" + generalUnico.numeroHojas()
                + ", eliminar raíz=" + generalUnico.eliminar("UTA"));
        ArbolBinarioBusqueda abbVacio = new ArbolBinarioBusqueda();
        System.out.println("ABB vacío: altura=" + abbVacio.altura()
                + ", nodos=" + abbVacio.contarNodos()
                + ", inorden=" + abbVacio.inorden()
                + ", buscar=" + (abbVacio.buscar(10) == null ? "no existe" : "existe")
                + ", eliminar=" + abbVacio.eliminar(10)
                + ", menor=" + (abbVacio.minimo() == null ? "N/A" : "existe")
                + ", promedio=" + abbVacio.promedioGeneral()
                + ", válido=" + abbVacio.esValido());
        ArbolBinarioBusqueda abbUnico = new ArbolBinarioBusqueda();
        abbUnico.insertar(new Estudiante(10, "Único", List.of(8.0)));
        System.out.println("ABB de un nodo: altura=" + abbUnico.altura()
                + ", nodos=" + abbUnico.contarNodos()
                + ", hojas=" + abbUnico.contarHojas());

        titulo("9. Búsqueda: cadena contra equilibrado (mismas 12 matrículas)");
        int[] ordenadas = DatosIniciales.MATRICULAS_EN_ORDEN.clone();
        Arrays.sort(ordenadas);
        int mayor = ordenadas[ordenadas.length - 1];
        System.out.print(AnalisisBusqueda.comparar(ordenadas, mayor, mayor + 5).resumen());

        titulo("10. Auditoría final");
        System.out.print(Auditoria.generar(general, abb).resumen());
        System.out.println();
        System.out.println("Los árboles quedaron en el estado final del escenario.");
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
