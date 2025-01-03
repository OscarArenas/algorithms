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

import com.oharenas.lexer.Lexer;
import com.oharenas.lexer.Token;
import java.util.Stack;

/**
 *
 * @author Oscar Arenas
 */
public class ArithmeticNotation {

    public static String fromInfixToPrefix(String infixNotation) {
        infixNotation = ArithmeticNotation.verifyInfixNotation(infixNotation);

        Lexer lexer = new Lexer(infixNotation);
        Stack<String> pilaExpresion = new Stack<>();

        while (!lexer.isFinish()) {
            if (lexer.match(Token.ERROR)) {
                return lexer.getMessage();
            } else {
                pilaExpresion.push(lexer.getString());
            }

            lexer.advance();
        }

        String numberPattern = "(\\+|-)?[0-9]+(\\.[0-9]*)?";
        String idPattern = "(\\-)?[a-zA-Z]+[a-zA-Z0-9]*";

        Stack<String> pilaResultado = new Stack();
        Stack<String> pilaOperadores = new Stack();

        while (!pilaExpresion.isEmpty()) {
            String cima = pilaExpresion.pop();
//            System.out.println("cima: "+cima);
            if (cima.matches(numberPattern)) {
                pilaResultado.push(cima);
            } else if (cima.matches(idPattern)) {
                pilaResultado.push(cima);
//                System.out.println("var: "+cima);
            } else if (cima.equals("(")) {
                String op = pilaOperadores.pop();
                while (!pilaOperadores.isEmpty() && !op.equals(")")) {
                    pilaResultado.push(op);
                    op = pilaOperadores.pop();
                }
            } else {
                if (!pilaOperadores.isEmpty() && !cima.equals(")")) {
                    String temp = pilaOperadores.peek();

                    while (!pilaOperadores.isEmpty() && !temp.equals(")") && precedence(temp) > precedence(cima)) {
                        pilaResultado.push(pilaOperadores.pop());

                        if (!pilaOperadores.isEmpty()) {
                            temp = pilaOperadores.peek();
                        }
                    }
                }
                pilaOperadores.push(cima);
            }
        }

        while (!pilaOperadores.isEmpty()) {
            String op = pilaOperadores.pop();

            if (!op.equals(")")) {
                pilaResultado.push(op);
            }
        }

        String cadena = "";
        while (!pilaResultado.isEmpty()) {
            cadena += pilaResultado.pop() + " ";
        }

        return eliminarEspaciosAdicionales(cadena);
    }

    public static String fromInfixToPostfix(String infixNotation) {
        infixNotation = ArithmeticNotation.verifyInfixNotation(infixNotation);

        Lexer lexer = new Lexer(infixNotation);
        Stack<String> pilaOperadores = new Stack();
        String cadena = "";

        while (!lexer.isFinish()) {
            if (lexer.match(Token.ERROR)) {
                return lexer.getMessage();
            } else if (lexer.match(Token.ENTERO)) {
                cadena += lexer.getInteger() + " ";
            } else if (lexer.match(Token.REAL)) {
                cadena += lexer.getDouble() + " ";
            } else if (lexer.match(Token.IDENTIFICADOR)) {
                cadena += lexer.getString() + " ";
            } else if (lexer.match(Token.PARENTESIS_ABRIR)) {
                pilaOperadores.push("(");
            } else if (lexer.match(Token.PARENTESIS_CERRAR)) {
                String op = pilaOperadores.pop();

                while (!pilaOperadores.isEmpty() && !op.equals("(")) {
                    cadena += op + " ";
                    op = pilaOperadores.pop();
                }
            } else {
                String op = lexer.getString();
                if (!pilaOperadores.isEmpty()) {
                    String temp = pilaOperadores.peek();
                    while (!pilaOperadores.isEmpty() && !temp.equals(")") && precedence(temp) >= precedence(op)) {
                        cadena += pilaOperadores.pop() + " ";

                        if (!pilaOperadores.isEmpty()) {
                            temp = pilaOperadores.peek();
                        }
                    }

                }
                pilaOperadores.push(op);
            }
            lexer.advance();
        }

        while (!pilaOperadores.isEmpty()) {
            cadena += pilaOperadores.pop() + " ";
        }
        return cadena;
    }

