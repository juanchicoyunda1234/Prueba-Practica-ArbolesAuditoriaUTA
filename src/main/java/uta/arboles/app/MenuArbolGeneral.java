package uta.arboles.app;

import uta.arboles.modelo.UnidadAcademica;
import uta.arboles.negocio.ArbolGeneral;

public class MenuArbolGeneral {

    private final Consola consola;
    private final Contexto contexto;

    public MenuArbolGeneral(Consola consola, Contexto contexto) {
        this.consola = consola;
        this.contexto = contexto;
    }

    public void ejecutar() {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            System.out.println("--- Árbol general (jerarquía académica) ---");
            System.out.println("1. Crear raíz");
            System.out.println("2. Agregar unidad");
            System.out.println("3. Buscar unidad por código");
            System.out.println("4. Eliminar unidad");
            System.out.println("5. Renombrar unidad");
            System.out.println("6. Imprimir jerarquía");
            System.out.println("7. Métricas (nodos, hojas, altura)");
            System.out.println("8. Conversión hijo izquierdo / hermano derecho");
            System.out.println("0. Volver");
            Integer opcion = consola.leerEntero("Opción: ");
            if (opcion == null) {
                continue;
            }
            try {
                switch (opcion) {
                    case 1 -> crearRaiz();
                    case 2 -> agregarUnidad();
                    case 3 -> buscarUnidad();
                    case 4 -> eliminarUnidad();
                    case 5 -> renombrarUnidad();
                    case 6 -> System.out.print(contexto.general().imprimirJerarquia());
                    case 7 -> mostrarMetricas();
                    case 8 -> System.out.print(
                            ArbolGeneral.describirHijoHermano(contexto.general().convertirAHijoHermano()));
                    case 0 -> volver = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void crearRaiz() {
        String codigo = consola.leerTexto("Código de la raíz: ");
        String nombre = consola.leerTexto("Nombre de la raíz: ");
        boolean creada = contexto.general().crearRaiz(codigo, nombre);
        System.out.println(creada ? "Raíz creada."
                : "Rechazado: ya existe una raíz o hay datos vacíos.");
    }

    private void agregarUnidad() {
        String padre = consola.leerTexto("Código del padre: ");
        String codigo = consola.leerTexto("Código de la nueva unidad: ");
        String nombre = consola.leerTexto("Nombre de la nueva unidad: ");
        boolean agregada = contexto.general().agregarUnidad(padre, codigo, nombre);
        System.out.println(agregada ? "Unidad agregada."
                : "Rechazado: el padre no existe, el código ya está en uso o hay datos vacíos.");
    }

    private void buscarUnidad() {
        String codigo = consola.leerTexto("Código a buscar: ");
        ArbolGeneral general = contexto.general();
        UnidadAcademica unidad = general.buscar(codigo);
        if (unidad == null) {
            System.out.println("No existe una unidad con ese código.");
            return;
        }
        System.out.println(unidad);
        System.out.println("Padre: " + (unidad.getPadre() == null ? "(es la raíz)" : unidad.getPadre().getCodigo()));
        System.out.println("Nivel: " + general.nivel(codigo));
        System.out.println("Grado: " + general.grado(codigo));
        System.out.println("Altura del subárbol: " + general.alturaDe(codigo));
        System.out.println(unidad.esHoja() ? "Es hoja." : "No es hoja.");
    }

    private void eliminarUnidad() {
        String codigo = consola.leerTexto("Código a eliminar: ");
        boolean eliminada = contexto.general().eliminar(codigo);
        System.out.println(eliminada ? "Unidad eliminada."
                : "Rechazado: la unidad no existe, es la raíz o tiene descendientes.");
    }

    private void renombrarUnidad() {
        String codigo = consola.leerTexto("Código de la unidad: ");
        String nombre = consola.leerTexto("Nuevo nombre: ");
        boolean renombrada = contexto.general().renombrar(codigo, nombre);
        System.out.println(renombrada ? "Unidad renombrada."
                : "Rechazado: la unidad no existe o el nombre está vacío.");
    }

    private void mostrarMetricas() {
        ArbolGeneral general = contexto.general();
        System.out.println("Nodos: " + general.numeroNodos());
        System.out.println("Hojas: " + general.numeroHojas());
        System.out.println("Altura (aristas): " + general.altura());
        System.out.println("Jerarquía válida: " + (general.jerarquiaValida() ? "sí" : "NO"));
    }
}
