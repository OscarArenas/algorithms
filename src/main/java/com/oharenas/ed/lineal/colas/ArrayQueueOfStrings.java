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
package com.oharenas.ed.lineal.colas;

import com.oharenas.util.EmptyQueueException;
import java.util.NoSuchElementException;

/**
 *
 * @author Oscar Arenas
 */
public class ArrayQueueOfStrings {

    // Fields (Atributos)
    private String[] data;
    private int front;
    private int n;

    // Methods 
    public ArrayQueueOfStrings(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("" + capacity);
        }
        data = new String[capacity];
        front = 0;
        n = 0;
    }

    public ArrayQueueOfStrings() {
        this(1);
    }

    public void enqueue(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (n == data.length) {
            resize(2 * n);
        }
        data[(front + n) % data.length] = item;
        n++;

    }

    public void enqueue(char item) {
        enqueue(item + "");
    }

    public void enqueue(byte item) {
        enqueue(item + "");
    }

    public void enqueue(short item) {
        enqueue(item + "");
    }

    public void enqueue(int item) {
        enqueue(item + "");
    }

    public void enqueue(long item) {
        enqueue(item + "");
    }

    public void enqueue(float item) {
        enqueue(item + "");
    }

    public void enqueue(double item) {
        enqueue(item + "");
    }

    public String dequeue() throws EmptyQueueException {
        if (n > 0) {
            String item = data[front];
            front = (front + 1) % data.length;
            n--;
            if (data.length / 4 == n) {
                resize(data.length / 2);
            }
            return item;
        }
        throw new EmptyQueueException();
    }

    public String peek() throws EmptyQueueException {
        if (n > 0) {
            return data[0];
        }
        throw new EmptyQueueException();
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean isNumber() throws NoSuchElementException {
        if (n > 0) {// Falta \\.[0-9]+
            String numberPattern = "(\\+|-)?[0-9]+(\\.[0-9]*)?";

            return data[0].matches(numberPattern);
        }
        throw new NoSuchElementException();
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
                newArray[i] = data[(front + i) % data.length];
            }
            data = newArray;
            front = 0;
        }
    }

    public boolean topEqualsTo(String item) throws EmptyQueueException {
        if (item == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        return peek().compareTo(item) == 0;
    }

    public void clear() {
        data = new String[1];
        front = 0;
        n = 0;
    }

    public void trimToSize() {
        if (n > 0 && data.length > n) {
            resize(n);
        }
    }

    public String get(int index) {
        if (index >= 0 && index < n) {
            return data[index];
        }
        throw new IndexOutOfBoundsException("" + index);
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
