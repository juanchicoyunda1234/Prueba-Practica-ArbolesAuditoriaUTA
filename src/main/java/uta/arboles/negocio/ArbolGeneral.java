package uta.arboles.negocio;

import java.util.ArrayList;
import uta.arboles.modelo.NodoHijoHermano;
import uta.arboles.modelo.UnidadAcademica;

public class ArbolGeneral {

    private UnidadAcademica raiz;

    public boolean estaVacio() {
        return raiz == null;
    }

    public UnidadAcademica getRaiz() {
        return raiz;
    }

    public boolean crearRaiz(String codigo, String nombre) {
        String codigoNormalizado = normalizarCodigo(codigo);
        String nombreNormalizado = normalizarNombre(nombre);
        if (raiz != null || codigoNormalizado == null || nombreNormalizado == null) {
            return false;
        }
        raiz = new UnidadAcademica(codigoNormalizado, nombreNormalizado);
        return true;
    }

    public boolean agregarUnidad(String codigoPadre, String codigo, String nombre) {
        String padreNormalizado = normalizarCodigo(codigoPadre);
        String codigoNormalizado = normalizarCodigo(codigo);
        String nombreNormalizado = normalizarNombre(nombre);
        if (padreNormalizado == null || codigoNormalizado == null || nombreNormalizado == null) {
            return false;
        }
        UnidadAcademica padre = buscar(raiz, padreNormalizado);
        if (padre == null) {
            return false;
        }
        if (buscar(raiz, codigoNormalizado) != null) {
            return false;
        }
        UnidadAcademica nueva = new UnidadAcademica(codigoNormalizado, nombreNormalizado);
        nueva.setPadre(padre);
        padre.getHijos().add(nueva);
        return true;
    }

    public UnidadAcademica buscar(String codigo) {
        String codigoNormalizado = normalizarCodigo(codigo);
        if (codigoNormalizado == null) {
            return null;
        }
        return buscar(raiz, codigoNormalizado);
    }

    public int nivel(String codigo) {
        UnidadAcademica unidad = buscar(codigo);
        if (unidad == null) {
            return -1;
        }
        int nivel = 0;
        UnidadAcademica actual = unidad.getPadre();
        while (actual != null) {
            nivel++;
            actual = actual.getPadre();
        }
        return nivel;
    }

    public int grado(String codigo) {
        UnidadAcademica unidad = buscar(codigo);
        if (unidad == null) {
            return -1;
        }
        return unidad.getHijos().size();
    }

    public int altura() {
        return altura(raiz);
    }

    public int alturaDe(String codigo) {
        UnidadAcademica unidad = buscar(codigo);
        if (unidad == null) {
            return -1;
        }
        return altura(unidad);
    }

    public int numeroNodos() {
        return contarNodos(raiz);
    }

    public int numeroHojas() {
        return contarHojas(raiz);
    }

    public boolean eliminar(String codigo) {
        UnidadAcademica unidad = buscar(codigo);
        if (unidad == null || unidad == raiz || !unidad.esHoja()) {
            return false;
        }
        unidad.getPadre().getHijos().remove(unidad);
        unidad.setPadre(null);
        return true;
    }

    public boolean renombrar(String codigo, String nuevoNombre) {
        UnidadAcademica unidad = buscar(codigo);
        String nombreNormalizado = normalizarNombre(nuevoNombre);
        if (unidad == null || nombreNormalizado == null) {
            return false;
        }
        unidad.setNombre(nombreNormalizado);
        return true;
    }

    public String imprimirJerarquia() {
        if (raiz == null) {
            return "(árbol vacío)\n";
        }
        StringBuilder salida = new StringBuilder();
        imprimir(raiz, 0, salida);
        return salida.toString();
    }

    public ArrayList<String> preorden() {
        ArrayList<String> codigos = new ArrayList<>();
        preorden(raiz, codigos);
        return codigos;
    }

    public boolean jerarquiaValida() {
        return validar(raiz, null, new ArrayList<>());
    }

    public NodoHijoHermano convertirAHijoHermano() {
        if (raiz == null) {
            return null;
        }
        return convertir(raiz);
    }

