package uta.arboles.negocio;

import java.util.ArrayList;
import java.util.List;
import uta.arboles.modelo.Estudiante;
import uta.arboles.modelo.NodoABB;

public class ArbolBinarioBusqueda {

    private NodoABB raiz;

    public boolean estaVacio() {
        return raiz == null;
    }

    public boolean insertar(Estudiante estudiante) {
        if (estudiante == null) {
            return false;
        }
        if (raiz == null) {
            raiz = new NodoABB(estudiante);
            return true;
        }
        return insertar(raiz, estudiante);
    }

    public Estudiante buscar(int matricula) {
        return buscar(raiz, matricula);
    }

    public int comparacionesBusqueda(int matricula) {
        return contarComparaciones(raiz, matricula);
    }

    public boolean modificarDatos(int matricula, String nuevoNombre, List<Double> nuevasNotas) {
        Estudiante estudiante = buscar(matricula);
        if (estudiante == null) {
            return false;
        }
        estudiante.actualizar(nuevoNombre, nuevasNotas);
        return true;
    }

    public boolean agregarNota(int matricula, double nota) {
        Estudiante estudiante = buscar(matricula);
        if (estudiante == null) {
            return false;
        }
        estudiante.agregarNota(nota);
        return true;
    }

    public boolean cambiarMatricula(int matriculaActual, int matriculaNueva) {
        if (matriculaActual == matriculaNueva || matriculaNueva <= 0) {
            return false;
        }
        Estudiante actual = buscar(matriculaActual);
        if (actual == null || buscar(matriculaNueva) != null) {
            return false;
        }
        Estudiante reubicado = new Estudiante(matriculaNueva, actual.getNombre(), actual.getNotas());
        eliminar(matriculaActual);
        return insertar(reubicado);
    }

    public boolean eliminar(int matricula) {
        if (buscar(matricula) == null) {
            return false;
        }
        raiz = eliminar(raiz, matricula);
        return true;
    }

    public ArrayList<Integer> preorden() {
        ArrayList<Integer> claves = new ArrayList<>();
        preorden(raiz, claves);
        return claves;
    }

    public ArrayList<Integer> inorden() {
        ArrayList<Integer> claves = new ArrayList<>();
        inorden(raiz, claves);
        return claves;
    }

    public ArrayList<Integer> posorden() {
        ArrayList<Integer> claves = new ArrayList<>();
        posorden(raiz, claves);
        return claves;
    }

