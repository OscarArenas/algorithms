/*
 * Copyright (C) 2019 Oscar Arenas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.oharenas.util;

import com.oharenas.comun.Empleado;
import com.oharenas.comun.Estudiante;
import com.oharenas.comun.Persona;
import com.oharenas.ed.lineal.listas.IntegerArrayList;
import com.oharenas.ed.lineal.listas.DoubleArrayList;
import com.oharenas.ed.lineal.listas.DoubleLinkedList;
import com.oharenas.ed.lineal.listas.IntegerLinkedList;
import com.oharenas.math.Matematica;
import com.oharenas.math.MatrizRacional;
import java.util.ArrayList;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Contiene algunos métodos que operan sobre vectores y matrices.
 *
 * @author Oscar Arenas
 */
public class Arreglo {

    public static final int JUSTIFICAR_DERECHA = 0;
    public static final int JUSTIFICAR_IZQUIERDA = 1;

    public static int[] generarConsecutivo(int n, int valorInicial) {
        return generarConsecutivo(n, valorInicial, 1);
    }

    public static int[] generarConsecutivo(int n, int valorInicial, int paso) {
        int[] vector = new int[n];

        for (int i = 0; i < n; i++) {
            vector[i] = valorInicial;
            valorInicial += paso;
        }
        return vector;
    }

    public static boolean tieneRepetidos(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            for (int j = i + 1; j < vector.length; j++) {
                if (vector[i] == vector[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int busquedaBinaria(int valor, int[] datos) {
        int bajo = 0;
        int alto = datos.length - 1;

        while (bajo <= alto) {
            int central = bajo + (alto - bajo) / 2;

            if (valor < datos[central]) {
                alto = central - 1;
            } else if (valor > datos[central]) {
                bajo = central + 1;
            } else {
                return central;
            }
        }
        return -1;
    }

    public static int[] mergeSort(int[] datos) {
        return mergeSort(datos, 0, datos.length - 1);
    }

    private static int[] mergeSort(int[] datos, int bajo, int alto) {
        if (bajo == alto) {
            return new int[]{datos[bajo]};
        } else {
            int central = bajo + (alto - bajo) / 2;
            return merge(mergeSort(datos, bajo, central),
                    mergeSort(datos, central + 1, alto));
        }
    }

    private static int[] merge(int[] a, int[] b) {
        int[] nuevoVector = new int[a.length + b.length];
        int i = 0, j = 0;
        for (int k = 0; k < nuevoVector.length; k++) {
            if (j == b.length || (i < a.length && a[i] < b[j])) {
                nuevoVector[k] = a[i++];
            } else {
                nuevoVector[k] = b[j++];
            }
        }
        return nuevoVector;
    }

    private static int[] auxiliar;

    public static void mergeSort2(int[] datos) {
        auxiliar = new int[datos.length];

        mergeSort2(datos, 0, datos.length - 1);
        auxiliar = null;
    }

    private static void mergeSort2(int[] datos, int bajo, int alto) {
        if (bajo < alto) {
            int mitad = bajo + (alto - bajo) / 2;

            mergeSort2(datos, bajo, mitad);
            mergeSort2(datos, mitad + 1, alto);

            merge2(datos, bajo, mitad, alto);
        }
    }
//    assert isSorted(a, bajo, mitad);     // precondition: a[bajo..mitad] sorted
//        assert isSorted(a, mitad + 1, alto); // precondition: a[mitad+1..alto] sorted

    private static void merge2(int[] datos, int bajo, int mitad, int alto) {
        int i = bajo, j = mitad + 1;
        for (int k = bajo; k <= alto; k++) {
            auxiliar[k] = datos[k];
        }
        for (int k = bajo; k <= alto; k++) {
            if (i > mitad) {
                datos[k] = auxiliar[j++];
            } else if (j > alto) {
                datos[k] = auxiliar[i++];
            } else if (auxiliar[j] < auxiliar[i]) {
                datos[k] = auxiliar[j++];
            } else {
                datos[k] = auxiliar[i++];
            }
        }
    }

    public static void mergesort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length);
    }

    private static void sort(Comparable[] a, Comparable[] aux,
            int lo, int hi) { // Sort a[lo, hi).
        if (hi - lo <= 1) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid, hi);
        int i = lo, j = mid;
        for (int k = lo; k < hi; k++) {
            if (i == mid) {
                aux[k] = a[j++];
            } else if (j == hi) {
                aux[k] = a[i++];
            } else if (a[j].compareTo(a[i]) < 0) {
                aux[k] = a[j++];
            } else {
                aux[k] = a[i++];
            }
        }
        for (int k = lo; k < hi; k++) {
            a[k] = aux[k];
        }
    }

    public static void quickSort(int[] datos) {
        quickSort(datos, 0, datos.length - 1);
    }

    private static void quickSort(int[] datos, int bajo, int alto) {
        if (bajo < alto) {
            int posicionPivote = particion(datos, bajo, alto);
            quickSort(datos, bajo, posicionPivote - 1);
            quickSort(datos, posicionPivote + 1, alto);
        }
    }

    /**
     * Método iterativo (no recursivo): Elige un elemento del arreglo data que
     * está en la región entre bajo y alto, inclusive, como el pivote. Ordena
     * los números de modo que los que son menores o igual que el pivote quedan
     * a la izquierda de éste y los *mayores quedan a la derecha. Retorna la
     * posición final del pivote.
     *
     * @param datos
     * @param bajo
     * @param alto
     * @return
     */
    private static int particion(int[] datos, int bajo, int alto) {
        int pivote = datos[alto];
        int posicionPivote = bajo;
        for (int i = bajo; i < alto; i++) {
            if (datos[i] <= pivote) {
                intercambiar(datos, posicionPivote, i);
                posicionPivote++;
            }
        }
        intercambiar(datos, posicionPivote, alto);
        return posicionPivote;
    }

    public static void quickSort2(int[] datos) {
        quickSort2(datos, 0, datos.length - 1);
    }

    private static void quickSort2(int[] a, int lo, int hi) {
        if (lo < hi) {
            int j = partition(a, lo, hi); // Partition (see page 291).
            quickSort2(a, lo, j - 1); // Sort left part a[lo .. j-1].
            quickSort2(a, j + 1, hi); // Sort right part a[j+1 .. hi].
        }
    }

    private static int partition(int[] a, int lo, int hi) { // Partition into a[lo..i-1], a[i], a[i+1..hi].
        int i = lo, j = hi + 1; // left and right scan indices
        int v = a[lo]; // partitioning item
        while (true) { // Scan right, scan left, check for scan complete, and exchange.
            while (a[++i] < v) {
                if (i == hi) {
                    break;
                }
            }
            while ((v < a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            intercambiar(a, i, j);
        }
        intercambiar(a, lo, j); // Put v = a[j] into position
        return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
    }

    public static void intercambiar(int[] datos, int i, int j) {
        int temp = datos[i];
        datos[i] = datos[j];
        datos[j] = temp;
    }

    public static double[][] multiplicarMatrices(double[][] matriz1, double[][] matriz2) {
        MatrizRacional mat1 = new MatrizRacional(matriz1);
        MatrizRacional mat2 = new MatrizRacional(matriz2);

        MatrizRacional ans = mat1.mult(mat2);

        if (ans != null) {
            return ans.getMatriz();
        }
        return null;
    }

    public static double[][] sumarMatrices(double[][] matriz1, double[][] matriz2) {
        MatrizRacional mat1 = new MatrizRacional(matriz1);
        MatrizRacional mat2 = new MatrizRacional(matriz2);

        MatrizRacional ans = mat1.sumar(mat2);

        if (ans != null) {
            return ans.getMatriz();
        }
        return null;
    }

    public static double[][] restarMatrices(double[][] matriz1, double[][] matriz2) {
        MatrizRacional mat1 = new MatrizRacional(matriz1);
        MatrizRacional mat2 = new MatrizRacional(matriz2);

        MatrizRacional ans = mat1.restar(mat2);

        if (ans != null) {
            return ans.getMatriz();
        }
        return null;
    }

    public static double determinante(double[][] matriz) {
        MatrizRacional mat = new MatrizRacional(matriz);
        return mat.det();
    }

    public static String[][] transpuesta(String[][] matriz) {
        String[][] auxiliar = new String[matriz[0].length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                auxiliar[j][i] = matriz[i][j];
            }
        }
        return auxiliar;
    }

    public static int[][] transpuesta(int[][] matriz) {
        int[][] auxiliar = new int[matriz[0].length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                auxiliar[j][i] = matriz[i][j];
            }
        }
        return auxiliar;
    }

    public static double[][] transpuesta(double[][] matriz) {
        double[][] auxiliar = new double[matriz[0].length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                auxiliar[j][i] = matriz[i][j];
            }
        }
        return auxiliar;
    }

