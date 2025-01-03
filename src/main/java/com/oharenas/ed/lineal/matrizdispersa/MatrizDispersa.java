/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oharenas.ed.lineal.matrizdispersa;

import com.oharenas.util.Util;

/**
 *
 * @author Oscar Arenas
 */
public class MatrizDispersa {

    // Campos
    private double[][] tripletas;
    private int n;
    public final int filas;
    public final int columnas;

    // Métodos
    public MatrizDispersa(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        tripletas = new double[1][3];
        n = 0;
    }

    public boolean agregar(int fila, int columna, double valor) {
        if (valor != 0 && fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            int i = 0;
            // Buscamos el punto de inserción de la tripleta por fila
            while (i < n && tripletas[i][0] < fila) {
                i++;
            }
            // Buscamos el punto de inserción de la tripleta por columna
            while (i < n && tripletas[i][0] == fila && tripletas[i][1] < columna) {
                i++;
            }

            if (i < n && tripletas[i][0] == fila && tripletas[i][1] == columna) {
                return false; // No se permiten dos valores en la misma posicion
            }

            if (tripletas.length == n) {
                cambiarCapacidad(2 * n);
            }

            for (int j = n; j > i; j--) {
                tripletas[j][0] = tripletas[j - 1][0];
                tripletas[j][1] = tripletas[j - 1][1];
                tripletas[j][2] = tripletas[j - 1][2];
            }

            tripletas[i][0] = fila;
            tripletas[i][1] = columna;
            tripletas[i][2] = valor;

            n++;
            return true;
        }
        return false;
    }

    private void cambiarCapacidad(int nc) {
        if (nc > 0 && nc >= n) {
            double[][] auxiliar = new double[nc][3];

            for (int i = 0; i < n; i++) {
                auxiliar[i] = tripletas[i];
            }
            tripletas = auxiliar;
        }
    }

    public boolean eliminar(int fila, int columna) {
        if (n > 0 && fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            int i = 0;
            while (i < n && fila > tripletas[i][0]) {
                i++;
            }
            while (i < n && fila == tripletas[i][0] && columna > tripletas[i][1]) {
                i++;
            }
            if (i < n && fila == tripletas[i][0] && columna == tripletas[i][1]) {
                n--;
                while (i < n) {
                    tripletas[i][0] = tripletas[i + 1][0];
                    tripletas[i][1] = tripletas[i + 1][1];
                    tripletas[i][2] = tripletas[i + 1][2];
                    i++;
                }
                if (tripletas.length / 4 == n) {
                    cambiarCapacidad(tripletas.length / 2);
                }
                return true;
            }
        }
        return false;
    }

    public boolean contiene(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            int i = 0;
            while (i < n && fila > tripletas[i][0]) {
                i++;
            }
            while (i < n && fila == tripletas[i][0] && columna > tripletas[i][1]) {
                i++;
            }
            return i < n && fila == tripletas[i][0] && columna == tripletas[i][1];
        }
        return false;
    }

    public double obtener(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            int i = 0;
            while (i < n && fila > tripletas[i][0]) {
                i++;
            }
            while (i < n && fila == tripletas[i][0] && columna > tripletas[i][1]) {
                i++;
            }
            if (i < n && fila == tripletas[i][0] && columna == tripletas[i][1]) {
                return tripletas[i][2];
            }
            return 0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString() {
        String cadena = "";

        if (n > 0) {
            String[] etiquetas = {"Fila", "Columna", "Valor"};
            int[] mayorAncho = {4, 7, 5};

            // Buscamos el valor de la matriz con más caracteres
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 3; j++) {
                    String numero = Util.realAString(tripletas[i][j]);
                    if (numero.length() > mayorAncho[j]) {
                        mayorAncho[j] = numero.length();
                    }
                }
            }

            for (int j = 0; j < 3; j++) {
                mayorAncho[j]++;
                int delta = mayorAncho[j] - etiquetas[j].length();
                for (int k = 0; k < delta; k++) {
                    cadena += " ";
                }
                cadena += etiquetas[j];
            }
            cadena += "\n";

            // Formamos la cadena con los valores de la matriz por filas
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 3; j++) {
                    String numero = Util.realAString(tripletas[i][j]);

                    int delta = mayorAncho[j] - numero.length();
                    for (int k = 0; k < delta; k++) {
                        cadena += " ";
                    }
                    cadena += numero;
                }
                cadena += "\n";
            }
        }
        return cadena;
    }

    public int cantidadTripletas() {
        return n;
    }

    public double dispersion() {
        return n / (double) (filas * columnas);
    }

}
