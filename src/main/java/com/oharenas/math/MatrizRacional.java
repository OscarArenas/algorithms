/*
 * Copyright (C) 2018 Oscar Arenas
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

/**
 * Clase para representar matrices de orden MxN.
 *
 * @author Oscar Arenas
 */
public class MatrizRacional {

    private Racional[][] matrizRacional;

    public MatrizRacional(int filas, int columnas) {
        matrizRacional = new Racional[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizRacional[i][j] = new Racional(0);
            }
        }
    }

    public MatrizRacional(int[] vector) {
        matrizRacional = new Racional[1][vector.length];

        for (int j = 0; j < vector.length; j++) {
            matrizRacional[0][j] = new Racional(vector[j]);
        }

    }

    public MatrizRacional(double[] vector) {
        matrizRacional = new Racional[1][vector.length];

        for (int j = 0; j < vector.length; j++) {
            matrizRacional[0][j] = new Racional(vector[j]);
        }

    }

    public MatrizRacional(int[][] matriz) {
        matrizRacional = new Racional[matriz.length][];
        int tamanioColumnas = matriz[0].length;

        for (int i = 0; i < matriz.length; i++) {
            int columnas = matriz[i].length;
            if (tamanioColumnas != columnas) {
                System.out.println("Lanzar excepcion: Columnas de diferente tamaño!");
            }

            matrizRacional[i] = new Racional[columnas];
            for (int j = 0; j < columnas; j++) {
                matrizRacional[i][j] = new Racional(matriz[i][j]);
            }
        }
    }

    public MatrizRacional(double[][] matriz) {
        matrizRacional = new Racional[matriz.length][];
        int tamanioColumnas = matriz[0].length;

        for (int i = 0; i < matriz.length; i++) {
            int columnas = matriz[i].length;
            if (tamanioColumnas != columnas) {
                System.out.println("Lanzar excepcion: Columnas de diferente tamaño!");
            }
            matrizRacional[i] = new Racional[columnas];
            for (int j = 0; j < columnas; j++) {
                matrizRacional[i][j] = new Racional(matriz[i][j]);
            }
        }
    }

    public MatrizRacional(Racional[][] matriz) {
        matrizRacional = new Racional[matriz.length][];
        int tamanioColumnas = matriz[0].length;

        for (int i = 0; i < matriz.length; i++) {
            int columnas = matriz[i].length;
            if (tamanioColumnas != columnas) {
                System.out.println("Lanzar excepcion: Columnas de diferente tamaño!");
            }

            matrizRacional[i] = new Racional[columnas];
            for (int j = 0; j < columnas; j++) {
                matrizRacional[i][j] = matriz[i][j].copia();
            }
        }
    }

    public void set(int fila, int columna, long valor) {
        if (fila >= 0 && fila < matrizRacional.length) {
            if (columna >= 0 && columna < matrizRacional[0].length) {
                matrizRacional[fila][columna] = new Racional(valor);
            }
        }
    }

    public MatrizRacional sumar(MatrizRacional matriz) {
        MatrizRacional ans = null;
        if (matrizRacional.length == matriz.matrizRacional.length && matrizRacional[0].length == matriz.matrizRacional[0].length) {
            ans = new MatrizRacional(matrizRacional.length, matrizRacional[0].length);
            for (int i = 0; i < matrizRacional.length; i++) {
                for (int j = 0; j < matrizRacional[i].length; j++) {
                    ans.matrizRacional[i][j] = matrizRacional[i][j].sumar(matriz.matrizRacional[i][j]);
                }
            }
        }
        return ans;
    }

    public MatrizRacional restar(MatrizRacional matriz) {
        MatrizRacional ans = null;
        if (matrizRacional.length == matriz.matrizRacional.length && matrizRacional[0].length == matriz.matrizRacional[0].length) {
            ans = new MatrizRacional(matrizRacional.length, matrizRacional[0].length);
            for (int i = 0; i < matrizRacional.length; i++) {
                for (int j = 0; j < matrizRacional[i].length; j++) {
                    ans.matrizRacional[i][j] = matrizRacional[i][j].restar(matriz.matrizRacional[i][j]);
                }
            }
        }
        return ans;
    }

    public MatrizRacional mult(MatrizRacional matriz) {
        MatrizRacional ans = null;

        if (matrizRacional[0].length == matriz.matrizRacional.length) {
            int columnas = matrizRacional[0].length;
            ans = new MatrizRacional(matrizRacional.length, matriz.matrizRacional[0].length);

            for (int i = 0; i < matrizRacional.length; i++) {
                for (int j = 0; j < matriz.matrizRacional[0].length; j++) {
                    for (int k = 0; k < columnas; k++) {
                        ans.matrizRacional[i][j] = ans.matrizRacional[i][j].sumar(matrizRacional[i][k].mult(matriz.matrizRacional[k][j]));
                    }
                }
            }
        }

        return ans;
    }

    public void set(int fila, int columna, double valor) {
        if (fila >= 0 && fila < matrizRacional.length) {
            if (columna >= 0 && columna < matrizRacional[0].length) {
                matrizRacional[fila][columna] = new Racional(valor);
            }
        }
    }

    public MatrizRacional transpuesta() {
        Racional[][] matriz = new Racional[matrizRacional[0].length][matrizRacional.length];

        for (int i = 0; i < matrizRacional.length; i++) {
            for (int j = 0; j < matrizRacional[i].length; j++) {
                matriz[j][i] = matrizRacional[i][j].copia();
            }
        }

        return new MatrizRacional(matriz);
    }

    public double[][] getMatriz() {
        double[][] matriz = new double[matrizRacional.length][];

        for (int i = 0; i < matriz.length; i++) {
            matriz[i] = new double[matrizRacional[i].length];
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = matrizRacional[i][j].razon();
            }
        }

        return matriz;
    }

    public MatrizRacional generarIdentidad(int n) {
        int[][] id = new int[n][n];

        for (int i = 0; i < n; i++) {
            id[i][i] = 1;
        }

        return new MatrizRacional(id);
    }

    public MatrizRacional generarIdentidad() {
        return generarIdentidad(matrizRacional.length);
    }

    public void multiplicar(int fila, Racional k) {
        for (int j = 0; j < matrizRacional[0].length; j++) {
            matrizRacional[fila][j] = matrizRacional[fila][j].mult(k);
        }
    }

    public void intercambiarFilas(int fila1, int fila2) {
        for (int j = 0; j < matrizRacional[0].length; j++) {
            Racional temp = matrizRacional[fila1][j];
            matrizRacional[fila1][j] = matrizRacional[fila2][j];
            matrizRacional[fila2][j] = temp;
        }
    }

    public double det() {
        if (matrizRacional.length == matrizRacional[0].length) {
            int size = matrizRacional.length;
            MatrizRacional copia = copiar();
            Racional deter = new Racional(1);

            // Ubicar unos en la diagonal princiipal y ceros por debajo de ellos.
            int i = 0;
            while (i < size) {
                if (!copia.matrizRacional[i][i].esCero()) {
                    // Ubicar el uno en la diagonal principal
                    deter = deter.mult(copia.matrizRacional[i][i]);
                    Racional reciproco = copia.matrizRacional[i][i].reciproco();
                    copia.multiplicar(i, reciproco);

                    //Ubica ceros debajo del uno.
                    for (int j = i + 1; j < size; j++) {
                        if (!copia.matrizRacional[j][i].esCero()) {
                            Racional inverso = copia.matrizRacional[j][i].negar();
                            for (int k = 0; k < size; k++) {
                                copia.matrizRacional[j][k] = copia.matrizRacional[j][k].sumar(copia.matrizRacional[i][k].mult(inverso));
                            }
                        }
                    }
                    //Cambiar de fila
                    i++;
                } else {
                    int noCero = -1;
                    for (int j = i + 1; j < size; j++) {
                        if (!copia.matrizRacional[j][i].esCero()) {
                            noCero = j;
                            break;
                        }
                    }
                    if (noCero > 0) {
                        copia.intercambiarFilas(i, noCero);
                        deter = deter.negar();
                    } else {
                        return 0;
                    }
                }
            }
            return deter.razon();
        }
        return 0;
    }

    public MatrizRacional inversa() {
        MatrizRacional inv = null;

        if (det() != 0) {
            if (matrizRacional.length == matrizRacional[0].length) {
                int size = matrizRacional.length;
                MatrizRacional copia = copiar();
                inv = generarIdentidad();

                // Ubicar unos en la diagonal princiipal y ceros por debajo de ellos.
                int i = 0;
                while (i < size) {
                    if (!copia.matrizRacional[i][i].esCero()) {
                        // Ubicar el uno en la diagonal principal
                        Racional reciproco = copia.matrizRacional[i][i].reciproco();

//                        matrizRacional[i][j] = matrizRacional[i][j].mult(reciproco);
                        copia.multiplicar(i, reciproco);
                        inv.multiplicar(i, reciproco);

                        //Ubica ceros debajo del uno.
                        for (int j = i + 1; j < size; j++) {
                            if (!copia.matrizRacional[j][i].esCero()) {
                                Racional inverso = copia.matrizRacional[j][i].negar();

                                for (int k = 0; k < size; k++) {
                                    copia.matrizRacional[j][k] = copia.matrizRacional[j][k].sumar(copia.matrizRacional[i][k].mult(inverso));
                                    inv.matrizRacional[j][k] = inv.matrizRacional[j][k].sumar(inv.matrizRacional[i][k].mult(inverso));
                                }
                            }
                        }
                        //Cambiar de fila
                        i++;
                    } else {
                        int noCero = -1;
                        for (int j = i + 1; j < size; j++) {
                            if (!copia.matrizRacional[j][i].esCero()) {
                                noCero = j;
                                break;
                            }
                        }
                        if (noCero > 0) {
                            copia.intercambiarFilas(i, noCero);
                            inv.intercambiarFilas(i, noCero);
                        } else {
                            return null;
                        }
                    }
                }
                // Ubicar ceros sobre la diagonal principal.
                i--;
                while (i > 0) {
                    for (int j = i - 1; j >= 0; j--) {
                        if (!copia.matrizRacional[j][i].esCero()) {
                            Racional inverso = copia.matrizRacional[j][i].negar();

                            for (int k = 0; k < size; k++) {
                                copia.matrizRacional[j][k] = copia.matrizRacional[j][k].sumar(copia.matrizRacional[i][k].mult(inverso));
                                inv.matrizRacional[j][k] = inv.matrizRacional[j][k].sumar(inv.matrizRacional[i][k].mult(inverso));
                            }
                        }
                    }
                    //
                    i--;
                }
            }
        }

        return inv;
    }

    public MatrizRacional copiar() {
        return new MatrizRacional(matrizRacional);
    }

    public boolean esIdentidad() {
        if (matrizRacional.length == matrizRacional[0].length) {
            for (int i = 0; i < matrizRacional.length; i++) {
                if (!matrizRacional[i][i].esUno()) {
                    return false;
                }
            }
            for (int i = 0; i < matrizRacional.length; i++) {
                for (int j = 0; j < matrizRacional.length; j++) {
                    if (i != j) {
                        if (!matrizRacional[i][j].esCero()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void print() {
        System.out.println(toString());
    }

    /**
     * Genera una cadena con los valores de la matriz expresados en forma de
     * número racional (fraccionario).
     *
     * @return Una cadena con los valores de la matriz.
     */
    @Override
    public String toString() {
        String[][] matriz = new String[matrizRacional.length][matrizRacional[0].length];
        String cadena = "";
        int mayorColumna = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = matrizRacional[i][j].toString();
                if (matriz[i][j].length() > mayorColumna) {
                    mayorColumna = matriz[i][j].length();
                }
            }
        }

        mayorColumna += 2;
        for (String[] matriz1 : matriz) {
            for (int j = 0; j < matriz1.length; j++) {
                int delta = mayorColumna - matriz1[j].length();
                for (int k = 0; k < delta; k++) {
                    cadena += " ";
                }
                cadena += matriz1[j];

            }
            cadena += "\n";
        }

        return cadena;
    }

    public String[][] convertToString() {
        String[][] matriz = new String[matrizRacional.length][matrizRacional[0].length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = matrizRacional[i][j].toString();
            }
        }

        return matriz;
    }
}
