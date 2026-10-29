package uta.arboles.modelo;

public class NodoABB {

    private Estudiante dato;
    private NodoABB izquierdo;
    private NodoABB derecho;

    public NodoABB(Estudiante dato) {
        this.dato = dato;
    }

    public Estudiante getDato() {
        return dato;
    }

    public void setDato(Estudiante dato) {
        this.dato = dato;
    }

    public NodoABB getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoABB izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoABB getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoABB derecho) {
        this.derecho = derecho;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
}
