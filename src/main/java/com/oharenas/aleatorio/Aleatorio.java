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
package com.oharenas.aleatorio;

import com.oharenas.comun.*;
import com.oharenas.ed.lineal.listas.DoubleArrayList;
import com.oharenas.ed.lineal.listas.DoubleLinkedList;
import com.oharenas.ed.lineal.listas.IntegerArrayList;
import com.oharenas.ed.lineal.listas.IntegerLinkedList;
import com.oharenas.util.Archivo;
import com.oharenas.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Para generar aleatoriamente valores de tipo entero, real, caracter, lógico,
 * cadena y objetos de la clase Estudiante, Fecha y Empleado.
 *
 * @author Oscar Arenas
 */
public class Aleatorio {

    private static final Random R;
    //
    public static String[] hombres;
    public static String[] mujeres;
    public static String[] apellidos;
    //
    private static String[] paises;
    private static String[] cultivos;

    static {// Se ejecuta cuando se carga la clase con import
        R = new Random();
        cargar();
    }

    private static void cargar() {
        if (hombres == null) {
            hombres = new String[229];
            mujeres = new String[299];
            apellidos = new String[527];

            cargarNombresHombres();
            cargarNombresMujeres();
            cargarApellidos();
        }
        cargarPaises();
        cargarCultivos();
    }

    /**
     * Genera un número aleatorio entero en el intervalo [0, n-1].
     *
     * @param n Entero positivo
     * @return Entero aleatorio en el intervalo [0, n-1]
     */
    public static int entero(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        return R.nextInt(n);
    }

    /**
     * Genera un número aleatorio entero positivo con n digitos.
     *
     * @param n Cantidad de digitos
     * @return cadena con n digitos
     */
    public static String enteroCadena(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        String numero = "";
        for (int i = 0; i < n; i++) {
            numero += entero(10);
        }
        return numero;
    }

    public static ArrayList<String> enteroCadena(int a, int b, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        if (b < a) {
            int aux = a;
            a = b;
            b = aux;
        }
        ArrayList<String> lista = new ArrayList<>();
        TreeSet<String> tree = new TreeSet<>();

        while (n > 0) {
            int m = Aleatorio.entero(a, b);
            String numero = enteroCadena(m);

            if (tree.add(numero)) {
                lista.add(numero);
                n--;
            }
        }

        return lista;
    }

    public static ArrayList<String> cadenaAlfanumerica(int a, int b, int n) {
        return cadenaAlfanumerica(a, b, n, "");
    }

    public static ArrayList<String> cadenaAlfanumerica(int a, int b, int n, String separadores) {
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        if (b < a) {
            int aux = a;
            a = b;
            b = aux;
        }
        ArrayList<String> lista = new ArrayList<>();
        TreeSet<String> tree = new TreeSet<>();

        while (n > 0) {
            int m = Aleatorio.entero(a, b);
            String numero = cadenaAlfanumerica(m, separadores);

            if (tree.add(numero)) {
                lista.add(numero);
                n--;
            }
        }

        return lista;
    }

    /**
     * Genera un número aleatorio entero en el intervalo [1, n].
     *
     * @param n Entero positivo
     * @return Entero aleatorio en el intervalo [1, n]
     * @throws IllegalArgumentException
     */
    public static int enteroPositivo(int n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        return 1 + R.nextInt(n);
    }

    /**
     * Genera un número aleatorio entero en el intervalo [-n, -1].
     *
     * @param n Entero positivo.
     * @return Entero aleatorio en el intervalo [-n, -1].
     * @throws IllegalArgumentException Si n es cero.
     */
    public static int enteroNegativo(int n) throws IllegalArgumentException {
        n = Math.abs(n);
        if (n == 0) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        return -(1 + R.nextInt(n));
    }

