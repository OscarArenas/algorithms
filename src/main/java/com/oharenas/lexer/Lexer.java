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

import com.oharenas.util.ArithmeticNotation;

/**
 *
 * @author Oscar Arenas
 */
public class Lexer {

    private String source;
    private int index;
    private int length;
    private int currentToken;
    private String message;

    public Lexer(String source) {
        set(source);
    }

    public final void set(String source) {
        this.source = ArithmeticNotation.eliminarEspaciosAdicionales(source);
        index = 0;
        length = 0;
        currentToken = 0;
        message = "";

    }

    private int getToken() {
        int n = source.length();
        index += length;
        length = 1;

        while (index < n && (source.charAt(index) == ' ' || source.charAt(index) == '\t')) {
            index++;
        }

        if (index < n) {
            char character = source.charAt(index);

            switch (character) {
                case '+':
                    return Token.SUMA;
                case '-':
                    if (index + 1 < n && source.charAt(index + 1) != ' ') {
                        int code = number();
                        if (code != Token.ERROR) {
                            return code;
                        } else {
                            code = id();
                            if (code == Token.IDENTIFICADOR) {
                                return Token.IDENTIFICADOR;
                            }
                        }
                    }
                    return Token.RESTA;
                case '*':
                    if (index + 1 < n && source.charAt(index + 1) == '*') {
                        length++;
                        return Token.POTENCIA;
                    }
                    return Token.MULTIPLICACION;
                case '/':
                    return Token.DIVISION;
                case '^':
                    return Token.POTENCIA;
                case '%':
                    return Token.MODULO;
                case '(':
                    return Token.PARENTESIS_ABRIR;
                case ')':
                    return Token.PARENTESIS_CERRAR;
                case ',':
                    return Token.COMA;
                case '.':
                    int code = number();
                    if (code == Token.ERROR) {
                        message = "Error: multiple decimal points";
                        System.out.println(message);
                    }
                    return code;
                default:
                    if (Character.isDigit(character)) {
                        return number();
                    } else if (Character.isAlphabetic(character)) {
                        return id();
                    } else {
                        message = "Error: Unknown symbol '" + character + "'";
                        System.out.println(message);
                        return Token.ERROR;
                    }
            }
        }
        return Token.EOF;
    }

    public void advance() {
        currentToken = getToken();
    }

    public boolean match(int token) {
        if (currentToken == 0) {
            currentToken = getToken();
        }
        return token == currentToken;
    }

    private int number() {
        int n = source.length();
        char character = source.charAt(index);

        if (character == '-' || character == '+') {
            if (index + 1 < n) {
                length++;
                character = source.charAt(index + 1);
            }
        }

        if (Character.isDigit(character)) {
            while (index + length < n
                    && Character.isDigit(source.charAt(index + length))) {
                ++length;
            }

            if (index + length < n
                    && source.charAt(index + length) == '.') {
                ++length;

                while (index + length < n
                        && Character.isDigit(source.charAt(index + length))) {
                    ++length;
                }

                return Token.REAL;
            }

            return Token.ENTERO;
        } else if (character == '.') {

            if (index + length < n
                    && Character.isDigit(source.charAt(index + length))) {
                while (index + length < n
                        && Character.isDigit(source.charAt(index + length))) {
                    ++length;
                }
                return Token.REAL;
            }

        }
        return Token.ERROR;
    }

    private int id() {
        int n = source.length();
        char character = source.charAt(index);

        if (character == '-') {
            if (index + 1 < n) {
                character = source.charAt(index + 1);
            }
        }

        if (Character.isAlphabetic(character)) {
            while (index + length < n
                    && Character.isLetterOrDigit(source.charAt(index + length))) {
                ++length;
            }

            switch (getString()) {
                case "abs":
                case "sqrt":
                case "sin":
                case "cos":
                case "tan":
                case "floor":
                case "ceil":
                case "min":
                case "max":
                    return Token.FUNCION_INTERNA;
                default:
                    return Token.IDENTIFICADOR;
            }
        }
        return Token.ERROR;
    }

    public boolean isFinish() {
        return match(Token.EOF);
    }

    public int getInteger() {
        return Integer.parseInt(source.substring(index, index
                + length));
    }

    public double getDouble() {
        return Double.parseDouble(source.substring(index, index
                + length));
    }

    public String getString() {
        return source.substring(index, index + length);
    }

    public String getMessage() {
        return message;
    }
}
