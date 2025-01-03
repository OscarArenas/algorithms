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

import java.awt.Toolkit;
import java.util.Scanner;

/**
 * Contiene métodos (funciones) para mostrar cadenas de caracteres con color.
 *
 * @author Oscar Arenas
 */
public class Consola {

    private static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    //public static final String WHITE = "\033[0;37m";   // WHITE

    // Bright Colors
    public static final String BRIGHT_BLACK = "\033[1;30m";   // BLACK
    public static final String BRIGHT_RED = "\033[1;31m";     // RED
    public static final String BRIGHT_GREEN = "\033[1;32m";   // GREEN
    public static final String BRIGHT_YELLOW = "\033[1;33m";  // YELLOW
    public static final String BRIGHT_BLUE = "\033[1;34m";    // BLUE
    public static final String BRIGHT_PURPLE = "\033[1;35m";  // PURPLE
    public static final String BRIGHT_CYAN = "\033[1;36m";    // CYAN
    //public static final String BRIGHT_WHITE = "\033[1;37m";   // WHITE

    /**
     * Muestra por pantalla la cadena en color rojo.
     *
     * @param string Cadena a mostrar por pantalla.
     */
    public static void printRed(String string) {
        printColor(string, RED);
    }

    /**
     * Muestra por pantalla la cadena en color verde.
     *
     * @param string Cadena a mostrar por pantalla.
     */
    public static void printGreen(String string) {
        printColor(string, GREEN);
    }

    /**
     * Muestra por pantalla la cadena en color amarillo.
     *
     * @param string Cadena a mostrar por pantalla.
     */
    public static void printYellow(String string) {
        printColor(string, YELLOW);
    }

    /**
     * Muestra por pantalla la cadena en color azul.
     *
     * @param string Cadena a mostrar por pantalla.
     */
    public static void printBlue(String string) {
        printColor(string, BLUE);
    }

    /**
     * Muestra por pantalla la cadena en color purpura.
     *
     * @param string Cadena a mostrar por pantalla.
     */
    public static void printPurple(String string) {
        printColor(string, PURPLE);
    }

    /**
     * Muestra por pantalla la cadena en color cian (celeste o azul claro
     * saturado).
     *
     * @param string Cadena a mostrar por pantalla.
     */
    public static void printCyan(String string) {
        printColor(string, CYAN);
    }

    /**
     * Muestra por pantalla la cadena en el color indicado y cambia de línea.
     *
     * @param string Cadena a mostrar por pantalla.
     * @param color Constante que indica el color de la cadena a mostrar.
     */
    public static void println(String string, String color) {
        System.out.println(color + string + RESET);
    }

    /**
     * Muestra por pantalla la cadena en el color indicado sin cambio de línea.
     *
     * @param string Cadena a mostrar por pantalla.
     * @param color Constante que indica el color de la cadena a mostrar.
     */
    public static void print(String string, String color) {
        System.out.print(color + string + RESET);
    }

    private static void printColor(String cadena, String color) {
        String[] lineas = cadena.split("\n");
        for (String linea : lineas) {
            println(linea, color);
        }
        // Contar cambios de linea al final de la cadena
        int contador = 0;
        for (int i = cadena.length() - 1; i > 0; i--) {
            char crt = cadena.charAt(i);
            if (crt == '\n') {
                contador++;
            } else if (crt != ' ' && crt != '\t') {
                break;
            }
        }
        for (int i = 0; i < contador; i++) {
            System.out.println();
        }
    }

    /**
     * Indica si el parametro color representa un colo válido.
     *
     * @param color Cadena con el código de un color.
     * @return Retorna true si el color es válido.
     */
    public static boolean isColor(String color) {
        if (color == null) {
            return false;
        }

        switch (color) {
            case BLACK:
                return true;
            case RED:
                return true;
            case GREEN:
                return true;
            case YELLOW:
                return true;
            case BLUE:
                return true;
            case PURPLE:
                return true;
            case CYAN:
                return true;
            case BRIGHT_BLACK:
                return true;
            case BRIGHT_BLUE:
                return true;
            case BRIGHT_CYAN:
                return true;
            case BRIGHT_GREEN:
                return true;
            case BRIGHT_PURPLE:
                return true;
            case BRIGHT_RED:
                return true;
            case BRIGHT_YELLOW:
                return true;
            default:
                return false;
        }
    }