    /**
     * Genera un número aleatorio entero en el intervalo [a, b].
     *
     * @param a Entero que indica el límite inferior de los valores aleatorios,
     * debe ser menor que el parametro b
     * @param b Entero que indica el límite superior de los valores aleatorios,
     * debe ser mayor que el parametro a
     * @return Entero aleatorio en el intervalo [a, b]
     */
    public static int entero(int a, int b) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
        return a + R.nextInt(b - a + 1);
    }

    /**
     * Genera un número aleatorio real en el intervalo [0, 1)
     *
     *
     * @return Real aleatorio en el intervalo [0, 1)
     */
    public static double real() {
        return R.nextDouble();
    }

    /**
     * Genera un número aleatorio real en el intervalo [a, b] con los decimales
     * especificados en el parametro decimales.
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param decimales Entero en el intervalo [0, 5]
     * @return Real aleatorio en el intervalo [a, b]
     * @throws IllegalArgumentException
     */
    public static double real(int a, int b, int decimales) throws IllegalArgumentException {
        if (decimales < 0) {
            throw new IllegalArgumentException("Argumento no válido: Los decimales deben ser no negativos.");
        }
        if (decimales > 5) {
            throw new IllegalArgumentException("Argumento no válido: Se permite máximo cinco decimales.");
        }

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        double p = Math.pow(10, decimales);

        return (a * p + R.nextInt((b - a) * ((int) p) + 1)) / p;
    }

    public static String cadenaAlfanumerica(int n) {
        return cadenaAlfanumerica(n, "");
    }

    public static String cadenaAlfanumerica(int n, String separadores) {
        String simbolos = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        simbolos += simbolos.toLowerCase();
        simbolos += "0123456789" + separadores;

        String cadena = "";
        for (int i = 0; i < n; i++) {
            cadena += simbolos.charAt(Aleatorio.entero(simbolos.length()));
        }

        return cadena;
    }

    /**
     * Genera vector de tamaño n con números aleatorios reales en el intervalo
     * [0, 1).
     *
     *
     * @param n Entero positivo que indica el tamaño del vector.
     * @return Vector de reales aleatorios en el intervalo [0, 1)
     */
    public static double[] generarVectorReal(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        double[] aleatorios = new double[n];

        for (int i = 0; i < n; i++) {
            aleatorios[i] = R.nextDouble();
        }

        return aleatorios;
    }

    /**
     * Genera un vector de tamaño n con números aleatorios reales en el
     * intervalo [a, b] con los decimales especificados en el parametro
     * decimales.
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param decimales Entero en el intervalo [0, 5]
     * @param n Entero positivo que indica el tamaño del vector
     * @return Vector real con números aleatorios en el intervalo [a, b]
     * @throws IllegalArgumentException
     */
    public static double[] generarVectorReal(int a, int b, int decimales, int n) throws IllegalArgumentException {
        if (decimales < 0) {
            throw new IllegalArgumentException("Argumento no válido: Los decimales deben ser no negativos.");
        }
        if (decimales > 5) {
            throw new IllegalArgumentException("Argumento no válido: Se permite máximo cinco decimales.");
        }

        double[] aleatorios = new double[n];

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        double p = Math.pow(10, decimales);

        for (int i = 0; i < n; i++) {
            aleatorios[i] = (a * p + R.nextInt((b - a) * ((int) p) + 1)) / p;
        }

        return aleatorios;
    }

    /**
     * Genera un número aleatorio entero en el intervalo [multiplo,
     * limiteSuperior] que es múltiplo del parámetro multiplo.
     *
     * @param multiplo Entero positivo
     * @param limiteSuperior Entero positivo mayor que el parámetro multiplo
     * @return Retorna untero aleatorio en el intervalo [multiplo,
     * limiteSuperior]
     * @throws IllegalArgumentException Lanza la excepción si multiplo es
     * negativo o cero, o si el parámetro limiteSuperior es inferior al
     * parámetro multiplo
     */
    public static int enteroMultiplo(int multiplo, int limiteSuperior) throws IllegalArgumentException {
        if (multiplo <= 0) {
            throw new IllegalArgumentException("Argumento no válido: El múltiplo debe ser mayor que cero");
        } else if (limiteSuperior <= multiplo) {
            throw new IllegalArgumentException("Argumento no válido: El múltiplo debe ser menor que el limiteSuperior");
        }
        return multiplo * enteroPositivo(limiteSuperior / multiplo);
    }

    /**
     * Genera un número entero aleatorio en el intervalo [limiteInferior,
     * limiteSuperior] y múltiplo del valor indicado en el parámetro múltiplo.
     *
     * @param limiteInferior Valor entero que indica el límite inferior del
     * rango dentro del cual se generará el entero aleatorio.
     * @param limiteSuperior Valor entero que indica el límite superior del
     * rango dentro del cual se generará el entero aleatorio.
     * @param multiplo Valor entero positivo que indica el factor del aleatorio.
     * @return Retorna un entero aleatoio dentro del intervalo [limiteInferior,
     * limiteSuperior] y que es múltiplo del valor indicado en el parámetro
     * múltiplo.
     * @throws IllegalArgumentException Lanza la excepción si el múltiplo es
     * cero o si los límites son iguales.
     */
    public static int enteroMultiplo(int limiteInferior, int limiteSuperior, int multiplo) throws IllegalArgumentException {
        if (multiplo == 0) {
            throw new IllegalArgumentException("Error: El múltiplo debe ser diferente de cero.");
        } else if (limiteInferior == limiteSuperior) {
            throw new IllegalArgumentException("Error: Los límites deben ser diferentes.");
        }

        multiplo = Math.abs(multiplo);

        if (limiteInferior > limiteSuperior) {
            int temp = limiteInferior;
            limiteInferior = limiteSuperior;
            limiteSuperior = temp;
        }

        int delta = (limiteSuperior - limiteInferior) / multiplo;

        return limiteInferior + enteroPositivo(delta) * multiplo;
    }

    /**
     * Genera un vector de tamaño n con números aleatorios enteros en el
     * intervalo [0, m-1].
     *
     * @param m Entero positivo que indica el límite superior de los valores
     * aleatorios
     * @param n Entero positivo que indica el tamaño del vector
     * @return Un vector de enteros aleatorios en el intervalo [0, m-1]
     */
    public static int[] generarVectorEntero(int m, int n) {
        if (m < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: El tamaño del vector debe ser mayor que cero");
        }
        int[] aleatorios = new int[n];

        for (int i = 0; i < n; i++) {
            aleatorios[i] = R.nextInt(m);
        }

        return aleatorios;
    }

    /**
     * Genera un vector de tamaño n con números aleatorios enteros en el
     * intervalo [1, m].
     *
     * @param m Entero positivo que indica el límite superior de los números
     * aleatorios.
     * @param n Entero positivo que indica el tamaño del vector
     * @return Vector de enteros aleatorios en el intervalo [1, m]
     * @throws IllegalArgumentException
     */
    public static int[] generarVectorEnterosPositivos(int m, int n) throws IllegalArgumentException {
        if (m < 1) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser mayor que cero");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Argumento no válido: El tamaño del vector debe ser mayor que cero");
        }
        int[] aleatorios = new int[n];

        for (int i = 0; i < n; i++) {
            aleatorios[i] = 1 + R.nextInt(m);
        }

        return aleatorios;
    }

    /**
     * Genera un vector de tamaño n con números aleatorios enteros en el
     * intervalo [a, b].
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param n Entero positivo que indica el tamaño del vector
     * @return Vector de enteros aleatorios en el intervalo [a, b]
     */
    public static int[] generarVectorEntero(int a, int b, int n) {
        int[] aleatorios = new int[n];
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        for (int i = 0; i < n; i++) {
            aleatorios[i] = a + R.nextInt(b - a + 1);
        }

        return aleatorios;
    }

    public static int[] generarVectorEnteroSinRepeticiones(int a, int b, int n) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        int d = b - a + 1;

        if (d < n) {
            throw new IllegalArgumentException("El intervalo [" + a + ", " + b + "] es muy pequeño.");
        }

        int[] aleatorios = new int[n];
        TreeSet<Integer> numeros = new TreeSet<>();
        int i = 0;

        while (i < n) {
            int num = a + R.nextInt(b - a + 1);

            if (numeros.add(num)) {
                aleatorios[i] = num;
                i++;
            }
        }

        return aleatorios;
    }

    public static int[] generarVectorEnteroOrdenadoSinRepeticiones(int a, int b, int n) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        int d = b - a + 1;

        if (d < n) {
            throw new IllegalArgumentException("El intervalo [" + a + ", " + b + "] es muy pequeño.");
        }

        int[] aleatorios = new int[n];
        TreeSet<Integer> numeros = new TreeSet<>();
        int i = 0;

        while (i < n) {
            int num = a + R.nextInt(b - a + 1);

            if (numeros.add(num)) {
                i++;
            }
        }
        i = 0;
        for (int numer : numeros) {
            aleatorios[i++] = numer;
        }

        return aleatorios;
    }

    /**
     * Genera una lista de enteros (IntegerArrayList) de tamaño n con números
     * aleatorios enteros en el intervalo [0, m-1].
     *
     * @param m Entero positivo que indica el límite superior de los valores
     * aleatorios
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Una lista de enteros aleatorios en el intervalo [0, m-1]
     */
    public static IntegerArrayList generarListaDeEnteros(int m, int n) {
        int[] valores = generarVectorEntero(m, n);
        IntegerArrayList lista = new IntegerArrayList();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera una lista de enteros (IntegerArrayList) de tamaño n con números
     * aleatorios enteros en el intervalo [a, b].
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Lista de enteros aleatorios en el intervalo [a, b]
     */
    public static IntegerArrayList generarListaDeEnteros(int a, int b, int n) {
        int[] valores = generarVectorEntero(a, b, n);
        IntegerArrayList lista = new IntegerArrayList();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    public static IntegerArrayList generarListaDeEnterosSinRepeticiones(int a, int b, int n) {
        int[] valores = generarVectorEnteroSinRepeticiones(a, b, n);
        IntegerArrayList lista = new IntegerArrayList();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera una lista (ArrayList) de tamaño n con números aleatorios enteros
     * en el intervalo [0, m-1].
     *
     * @param m Entero positivo que indica el límite superior de los valores
     * aleatorios
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Una lista de enteros aleatorios en el intervalo [0, m-1]
     */
    public static ArrayList<Integer> generarListaEntera(int m, int n) {
        int[] valores = generarVectorEntero(m, n);
        ArrayList<Integer> lista = new ArrayList<>();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera una lista (ArrayList) de tamaño n con números aleatorios enteros
     * en el intervalo [a, b].
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Lista de enteros aleatorios en el intervalo [a, b]
     */
    public static ArrayList<Integer> generarListaEntera(int a, int b, int n) {
        int[] valores = generarVectorEntero(a, b, n);
        ArrayList<Integer> lista = new ArrayList<>();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    public static ArrayList<Integer> generarListaEnteraSinRepeticiones(int a, int b, int n) {
        int[] valores = generarVectorEnteroSinRepeticiones(a, b, n);
        ArrayList<Integer> lista = new ArrayList<>();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera lista (ArrayList) de tamaño n con números aleatorios reales en el
     * intervalo [0, 1).
     *
     *
     * @param n Entero positivo que indica el tamaño de la lista.
     * @return Lista de reales aleatorios en el intervalo [0, 1)
     */
    public static ArrayList<Double> generarListaReal(int n) {
        double[] valores = generarVectorReal(n);
        ArrayList<Double> lista = new ArrayList<>();

        for (double valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera una lista (ArrayList) de tamaño n con números aleatorios reales en
     * el intervalo [a, b] con los decimales especificados en el parametro
     * decimales.
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param decimales Entero en el intervalo [0, 5]
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Lista real con números aleatorios en el intervalo [a, b]
     */
    public static ArrayList<Double> generarListaReal(int a, int b, int decimales, int n) {
        double[] valores = generarVectorReal(a, b, decimales, n);
        ArrayList<Double> lista = new ArrayList<>();

        for (double valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera lista (DoubleArrayList) de tamaño n con números aleatorios reales
     * en el intervalo [0, 1).
     *
     *
     * @param n Entero positivo que indica el tamaño de la lista.
     * @return Lista de reales aleatorios en el intervalo [0, 1)
     */
    public static DoubleArrayList generarDoubleArrayList(int n) {
        double[] valores = generarVectorReal(n);
        DoubleArrayList lista = new DoubleArrayList();

        for (double valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera una lista (ArrayList) de tamaño n con números aleatorios reales en
     * el intervalo [a, b] con los decimales especificados en el parametro
     * decimales.
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param decimales Entero en el intervalo [0, 5]
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Lista real con números aleatorios en el intervalo [a, b]
     */
    public static DoubleArrayList generarDoubleArrayList(int a, int b, int decimales, int n) {
        double[] valores = generarVectorReal(a, b, decimales, n);
        DoubleArrayList lista = new DoubleArrayList();

        for (double valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    public static IntegerArrayList generarIntegerArrayList(int a, int b, int n) {
        int[] valores = generarVectorEntero(a, b, n);
        IntegerArrayList lista = new IntegerArrayList();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    public static IntegerLinkedList generarIntegerLinkedList(int a, int b, int n) {
        int[] valores = generarVectorEntero(a, b, n);
        IntegerLinkedList lista = new IntegerLinkedList();

        for (int valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    /**
     * Genera una lista simplemente enlazada (DoubleLinkedList) de tamaño n con
     * números aleatorios reales en el intervalo [a, b] con los decimales
     * especificados en el parametro decimales.
     *
     * @param a Entero menor que el parametro b
     * @param b Entero mayor que el parametro a
     * @param decimales Entero en el intervalo [0, 5]
     * @param n Entero positivo que indica el tamaño de la lista
     * @return Lista real con números aleatorios en el intervalo [a, b]
     */
    public static DoubleLinkedList generarDoubleLinkedList(int a, int b, int decimales, int n) {
        double[] valores = generarVectorReal(a, b, decimales, n);
        DoubleLinkedList lista = new DoubleLinkedList();

        for (double valor : valores) {
            lista.add(valor);
        }
        return lista;
    }

    public static String generarCodigoCuentaAhorros(int digitos) {
        String codigo = "";

        for (int i = 0; i < digitos; i++) {
            codigo += R.nextInt(10);
        }

        return "A-" + codigo;
    }

    public static CuentaDeAhorros generarCuentaDeAhorros() {
        String nombre;
        String apellido = generarApellido();
        double nota = enteroMultiplo(800000, 10000000, 200000);
        int codigo = entero(1, 10000000);
        int genero;

        if (generarBoolean()) {
            genero = Persona.FEMENINO;
            nombre = generarNombreFemenino();
        } else {
            genero = Persona.MASCULINO;
            nombre = generarNombreMasculino();
        }

        return new CuentaDeAhorros(codigo, nombre, apellido, nota);
    }

    public static CuentaDeAhorros[] generarCuentaDeAhorros(int n) {
        CuentaDeAhorros[] cuentaDeAhorros = new CuentaDeAhorros[n];

        for (int i = 0; i < n; i++) {
            String apellido = generarApellido();
            double nota = enteroMultiplo(800000, 10000000, 200000);

            int genero = generarGenero();
            String nombre = generarNombre(genero);

            cuentaDeAhorros[i] = new CuentaDeAhorros(i + 1, nombre, apellido, nota);
        }
        return cuentaDeAhorros;
    }

    public static CuentaDeAhorros[] generarCuentaDeAhorros2(int n) {
        CuentaDeAhorros[] cuentaDeAhorros = generarCuentaDeAhorros(n);

        for (int i = 0; i < n; i++) {
            int j = entero(n);
            int k = entero(n);
            CuentaDeAhorros temp = cuentaDeAhorros[j];
            cuentaDeAhorros[j] = cuentaDeAhorros[k];
            cuentaDeAhorros[k] = temp;
        }
        return cuentaDeAhorros;
    }

    public static String[] generarCodigoCuentaAhorros2(int n) {
        if (n >= 10000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que 10 millones");
        }
        String[] codigos = new String[n];
        TreeSet<String> datos = new TreeSet<>();
        int i = 0;

        while (i < n) {
            String codigo = generarCodigoCuentaAhorros(4);

            if (datos.add(codigo)) {
                codigos[i] = codigo;
                i++;
            }
        }

        return codigos;
    }

    public static String generarCodigoCuentaCorriente(int digitos) {

        String codigo = "";

        for (int i = 0; i < digitos; i++) {
            codigo += R.nextInt(10);
        }

        return "C-" + codigo;
    }

    public static String[] generarCodigoCuentaCorriente2(int n) {
        if (n >= 10000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que 10 millones");
        }
        String[] codigos = new String[n];
        TreeSet<String> datos = new TreeSet<>();
        int i = 0;

        while (i < n) {
            String codigo = generarCodigoCuentaCorriente(4);
            if (datos.add(codigo)) {
                codigos[i] = codigo;
                i++;
            }
        }

        return codigos;
    }

    /**
     * Genera parejas aleatorias de valores enteros y las almacena en una
     * matriz. La primera fila contiene el primer miembro de cada pareja y la
     * segunda fila almacena el segundo miembro de cada pareja.
     *
     * @param n Valor entero par que indica la cantidad de valores con los que
     * se formaran las n/2 parejas.
     * @return Una matriz de dos filas y n/2 columnas con las parejas formadas
     * aleatoriamente.
     * @throws IllegalArgumentException
     */
    public static int[][] generarParejas(int n) throws IllegalArgumentException {
        if (n < 4) {
            throw new IllegalArgumentException("El parametro debe ser mayor que 3");
        }
        if (n % 2 == 1) {
            --n;
        }

        ArrayList<Integer> lista = new ArrayList();

        for (int i = 1; i <= n; i++) {
            lista.add(i);
        }

        Random aleatorio = new Random();
        int[][] parejas = new int[2][n / 2];
        int j = 0;

        while (!lista.isEmpty()) {
            int t = lista.size() - 1;
            int indice = aleatorio.nextInt(t);
            parejas[0][j] = lista.get(indice);
            lista.remove(indice);

            t = lista.size() - 1;

            if (t > 0) {
                indice = aleatorio.nextInt(t);
            } else {
                indice = 0;
            }

            parejas[1][j] = lista.get(indice);
            lista.remove(indice);
            ++j;
        }
        return parejas;
    }

    /**
     * Genera una letra minúscula aleatoria.
     *
     * @return Retorna un caracter alfabetico aleatorio.
     */
    public static char generarLetraMinuscula() {
        String letras = "abcdefghijklmnñopqrstuvwxyz";
        int indice = R.nextInt(letras.length());

        return letras.charAt(indice);
    }

    /**
     * Genera una letra mayúscula aleatoria.
     *
     * @return Retorna un caracter alfabetico aleatorio.
     */
    public static char generarLetraMayuscula() {
        String letras = "abcdefghijklmnñopqrstuvwxyz".toUpperCase();
        int indice = R.nextInt(letras.length());

        return letras.charAt(indice);
    }

    /**
     * Genera una letra aleatoria. La letra puede ser minúscula o mayúscula.
     *
     * @return Retorna un caracter alfabetico aleatorio.
     */
    public static char generarLetra() {
        String letras = "abcdefghijklmnñopqrstuvwxyz";
        letras += letras.toUpperCase();

        int indice = R.nextInt(letras.length());

        return letras.charAt(indice);
    }

    /**
     * Genera un valor lógico aleatorio.
     *
     * @return Retorna true o false.
     */
    public static boolean generarBoolean() {
        return R.nextInt(2) == 0;
    }

    /**
     * Genera un vector de tamaño n con valores lógicos (true, false)
     * aleatorios.
     *
     * @param n Entero positivo que indica el tamaño del vector.
     * @return Retorna un vector con valores lógicos aleatorios.
     */
    public static boolean[] generarVectorBoolean(int n) {
        boolean[] valores = new boolean[n];
        for (int i = 0; i < n; i++) {
            valores[i] = R.nextInt(2) == 0;
        }
        return valores;
    }

    /**
     * Recibe un vector con valores enteros y devuelve uno de esos valores
     * elegido de forma aleatoria.
     *
     * @param valores Vector con valores enteros
     * @return Retorna un valor elegido aleatoriamente del vector valores.
     */
    public static int elegir(int[] valores) {
        return valores[R.nextInt(valores.length)];
    }

    /**
     * Recibe un vector con valores reales y devuelve uno de esos valores
     * elegido de forma aleatoria.
     *
     * @param valores Vector con valores reales
     * @return Retorna un valor elegido aleatoriamente del vector valores.
     */
    public static double elegir(double[] valores) {
        return valores[R.nextInt(valores.length)];
    }

    /**
     * Recibe un vector con valores tipo String y devuelve uno de esos valores
     * elegido de forma aleatoria.
     *
     * @param valores Vector con valores de tipo String
     * @return Retorna un valor elegido aleatoriamente del vector valores.
     */
    public static String elegir(String[] valores) {
        return valores[R.nextInt(valores.length)];
    }

    public static String generarCadena(String alfabeto, int longitud) {
        String cadena = "";

        if (alfabeto != null) {
            for (int i = 0; i < longitud; i++) {
                int k = entero(alfabeto.length());
                cadena += alfabeto.charAt(k);
            }
        }
        return cadena;
    }

    /**
     * Genera una matriz de tamaño filas x columnas con valores enteros
     * aleatorios en el intervalo [a, b].
     *
     * @param filas Entero positivo que indica la cantidad de filas de la matriz
     * @param columnas Entero positivo que indica la cantidad de columnas de la
     * matriz
     * @param a Entero que indica el límite inferior de los valores aleatorios,
     * debe ser menor que el parametro b
     * @param b Entero que indica el límite superior de los valores aleatorios,
     * debe ser mayor que el parametro a
     * @return Retorna un matriz de tamaño filas x columnas con valores
     * aleatories en el intervalo [a, b]
     */
    public static int[][] generarMatrizEnteros(int filas, int columnas, int a, int b) {
        int[][] matriz = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = entero(a, b);
            }
        }
        return matriz;
    }

    public static int[][] generarArregloBidimensionalEnteros(int filas, int columnas, int a, int b) {
        int[][] matriz = new int[filas][];

        for (int i = 0; i < matriz.length; i++) {
            matriz[i] = new int[entero(columnas)];
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = entero(a, b);
            }
        }
        return matriz;
    }

    public static byte[][] generarMatrizAdyacencia(int n) {
        byte[][] matriz = new byte[n][n];

        int t = n * (n - 1) / 2;
        int c = entero(t / 3, 3 * t / 4);

        while (c > 0) {
            int i = entero(n);
            int j = entero(n);

            if (i != j && matriz[i][j] == 0) {
                matriz[i][j] = 1;
                matriz[j][i] = 1;
                c--;
            }
        }

        return matriz;
    }

    /**
     * Genera una matriz de tamaño filas x columnas con valores aleatorios
     * reales en el intervalo [a, b].
     *
     * @param filas Entero positivo que indica la cantidad de filas de la matriz
     * @param columnas Entero positivo que indica la cantidad de columnas de la
     * matriz
     * @param a Entero que indica el límite inferior de los valores aleatorios,
     * debe ser menor que el parametro b
     * @param b Entero que indica el límite superior de los valores aleatorios,
     * debe ser mayor que el parametro a
     * @param decimales Indica la cantidad de decimales de los valores
     * aleatorios. La cantidad debe ser menor o igual a cinco.
     * @return Retorna un matriz de tamaño filas x columnas con valores
     * aleatories en el intervalo [a, b]
     * @throws IllegalArgumentException
     */
    public static double[][] generarMatrizReal(int filas, int columnas, int a, int b, int decimales) throws IllegalArgumentException {
        double[][] matriz = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = real(a, b, decimales);
            }
        }
        return matriz;
    }

    /**
     * Genera datos para una matriz de enteros dispersa representada mediante
     * tripletas. La tripleta esta formada por el indice de la fila, el indice
     * de la columna y el valor diferente de cero (i, j, valor).
     *
     * @param filas Entero que indica la cantidad de filas de la matriz densa.
     * @param columnas Entero que indica la cantidad de columnas de la matriz
     * densa.
     * @param dispersion Representa la dispersión de la matriz densa.
     * @param a Límite inferior para generar los valores aleatorios.
     * @param b Límite superior para generar los valores aleatorios.
     * @return Una matriz de enteros, cada fila representa una tripleta.
     * @throws IllegalArgumentException Si las filas o las columnas son menores
     * que uno (1)
     */
    public static int[][] generarTripletasMatrizDispersa(int filas, int columnas, double dispersion, int a, int b) throws IllegalArgumentException {
        if (filas < 1) {
            throw new IllegalArgumentException("Las filas deben ser mayores que cero.");
        }
        if (columnas < 1) {
            throw new IllegalArgumentException("Las columnas deben ser mayores que cero.");
        }
        if (dispersion < 1 || dispersion > 98) {
            throw new IllegalArgumentException("La dispersion debe ser un valor en el intervalo [1, 100]");
        }
        int cantidadTripletas = (int) Math.round(filas * columnas * dispersion / 100);
        int[][] matriz = new int[cantidadTripletas][3];

        TreeSet<String> cadenas = new TreeSet<>();
        int i = 0;
        while (i < cantidadTripletas) {
            int row = entero(filas);
            int column = entero(columnas);

            String tripleta = row + "," + column;

            if (cadenas.add(tripleta)) {
                matriz[i][0] = row;
                matriz[i][1] = column;
                matriz[i][2] = entero(a, b);
                i++;
            }
        }
        return matriz;
    }

    public static double[][] generarTripletasMatrizDispersa(int filas, int columnas, double dispersion, int a, int b, int decimales) {
        if (filas < 1) {
            throw new IllegalArgumentException("Las filas deben ser mayores que cero.");
        }
        if (columnas < 1) {
            throw new IllegalArgumentException("Las columnas deben ser mayores que cero.");
        }
        if (dispersion < 1 || dispersion > 98) {
            throw new IllegalArgumentException("La dispersion debe ser un valor en el intervalo [1, 100]");
        }
        int cantidadTripletas = (int) Math.round(filas * columnas * dispersion / 100);
        double[][] matriz = new double[cantidadTripletas][3];

        TreeSet<String> cadenas = new TreeSet<>();
        int i = 0;

        while (i < cantidadTripletas) {
            int row = entero(filas);
            int column = entero(columnas);

            String tripleta = row + "," + column;

            if (cadenas.add(tripleta)) {
                matriz[i][0] = row;
                matriz[i][1] = column;
                matriz[i][2] = real(a, b, decimales);
                i++;
            }
        }
        return matriz;
    }

    /**
     * El Número de Identificación Tributaria, conocido también por el acrónimo
     * NIT, es un número único colombiano que asigna la DIAN (Dirección de
     * Impuestos y Aduanas Nacionales de Colombia) por una sola vez cuando el
     * obligado se inscribe en el RUT (Registro Único Tributario). La
     * conformación del NIT es de competencia de la DIAN. Recordamos que el RUT
     * es el mecanismo único para identificar, ubicar y clasificar a los sujetos
     * de obligaciones administradas y controladas por la DIAN.
     *
     * El RUT le permite a la DIAN contar con información veraz, actualizada,
     * clasificada y confiable de todos los sujetos obligados a inscribirse en
     * el mismo, para desarrollar una gestión efectiva en materia de recaudo,
     * control y servicio que a su vez facilite el cumplimiento de las
     * obligaciones tributarias, aduaneras y cambiarias así como la
     * simplificación de trámites y reducción de costos.
     *
     * @return Retorna una cadena con 10 digitos
     */
    public static String generarNIT() {
        return R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10)
                + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10)
                + R.nextInt(10) + R.nextInt(10) + "";
    }

    /**
     * El Número de Identificación Tributaria, conocido también por el acrónimo
     * NIT, es un número único colombiano que asigna la DIAN (Dirección de
     * Impuestos y Aduanas Nacionales de Colombia) por una sola vez cuando el
     * obligado se inscribe en el RUT (Registro Único Tributario). La
     * conformación del NIT es de competencia de la DIAN. Recordamos que el RUT
     * es el mecanismo único para identificar, ubicar y clasificar a los sujetos
     * de obligaciones administradas y controladas por la DIAN.
     *
     * El RUT le permite a la DIAN contar con información veraz, actualizada,
     * clasificada y confiable de todos los sujetos obligados a inscribirse en
     * el mismo, para desarrollar una gestión efectiva en materia de recaudo,
     * control y servicio que a su vez facilite el cumplimiento de las
     * obligaciones tributarias, aduaneras y cambiarias así como la
     * simplificación de trámites y reducción de costos.
     *
     * @param n Entero positivo que indica la cantidad de números NIT diferentes
     * que se generarán.
     * @return Retorna un vector de tipo String con n números NIT diferentes.
     * @throws IllegalArgumentException
     */
    public static String[] generarVectorNIT(int n) throws IllegalArgumentException {
        if (n > 1000000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que mil millones");
        }
        String[] nits = new String[n];
        TreeSet<String> datos = new TreeSet<>();
        int i = 0;

        while (i < n) {
            String nit = generarNIT();

            if (datos.add(nit)) {
                nits[i] = nit;
                i++;
            }
        }

        return nits;
    }

    /**
     * Genera una placa de carro aleatoria.
     *
     * @return Retorna una cadena con un placa de carro aleatoria.
     */
    public static String generarPlacaCarro() {
        String letras = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        int n = letras.length();

        String placa = "" + letras.charAt(R.nextInt(n)) + letras.charAt(R.nextInt(n)) + letras.charAt(R.nextInt(n));
        placa += " " + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);

        return placa;
    }

    /**
     * Genera una placa de motocicleta aleatoria.
     *
     * @return Retorna una cadena con un placa de motocicleta aleatoria.
     */
    public static String generarPlacaMoto() {
        String letras = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        int n = letras.length();

        String placa = "" + letras.charAt(R.nextInt(n)) + letras.charAt(R.nextInt(n)) + letras.charAt(R.nextInt(n));
        placa += " " + R.nextInt(10) + R.nextInt(10) + letras.charAt(R.nextInt(n));

        return placa;
    }

    /**
     * Genera aleatoriamente un día de la semana.
     *
     * @return Retorna una cadena con el nombre de un día de la semana.
     */
    public static String generarDiaSemana() {
        return Fecha.DIAS[entero(Fecha.DIAS.length)];
    }

    /**
     * Genera aleatoriamente el nombre de un mes del año.
     *
     * @return El nombre de un mes en español.
     */
    public static String generarMes() {
        return Fecha.MESES[R.nextInt(Fecha.MESES.length)];
    }

    /**
     * Genera una fecha aleatoria entre dos años.
     *
     * @param anio1 Valor entero que representa un año
     * @param anio2 Valor entero que representa otro año
     * @return Retorna una fecha aleatoria entre los años anio1 y anio2
     */
    public static Fecha generarFecha(int anio1, int anio2) {
        return new Fecha(generarFecha2(anio1, anio2));
    }

    private static String generarFecha2(int anio1, int anio2) {
        if (anio1 < 0 || anio2 < 0) {
            throw new IllegalArgumentException("El año debe ser no negativo.");
        }
        if (anio1 > anio2) {
            int temp = anio1;
            anio1 = anio2;
            anio2 = temp;
        }

        String fecha;
        int anio = anio1 + R.nextInt(anio2 - anio1 + 1);
        int mes = 1 + R.nextInt(12);
        int dia, maximoDias;

        // 28 o 29: febrero
        // 30 dias: abril, junio, septiembre, noviembre
        // 31 dias: enero, marzo, mayo, julio, agosto, octubre, diciembre
        switch (mes) {
            case 2:
                maximoDias = (Util.esBisiesto(anio) ? 29 : 28);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maximoDias = 30;
                break;
            default:
                maximoDias = 31;
        }

        dia = 1 + R.nextInt(maximoDias);

        fecha = (dia < 10) ? "0" + dia + "/" : dia + "/";
        fecha += (mes < 10) ? "0" + mes : "" + mes;
        fecha += "/" + anio;

        return fecha;
    }

    /**
     * Genera un día aleatorio del mes actual.
     *
     * @return Retorna un entero que representa un día del mes actual.
     */
    public static int generarDia() {
        GregorianCalendar gc = new GregorianCalendar();

        // Obtenemos los valores del día, mes y año del calendario.
        int dias;
        int mes = gc.get(Calendar.MONTH) + 1;
        int anio = gc.get(Calendar.YEAR);

        switch (mes) {
            case 2:
                dias = (Util.esBisiesto(anio) ? 29 : 28);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                dias = 30;
                break;
            default:
                dias = 31;
        }

        return 1 + R.nextInt(dias);
    }

    /**
     * Genera una hora aleatoria con el formato hh:mm:ss
     *
     * @return Retorna una cadena que representa una hora.
     */
    public static String generarHora() {
        String hora = generarHoraSinSegundos() + ":" + R.nextInt(6) + R.nextInt(10);

        return hora;
    }

    /**
     * Genera una hora aleatoria con el formato hh:mm
     *
     * @return Retorna una cadena que representa una hora.
     */
    public static String generarHoraSinSegundos() {
        int h = R.nextInt(24);

        String hora = (h < 10) ? "0" + h : "" + h;
        hora += ":" + R.nextInt(6) + R.nextInt(10);

        return hora;
    }

    /**
     * Retorna un nombre masculino aleatorio
     *
     * @return Nombre masculino
     */
    public static String generarNombreMasculino() {
        return hombres[R.nextInt(hombres.length)];
    }

    /**
     * Retorna un nombre masculino aleatorio más un apellido aleatorio
     *
     * @return Nombre masculino más un apellido
     */
    public static String generarNombreMasculinoYApellido() {
        return generarNombreMasculino() + " " + generarApellido();
    }

    /**
     * Retorna un nombre femenino aleatorio
     *
     * @return Nombre femenino
     */
    public static String generarNombreFemenino() {
        return mujeres[R.nextInt(mujeres.length)];
    }

    /**
     * Retorna un nombre femenino aleatorio más un apellido aleatorio
     *
     * @return Nombre femenino más un apellido
     */
    public static String generarNombreFemeninoYApellido() {
        return generarNombreFemenino() + " " + generarApellido();
    }

    /**
     * Retorna un apellido aleatorio
     *
     * @return Un apellido
     */
    public static String generarApellido() {
        return apellidos[R.nextInt(apellidos.length)];
    }

    /**
     * Retorna dos apellidos aleatorios en una unica cadena.
     *
     * @return String que contiene dos apellidos separados por un espacio en
     * blanco.
     */
    public static String generarApellidos() {
        return apellidos[R.nextInt(apellidos.length)] + " " + apellidos[R.nextInt(apellidos.length)];
    }

    /**
     * Retorna un nombre aleatorio. Si el argumento es uno (1) retorna un nombre
     * femenino y si el argumento es dos (2) retorna un nombre masculino. Para
     * cualquier otro valor del argumento el metodo lanzará la excepcion
     *
     * @param genero entero que solo puede ser 1 o 2
     * @return String que representa un nombre.
     * @throws IllegalArgumentException
     */
    public static String generarNombre(int genero) throws IllegalArgumentException {
        switch (genero) {
            case Persona.FEMENINO:
                return mujeres[R.nextInt(mujeres.length)];
            case Persona.MASCULINO:
                return hombres[R.nextInt(hombres.length)];
            default:
                throw new IllegalArgumentException("Argumento no válido. Solo son válidos el 0 y el 1.");
        }
    }

    /**
     * Verifica si un nombre pertenece a la clase. Verifica si el nombre se
     * puede generar aleatoriamente.
     *
     * @param nombre Cadena que representa el nombre de una mujer o de un
     * hombre.
     * @return Retorna true si ya existe el nombre y false en caso contrario.
     */
    public static boolean existeNombre(String nombre) {
        nombre = nombre.toLowerCase();

        for (String hombre : hombres) {
            if (hombre.toLowerCase().compareTo(nombre) == 0) {
                return true;
            }
        }
        for (String mujer : mujeres) {
            if (mujer.toLowerCase().compareTo(nombre) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un apellido pertenece a la clase. Verifica si el apellido se
     * puede generar aleatoriamente.
     *
     * @param apellido Cadena que representa un apellido.
     * @return Retorna true si ya existe el apellido y false en caso contrario.
     */
    public static boolean existeApellido(String apellido) {
        for (String hombre : apellidos) {
            if (hombre.compareToIgnoreCase(apellido) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera un objeto de la clase Estudiante y lo inicializa con valores
     * aleatorios.
     *
     * @return
     */
    public static Estudiante generarEstudiante() {
        String apellido = generarApellido();
        String nuip = generarNUIP();
        double nota = real(0, 5, 1);
        int genero = generarGenero();
        String nombre = generarNombre(genero);

        return new Estudiante(nuip, nombre, apellido, genero, nota);
    }

    private static Estudiante generarEstudianteSinNUIP() {
        String apellido = generarApellido();
        String nuip = "";
        double nota = real(0, 5, 1);
        int genero = generarGenero();
        String nombre = generarNombre(genero);

        return new Estudiante(nuip, nombre, apellido, genero, nota);
    }

    /**
     * Genera un objeto de la clase Empleado y lo inicializa con valores
     * aleatorios.
     *
     * @return
     */
    public static Empleado generarEmpleado() {
        String apellido = generarApellido();
        String nuip = generarNUIP();
        double salario = enteroMultiplo(1000000, 5000000, 20000);
        int genero = generarGenero();
        String nombre = generarNombre(genero);

        Fecha actual = Fecha.hoy();
        int anioActual = actual.anio;

        Fecha fechaNac = new Fecha(generarFecha(anioActual - 65, anioActual - 18));
        Fecha fechaVin = new Fecha(generarFecha(fechaNac.anio + 17, anioActual - 1));

        return new Empleado(nuip, nombre, apellido, genero, salario, fechaNac, fechaVin);
    }

    public static Empleado generarEmpleado2() {
        String apellido = generarApellido();
        String nuip = generarNUIP2();
        double salario = enteroMultiplo(1000000, 5000000, 20000);
        int genero = generarGenero();
        String nombre = generarNombre(genero);

        Fecha actual = Fecha.hoy();
        int anioActual = actual.anio;

        Fecha fechaNac = new Fecha(generarFecha(anioActual - 65, anioActual - 18));
        Fecha fechaVin = new Fecha(generarFecha(fechaNac.anio + 17, anioActual - 1));

        return new Empleado(nuip, nombre, apellido, genero, salario, fechaNac, fechaVin);
    }

    public static Empleado generarEmpleadoSinNUIP() {
        String apellido = generarApellido();
        String nuip = "";
        double salario = enteroMultiplo(1000000, 5000000, 20000);
        int genero = generarGenero();
        String nombre = generarNombre(genero);

        Fecha actual = Fecha.hoy();
        int anioActual = actual.anio;

        Fecha fechaNac = new Fecha(generarFecha(anioActual - 65, anioActual - 18));
        Fecha fechaVin = new Fecha(generarFecha(fechaNac.anio + 17, anioActual - 1));

        return new Empleado(nuip, nombre, apellido, genero, salario, fechaNac, fechaVin);
    }

    public static String generarEstudiantesJSON(int n) {
        if (n == 0) {
            return "{\n}";
        }
        if (n < 0) {
            throw new IllegalArgumentException("" + n);
        }
        String texto = "{\n";

        Estudiante[] e = generarEstudiantes(n);

        for (int i = 0; i < n; i++) {
            texto += "  {\n";
            Estudiante estudiante = e[i];
            texto += "    \"id\": " + (i + 1) + ",\n";
            texto += "    \"nuip\": \"" + estudiante.getNuip() + "\",\n";
            texto += "    \"nombre\": \"" + estudiante.getNombre() + "\",\n";
            texto += "    \"apellido\": \"" + estudiante.getApellido() + "\",\n";
            texto += "    \"genero\": \"" + (estudiante.esMujer() ? "F" : "M") + "\",\n";
            texto += "    \"nota\": " + estudiante.getNota() + "\n";
            texto += "  },\n";
        }

        return texto.substring(0, texto.length() - 2) + "\n}";
    }

    /**
     * Genera un vector de objetos de la clase Estudiante y los inicializa con
     * valores aleatorios. El NUIP de cada estudiante es único y separado con
     * puntos.
     *
     * @param n Cantidad de estudiantes en el vector retornado.
     * @return Vector de tipo Estudiante de tamaño n.
     */
    public static Estudiante[] generarEstudiantes(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        String[] nuips = generarNUIPs(n);
        Estudiante[] grupo = new Estudiante[n];

        for (int i = 0; i < n; i++) {
            grupo[i] = generarEstudianteSinNUIP();
            grupo[i].setNuip(nuips[i]);
            grupo[i].setRepitente(Aleatorio.generarBoolean());
        }
        return grupo;
    }

    /**
     * Genera un vector de objetos de la clase Estudiante y los inicializa con
     * valores aleatorios. El NUIP de cada estudiante es único y sus cifras no
     * estan separadas con puntos.
     *
     * @param n Cantidad de estudiantes en el vector retornado.
     * @return Vector de tipo Estudiante de tamaño n.
     */
    public static Estudiante[] generarEstudiantes2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        String[] nuips = generarNUIPs2(n);
        Estudiante[] grupo = new Estudiante[n];

        for (int i = 0; i < n; i++) {
            grupo[i] = generarEstudianteSinNUIP();
            grupo[i].setNuip(nuips[i]);
            grupo[i].setRepitente(Aleatorio.generarBoolean());
        }
        return grupo;
    }

    /**
     * Genera un vector de objetos de la clase Estudiante y los inicializa con
     * valores aleatorios. El NUIP de cada estudiante es único y se asigna
     * consecutivamente desde 1 hasta n (tamaño del vector).
     *
     * @param n Cantidad de estudiantes en el vector retornado.
     * @return Vector de tipo Estudiante de tamaño n.
     */
    public static Estudiante[] generarEstudiantes3(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Estudiante[] grupo = new Estudiante[n];

        for (int i = 0; i < n; i++) {
            grupo[i] = generarEstudianteSinNUIP();
            grupo[i].setNuip((i + 1) + "");
            grupo[i].setRepitente(Aleatorio.generarBoolean());
        }
        return grupo;
    }

    /**
     * Genera un vector de objetos de la clase Estudiante y los inicializa con
     * valores aleatorios. El NUIP de cada estudiante es único y se asigna
     * aleatoriamente con valores pertenecientes al intervalo [1, n], donde n es
     * el tamaño del vector.
     *
     * @param n Cantidad de estudiantes en el vector retornado.
     * @return Vector de tipo Estudiante de tamaño n.
     */
    public static Estudiante[] generarEstudiantes4(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Estudiante[] grupo = generarEstudiantes3(n);

        int r = 0;
        int s = entero(1, n - 1);

        Estudiante temp = grupo[r];
        grupo[r] = grupo[s];
        grupo[s] = temp;

        for (int i = 0; i < n; i++) {
            r = entero(n);
            s = entero(n);

            temp = grupo[r];
            grupo[r] = grupo[s];
            grupo[s] = temp;
        }
        return grupo;
    }

    public static ArrayList<Estudiante> generarListaEstudiantes(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Estudiante[] estudiantes = generarEstudiantes(n);
        ArrayList<Estudiante> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(estudiantes[i]);
        }
        return grupo;
    }

    public static ArrayList<Estudiante> generarListaEstudiantes2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        Estudiante[] estudiantes = generarEstudiantes2(n);
        ArrayList<Estudiante> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(estudiantes[i]);
        }
        return grupo;
    }

    public static ArrayList<Estudiante> generarListaEstudiantes3(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        Estudiante[] estudiantes = generarEstudiantes3(n);
        ArrayList<Estudiante> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(estudiantes[i]);
        }
        return grupo;
    }

    public static ArrayList<Estudiante> generarListaEstudiantes4(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        Estudiante[] estudiantes = generarEstudiantes4(n);
        ArrayList<Estudiante> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(estudiantes[i]);
        }
        return grupo;
    }

    public static void generarEstudiantes(String nombreArchivo, int n) {
        if (!nombreArchivo.contains(".")) {
            nombreArchivo += ".csv";
        }
        String[] nuips = generarNUIPs(n);
        File archivo = new File(nombreArchivo);

        try (PrintWriter output = new PrintWriter(archivo)) {
            for (String nuip : nuips) {
                int g = Aleatorio.generarGenero();
                String nombre = Aleatorio.generarNombre(g);
                String cadena = nuip + "," + nombre + "," + Aleatorio.generarApellido();
                cadena += "," + g + "," + Aleatorio.real(0, 5, 1);

                output.println(cadena);
            }
        } catch (IOException exc) {
        }
    }

    public static Empleado[] generarEmpleados(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        String[] nuips = generarNUIPs(n);
        Empleado[] grupo = new Empleado[n];

        for (int i = 0; i < n; i++) {
            grupo[i] = generarEmpleadoSinNUIP();
            grupo[i].setNuip(nuips[i]);
        }
        return grupo;
    }

    public static Empleado[] generarEmpleados2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        String[] nuips = generarNUIPs2(n);
        Empleado[] grupo = new Empleado[n];

        for (int i = 0; i < n; i++) {
            grupo[i] = generarEmpleadoSinNUIP();
            grupo[i].setNuip(nuips[i]);
        }
        return grupo;
    }

    public static ArrayList<Empleado> generarListaEmpleados(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Empleado[] empleados = generarEmpleados(n);
        ArrayList<Empleado> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(empleados[i]);
        }

        return grupo;
    }

    public static ArrayList<Empleado> generarListaEmpleados2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        Empleado[] empleado = generarEmpleados2(n);
        ArrayList<Empleado> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(empleado[i]);
        }
        return grupo;
    }

    public static ArrayList<Empleado> generarListaEmpleados3(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Empleado[] empleado = generarEmpleados3(n);
        ArrayList<Empleado> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(empleado[i]);
        }
        return grupo;
    }

    public static ArrayList<Empleado> generarListaEmpleados4(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Empleado[] empleado = generarEmpleados4(n);
        ArrayList<Empleado> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(empleado[i]);
        }
        return grupo;
    }

    /**
     * Genera un vector de objetos de la clase Empleado y los inicializa con
     * valores aleatorios. El NUIP de cada empleado es único y se asigna
     * consecutivamente desde 1 hasta n (tamaño del vector).
     *
     * @param n Cantidad de empleados en el vector retornado.
     * @return Vector de tipo Empleado de tamaño n.
     */
    public static Empleado[] generarEmpleados3(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Empleado[] grupo = new Empleado[n];

        for (int i = 0; i < n; i++) {
            grupo[i] = generarEmpleadoSinNUIP();
            grupo[i].setNuip((i + 1) + "");
        }
        return grupo;
    }

    /**
     * Genera un vector de objetos de la clase Empleado y los inicializa con
     * valores aleatorios. El NUIP de cada empleado es único y se asigna
     * aleatoriamente con valores pertenecientes al intervalo [1, n], donde n es
     * el tamaño del vector.
     *
     * @param n Cantidad de empleados en el vector retornado.
     * @return Vector de tipo Empleado de tamaño n.
     */
    public static Empleado[] generarEmpleados4(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Empleado[] grupo = generarEmpleados3(n);

        int r = 0;
        int s = entero(1, n - 1);

        Empleado temp = grupo[r];
        grupo[r] = grupo[s];
        grupo[s] = temp;

        for (int i = 0; i < n; i++) {
            r = entero(n);
            s = entero(n);

            temp = grupo[r];
            grupo[r] = grupo[s];
            grupo[s] = temp;
        }
        return grupo;
    }

    private static String formarUsuario(String nombre, String apellido) {
        String cadena = "";

        String[] names = nombre.split(" ");

        cadena = Util.eliminarTilde(names[0]).charAt(0) + "";

        if (names.length == 2) {
            cadena += Util.eliminarTilde(names[1]).charAt(0);
        }

        cadena += Util.eliminarTilde(Util.eliminar(' ', apellido));

        return cadena.toLowerCase();
    }

    public static Persona[] generarPersonas(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        String[] nuips = generarNUIPs(n);
        Persona[] grupo = new Persona[n];
        TreeSet<String> claves = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            String apellido = generarApellido();
            String apellido2 = generarApellido();
            String cellphone = generarNumeroCelular();

            int genero = generarGenero();
            String nombre = generarNombre(genero);

            String usuario = formarUsuario(nombre, apellido);

            int hoy = Fecha.hoy().anio;
            int anioInicial = hoy - 60;
            int anioFinal = hoy - 15;

            if (anioInicial < 1900) {
                anioInicial = 1980;
                anioFinal = 2005;
            }
            if (anioFinal < 1900) {
                anioFinal = 2005;
            }
            Fecha fechaNac = new Fecha(generarFecha(anioInicial, anioFinal));
            int anioNacimiento = fechaNac.anio;
            int intento = 0;
            String tempUsuario = usuario;

            while (!claves.add(tempUsuario)) {
                if (intento == 0) {
                    String segAp = Util.eliminarTilde(apellido2).toLowerCase();
                    usuario += segAp;
                    tempUsuario = usuario;
                    intento++;
                } else {
                    tempUsuario = usuario + anioNacimiento;
                    anioNacimiento++;
                }
            }
            usuario = tempUsuario;

            int correo = entero(3);
            String email = usuario + "@";
            if (correo == 0) {
                email += "gmail.com";
            } else if (correo == 1) {
                email += "hotmail.com";
            } else {
                email += "outlook.es";
            }

            grupo[i] = new Persona(nuips[i], nombre, apellido, apellido2, cellphone, email, fechaNac, genero);
        }

        return grupo;
    }

    public static Persona[] generarPersonas2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        String[] nuips = generarNUIPs2(n);
        Persona[] grupo = new Persona[n];
        TreeSet<String> claves = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            String apellido = generarApellido();
            String apellido2 = generarApellido();
            String cellphone = generarNumeroCelular();

            int genero = generarGenero();
            String nombre = generarNombre(genero);

            String usuario = formarUsuario(nombre, apellido);

            int hoy = Fecha.hoy().anio;
            int anioInicial = hoy - 60;
            int anioFinal = hoy - 15;

            if (anioInicial < 1900) {
                anioInicial = 1980;
                anioFinal = 2005;
            }
            if (anioFinal < 1900) {
                anioFinal = 2005;
            }

            Fecha fechaNac = new Fecha(generarFecha(anioInicial, anioFinal));
            int anioNacimiento = fechaNac.anio;
            int intento = 0;
            String tempUsuario = usuario;

            while (!claves.add(tempUsuario)) {
                if (intento == 0) {
                    String segAp = Util.eliminarTilde(apellido2).toLowerCase();
                    usuario += segAp;
                    tempUsuario = usuario;
                    intento++;
                } else {
                    tempUsuario = usuario + anioNacimiento;
                    anioNacimiento++;
                }
            }
            usuario = tempUsuario;

            int correo = entero(3);
            String email = usuario + "@";
            if (correo == 0) {
                email += "gmail.com";
            } else if (correo == 1) {
                email += "hotmail.com";
            } else {
                email += "outlook.es";
            }
            grupo[i] = new Persona(nuips[i], nombre, apellido, apellido2, cellphone, email, fechaNac, genero);
        }

        return grupo;
    }

    public static Persona[] generarPersonas3(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }
        Persona[] grupo = new Persona[n];
        TreeSet<String> claves = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            String apellido = generarApellido();
            String apellido2 = generarApellido();
            String cellphone = generarNumeroCelular();

            int genero = generarGenero();
            String nombre = generarNombre(genero);

            String usuario = formarUsuario(nombre, apellido);

            int hoy = Fecha.hoy().anio;
            int anioInicial = hoy - 60;
            int anioFinal = hoy - 15;

            if (anioInicial < 1900) {
                anioInicial = 1980;
                anioFinal = 2005;
            }
            if (anioFinal < 1900) {
                anioFinal = 2005;
            }
            Fecha fechaNac = new Fecha(generarFecha(anioInicial, anioFinal));
            int anioNacimiento = fechaNac.anio;
            int intento = 0;
            String tempUsuario = usuario;

            while (!claves.add(tempUsuario)) {
                if (intento == 0) {
                    String segAp = Util.eliminarTilde(apellido2).toLowerCase();
                    usuario += segAp;
                    tempUsuario = usuario;
                    intento++;
                } else {
                    tempUsuario = usuario + anioNacimiento;
                    anioNacimiento++;
                }
            }
            usuario = tempUsuario;

            int correo = entero(3);
            String email = usuario + "@";
            if (correo == 0) {
                email += "gmail.com";
            } else if (correo == 1) {
                email += "hotmail.com";
            } else {
                email += "outlook.es";
            }
            grupo[i] = new Persona("" + (i + 1), nombre, apellido, apellido2, cellphone, email, fechaNac, genero);
        }
        return grupo;
    }

    public static Persona[] generarPersonas4(int n) {
        Persona[] grupo = generarPersonas3(n);

        int r = 0;
        int s = entero(1, n - 1);

        Persona temp = grupo[r];
        grupo[r] = grupo[s];
        grupo[s] = temp;

        for (int i = 0; i < n; i++) {
            r = entero(n);
            s = entero(n);

            temp = grupo[r];
            grupo[r] = grupo[s];
            grupo[s] = temp;
        }

        return grupo;
    }

    public static ArrayList<Persona> generarListaPersonas(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Persona[] personas = generarPersonas(n);
        ArrayList<Persona> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(personas[i]);
        }
        return grupo;
    }

    public static ArrayList<Persona> generarListaPersonas2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Persona[] personas = generarPersonas2(n);
        ArrayList<Persona> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(personas[i]);
        }
        return grupo;
    }

    public static ArrayList<Persona> generarListaPersonas3(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Persona[] personas = generarPersonas3(n);
        ArrayList<Persona> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(personas[i]);
        }
        return grupo;
    }

    public static ArrayList<Persona> generarListaPersonas4(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        Persona[] personas = generarPersonas4(n);
        ArrayList<Persona> grupo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            grupo.add(personas[i]);
        }
        return grupo;
    }

    public static void generarArchivUsuarios(int n, String rutaArchivo) {
        if (n < 1) {
            throw new IllegalArgumentException("El argumento debe ser mayor que cero.");
        }

        String[] celular = generarNumeroCelular(n);
        int[] ids = new int[n];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = ids.length - 1 - i;
        }
