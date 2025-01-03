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
 * @param <E>
 */
public class MinPriorityQueue<E extends Comparable<E>> {

    private E[] data;
    private int n;

    public MinPriorityQueue() {
        this(1);
    }

    public MinPriorityQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        data = (E[]) new Object[capacity + 1];
        n = 0;
    }

    public MinPriorityQueue(E[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException();
        }
        n = data.length;
        data = (E[]) new Object[n + 1];

        for (int i = 0; i < n; i++) {
            add(values[i]);
        }
    }

    public final void add(E item) {
        if (data.length == n + 1) {
            resize(2 * n);
        }
        data[++n] = item;
        swim(n);
    }

    // Fix when violates heap order (larger key than parent)
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    private boolean greater(int i, int j) {
        return data[i].compareTo(data[j]) > 0;
    }

    private void swap(int i, int j) {
        E aux = data[i];
        data[i] = data[j];
        data[j] = aux;
    }

    // Remove only minimum item
    public E remove() {
        if (n == 0) {
            throw new EmptyQueueException();
        }
        E min = data[1];     // Retrieve max key from top.
        swap(1, n--);        // Exchange with last item.
        data[n + 1] = null;  // Avoid loitering.
        sink(1);

        if (n > 0 && n == (data.length - 1) / 4) {
            resize(data.length / 2);
        }

        return min;
    }

    // Fix when violates heap order (smaller than a child)
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;

            if (j < n && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    private void resize(int capacity) {
        if (capacity > n) {
            E[] newArray = (E[]) new Object[capacity + 1];

            for (int i = 1; i <= n; i++) {
                newArray[i] = data[i];
            }
            data = newArray;
        }
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public int height() {
        if (n == 0) {
            return 0;
        }
        return (int) Math.ceil(Math.log(n) / Math.log(2));
    }

    public String toString() {
        String text = "";

        if (n > 0) {
            for (int i = 1; i < n; i++) {
                text += data[i] + ", ";
            }
            text += data[n];
        }
        return "[" + text + "]";
    }
}
