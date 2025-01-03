/*
 * Copyright (C) 2021 Oscar Arenas
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
package com.oharenas.ed.jerarquica.grafos;

import com.oharenas.util.Util;
import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public class Graph implements IGraph {

    private double[][] matrix;
    private int amountEdges;

    public Graph(int n) {
        matrix = new double[n][n];
        amountEdges = 0;
    }

    @Override
    public boolean addEdge(int v, int w) {
        return addEdge(v, w, 1);
    }

    public boolean addEdge(int v, int w, double weight) {
        boolean ans = containsVertex(v) && containsVertex(w) && v != w;
        ans = ans && weight != 0 && matrix[v][w] == 0;

        if (ans) {
            matrix[v][w] = weight;
            matrix[w][v] = weight;
            amountEdges++;
        }
        return ans;
    }

    @Override
    public boolean removeEdge(int v, int w) {
        boolean ans = containsVertex(v) && containsVertex(w) && v != w;
        ans = ans && matrix[v][w] != 0;

        if (ans) {
            matrix[v][w] = 0;
            matrix[w][v] = 0;
            amountEdges--;
        }
        return ans;
    }

    @Override
    public void addVertex() {
        int n = matrix.length;
        double[][] aux = new double[n + 1][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                aux[i][j] = matrix[i][j];
            }
        }
        matrix = aux;
    }

    @Override
    public boolean removeVertex(int v) {
        if (containsVertex(v)) {
            if (matrix.length > 1) {
                amountEdges -= adjacents(v).size();

                int n = matrix.length;
                double[][] auxiliar = new double[n - 1][n - 1];

                int row = 0;
                for (int i = 0; i < n; i++) {
                    if (i != v) {
                        int column = 0;
                        for (int j = 0; j < n; j++) {
                            if (j != v) {
                                auxiliar[row][column++] = matrix[i][j];
                            }
                        }
                        row++;
                    }
                }
                matrix = auxiliar;
            } else {
                clear();
            }
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Integer> adjacents(int v) {
        ArrayList<Integer> vecinos = new ArrayList<>();

        if (containsVertex(v)) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[v][i] != 0) {
                    vecinos.add(i);
                }
            }
        }
        return vecinos;
    }

    @Override
    public boolean containsEdge(int v, int w) {
        boolean ans = containsVertex(v) && containsVertex(w);

        return ans && matrix[v][w] != 0;
    }

    @Override
    public boolean containsVertex(int v) {
        return v >= 0 && v < matrix.length;
    }

    @Override
    public boolean isEmpty() {
        return matrix.length == 0;
    }

    @Override
    public void clear() {
        matrix = new double[0][0];
        amountEdges = 0;
    }

    @Override
    public int verteces() {
        return matrix.length;
    }

    @Override
    public int edges() {
        return amountEdges;
    }

    @Override
    public boolean setWeight(int v, int w, int weight) {
        boolean ans = containsVertex(v) && containsVertex(w) && v != w;
        ans = ans && weight != 0 && matrix[v][w] != 0;

        if (ans) {
            matrix[v][w] = weight;
            matrix[w][v] = weight;
        }
        return ans;
    }

    public void showAdjacencyMatrix() {
        String cadena = "";
        int n = matrix.length;
        int longitudPrimeraColumna = (n + "").length();
        int[] mayorPorColumnas = new int[n];
        String[][] pesos = new String[n][n];

        longitudPrimeraColumna++;
        for (int i = 0; i < n; i++) {
            mayorPorColumnas[i] = 2;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                pesos[i][j] = Util.realAString(matrix[i][j]);
                int caracteres = pesos[i][j].length();
                if (caracteres > mayorPorColumnas[j]) {
                    mayorPorColumnas[j] = caracteres;
                }
                caracteres = (j + "").length() + 1;
                if (caracteres > mayorPorColumnas[j]) {
                    mayorPorColumnas[j] = caracteres;
                }
            }
        }
        // Encabezado columnas
        longitudPrimeraColumna++;
        for (int i = 0; i < longitudPrimeraColumna; i++) {
            cadena += " ";
        }
        for (int i = 0; i < n; i++) {
            mayorPorColumnas[i]++;//Sumamos el espacio en blanco entre columnas
            int cifras = (i + "").length() + 1;
            int delta = mayorPorColumnas[i] - cifras;
            for (int k = 0; k < delta; k++) {
                cadena += " ";
            }
            cadena += i + ":";
        }
        cadena += "\n";
        // Mostrar pesos y encabezado de filas
        for (int i = 0; i < n; i++) {
            int cifras = (i + "").length() + 1;
            int delta = longitudPrimeraColumna - cifras;
            for (int r = 0; r < delta; r++) {
                cadena += " ";
            }
            cadena += i + ":";
            for (int j = 0; j < n; j++) {
                cifras = pesos[i][j].length();
                delta = mayorPorColumnas[j] - cifras;
                for (int k = 0; k < delta; k++) {
                    cadena += " ";
                }
                cadena += pesos[i][j];
            }
            cadena += "\n";
        }

        System.out.println(cadena);
    }

    @Override
    public int degree(int v) {
        int c = 0;
        if (containsVertex(v)) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[v][i] != 0) {
                    c++;
                }
            }
        }
        return c;
    }

    @Override
    public boolean isWeighted() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasNegativeWeights() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showAdjacencyList() {
        String text = "";
        if (isWeighted()) {
            for (int i = 0; i < matrix.length; i++) {
                text += i + ": [";
                ArrayList<Integer> list = adjacents(i);

                if (!list.isEmpty()) {
                    for (int j = 0; j < list.size() - 1; j++) {
                        text += "(" + list.get(j) + ", " + Util.realAString(matrix[i][list.get(j)]) + "), ";
                    }
                    text += "(" + list.get(list.size() - 1) + ", " + Util.realAString(matrix[i][list.get(list.size() - 1)]) + ")";
                }
                text += "]\n";
            }
        } else {
            for (int i = 0; i < matrix.length; i++) {
                text += i + ": [";
                ArrayList<Integer> list = adjacents(i);

                if (!list.isEmpty()) {
                    for (int j = 0; j < list.size() - 1; j++) {
                        text += list.get(j) + ", ";
                    }
                    text += list.get(list.size() - 1);
                }
                text += "]\n";
            }
        }
        System.out.println(text);
    }

}
