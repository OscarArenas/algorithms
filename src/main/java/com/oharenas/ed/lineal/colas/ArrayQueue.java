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

/**
 *
 * @author Oscar Arenas
 */
public class ArrayQueue<E> {

    private E[] data;
    private int front;
    private int n;

    public ArrayQueue() {
        data = (E[]) new Object[1];
        n = 0;
        front = 0;
    }

    public void enqueue(E item) {
        if (n == data.length) {
            resize(2 * n);
        }
        data[(front + n) % data.length] = item;
        n++;
    }

    public E dequeue() throws EmptyQueueException {
        if (n > 0) {
            E item = data[front];
            front = (front + 1) % data.length;
            n--;
            if (data.length / 4 == n) {
                resize(data.length / 2);
            }
            return item;
        }
        throw new EmptyQueueException();
    }

    public E peek() throws EmptyQueueException {
        if (n > 0) {
            return data[front];
        }
        throw new EmptyQueueException();
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
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
            E[] newArray = (E[]) new Object[nc];

            for (int i = 0; i < n; i++) {
                newArray[i] = data[(front + i) % data.length];
            }
            data = newArray;
            front = 0;
        }
    }

    public void clear() {
        data = (E[]) new Object[1];
        front = 0;
        n = 0;
    }

    public void trimToSize() {
        if (n > 0 && data.length > n) {
            resize(n);
        }
    }

    public E get(int index) {
        if (index >= 0 && index < n) {
            return data[index];
        }
        throw new IndexOutOfBoundsException("" + index);
    }
}
