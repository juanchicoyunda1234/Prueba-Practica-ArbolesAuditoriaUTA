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
}
