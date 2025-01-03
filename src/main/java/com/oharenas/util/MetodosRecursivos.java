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
package com.oharenas.util;

/**
 *
 * @author Oscar Arenas
 */
public class MetodosRecursivos {

    // Método principal (header)
    public static long calcularFactorial(int n)
            throws IllegalArgumentException {
        if (n >= 0) {
            return factorial(n);
        }
        throw new IllegalArgumentException("" + n);
    }

    // Método auxiliar (helper)
    private static long factorial(int n) {
        if (n == 0) {// Caso base
            return 1;
        } else {// Caso recursivo
            return n * factorial(n - 1);
        }
    }
// Método principal (header)

    public static long calcularFactorial2(int n)
            throws IllegalArgumentException {
        if (n >= 0) {
            return factorial2(n, 0, 1);
        }
        throw new IllegalArgumentException("" + n);
    }

    // Método auxiliar (helper)
    private static long factorial2(int n, int i, int fac) {
        if (i < n) {
            i++;
            return factorial2(n, i, fac * i);
        }
        return fac;
    }

    public static void mostrarDecreciente(int n) {
        if (n > 0) {
            System.out.println(n);
            mostrarDecreciente(n - 1);
        }
    }

    public static void mostrarCreciente(int n) {
        if (n > 0) {
            mostrarCreciente(n - 1);
            System.out.println(n);
        }
    }

    public static boolean esPrimo(int n) {
        if (n > 1) {
            return esPrimo(2, n);
        } else {
            return false;
        }
    }

    private static boolean esPrimo(int i, int n) {
        if (i * i <= n) {
            if (n % i == 0) {
                return false;
            }
            return esPrimo(i + 1, n);
        }
        return true;
    }

    public static double sumar(double[] vector) {
        return sumar(vector, 0);
    }

    private static double sumar(double[] vector, int i) {
        if (i < vector.length) {
            return vector[i] + sumar(vector, i + 1);
        }
        return 0;
    }

    public static double mayor(double[] vector) {
        return mayor(vector, 0);
    }

    private static double mayor(double[] vector, int i) {
        if (i < vector.length - 1) {
            return Math.max(vector[i], mayor(vector, i + 1));
        }
        return vector[vector.length - 1];
    }

    public static void torresDeHanoi(int discos)
            throws IllegalArgumentException {
        if (discos > 0) {
            mover(discos, 1, 2, 3);
        } else {
            throw new IllegalArgumentException("" + discos);
        }
    }

    private static void mover(int discos, int origen, int auxiliar, int destino) {
        if (discos == 1) {
            System.out.println("Mover disco " + discos + ": " + origen + " -> " + destino);
        } else {
            mover(discos - 1, origen, destino, auxiliar);
            System.out.println("Mover disco " + discos + ": " + origen + " -> " + destino);
            mover(discos - 1, auxiliar, origen, destino);
        }
    }

    public static void torresDeHanoi2(int discos) throws IllegalArgumentException {
        if (discos > 0) {
            mover2(discos, 1, 2, 3);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void mover2(int discos, int origen, int auxiliar, int destino) {
        if (discos > 0) {
            mover2(discos - 1, origen, destino, auxiliar);
            System.out.println("Mover disco " + discos + ": " + origen + " -> " + destino);
            mover2(discos - 1, auxiliar, origen, destino);
        }
    }

    public static void torresDeHanoi3(int discos) throws IllegalArgumentException {
        if (discos > 0) {
            mover3(discos, 'A', 'B', 'C');
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void mover3(int discos, char origen, char auxiliar, char destino) {
        if (discos == 1) {
            System.out.println("Mover disco " + discos + ": " + origen + " -> " + destino);
        } else {
            mover3(discos - 1, origen, destino, auxiliar);
            System.out.println("Mover disco " + discos + ": " + origen + " -> " + destino);
            mover3(discos - 1, auxiliar, origen, destino);
        }
    }

    public static boolean esPalindromo(String texto) {
        return esPalindromo(texto.toLowerCase(), 0, texto.length() - 1);
    }

    private static boolean esPalindromo(String texto, int bajo, int alto) {
        if (bajo < alto) {
            if (texto.charAt(bajo) == ' ') {
                return esPalindromo(texto, bajo + 1, alto);
            } else if (texto.charAt(alto) == ' ') {
                return esPalindromo(texto, bajo, alto - 1);
            } else if (texto.charAt(bajo) != texto.charAt(alto)) {
                return false;
            } else {
                return esPalindromo(texto, bajo + 1, alto - 1);
            }
        }
        return true;
    }

    public static boolean esAnagrama(String texto1, String texto2) {
        String text1 = texto1.replaceAll(" ", "");
        String text2 = texto2.replaceAll(" ", "");

        text1 = text1.replaceAll(",", "");
        text2 = text2.replaceAll(",", "");

        text1 = text1.replaceAll("¡", "");
        text2 = text2.replaceAll("¡", "");

        text1 = text1.replaceAll("!", "");
        text2 = text2.replaceAll("!", "");

        if (text1.length() == text2.length()) {
            return esAnagrama(text1.toLowerCase(), text2.toLowerCase(), 0);
        }
        return false;
    }

    private static boolean esAnagrama(String texto1, String texto2, int i) {
        if (i < texto1.length()) {
            if (texto1.charAt(i) == ' ') {
                return esAnagrama(texto1, texto2, i + 1);
            } else if (texto2.contains(texto1.charAt(i) + "")) {
                return esAnagrama(texto1, texto2.replaceFirst(texto1.charAt(i) + "", ""), i + 1);
            } else {
                return false;
            }
        }
        return true;
    }

    public static String dec2hex(long numero) {
        return dec2hexHelper(numero);
    }

    private static String dec2hexHelper(long numero) {
        if (numero >= 16) {
            return dec2hexHelper(numero / 16) + hexadecimal((int) (numero % 16));
        }
        return numero + "";
    }

    private static String hexadecimal(int n) {
        if (n >= 0 && n <= 9) {
            return n + "";
        } else {
            switch (n) {
                case 10:
                    return "A";
                case 11:
                    return "B";
                case 12:
                    return "C";
                case 13:
                    return "D";
                case 14:
                    return "E";
                case 15:
                    return "F";
                default:
                    return "#";
            }
        }
    }

    public static int calcularFibonacci(int n)
            throws IllegalArgumentException {
        if (n >= 0) {
            return fibonacci(n);
        }
        throw new IllegalArgumentException("" + n);
    }

    private static int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
