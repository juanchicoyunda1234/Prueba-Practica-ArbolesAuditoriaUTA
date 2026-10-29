package uta.arboles.app;

import uta.arboles.negocio.ArbolBinarioBusqueda;
import uta.arboles.negocio.ArbolGeneral;
import uta.arboles.negocio.DatosIniciales;

public class Contexto {

    private ArbolGeneral general = new ArbolGeneral();
    private ArbolBinarioBusqueda abb = new ArbolBinarioBusqueda();

    public ArbolGeneral general() {
        return general;
    }

    public ArbolBinarioBusqueda abb() {
        return abb;
    }

    public void cargarObligatorios() {
        general = DatosIniciales.crearJerarquia();
        abb = DatosIniciales.crearEstudiantes();
    }

    public void vaciar() {
        general = new ArbolGeneral();
        abb = new ArbolBinarioBusqueda();
    }
}
