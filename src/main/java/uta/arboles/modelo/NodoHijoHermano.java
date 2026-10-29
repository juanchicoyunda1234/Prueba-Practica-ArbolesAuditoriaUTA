package uta.arboles.modelo;

public class NodoHijoHermano {

    private final String codigo;
    private final String nombre;
    private NodoHijoHermano hijoIzquierdo;
    private NodoHijoHermano hermanoDerecho;

    public NodoHijoHermano(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public NodoHijoHermano getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoHijoHermano hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoHijoHermano getHermanoDerecho() {
        return hermanoDerecho;
    }

    public void setHermanoDerecho(NodoHijoHermano hermanoDerecho) {
        this.hermanoDerecho = hermanoDerecho;
    }
}
