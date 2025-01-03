/*
 * Copyright (C) 2020 Oscar Arenas
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
package com.oharenas.math;

import com.oharenas.util.Util;

/**
 * Compressed Sparse Row (CSR).
 *
 * @author Oscar Arenas
 */
public class SparseMatrix {

    private static final int CAPACIDAD_INICIAL = 8;

    private int filas;
    private int columnas;
    //
    private double[] valores;
    private int[] indicesColumnas;
    private int[] indicesFilaAColumna;
    private int n; // Cantidad de tripletas

    public SparseMatrix(int rows, int columns) {
        if (rows < 1) {
            throw new IllegalArgumentException("Las filas deben ser mayores que 1.");
        }
        if (columns < 1) {
            throw new IllegalArgumentException("Las columnas deben ser mayores que 1.");
        }

        this.filas = rows;
        this.columnas = columns;
        n = 0;
        valores = new double[CAPACIDAD_INICIAL];
        indicesColumnas = new int[CAPACIDAD_INICIAL];
        indicesFilaAColumna = new int[rows + 1];
    }

    public boolean add(int row, int column, double value) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (value == 0) {
            throw new IllegalArgumentException("El valor debe ser diferente de cero");
        }
        if (row < 0 || row >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + row);
        }
        if (column < 0 || column >= columnas) {
            throw new IndexOutOfBoundsException("Columna fuera de rango: " + column);
        }

        if (valores.length == n) {
            resize(2 * n);
        }

        int i = indicesFilaAColumna[row];

        // Buscamos el punto de inserción en el vector 'valores e 
        // 'indicesColumnas' 
        while (i < indicesFilaAColumna[row + 1] && indicesColumnas[i] < column) {
            i++;
        }

        if (i < indicesFilaAColumna[row + 1] && indicesColumnas[i] == column) {
            return false; // No se permiten valores repetidos
        }

        // Se desplazan los valores hasta 'i' una posición hacia atras para   
        // abrir el espacio para el valor y la columna de la nueva tripleta.
        for (int j = n; j > i; j--) {
            valores[j] = valores[j - 1];
            indicesColumnas[j] = indicesColumnas[j - 1];
        }

        indicesColumnas[i] = column;
        valores[i] = value;

        // Se incrementa en una unidad todas las posiciones después de la 
        // recién insertada  
        for (int j = row + 1; j <= filas; j++) {
            indicesFilaAColumna[j]++;
        }

        n++;
        return true;
    }

    public boolean remove(int row, int column) {
        if (n > 0 && row >= 0 && row < filas && column >= 0 && column < columnas) {
            int i = indicesFilaAColumna[row];

            // Buscamos el valor mediante la fila y la columna
            while (i < indicesFilaAColumna[row + 1] && indicesColumnas[i] < column) {
                i++;
            }

            // Se encontró la posición a borrar.
            if (i < indicesFilaAColumna[row + 1] && indicesColumnas[i] == column) {
                n--;
                for (int j = i; j < n; j++) {
                    valores[j] = valores[j + 1];
                    indicesColumnas[j] = indicesColumnas[j + 1];
                }

                for (int j = row + 1; j <= filas; j++) {
                    indicesFilaAColumna[j]--;
                }

                if (n > 0 && valores.length / 4 == n) {
                    resize(valores.length / 2);
                }
                return true;
            }
        }
        return false;
    }

    public boolean set(int row, int column, double value) {
        if (n > 0 && value != 0 && row >= 0 && row < filas && column >= 0 && column < columnas) {
            int i = indicesFilaAColumna[row];

            // Buscamos el valor mediante la fila y la columna
            while (i < indicesFilaAColumna[row + 1] && indicesColumnas[i] < column) {
                i++;
            }

            // Se encontró la posición a modificar.
            if (i < indicesFilaAColumna[row + 1] && indicesColumnas[i] == column) {
                valores[i] = value;
                return true;
            }
        }
        return false;
    }

    public double getValue(int row, int column) {
        if (row >= 0 && row < filas && column >= 0 && column < columnas) {
            for (int i = indicesFilaAColumna[row]; i < indicesFilaAColumna[row + 1]; i++) {
                if (column == indicesColumnas[i]) {
                    return valores[i];
                }
            }
            return 0;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean isEqualTo(SparseMatrix other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (n > 0 && n == other.n && filas == other.filas && columnas == other.columnas) {

            for (int i = 0; i < n; i++) {
                if (valores[i] != other.valores[i] || indicesColumnas[i] != other.indicesColumnas[i]) {
                    return false;
                }
            }
            for (int i = 0; i < indicesFilaAColumna.length; i++) {
                if (indicesFilaAColumna[i] != other.indicesFilaAColumna[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public double dispertion() {
        return n / (double) (filas * columnas);
    }

    public int size() {
        return n;
    }

    private void resize(int nc) {
        if (nc > 0 && nc >= n) {
            double[] auxiliar1 = new double[nc];
            int[] auxiliar2 = new int[nc];

            for (int i = 0; i < n; i++) {
                auxiliar1[i] = valores[i];
                auxiliar2[i] = indicesColumnas[i];
            }
            valores = auxiliar1;
            indicesColumnas = auxiliar2;
        }
    }

    public void clear() {
        n = 0;
        valores = new double[CAPACIDAD_INICIAL];
        indicesColumnas = new int[CAPACIDAD_INICIAL];
        indicesFilaAColumna = new int[indicesFilaAColumna.length];
    }

    public SparseMatrix copy() {
        SparseMatrix sparseMatrix = new SparseMatrix(filas, columnas);

        sparseMatrix.valores = valores.clone();
        sparseMatrix.indicesColumnas = indicesColumnas.clone();
        sparseMatrix.indicesFilaAColumna = indicesFilaAColumna.clone();
        sparseMatrix.n = n;

        return sparseMatrix;
    }

    public int getRows() {
        return filas;
    }

    public int getColumns() {
        return columnas;
    }

    @Override
    public String toString() {
        String cadena = "";

        for (int i = 0; i < filas; i++) {
            for (int j = indicesFilaAColumna[i]; j < indicesFilaAColumna[i + 1]; j++) {
                cadena += "(" + i + ", " + indicesColumnas[j] + ", " + Util.realAString(valores[j]) + ")\n";
            }
        }
        return cadena;
    }
}