//        int m = 2 * n / 3;
        for (int i = 0; i < n; i++) {
            int a = entero(n);
            int b = entero(n);
            int tmp = ids[a];
            ids[a] = ids[b];
            ids[b] = tmp;
        }
        TreeSet<String> claves = new TreeSet<>();
        StringBuilder cadena = new StringBuilder();

        for (int i = 0; i < n; i++) {
            cadena.append(ids[i]);
            cadena.append(",");

            String apellido = generarApellido();
            String apellido2 = generarApellido();
//            String cellphone = generarNumeroCelular();

            int genero = generarGenero();
            String nombre = generarNombre(genero);

            cadena.append(nombre);
            cadena.append(",");
            cadena.append(apellido);
            cadena.append(",");
            cadena.append(apellido2);
            cadena.append(",");

            int hoy = Fecha.hoy().anio;
            int anioInicial = hoy - 60;
            int anioFinal = hoy - 15;

            if (anioInicial < 1900) {
                anioInicial = 1980;
                anioFinal = 2005;
            }
            if (anioFinal < 1900) {
                anioFinal = 2005;
            }
            Fecha fechaNac = new Fecha(generarFecha(anioInicial, anioFinal));

            String usuario = formarUsuario(nombre, apellido);

            int anioNacimiento = fechaNac.anio;
            int intento = 0;
            String tempUsuario = usuario;
            while (!claves.add(tempUsuario)) {
                if (intento == 0) {
                    String segAp = Util.eliminarTilde(apellido2).toLowerCase();
                    usuario += segAp;
                    tempUsuario = usuario;
                    intento++;
                } else {
                    tempUsuario = usuario + anioNacimiento;
                    anioNacimiento++;
                }
            }
            usuario = tempUsuario;

            int correo = entero(3);
            String proveedorCorreo = "";
            if (correo == 0) {
                proveedorCorreo = "gmail.com";
            } else if (correo == 1) {
                proveedorCorreo = "hotmail.com";
            } else {
                proveedorCorreo = "outlook.es";
            }
            String email = usuario + "@" + proveedorCorreo;

            cadena.append(usuario);
            cadena.append(",");
            cadena.append(genero);
            cadena.append(",");
            cadena.append(email);
            cadena.append(",");
            cadena.append(celular[i]);
            cadena.append(",");
            cadena.append(fechaNac.dia);
            cadena.append(",");
            cadena.append(fechaNac.mes);
            cadena.append(",");
            cadena.append(fechaNac.anio);
            cadena.append("\n");
        }
        Archivo.crearArchivo(cadena.toString(), rutaArchivo);
    }

    /**
     * Genera un valor de NUIP aleatorio empezando en mil millones. NUIP: Número
     * único de identificación personal. Utiliza el punto para separar los
     * números.
     *
     * @return Cadena aleatoria que representa un NUIP
     */
    public static String generarNUIP() {
        return "1." + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + "."
                + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + "." + R.nextInt(10) + R.nextInt(10)
                + R.nextInt(10);
    }

    /**
     * Genera un valor de NUIP aleatorio empezando en mil millones. NUIP: Número
     * único de identificación personal. No utiliza el punto para separar los
     * números.
     *
     * @return Cadena aleatoria que representa un NUIP
     */
    public static String generarNUIP2() {
        return "1" + R.nextInt(10) + R.nextInt(10) + R.nextInt(10)
                + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10)
                + R.nextInt(10);
    }

    /**
     * Genera un vector de tamaño n con NUIPs diferentes. En el vector no hay
     * NUIPs repetidos. Utiliza el punto para separar los números.
     *
     * @param n Indica el tamaño del vector y debe ser menor que mil millones.
     * @return Un vector con NUIPs diferentes.
     * @throws IllegalArgumentException Si n es mayor o igual a mil millones.
     */
    public static String[] generarNUIPs(int n) throws IllegalArgumentException {
        if (n >= 1000000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que mil millones");
        }
        String[] nuips = new String[n];
        TreeSet<String> claves = new TreeSet<>();
        int i = 0;

        while (i < n) {
            String nuip = generarNUIP();

            if (claves.add(nuip)) {
                nuips[i] = nuip;
                i++;
            }
        }
        return nuips;
    }

    /**
     * Genera un vector de tamaño n con NUIPs diferentes. En el vector no hay
     * NUIPs repetidos. No utiliza el punto para separar los números.
     *
     * @param n Indica el tamaño del vector y debe ser menor que mil millones
     * @return Un vector con NUIPs diferentes
     * @throws IllegalArgumentException
     */
    public static String[] generarNUIPs2(int n) throws IllegalArgumentException {
        if (n >= 1000000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que mil millones");
        }

        TreeSet<String> claves = new TreeSet<>();
        String[] nuips = new String[n];
        int i = 0;

        while (i < n) {
            String nuip = generarNUIP2();

            if (claves.add(nuip)) {
                nuips[i] = nuip;
                i++;
            }
        }

        return nuips;
    }

    /**
     * Genera un número de cedula a partir de 40 millones.
     *
     * @return Número de cédula a partir de 40 millones
     */
    public static String generarCedulaMujer() {
        return "4" + R.nextInt(10) + "." + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + "."
                + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);
    }

    /**
     * Genera un vector de tamaño n con números de cedula de mujeres diferentes.
     * En el vector no hay números de cédulas repetidos.
     *
     * @param n Indica el tamaño del vector y debe ser menor que 10 millones
     * @return Un vector con cédulas diferentes para mujeres
     * @throws IllegalArgumentException
     */
    public static String[] generarCedulasMujer(int n) throws IllegalArgumentException {
        if (n >= 10000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que 10 millones");
        }
        TreeSet<String> claves = new TreeSet<>();
        String[] cedulas = new String[n];
        int i = 0;

        while (i < n) {
            String cedula = generarCedulaMujer();
            if (claves.add(cedula)) {
                cedulas[i] = cedula;
                i++;
            }
        }

        return cedulas;
    }

    /**
     * Genera un número de cedula a partir de 70 millones.
     *
     * @return Número de cédula a partir de 70 millones
     */
    public static String generarCedulaHombre() {
        return "7" + R.nextInt(10) + "." + R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + "."
                + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);
    }

    /**
     * Genera un vector de tamaño n con números de cedula de hombres diferentes.
     * En el vector no hay números de cédulas repetidos.
     *
     * @param n Indica el tamaño del vector y debe ser menor que 10 millones
     * @return Un vector con cédulas diferentes para hombres
     * @throws IllegalArgumentException
     */
    public static String[] generarCedulasHombre(int n) throws IllegalArgumentException {
        if (n >= 10000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que 10 millones");
        }
        TreeSet<String> claves = new TreeSet<>();
        String[] cedulas = new String[n];
        int i = 0;

        while (i < n) {
            String cedula = generarCedulaHombre();
            if (claves.add(cedula)) {
                cedulas[i] = cedula;
                i++;
            }
        }

        return cedulas;
    }

    /**
     *
     * @param tipo Si tipo es cero genera un número de cédula para mujer, si
     * tipo es uno genera un número de cédula para hombre y cualquier otro valor
     * para tipo genera un número NUIP.
     * @return
     */
    public static String generarNUIP(int tipo) {
        String nuip;
        switch (tipo) {
            case 0:
                nuip = generarCedulaMujer();
                break;
            case 1:
                nuip = generarCedulaHombre();
                break;
            default:
                nuip = generarNUIP();
        }
        return nuip;
    }

    /**
     * Genera un valor entero aleatorio en el intervalo [1, 2]. 1 indica
     * FEMENINO y 2 indica MASCULINO.
     *
     * @return Retorna 1 para indicar FEMENINO o retorna 2 para indicar
     * MASCULINO.
     */
    public static int generarGenero() {
        return (R.nextInt(2) == Persona.FEMENINO ? Persona.FEMENINO : Persona.MASCULINO);
    }

    /**
     * Genera un vector de tamaño n con valores enteros aleatorios en el
     * intervalo [1, 2]. 1 indica FEMENINO y 2 indica MASCULINO.
     *
     * @param n Entero positivo que indica el tamaño del vector.
     * @return Retorna un vector con varios valores. El valor 1 para indicar
     * FEMENINO o y el valor 2 para indicar MASCULINO.
     */
    public static int[] generarGenero(int n) {
        int[] genero = new int[n];

        for (int i = 0; i < n; i++) {
            genero[i] = 1 + R.nextInt(2);
        }

        return genero;
    }

    /**
     * Genera una cadena que representa uno de los ocho tipos sanguíneos
     * posibles (A+, A-, B+, B-, AB+, AB-, O+, O-).
     *
     * @return Retorna una cadena con un tipo de sangre.
     */
    public static String generarTipoSanguineo() {
        String[] tipoSangre = {"A+", "AB-", "O-", "A-", "B+", "B-", "AB+", "O+"};
        return tipoSangre[R.nextInt(tipoSangre.length)];
    }

    /**
     * Genera un número aleatorio de teléfono celular (móvil).
     *
     * @return Retorna una cadena que representa un número de teléfono celular
     * (móvil).
     */
    public static String generarNumeroCelular() {
        String celular = "3" + R.nextInt(4) + R.nextInt(10) + "";
        celular += "" + (1 + R.nextInt(9)) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);
        celular += "" + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);

        return celular;
    }

    /**
     * Genera un número aleatorio de teléfono celular (móvil).
     *
     * @return Retorna una cadena que representa un número de teléfono celular
     * (móvil).
     */
    public static String generarNumeroMovil() {
        String celular = "3" + R.nextInt(4) + R.nextInt(10) + "-";
        celular += "" + (1 + R.nextInt(9)) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);
        celular += "" + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);

        return celular;
    }

    public static String[] generarNumeroCelular(int n) throws IllegalArgumentException {
        if (n >= 10000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que 10 millones");
        }
        TreeSet<String> claves = new TreeSet<>();
        String[] celulares = new String[n];
        int i = 0;

        while (i < n) {
            String celular = generarNumeroCelular();
            if (claves.add(celular)) {
                celulares[i] = celular;
                i++;
            }
        }

        return celulares;
    }

    public static String[] generarNumeroMovil(int n) throws IllegalArgumentException {
        if (n >= 10000000) {
            throw new IllegalArgumentException("Argumento no válido: Debe ser menor que 10 millones");
        }
        TreeSet<String> claves = new TreeSet<>();
        String[] celulares = new String[n];
        int i = 0;

        while (i < n) {
            String celular = generarNumeroMovil();
            if (claves.add(celular)) {
                celulares[i] = celular;
                i++;
            }
        }

        return celulares;
    }

    /**
     * Genera un número aleatorio de teléfono fijo.
     *
     * @return Retorna una cadena que representa un número de teléfono fijo
     *
     */
    public static String generarNumeroTelefonoFijo() {
        String fijo = "";
        fijo += (2 + R.nextInt(5)) + (2 + R.nextInt(8)) + R.nextInt(10);
        fijo += R.nextInt(10) + R.nextInt(10) + R.nextInt(10) + R.nextInt(10);

        return fijo;
    }

    /**
     * Genera la dirección de un inmueble.
     *
     * @return Cadena que representa la dirección de un inmueble.
     */
    public static String generarDireccion() {
        String direccion = (R.nextInt(2) == 0) ? "Calle " : "Carrera ";

        direccion += R.nextInt(200);

        if (Math.random() < 0.25) {
            direccion += generarCadena("ABCDEFG", 1);
        }

        direccion += " #" + R.nextInt(200);

        if (Math.random() < 0.15) {
            direccion += generarCadena("ABCDEFG", 1);
        }
        direccion += "-" + R.nextInt(200);

        return direccion;
    }

    public static String[] generarDirecciones(int n) {
        String[] direcciones = new String[n];
        TreeSet<String> claves = new TreeSet<>();
        int i = 0;

        while (i < n) {
            String direccion = generarDireccion();
            if (claves.add(direccion)) {
                direcciones[i] = direccion;
                i++;
            }
        }
        return direcciones;
    }

    private static void cargarNombresHombres() {
        hombres[0] = "Adolfo";
        hombres[1] = "Adrián";
        hombres[2] = "Agustín";
        hombres[3] = "Albeiro";
        hombres[4] = "Alberto";
        hombres[5] = "Aldo";
        hombres[6] = "Alejandro";
        hombres[7] = "Alex";
        hombres[8] = "Alexander";
        hombres[9] = "Alexis";
        hombres[10] = "Alfonso";
        hombres[11] = "Alfredo";
        hombres[12] = "Anderson";
        hombres[13] = "Andrés";
        hombres[14] = "Andrés Felipe";
        hombres[15] = "Angerson";
        hombres[16] = "Antonio";
        hombres[17] = "Arley";
        hombres[18] = "Armando";
        hombres[19] = "Arturo";
        hombres[20] = "Augusto";
        hombres[21] = "Axel";
        hombres[22] = "Benjamín";
        hombres[23] = "Braian";
        hombres[24] = "Brayan";
        hombres[25] = "Brian";
        hombres[26] = "Bruno";
        hombres[27] = "Camilo";
        hombres[28] = "Carlos";
        hombres[29] = "Carlos Alberto";
        hombres[30] = "Carlos Andrés";
        hombres[31] = "Carlos Arturo";
        hombres[32] = "Carlos Augusto";
        hombres[33] = "Carlos Mario";
        hombres[34] = "Cristian";
        hombres[35] = "Cristofer";
        hombres[36] = "Cristóbal";
        hombres[37] = "César";
        hombres[38] = "César Augusto";
        hombres[39] = "Daniel";
        hombres[40] = "Danilo";
        hombres[41] = "Danny";
        hombres[42] = "Dany";
        hombres[43] = "Darío";
        hombres[44] = "David";
        hombres[45] = "Dayro";
        hombres[46] = "Diego";
        hombres[47] = "Duvan";
        hombres[48] = "Dylan";
        hombres[49] = "Edgar";
        hombres[50] = "Edison";
        hombres[51] = "Eduardo";
        hombres[52] = "Edward";
        hombres[53] = "Edwin";
        hombres[54] = "Efraín";
        hombres[55] = "Elí";
        hombres[56] = "Elías";
        hombres[57] = "Emiliano";
        hombres[58] = "Emilio";
        hombres[59] = "Emmanuel";
        hombres[60] = "Enrique";
        hombres[61] = "Ernesto";
        hombres[62] = "Esteban";
        hombres[63] = "Estiven";
        hombres[64] = "Fabio";
        hombres[65] = "Fabián";
        hombres[66] = "Federico";
        hombres[67] = "Felipe";
        hombres[68] = "Fernando";
        hombres[69] = "Francisco";
        hombres[70] = "Francisco Javier";
        hombres[71] = "Francisco José";
        hombres[72] = "Gabriel";
        hombres[73] = "Germán";
        hombres[74] = "Gerónimo";
        hombres[75] = "Gilmer";
        hombres[76] = "Giovanni";
        hombres[77] = "Gonzalo";
        hombres[78] = "Gregorio";
        hombres[79] = "Guillermo";
        hombres[80] = "Gustavo";
        hombres[81] = "Henry";
        hombres[82] = "Hernando";
        hombres[83] = "Hernán";
        hombres[84] = "Horacio";
        hombres[85] = "Hugo";
        hombres[86] = "Humberto";
        hombres[87] = "Héctor";
        hombres[88] = "Ignacio";
        hombres[89] = "Isaac";
        hombres[90] = "Iván";
        hombres[91] = "Jacobo";
        hombres[92] = "Jaime";
        hombres[93] = "Jairo";
        hombres[94] = "James";
        hombres[95] = "Jason";
        hombres[96] = "Javier";
        hombres[97] = "Jerónimo";
        hombres[98] = "Jesús";
        hombres[99] = "Joan";
        hombres[100] = "Joaquín";
        hombres[101] = "John";
        hombres[102] = "Johnny";
        hombres[103] = "Jonathan";
        hombres[104] = "Jordi";
        hombres[105] = "Jorge";
        hombres[106] = "Jose María";
        hombres[107] = "Joseph";
        hombres[108] = "Josué";
        hombres[109] = "José";
        hombres[110] = "José Angel";
        hombres[111] = "José Antonio";
        hombres[112] = "José Francisco";
        hombres[113] = "José Ignacio";
        hombres[114] = "José Luis";
        hombres[115] = "José Manuel";
        hombres[116] = "José Miguel";
        hombres[117] = "Juan";
        hombres[118] = "Juan Antonio";
        hombres[119] = "Juan Carlos";
        hombres[120] = "Juan David";
        hombres[121] = "Juan Diego";
        hombres[122] = "Juan Esteban";
        hombres[123] = "Juan Fernando";
        hombres[124] = "Juan Francisco";
        hombres[125] = "Juan José";
        hombres[126] = "Juan Luis";
        hombres[127] = "Juan Manuel";
        hombres[128] = "Juan Pablo";
        hombres[129] = "Juan Sebastián";
        hombres[130] = "Julio";
        hombres[131] = "Julio César";
        hombres[132] = "Julián";
        hombres[133] = "Julián Esteban";
        hombres[134] = "Kevin";
        hombres[135] = "Leandro";
        hombres[136] = "Leo";
        hombres[137] = "Leonardo";
        hombres[138] = "Leonel";
        hombres[139] = "León";
        hombres[140] = "Lionel";
        hombres[141] = "Lorenzo";
        hombres[142] = "Lucas";
        hombres[143] = "Luis";
        hombres[144] = "Luis Alberto";
        hombres[145] = "Luis Armando";
        hombres[146] = "Luis Carlos";
        hombres[147] = "Luis David";
        hombres[148] = "Luis Fernando";
        hombres[149] = "Luis Javier";
        hombres[150] = "Luis Miguel";
        hombres[151] = "Manuel";
        hombres[152] = "Marc";
        hombres[153] = "Marcos";
        hombres[154] = "Mariano";
        hombres[155] = "Mario";
        hombres[156] = "Martín";
        hombres[157] = "Mateo";
        hombres[158] = "Mathias";
        hombres[159] = "Matías";
        hombres[160] = "Mauricio";
        hombres[161] = "Maximiliano";
        hombres[162] = "Miguel";
        hombres[163] = "Miguel Angel";
        hombres[164] = "Nelson";
        hombres[165] = "Nestor";
        hombres[166] = "Nicolas";
        hombres[167] = "Nicolás";
        hombres[168] = "Nilson";
        hombres[169] = "Octavio";
        hombres[170] = "Omar";
        hombres[171] = "Orlando";
        hombres[172] = "Oscar";
        hombres[173] = "Osvaldo";
        hombres[174] = "Pablo";
        hombres[175] = "Pascual";
        hombres[176] = "Pedro";
        hombres[177] = "Rafael";
        hombres[178] = "Ramiro";
        hombres[179] = "Ramón";
        hombres[180] = "Raúl";
        hombres[181] = "Ricardo";
        hombres[182] = "Roberto";
        hombres[183] = "Robinson";
        hombres[184] = "Rodrigo";
        hombres[185] = "Román";
        hombres[186] = "Rubén";
        hombres[187] = "Salim";
        hombres[188] = "Samuel";
        hombres[189] = "Samuel David";
        hombres[190] = "Santiago";
        hombres[191] = "Saúl";
        hombres[192] = "Sebastian";
        hombres[193] = "Sebastián";
        hombres[194] = "Sergio";
        hombres[195] = "Simón";
        hombres[196] = "Thiago";
        hombres[197] = "Thomas";
        hombres[198] = "Tobías";
        hombres[199] = "Tomás";
        hombres[200] = "Uriel";
        hombres[201] = "Vicente";
        hombres[202] = "Victor";
        hombres[203] = "Victor Manuel";
        hombres[204] = "Vladimir";
        hombres[205] = "Walter";
        hombres[206] = "William";
        hombres[207] = "Wilmar";
        hombres[208] = "Wilmer";
        hombres[209] = "Wilson";
        hombres[210] = "Yordan";
        hombres[211] = "Yorman";
        hombres[212] = "Alvaro";
        hombres[213] = "Angel";
        hombres[214] = "Angel David";
        hombres[215] = "Anibal";

        hombres[216] = "Milton";
        hombres[217] = "Jhon";
        hombres[218] = "José Alejando";
        hombres[219] = "José Fernando";
        hombres[220] = "Jeferson";
        hombres[221] = "Steven";
        hombres[222] = "Juan Felipe";
        hombres[223] = "José Daniel";
        hombres[224] = "Emanuel";
        hombres[225] = "Yeison";
        hombres[226] = "Eduar";
        hombres[227] = "Geffry";
        hombres[228] = "Jean Paul";
    }

    private static void cargarNombresMujeres() {
        mujeres[0] = "Adriana";
        mujeres[1] = "Alba";
        mujeres[2] = "Alejandra";
        mujeres[3] = "Alexandra";
        mujeres[4] = "Alicia";
        mujeres[5] = "Amalia";
        mujeres[6] = "Amanda";
        mujeres[7] = "Amelia";
        mujeres[8] = "Amparo";
        mujeres[9] = "Ana";
        mujeres[10] = "Ana Belén";
        mujeres[11] = "Ana Isabel";
        mujeres[12] = "Ana Lucía";
        mujeres[13] = "Ana Luz";
        mujeres[14] = "Ana María";
        mujeres[15] = "Ana Victoria";
        mujeres[16] = "Anabel";
        mujeres[17] = "Analuz";
        mujeres[18] = "Andrea";
        mujeres[19] = "Angela";
        mujeres[20] = "Angélica";
        mujeres[21] = "Angelina";
        mujeres[22] = "Angie";
        mujeres[23] = "Anna";
        mujeres[24] = "Annie";
        mujeres[25] = "Antonella";
        mujeres[26] = "Antonia";
        mujeres[27] = "Arya";
        mujeres[28] = "Astrid";
        mujeres[29] = "Auri";
        mujeres[30] = "Aurora";
        mujeres[31] = "Aída";
        mujeres[32] = "Beatriz";
        mujeres[33] = "Belén";
        mujeres[34] = "Bianca";
        mujeres[35] = "Bibiana";
        mujeres[36] = "Blanca";
        mujeres[37] = "Brenda";
        mujeres[38] = "Camila";
        mujeres[39] = "Carla";
        mujeres[40] = "Carmen";
        mujeres[41] = "Carol";
        mujeres[42] = "Carolina";
        mujeres[43] = "Catalina";
        mujeres[44] = "Cecilia";
        mujeres[45] = "Celeste";
        mujeres[46] = "Cielo";
        mujeres[47] = "Clara";
        mujeres[48] = "Claudia";
        mujeres[49] = "Constanza";
        mujeres[50] = "Consuelo";
        mujeres[51] = "Coral";
        mujeres[52] = "Coraline";
        mujeres[53] = "Cristina";
        mujeres[54] = "Cynthia";
        mujeres[55] = "Daniela";
        mujeres[56] = "Danna";
        mujeres[57] = "Danna Sofía";
        mujeres[58] = "Dany";
        mujeres[59] = "Denna";
        mujeres[60] = "Devi";
        mujeres[61] = "Diana";
        mujeres[62] = "Diana Patricia";
        mujeres[63] = "Dora";
        mujeres[64] = "Dora Isabel";
        mujeres[65] = "Dora Isabel";
        mujeres[66] = "Doris";
        mujeres[67] = "Dulce";
        mujeres[68] = "Dulce María";
        mujeres[69] = "Dulcemaría";
        mujeres[70] = "Elena";
        mujeres[71] = "Eleonor";
        mujeres[72] = "Eliana";
        mujeres[73] = "Elisa";
        mujeres[74] = "Elsa";
        mujeres[75] = "Ema";
        mujeres[76] = "Emilia";
        mujeres[77] = "Emily";
        mujeres[78] = "Emily Sofía";
        mujeres[79] = "Emma";
        mujeres[80] = "Erica";
        mujeres[81] = "Erika";
        mujeres[82] = "Esmeralda";
        mujeres[83] = "Esperanza";
        mujeres[84] = "Estefanía";
        mujeres[85] = "Esther";
        mujeres[86] = "Estrella";
        mujeres[87] = "Eumelia";
        mujeres[88] = "Eva";
        mujeres[89] = "Eva María";
        mujeres[90] = "Fanny";
        mujeres[91] = "Fernanda";
        mujeres[92] = "Fiona";
        mujeres[93] = "Flor";
        mujeres[94] = "Frida";
        mujeres[95] = "Gabriela";
        mujeres[96] = "Geraldine";
        mujeres[97] = "Gina";
        mujeres[98] = "Gloria";
        mujeres[99] = "Greta";
        mujeres[100] = "Gretel";
        mujeres[101] = "Guadalupe";
        mujeres[102] = "Heidi";
        mujeres[103] = "Helena";
        mujeres[104] = "Ingrid";
        mujeres[105] = "Inés";
        mujeres[106] = "Irene";
        mujeres[107] = "Iris";
        mujeres[108] = "Irma";
        mujeres[109] = "Isabel";
        mujeres[110] = "Isabel Cristina";
        mujeres[111] = "Isabela";
        mujeres[112] = "Isabella";
        mujeres[113] = "Ivonne";
        mujeres[114] = "Jacqueline";
        mujeres[115] = "Janet";
        mujeres[116] = "Janeth";
        mujeres[117] = "Jazmín";
        mujeres[118] = "Jennifer";
        mujeres[119] = "Jenny";
        mujeres[120] = "Jessica";
        mujeres[121] = "Jimena";
        mujeres[122] = "Joana";
        mujeres[123] = "Johana";
        mujeres[124] = "Juana";
        mujeres[125] = "Juanita";
        mujeres[126] = "Judith";
        mujeres[127] = "Julia";
        mujeres[128] = "Juliana";
        mujeres[129] = "Julieta";
        mujeres[130] = "Jésica";
        mujeres[131] = "Karen";
        mujeres[132] = "Karina";
        mujeres[133] = "Karla";
        mujeres[134] = "Katherine";
        mujeres[135] = "Kelly";
        mujeres[136] = "Lara";
        mujeres[137] = "Laura";
        mujeres[138] = "Laura Valentina";
        mujeres[139] = "Leidy";
        mujeres[140] = "Lidia";
        mujeres[141] = "Ligia";
        mujeres[142] = "Liliana";
        mujeres[143] = "Lina";
        mujeres[144] = "Lina Marcela";
        mujeres[145] = "Linda";
        mujeres[146] = "Lisa";
        mujeres[147] = "Lisseth";
        mujeres[148] = "Liz";
        mujeres[149] = "Lorena";
        mujeres[150] = "Lucero";
        mujeres[151] = "Luciana";
        mujeres[152] = "Lucía";
        mujeres[153] = "Luisa";
        mujeres[154] = "Luisa Fernanda";
        mujeres[155] = "Luna";
        mujeres[156] = "Luz";
        mujeres[157] = "Luz Elena";
        mujeres[158] = "Luz Edilma";
        mujeres[159] = "Luz Marina";
        mujeres[160] = "Mabel";
        mujeres[161] = "Maia";
        mujeres[162] = "Maite";
        mujeres[163] = "Manuela";
        mujeres[164] = "Mar";
        mujeres[165] = "Mara";
        mujeres[166] = "Marcela";
        mujeres[167] = "Margarita";
        mujeres[168] = "Maria";
        mujeres[169] = "Maria";
        mujeres[170] = "Maria Antonia";
        mujeres[171] = "Maria Camila";
        mujeres[172] = "Maria Clara";
        mujeres[173] = "Maria Cristina";
        mujeres[174] = "Maria Elena";
        mujeres[175] = "Maria Isabel";
        mujeres[176] = "Maria José";
        mujeres[177] = "Maria Luisa";
        mujeres[178] = "Maria Mar";
        mujeres[179] = "Maria Paulina";
        mujeres[180] = "Maria Pilar";
        mujeres[181] = "Maria Teresa";
        mujeres[182] = "Maria Victoria";
        mujeres[183] = "Mariana";
        mujeres[184] = "Maribel";
        mujeres[185] = "Marina";
        mujeres[186] = "Marisol";
        mujeres[187] = "Mariángel";
        mujeres[188] = "Marta";
        mujeres[189] = "Martha";
        mujeres[190] = "Mary";
        mujeres[191] = "Maryam";
        mujeres[192] = "María";
        mujeres[193] = "María Celeste";
        mujeres[194] = "María Fernanda";
        mujeres[195] = "María Paula";
        mujeres[196] = "Maya";
        mujeres[197] = "Mayra";
        mujeres[198] = "Melanie";
        mujeres[199] = "Melisa";
        mujeres[200] = "Melissa";
        mujeres[201] = "Mercedes";
        mujeres[202] = "Mery";
        mujeres[203] = "Michelle";
        mujeres[204] = "Milena";
        mujeres[205] = "Miriam";
        mujeres[206] = "Mónica";
        mujeres[207] = "Nadia";
        mujeres[208] = "Nancy";
        mujeres[209] = "Naomi";
        mujeres[210] = "Natalia";
        mujeres[211] = "Natalí";
        mujeres[212] = "Natasha";
        mujeres[213] = "Nelly";
        mujeres[214] = "Nicole";
        mujeres[215] = "Nicolle";
        mujeres[216] = "Nidia";
        mujeres[217] = "Noelia";
        mujeres[218] = "Noemí";
        mujeres[219] = "Nohelia";
        mujeres[220] = "Nora";
        mujeres[221] = "Norma";
        mujeres[222] = "Ofelia";
        mujeres[223] = "Olga";
        mujeres[224] = "Olivia";
        mujeres[225] = "Omaira";
        mujeres[226] = "Paloma";
        mujeres[227] = "Pamela";
        mujeres[228] = "Paola";
        mujeres[229] = "Patricia";
        mujeres[230] = "Paula";
        mujeres[231] = "Paula Andrea";
        mujeres[232] = "Paulina";
        mujeres[233] = "Pilar";
        mujeres[234] = "Raquel";
        mujeres[235] = "Rocío";
        mujeres[236] = "Rosa";
        mujeres[237] = "Rosa María";
        mujeres[238] = "Rosalba";
        mujeres[239] = "Rosario";
        mujeres[240] = "Salomé";
        mujeres[241] = "Samantha";
        mujeres[242] = "Sandra";
        mujeres[243] = "Sandra Milena";
        mujeres[244] = "Sansa";
        mujeres[245] = "Sara";
        mujeres[246] = "Sara Sofía";
        mujeres[247] = "Sara Valentina";
        mujeres[248] = "Saray";
        mujeres[249] = "Silvia";
        mujeres[250] = "Siomara";
        mujeres[251] = "Sofía";
        mujeres[252] = "Sol";
        mujeres[253] = "Sonia";
        mujeres[254] = "Soraia";
        mujeres[255] = "Soraida";
        mujeres[256] = "Soraya";
        mujeres[257] = "Sorayda";
        mujeres[258] = "Steffanía";
        mujeres[259] = "Stephanie";
        mujeres[260] = "Susana";
        mujeres[261] = "Tania";
        mujeres[262] = "Tatiana";
        mujeres[263] = "Teresa";
        mujeres[264] = "Valentina";
        mujeres[265] = "Valeria";
        mujeres[266] = "Vanessa";
        mujeres[267] = "Vannesa";
        mujeres[268] = "Verónica";
        mujeres[269] = "Victoria";
        mujeres[270] = "Violeta";
        mujeres[271] = "Virginia";
        mujeres[272] = "Vivian";
        mujeres[273] = "Viviana";
        mujeres[274] = "Ximena";
        mujeres[275] = "Xiomara";
        mujeres[276] = "Yolanda";

        mujeres[277] = "Maria Ema";
        mujeres[278] = "Maria Alejandra";
        mujeres[279] = "Laura Sofía";
        mujeres[280] = "Maria Valentina";
        mujeres[281] = "Luz Helena";
        mujeres[282] = "Dara";
        mujeres[283] = "Dayana";
        mujeres[284] = "Dana";
        mujeres[285] = "Daisy";
        mujeres[286] = "Sindy";
        mujeres[287] = "Libia";
        mujeres[288] = "Daniella";
        mujeres[289] = "Maria del Pilar";

        mujeres[290] = "Maria Belén";
        mujeres[291] = "Lida";
        mujeres[292] = "Yamile";
        mujeres[293] = "Lizeth";
        mujeres[294] = "Maritza";
        mujeres[295] = "Paola Andrea";
        mujeres[296] = "Ana Catalina";
        mujeres[297] = "Ana Carolina";
        mujeres[298] = "Ana Milena";
        // ana 
    }

    private static void cargarApellidos() {
        apellidos[0] = "Abad";
        apellidos[1] = "Abellaneda";
        apellidos[2] = "Abello";
        apellidos[3] = "Acevedo";
        apellidos[4] = "Acosta";
        apellidos[5] = "Acuña";
        apellidos[6] = "Agudelo";
        apellidos[7] = "Aguilar";
        apellidos[8] = "Aguirre";
        apellidos[9] = "Alarcón";
        apellidos[10] = "Alcazar";
        apellidos[11] = "Alfaro";
        apellidos[12] = "Allende";
        apellidos[13] = "Alvarado";
        apellidos[14] = "Alvira";
        apellidos[15] = "Alzate";
        apellidos[16] = "Amador";
        apellidos[17] = "Amados";
        apellidos[18] = "Amaya";
        apellidos[19] = "Anaya";
        apellidos[20] = "Andrade";
        apellidos[21] = "Angarita";
        apellidos[22] = "Angulo";
        apellidos[23] = "Aparicio";
        apellidos[24] = "Aragón";
        apellidos[25] = "Aramburo";
        apellidos[26] = "Aramburu";
        apellidos[27] = "Arango";
        apellidos[28] = "Araujo";
        apellidos[29] = "Arbelaez";
        apellidos[30] = "Arboleda";
        apellidos[31] = "Arce";
        apellidos[32] = "Arcila";
        apellidos[33] = "Arciniegas";
        apellidos[34] = "Ardila";
        apellidos[35] = "Areiza";
        apellidos[36] = "Arenas";
        apellidos[37] = "Arevalo";
        apellidos[38] = "Arias";
        apellidos[39] = "Aristizábal";
        apellidos[40] = "Ariza";
        apellidos[41] = "Arredondo";
        apellidos[42] = "Arroyave";
        apellidos[43] = "Arrubla";
        apellidos[44] = "Arsuaga";
        apellidos[45] = "Avalo";
        apellidos[46] = "Avellaneda";
        apellidos[47] = "Avendaño";
        apellidos[48] = "Ayala";
        apellidos[49] = "Baena";
        apellidos[50] = "Balbuena";
        apellidos[51] = "Ballestero";
        apellidos[52] = "Ballesteros";
        apellidos[53] = "Barco";
        apellidos[54] = "Barragán";
        apellidos[55] = "Barrera";
        apellidos[56] = "Barreros";
        apellidos[57] = "Barrientos";
        apellidos[58] = "Barrio";
        apellidos[59] = "Barrios";
        apellidos[60] = "Barón";
        apellidos[61] = "Bastida";
        apellidos[62] = "Bastidas";
        apellidos[63] = "Becerra";
        apellidos[64] = "Bedoya";
        apellidos[65] = "Bejarano";
        apellidos[66] = "Belalcazar";
        apellidos[67] = "Beltran";
        apellidos[68] = "Benavides";
        apellidos[69] = "Benítez";
        apellidos[70] = "Bermúdez";
        apellidos[71] = "Bernal";
        apellidos[72] = "Berrío";
        apellidos[73] = "Betancourt";
        apellidos[74] = "Betancur";
        apellidos[75] = "Blanco";
        apellidos[76] = "Blandón";
        apellidos[77] = "Bohórquez";
        apellidos[78] = "Bolaños";
        apellidos[79] = "Bolivar";
        apellidos[80] = "Bonilla";
        apellidos[81] = "Borges";
        apellidos[82] = "Borja";
        apellidos[83] = "Borrero";
        apellidos[84] = "Botero";
        apellidos[85] = "Bravo";
        apellidos[86] = "Buendía";
        apellidos[87] = "Bueno";
        apellidos[88] = "Buitrago";
        apellidos[89] = "Burgos";
        apellidos[90] = "Bustamante";
        apellidos[91] = "Bustos";
        apellidos[92] = "Caballero";
        apellidos[93] = "Cabañas";
        apellidos[94] = "Cabrera";
        apellidos[95] = "Cadavid";
        apellidos[96] = "Cadena";
        apellidos[97] = "Caicedo";
        apellidos[98] = "Calderón";
        apellidos[99] = "Calero";
        apellidos[100] = "Calle";
        apellidos[101] = "Callejas";
        apellidos[102] = "Camacho";
        apellidos[103] = "Camargo";
        apellidos[104] = "Campillo";
        apellidos[105] = "Campo";
        apellidos[106] = "Campos";
        apellidos[107] = "Campuzano";
        apellidos[108] = "Cano";
        apellidos[109] = "Cardona";
        apellidos[110] = "Cardozo";
        apellidos[111] = "Carmona";
        apellidos[112] = "Caro";
        apellidos[113] = "Carrasco";
        apellidos[114] = "Carrasquilla";
        apellidos[115] = "Carreño";
        apellidos[116] = "Carrillo";
        apellidos[117] = "Carrizo";
        apellidos[118] = "Carvajal";
        apellidos[119] = "Castañeda";
        apellidos[120] = "Castaño";
        apellidos[121] = "Castellanos";
        apellidos[122] = "Castillo";
        apellidos[123] = "Castro";
        apellidos[124] = "Cañas";
        apellidos[125] = "Ceballos";
        apellidos[126] = "Celis";
        apellidos[127] = "Cepeda";
        apellidos[128] = "Chaparro";
        apellidos[129] = "Chaverra";
        apellidos[130] = "Chávez";
        apellidos[131] = "Cifuentes";
        apellidos[132] = "Colmenares";
        apellidos[133] = "Combita";
        apellidos[134] = "Comesaña";
        apellidos[135] = "Conde";
        apellidos[136] = "Contreras";
        apellidos[137] = "Coronado";
        apellidos[138] = "Coronel";
        apellidos[139] = "Corral";
        apellidos[140] = "Corrales";
        apellidos[141] = "Correa";
        apellidos[142] = "Cortés";
        apellidos[143] = "Corzo";
        apellidos[144] = "Costa";
        apellidos[145] = "Cruz";
        apellidos[146] = "Cuartas";
        apellidos[147] = "Cáceres";
        apellidos[148] = "Cárdenas";
        apellidos[149] = "Céspedes";
        apellidos[150] = "Córdoba";
        apellidos[151] = "Daza";
        apellidos[152] = "Delgado";
        apellidos[153] = "Domínguez";
        apellidos[154] = "Dorado";
        apellidos[155] = "Duarte";
        apellidos[156] = "Dueñas";
        apellidos[157] = "Duque";
        apellidos[158] = "Durango";
        apellidos[159] = "Durán";
        apellidos[160] = "Díaz";
        apellidos[161] = "Echandía";
        apellidos[162] = "Echavarría";
        apellidos[163] = "Echevarría";
        apellidos[164] = "Echeverri";
        apellidos[165] = "Echeverry";
        apellidos[166] = "Elizondo";
        apellidos[167] = "Escalante";
        apellidos[168] = "Escalona";
        apellidos[169] = "Escobar";
        apellidos[170] = "Escudero";
        apellidos[171] = "Espinosa";
        apellidos[172] = "Estrada";
        apellidos[173] = "Fajardo";
        apellidos[174] = "Farfán";
        apellidos[175] = "Fernández";
        apellidos[176] = "Ferreira";
        apellidos[177] = "Figueroa";
        apellidos[178] = "Flores";
        apellidos[179] = "Flórez";
        apellidos[180] = "Fonseca";
        apellidos[181] = "Forero";
        apellidos[182] = "Foronda";
        apellidos[183] = "Franco";
        apellidos[184] = "Fuentes";
        apellidos[185] = "Gaitán";
        apellidos[186] = "Galeano";
        apellidos[187] = "Galindo";
        apellidos[188] = "Gallego";
        apellidos[189] = "Gallo";
        apellidos[190] = "Galvis";
        apellidos[191] = "Galán";
        apellidos[192] = "Gamboa";
        apellidos[193] = "Garcés";
        apellidos[194] = "García";
        apellidos[195] = "Garrido";
        apellidos[196] = "Garzon";
        apellidos[197] = "Gaviria";
        apellidos[198] = "Gil";
        apellidos[199] = "Giraldo";
        apellidos[200] = "Godoy";
        apellidos[201] = "Goez";
        apellidos[202] = "González";
        apellidos[203] = "Gordillo";
        apellidos[204] = "Graciano";
        apellidos[205] = "Granada";
        apellidos[206] = "Guarín";
        apellidos[207] = "Guerra";
        apellidos[208] = "Guerrero";
        apellidos[209] = "Guevara";
        apellidos[210] = "Guisao";
        apellidos[211] = "Gutiérrez";
        apellidos[212] = "Guzmán";
        apellidos[213] = "Gómez";
        apellidos[214] = "Henao";
        apellidos[215] = "Hernández";
        apellidos[216] = "Herrera";
        apellidos[217] = "Hidalgo";
        apellidos[218] = "Hierro";
        apellidos[219] = "Higuita";
        apellidos[220] = "Hincapié";
        apellidos[221] = "Hinestroza";
        apellidos[222] = "Holguín";
        apellidos[223] = "Hoyos";
        apellidos[224] = "Huerta";
        apellidos[225] = "Huertas";
        apellidos[226] = "Hurtado";
        apellidos[227] = "Ibargüen";
        apellidos[228] = "Ibarra";
        apellidos[229] = "Ibañez";
        apellidos[230] = "Idarraga";
        apellidos[231] = "Iglesias";
        apellidos[232] = "Infante";
        apellidos[233] = "Isaza";
        apellidos[234] = "Islas";
        apellidos[235] = "Izaguirre";
        apellidos[236] = "Izquierdo";
        apellidos[237] = "Jaimes";
        apellidos[238] = "Jara";
        apellidos[239] = "Jaramillo";
        apellidos[240] = "Jiménez";
        apellidos[241] = "Joya";
        apellidos[242] = "Jurado";
        apellidos[243] = "Jurao";
        apellidos[244] = "Juárez";
        apellidos[245] = "Lagos";
        apellidos[246] = "Latorre";
        apellidos[247] = "Ledesma";
        apellidos[248] = "Ledezma";
        apellidos[249] = "Leiva";
        apellidos[250] = "Lerma";
        apellidos[251] = "Lescano";
        apellidos[252] = "Linares";
        apellidos[253] = "Linero";
        apellidos[254] = "Lizarralde";
        apellidos[255] = "Lizcano";
        apellidos[256] = "Llano";
        apellidos[257] = "Llanos";
        apellidos[258] = "Lombana";
        apellidos[259] = "Londoño";
        apellidos[260] = "Lopera";
        apellidos[261] = "Losada";
        apellidos[262] = "Lozano";
        apellidos[263] = "Luján";
        apellidos[264] = "Luna";
        apellidos[265] = "Luzardo";
        apellidos[266] = "López";
        apellidos[267] = "Macías";
        apellidos[268] = "Maldonado";
        apellidos[269] = "Manrique";
        apellidos[270] = "Mantilla";
        apellidos[271] = "Marroquín";
        apellidos[272] = "Martínez";
        apellidos[273] = "Marulanda";
        apellidos[274] = "Marín";
        apellidos[275] = "Mateus";
        apellidos[276] = "Matheus";
        apellidos[277] = "Maya";
        apellidos[278] = "Medina";
        apellidos[279] = "Medrano";
        apellidos[280] = "Mejía";
        apellidos[281] = "Melo";
        apellidos[282] = "Mendoza";
        apellidos[283] = "Mesa";
        apellidos[284] = "Miranda";
        apellidos[285] = "Molina";
        apellidos[286] = "Monroy";
        apellidos[287] = "Monsalve";
        apellidos[288] = "Montealegre";
        apellidos[289] = "Montenegro";
        apellidos[290] = "Montero";
        apellidos[291] = "Monterrosa";
        apellidos[292] = "Montes";
        apellidos[293] = "Montoya";
        apellidos[294] = "Mora";
        apellidos[295] = "Morales";
        apellidos[296] = "Moreno";
        apellidos[297] = "Morán";
        apellidos[298] = "Mosquera";
        apellidos[299] = "Motta";
        apellidos[300] = "Muentes";
        apellidos[301] = "Munera";
        apellidos[302] = "Murcia";
        apellidos[303] = "Murillo";
        apellidos[304] = "Muñoz";
        apellidos[305] = "Márquez";
        apellidos[306] = "Méndez";
        apellidos[307] = "Nadal";
        apellidos[308] = "Naranjo";
        apellidos[309] = "Narváez";
        apellidos[310] = "Nava";
        apellidos[311] = "Navarrete";
        apellidos[312] = "Navarro";
        apellidos[313] = "Negrete";
        apellidos[314] = "Neira";
        apellidos[315] = "Nieto";
        apellidos[316] = "Niño";
        apellidos[317] = "Noguera";
        apellidos[318] = "Noriega";
        apellidos[319] = "Novoa";
        apellidos[320] = "Nuñez";
        apellidos[321] = "Obregón";
        apellidos[322] = "Ocampo";
        apellidos[323] = "Ochoa";
        apellidos[324] = "Ojeda";
        apellidos[325] = "Olarte";
        apellidos[326] = "Olivares";
        apellidos[327] = "Olivera";
        apellidos[328] = "Olmedo";
        apellidos[329] = "Olmo";
        apellidos[330] = "Ordoñez";
        apellidos[331] = "Orduz";
        apellidos[332] = "Orozco";
        apellidos[333] = "Orrego";
        apellidos[334] = "Ortega";
        apellidos[335] = "Ortiz";
        apellidos[336] = "Osorio";
        apellidos[337] = "Ospina";
        apellidos[338] = "Ossa";
        apellidos[339] = "Pabón";
        apellidos[340] = "Pacheco";
        apellidos[341] = "Pachón";
        apellidos[342] = "Padilla";
        apellidos[343] = "Palacio";
        apellidos[344] = "Palacios";
        apellidos[345] = "Palomeque";
        apellidos[346] = "Palomino";
        apellidos[347] = "Pamplona";
        apellidos[348] = "Paniagua";
        apellidos[349] = "Pantoja";
        apellidos[350] = "Pardo";
        apellidos[351] = "Paredes";
        apellidos[352] = "Pareja";
        apellidos[353] = "Parra";
        apellidos[354] = "París";
        apellidos[355] = "Patiño";
        apellidos[356] = "Pedraza";
        apellidos[357] = "Peláez";
        apellidos[358] = "Peralta";
        apellidos[359] = "Perdomo";
        apellidos[360] = "Perea";
        apellidos[361] = "Peña";
        apellidos[362] = "Piedrahita";
        apellidos[363] = "Pinares";
        apellidos[364] = "Pineda";
        apellidos[365] = "Pinto";
        apellidos[366] = "Piñerez";
        apellidos[367] = "Polo";
        apellidos[368] = "Ponce";
        apellidos[369] = "Porras";
        apellidos[370] = "Portilla";
        apellidos[371] = "Posada";
        apellidos[372] = "Prada";
        apellidos[373] = "Preciado";
        apellidos[374] = "Prieto";
        apellidos[375] = "Puentes";
        apellidos[376] = "Pulido";
        apellidos[377] = "Páez";
        apellidos[378] = "Pérez";
        apellidos[379] = "Quiceno";
        apellidos[380] = "Quijano";
        apellidos[381] = "Quinchia";
        apellidos[382] = "Quintana";
        apellidos[383] = "Quintero";
        apellidos[384] = "Quiroga";
        apellidos[385] = "Quiroz";
        apellidos[386] = "Quiñones";
        apellidos[387] = "Quiñonez";
        apellidos[388] = "Rada";
        apellidos[389] = "Ramos";
        apellidos[390] = "Ramírez";
        apellidos[391] = "Rangel";
        apellidos[392] = "Reinoso";
        apellidos[393] = "Rendon";
        apellidos[394] = "Rentería";
        apellidos[395] = "Restrepo";
        apellidos[396] = "Rey";
        apellidos[397] = "Reyes";
        apellidos[398] = "Ribera";
        apellidos[399] = "Rincón";
        apellidos[400] = "Rivas";
        apellidos[401] = "Rivera";
        apellidos[402] = "Rivero";
        apellidos[403] = "Roa";
        apellidos[404] = "Robles";
        apellidos[405] = "Rocha";
        apellidos[406] = "Rodríguez";
        apellidos[407] = "Rojas";
        apellidos[408] = "Romero";
        apellidos[409] = "Rondón";
        apellidos[410] = "Rosales";
        apellidos[411] = "Rosero";
        apellidos[412] = "Rua";
        apellidos[413] = "Rubio";
        apellidos[414] = "Rueda";
        apellidos[415] = "Ruiz";
        apellidos[416] = "Ríos";
        apellidos[417] = "Saavedra";
        apellidos[418] = "Salas";
        apellidos[419] = "Salazar";
        apellidos[420] = "Salcedo";
        apellidos[421] = "Saldarriaga";
        apellidos[422] = "Salgado";
        apellidos[423] = "Samper";
        apellidos[424] = "Sanabria";
        apellidos[425] = "Sandoval";
        apellidos[426] = "Santa";
        apellidos[427] = "Santamaría";
        apellidos[428] = "Santander";
        apellidos[429] = "Santillan";
        apellidos[430] = "Santos";
        apellidos[431] = "Sanz";
        apellidos[432] = "Sarmiento";
        apellidos[433] = "Segura";
        apellidos[434] = "Sepúlveda";
        apellidos[435] = "Serna";
        apellidos[436] = "Serrano";
        apellidos[437] = "Sevilla";
        apellidos[438] = "Sierra";
        apellidos[439] = "Silva";
        apellidos[440] = "Solano";
        apellidos[441] = "Soler";
        apellidos[442] = "Sosa";
        apellidos[443] = "Soto";
        apellidos[444] = "Suárez";
        apellidos[445] = "Sáenz";
        apellidos[446] = "Sánchez";
        apellidos[447] = "Tabares";
        apellidos[448] = "Tamayo";
        apellidos[449] = "Tarazona";
        apellidos[450] = "Tejada";
        apellidos[451] = "Tejeda";
        apellidos[452] = "Tejeiro";
        apellidos[453] = "Tellez";
        apellidos[454] = "Tello";
        apellidos[455] = "Toledo";
        apellidos[456] = "Toro";
        apellidos[457] = "Torres";
        apellidos[458] = "Tovar";
        apellidos[459] = "Trejo";
        apellidos[460] = "Trejos";
        apellidos[461] = "Triana";
        apellidos[462] = "Trujillo";
        apellidos[463] = "Ulloa";
        apellidos[464] = "Umaña";
        apellidos[465] = "Upegui";
        apellidos[466] = "Urbano";
        apellidos[467] = "Urbina";
        apellidos[468] = "Uribe";
        apellidos[469] = "Urquijo";
        apellidos[470] = "Urrea";
        apellidos[471] = "Urrego";
        apellidos[472] = "Valderrama";
        apellidos[473] = "Valdez";
        apellidos[474] = "Valdés";
        apellidos[475] = "Valencia";
        apellidos[476] = "Valenciano";
        apellidos[477] = "Valle";
        apellidos[478] = "Vallejo";
        apellidos[479] = "Valverde";
        apellidos[480] = "Vanegas";
        apellidos[481] = "Varela";
        apellidos[482] = "Vargas";
        apellidos[483] = "Vega";
        apellidos[484] = "Velasco";
        apellidos[485] = "Velásquez";
        apellidos[486] = "Vera";
        apellidos[487] = "Vergara";
        apellidos[488] = "Vidal";
        apellidos[489] = "Viera";
        apellidos[490] = "Villa";
        apellidos[491] = "Villada";
        apellidos[492] = "Villamil";
        apellidos[493] = "Villamizar";
        apellidos[494] = "Villanueva";
        apellidos[495] = "Villegas";
        apellidos[496] = "Vivas";
        apellidos[497] = "Vásquez";
        apellidos[498] = "Vélez";
        apellidos[499] = "Yatra";
        apellidos[500] = "Yepes";
        apellidos[501] = "Zabaleta";
        apellidos[502] = "Zambrano";
        apellidos[503] = "Zamora";
        apellidos[504] = "Zamudio";
        apellidos[505] = "Zanabria";
        apellidos[506] = "Zapata";
        apellidos[507] = "Zapatero";
        apellidos[508] = "Zavaleta";
        apellidos[509] = "Zea";
        apellidos[510] = "Zuleta";
        apellidos[511] = "Zuluaga";
        apellidos[512] = "Zuñiga";
        apellidos[513] = "Alvarez";
        apellidos[514] = "Avalos";
        apellidos[515] = "Avila";
        apellidos[516] = "Úsuga";
        apellidos[517] = "Noreña";
        apellidos[518] = "Urán";
        apellidos[519] = "Lotero";
        apellidos[520] = "Chica";
        apellidos[521] = "Atehortúa";
        apellidos[522] = "Cañaveral";
        apellidos[523] = "Arana";
        apellidos[524] = "Castrillón";
        apellidos[525] = "Obando";
        apellidos[526] = "Peñaranda";
//   
    }

    public static String generarEscolaridad() {
        String[] esc = {"Primaria", "Bachillerato", "Técnica", "Tecnología", "Profesional", "Postgrado"};

        return esc[entero(esc.length)];
    }

    public static String generarCultivo() {
        return cultivos[entero(cultivos.length)];
    }

    private static void cargarCultivos() {
        String[] cult = {"Papa capira",
            "Maíz", "Frijol", "Tomate de árbol", "Flores",
            "Yuca", "Repollo", "Cilantro", "Fresas", "Cebolla junca",
            "Cebolla huevo blanca", "Lulo", "Limón", "Aguacate", "Cidra",
            "Tomate chonto", "Zanahoria", "Papa nevada", "Papa criolla",
            "Coles", "Col de bruselas", "Remolacha", "Cebolla huevo morada",
            "Brócoli", "Arveja", "Ahuyama"};

        cultivos = new String[cult.length];
        int i = 0;
        for (String string : cult) {
            cultivos[i++] = string;
        }
    }

    public static String[] generarCultivos(int n) {
        if (n > 0 && n < cultivos.length) {
            String[] c = new String[n];
            int i = 0;
            TreeSet<String> arbol = new TreeSet<>();

            while (i < n) {
                String item = generarCultivo();

                if (arbol.add(item)) {
                    c[i++] = item;
                }
            }

            return c;
        }
        return null;
    }

    public static String pais() {
        return paises[entero(paises.length)];
    }

    private static void cargarPaises() {
        String cadena = "Afganistán\n"
                + "Albania\n"
                + "Alemania\n"
                + "Andorra\n"
                + "Angola\n"
                + "Antigua y Barbuda\n"
                + "Arabia Saudita\n"
                + "Argelia\n"
                + "Argentina\n"
                + "Armenia\n"
                + "Australia\n"
                + "Austria\n"
                + "Azerbaiyán\n"
                + "Bahamas\n"
                + "Bangladés\n"
                + "Barbados\n"
                + "Baréin\n"
                + "Bélgica\n"
                + "Belice\n"
                + "Benín\n"
                + "Bielorrusia\n"
                + "Birmania\n"
                + "Bolivia\n"
                + "Bosnia y Herzegovina\n"
                + "Botsuana\n"
                + "Brasil\n"
                + "Brunéi\n"
                + "Bulgaria\n"
                + "Burkina Faso\n"
                + "Burundi\n"
                + "Bután\n"
                + "Cabo Verde\n"
                + "Camboya\n"
                + "Camerún\n"
                + "Canadá\n"
                + "Catar\n"
                + "Chad\n"
                + "Chile\n"
                + "China\n"
                + "Chipre\n"
                + "Ciudad del Vaticano\n"
                + "Colombia\n"
                + "Comoras\n"
                + "Corea del Norte\n"
                + "Corea del Sur\n"
                + "Costa de Marfil\n"
                + "Costa Rica\n"
                + "Croacia\n"
                + "Cuba\n"
                + "Dinamarca\n"
                + "Dominica\n"
                + "Ecuador\n"
                + "Egipto\n"
                + "El Salvador\n"
                + "Emiratos Arabes Unidos\n"
                + "Eritrea\n"
                + "Eslovaquia\n"
                + "Eslovenia\n"
                + "España\n"
                + "Estados Unidos\n"
                + "Estonia\n"
                + "Etiopía\n"
                + "Filipinas\n"
                + "Finlandia\n"
                + "Fiyi\n"
                + "Francia\n"
                + "Gabón\n"
                + "Gambia\n"
                + "Georgia\n"
                + "Ghana\n"
                + "Granada\n"
                + "Grecia\n"
                + "Guatemala\n"
                + "Guyana\n"
                + "Guinea\n"
                + "Guinea ecuatorial\n"
                + "Guinea-Bisáu\n"
                + "Haití\n"
                + "Honduras\n"
                + "Hungría\n"
                + "India\n"
                + "Indonesia\n"
                + "Irak\n"
                + "Irán\n"
                + "Irlanda\n"
                + "Islandia\n"
                + "Islas Marshall\n"
                + "Islas Salomón\n"
                + "Israel\n"
                + "Italia\n"
                + "Jamaica\n"
                + "Japón\n"
                + "Jordania\n"
                + "Kazajistán\n"
                + "Kenia\n"
                + "Kirguistán\n"
                + "Kiribati\n"
                + "Kuwait\n"
                + "Laos\n"
                + "Lesoto\n"
                + "Letonia\n"
                + "Líbano\n"
                + "Liberia\n"
                + "Libia\n"
                + "Liechtenstein\n"
                + "Lituania\n"
                + "Luxemburgo\n"
                + "Macedonia del Norte\n"
                + "Madagascar\n"
                + "Malasia\n"
                + "Malaui\n"
                + "Maldivas\n"
                + "Malí\n"
                + "Malta\n"
                + "Marruecos\n"
                + "Mauricio\n"
                + "Mauritania\n"
                + "México\n"
                + "Micronesia\n"
                + "Moldavia\n"
                + "Mónaco\n"
                + "Mongolia\n"
                + "Montenegro\n"
                + "Mozambique\n"
                + "Namibia\n"
                + "Nauru\n"
                + "Nepal\n"
                + "Nicaragua\n"
                + "Níger\n"
                + "Nigeria\n"
                + "Noruega\n"
                + "Nueva Zelanda\n"
                + "Omán\n"
                + "Países Bajos\n"
                + "Pakistán\n"
                + "Palaos\n"
                + "Panamá\n"
                + "Papúa Nueva Guinea\n"
                + "Paraguay\n"
                + "Perú\n"
                + "Polonia\n"
                + "Portugal\n"
                + "Reino Unido\n"
                + "República Centroafricana\n"
                + "República Checa\n"
                + "República del Congo\n"
                + "República Democrática del Congo\n"
                + "República Dominicana\n"
                + "Ruanda\n"
                + "Rumanía\n"
                + "Rusia\n"
                + "Samoa\n"
                + "San Cristóbal y Nieves\n"
                + "San Marino\n"
                + "San Vicente y las Granadinas\n"
                + "Santa Lucía\n"
                + "Santo Tomé y Príncipe\n"
                + "Senegal\n"
                + "Serbia\n"
                + "Seychelles\n"
                + "Sierra Leona\n"
                + "Singapur\n"
                + "Siria\n"
                + "Somalia\n"
                + "Sri Lanka\n"
                + "Suazilandia\n"
                + "Sudáfrica\n"
                + "Sudán\n"
                + "Sudán del Sur\n"
                + "Suecia\n"
                + "Suiza\n"
                + "Surinam\n"
                + "Tailandia\n"
                + "Tanzania\n"
                + "Tayikistán\n"
                + "Timor Oriental\n"
                + "Togo\n"
                + "Tonga\n"
                + "Trinidad y Tobago\n"
                + "Túnez\n"
                + "Turkmenistán\n"
                + "Turquía\n"
                + "Tuvalu\n"
                + "Ucrania\n"
                + "Uganda\n"
                + "Uruguay\n"
                + "Uzbekistán\n"
                + "Vanuatu\n"
                + "Venezuela\n"
                + "Vietnam\n"
                + "Yemen\n"
                + "Yibuti\n"
                + "Zambia\n"
                + "Zimbabue";

        paises = cadena.split("\n");

//        System.out.println("p: " + paises.length);
    }

    public static char[][] crearIsla(int m, int n) {
        char[][] isla = null;
        if (m > 4 && n > 4) {
            isla = new char[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    isla[i][j] = ' ';
                    if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                        isla[i][j] = 'A';
                    }
                }
            }
            int i = entero(1, m - 2);
            int j = entero(1, n - 2);
            isla[i][j] = 'P';

            do {
                i = entero(1, m - 2);
                j = entero(1, n - 2);
            } while (isla[i][j] != ' ');

            isla[i][j] = 'T';
        }
        return isla;
    }
}
