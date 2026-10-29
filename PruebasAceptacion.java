package uta.arboles.pruebas;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import uta.arboles.modelo.Estudiante;
import uta.arboles.modelo.NodoHijoHermano;
import uta.arboles.modelo.UnidadAcademica;
import uta.arboles.negocio.AnalisisBusqueda;
import uta.arboles.negocio.ArbolBinarioBusqueda;
import uta.arboles.negocio.ArbolGeneral;
import uta.arboles.negocio.Auditoria;
import uta.arboles.negocio.DatosIniciales;
import uta.arboles.negocio.ReporteAuditoria;
import uta.arboles.negocio.ResultadoAnalisis;

public class PruebasAceptacion {

    private interface Caso {
        void correr() throws Exception;
    }

    private static int aprobadas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        ejecutar("01 Jerarquía inicial: 9 unidades, 5 hojas, altura 3, grado FISEI 2, nivel 3B 3",
                PruebasAceptacion::jerarquiaInicial);
        ejecutar("02 Jerarquía inválida: código duplicado rechazado y conteo preservado",
                PruebasAceptacion::codigoDuplicado);
        ejecutar("03 Jerarquía inválida: padre inexistente y datos vacíos rechazados",
                PruebasAceptacion::padreInexistente);
        ejecutar("04 Raíz única: altura 0, una hoja, no se puede eliminar ni duplicar",
                PruebasAceptacion::raizUnica);
        ejecutar("05 Árbol general vacío: valores base sin excepciones",
                PruebasAceptacion::generalVacio);
        ejecutar("06 Eliminar FISEI con descendencia: rechazo y jerarquía sin cambios",
                PruebasAceptacion::eliminarUnidadNoHoja);
        ejecutar("07 Eliminar una hoja: se permite y ajusta grado y conteos",
                PruebasAceptacion::eliminarHoja);
        ejecutar("08 Transformación hijo izquierdo / hermano derecho",
                PruebasAceptacion::transformacion);
        ejecutar("09 ABB tras 12 inserciones: inorden, nodos, altura y hojas",
                PruebasAceptacion::abbDoceInserciones);
        ejecutar("10 ABB: preorden y posorden de las 12 inserciones",
                PruebasAceptacion::abbRecorridosOriginales);
        ejecutar("11 ABB: insertar de nuevo 40 se rechaza y el conteo sigue en 12",
                PruebasAceptacion::abbDuplicado);
        ejecutar("12 ABB: modificar 35 sin alterar la clave ni la forma del árbol",
                PruebasAceptacion::abbModificar);
        ejecutar("13 ABB: eliminar 20, 60 y 70 deja el inorden esperado y 9 nodos",
                PruebasAceptacion::abbEliminarTresCasos);
        ejecutar("14 ABB: el sucesor inorden de 70 es 75 (preorden y posorden finales)",
                PruebasAceptacion::abbSucesor);
        ejecutar("15 ABB: inorden antes y después de eliminar sigue ascendente y válido",
                PruebasAceptacion::abbInordenAscendente);
        ejecutar("16 ABB vacío: valores base sin excepciones",
                PruebasAceptacion::abbVacio);
        ejecutar("17 ABB de un nodo: altura 0 y vuelve a quedar vacío al eliminarlo",
                PruebasAceptacion::abbUnNodo);
        ejecutar("18 Consistencia del promedio, máximo y mínimo de notas individuales",
                PruebasAceptacion::promedioConsistente);
        ejecutar("19 Datos inválidos: nota fuera de rango, nombre vacío y modificación atómica",
                PruebasAceptacion::datosInvalidos);
        ejecutar("20 ABB: cambiar matrícula reubica el nodo y respeta el invariante",
                PruebasAceptacion::cambiarMatricula);
        ejecutar("21 Búsqueda: cadena O(n) contra equilibrado O(h) con las mismas 12 claves",
                PruebasAceptacion::cadenaContraEquilibrado);
        ejecutar("22 Orden de inserción equilibrado: empieza por la mediana y no pierde claves",
                PruebasAceptacion::ordenEquilibrado);
        ejecutar("23 Auditoría coincide con los datos del escenario inicial y final",
                PruebasAceptacion::auditoria);

