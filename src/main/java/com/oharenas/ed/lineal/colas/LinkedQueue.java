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
public class LinkedQueue<E> {

    // Campos (Atributos)
    private Node<E> front;
    private Node<E> back;
    private int n;

    // Métodos
    public LinkedQueue() {
        front = null;
        back = null;
        n = 0;
    }

    public void enqueue(E item) {
        Node<E> newNode = new Node<>(item);

        if (front == null) {
            front = newNode;
        } else {
            back.next = newNode;
        }

        back = newNode;
        n++;
    }

    public E dequeue() throws EmptyQueueException {
        if (front != null) {
            E item = front.item;
            front = front.next;
            n--;
            if (front == null) {
                back = null;
            }
            return item;
        }
        throw new EmptyQueueException();
    }

    public E peek() throws EmptyQueueException {
        if (front != null) {
            return front.item;
        }
        throw new EmptyQueueException();
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return n;
    }

    public void clear() {
        front = null;
        back = null;
        n = 0;
    }

    public E get(int index) {
        if (index >= 0 && index < n) {
            Node<E> current = front;

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