    public static double[] generar(double a, double b, double delta) {
        if (delta == 0.0) {
            System.out.println("Lanzar excepcion");
            return null;
        }
        delta = Math.abs(delta);
        double ancho = (b - a) / delta;

        String ct = delta + "";
        String[] div = ct.split("\\.");
        int mayorLongitudDecimales = div[1].length();

        ct = a + "";
        div = ct.split("\\.");

        int longitud = div[1].length();
        if (longitud > mayorLongitudDecimales) {
            mayorLongitudDecimales = longitud;
        }
        if (a > b) {
            delta = -delta;
            ancho = -ancho;
        }

        int size = 1 + (int) ancho;

        double[] vector = new double[size];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Matematica.redondear(a + i * delta, mayorLongitudDecimales);

        }
        return vector;
    }

    /**
     * Genera un vector con los nombres de los días de la semana. Los nombres
     * estan ordenados de lunes a domingo.
     *
     * @return Retorna un vector con los nombres de los días de la semana.
     */
    public static String[] obtenerDias() {
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        return dias;
    }

    /**
     * Copia los valores enteros de datos en un matriz de tipo String.
     *
     * @param datos Matriz de tipo entero.
     * @return Una matriz de tipo String con los valores de datos.
     */
    public static String[][] convertirAMatrizString(int[][] datos) {
        String[][] cadena = new String[datos.length][datos[0].length];
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                cadena[i][j] = datos[i][j] + "";
            }
        }
        return cadena;
    }

    public static String[][] convertirAMatrizString(byte[][] datos) {
        String[][] cadena = new String[datos.length][datos[0].length];
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                cadena[i][j] = datos[i][j] + "";
            }
        }
        return cadena;
    }

    /**
     * Copia los valores reales de datos en un matriz de tipo String.
     *
     * @param datos Matriz de tipo real.
     * @return Una matriz de tipo String con los valores de datos.
     */
    public static String[][] convertirAMatrizString(double[][] datos) {
        String[][] cadena = new String[datos.length][datos[0].length];
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                cadena[i][j] = datos[i][j] + "";
            }
        }
        return cadena;
    }

    public static String[][] convertirAMatrizString(char[][] datos) {
        String[][] cadena = new String[datos.length][datos[0].length];
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                cadena[i][j] = datos[i][j] + "";
            }
        }
        return cadena;
    }

    public static String[][] convertirAMatrizString(int[] datos) {
        String[][] cadena = new String[1][datos.length];

        for (int i = 0; i < datos.length; i++) {
            cadena[0][i] = datos[i] + "";
        }
        return cadena;
    }

    public static String[][] convertirAMatrizString(boolean[] datos) {
        String[][] cadena = new String[1][datos.length];

        for (int i = 0; i < datos.length; i++) {
            cadena[0][i] = datos[i] + "";
        }
        return cadena;
    }

    public static String[][] convertirAMatrizString(double[] datos) {
        String[][] cadena = new String[1][datos.length];

        for (int i = 0; i < datos.length; i++) {
            cadena[0][i] = datos[i] + "";
        }
        return cadena;
    }

    public static String[][] convertirAMatrizString(String[] datos) {
        String[][] cadena = new String[1][datos.length];

        for (int i = 0; i < datos.length; i++) {
            cadena[0][i] = datos[i] + "";
        }
        return cadena;
    }

    /**
     * Extrae los valores distintos de un vector.
     *
     * @param vector Arreglo de tipo entero con valores posiblemente repetidos.
     * @return Retorna un vector con los valores del parámetro pero sin valores
     * repetidos.
     */
    public static int[] calcularValoresDistintos(int[] vector) {
        int[] valoresDistintos = new int[vector.length];
        int cantidadValoresDistintos = 0;
        // Almacenamos en un vector los valores diferentes.
        for (int i = 0; i < vector.length; i++) {
            int j = 0;
            while (j < i && valoresDistintos[j] != vector[i]) {
                j++;
            }
            if (j == i) {
                valoresDistintos[cantidadValoresDistintos] = vector[i];
                cantidadValoresDistintos++;
            }
        }

        int[] valoresDistintos2 = new int[cantidadValoresDistintos];
        for (int i = 0; i < cantidadValoresDistintos; i++) {
            valoresDistintos2[i] = valoresDistintos[i];
        }

        insercion(valoresDistintos2);

        return valoresDistintos2;
    }

    /**
     * Ordena los elementos del vector de menor a mayor mediante la estrategia
     * del ordenamiento por inserción.
     *
     * @param vector Arreglo unidimensional de tipo entero con valores sin
     * ordenar.
     */
    public static void insercion(int[] vector) {
        for (int i = 1; i < vector.length; i++) { // desde el segundo elemento hasta
            int objetivo = vector[i]; // el final, guardamos el elemento y
            int j = i - 1; // empezamos a comprobar con el anterior

            while ((j >= 0) && (objetivo < vector[j])) { // mientras queden posiciones y el
                // valor de objetivo sea menor que los
                vector[j + 1] = vector[j];       // de la izquierda, se desplaza a
                j--;                                 // la derecha
            }
            vector[j + 1] = objetivo; // colocamos objetivo en su sitio
        }
    }

    /**
     * Ordena el primer vector de menor a mayor y modifica los valores del
     * segundo vector de acuerdo al primero.
     *
     * @param vector1
     * @param vector2
     */
    public static void insercion(int[] vector1, int[] vector2) {
        for (int i = 1; i < vector1.length; i++) {
            int objetivo1 = vector1[i];
            int objetivo2 = vector2[i];
            int j = i - 1;

            while ((j >= 0) && (objetivo1 < vector1[j])) {
                vector1[j + 1] = vector1[j];
                vector2[j + 1] = vector2[j];
                j--;
            }
            vector1[j + 1] = objetivo1;
            vector2[j + 1] = objetivo2;
        }
    }

    public static boolean esMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[0].length != matriz[i].length) {
                return false;
            }
        }
        return true;
    }

    public static boolean esMatriz(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[0].length != matriz[i].length) {
                return false;
            }
        }
        return true;
    }

    public static boolean esMatriz(String[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[0].length != matriz[i].length) {
                return false;
            }
        }
        return true;
    }

    public static int[][] eliminarColumna(int[][] matriz, int columna) throws IllegalArgumentException {
        if (esMatriz(matriz)) {
            if (columna >= 0 && columna < matriz[0].length) {
                int[][] nuevaMatriz = new int[matriz.length][matriz[0].length - 1];
                int k;

                for (int i = 0; i < matriz.length; i++) {
                    k = 0;
                    for (int j = 0; j < matriz[i].length; j++) {
                        if (j != columna) {
                            nuevaMatriz[i][k++] = matriz[i][j];
                        }
                    }
                }
                return nuevaMatriz;
            }
            throw new IllegalArgumentException("No existe la columna: " + columna);
        }
        throw new IllegalArgumentException("No es una matriz rectangular");
    }

    public static int[][] agregarFila(int[] vectorFila) {
        int[][] nuevaMatriz = new int[1][vectorFila.length];

        nuevaMatriz[0] = vectorFila.clone();

        return nuevaMatriz;
    }

    public static double[][] agregarFila(double[] vectorFila) {
        double[][] nuevaMatriz = new double[1][vectorFila.length];

        nuevaMatriz[0] = vectorFila.clone();

        return nuevaMatriz;
    }

    public static String[][] agregarFila(String[] vectorFila) {
        String[][] nuevaMatriz = new String[1][vectorFila.length];

        nuevaMatriz[0] = vectorFila.clone();

        return nuevaMatriz;
    }

    public static int[][] agregarColumna(int[] vectorColumna) {
        int[][] nuevaMatriz = new int[vectorColumna.length][1];

        for (int i = 0; i < nuevaMatriz.length; i++) {
            nuevaMatriz[i][0] = vectorColumna[i];
        }

        return nuevaMatriz;
    }

    public static double[][] agregarColumna(double[] vectorColumna) {
        double[][] nuevaMatriz = new double[vectorColumna.length][1];

        for (int i = 0; i < nuevaMatriz.length; i++) {
            nuevaMatriz[i][0] = vectorColumna[i];
        }

        return nuevaMatriz;
    }

    public static String[][] agregarColumna(String[] vectorColumna) {
        String[][] nuevaMatriz = new String[vectorColumna.length][1];

        for (int i = 0; i < nuevaMatriz.length; i++) {
            nuevaMatriz[i][0] = (vectorColumna[i] != null) ? vectorColumna[i] + "" : null;
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva fila al final de la matriz rectangular pasada como
     * parámetro. Esta nueva fila queda inicializada en cero.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @return Matriz rectangular de valores enteros con una fila más que la
     * pasada como parámetro.
     */
    public static int[][] agregarFila(int[][] matriz) {
        int[][] nuevaMatriz = new int[matriz.length + 1][matriz[0].length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva fila al final de la matriz rectangular pasada como
     * parámetro los valores del parámetro vectorFila.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @param vectorFila Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores enteros con una fila más que la
     * pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorFila no es igual
     * al número de columnas de la matriz.
     */
    public static int[][] agregarFila(int[][] matriz, int[] vectorFila) throws IllegalArgumentException {
        if (matriz == null) {
            return null;
        }
        int[][] nuevaMatriz = null;

        if (matriz[0].length == vectorFila.length) {
            nuevaMatriz = agregarFila(matriz);

            for (int j = 0; j < vectorFila.length; j++) {
                nuevaMatriz[matriz.length][j] = vectorFila[j];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorFila.length + ") debe ser igual al número de columnas (" + matriz[0].length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva columna al final de la matriz rectangular pasada como
     * parámetro. Esta nueva columna queda inicializada en cero.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @return Matriz rectangular de valores enteros con una columna más que la
     * pasada como parámetro.
     */
    public static int[][] agregarColumna(int[][] matriz) {
        int[][] nuevaMatriz = new int[matriz.length][matriz[0].length + 1];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva columna al final de la matriz rectangular los valores
     * del parámetro vectorColumna.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @param vectorColumna Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores enteros con una columna más que la
     * pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorColumna no es
     * igual al número de filas de la matriz.
     */
    public static int[][] agregarColumna(int[][] matriz, int[] vectorColumna) throws IllegalArgumentException {
        int[][] nuevaMatriz = null;

        if (matriz.length == vectorColumna.length) {
            nuevaMatriz = agregarColumna(matriz);

            for (int i = 0; i < vectorColumna.length; i++) {
                nuevaMatriz[i][matriz[0].length] = vectorColumna[i];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorColumna.length + ") debe ser igual al número de filas (" + matriz.length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva fila al final de la matriz rectangular pasada como
     * parámetro. Esta nueva fila queda inicializada en cero.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @return Matriz rectangular de valores enteros con una fila más que la
     * pasada como parámetro.
     */
    public static long[][] agregarFila(long[][] matriz) {
        long[][] nuevaMatriz = new long[matriz.length + 1][matriz[0].length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva fila al final de la matriz rectangular pasada como
     * parámetro los valores del parámetro vectorFila.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @param vectorFila Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores enteros con una fila más que la
     * pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorFila no es igual
     * al número de columnas de la matriz.
     */
    public static long[][] agregarFila(long[][] matriz, long[] vectorFila) throws IllegalArgumentException {
        long[][] nuevaMatriz = null;

        if (matriz[0].length == vectorFila.length) {
            nuevaMatriz = agregarFila(matriz);

            for (int j = 0; j < vectorFila.length; j++) {
                nuevaMatriz[matriz.length][j] = vectorFila[j];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorFila.length + ") debe ser igual al número de columnas (" + matriz[0].length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva columna al final de la matriz rectangular pasada como
     * parámetro. Esta nueva columna queda inicializada en cero.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @return Matriz rectangular de valores enteros con una columna más que la
     * pasada como parámetro.
     */
    public static long[][] agregarColumna(long[][] matriz) {
        long[][] nuevaMatriz = new long[matriz.length][matriz[0].length + 1];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva columna al final de la matriz rectangular los valores
     * del parámetro vectorColumna.
     *
     * @param matriz Matriz rectangular de valores enteros.
     * @param vectorColumna Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores enteros con una columna más que la
     * pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorColumna no es
     * igual al número de filas de la matriz.
     */
    public static long[][] agregarColumna(long[][] matriz, long[] vectorColumna) throws IllegalArgumentException {
        long[][] nuevaMatriz = null;

        if (matriz.length == vectorColumna.length) {
            nuevaMatriz = agregarColumna(matriz);

            for (int i = 0; i < vectorColumna.length; i++) {
                nuevaMatriz[i][matriz[0].length] = vectorColumna[i];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorColumna.length + ") debe ser igual al número de filas (" + matriz.length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva fila al final de la matriz rectangular pasada como
     * parámetro. Esta nueva fila queda inicializada en cero.
     *
     * @param matriz Matriz rectangular de valores reales.
     * @return Matriz rectangular de valores reales con una fila más que la
     * pasada como parámetro.
     */
    public static double[][] agregarFila(double[][] matriz) {
        double[][] nuevaMatriz = new double[matriz.length + 1][matriz[0].length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva fila al final de la matriz rectangular los valores
     * del vectorFila.
     *
     * @param matriz Matriz rectangular de valores reales.
     * @param vectorFila Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores reales con una fila más que la
     * pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorFila no es igual
     * al número de columnas de la matriz.
     */
    public static double[][] agregarFila(double[][] matriz, double[] vectorFila) throws IllegalArgumentException {
        double[][] nuevaMatriz = null;

        if (matriz[0].length == vectorFila.length) {
            nuevaMatriz = agregarFila(matriz);

            for (int j = 0; j < vectorFila.length; j++) {
                nuevaMatriz[matriz.length][j] = vectorFila[j];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorFila.length + ") debe ser igual al número de columnas (" + matriz[0].length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva columna al final de la matriz rectangular pasada como
     * parámetro. Esta nueva columna queda inicializada en cero.
     *
     * @param matriz Matriz rectangular de valores reales.
     * @return Matriz rectangular de valores reales con una columna más que la
     * pasada como parámetro.
     */
    public static double[][] agregarColumna(double[][] matriz) {
        double[][] nuevaMatriz = new double[matriz.length][matriz[0].length + 1];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva columna al final de la matriz rectangular los valores
     * del parámetro vectorColumna.
     *
     * @param matriz Matriz rectangular de valores reales.
     * @param vectorColumna Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores reales con una columna más que la
     * pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorColumna no es
     * igual al número de filas de la matriz.
     */
    public static double[][] agregarColumna(double[][] matriz, double[] vectorColumna) throws IllegalArgumentException {
        double[][] nuevaMatriz = null;

        if (matriz.length == vectorColumna.length) {
            nuevaMatriz = agregarColumna(matriz);

            for (int i = 0; i < vectorColumna.length; i++) {
                nuevaMatriz[i][matriz[0].length] = vectorColumna[i];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorColumna.length + ") debe ser igual al número de filas (" + matriz.length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva fila al final de la matriz rectangular pasada como
     * parámetro. Esta nueva columna queda inicializada en null.
     *
     * @param matriz Matriz rectangular de valores de tipo String.
     * @return Matriz rectangular de valores de tipo String con una columna más
     * que la pasada como parámetro.
     */
    public static String[][] agregarFila(String[][] matriz) {
        String[][] nuevaMatriz = new String[matriz.length + 1][matriz[0].length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva fila al final de la matriz rectangular los valores
     * del vectorFila.
     *
     * @param matriz Matriz rectangular de valores de tipo String.
     * @param vectorFila Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores de tipo String con una fila más que
     * la pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorFila no es igual
     * al número de columnas de la matriz.
     */
    public static String[][] agregarFila(String[][] matriz, String[] vectorFila) throws IllegalArgumentException {
        String[][] nuevaMatriz = null;

        if (matriz[0].length == vectorFila.length) {
            nuevaMatriz = agregarFila(matriz);

            for (int j = 0; j < vectorFila.length; j++) {
                nuevaMatriz[matriz.length][j] = vectorFila[j];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorFila.length + ") debe ser igual al número de columnas (" + matriz[0].length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    /**
     * Agrega una nueva columna al final de la matriz rectangular pasada como
     * parámetro. Esta nueva columna queda inicializada en null.
     *
     * @param matriz Matriz rectangular de valores de tipo String.
     * @return Matriz rectangular de valores de tipo String con una columna más
     * que la pasada como parámetro.
     */
    public static String[][] agregarColumna(String[][] matriz) {
        String[][] nuevaMatriz = new String[matriz.length][matriz[0].length + 1];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        return nuevaMatriz;
    }

    /**
     * Agrega en una nueva columna al final de la matriz rectangular los valores
     * del parámetro vectorColumna.
     *
     * @param matriz Matriz rectangular de valores de tipo String.
     * @param vectorColumna Vector cuyos valores se copiaran en la matriz.
     * @return Matriz rectangular de valores de tipo Strin con una columna más
     * que la pasada como parámetro.
     * @throws IllegalArgumentException Si el tamaño del vectorColumna no es
     * igual al número de filas de la matriz.
     */
    public static String[][] agregarColumna(String[][] matriz, String[] vectorColumna) throws IllegalArgumentException {
        String[][] nuevaMatriz = null;

        if (matriz.length == vectorColumna.length) {
            nuevaMatriz = agregarColumna(matriz);

            for (int i = 0; i < vectorColumna.length; i++) {
                nuevaMatriz[i][matriz[0].length] = vectorColumna[i];
            }
        } else {
            throw new IllegalArgumentException("El tamaño del vector (" + vectorColumna.length + ") debe ser igual al número de filas (" + matriz.length + ") de la matriz.");
        }
        return nuevaMatriz;
    }

    public static int[][] eliminarFila(int[][] matriz, int fila) throws IllegalArgumentException {
        if (matriz != null && fila >= 0 && fila < matriz.length) {
            int[][] nuevaMatriz = null;

            if (matriz.length > 1) {
                nuevaMatriz = new int[matriz.length - 1][matriz[0].length];
                int k = 0;

                for (int i = 0; i < matriz.length; i++) {
                    if (i != fila) {
                        nuevaMatriz[k++] = matriz[i].clone();
                    }
                }
            }
            return nuevaMatriz;
        }
        throw new IllegalArgumentException("No existe la fila: " + fila);
    }

    public static double[][] eliminarFila(double[][] matriz, int fila) throws IllegalArgumentException {
        if (matriz != null && fila >= 0 && fila < matriz.length) {
            double[][] nuevaMatriz = null;

            if (matriz.length > 1) {
                nuevaMatriz = new double[matriz.length - 1][matriz[0].length];
                int k = 0;

                for (int i = 0; i < matriz.length; i++) {
                    if (i != fila) {
                        nuevaMatriz[k++] = matriz[i].clone();
                    }
                }
            }
            return nuevaMatriz;
        }
        throw new IllegalArgumentException("No existe la fila: " + fila);
    }

    public static String[][] eliminarFila(String[][] matriz, int fila) throws IllegalArgumentException {
        if (matriz != null && fila >= 0 && fila < matriz.length) {
            String[][] nuevaMatriz = null;

            if (matriz.length > 1) {
                nuevaMatriz = new String[matriz.length - 1][matriz[0].length];
                int k = 0;
                for (int i = 0; i < matriz.length; i++) {
                    if (k != fila) {
                        nuevaMatriz[k++] = matriz[i].clone();
                    }
                }
            }
            return nuevaMatriz;
        }
        throw new IllegalArgumentException("No existe la fila: " + fila);
    }

    public static String[][] eliminarColumna(String[][] matriz, int columna) throws IllegalArgumentException {
        if (matriz != null && esMatriz(matriz)) {
            if (columna >= 0 && columna < matriz[0].length) {
                String[][] nuevaMatriz = null;

                if (matriz[0].length > 1) {
                    nuevaMatriz = new String[matriz.length][matriz[0].length - 1];
                    int k;

                    for (int i = 0; i < matriz.length; i++) {
                        k = 0;
                        for (int j = 0; j < matriz[i].length; j++) {
                            if (j != columna) {
                                nuevaMatriz[i][k++] = matriz[i][j];
                            }
                        }
                    }
                }
                return nuevaMatriz;
            }
            throw new IllegalArgumentException("No existe la columna: " + columna);
        }
        throw new IllegalArgumentException("No existe la matriz");
    }

    /**
     * Ordena los elementos del vector de menor a mayor mediante la estrategia
     * del ordenamiento por inserción.
     *
     * @param vector Arreglo unidimensional de tipo real con valores sin
     * ordenar.
     */
    public static void ordenarPorInsercion(double[] vector) {
        for (int i = 1; i < vector.length; i++) {
            double objetivo = vector[i];
            int j = i - 1;

            while ((j >= 0) && (objetivo < vector[j])) {
                vector[j + 1] = vector[j];
                j--;
            }
            vector[j + 1] = objetivo;
        }
    }

    public static void ordenarPorInsercion(double[] vector1, double[] vector2) {
        for (int i = 1; i < vector1.length; i++) {
            double objetivo1 = vector1[i];
            double objetivo2 = vector2[i];
            int j = i - 1;

            while ((j >= 0) && (objetivo1 < vector1[j])) {
                vector1[j + 1] = vector1[j];
                vector2[j + 1] = vector2[j];
                j--;
            }
            vector1[j + 1] = objetivo1;
            vector2[j + 1] = objetivo2;
        }
    }

    public static void shell(int[] datos) {
        int n = datos.length;
        int salto = n / 2;
        while (salto > 0) {
            for (int i = salto; i < n; i++) {
                int j = i - salto;
                while (j >= 0) {
                    int k = j + salto;
                    if (datos[j] > datos[k]) {
                        int auxiliar = datos[j];
                        datos[j] = datos[k];
                        datos[k] = auxiliar;
                    } else {
                        j = 0;
                    }
                    j -= salto;
                }
            }
            salto /= 2;
        }
    }

    public static void shell(double[] datos) {
        int n = datos.length;
        int salto = n / 2;
        while (salto > 0) {
            for (int i = salto; i < n; i++) {
                int j = i - salto;
                while (j >= 0) {
                    int k = j + salto;
                    if (datos[j] > datos[k]) {
                        double auxiliar = datos[j];
                        datos[j] = datos[k];
                        datos[k] = auxiliar;
                    } else {
                        j = 0;
                    }
                    j -= salto;
                }
            }
            salto /= 2;
        }
    }

    /**
     * Determina si un vector esta ordenado ascendentemente.
     *
     * @param vector Arreglo unidimensional de tipo entero
     * @return Retorna true si el vector esta ordenado de menor a mayor
     */
    public static boolean estaOrdenado(int[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenado(long[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenado(byte[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenado(short[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenado(char[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determina si un vector esta ordenado ascendentemente.
     *
     * @param vector Arreglo unidimensional de tipo real
     * @return Retorna true si el vector esta ordenado de menor a mayor
     */
    public static boolean estaOrdenado(double[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenado(float[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1] > vector[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenado(String[] vector) {
        for (int i = 1; i < vector.length; i++) {
            if (vector[i - 1].compareTo(vector[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean tieneValoresNull(String[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                if (datos[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean tieneValoresNaN(double[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                if (datos[i][j] == Double.NaN) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void mostrarPosicionesNulas(String[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                if (datos[i][j] == null) {
                    System.out.println("(" + i + ", " + j + ")");
                }
            }
        }
    }

    // *****************************************MOSTRAR ARREGLOS*******************************************************************************
    /**
     * Muestra por pantalla un vector con valores enteros. Primero se muestran
     * las posiciones del vector en color azul y debajo de cada posición se
     * muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de enteros
     */
    public static void mostrarVector(int[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];

            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            mostrarVector(stringVector);
        }
    }

    /**
     * Muestra por pantalla una lista con valores enteros. Primero se muestran
     * las posiciones de la lista en color azul y debajo de cada posición se
     * muestra el elemento correspondiente de la lista.
     *
     * @param lista Lista de enteros
     */
    public static void mostrarLista(ArrayList<Integer> lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector);
        }
    }

    public static void mostrarListaReal(ArrayList<Double> lista) {
        if (lista != null && !lista.isEmpty()) {
            double[] valores = new double[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                valores[i] = lista.get(i);
            }
            mostrarVector(valores);
        }
    }

    public static void mostrarListaReal(ArrayList<Double> lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            double[] valores = new double[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                valores[i] = lista.get(i);
            }
            mostrarVector(valores, titulo);
        }
    }

    public static void mostrarLista(ArrayList<Integer> lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector, titulo);
        }
    }

    public static void mostrarVector(byte[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            mostrarVector(stringVector);
        }
    }

    /**
     * Muestra por pantalla un vector con valores enteros. Primero se muestran
     * las posiciones del vector en color azul y debajo de cada posición se
     * muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de reales
     */
    public static void imprimirVector(int[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            imprimirVector(stringVector);
        }
    }

    /**
     * Muestra por pantalla una lista con valores enteros. Primero se muestran
     * las posiciones de la lista en color azul y debajo de cada posición se
     * muestra el elemento correspondiente de la lista.
     *
     * @param lista Lista de enteros
     */
    public static void imprimirLista(ArrayList<Integer> lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector);
        }
    }

    public static void imprimirLista(IntegerArrayList lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void mostrarLista(IntegerArrayList lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    /**
     * Muestra por pantalla una lista (ArrayList) con valores de tipo entero.
     * Primero se muestran las posiciones del vector en color azul y debajo de
     * cada posición se muestra el elemento correspondiente de la lista.
     *
     * @param lista Lista de tipo String
     * @param titulo Cadena con el título de la lista. La cadena se muestra en
     * color negro y justificada a la izquierda.
     */
    public static void imprimirLista(ArrayList<Integer> lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector, titulo);
        }
    }

    public static void imprimirLista(IntegerArrayList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void mostrarLista(IntegerArrayList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void imprimirLista(IntegerLinkedList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
        }
    }

    public static void mostrarLista(IntegerLinkedList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
        }
    }

    public static void imprimirLista(DoubleArrayList lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void imprimirLista(DoubleArrayList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void imprimirLista(DoubleLinkedList lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector);
            Consola.printGreen("Tamaño: " + lista.size());
        }
    }

    public static void imprimirLista(DoubleLinkedList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            imprimirVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
        }
    }

    public static void mostrarLista(DoubleArrayList lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void mostrarLista(DoubleArrayList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
            Consola.printRed("Capacidad: " + lista.capacity());
        }
    }

    public static void mostrarLista(DoubleLinkedList lista) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector);
            Consola.printGreen("Tamaño: " + lista.size());
        }
    }

    public static void mostrarLista(DoubleLinkedList lista, String titulo) {
        if (lista != null && !lista.isEmpty()) {
            String[] stringVector = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                stringVector[i] = lista.get(i) + "";
            }
            mostrarVector(stringVector, titulo);
            Consola.printGreen("Tamaño: " + lista.size());
        }
    }

    /**
     * Muestra por pantalla un vector con valores enteros. Primero se muestran
     * las posiciones del vector en color azul y debajo de cada posición se
     * muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de reales
     * @param titulo Cadena con el título del vector. La cadena se muestra en
     * color negro y justificada a la izquierda.
     */
    public static void mostrarVector(int[] vector, String titulo) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            mostrarVector(stringVector, titulo);
        }
    }

    public static void imprimirVector(int[] vector, String titulo) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            imprimirVector(stringVector, titulo);
        }
    }

    /**
     * Muestra por pantalla un vector con valores reales. Primero se muestran
     * las posiciones del vector en color azul y debajo de cada posición se
     * muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de reales
     * @param titulo Cadena con el título del vector. La cadena se muestra en
     * color negro y justificada a la izquierda.
     */
    public static void mostrarVector(double[] vector, String titulo) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                int valor = (int) vector[i];
                if (vector[i] == valor) {
                    stringVector[i] = valor + "";
                } else {
                    stringVector[i] = vector[i] + "";
                }
            }
            mostrarVector(stringVector, titulo);
        }
    }

    public static void imprimirVector(double[] vector, String titulo) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                int valor = (int) vector[i];
                if (vector[i] == valor) {
                    stringVector[i] = valor + "";
                } else {
                    stringVector[i] = vector[i] + "";
                }
            }
            imprimirVector(stringVector, titulo);
        }
    }

    /**
     * Muestra por pantalla un vector con valores reales. Primero se muestran
     * las posiciones del vector en color azul y debajo de cada posición se
     * muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de reales
     */
    public static void mostrarVector(double[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                int valor = (int) vector[i];
                if (vector[i] == valor) {
                    stringVector[i] = valor + "";
                } else {
                    stringVector[i] = vector[i] + "";
                }
            }
            mostrarVector(stringVector);
        }
    }

    /**
     * Muestra por pantalla un vector con valores reales. Primero se muestran
     * las posiciones del vector en color azul y debajo de cada posición se
     * muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de reales
     */
    public static void imprimirVector(double[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                int valor = (int) vector[i];
                if (vector[i] == valor) {
                    stringVector[i] = valor + "";
                } else {
                    stringVector[i] = vector[i] + "";
                }
            }
            imprimirVector(stringVector);
        }
    }

    /**
     * Muestra por pantalla un vector con valores reales que se redondean a la
     * cantidad de decimales indicada. Primero se muestran las posiciones del
     * vector en color azul y debajo de cada posición se muestra el elemento
     * correspondiente del vector.
     *
     * @param vector Vector de reales
     * @param decimales Valor entero que indica la cantidad de decimales a
     * mostrar por cada valor del vector.
     * @param titulo Cadena con el título del vector. La cadena se muestra en
     * color negro y justificada a la izquierda.
     */
    public static void mostrarVector(double[] vector, int decimales, String titulo) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                double valor = Matematica.redondear(vector[i], decimales);
                int valorEntero = (int) valor;

                if (valor == valorEntero) {
                    stringVector[i] = valorEntero + "";
                } else {
                    stringVector[i] = valor + "";
                }
            }
            mostrarVector(stringVector, titulo);
        }
    }

    public static void imprimirVector(double[] vector, int decimales, String titulo) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                double valor = Matematica.redondear(vector[i], decimales);
                int valorEntero = (int) valor;

                if (valor == valorEntero) {
                    stringVector[i] = valorEntero + "";
                } else {
                    stringVector[i] = valor + "";
                }
            }
            imprimirVector(stringVector, titulo);
        }
    }

    /**
     * Muestra por pantalla un vector con valores reales que se redondean a la
     * cantidad de decimales indicada. Primero se muestran las posiciones del
     * vector en color azul y debajo de cada posición se muestra el elemento
     * correspondiente del vector.
     *
     * @param vector Vector de reales
     * @param decimales Valor entero que indica la cantidad de decimales a
     * mostrar por cada valor del vector.
     */
    public static void mostrarVector(double[] vector, int decimales) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                double valor = Matematica.redondear(vector[i], decimales);
                int valorEntero = (int) valor;

                if (valor == valorEntero) {
                    stringVector[i] = valorEntero + "";
                } else {
                    stringVector[i] = valor + "";
                }
            }
            mostrarVector(stringVector);
        }
    }

    public static void imprimirVector(double[] vector, int decimales) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                double valor = Matematica.redondear(vector[i], decimales);
                int valorEntero = (int) valor;

                if (valor == valorEntero) {
                    stringVector[i] = valorEntero + "";
                } else {
                    stringVector[i] = valor + "";
                }
            }
            imprimirVector(stringVector);
        }
    }

    /**
     * Muestra por pantalla un vector con valores de tipo String. Primero se
     * muestran las posiciones del vector en color azul y debajo de cada
     * posición se muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de tipo String
     */
    public static void mostrarVector(String[] vector) {
        mostrarVector(vector, null);
    }

    /**
     * Muestra por pantalla un vector con valores de tipo String. Primero se
     * muestran las posiciones del vector en color azul y debajo de cada
     * posición se muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de tipo String
     */
    public static void imprimrVector(String[] vector) {
        imprimirVector(vector, null);
    }

    /**
     * Muestra por pantalla un vector con valores de tipo String. Primero se
     * muestran las posiciones del vector en color azul y debajo de cada
     * posición se muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de tipo String
     */
    public static void imprimirVector(String[] vector) {
        imprimirVector(vector, null);
    }

    /**
     * Muestra por pantalla un vector con valores de tipo String. Primero se
     * muestran las posiciones del vector en color azul y debajo de cada
     * posición se muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de tipo String
     * @param titulo Cadena con el título del vector. La cadena se muestra en
     * color negro y justificada a la izquierda.
     */
    public static void mostrarVector(String[] vector, String titulo) {
        if (vector != null) {
            int mayorColumna = (vector.length + "").length();

            for (String vector1 : vector) {
                int cifras = vector1.length();
                if (cifras > mayorColumna) {
                    mayorColumna = cifras;
                }
            }
            mayorColumna += 2;
            String filaEtiquetas = "", filaElementos = "";
            String valor = vector[0];
            int mayorPrimerElemento = 2;
            if (valor.length() > mayorPrimerElemento) {
                mayorPrimerElemento = valor.length();
            }

            valor = 1 + ":";
            int delta = mayorPrimerElemento - valor.length();

            for (int j = 0; j < delta; j++) {
                filaEtiquetas += " ";
            }
            filaEtiquetas += valor;
            //
            valor = vector[0];
            delta = mayorPrimerElemento - valor.length();

            for (int j = 0; j < delta; j++) {
                filaElementos += " ";
            }
            filaElementos += valor;

            for (int i = 1; i < vector.length; i++) {
                valor = (i + 1) + ":";
                delta = mayorColumna - valor.length();

                for (int j = 0; j < delta; j++) {
                    filaEtiquetas += " ";
                }
                filaEtiquetas += valor;
                //
                valor = vector[i];
                delta = mayorColumna - valor.length();

                for (int j = 0; j < delta; j++) {
                    filaElementos += " ";
                }
                filaElementos += valor;

            }
            if (titulo != null) {
                System.out.println(titulo);
            }
            Consola.printBlue(filaEtiquetas);
            System.out.println(filaElementos);
        }
    }

    /**
     * Muestra por pantalla un vector con valores de tipo String. Primero se
     * muestran las posiciones del vector en color azul y debajo de cada
     * posición se muestra el elemento correspondiente del vector.
     *
     * @param vector Vector de tipo String
     * @param titulo Cadena con el título del vector. La cadena se muestra en
     * color negro y justificada a la izquierda.
     */
    public static void imprimirVector(String[] vector, String titulo) {
        if (vector != null) {
            int mayorColumna = (vector.length + "").length();

            for (String vector1 : vector) {
                int cifras = vector1.length();
                if (cifras > mayorColumna) {
                    mayorColumna = cifras;
                }
            }
            mayorColumna += 2;
            String filaEtiquetas = "", filaElementos = "";
            String valor = vector[0];
            int mayorPrimerElemento = 2;
            if (valor.length() > mayorPrimerElemento) {
                mayorPrimerElemento = valor.length();
            }

            valor = "0:";
            int delta = mayorPrimerElemento - valor.length();

            for (int j = 0; j < delta; j++) {
                filaEtiquetas += " ";
            }
            filaEtiquetas += valor;
            //
            valor = vector[0];
            delta = mayorPrimerElemento - valor.length();

            for (int j = 0; j < delta; j++) {
                filaElementos += " ";
            }
            filaElementos += valor;

            for (int i = 1; i < vector.length; i++) {
                valor = i + ":";
                delta = mayorColumna - valor.length();

                for (int j = 0; j < delta; j++) {
                    filaEtiquetas += " ";
                }
                filaEtiquetas += valor;
                //
                valor = vector[i];
                delta = mayorColumna - valor.length();

                for (int j = 0; j < delta; j++) {
                    filaElementos += " ";
                }
                filaElementos += valor;

            }
            if (titulo != null) {
                System.out.println(titulo);
            }
            Consola.printBlue(filaEtiquetas);
            System.out.println(filaElementos);
        }
    }

    public static void imprimir(String[] vector, String titulo) {
        if (vector != null) {
            int mayorColumna = (vector.length + "").length();

            for (String vector1 : vector) {
                int cifras = vector1.length();
                if (cifras > mayorColumna) {
                    mayorColumna = cifras;
                }
            }
            mayorColumna += 2;
            String filaEtiquetas = "", filaElementos = "";
            String valor = vector[0];
            int mayorPrimerElemento = 2;
            if (valor.length() > mayorPrimerElemento) {
                mayorPrimerElemento = valor.length();
            }

            valor = "0:";
            int delta = mayorPrimerElemento - valor.length();

            for (int j = 0; j < delta; j++) {
                filaEtiquetas += " ";
            }
            filaEtiquetas += valor;
            //
            valor = vector[0];
            delta = mayorPrimerElemento - valor.length();

            for (int j = 0; j < delta; j++) {
                filaElementos += " ";
            }
            filaElementos += valor;

            for (int i = 1; i < vector.length; i++) {
                valor = i + ":";
                delta = mayorColumna - valor.length();

                for (int j = 0; j < delta; j++) {
                    filaEtiquetas += " ";
                }
                filaEtiquetas += valor;
                //
                valor = vector[i];
                delta = mayorColumna - valor.length();

                for (int j = 0; j < delta; j++) {
                    filaElementos += " ";
                }
                filaElementos += valor;

            }
            if (titulo != null) {
                System.out.println(titulo);
            }
            System.out.println(filaEtiquetas);
            System.out.println(filaElementos);
        }
    }

    /**
     * Muestra por pantalla un vector con valores det tipo char. Primero se
     * muestran las posiciones del vector en color azul y debajo de cada
     * posición se muestra el elemento correspondiente del vector.
     *
     * @param vector Vector con elementos de tipo char.
     */
    public static void mostrarVector(char[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            mostrarVector(stringVector);
        }
    }

    public static void imprimirVector(char[] vector) {
        if (vector != null) {
            String[] stringVector = new String[vector.length];
            for (int i = 0; i < vector.length; i++) {
                stringVector[i] = vector[i] + "";
            }
            imprimirVector(stringVector);
        }
    }

    public static void mostrarArregloBidimensional(String[][] valores, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        if (valores == null || valores.length == 0) {
            throw new NullPointerException("No hay arreglo bidimensional");
        }
        int filaMayorLongitud = 0;
        int mayorLongitudElemento = 0;

        for (int i = 0; i < valores.length; i++) {
            if (valores[i] != null && valores[i].length > filaMayorLongitud) {
                filaMayorLongitud = valores[i].length;
            }

            for (int j = 0; j < valores[i].length; j++) {
                if (valores[i][j].length() > mayorLongitudElemento) {
                    mayorLongitudElemento = valores[i][j].length();
                }
            }
        }

        if (nombresFilas != null && filaMayorLongitud == nombresFilas.length) {
        }
    }

    public static String darFormato(String cadena, int espacios) {
        String ans = "";
        try {
            double numero = Double.parseDouble(cadena);
            for (int i = 0; i < espacios; i++) {
                ans += " ";
            }
            ans += cadena;
        } catch (NumberFormatException exc) {
            ans += cadena;
            for (int i = 0; i < espacios; i++) {
                ans += " ";
            }
        }
        return ans;
    }

    public static void mostrarPersonas(Persona[] personas) {
        int n = personas.length;
        String[][] matriz = new String[n][8];
        int i = 0;
        for (Persona persona : personas) {
            matriz[i][0] = persona.getNuip();
            matriz[i][1] = persona.getNombre();
            matriz[i][2] = persona.getPrimerApellido();
            matriz[i][3] = persona.getSegundoApellido();
            matriz[i][6] = persona.getNumeroTelefonico();
            matriz[i][5] = persona.getEmail();
            matriz[i][4] = (persona.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
            matriz[i][7] = persona.getFechaNacimiento().toString();
            i++;
        }
        String[] campos = {"NUIP", "Nombre", "Primer Apellido", "Segundo Apellido", "Género", "Email", "Teléfono", "Fecha nacimiento"};

        mostrarMatriz(matriz, "DATOS PERSONAS", null, campos);
    }

    public static void mostrarPersonas(ArrayList<Persona> personas) {
        int n = personas.size();
        String[][] matriz = new String[n][8];
        int i = 0;
        for (Persona persona : personas) {
            matriz[i][0] = persona.getNuip();
            matriz[i][1] = persona.getNombre();
            matriz[i][2] = persona.getPrimerApellido();
            matriz[i][3] = persona.getSegundoApellido();
            matriz[i][6] = persona.getNumeroTelefonico();
            matriz[i][5] = persona.getEmail();
            matriz[i][4] = (persona.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
            matriz[i][7] = persona.getFechaNacimiento().toString();
            i++;
        }
        String[] campos = {"NUIP", "Nombre", "Primer Apellido", "Segundo Apellido", "Género", "Email", "Teléfono", "Fecha nacimiento"};

        mostrarMatriz(matriz, "DATOS PERSONAS", null, campos);
    }

    public static void mostrarEstudiantes(Estudiante[] estudiantes) {
        int n = estudiantes.length;
        String[][] matriz = new String[n][6];
        int i = 0;
        for (Estudiante estudiante : estudiantes) {
            matriz[i][0] = estudiante.getNuip();
            matriz[i][1] = estudiante.getNombre();
            matriz[i][2] = estudiante.getApellido();
            matriz[i][3] = (estudiante.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
            matriz[i][4] = estudiante.getNota() + "";
            matriz[i][5] = (estudiante.isRepitente()) ? "Si" : "No";
            i++;
        }
        String[] campos = {"NUIP", "Nombre", "Apellido", "Género", "Nota", "Repitente"};

        mostrarMatriz(matriz, "ESTUDIANTES", null, campos);
    }

    public static void mostrarEstudiantes(Estudiante[] estudiantes, int justificar) {
        int n = estudiantes.length;
        String[][] matriz = new String[n][6];
        int i = 0;
        for (Estudiante estudiante : estudiantes) {
            matriz[i][0] = estudiante.getNuip();
            matriz[i][1] = estudiante.getNombre();
            matriz[i][2] = estudiante.getApellido();
            matriz[i][3] = (estudiante.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
            matriz[i][4] = estudiante.getNota() + "";
            matriz[i][5] = (estudiante.isRepitente()) ? "Si" : "No";
            i++;
        }
        String[] campos = {"NUIP", "Nombre", "Apellido", "Género", "Nota", "Repitente"};

        mostrarMatriz(matriz, "ESTUDIANTES", null, campos, null, justificar);
    }

    public static void mostrarEstudiantes(ArrayList<Estudiante> lista) {
        int n = lista.size();
        String[][] matriz = new String[n][6];
        int i = 0;
        for (Estudiante estudiante : lista) {
            matriz[i][0] = estudiante.getNuip();
            matriz[i][1] = estudiante.getNombre();
            matriz[i][2] = estudiante.getApellido();
            matriz[i][3] = (estudiante.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
            matriz[i][4] = estudiante.getNota() + "";
            matriz[i][5] = (estudiante.isRepitente()) ? "Si" : "No";

            i++;
        }
        String[] campos = {"NUIP", "Nombre", "Apellido", "Género", "Nota", "Repitente"};

        mostrarMatriz(matriz, "ESTUDIANTES", null, campos);
    }

    public static void mostrarEmpleados(Empleado[] empleados) {
        int n = empleados.length;
        String[][] matriz = new String[n][7];
        int i = 0;
        for (Empleado empleado : empleados) {
            if (empleado != null) {
                matriz[i][0] = empleado.getNuip();
                matriz[i][1] = empleado.getNombre();
                matriz[i][2] = empleado.getApellido();
                matriz[i][3] = (empleado.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
                matriz[i][4] = "$" + empleado.getSalario();
                matriz[i][5] = empleado.getFechaNacimiento().toString();
                matriz[i][6] = empleado.getFechaIngreso().toString();
            }
            i++;
        }

        String[] campos = {"NUIP", "Nombre", "Apellido", "Género", "Salario", "Fecha Nacimiento", "Fecha Ingreso"};

        mostrarMatriz(matriz, "EMPLEADOS", null, campos);
    }

    public static void mostrarEmpleados(ArrayList<Empleado> lista) {
        int n = lista.size();
        String[][] matriz = new String[n][7];
        int i = 0;
        for (Empleado empleado : lista) {
            matriz[i][0] = empleado.getNuip();
            matriz[i][1] = empleado.getNombre();
            matriz[i][2] = empleado.getApellido();
            matriz[i][3] = (empleado.getGenero() == Estudiante.FEMENINO) ? "F" : "M";
            matriz[i][4] = "$" + empleado.getSalario();
            matriz[i][5] = empleado.getFechaNacimiento().toString();
            matriz[i][6] = empleado.getFechaIngreso().toString();
            i++;
        }

        String[] campos = {"NUIP", "Nombre", "Apellido", "Género", "Salario", "Fecha Nacimiento", "Fecha Ingreso"};

        mostrarMatriz(matriz, "EMPLEADOS", null, campos);
    }

    /**
     * Muestra por pantalla una matriz con valores enteros.
     *
     * @param matriz Matriz de enteros
     */
    public static void mostrarMatriz(int[][] matriz) {
        mostrarMatriz(matriz, null);
    }

    public static void imprimirMatriz(int[][] matriz) {
        imprimirMatriz(matriz, null);
    }

    public static void imprimir(int[][] matriz) {
        imprimir(matriz, null);
    }

    /**
     * Muestra por pantalla una matriz de enteros con nombres para las filas y
     * para las columnas. Si se suministran los nombres de las filas y de las
     * columnas se mostrarán en color azul. Si no se suministran los nombres de
     * las filas y/o de las columnas se mostrará el número de la fila y/o
     * columna seguido de dos puntos en color azul. Los valores de la matriz se
     * muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param nombresFilas Vector de tipo String con nombres para las filas.
     * @param nombresColumnas Vector de tipo String con nombres para las
     * columnas.
     */
    public static void mostrarMatriz(int[][] matriz, String[] nombresFilas, String[] nombresColumnas) {
        mostrarMatriz(matriz, nombresFilas, nombresColumnas, Consola.BLUE);
    }

    /**
     * Muestra por pantalla una matriz de enteros. Muestra el número de cada
     * fila y cada columna seguido de dos puntos en el color indicado. Los
     * valores de la matriz se muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param titulo Candena con el título de la matriz.
     */
    public static void mostrarMatriz(int[][] matriz, String titulo) {
        mostrarMatriz(matriz, titulo, null, null);
    }

    public static void imprimirMatriz(int[][] matriz, String titulo) {
        imprimirMatriz(matriz, titulo, null, null);
    }

    public static void imprimir(int[][] matriz, String titulo) {
        imprimir(matriz, titulo, null, null);
    }

    /**
     * Muestra por pantalla una matriz de enteros. Muestra el número de cada
     * fila y cada columna seguido de dos puntos en el color indicado. Los
     * valores de la matriz se muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param justificar Valor entero que indica el justificado con que se
     * mostrarán los valores y los nombres de las filas y columnas. El valor
     * cero justificará el texto a la derecha y un valor diferente de cero lo
     * justificará a la izquierda.
     */
    public static void mostrarMatriz(int[][] matriz, int justificar) {
        mostrarMatriz(matriz, "", null, null, null, justificar);
    }

    /**
     * Muestra por pantalla una matriz de enteros con nombres para las filas y
     * para las columnas. Si se suministran los nombres de las filas y de las
     * columnas se mostrarán con el color indicado. Si no se suministran los
     * nombres de las filas y/o de las columnas se mostrará el número de la fila
     * y/o columna seguido de dos puntos con el color indicado. Los valores de
     * la matriz se muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param nombresFilas Vector de tipo String con nombres para las filas.
     * @param nombresColumnas Vector de tipo String con nombres para las
     * columnas.
     * @param color Cadena que indica el color de los nombres de las filas y de
     * los nombre de las columnas. Debe ser un valor de la clase Consola.
     */
    public static void mostrarMatriz(int[][] matriz, String[] nombresFilas, String[] nombresColumnas, String color) {
        mostrarMatriz(matriz, "", nombresFilas, nombresColumnas, color, JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de enteros con nombres para las filas y
     * para las columnas. Si se suministran los nombres de las filas y de las
     * columnas se mostrarán con el color indicado. Si no se suministran los
     * nombres de las filas y/o de las columnas se mostrará el número de la fila
     * y/o columna seguido de dos puntos con el color indicado. Los valores de
     * la matriz se muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param nombresFilas Vector de tipo String con nombres para las filas.
     * @param nombresColumnas Vector de tipo String con nombres para las
     * columnas.
     * @param justificar Valor entero que indica el justificado con que se
     * mostrarán los valores y los nombres de las filas y columnas. El valor
     * cero justificará el texto a la derecha y un valor diferente de cero lo
     * justificará a la izquierda.
     */
    public static void mostrarMatriz(int[][] matriz, String[] nombresFilas, String[] nombresColumnas, int justificar) {
        mostrarMatriz(matriz, "", nombresFilas, nombresColumnas, null, justificar);
    }

    /**
     * Muestra por pantalla una matriz de enteros con nombres para las filas y
     * para las columnas. Si se suministran los nombres de las filas y de las
     * columnas se mostrarán con el color indicado. Si no se suministran los
     * nombres de las filas y/o de las columnas se mostrará el número de la fila
     * y/o columna seguido de dos puntos con el color indicado. Los valores de
     * la matriz se muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param nombresFilas Vector de tipo String con nombres para las filas.
     * @param nombresColumnas Vector de tipo String con nombres para las
     * columnas.
     */
    public static void mostrarMatriz(int[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        mostrarMatriz(matriz, titulo, nombresFilas, nombresColumnas, null, JUSTIFICAR_DERECHA);
    }

    public static void imprimirMatriz(int[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        imprimirMatriz(matriz, titulo, nombresFilas, nombresColumnas, null, JUSTIFICAR_DERECHA);
    }

    public static void imprimir(int[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        imprimir(matriz, titulo, nombresFilas, nombresColumnas, null, JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de enteros con nombres para las filas y
     * para las columnas. Si se suministran los nombres de las filas y de las
     * columnas se mostrarán con el color indicado. Si no se suministran los
     * nombres de las filas y/o de las columnas se mostrará el número de la fila
     * y/o columna seguido de dos puntos con el color indicado. Los valores de
     * la matriz se muestran en color negro.
     *
     * @param matriz Matriz con valores enteros.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param nombresFilas Vector de tipo String con nombres para las filas.
     * @param nombresColumnas Vector de tipo String con nombres para las
     * columnas.
     * @param color Cadena que indica el color de los nombres de las filas y de
     * los nombre de las columnas. Debe ser un valor de la clase Consola.
     * @param justificar Valor entero que indica el justificado con que se
     * mostrarán los valores y los nombres de las filas y columnas. El valor
     * cero justificará el texto a la derecha y un valor diferente de cero lo
     * justificará a la izquierda.
     */
    public static void mostrarMatriz(int[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }

        String[][] stringMatriz = convertirAMatrizString(matriz);

        mostrarMatriz(stringMatriz, titulo, nombresFilas, nombresColumnas, color, justificar);
    }

    public static void imprimirMatriz(int[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }

        String[][] stringMatriz = convertirAMatrizString(matriz);

        imprimirMatriz(stringMatriz, titulo, nombresFilas, nombresColumnas, color, justificar);
    }

    public static void imprimir(int[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }

        String[][] stringMatriz = convertirAMatrizString(matriz);

        imprimir(stringMatriz, titulo, nombresFilas, nombresColumnas, color, justificar);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados. Si
     * se suministran los nombres de las filas y de las columnas se mostrarán en
     * color azul. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos en color azul. Los valores de la matriz se muestran en color
     * negro.
     *
     * @param matriz Matriz de tipo real.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     */
    public static void mostrarMatriz(double[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        mostrarMatriz(matriz, -1, titulo, nombresFilas, nombresColumnas, "", JUSTIFICAR_DERECHA);
    }

    public static void imprimirMatriz(double[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        imprimirMatriz(matriz, -1, titulo, nombresFilas, nombresColumnas, "", JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados. Si
     * se suministran los nombres de las filas y de las columnas se mostrarán
     * con el color indicado. Si no se suministran los nombres de las filas y/o
     * de las columnas se mostrará el número de la fila y/o columna seguido de
     * dos puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro. El título se muestra en color negro y centrado.
     *
     * @param matriz Matriz de tipo real.
     * @param decimales Valor entero no negativo que indica la cantidad de
     * decimales con que se mostrarán los valores de la matriz.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     * @param color Cadena que representa el color. Debe ser un valor de la
     * clase Consola.
     * @param justificar Valor entero que indica el justificado con que se
     * mostrarán los valores y los nombres de las filas y columnas. El valor
     * cero justificará el texto a la derecha y un valor diferente de cero lo
     * justificará a la izquierda.
     */
    public static void mostrarMatriz(double[][] matriz, int decimales, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }
        String[][] stringMatrix = new String[matriz.length][];

        for (int i = 0; i < matriz.length; i++) {
            stringMatrix[i] = new String[matriz[i].length];
            for (int j = 0; j < matriz[i].length; j++) {
                double valorReal = Matematica.redondear(matriz[i][j], decimales);
                int valorEntero = (int) valorReal;
                if (valorEntero == valorReal) {
                    stringMatrix[i][j] = valorEntero + "";
                } else {
                    stringMatrix[i][j] = valorReal + "";
                }
            }
        }

        mostrarMatriz(stringMatrix, titulo, nombresFilas, nombresColumnas, color, justificar);
    }

    public static void imprimirMatriz(double[][] matriz, int decimales, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }
        String[][] stringMatrix = new String[matriz.length][];

        for (int i = 0; i < matriz.length; i++) {
            stringMatrix[i] = new String[matriz[i].length];
            for (int j = 0; j < matriz[i].length; j++) {
                double valorReal = Matematica.redondear(matriz[i][j], decimales);
                int valorEntero = (int) valorReal;
                if (valorEntero == valorReal) {
                    stringMatrix[i][j] = valorEntero + "";
                } else {
                    stringMatrix[i][j] = valorReal + "";
                }
            }
        }

        imprimirMatriz(stringMatrix, titulo, nombresFilas, nombresColumnas, color, justificar);
    }

    public static void imprimirMatriz(double[][] matriz, int decimales, String titulo) {
        imprimirMatriz(matriz, decimales, titulo, null, null, titulo, JUSTIFICAR_DERECHA);
    }

    public static void imprimirMatriz(double[][] matriz, String titulo) {
        imprimirMatriz(matriz, titulo, null, null);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados. Si
     * se suministran los nombres de las filas y de las columnas se mostrarán en
     * color azul. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos en color azul. Los valores de la matriz se muestran en color
     * negro.
     *
     * @param matriz Matriz de tipo real.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     * @param justificar Valor entero que indica el justificado con que se
     * mostrarán los valores y los nombres de las filas y columnas. El valor
     * cero justificará el texto a la derecha y un valor diferente de cero lo
     * justificará a la izquierda.
     */
    public static void mostrarMatriz(double[][] matriz, String[] nombresFilas, String[] nombresColumnas, int justificar) {
        mostrarMatriz(matriz, -1, "", nombresFilas, nombresColumnas, null, justificar);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados. Si
     * se suministran los nombres de las filas y de las columnas se mostrarán
     * con el color indicado. Si no se suministran los nombres de las filas y/o
     * de las columnas se mostrará el número de la fila y/o columna seguido de
     * dos puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro.
     *
     * @param matriz Matriz de tipo real.
     * @param decimales Valor entero no negativo que indica la cantidad de
     * decimales con que se mostrarán los valores de la matriz.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     * @param color Cadena que representa el color. Debe ser un valor de la
     * clase Consola.
     */
    public static void mostrarMatriz(double[][] matriz, int decimales, String[] nombresFilas, String[] nombresColumnas, String color) {
        mostrarMatriz(matriz, decimales, null, nombresFilas, nombresColumnas, color, 0);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados. Si
     * se suministran los nombres de las filas y de las columnas se mostrarán en
     * color azul. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos en color azul. Los valores de la matriz se muestran en color
     * negro.
     *
     * @param matriz Matriz de tipo real.
     * @param decimales Valor entero no negativo que indica la cantidad de
     * decimales con que se mostrarán los valores de la matriz.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     */
    public static void mostrarMatriz(double[][] matriz, int decimales, String[] nombresFilas, String[] nombresColumnas) {
        mostrarMatriz(matriz, decimales, nombresFilas, nombresColumnas, Consola.BLUE);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados.
     * Muestra el número de cada fila y de cada columna seguido de dos puntos en
     * el color indicado. Los valores de la matriz se muestran en color negro.
     *
     * @param matriz Matriz de tipo real.
     * @param decimales Valor entero no negativo que indica la cantidad de
     * decimales con que se mostrarán los valores de la matriz.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     */
    public static void mostrarMatriz(double[][] matriz, int decimales, String titulo) {
        mostrarMatriz(matriz, decimales, titulo, null, null, null, 0);
    }

    /**
     * Muestra por pantalla una matriz de reales con los decimales indicados.
     * Muestra el número de cada fila y de cada columna seguido de dos puntos en
     * color azul. Los valores de la matriz se muestran en color negro.
     *
     * @param matriz Matriz de tipo real.
     * @param decimales Valor entero no negativo que indica la cantidad de
     * decimales con que se mostrarán los valores de la matriz.
     */
    public static void mostrarMatriz(double[][] matriz, int decimales) {
        mostrarMatriz(matriz, decimales, null, null, Consola.BLUE);
    }

    /**
     * Muestra por pantalla una matriz de reales. Si se suministran los nombres
     * de las filas y de las columnas se mostrarán con el color indicado. Si no
     * se suministran los nombres de las filas y/o de las columnas se mostrará
     * el número de la fila y/o columna seguido de dos puntos con el color
     * indicado. Los valores de la matriz se muestran en color negro.
     *
     * @param matriz Matriz de tipo real.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     * @param color Cadena que representa el color. Debe ser un valor de la
     * clase Consola.
     */
    public static void mostrarMatriz(double[][] matriz, String[] nombresFilas, String[] nombresColumnas, String color) {
        mostrarMatriz(matriz, -1, nombresFilas, nombresColumnas, color);
    }

    /**
     * Muestra por pantalla una matriz de reales. Muestra el número de cada fila
     * y cada columna seguido de dos puntos con el color indicado. Los valores
     * de la matriz se muestran en color negro.
     *
     * @param matriz Matriz de tipo real.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     */
    public static void mostrarMatriz(double[][] matriz, String titulo) {
        mostrarMatriz(matriz, -1, titulo, null, null, null, 0);
    }

    /**
     * Muestra por pantalla una matriz de reales. Si se suministran los nombres
     * de las filas y de las columnas se mostrarán en color azul. Si no se
     * suministran los nombres de las filas y/o de las columnas se mostrará el
     * número de la fila y/o columna seguido de dos puntos en el color azul. Los
     * valores de la matriz se muestran en color negro.
     *
     * @param matriz Matriz de tipo real.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     */
    public static void mostrarMatriz(double[][] matriz, String[] nombresFilas, String[] nombresColumnas) {
        mostrarMatriz(matriz, -1, nombresFilas, nombresColumnas, Consola.BLUE);
    }

    /**
     * Muestre por pantalla una matriz con valores reales. No se numeran las
     * filas ni las columnas.
     *
     * @param matriz Matriz de reales
     */
    public static void mostrarMatriz(double[][] matriz) {
        int mayor = 0;
        int decimales = 0;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                String ct = matriz[i][j] + "";
                String[] div = ct.split("\\.");

                int longitud = ct.length();
                if (longitud > mayor) {
                    mayor = longitud;
                }

                if (div.length > 1 && div[1].length() > decimales) {
                    decimales = div[1].length();
                }
            }
        }

        int espacios = 2 + mayor;
        String formato = "%" + espacios + "." + decimales + "f";

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf(formato, matriz[i][j]);
            }
            System.out.println();
        }
    }

    public static void imprimirMatriz(double[][] matriz) {
        int mayor = 0;
        int decimales = 0;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                String ct = matriz[i][j] + "";
                String[] div = ct.split("\\.");

                int longitud = ct.length();
                if (longitud > mayor) {
                    mayor = longitud;
                }

                if (div.length > 1 && div[1].length() > decimales) {
                    decimales = div[1].length();
                }
            }
        }

        int espacios = 2 + mayor;
        String formato = "%" + espacios + "." + decimales + "f";

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf(formato, matriz[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Muestra por pantalla una matriz de tipo String. Si se suministran los
     * nombres de las filas y de las columnas se mostrarán con el color
     * indicado. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro. El título se muestra en color negro y centrado.
     *
     * @param matriz Matriz de tipo String.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     */
    public static void mostrarMatriz(String[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        mostrarMatriz(matriz, titulo, nombresFilas, nombresColumnas, null, JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de tipo String. Si se suministran los
     * nombres de las filas y de las columnas se mostrarán con el color
     * indicado. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro. El título se muestra en color negro y centrado.
     *
     * @param matriz Matriz de tipo String.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     */
    public static void mostrarMatriz(String[][] matriz, String titulo) {
        mostrarMatriz(matriz, titulo, null, null, null, JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de tipo String. Si se suministran los
     * nombres de las filas y de las columnas se mostrarán con el color
     * indicado. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro. El título se muestra en color negro y centrado.
     *
     * @param matriz Matriz de tipo String.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param color Cadena que representa el color. Debe ser un valor de la
     * clase Consola.
     */
    public static void mostrarMatriz(String[][] matriz, String titulo, String color) {
        mostrarMatriz(matriz, titulo, null, null, color, JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de tipo String. Si se suministran los
     * nombres de las filas y de las columnas se mostrarán con el color
     * indicado. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro. El título se muestra en color negro y centrado.
     *
     * @param matriz Matriz de tipo String.
     *
     */
    public static void mostrarMatriz(String[][] matriz) {
        mostrarMatriz(matriz, null, null, null, null, JUSTIFICAR_DERECHA);
    }

    public static void imprimirMatriz(String[][] matriz) {
        imprimirMatriz(matriz, null, null, null, null, JUSTIFICAR_DERECHA);
    }

    /**
     * Muestra por pantalla una matriz de tipo String. Si se suministran los
     * nombres de las filas y de las columnas se mostrarán con el color
     * indicado. Si no se suministran los nombres de las filas y/o de las
     * columnas se mostrará el número de la fila y/o columna seguido de dos
     * puntos con el color indicado. Los valores de la matriz se muestran en
     * color negro. El título se muestra en color negro y centrado.
     *
     * @param matriz Matriz de tipo String.
     * @param titulo Cadena con el título de la matriz. La cadena se muestra en
     * color negro y centrada.
     * @param nombresFilas Vector con los nombres de cada una de las filas de la
     * matriz.
     * @param nombresColumnas Vector con los nombres de cada una de las columnas
     * de la matriz.
     * @param color Cadena que representa el color. Debe ser un valor de la
     * clase Consola.
     * @param justificar Valor entero que indica el justificado con que se
     * mostrarán los valores y los nombres de las filas y columnas. El valor
     * cero justificará el texto a la derecha y un valor diferente de cero lo
     * justificará a la izquierda.
     */
    public static void mostrarMatriz(String[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }
        if (!Consola.isColor(color)) {
            color = Consola.BLUE;
        }

        int mayorFila = 0;
        if (nombresFilas != null && nombresFilas.length == matriz.length) {
            for (int i = 0; i < nombresFilas.length; i++) {
                if (nombresFilas[i].length() > mayorFila) {
                    mayorFila = nombresFilas[i].length();
                }
            }
        } else {
            mayorFila = (matriz.length + "").length();
            nombresFilas = null;
        }

        int[] mayorColumnas = new int[matriz[0].length];
        int mayorColumna = 0;
        if (nombresColumnas != null && nombresColumnas.length == matriz[0].length) {
            int i = 0;
            for (String nombreColumna : nombresColumnas) {
                if (nombreColumna.length() > mayorColumna) {
                    mayorColumna = nombreColumna.length();
                }
                mayorColumnas[i] = nombreColumna.length();
                i++;
            }
        } else {
            mayorColumna = (matriz[0].length + "").length();
            nombresColumnas = null;
        }
        int columnaMayorLongitud = 0;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i] != null) {
                int longitud = matriz[i].length;

                if (longitud > columnaMayorLongitud) {
                    columnaMayorLongitud = longitud;
                }
                longitud = (longitud + "").length();

                if (longitud > mayorColumna) {
                    mayorColumna = longitud;
                }
                for (int j = 0; j < matriz[i].length; j++) {
                    if (matriz[i][j] != null) {
                        longitud = matriz[i][j].length();
                    } else {
                        longitud = 0;
                    }
                    if (longitud > mayorColumna) {
                        mayorColumna = longitud;
                    }
                    if (longitud > mayorColumnas[j]) {
                        mayorColumnas[j] = longitud;
                    }
                }
            } else {
                System.out.println("Error: Hay una posición vacía en la matriz.");
                return;
            }
        }
        if (mayorColumna == 0) {
            return;
        }

        if (justificar != JUSTIFICAR_DERECHA) {
            ++mayorFila;
        }

//        int espacios = 2 + mayorColumna;
        // Mostrar titulo
        if (titulo != null && titulo.length() > 0) {
            int posicionTitulo = (mayorFila + 2) - titulo.length();

            for (int i = 0; i < matriz[0].length; i++) {
                posicionTitulo += 2 + mayorColumnas[i];
            }
            posicionTitulo /= 2;
            for (int i = 0; i < posicionTitulo; i++) {
                System.out.print(" ");
            }
            System.out.println(titulo);
        }
        System.out.printf("%" + (mayorFila + 1) + "s", "");

        // Mostrar etiquetas de las columnas
        String espaciosEnBlanco = "";
        if (nombresColumnas != null) {
            int k = 0;
            for (String nombresColumna : nombresColumnas) {
//                int delta = espacios - nombresColumna.length();
                int delta = 2 + mayorColumnas[k] - nombresColumna.length();
                k++;
                espaciosEnBlanco = "";

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    Consola.print(nombresColumna, color);
                } else {
                    Consola.print(nombresColumna, color);
                    System.out.print(espaciosEnBlanco);
                }
//                Consola.print(nombresColumna, color);
            }
        } else {
            for (int j = 0; j < matriz[0].length; j++) {
                espaciosEnBlanco = "";
                String etiquetaColumna = (j + 1) + ":";
                int delta = 2 + mayorColumnas[j] - etiquetaColumna.length();

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    Consola.print(etiquetaColumna, color);
                } else {
                    Consola.print(etiquetaColumna, color);
                    System.out.print(espaciosEnBlanco);
                }
            }
        }
        System.out.println();

        // Mostrar filas y elementos matriz
        for (int i = 0; i < matriz.length; i++) {
            String textoFila;
            espaciosEnBlanco = "";
            // Mostrar etiqueta de la fila
            if (nombresFilas == null) {
                textoFila = (i + 1) + ":";
            } else {
                textoFila = nombresFilas[i];
            }

            int delta = (mayorFila + 1) - textoFila.length();

            for (int k = 0; k < delta; k++) {
                espaciosEnBlanco += " ";
            }
            if (justificar == JUSTIFICAR_DERECHA) {
                System.out.print(espaciosEnBlanco);
                Consola.print(textoFila, color);
            } else {
                Consola.print(textoFila, color);
                System.out.print(espaciosEnBlanco);
            }

            // Mostrar elementos de la matriz
            int j = 0;
            for (String item : matriz[i]) {
                if (item == null) {
                    continue;
                };
                delta = 2 + mayorColumnas[j] - item.length();
                j++;
                espaciosEnBlanco = "";
                for (int k = 0; k < delta; k++) {
                    espaciosEnBlanco += " ";
                }
                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco + item);
                } else {
                    System.out.print(item + espaciosEnBlanco);
                }
            }
            System.out.println();
        }
    }

    public static void mostrarMatriz(char[][] datos) {
        String[][] matriz = new String[datos.length][datos[0].length];

        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                matriz[i][j] = datos[i][j] + "";
            }
        }
        mostrarMatriz(matriz);
    }

    public static void mostrarMatriz(char[][] datos, String titulo) {
        String[][] matriz = new String[datos.length][datos[0].length];

        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                matriz[i][j] = datos[i][j] + "";
            }
        }
        mostrarMatriz(matriz, titulo);
    }

    public static void imprimirMatriz(char[][] datos) {
        String[][] matriz = new String[datos.length][datos[0].length];

        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                matriz[i][j] = datos[i][j] + "";
            }
        }
        imprimirMatriz(matriz);
    }

    public static void imprimirMatriz(char[][] datos, String titulo) {
        String[][] matriz = new String[datos.length][datos[0].length];

        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                matriz[i][j] = datos[i][j] + "";
            }
        }
        imprimirMatriz(matriz, titulo);
    }

    public static void imprimirMatriz(String[][] matriz, String titulo) {
        imprimirMatriz(matriz, titulo, null, null, null, JUSTIFICAR_DERECHA);
    }

    public static void imprimirMatriz(String[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas) {
        imprimirMatriz(matriz, titulo, nombresFilas, nombresColumnas, null, JUSTIFICAR_DERECHA);
    }

    public static void imprimirMatriz(String[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }
        if (!Consola.isColor(color)) {
            color = Consola.BLUE;
        }

        int mayorFila = 0;
        if (nombresFilas != null && nombresFilas.length == matriz.length) {
            for (int i = 0; i < nombresFilas.length; i++) {
                if (nombresFilas[i].length() > mayorFila) {
                    mayorFila = nombresFilas[i].length();
                }
            }
        } else {
            mayorFila = (matriz.length + "").length();
            nombresFilas = null;
        }

        int mayorColumna = 0;
        if (nombresColumnas != null && nombresColumnas.length == matriz[0].length) {
            for (String nombresColumna : nombresColumnas) {
                if (nombresColumna.length() > mayorColumna) {
                    mayorColumna = nombresColumna.length();
                }
            }
        } else {
            mayorColumna = (matriz[0].length + "").length();
            nombresColumnas = null;
        }
        int columnaMayorLongitud = 0;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i] != null) {
                int longitud = matriz[i].length;

                if (longitud > columnaMayorLongitud) {
                    columnaMayorLongitud = longitud;
                }
                longitud = (longitud + "").length();

                if (longitud > mayorColumna) {
                    mayorColumna = longitud;
                }
                for (int j = 0; j < matriz[i].length; j++) {
                    longitud = matriz[i][j].length();
                    if (longitud > mayorColumna) {
                        mayorColumna = longitud;
                    }
                }
            } else {
                System.out.println("Error: Hay una posición vacía en la matriz.");
                return;
            }
        }
        if (mayorColumna == 0) {
            return;
        }

        if (justificar != JUSTIFICAR_DERECHA) {
            ++mayorFila;
        }

        int espacios = 2 + mayorColumna;
        // Mostrar titulo
        if (titulo != null && titulo.length() > 0) {
            int posicionTitulo = (espacios * columnaMayorLongitud) + (mayorFila + 2) - titulo.length();
            posicionTitulo /= 2;
            for (int i = 0; i < posicionTitulo; i++) {
                System.out.print(" ");
            }
            System.out.println(titulo);
        }
        System.out.printf("%" + (mayorFila + 1) + "s", "");

        // Mostrar etiquetas de las columnas
        String espaciosEnBlanco = "";
        if (nombresColumnas != null) {
            for (String nombresColumna : nombresColumnas) {
                int delta = espacios - nombresColumna.length();
                espaciosEnBlanco = "";

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    Consola.print(nombresColumna, color);
                } else {
                    Consola.print(nombresColumna, color);
                    System.out.print(espaciosEnBlanco);
                }
//                Consola.print(nombresColumna, color);
            }
        } else {
            for (int j = 0; j < columnaMayorLongitud; j++) {
                espaciosEnBlanco = "";
                String etiquetaColumna = j + ":";
                int delta = espacios - etiquetaColumna.length();

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    Consola.print(etiquetaColumna, color);
                } else {
                    Consola.print(etiquetaColumna, color);
                    System.out.print(espaciosEnBlanco);
                }
            }
        }
        System.out.println();

        // Mostrar filas y elementos matriz
        for (int i = 0; i < matriz.length; i++) {
            String textoFila;
            espaciosEnBlanco = "";
            // Mostrar etiqueta de la fila
            if (nombresFilas == null) {
                textoFila = i + ":";
            } else {
                textoFila = nombresFilas[i];
            }

            int delta = (mayorFila + 1) - textoFila.length();

            for (int k = 0; k < delta; k++) {
                espaciosEnBlanco += " ";
            }
            if (justificar == JUSTIFICAR_DERECHA) {
                System.out.print(espaciosEnBlanco);
                Consola.print(textoFila, color);
            } else {
                Consola.print(textoFila, color);
                System.out.print(espaciosEnBlanco);
            }

            // Mostrar elementos de la matriz
            for (String item : matriz[i]) {
                delta = espacios - item.length();
                espaciosEnBlanco = "";
                for (int k = 0; k < delta; k++) {
                    espaciosEnBlanco += " ";
                }
                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco + item);
                } else {
                    System.out.print(item + espaciosEnBlanco);
                }
            }
            System.out.println();
        }
    }

    public static void imprimir(String[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }
        if (!Consola.isColor(color)) {
            color = Consola.BLUE;
        }

        int mayorFila = 0;
        if (nombresFilas != null && nombresFilas.length == matriz.length) {
            for (int i = 0; i < nombresFilas.length; i++) {
                if (nombresFilas[i].length() > mayorFila) {
                    mayorFila = nombresFilas[i].length();
                }
            }
        } else {
            mayorFila = (matriz.length + "").length();
            nombresFilas = null;
        }

        int mayorColumna = 0;
        if (nombresColumnas != null && nombresColumnas.length == matriz[0].length) {
            for (String nombresColumna : nombresColumnas) {
                if (nombresColumna.length() > mayorColumna) {
                    mayorColumna = nombresColumna.length();
                }
            }
        } else {
            mayorColumna = (matriz[0].length + "").length();
            nombresColumnas = null;
        }
        int columnaMayorLongitud = 0;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i] != null) {
                int longitud = matriz[i].length;

                if (longitud > columnaMayorLongitud) {
                    columnaMayorLongitud = longitud;
                }
                longitud = (longitud + "").length();

                if (longitud > mayorColumna) {
                    mayorColumna = longitud;
                }
                for (int j = 0; j < matriz[i].length; j++) {
                    longitud = matriz[i][j].length();
                    if (longitud > mayorColumna) {
                        mayorColumna = longitud;
                    }
                }
            } else {
                System.out.println("Error: Hay una posición vacía en la matriz.");
                return;
            }
        }
        if (mayorColumna == 0) {
            return;
        }

        if (justificar != JUSTIFICAR_DERECHA) {
            ++mayorFila;
        }

        int espacios = 2 + mayorColumna;
        // Mostrar titulo
        if (titulo != null && titulo.length() > 0) {
            int posicionTitulo = (espacios * columnaMayorLongitud) + (mayorFila + 2) - titulo.length();
            posicionTitulo /= 2;
            for (int i = 0; i < posicionTitulo; i++) {
                System.out.print(" ");
            }
            System.out.println(titulo);
        }
        System.out.printf("%" + (mayorFila + 1) + "s", "");

        // Mostrar etiquetas de las columnas
        String espaciosEnBlanco = "";
        if (nombresColumnas != null) {
            for (String nombresColumna : nombresColumnas) {
                int delta = espacios - nombresColumna.length();
                espaciosEnBlanco = "";

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    System.out.print(nombresColumna);
                } else {
                    System.out.print(nombresColumna);
                    System.out.print(espaciosEnBlanco);
                }
//                Consola.print(nombresColumna, color);
            }
        } else {
            for (int j = 0; j < columnaMayorLongitud; j++) {
                espaciosEnBlanco = "";
                String etiquetaColumna = j + ":";
                int delta = espacios - etiquetaColumna.length();

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    System.out.print(etiquetaColumna);
                } else {
                    System.out.print(etiquetaColumna);
                    System.out.print(espaciosEnBlanco);
                }
            }
        }
        System.out.println();

        // Mostrar filas y elementos matriz
        for (int i = 0; i < matriz.length; i++) {
            String textoFila;
            espaciosEnBlanco = "";
            // Mostrar etiqueta de la fila
            if (nombresFilas == null) {
                textoFila = i + ":";
            } else {
                textoFila = nombresFilas[i];
            }

            int delta = (mayorFila + 1) - textoFila.length();

            for (int k = 0; k < delta; k++) {
                espaciosEnBlanco += " ";
            }
            if (justificar == JUSTIFICAR_DERECHA) {
                System.out.print(espaciosEnBlanco);
                System.out.print(textoFila);
            } else {
                System.out.print(textoFila);
                System.out.print(espaciosEnBlanco);
            }

            // Mostrar elementos de la matriz
            for (String item : matriz[i]) {
                delta = espacios - item.length();
                espaciosEnBlanco = "";
                for (int k = 0; k < delta; k++) {
                    espaciosEnBlanco += " ";
                }
                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco + item);
                } else {
                    System.out.print(item + espaciosEnBlanco);
                }
            }
            System.out.println();
        }
    }

    public static void imprimirArregoBidimensional(int[][] arreglo) {
        String[][] matriz = new String[arreglo.length][];

        for (int i = 0; i < arreglo.length; i++) {
            matriz[i] = new String[arreglo[i].length];
        }

        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo[i].length; j++) {
                matriz[i][j] = arreglo[i][j] + "";
            }
        }

        imprimirMatriz(matriz, null, null, null, null, JUSTIFICAR_DERECHA);
    }

    private static void imprimirArregoBidimensional(String[][] matriz, String titulo, String[] nombresFilas, String[] nombresColumnas, String color, int justificar) {
        if (matriz == null) {
            return;
        }
        if (matriz.length == 0) {
            return;
        }
        if (!Consola.isColor(color)) {
            color = Consola.BLUE;
        }

        int mayorFila = 0;
        if (nombresFilas != null && nombresFilas.length == matriz.length) {
            for (int i = 0; i < nombresFilas.length; i++) {
                if (nombresFilas[i].length() > mayorFila) {
                    mayorFila = nombresFilas[i].length();
                }
            }
        } else {
            mayorFila = (matriz.length + "").length();
            nombresFilas = null;
        }

        int mayorColumna = 0;
        if (nombresColumnas != null && nombresColumnas.length == matriz[0].length) {
            for (String nombresColumna : nombresColumnas) {
                if (nombresColumna.length() > mayorColumna) {
                    mayorColumna = nombresColumna.length();
                }
            }
        } else {
            mayorColumna = (matriz[0].length + "").length();
            nombresColumnas = null;
        }
        int columnaMayorLongitud = 0;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i] != null) {
                int longitud = matriz[i].length;

                if (longitud > columnaMayorLongitud) {
                    columnaMayorLongitud = longitud;
                }
                longitud = (longitud + "").length();

                if (longitud > mayorColumna) {
                    mayorColumna = longitud;
                }
                for (int j = 0; j < matriz[i].length; j++) {
                    longitud = matriz[i][j].length();
                    if (longitud > mayorColumna) {
                        mayorColumna = longitud;
                    }
                }
            } else {
                System.out.println("Error: Hay una posición vacía en la matriz.");
                return;
            }
        }
        if (mayorColumna == 0) {
            return;
        }

        if (justificar != JUSTIFICAR_DERECHA) {
            ++mayorFila;
        }

        int espacios = 2 + mayorColumna;
        // Mostrar titulo
        if (titulo != null && titulo.length() > 0) {
            int posicionTitulo = (espacios * columnaMayorLongitud) + (mayorFila + 2) - titulo.length();
            posicionTitulo /= 2;
            for (int i = 0; i < posicionTitulo; i++) {
                System.out.print(" ");
            }
            System.out.println(titulo);
        }
        System.out.printf("%" + (mayorFila + 1) + "s", "");

        // Mostrar etiquetas de las columnas
        String espaciosEnBlanco = "";
        if (nombresColumnas != null) {
            for (String nombresColumna : nombresColumnas) {
                int delta = espacios - nombresColumna.length();
                espaciosEnBlanco = "";

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
//                    System.out.print(espaciosEnBlanco);
                    Consola.print(nombresColumna, color);
                } else {
                    Consola.print(nombresColumna, color);
                    System.out.print(espaciosEnBlanco);
                }
//                Consola.print(nombresColumna, color);
            }
        } else {
            for (int j = 0; j < columnaMayorLongitud; j++) {
                espaciosEnBlanco = "";
                String etiquetaColumna = j + ":";
                int delta = espacios - etiquetaColumna.length();

                for (int i = 0; i < delta; i++) {
                    espaciosEnBlanco += " ";
                }

                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco);
                    Consola.print(etiquetaColumna, color);
                } else {
                    Consola.print(etiquetaColumna, color);
                    System.out.print(espaciosEnBlanco);
                }
            }
        }
        System.out.println();

        // Mostrar filas y elementos matriz
        for (int i = 0; i < matriz.length; i++) {
            String textoFila;
            espaciosEnBlanco = "";
            // Mostrar etiqueta de la fila
            if (nombresFilas == null) {
                textoFila = i + ":";
            } else {
                textoFila = nombresFilas[i];
            }

            int delta = (mayorFila + 1) - textoFila.length();

            for (int k = 0; k < delta; k++) {
                espaciosEnBlanco += " ";
            }
            if (justificar == JUSTIFICAR_DERECHA) {
                System.out.print(espaciosEnBlanco);
                Consola.print(textoFila, color);
            } else {
                Consola.print(textoFila, color);
                System.out.print(espaciosEnBlanco);
            }

            // Mostrar elementos de la matriz
            for (String item : matriz[i]) {
                delta = espacios - item.length();
                espaciosEnBlanco = "";
                for (int k = 0; k < delta; k++) {
                    espaciosEnBlanco += " ";
                }
                if (justificar == JUSTIFICAR_DERECHA) {
                    System.out.print(espaciosEnBlanco + item);
                } else {
                    System.out.print(item + espaciosEnBlanco);
                }
            }
            System.out.println();
        }
    }

    public static void dibujar(String[][] matriz) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null) {
            return;
        }
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaArreglo ventana = new VentanaArreglo(matriz);

        ventana.setVisible(true);
    }

    public static void dibujar(String[][] matriz, String titulo) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null) {
            return;
        }
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaArreglo ventana = new VentanaArreglo(matriz, titulo);

        ventana.setVisible(true);
    }

    public static void dibujar(int[][] matriz) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null) {
            return;
        }
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaArreglo ventana = new VentanaArreglo(matriz);

        ventana.setVisible(true);
    }

    public static void dibujar(double[][] matriz) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null) {
            return;
        }
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaArreglo ventana = new VentanaArreglo(matriz);

        ventana.setVisible(true);
    }

    public static void dibujar(int[][] matriz, String titulo) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null) {
            return;
        }
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaArreglo ventana = new VentanaArreglo(matriz);

        ventana.setVisible(true);
    }

    public static void dibujar(double[][] matriz, String titulo) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null) {
            return;
        }
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaArreglo ventana = new VentanaArreglo(matriz);

        ventana.setVisible(true);
    }
}
