/*
 * Copyright (C) 2021 Oscar Arenas
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

import java.util.Iterator;

/**
 *
 * @author Oscar Arenas
 */
public class CharacterLinkedList implements Iterable<Character> {

    private Nodo primerNodo;
    private Nodo ultimoNodo;
    private int n;

    public CharacterLinkedList() {
        primerNodo = null;
        ultimoNodo = null;
        n = 0;
    }

    /**
     * Inserta un element entero al final de la lista.
     *
     * @param element Número entero para almacenar al final de la lista.
     *
     */
    public void add(char element) {
        Nodo nuevoNodo = new Nodo(element);

        if (primerNodo != null) {
            ultimoNodo.siguiente = nuevoNodo;
        } else {
            primerNodo = nuevoNodo;
        }
        ultimoNodo = nuevoNodo;
        n++;
    }

    public boolean add(int index, char element) {
        if (index >= 0 && index <= n) {
            if (index == n) {
                add(element);
            } else {
                Nodo actual = primerNodo;
                Nodo anterior = null;

                while (index-- > 0) {
                    anterior = actual;
                    actual = actual.siguiente;
                }
                Nodo nuevoNodo = new Nodo(element, actual);

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

    public boolean remove(int index) {
        if (index >= 0 && index < n) {
            Nodo actual = primerNodo;
            Nodo anterior = null;

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

    public boolean removeElement(char element) {
        Nodo actual = primerNodo;
        Nodo anterior = null;

        while (actual != null && actual.element != element) {
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

    public double get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            Nodo actual = primerNodo;

            while (index-- > 0) {
                actual = actual.siguiente;
            }
            return actual.element;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
    }

    @Override
    public String toString() {
        String cadena = "";

        if (primerNodo != null) {
            Nodo actual = primerNodo;
            cadena += actual.element;
            actual = actual.siguiente;

            while (actual != null) {
                cadena += ", " + actual.element;
                actual = actual.siguiente;
            }
        }
        return "[" + cadena + "]";
    }

    public boolean set(int index, char element) {
        if (index >= 0 && index < n) {
            Nodo actual = primerNodo;

            while (index-- > 0) {
                actual = actual.siguiente;
            }

            actual.element = element;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(char element) {
        Nodo actual = primerNodo;
        while (actual != null) {
            if (actual.element == element) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public int size() {
        return n;
    }

    private class Nodo {

        char element;
        Nodo siguiente;

        Nodo(char element) {
            this.element = element;
            siguiente = null;
        }

        Nodo(char element, Nodo siguiente) {
            this.element = element;
            this.siguiente = siguiente;
        }
    }

    @Override
    public Iterator<Character> iterator() {
        return new MiIterador();
    }

    private class MiIterador implements Iterator<Character> {

        private Nodo actual;
        private Nodo anterior;

        public MiIterador() {
            actual = primerNodo;
            anterior = null;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public Character next() {
            anterior = actual;
            actual = actual.siguiente;
            return anterior.element;
        }
    }
}