    // Arithmetic operators precedence
    public static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
            case "%":
                return 2;
            case "^":
            case "**":
                return 3;
            default:
                break;
        }
        return 0;
    }

    public static String reverse(String text) {
        String newText = "";
        for (int i = 0; i < text.length(); i++) {
            newText = text.charAt(i) + newText;
        }
        return newText;
    }

    public static String removeAllWhitespaces(String text) {
        String newText = "";
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            if (symbol != ' ' && symbol != '\t') {
                newText += symbol;
            }
        }
        return newText;
    }

    public static String eliminarEspaciosAdicionales(String cadena) {
        boolean espacio = false;
        String nuevaCadena = "";

        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == ' ' || cadena.charAt(i) == '\t') {
                espacio = true;
            } else if (espacio) {
                nuevaCadena += " " + cadena.charAt(i);
                espacio = false;
            } else {
                nuevaCadena += cadena.charAt(i);
            }
        }

        if (nuevaCadena.length() > 0 && nuevaCadena.charAt(0) == ' ') {
            nuevaCadena = nuevaCadena.substring(1);
        }
        return nuevaCadena;
    }

    public static String verifyInfixNotation(String expresion) {
        if (!verificarParentesis(expresion)) {
            System.out.println("Error: Unbalanced parentheses");
            return "";
        }

        expresion = cambiarPotencia(removeAllWhitespaces(expresion));

        return eliminarMenos(expresion);
    }

    public static boolean verificarParentesis(String expresion) {
        Stack<String> pila = new Stack();
        int i = 0;
        int n = expresion.length();

        while (i < n) {
            char caracter = expresion.charAt(i);

            if (caracter == '(') {
                pila.push(caracter + "");
            } else if (caracter == ')') {
                if (!pila.isEmpty()) {
                    pila.pop();
                } else {
                    return false;
                }
            }
            i++;
        }

        return pila.isEmpty();
    }

    private static String cambiarPotencia(String cadena) {
        String nuevaCadena = "";
        int n = cadena.length();

        for (int i = 0; i < n; i++) {
            char s = cadena.charAt(i);
            if (s == '*') {
                if (i + 1 < n && cadena.charAt(i + 1) == '*') {
                    s = '^';
                    i++;
                }
            }
            nuevaCadena += s;
        }
        return nuevaCadena;
    }

    public static String eliminarMenos(String cadena) {
        cadena = removeAllWhitespaces(cadena);
//        System.out.println(" - Sin espacios: " + cadena);
        String nuevaCadena = "";
        String cadenaNumero = "";
        String cadenaID = "";
        boolean esNumero = true;
        boolean esSigno = true;
        boolean decimal = false;
        int signo = 1;
        int n = cadena.length();

        for (int i = 0; i < n; i++) {
            char s = cadena.charAt(i);

            if (esSigno) {
                switch (s) {
                    case '-':
                        signo = -signo;
                        break;
                    case '+':
                        break;
                    default:
                        esSigno = false;
                        --i;
                        break;
                }
            } else if (esNumero) {
                if (Character.isDigit(s)) {
                    cadenaNumero += s;
                } else if (s == '.' && !decimal) {
                    decimal = true;
                    cadenaNumero += s;
                } else {
                    if (signo == -1) {
                        nuevaCadena += "-";
                        signo = 1;
                    }
                    if (cadenaNumero.length() > 0) {
                        nuevaCadena += cadenaNumero + " ";
                        cadenaNumero = "";
                    }
                    --i;
                    esNumero = false;
                    decimal = false;
                }
            } else if (Character.isLetterOrDigit(s)) {
                cadenaID += s;
            } else {
                if (cadenaID.length() > 0) {
                    nuevaCadena += cadenaID + " ";
                    cadenaID = "";
                }
                signo = 1;
                if (s != ')') {
                    esNumero = true;
                    esSigno = true;
                } else {
                    int m = nuevaCadena.length();
                    if (m > 1 && nuevaCadena.charAt(m - 1) == ' ') {
                        nuevaCadena = nuevaCadena.substring(0, m - 1);
                    }
                }

                nuevaCadena += s;
                if (s != '.' && s != '(') {
                    nuevaCadena += " ";
                }
            }
        }
        if (signo == -1) {
            if (cadenaNumero.length() > 0) {
                nuevaCadena += "-" + cadenaNumero;
                cadenaNumero = "";
            } else {

            }
        } else if (cadenaID.length() > 0) {
            nuevaCadena += cadenaID;
        }

        return nuevaCadena + cadenaNumero;
    }

    public static String darFormato(String cadena) {
        String nuevaCadena = "";
        String cadenaNumero = "";
        String signo = "";
        boolean esNumero = true;
        boolean esSigno = true;

        for (int i = 0; i < cadena.length(); i++) {
            char s = cadena.charAt(i);

            if (esSigno) {
                if (s == '-' || s == '+') {
                    signo += s;
                    esSigno = false;
                } else {
                    cadenaNumero = "";
                    --i;
                    esSigno = false;
                }
            } else if (esNumero) {
                if (Character.isDigit(s) || s == '.') {
                    cadenaNumero += s + "";
                } else {
                    if (cadenaNumero.length() > 0) {
                        nuevaCadena += signo + cadenaNumero + " ";
                        cadenaNumero = "";
                        signo = "";
                    }
                    --i;
                    esNumero = false;
                }
            } else if (Character.isLetterOrDigit(s)) {
                cadenaNumero += s;
            } else {
                if (cadenaNumero.length() > 0) {
                    nuevaCadena += signo + cadenaNumero + " ";
                    cadenaNumero = "";
                    signo = "";
                }
                nuevaCadena += s + " ";
                esNumero = true;
                esSigno = true;
            }
        }
        if (signo.equals("-")) {
            nuevaCadena += "-" + cadenaNumero;
            cadenaNumero = "";
        }
        return eliminarEspaciosAdicionales(nuevaCadena + cadenaNumero);
    }

    public static double evaluate(String expresionAritmetica) throws IllegalArgumentException {
        expresionAritmetica = verifyInfixNotation(expresionAritmetica);

        double resultado = 0;
        Stack<Double> numeros = new Stack<>();
        Stack<String> operadores = new Stack<>();

        Lexer lexer = new Lexer(expresionAritmetica);

        String op;

        while (!lexer.isFinish()) {
            if (lexer.match(Token.ENTERO) || lexer.match(Token.REAL)) {
                numeros.push(lexer.getDouble());
            } else if (lexer.match(Token.SUMA) || lexer.match(Token.RESTA) || lexer.match(Token.MULTIPLICACION) || lexer.match(Token.DIVISION) || lexer.match(Token.FUNCION_INTERNA)) {
                op = lexer.getString();
                operadores.push(op);
            } else if (lexer.match(Token.PARENTESIS_CERRAR)) {
                double numero2 = numeros.pop();
                op = operadores.pop();

                switch (op) {
                    case "+":
                        resultado = numeros.pop() + numero2;
                        break;
                    case "-":
                        resultado = numeros.pop() - numero2;
                        break;
                    case "*":
                        resultado = numeros.pop() * numero2;
                        break;
                    case "/":
                        resultado = numeros.pop() / numero2;
                        break;
                    case "^":
                        resultado = Math.pow(numeros.pop(), numero2);
                        break;
                    case "sqrt":
                        if (numero2 >= 0) {
                            resultado = Math.sqrt(numero2);
                        } else {
                            throw new IllegalArgumentException("Sqrt of " + numero2);
                        }
                        break;
                    default:
                }

                numeros.push(resultado);

            } else if (lexer.match(Token.IDENTIFICADOR)) {
                throw new IllegalArgumentException("" + lexer.getString());
            } else if (lexer.match(Token.ERROR)) {
                throw new IllegalArgumentException("Unknown token '" + lexer.getString() + "'");
            }

            lexer.advance();
        }// Fin WHILE

        return numeros.pop();
    }

    private static double evaluatePostfix3(String expresionAritmetica) throws IllegalArgumentException {
        String[] tokens = expresionAritmetica.split(" ");
        Stack<Double> numeros = new Stack<>();

        System.out.println("Operaciones:");

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                System.out.println("Operacion: " + token);
                double resultado = 0;
                double numero2 = numeros.pop();

                System.out.println(" - Pop 1: " + numero2);
                System.out.println(" - Pop 2: " + numeros.peek());

                switch (token) {
                    case "+":
                        resultado = numeros.pop() + numero2;
                        break;
                    case "-":
                        resultado = numeros.pop() - numero2;
                        break;
                    case "*":
                        resultado = numeros.pop() * numero2;
                        break;
                    case "/":
                        resultado = numeros.pop() / numero2;
                        break;
                    case "^":
                        resultado = Math.pow(numeros.pop(), numero2);
                        break;
                    case "sqrt":
                        if (numero2 >= 0) {
                            resultado = Math.sqrt(numero2);
                        } else {
                            throw new IllegalArgumentException("Sqrt of " + numero2);
                        }
                        break;
                    default:
                }
                System.out.println(" * Estado pila: " + numeros);
                numeros.push(resultado);
                System.out.println(" ** Resultado: " + numeros + "\n");
            } else {
                numeros.push(Double.parseDouble(token));
                System.out.println("Push: " + numeros);
//                System.out.println(numeros);
            }
        }// Fin WHILE

        return numeros.pop();
    }

    public static double evaluatePostfix(String expresionAritmetica) throws IllegalArgumentException {
        Stack<Double> numeros = new Stack<>();
        Lexer lexer = new Lexer(expresionAritmetica);

        System.out.println("Operaciones:");

        while (!lexer.isFinish()) {
            if (lexer.match(Token.ENTERO) || lexer.match(Token.REAL)) {
                numeros.push(lexer.getDouble());
                System.out.println("Push: " + numeros);
            } else if (lexer.match(Token.SUMA) || lexer.match(Token.RESTA) || lexer.match(Token.MULTIPLICACION) || lexer.match(Token.DIVISION) || lexer.match(Token.FUNCION_INTERNA)) {
                String op = lexer.getString();
                System.out.println("Operacion: " + op);
                double resultado = 0;
                double numero2 = numeros.pop();

                System.out.println(" - Pop 1: " + numero2);
                System.out.println(" - Pop 2: " + numeros.peek());

                switch (op) {
                    case "+":
                        resultado = numeros.pop() + numero2;
                        break;
                    case "-":
                        resultado = numeros.pop() - numero2;
                        break;
                    case "*":
                        resultado = numeros.pop() * numero2;
                        break;
                    case "/":
                        resultado = numeros.pop() / numero2;
                        break;
                    case "^":
                        resultado = Math.pow(numeros.pop(), numero2);
                        break;
                    case "sqrt":
                        if (numero2 >= 0) {
                            resultado = Math.sqrt(numero2);
                        } else {
                            throw new IllegalArgumentException("Sqrt of " + numero2);
                        }
                        break;
                    default:
                }
                System.out.println(" * Estado pila: " + numeros);
                numeros.push(resultado);
                System.out.println(" ** Resultado: " + numeros + "\n");
            } else {
                throw new IllegalArgumentException("Símbolo desconocido " + lexer.getString());
            }
            lexer.advance();
        }// Fin WHILE

        return numeros.pop();
    }
}