    public static void mostrarMensajeDeError(String mensaje) {
        System.out.println();
        if (mensaje != null) {
            printRed("Error: " + mensaje);
        } else {
            printRed("Error!!!");
        }

        Toolkit.getDefaultToolkit().beep();
        System.out.println();
    }

    public static void mostrarMensajeDeErrorConPuasa(String mensaje) {
        mostrarMensajeDeError(mensaje);

        System.out.print("Presione Enter para continuar... ");

        Scanner input = new Scanner(System.in);
        input.nextLine();
        System.out.println();
    }

    public static void imprimirMenu(String titulo, String[] opciones, int margen) {
        imprimirMenu(titulo, opciones, '*', '*', margen);
    }

    public static void imprimirMenu(String titulo, String[] opciones) {
        imprimirMenu(titulo, opciones, '*', '*', 10);
    }

    public static void imprimirMenu(String titulo, String[] opciones, char simbolo) {
        imprimirMenu(titulo, opciones, simbolo, simbolo, 10);
    }

    public static void imprimirMenu(String titulo, String[] opciones, char simbolo, int margen) {
        imprimirMenu(titulo, opciones, simbolo, simbolo, margen);
    }

    public static void imprimirMenu(String titulo, String[] opciones, char simboloHorizontal, char simboloVertical) {
        imprimirMenu(titulo, opciones, simboloHorizontal, simboloVertical, 10);
    }

    public static void imprimirMenu(String titulo, String[] opciones, char simboloHorizontal, char simboloVertical, int margen) {
        imprimirMenu(titulo, opciones, simboloHorizontal, simboloVertical, simboloVertical, margen);
    }

    public static void imprimirMenu(String titulo, String[] opciones, char simboloHorizontal, char simboloVerticalIzquierda, char simboloVerticalDerecha, int margen) {
        if (titulo == null) {
            titulo = "Menú";
        }
        String[] lineasTitulo = titulo.split("\n");
        String[] opciones2 = opciones.clone();
        if (simboloHorizontal > 400) {
            simboloHorizontal = '*';
        }
//if (simboloVerticalIzquierda > 255) {
//            simboloVerticalIzquierda = '*';
//        }
        int mayorAncho = 0;
        int i = 0;
        for (String linea : lineasTitulo) {
            linea = linea.trim();
            lineasTitulo[i++] = linea;
            if (linea.length() > mayorAncho) {
                mayorAncho = linea.length();
            }
        }
        i = 0;
        for (String linea : opciones2) {
            linea = (i + 1) + ". " + linea.trim();
            opciones2[i++] = linea;
            if (linea.length() > mayorAncho) {
                mayorAncho = linea.length();
            }
        }
        if (mayorAncho > 6) {
            margen = Math.abs(margen);
            mayorAncho += 6;
            String rectangulo = "";
            String margenSuperior = "";

            for (int j = 0; j < margen; j++) {
                margenSuperior += " ";
            }

            for (i = 0; i < mayorAncho; i++) {
                margenSuperior += simboloHorizontal;
            }
            margenSuperior += "\n";
            rectangulo += margenSuperior;
            i = 0;
            for (String linea : lineasTitulo) {
                if (!linea.isEmpty()) {
                    int delta = (mayorAncho - 1 - linea.length()) / 2;

                    for (int j = 0; j < margen; j++) {
                        rectangulo += " ";
                    }
                    rectangulo += simboloVerticalIzquierda;
                    for (i = 0; i < delta; i++) {
                        rectangulo += ' ';
                    }
                    rectangulo += linea;
                    delta = mayorAncho - (2 + delta + linea.length());
                    for (i = 0; i < delta; i++) {
                        rectangulo += ' ';
                    }
                    rectangulo += simboloVerticalDerecha + "\n";
                }
            }
            rectangulo += margenSuperior;

            for (String linea : opciones2) {
                if (!linea.isEmpty()) {
                    int delta = mayorAncho - linea.length() - 3;
                    for (int j = 0; j < margen; j++) {
                        rectangulo += " ";
                    }
                    rectangulo += simboloVerticalIzquierda + "  ";

                    rectangulo += linea;
                    for (i = 0; i < delta - 1; i++) {
                        rectangulo += ' ';
                    }
                    rectangulo += simboloVerticalDerecha + "\n";
                }
            }
            rectangulo += margenSuperior;
            System.out.println(rectangulo);
        }
    }
}
