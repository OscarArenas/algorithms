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

import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public class Parser {

    private Lexer lexer;
    private String source;
    private ArrayList<Number> listaInstrucciones;
    private String mensajeError;
    private boolean continuar;
    private boolean faltaOperando;

    public Parser(String source) {
        this.lexer = new Lexer(source);
        this.source = "";
        faltaOperando = false;
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        listaInstrucciones = new ArrayList<>();
        source = "";
        mensajeError = "";
        continuar = true;
    }

//    public void declaraciones() {
//        while (!lexer.isFinish() && !lexer.match(Token.ERROR) && continuar) {
//            expresion();
//        }
//        if (lexer.match(Token.ERROR) || !continuar) {
//            mensajeError = lexer.getMessage() + mensajeError;
//            listaInstrucciones.add(Token.ERROR);
//        }
//        listaInstrucciones.add(Token.EOF);
//    }
    public void declaraciones2() {
        int contarParentesis = 0;
        int contarOperandos = 0;

        while (!lexer.isFinish()) {
            if (lexer.match(Token.ENTERO) || lexer.match(Token.REAL) || lexer.match(Token.IDENTIFICADOR)) {
                source += lexer.getString();
                System.out.println("Entero: " + lexer.getInteger());
            } else if (lexer.match(Token.REAL)) {
                source += lexer.getString();
                System.out.println("Real: " + lexer.getString());
            } else if (lexer.match(Token.IDENTIFICADOR)) {
                source += lexer.getString();
                System.out.println("ID: " + lexer.getString());
            } else if (lexer.match(Token.SUMA)) {
                source += " +";
                contarOperandos++;
                System.out.println("SUMA");
            } else if (lexer.match(Token.RESTA)) {
                source += " -";
                contarOperandos++;
                System.out.println("RESTA");
            } else if (lexer.match(Token.MULTIPLICACION)) {
                source += " *";
                contarOperandos++;
                System.out.println("MULTIPLICACION");
            } else if (lexer.match(Token.DIVISION)) {
                source += " /";
                contarOperandos++;
                System.out.println("DIVISION");
            } else if (lexer.match(Token.POTENCIA)) {
                source += " ^";
                contarOperandos++;
                System.out.println("POTENCIA");
            } else if (lexer.match(Token.MODULO)) {
                source += " %";
                contarOperandos++;
                System.out.println("MODULO");
            } else if (lexer.match(Token.PARENTESIS_ABRIR)) {
                source += " (";
                contarParentesis++;
                System.out.println("(");
            } else if (lexer.match(Token.PARENTESIS_CERRAR)) {
                if (contarParentesis > 0) {
                    contarParentesis--;
                    source += ")";
                    System.out.println(")");
                } else {
                    System.out.println("Error: Falta cerrar paréntesis ')'");
                }
            } else if (lexer.match(Token.ERROR)) {
                System.out.println(lexer.getMessage());
                break;
            }

            lexer.advance();
        }
    }

    public String getSource() {
        return source;
    }

    private void expresion() {
        termino();
        expresionPrima();
    }

    private void expresionPrima() {
        if (lexer.match(Token.SUMA)) {
            lexer.advance();
            termino();
            listaInstrucciones.add(Token.SUMA);
            expresionPrima();
        }
        if (lexer.match(Token.RESTA)) {
            lexer.advance();
            termino();
            listaInstrucciones.add(Token.RESTA);
            expresionPrima();
        }
    }

    private void termino() {
        if (lexer.match(Token.ENTERO)) {
            int entero = lexer.getInteger();
            lexer.advance();

            listaInstrucciones.add(entero);

        } else if (lexer.match(Token.REAL)) {
            double real = lexer.getDouble();

            lexer.advance();
//            listaInstrucciones.add(Token.PUSH_REAL);
            listaInstrucciones.add(real);

        } else if (lexer.match(Token.IDENTIFICADOR)) {
            String id = lexer.getString();

            lexer.advance();
//            listaInstrucciones.add(Token.PUSH_REAL);
//            listaInstrucciones.add(id);

        } else if (lexer.match(Token.PARENTESIS_ABRIR)) {
            lexer.advance();

            expresion();
            if (!lexer.match(Token.PARENTESIS_CERRAR)) {
                mensajeError += "\nError: Se esperaba ')'";
                continuar = false;
            }

            lexer.advance();
        } else if (lexer.match(Token.RESTA)) {
            lexer.advance();
            termino();
        }
    }

    public void set(Lexer lexer) {
        this.lexer = lexer;
        listaInstrucciones = new ArrayList<>();
    }

    public ArrayList<Number> getInstrucciones() {
        return listaInstrucciones;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    private void setMensajeError(String mensajeError) {
        this.mensajeError += "\n" + mensajeError;
        continuar = false;
    }
}
