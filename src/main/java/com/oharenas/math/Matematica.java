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
package com.oharenas.math;

import com.oharenas.util.Arreglo;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Contiene algunos métodos que implementan funciones matemáticas.
 *
 * @author Oscar Arenas
 */
public class Matematica {

    /**
     * Calcula el factorial de un entero no negativo.
     *
     * @param n Entero no negativo al cual se le calculará el factorial.
     * @return Retorna el factorial del parámetro n.
     * @throws IllegalArgumentException Lanza la excepción si el parámetro n es
     * negativo.
     */
    public static long factorial(long n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("El argumento no puede ser negativo.");
        }

        long f = 1L;
        for (long i = 2L; i <= n; i++) {
            f *= i;
        }
        return f;
    }

    public static BigInteger factorial(BigInteger numero) {
        if (numero.compareTo(BigInteger.ONE) < 0) {
            throw new IllegalArgumentException("El argumento no puede ser negativo.");
        }
        return factorial2(numero);
    }

    private static BigInteger factorial2(BigInteger numero) {
        if (numero.compareTo(BigInteger.ONE) == 0) {//Caso base
            return BigInteger.ONE;
        }
        // Caso recursivo
        return numero.multiply(
                factorial2(numero.subtract(BigInteger.ONE)));
    }

    /**
     * Calcula el factorial de un entero no negativo de forma recursiva.
     *
     * @param n Entero no negativo al cual se le calculará el factorial.
     * @return Retorna el factorial del parámetro n.
     * @throws IllegalArgumentException Lanza la excepción si el parámetro n es
     * negativo.
     */
    public static long factorialRecursivo(long n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("El argumento no puede ser negativo.");
        }
        return factorialRecursivo2(n);
    }

    private static long factorialRecursivo2(long n) throws IllegalArgumentException {
        if (n > 0) {
            return n * factorialRecursivo2(n - 1);
        }
        return 1L;
    }

    public static long calcularPermutaciones(int n, int r) {
        if (n < 1 || r < 0) {
            throw new IllegalArgumentException("Los argumentos no puede ser menores que uno (1).");
        }
        if (r > n) {
            throw new IllegalArgumentException("Argumentos no válidos: r no puede ser mayor que n.");
        }

        long f = 1;
        for (int i = n - r + 1; i <= n; i++) {
            f *= i;
        }
        return f;
    }

    public static long calcularCombinaciones(int n, int r) throws IllegalArgumentException {
        return calcularPermutaciones(n, r) / factorial(r);
    }

    /**
     * A partir de una permutación sin repeticiones dada calcula la siguiente
     * permutación en orden lexicográfico.
     *
     * @param permutacion Indica la permutación a partir de la cual se va a
     * calcular la siguiente permutación.
     * @return Retorna la siguiente permutación a la dada en orden
     * lexicográfico.
     * @throws IllegalArgumentException Lanza la excepción si en la permutación
     * dada hay valores repetidos.
     */
    public static int[] generarPermutacion(int[] permutacion) throws IllegalArgumentException {
        if (Arreglo.tieneRepetidos(permutacion)) {
            throw new IllegalArgumentException("Hay valores repetidos en la permutación dada.");
        }
        int[] permutacionSiguiente = permutacion.clone();

        int j = permutacion.length - 2;

        while (permutacion[j] > permutacion[j + 1]) {
            j--;
        }
        int k = permutacion.length - 1;

        while (permutacion[j] > permutacion[k]) {
            k--;
        }

        Arreglo.intercambiar(permutacionSiguiente, j, k);

        int r = permutacion.length - 1;
        int s = j + 1;

        while (r > s) {
            Arreglo.intercambiar(permutacionSiguiente, r, s);
            r--;
            s++;
        }
        return permutacionSiguiente;
    }

    public static void generarPermutaciones(int n) throws IllegalArgumentException {
        int[] permutacion = Arreglo.generarConsecutivo(n, 0);

        System.out.println(Arrays.toString(permutacion));

        long k = calcularPermutaciones(n, n);
        for (int i = 1; i < k; i++) {
            permutacion = generarPermutacion(permutacion);

            System.out.println(Arrays.toString(permutacion));
        }
    }

    public static int[][] generarCombinaciones(int n, int r) {
        int total = (int) calcularCombinaciones(n, r);
        int[][] combinaciones = new int[total][];

        int[] combinacion = Arreglo.generarConsecutivo(r, 0);
//        System.out.println(Arrays.toString(combinacion));
        combinaciones[0] = combinacion.clone();

        for (int i = 1; i < total; i++) {
            int m = r - 1;
            int valorMaximo = n - 1;

            // Encuentra el elemento más a la derecha
            // que no tiene su valor máximo
            while (m > 0 && combinacion[m] == valorMaximo) {
                m--;
                valorMaximo--;
            }
            combinacion[m]++;

            for (int j = m + 1; j < r; j++) {
                combinacion[j] = combinacion[j - 1] + 1;
            }
            combinaciones[i] = combinacion.clone();
        }
        return combinaciones;
    }

    /**
     * Redondea un valor real a la cantidad indicada de decimales. Si decimales
     * es negativo o mayor que cinco, no se redondea el número.
     *
     * @param numero Valor real para redondear.
     * @param decimales Cantidad decimales a la que se redondeará el número.
     * @return Retorna el número redondeado con los decimales indicados.
     */
    public static double redondear(double numero, int decimales) {
        double t = numero;
        if (decimales >= 0 && decimales <= 5) {
            double pt = Math.pow(10, decimales);
            t = numero * pt;
            t = Math.round(t) / pt;
        }
        return t;
    }

    /**
     * Verifica si un número entero es primo.
     *
     * @param n Número entero sobre el que se indicará si es primo.
     * @return Retorna true si el parámetro n es primo y false en caso
     * contrario.
     */
    public static boolean esPrimo(int n) {
        if (n > 1) {
            int limite = (int) Math.sqrt(n);

            for (int i = 2; i < limite; i++) {
                if (n % i == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Máximo común divisor.
     *
     * @param a
     * @param b
     * @return
     */
    public static long mcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    /**
     * Mínimo común multiplo
     *
     * @param a
     * @param b
     * @return
     */
    public static long mcm(long a, long b) {
        return a * b / mcd(a, b);
    }

    public static double log2(double n) {
        return Math.log(n) / Math.log(2);
    }
}
