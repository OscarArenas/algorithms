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
package com.oharenas.ed.lineal.listas;

/**
 *
 * @author Oscar Arenas
 * @param <E>
 */
public class MyArrayList<E> implements IList<E> {

    private E[] datos;
    private int n;
    private static int CAPACIDAD_INICIAL = 8;

    // Métodos (Comportamientos)
    public MyArrayList() {
        datos = (E[]) new Object[CAPACIDAD_INICIAL];
        n = 0;
    }

    public MyArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        datos = (E[]) new Object[capacity];
        n = 0;
    }

    public MyArrayList(Iterable<E> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        for (E dato : data) {
            add(dato);
        }
    }

    @Override
    public final void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (datos.length == n) {
            resize(2 * n);
        }
        datos[n++] = element;
    }

    @Override
    public boolean add(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (index >= 0 && index <= n) {
            if (datos.length == n) {
                resize(2 * n);
            }
            for (int i = n; i > index; i--) {
                datos[i] = datos[i - 1];
            }
            datos[index] = element;
            n++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < n; i++) {
            if (datos[i].equals(element)) {
                n--;
                for (; i < n; i++) {
                    datos[i] = datos[i + 1];
                }
                if (datos.length / 4 == n) {
                    resize(datos.length / 2);
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public boolean remove(int index) {
        if (index >= 0 && index < n) {
            n--;
            for (int i = index; i < n; i++) {
                datos[i] = datos[i + 1];
            }
            if (datos.length / 4 == n) {
                resize(datos.length / 2);
            }
            return true;
        }
        return false;
    }

    private void resize(int nc) {
        if (nc > 0 && nc >= n) {
            E[] nuevoVector = (E[]) new Object[nc];

            for (int i = 0; i < n; i++) {
                nuevoVector[i] = datos[i];
            }
            datos = nuevoVector;
        }
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void clear() {
        datos = (E[]) new Object[CAPACIDAD_INICIAL];
        n = 0;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            return datos[index];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean set(int index, E element) {
        if (element != null && index >= 0 && index < n) {
            datos[index] = element;
            return true;
        }
        return false;
    }
}