    public ArrayList<Estudiante> listarInorden() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        listarInorden(raiz, estudiantes);
        return estudiantes;
    }

    public int altura() {
        return altura(raiz);
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    public Estudiante minimo() {
        if (raiz == null) {
            return null;
        }
        return minimoNodo(raiz).getDato();
    }

    public Estudiante maximo() {
        if (raiz == null) {
            return null;
        }
        return maximoNodo(raiz).getDato();
    }

    public double promedioGeneral() {
        int total = contarNodos(raiz);
        if (total == 0) {
            return 0.0;
        }
        return sumarPromedios(raiz) / total;
    }

    public boolean esValido() {
        return validar(raiz, null, null);
    }

    private boolean insertar(NodoABB actual, Estudiante estudiante) {
        int comparacion = Integer.compare(estudiante.getMatricula(), actual.getDato().getMatricula());
        if (comparacion == 0) {
            return false;
        }
        if (comparacion < 0) {
            if (actual.getIzquierdo() == null) {
                actual.setIzquierdo(new NodoABB(estudiante));
                return true;
            }
            return insertar(actual.getIzquierdo(), estudiante);
        }
        if (actual.getDerecho() == null) {
            actual.setDerecho(new NodoABB(estudiante));
            return true;
        }
        return insertar(actual.getDerecho(), estudiante);
    }

    private Estudiante buscar(NodoABB actual, int matricula) {
        if (actual == null) {
            return null;
        }
        int comparacion = Integer.compare(matricula, actual.getDato().getMatricula());
        if (comparacion == 0) {
            return actual.getDato();
        }
        if (comparacion < 0) {
            return buscar(actual.getIzquierdo(), matricula);
        }
        return buscar(actual.getDerecho(), matricula);
    }

    private int contarComparaciones(NodoABB actual, int matricula) {
        if (actual == null) {
            return 0;
        }
        int comparacion = Integer.compare(matricula, actual.getDato().getMatricula());
        if (comparacion == 0) {
            return 1;
        }
        if (comparacion < 0) {
            return 1 + contarComparaciones(actual.getIzquierdo(), matricula);
        }
        return 1 + contarComparaciones(actual.getDerecho(), matricula);
    }

    private NodoABB eliminar(NodoABB actual, int matricula) {
        if (actual == null) {
            return null;
        }
        int comparacion = Integer.compare(matricula, actual.getDato().getMatricula());
        if (comparacion < 0) {
            actual.setIzquierdo(eliminar(actual.getIzquierdo(), matricula));
            return actual;
        }
        if (comparacion > 0) {
            actual.setDerecho(eliminar(actual.getDerecho(), matricula));
            return actual;
        }
        if (actual.getIzquierdo() == null) {
            return actual.getDerecho();
        }
        if (actual.getDerecho() == null) {
            return actual.getIzquierdo();
        }
        NodoABB sucesor = minimoNodo(actual.getDerecho());
        actual.setDato(sucesor.getDato());
        actual.setDerecho(eliminar(actual.getDerecho(), sucesor.getDato().getMatricula()));
        return actual;
    }

    private NodoABB minimoNodo(NodoABB actual) {
        if (actual.getIzquierdo() == null) {
            return actual;
        }
        return minimoNodo(actual.getIzquierdo());
    }

    private NodoABB maximoNodo(NodoABB actual) {
        if (actual.getDerecho() == null) {
            return actual;
        }
        return maximoNodo(actual.getDerecho());
    }

    private void preorden(NodoABB actual, ArrayList<Integer> claves) {
        if (actual == null) {
            return;
        }
        claves.add(actual.getDato().getMatricula());
        preorden(actual.getIzquierdo(), claves);
        preorden(actual.getDerecho(), claves);
    }

    private void inorden(NodoABB actual, ArrayList<Integer> claves) {
        if (actual == null) {
            return;
        }
        inorden(actual.getIzquierdo(), claves);
        claves.add(actual.getDato().getMatricula());
        inorden(actual.getDerecho(), claves);
    }

    private void posorden(NodoABB actual, ArrayList<Integer> claves) {
        if (actual == null) {
            return;
        }
        posorden(actual.getIzquierdo(), claves);
        posorden(actual.getDerecho(), claves);
        claves.add(actual.getDato().getMatricula());
    }

    private void listarInorden(NodoABB actual, ArrayList<Estudiante> estudiantes) {
        if (actual == null) {
            return;
        }
        listarInorden(actual.getIzquierdo(), estudiantes);
        estudiantes.add(actual.getDato());
        listarInorden(actual.getDerecho(), estudiantes);
    }

    private int altura(NodoABB actual) {
        if (actual == null) {
            return -1;
        }
        return 1 + Math.max(altura(actual.getIzquierdo()), altura(actual.getDerecho()));
    }

    private int contarNodos(NodoABB actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + contarNodos(actual.getIzquierdo()) + contarNodos(actual.getDerecho());
    }

    private int contarHojas(NodoABB actual) {
        if (actual == null) {
            return 0;
        }
        if (actual.esHoja()) {
            return 1;
        }
        return contarHojas(actual.getIzquierdo()) + contarHojas(actual.getDerecho());
    }

    private double sumarPromedios(NodoABB actual) {
        if (actual == null) {
            return 0.0;
        }
        return actual.getDato().promedio()
                + sumarPromedios(actual.getIzquierdo())
                + sumarPromedios(actual.getDerecho());
    }

    private boolean validar(NodoABB actual, Integer limiteInferior, Integer limiteSuperior) {
        if (actual == null) {
            return true;
        }
        int clave = actual.getDato().getMatricula();
        if (limiteInferior != null && clave <= limiteInferior) {
            return false;
        }
        if (limiteSuperior != null && clave >= limiteSuperior) {
            return false;
        }
        return validar(actual.getIzquierdo(), limiteInferior, clave)
                && validar(actual.getDerecho(), clave, limiteSuperior);
    }
}
