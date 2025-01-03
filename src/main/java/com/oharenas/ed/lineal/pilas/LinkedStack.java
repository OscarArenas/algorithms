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
 * @param <E>
 */
public class LinkedStack<E> implements StackInterface<E> {

    private Node<E> top;
    private int n;

    public LinkedStack() {
        top = null;
        n = 0;
    }

    @Override
    public void push(E item) {
        top = new Node<>(item, top);
        n++;
    }

    @Override
    public E pop() throws EmptyStackException {
        if (top != null) {
            E item = top.item;
            top = top.next;
            n--;
            return item;
        }
        throw new EmptyStackException();
    }

    @Override
    public E peek() throws EmptyStackException {
        if (top != null) {
            return top.item;
        }
        throw new EmptyStackException();
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void clear() {
        top = null;
        n = 0;
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < n) {
            Node<E> current = top;

            while (index-- > 0) {
                current = current.next;
            }

            return current.item;
        }
        throw new IndexOutOfBoundsException("" + index);
    }

    private class Node<E> {

        // Campos (Atributos)
        E item;
        Node next;

        // Métodos (Comportamientos)
        public Node(E item) {
            this.item = item;
            next = null;
        }

        public Node(E item, Node next) {
            this.item = item;
            this.next = next;
        }
    }
}
