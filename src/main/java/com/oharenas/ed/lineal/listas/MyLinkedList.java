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
public class MyLinkedList<E> implements IList<E>{

    // Campos (Atributos)
    private Nodo<E> primerNodo;
    private Nodo<E> ultimoNodo;
    private int n;

    // Métodos (Comportamientos)
    public MyLinkedList() {
        primerNodo = null;
        ultimoNodo = null;
        n = 0;
    }

    /**
     * Inserta un element al final de la lista.
     *
     * @param element Número entero para almacenar al final de la lista.
     *
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        Nodo<E> nuevoNodo = new Nodo<>(element);

        if (primerNodo != null) {
            ultimoNodo.siguiente = nuevoNodo;
        } else {
            primerNodo = nuevoNodo;
        }
        ultimoNodo = nuevoNodo;
        n++;
    }

    @Override
    public boolean add(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (index >= 0 && index <= n) {
            if (index == n) {
                add(element);
            } else {
                Nodo<E> actual = primerNodo;
                Nodo<E> anterior = null;
                int i = 0;

                while (i < index) {
                    i++;
                    anterior = actual;
                    actual = actual.siguiente;
                }
                Nodo<E> nuevoNodo = new Nodo<>(element, actual);

                if (anterior == null) {
                    primerNodo = nuevoNodo;
                } else {
                    anterior.siguiente = nuevoNodo;
                }
                n++;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(int index) {
        if (index >= 0 && index < n) {
            Nodo<E> actual = primerNodo;
            Nodo<E> anterior = null;

            while (index-- > 0) {
                anterior = actual;
                actual = actual.siguiente;
            }
            if (anterior == null) {
                primerNodo = actual.siguiente;
            } else {
                anterior.siguiente = actual.siguiente;
            }
            if (actual == ultimoNodo) {
                ultimoNodo = anterior;
            }
            n--;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        Nodo<E> actual = primerNodo;
        Nodo<E> anterior = null;

        while (actual != null && !element.equals(actual.element)) {
            anterior = actual;
            actual = actual.siguiente;
        }

        if (actual != null) {
            if (anterior == null) {
                primerNodo = actual.siguiente;
            } else {
                anterior.siguiente = actual.siguiente;
            }
            if (actual == ultimoNodo) {
                ultimoNodo = anterior;
            }
            n--;
            return true;
        }
        return false;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            Nodo<E> actual = primerNodo;

            while (index-- > 0) {
                actual = actual.siguiente;
            }

            return actual.element;
        }
        throw new IndexOutOfBoundsException("No existe el índice: " + index);
    }

    @Override
    public boolean set(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (index >= 0 && index < n) {
            Nodo<E> actual = primerNodo;

            while (index-- > 0) {
                actual = actual.siguiente;
            }
            actual.element = element;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        primerNodo = null;
        ultimoNodo = null;
        n = 0;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public int size() {
        return n;
    }

    private class Nodo<E> {

        // Campos (Atributos)
        E element;
        Nodo siguiente;

        // Métodos (Comportamientos)
        public Nodo(E element) {
            this.element = element;
            siguiente = null;
        }

        public Nodo(E element, Nodo siguiente) {
            this.element = element;
            this.siguiente = siguiente;
        }
    }
}
