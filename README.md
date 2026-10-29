# Sistema de auditoría jerárquica y académica UTA

Prueba práctica grupal de **Estructura de Datos** · UTA · FISEI · Ingeniería de Software · Nivel III.
Árboles en Java (Java 17 o superior, sin librerías externas).

El sistema audita a la vez la jerarquía académica de la Facultad (**árbol general**) y la matrícula de sus estudiantes (**árbol binario de búsqueda, ABB**), con un menú de consola que opera sobre los datos que se tecleen, sin respuestas codificadas.

## Contenido

1. [Integrantes](#integrantes)
2. [El ejercicio](#el-ejercicio)
3. [Estructura del repositorio](#estructura-del-repositorio)
4. [Compilar, ejecutar y probar](#compilar-ejecutar-y-probar)
5. [Menú de consola](#menú-de-consola)
6. [Diseño y decisiones](#diseño-y-decisiones)
7. [Diagramas](#diagramas)
8. [Complejidad](#complejidad)
9. [Cobertura de pruebas](#cobertura-de-pruebas)
10. [Evidencia de ejecución y explicación](#evidencia-de-ejecución-y-explicación)
11. [Capturas de evidencia](#capturas-de-evidencia)
12. [GitHub y colaboración](#github-y-colaboración)
13. [Bibliografía](#bibliografía)

---

## Integrantes

| Integrante | Usuario GitHub | Responsabilidad asignada | Módulo asignado |
|---|---|---|---|
| Chico Yunda Juan Carlos (líder) | juanchicoyunda1234 | Liderazgo, integración final, casos de prueba, README | `negocio.Auditoria`, `negocio.AnalisisBusqueda`, `negocio.DatosIniciales`, `pruebas.PruebasAceptacion` |
| Torosina Armendariz Jeremy | WinoSpop | Documentación y diagramas | Diagramas de clases, sección de estructuras del README |
| Altamirano Segovia Jullisa | jullisaaltamirano2017-boop | Documentación e informe | `docs/evidencia`, evidencia de ejecución |
| Romo Nunez Joseph | wayusa25-cmyk | Backend: modelo | Paquete `modelo` (clases de datos y nodos) |
| Llamuca Abrajan Andres | llamucaandres161 | Backend: negocio | `negocio.ArbolGeneral`, `negocio.ArbolBinarioBusqueda` |
| Tuza Quinatoa Noemi | edithtuza15-collab | Frontend: integración | Paquete `app` (`Main`, menús de consola) |

Los aportes de cada persona se demuestran con sus propios commits y pull requests; ver [GitHub y colaboración](#github-y-colaboración).

---

## El ejercicio

**Caso integrador: Sistema de auditoría jerárquica y académica UTA.** Tiempo sugerido: 150 minutos. Calificación: 2,00 puntos (independiente del cuestionario teórico de 2,00 puntos).

### Situación problemática

La Facultad requiere auditar simultáneamente su jerarquía académica (árbol general) y la matrícula de sus estudiantes (ABB). Actualmente los árboles se administran por separado, existen operaciones incompletas y se han detectado errores relacionados con identificadores duplicados, edición de claves, eliminación con descendientes, alturas incoherentes y falta de evidencias de pruebas. Cada grupo desarrolla desde cero una solución funcional, sin copiar las implementaciones completas de los repositorios de referencia.

### Datos de entrada obligatorios

**Árbol general:** `UTA → FISEI, FCHE, FCS`; `FISEI → SOFTWARE, TI`; `SOFTWARE → 3A, 3B`; `TI → 4A`.
Reglas: los códigos de unidad son únicos; no se admiten ciclos; una unidad no raíz puede eliminarse solo si es hoja; la raíz UTA no puede eliminarse.

**ABB:** insertar en orden las matrículas `50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85`. Cada matrícula contiene nombre y promedio válido entre 0 y 10. Intentar la duplicada 40; luego modificar nombre y promedio de 35 sin cambiar la matrícula; eliminar 20 (hoja), 60 (un hijo, 65) y 70 (dos hijos, usando el sucesor inorden).

### Funcionalidades exigidas

- **Árbol general:** agregar unidad validando código único y relación padre-hijo; buscar por código; nivel; grado; altura en aristas; número de nodos y hojas; imprimir la jerarquía con sangría; eliminación segura; conversión hijo-izquierdo / hermano-derecho.
- **ABB:** insertar recursivamente, buscar, recorridos preorden, inorden y posorden, modificar sin alterar la clave, eliminar los tres casos, altura, total de nodos, hojas, mínimo, máximo y promedio por estudiante (media aritmética de las notas registradas).
- **Integración:** menú de consola con auditoría que informe total de unidades, hojas, altura general, matrícula menor y mayor, promedio y validez del invariante ABB.
- **Análisis:** contadores de comparaciones para buscar un identificador existente y uno inexistente en dos ABB del mismo tamaño: uno por inserciones ascendentes y otro con inserciones diseñadas para ser más equilibradas.
- **Pruebas automatizadas:** JUnit 5 o un ejecutable de pruebas con aserciones, mínimo 12 casos.
- **GitHub:** ramas feature por integrante, al menos una pull request revisada por otro integrante antes de fusionar, commits sustanciales y README con instrucciones, capturas o salidas y tabla de contribuciones.

### Casos de aceptación verificables

| Prueba / instrucción | Resultado esperado |
|---|---|
| Jerarquía inicial | 9 unidades; hojas FCHE, FCS, 3A, 3B, 4A (5); altura 3; grado de FISEI 2; nivel de 3B 3 |
| ABB tras 12 inserciones | Inorden: 20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85 |
| Insertar de nuevo 40 | Operación rechazada y conteo preservado (12) |
| Eliminar 20, 60 y 70 | Inorden: 30, 35, 40, 45, 50, 65, 75, 80, 85; 9 nodos |
| Eliminar FISEI con descendencia | Rechazo, jerarquía sin cambios |
| Transformación general a binario | Primer hijo de UTA = FISEI, hermano derecho de FISEI = FCHE, hermano derecho de FCHE = FCS |
| Árboles vacíos y de un nodo | Sin excepciones de referencia nula; altura del vacío -1, de un nodo 0 |
| Búsqueda ABB | Comparar el desempeño O(h) en cadena y balanceado; justificar sin inventar garantías de autoequilibrio |

### Rúbrica (2,00 puntos)

| Criterio | Puntos |
|---|---|
| Árbol general, reglas e integridad | 0,35 |
| ABB completo y correcto | 0,45 |
| Recursión, complejidad y representación | 0,30 |
| Pruebas y casos límite | 0,30 |
| Integración, arquitectura y documentación | 0,25 |
| Colaboración GitHub y defensa individual | 0,35 |

### Condiciones de evaluación

No se permite `TreeMap`, `TreeSet` ni paquetes externos que implementen internamente las operaciones pedidas; `ArrayList` solo para los hijos de un árbol general y los resultados de recorridos. Cada integrante defiende oralmente un método específico, un caso base, una prueba de fallo y la complejidad de búsqueda durante 2 a 3 minutos.

---

