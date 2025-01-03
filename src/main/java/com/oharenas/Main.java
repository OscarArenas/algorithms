package com.oharenas;

import com.oharenas.aleatorio.Aleatorio;
import com.oharenas.util.Arreglo;

public class Main {
    public static void main(String[] args) {

        for (int i = 1; i <= 2; i++) {
            int[][] matriz = Aleatorio.generarMatrizEnteros(4,15,1,20);
            Arreglo.mostrarMatriz(matriz);
            System.out.println();
        }
    }
}
