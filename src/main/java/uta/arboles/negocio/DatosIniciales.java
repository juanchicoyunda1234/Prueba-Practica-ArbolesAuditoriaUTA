package uta.arboles.negocio;

import java.util.List;
import uta.arboles.modelo.Estudiante;

public final class DatosIniciales {

    public static final int[] MATRICULAS_EN_ORDEN = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};

    private static final String[] NOMBRES = {
        "Ana Torres", "Luis Mena", "Carla Ruiz", "Diego Salas", "Elena Paredes", "Mateo Vera",
        "Sofía Cando", "Andrés Lema", "Paula Naranjo", "Jorge Tapia", "Marcela Guevara", "Daniel Chiluisa"
    };

    private static final List<List<Double>> NOTAS = List.of(
            List.of(8.5, 9.0, 7.5),
            List.of(7.0, 8.0, 9.0),
            List.of(9.5, 9.0, 10.0),
            List.of(6.5, 7.0, 8.0),
            List.of(8.0, 8.5),
            List.of(7.5, 7.0, 6.5),
            List.of(9.0, 9.5),
            List.of(8.0, 7.5, 8.5),
            List.of(6.0, 7.5, 7.0),
            List.of(8.5, 8.0, 9.0),
            List.of(7.0, 7.5),
            List.of(9.0, 8.5, 10.0));

    private DatosIniciales() {
    }

    public static ArbolGeneral crearJerarquia() {
        ArbolGeneral arbol = new ArbolGeneral();
        arbol.crearRaiz("UTA", "Universidad Técnica de Ambato");
        arbol.agregarUnidad("UTA", "FISEI", "Facultad de Ingeniería en Sistemas, Electrónica e Industrial");
        arbol.agregarUnidad("UTA", "FCHE", "Facultad de Ciencias Humanas y de la Educación");
        arbol.agregarUnidad("UTA", "FCS", "Facultad de Ciencias de la Salud");
        arbol.agregarUnidad("FISEI", "SOFTWARE", "Carrera de Software");
        arbol.agregarUnidad("FISEI", "TI", "Carrera de Tecnologías de la Información");
        arbol.agregarUnidad("SOFTWARE", "3A", "Software tercer nivel paralelo A");
        arbol.agregarUnidad("SOFTWARE", "3B", "Software tercer nivel paralelo B");
        arbol.agregarUnidad("TI", "4A", "Tecnologías de la Información cuarto nivel paralelo A");
        return arbol;
    }

    public static ArbolBinarioBusqueda crearEstudiantes() {
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();
        for (int i = 0; i < MATRICULAS_EN_ORDEN.length; i++) {
            arbol.insertar(new Estudiante(MATRICULAS_EN_ORDEN[i], NOMBRES[i], NOTAS.get(i)));
        }
        return arbol;
    }

    public static Estudiante crearEstudianteObligatorio(int matricula) {
        for (int i = 0; i < MATRICULAS_EN_ORDEN.length; i++) {
            if (MATRICULAS_EN_ORDEN[i] == matricula) {
                return new Estudiante(matricula, NOMBRES[i], NOTAS.get(i));
            }
        }
        return null;
    }
}
