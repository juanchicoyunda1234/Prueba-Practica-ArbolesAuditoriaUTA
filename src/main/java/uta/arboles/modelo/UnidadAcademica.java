package uta.arboles.modelo;

import java.util.ArrayList;

public class UnidadAcademica {

    private final String codigo;
    private String nombre;
    private UnidadAcademica padre;
    private final ArrayList<UnidadAcademica> hijos = new ArrayList<>();

    public UnidadAcademica(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadAcademica getPadre() {
        return padre;
    }

    public void setPadre(UnidadAcademica padre) {
        this.padre = padre;
    }

    public ArrayList<UnidadAcademica> getHijos() {
        return hijos;
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    @Override
    public String toString() {
        return codigo + " (" + nombre + ")";
    }
}
