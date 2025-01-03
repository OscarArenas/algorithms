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
package com.oharenas.ed.lineal.pilas;

import java.util.EmptyStackException;

/**
 *
 * @author Oscar Arenas
 */
public class ArrayStackOfStrings {

    // Fields (Atributos)
    private String[] data;
    private int n;

    // Methods 
    public ArrayStackOfStrings(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("" + capacity);
        }
        data = new String[capacity];
        n = 0;
    }

    public ArrayStackOfStrings() {
        this(1);
    }

    public void push(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (n == data.length) {
            resize(2 * n);
        }
        data[n++] = item;

    }

    public void push(char item) {
        push(item + "");
    }

    public void push(byte item) {
        push(item + "");
    }

    public void push(short item) {
        push(item + "");
    }

    public void push(int item) {
        push(item + "");
    }

    public void push(long item) {
        push(item + "");
    }

    public void push(float item) {
        push(item + "");
    }

    public void push(double item) {
        push(item + "");
    }

    public String pop() throws EmptyStackException {
        if (n > 0) {
            n--;
            String item = data[n];
            data[n] = null;

            if (n > 0 && data.length / 4 == n) {
                resize(data.length / 2);
            }
            return item;
        }
        throw new EmptyStackException();
    }

    public String peek() throws EmptyStackException {
        if (n > 0) {
            return data[n - 1];
        }
        throw new EmptyStackException();
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean isNumber() throws EmptyStackException {
        if (n > 0) {// Falta \\.[0-9]+
            String numberPattern = "(\\+|-)?[0-9]+(\\.[0-9]*)?";

            return data[n - 1].matches(numberPattern);
        }
        throw new EmptyStackException();
    }

    /**
     * Cambia el tamaño del vector interno. El tamaño del vector interno
     * determina la capacidad de almacenamiento de la lista. La capacidad
     * siempre es mayor que cero, y mayor o igual al tamaño de la lista.
     *
     * @param nc entero que indica el nuevo tamaño del vector interno. El
     * parámetro nc es la abreviación de nueva capacidad.
     */
    private void resize(int nc) {
        if (nc > 0 && nc >= n) {
            String[] newArray = new String[nc];

            for (int i = 0; i < n; i++) {
                newArray[i] = data[i];
            }
            data = newArray;
        }
    }

    public boolean topEqualsTo(String item) throws EmptyStackException {
        if (item == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        return peek().compareTo(item) == 0;
    }

    public void clear() {
        data = new String[1];
        n = 0;
    }

    @Override
    public String toString() {
        String text = "";

        if (n > 0) {
            for (int i = 0; i < n - 1; i++) {
                text += data[i] + ", ";
            }
            text += data[n - 1];
        }

        return "[" + text + "]";
    }
}
