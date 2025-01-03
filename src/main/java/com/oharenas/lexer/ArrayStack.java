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

import java.util.EmptyStackException;

/**
 *
 * @author Oscar Arenas
 */
public class ArrayStack {

    // Fields (Atributos)
    private String[] data;
    private int size;

    // Methods 
    public ArrayStack() {
        data = new String[1];
        size = 0;
    }

    public boolean push(String item) {
        if (size == data.length) {
            stretch();
        }

        data[size++] = item;
        return true;
    }

    public boolean push(char item) {
        return push(item + "");
    }

    public boolean push(int item) {
        return push(item + "");
    }

    public boolean push(double item) {
        return push(item + "");
    }

    public String pop() throws EmptyStackException {
        if (size > 0) {
            size--;
            return data[size];
        }
        throw new EmptyStackException();
    }

    public String peek() throws EmptyStackException {
        if (size > 0) {
            return data[size - 1];
        }
        throw new EmptyStackException();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isNumber() throws EmptyStackException {
        if (size > 0) {// Falta \\.[0-9]+
            String numberPattern = "(\\+|-)?[0-9]+(\\.[0-9]*)?";

            return data[size - 1].matches(numberPattern);
        }
        throw new EmptyStackException();
    }

    private void stretch() {
        String[] nuevoVector = new String[2 * data.length];

        for (int i = 0; i < data.length; i++) {
            nuevoVector[i] = data[i];
        }
        data = nuevoVector;
    }

    public boolean topIsEqualsTo(String item) throws EmptyStackException {
        return peek().equals(item);
    }

    @Override
    public String toString() {
        String cadena = "";

        if (size > 0) {
            for (int i = size - 1; i > 0; i--) {
                cadena += data[i] + ", ";
            }
            cadena += data[0];
        }

        return "[" + cadena + "]";
    }
}