    public static String describirHijoHermano(NodoHijoHermano raizBinaria) {
        if (raizBinaria == null) {
            return "(árbol vacío)\n";
        }
        StringBuilder salida = new StringBuilder();
        describir(raizBinaria, salida);
        return salida.toString();
    }

    private UnidadAcademica buscar(UnidadAcademica actual, String codigo) {
        if (actual == null) {
            return null;
        }
        if (actual.getCodigo().equals(codigo)) {
            return actual;
        }
        for (UnidadAcademica hijo : actual.getHijos()) {
            UnidadAcademica encontrada = buscar(hijo, codigo);
            if (encontrada != null) {
                return encontrada;
            }
        }
        return null;
    }

    private int altura(UnidadAcademica actual) {
        if (actual == null) {
            return -1;
        }
        int mayor = -1;
        for (UnidadAcademica hijo : actual.getHijos()) {
            mayor = Math.max(mayor, altura(hijo));
        }
        return mayor + 1;
    }

    private int contarNodos(UnidadAcademica actual) {
        if (actual == null) {
            return 0;
        }
        int total = 1;
        for (UnidadAcademica hijo : actual.getHijos()) {
            total += contarNodos(hijo);
        }
        return total;
    }

    private int contarHojas(UnidadAcademica actual) {
        if (actual == null) {
            return 0;
        }
        if (actual.esHoja()) {
            return 1;
        }
        int total = 0;
        for (UnidadAcademica hijo : actual.getHijos()) {
            total += contarHojas(hijo);
        }
        return total;
    }

    private void imprimir(UnidadAcademica actual, int nivel, StringBuilder salida) {
        for (int i = 0; i < nivel; i++) {
            salida.append("  ");
        }
        salida.append(actual).append('\n');
        for (UnidadAcademica hijo : actual.getHijos()) {
            imprimir(hijo, nivel + 1, salida);
        }
    }

    private void preorden(UnidadAcademica actual, ArrayList<String> codigos) {
        if (actual == null) {
            return;
        }
        codigos.add(actual.getCodigo());
        for (UnidadAcademica hijo : actual.getHijos()) {
            preorden(hijo, codigos);
        }
    }

    private boolean validar(UnidadAcademica actual, UnidadAcademica padreEsperado, ArrayList<String> vistos) {
        if (actual == null) {
            return true;
        }
        if (actual.getPadre() != padreEsperado || vistos.contains(actual.getCodigo())) {
            return false;
        }
        vistos.add(actual.getCodigo());
        for (UnidadAcademica hijo : actual.getHijos()) {
            if (!validar(hijo, actual, vistos)) {
                return false;
            }
        }
        return true;
    }

    private NodoHijoHermano convertir(UnidadAcademica unidad) {
        NodoHijoHermano nodo = new NodoHijoHermano(unidad.getCodigo(), unidad.getNombre());
        NodoHijoHermano anterior = null;
        for (UnidadAcademica hijo : unidad.getHijos()) {
            NodoHijoHermano convertido = convertir(hijo);
            if (anterior == null) {
                nodo.setHijoIzquierdo(convertido);
            } else {
                anterior.setHermanoDerecho(convertido);
            }
            anterior = convertido;
        }
        return nodo;
    }

    private static void describir(NodoHijoHermano actual, StringBuilder salida) {
        if (actual == null) {
            return;
        }
        String hijo = actual.getHijoIzquierdo() == null ? "-" : actual.getHijoIzquierdo().getCodigo();
        String hermano = actual.getHermanoDerecho() == null ? "-" : actual.getHermanoDerecho().getCodigo();
        salida.append(actual.getCodigo())
                .append(" | hijo izquierdo: ").append(hijo)
                .append(" | hermano derecho: ").append(hermano)
                .append('\n');
        describir(actual.getHijoIzquierdo(), salida);
        describir(actual.getHermanoDerecho(), salida);
    }

    private static String normalizarCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        return codigo.trim().toUpperCase();
    }

    private static String normalizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        return nombre.trim();
    }
}