        System.out.println();
        System.out.println("Casos aprobados: " + aprobadas + " | fallidos: " + fallidas);
        if (fallidas > 0) {
            System.exit(1);
        }
    }

    private static ArbolGeneral jerarquia() {
        return DatosIniciales.crearJerarquia();
    }

    private static ArbolBinarioBusqueda estudiantes() {
        return DatosIniciales.crearEstudiantes();
    }

    private static void jerarquiaInicial() {
        ArbolGeneral arbol = jerarquia();
        igual(9, arbol.numeroNodos(), "unidades");
        igual(5, arbol.numeroHojas(), "hojas");
        igual(3, arbol.altura(), "altura");
        igual(2, arbol.grado("FISEI"), "grado de FISEI");
        igual(3, arbol.nivel("3B"), "nivel de 3B");
        igual(0, arbol.nivel("UTA"), "nivel de la raíz");
        igual(List.of("UTA", "FISEI", "SOFTWARE", "3A", "3B", "TI", "4A", "FCHE", "FCS"),
                arbol.preorden(), "preorden de códigos");
        afirmar(arbol.jerarquiaValida(), "la jerarquía inicial debe ser válida");
    }

    private static void codigoDuplicado() {
        ArbolGeneral arbol = jerarquia();
        afirmar(!arbol.agregarUnidad("UTA", "FISEI", "Otra"), "código repetido bajo el mismo padre");
        afirmar(!arbol.agregarUnidad("TI", "uta", "Intento de ciclo"), "código de la raíz bajo un descendiente");
        afirmar(!arbol.agregarUnidad("FCS", "3a", "Repetido en minúsculas"), "código repetido en otra rama");
        igual(9, arbol.numeroNodos(), "conteo preservado");
        afirmar(arbol.jerarquiaValida(), "la jerarquía sigue válida");
    }

    private static void padreInexistente() {
        ArbolGeneral arbol = jerarquia();
        afirmar(!arbol.agregarUnidad("NOEXISTE", "NUEVA", "Nueva"), "padre inexistente");
        afirmar(!arbol.agregarUnidad("UTA", "  ", "Sin código"), "código vacío");
        afirmar(!arbol.agregarUnidad("UTA", "NUEVA", ""), "nombre vacío");
        afirmar(!arbol.agregarUnidad(null, "NUEVA", "Nueva"), "padre nulo");
        igual(9, arbol.numeroNodos(), "conteo preservado");
    }

    private static void raizUnica() {
        ArbolGeneral arbol = new ArbolGeneral();
        afirmar(arbol.crearRaiz("UTA", "Universidad Técnica de Ambato"), "crear raíz");
        afirmar(!arbol.crearRaiz("OTRA", "Otra raíz"), "no se admite una segunda raíz");
        igual(0, arbol.altura(), "altura de raíz única");
        igual(1, arbol.numeroNodos(), "nodos");
        igual(1, arbol.numeroHojas(), "hojas");
        igual(0, arbol.grado("UTA"), "grado");
        afirmar(!arbol.eliminar("UTA"), "la raíz UTA no puede eliminarse");
        igual(1, arbol.numeroNodos(), "la raíz sigue presente");
    }

    private static void generalVacio() {
        ArbolGeneral arbol = new ArbolGeneral();
        afirmar(arbol.estaVacio(), "debe estar vacío");
        igual(-1, arbol.altura(), "altura del vacío");
        igual(0, arbol.numeroNodos(), "nodos");
        igual(0, arbol.numeroHojas(), "hojas");
        igual(-1, arbol.nivel("UTA"), "nivel de unidad inexistente");
        igual(-1, arbol.grado("UTA"), "grado de unidad inexistente");
        afirmar(arbol.buscar("UTA") == null, "buscar en vacío");
        afirmar(!arbol.eliminar("UTA"), "eliminar en vacío");
        afirmar(!arbol.agregarUnidad("UTA", "FISEI", "Facultad"), "agregar sin raíz");
        afirmar(arbol.convertirAHijoHermano() == null, "convertir un árbol vacío");
        afirmar(arbol.preorden().isEmpty(), "preorden vacío");
        afirmar(arbol.jerarquiaValida(), "el vacío es válido");
        afirmar(!arbol.imprimirJerarquia().isEmpty(), "imprimir no lanza excepción");
    }

    private static void eliminarUnidadNoHoja() {
        ArbolGeneral arbol = jerarquia();
        String antes = arbol.imprimirJerarquia();
        afirmar(!arbol.eliminar("FISEI"), "FISEI tiene descendencia");
        afirmar(!arbol.eliminar("SOFTWARE"), "SOFTWARE tiene descendencia");
        afirmar(!arbol.eliminar("NOEXISTE"), "unidad inexistente");
        igual(antes, arbol.imprimirJerarquia(), "jerarquía sin cambios");
        igual(9, arbol.numeroNodos(), "conteo preservado");
    }

    private static void eliminarHoja() {
        ArbolGeneral arbol = jerarquia();
        afirmar(arbol.eliminar("3a"), "3A es hoja");
        igual(8, arbol.numeroNodos(), "nodos tras eliminar");
        igual(1, arbol.grado("SOFTWARE"), "grado de SOFTWARE");
        afirmar(arbol.buscar("3A") == null, "3A ya no existe");
        afirmar(arbol.eliminar("3B"), "3B es hoja");
        afirmar(arbol.buscar("SOFTWARE").esHoja(), "SOFTWARE pasó a ser hoja");
        afirmar(arbol.eliminar("SOFTWARE"), "ahora SOFTWARE puede eliminarse");
        afirmar(arbol.jerarquiaValida(), "la jerarquía sigue válida");
    }

    private static void transformacion() {
        ArbolGeneral arbol = jerarquia();
        NodoHijoHermano raiz = arbol.convertirAHijoHermano();
        igual("UTA", raiz.getCodigo(), "raíz");
        afirmar(raiz.getHermanoDerecho() == null, "la raíz no tiene hermano");
        igual("FISEI", raiz.getHijoIzquierdo().getCodigo(), "primer hijo de UTA");
        igual("FCHE", raiz.getHijoIzquierdo().getHermanoDerecho().getCodigo(), "hermano derecho de FISEI");
        igual("FCS", raiz.getHijoIzquierdo().getHermanoDerecho().getHermanoDerecho().getCodigo(),
                "hermano derecho de FCHE");
        afirmar(raiz.getHijoIzquierdo().getHermanoDerecho().getHermanoDerecho().getHermanoDerecho() == null,
                "FCS es el último hermano");
        verificarEquivalencia(arbol.getRaiz(), raiz);
    }

    private static void verificarEquivalencia(UnidadAcademica unidad, NodoHijoHermano nodo) {
        igual(unidad.getCodigo(), nodo.getCodigo(), "código equivalente");
        NodoHijoHermano cadena = nodo.getHijoIzquierdo();
        for (UnidadAcademica hijo : unidad.getHijos()) {
            afirmar(cadena != null, "faltan hermanos para " + hijo.getCodigo());
            verificarEquivalencia(hijo, cadena);
            cadena = cadena.getHermanoDerecho();
        }
        afirmar(cadena == null, "sobran hermanos bajo " + unidad.getCodigo());
    }

    private static void abbDoceInserciones() {
        ArbolBinarioBusqueda abb = estudiantes();
        igual(List.of(20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85), abb.inorden(), "inorden");
        igual(12, abb.contarNodos(), "nodos");
        igual(3, abb.altura(), "altura");
        igual(6, abb.contarHojas(), "hojas");
        igual(20, abb.minimo().getMatricula(), "menor");
        igual(85, abb.maximo().getMatricula(), "mayor");
        afirmar(abb.esValido(), "invariante");
    }

    private static void abbRecorridosOriginales() {
        ArbolBinarioBusqueda abb = estudiantes();
        igual(List.of(50, 30, 20, 40, 35, 45, 70, 60, 65, 80, 75, 85), abb.preorden(), "preorden");
        igual(List.of(20, 35, 45, 40, 30, 65, 60, 75, 85, 80, 70, 50), abb.posorden(), "posorden");
    }

    private static void abbDuplicado() {
        ArbolBinarioBusqueda abb = estudiantes();
        Estudiante repetido = new Estudiante(40, "Duplicado", List.of(5.0));
        afirmar(!abb.insertar(repetido), "la matrícula 40 ya existe");
        igual(12, abb.contarNodos(), "conteo preservado");
        igual("Elena Paredes", abb.buscar(40).getNombre(), "el original no cambió");
        afirmar(!abb.insertar(null), "insertar nulo");
    }

    private static void abbModificar() {
        ArbolBinarioBusqueda abb = estudiantes();
        List<Integer> preordenAntes = abb.preorden();
        afirmar(abb.modificarDatos(35, "Andrés Lema Pérez", List.of(9.0, 9.5)), "modificar 35");
        Estudiante modificado = abb.buscar(35);
        igual("Andrés Lema Pérez", modificado.getNombre(), "nombre nuevo");
        igual(9.25, modificado.promedio(), "promedio nuevo");
        igual(35, modificado.getMatricula(), "la clave no cambia");
        igual(preordenAntes, abb.preorden(), "la forma del árbol no cambia");
        afirmar(!abb.modificarDatos(999, "Nadie", List.of(5.0)), "modificar inexistente");
    }

    private static void abbEliminarTresCasos() {
        ArbolBinarioBusqueda abb = estudiantes();
        afirmar(abb.eliminar(20), "eliminar la hoja 20");
        afirmar(abb.eliminar(60), "eliminar 60 con un hijo");
        afirmar(abb.eliminar(70), "eliminar 70 con dos hijos");
        igual(List.of(30, 35, 40, 45, 50, 65, 75, 80, 85), abb.inorden(), "inorden final");
        igual(9, abb.contarNodos(), "nodos finales");
        afirmar(!abb.eliminar(20), "20 ya no existe");
        afirmar(abb.buscar(20) == null && abb.buscar(60) == null && abb.buscar(70) == null,
                "las tres matrículas desaparecieron");
        afirmar(abb.esValido(), "invariante");
    }

    private static void abbSucesor() {
        ArbolBinarioBusqueda abb = estudiantes();
        abb.eliminar(20);
        abb.eliminar(60);
        abb.eliminar(70);
        igual(List.of(50, 30, 40, 35, 45, 75, 65, 80, 85), abb.preorden(), "preorden final");
        igual(List.of(35, 45, 40, 30, 65, 85, 80, 75, 50), abb.posorden(), "posorden final");
        igual(3, abb.altura(), "altura final");
        igual(4, abb.contarHojas(), "hojas finales");
        igual(30, abb.minimo().getMatricula(), "menor final");
        igual(85, abb.maximo().getMatricula(), "mayor final");
    }

    private static void abbInordenAscendente() {
        ArbolBinarioBusqueda abb = estudiantes();
        afirmar(ascendente(abb.inorden()), "inorden inicial ascendente");
        abb.eliminar(50);
        afirmar(ascendente(abb.inorden()), "inorden ascendente tras eliminar la raíz");
        afirmar(abb.esValido(), "invariante tras eliminar la raíz");
        igual(11, abb.contarNodos(), "nodos");
        abb.eliminar(30);
        abb.eliminar(80);
        afirmar(ascendente(abb.inorden()), "inorden ascendente tras más eliminaciones");
        afirmar(abb.esValido(), "invariante final");
    }

    private static boolean ascendente(List<Integer> claves) {
        for (int i = 1; i < claves.size(); i++) {
            if (claves.get(i) <= claves.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static void abbVacio() {
        ArbolBinarioBusqueda abb = new ArbolBinarioBusqueda();
        afirmar(abb.estaVacio(), "debe estar vacío");
        igual(-1, abb.altura(), "altura del vacío");
        igual(0, abb.contarNodos(), "nodos");
        igual(0, abb.contarHojas(), "hojas");
        afirmar(abb.inorden().isEmpty() && abb.preorden().isEmpty() && abb.posorden().isEmpty(),
                "recorridos vacíos");
        afirmar(abb.buscar(10) == null, "buscar en vacío");
        afirmar(!abb.eliminar(10), "eliminar en vacío");
        afirmar(abb.minimo() == null && abb.maximo() == null, "mínimo y máximo nulos");
        igual(0.0, abb.promedioGeneral(), "promedio del vacío");
        afirmar(abb.esValido(), "el vacío es válido");
        igual(0, abb.comparacionesBusqueda(10), "comparaciones en vacío");
    }

    private static void abbUnNodo() {
        ArbolBinarioBusqueda abb = new ArbolBinarioBusqueda();
        afirmar(abb.insertar(new Estudiante(10, "Único", List.of(8.0))), "insertar el único");
        igual(0, abb.altura(), "altura de un nodo");
        igual(1, abb.contarHojas(), "hojas");
        igual(10, abb.minimo().getMatricula(), "menor");
        igual(10, abb.maximo().getMatricula(), "mayor");
        igual(1, abb.comparacionesBusqueda(10), "una comparación");
        afirmar(abb.eliminar(10), "eliminar el único");
        afirmar(abb.estaVacio(), "vuelve a quedar vacío");
        igual(-1, abb.altura(), "altura tras vaciar");
    }

    private static void promedioConsistente() {
        Estudiante estudiante = new Estudiante(1, "Prueba", List.of(8.0, 9.0, 10.0));
        igual(9.0, estudiante.promedio(), "promedio aritmético");
        igual(10.0, estudiante.notaMaxima(), "nota máxima");
        igual(8.0, estudiante.notaMinima(), "nota mínima");
        estudiante.agregarNota(7.0);
        igual(8.5, estudiante.promedio(), "promedio tras agregar una nota");
        igual(7.0, estudiante.notaMinima(), "nota mínima tras agregar");
        ArbolBinarioBusqueda abb = new ArbolBinarioBusqueda();
        abb.insertar(new Estudiante(2, "A", List.of(6.0, 8.0)));
        abb.insertar(new Estudiante(1, "B", List.of(10.0)));
        igual(8.5, abb.promedioGeneral(), "promedio general de promedios 7.0 y 10.0");
        afirmar(abb.agregarNota(2, 10.0), "agregar una nota por matrícula");
        igual(8.0, abb.buscar(2).promedio(), "promedio de 6, 8 y 10");
        igual(9.0, abb.promedioGeneral(), "promedio general de promedios 8.0 y 10.0");
    }

    private static void datosInvalidos() {
        lanza(() -> new Estudiante(1, "Ana", List.of(11.0)), "nota mayor que 10");
        lanza(() -> new Estudiante(1, "Ana", List.of(-0.5)), "nota negativa");
        lanza(() -> new Estudiante(1, " ", List.of(5.0)), "nombre vacío");
        lanza(() -> new Estudiante(0, "Ana", List.of(5.0)), "matrícula no positiva");
        lanza(() -> new Estudiante(1, "Ana", List.of()), "sin notas");
        ArbolBinarioBusqueda abb = estudiantes();
        Estudiante antes = abb.buscar(40);
        String nombreAntes = antes.getNombre();
        List<Double> notasAntes = antes.getNotas();
        lanza(() -> abb.modificarDatos(40, "Nombre válido", List.of(5.0, 12.0)), "modificar con nota inválida");
        igual(nombreAntes, abb.buscar(40).getNombre(), "el nombre no cambió por una modificación fallida");
        igual(notasAntes, abb.buscar(40).getNotas(), "las notas no cambiaron");
        lanza(() -> abb.agregarNota(40, 10.5), "agregar nota fuera de rango");
    }

    private static void cambiarMatricula() {
        ArbolBinarioBusqueda abb = estudiantes();
        afirmar(abb.cambiarMatricula(35, 36), "cambiar 35 por 36");
        afirmar(abb.buscar(35) == null, "la matrícula 35 ya no existe");
        igual("Andrés Lema", abb.buscar(36).getNombre(), "los datos se conservan");
        igual(12, abb.contarNodos(), "conteo preservado");
        afirmar(abb.esValido(), "invariante");
        afirmar(!abb.cambiarMatricula(36, 50), "la nueva matrícula ya existe");
        afirmar(!abb.cambiarMatricula(999, 1000), "la matrícula actual no existe");
        afirmar(!abb.cambiarMatricula(36, 36), "mismo valor");
        afirmar(!abb.cambiarMatricula(36, -3), "matrícula nueva inválida");
        afirmar(abb.cambiarMatricula(50, 5), "cambiar la raíz por una matrícula menor");
        afirmar(ascendente(abb.inorden()) && abb.esValido(), "el árbol sigue ordenado");
    }

    private static void cadenaContraEquilibrado() {
        int[] ordenadas = DatosIniciales.MATRICULAS_EN_ORDEN.clone();
        Arrays.sort(ordenadas);
        ResultadoAnalisis resultado = AnalisisBusqueda.comparar(ordenadas, 85, 90);
        igual(12, resultado.n(), "tamaño");
        igual(11, resultado.alturaCadena(), "altura de la cadena");
        igual(3, resultado.alturaEquilibrado(), "altura del equilibrado");
        igual(12, resultado.comparacionesExistenteCadena(), "buscar 85 en la cadena");
        igual(4, resultado.comparacionesExistenteEquilibrado(), "buscar 85 en el equilibrado");
        igual(12, resultado.comparacionesInexistenteCadena(), "buscar 90 en la cadena");
        igual(4, resultado.comparacionesInexistenteEquilibrado(), "buscar 90 en el equilibrado");
        afirmar(resultado.promedioCadena() > resultado.promedioEquilibrado(),
                "el promedio de la cadena debe superar al del equilibrado");
        igual(6.5, resultado.promedioCadena(), "promedio de la cadena (n + 1) / 2");
        lanza(() -> AnalisisBusqueda.comparar(ordenadas, 86, 90), "clave existente que no está");
        lanza(() -> AnalisisBusqueda.comparar(ordenadas, 85, 80), "clave inexistente que sí está");
    }

    private static void ordenEquilibrado() {
        int[] ordenadas = {20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85};
        int[] orden = AnalisisBusqueda.ordenEquilibrado(ordenadas);
        igual(12, orden.length, "no se pierden claves");
        igual(50, orden[0], "empieza por la mediana");
        int[] copia = orden.clone();
        Arrays.sort(copia);
        afirmar(Arrays.equals(ordenadas, copia), "contiene exactamente las mismas claves");
        igual(0, AnalisisBusqueda.ordenEquilibrado(new int[0]).length, "entrada vacía");
    }

    private static void auditoria() {
        ArbolGeneral general = jerarquia();
        ArbolBinarioBusqueda abb = estudiantes();
        ReporteAuditoria inicial = Auditoria.generar(general, abb);
        igual(9, inicial.totalUnidades(), "unidades");
        igual(5, inicial.hojas(), "hojas");
        igual(3, inicial.alturaGeneral(), "altura general");
        igual(12, inicial.totalEstudiantes(), "estudiantes");
        igual(20, inicial.matriculaMenor(), "matrícula menor");
        igual(85, inicial.matriculaMayor(), "matrícula mayor");
        afirmar(inicial.abbValido() && inicial.jerarquiaValida(), "estructuras válidas");
        double suma = 0.0;
        for (Estudiante estudiante : abb.listarInorden()) {
            suma += estudiante.promedio();
        }
        casiIgual(suma / 12.0, inicial.promedioGeneral(), "promedio general");
        abb.eliminar(20);
        abb.eliminar(60);
        abb.eliminar(70);
        ReporteAuditoria posterior = Auditoria.generar(general, abb);
        igual(9, posterior.totalEstudiantes(), "estudiantes finales");
        igual(30, posterior.matriculaMenor(), "menor final");
        igual(85, posterior.matriculaMayor(), "mayor final");
        ReporteAuditoria vacio = Auditoria.generar(new ArbolGeneral(), new ArbolBinarioBusqueda());
        igual(0, vacio.totalUnidades(), "unidades en vacío");
        afirmar(vacio.matriculaMenor() == null && vacio.matriculaMayor() == null, "matrículas nulas en vacío");
        igual(-1, vacio.alturaGeneral(), "altura en vacío");
    }

    private static void ejecutar(String nombre, Caso caso) {
        try {
            caso.correr();
            aprobadas++;
            System.out.println("[OK]    " + nombre);
        } catch (Throwable error) {
            fallidas++;
            System.out.println("[FALLO] " + nombre + " -> " + error.getMessage());
        }
    }

    private static void afirmar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static void igual(Object esperado, Object actual, String mensaje) {
        if (!Objects.equals(esperado, actual)) {
            throw new AssertionError(mensaje + ": esperado=" + esperado + " actual=" + actual);
        }
    }

    private static void casiIgual(double esperado, double actual, String mensaje) {
        if (Math.abs(esperado - actual) > 1e-9) {
            throw new AssertionError(mensaje + ": esperado=" + esperado + " actual=" + actual);
        }
    }

    private static void lanza(Runnable accion, String mensaje) {
        try {
            accion.run();
        } catch (IllegalArgumentException esperado) {
            return;
        }
        throw new AssertionError("Se esperaba IllegalArgumentException: " + mensaje);
    }
}
