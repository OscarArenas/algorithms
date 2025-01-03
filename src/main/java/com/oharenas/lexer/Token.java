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
package com.oharenas.lexer;

/**
 *
 * @author Oscar Arenas
 */
public class Token {

    public static final int EOF = -2;
    public static final int ERROR = -1;
    //
    public static final int SUMA = 1;
    public static final int RESTA = 2;
    public static final int MULTIPLICACION = 3;
    public static final int DIVISION = 4;
    public static final int POTENCIA = 5;
    public static final int DIV = 6;
    public static final int MODULO = 7;
    //
    public static final int ENTERO = 20;
    public static final int REAL = 21;
    public static final int LOGICO = 22;
    public static final int CARACTER = 23;
    public static final int CADENA = 24;
    public static final int VECTOR_ENTERO = 25;
    public static final int VECTOR_REAL = 26;
    public static final int VECTOR_LOGICO = 27;
    public static final int VECTOR_CARACTER = 28;
    public static final int VECTOR_CADENA = 29;
    public static final int MATRIZ_ENTERO = 30;
    public static final int MATRIZ_REAL = 31;
    public static final int MATRIZ_LOGICO = 32;
    public static final int MATRIZ_CARACTER = 33;
    public static final int MATRIZ_CADENA = 34;
    public static final int IMAGINARIO = 35;
    //
    public static final int ASIGNACION = 50;
    public static final int VARIABLE = 51;
    public static final int CONSTANTE = 52;
    public static final int ALGORITMO = 53;
    public static final int PARENTESIS_ABRIR = 54;
    public static final int PARENTESIS_CERRAR = 55;
    public static final int PALABRA_RESERVADA = 56;
    public static final int COMA = 57;
    public static final int PUNTO_Y_COMA = 58;
    public static final int COMILLA_SIMPLE = 59;
    public static final int COMILLA_DOBLE = 60;
    public static final int CAMBIO_LINEA = 61;
    public static final int GUION_BAJO = 62;
    public static final int DOS_PUNTOS = 63;
    public static final int PUNTO = 64;
    public static final int FUNCION_INTERNA = 65;
    public static final int IDENTIFICADOR = 66;
    public static final int CORCHETE_ABRIR = 67;
    public static final int CORCHETE_CERRAR = 68;
    public static final int CONSTANTE_INTERNA = 69;
    //
    public static final int MENOR_QUE = 200;
    public static final int MAYOR_QUE = 201;
    public static final int IGUAL = 202;
    public static final int DIFERENTE = 203;
    public static final int MENOR_O_IGUAL_QUE = 204;
    public static final int MAYOR_O_IGUAL_QUE = 205;
    public static final int Y_LOGICO = 206;
    public static final int O_LOGICO = 207;
    public static final int NO_LOGICO = 208;
    public static final int O_EXCLUSIVO_LOGICO = 209;
}
