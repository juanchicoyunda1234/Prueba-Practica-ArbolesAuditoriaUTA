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
}
