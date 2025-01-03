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
public class Deque<E> {

    private DLNode<E> front;
    private DLNode<E> back;
    private int n;

    // construct an empty deque
    public Deque() {
        front = null;
        back = null;
        n = 0;
    }

    // add the item to the front
    public void addFirst(E item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        DLNode<E> newFront = new DLNode<>(item);

        if (front != null) {
            newFront.next = front;
            front.prev = newFront;
        } else {
            back = newFront;
        }

        front = newFront;
        n++;
    }

    // add the item to the back
    public void addLast(E item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        DLNode<E> newBack = new DLNode<>(item);
        newBack.item = item;

        if (front != null) {
            back.next = newBack;
            newBack.prev = back;
        } else {
            front = newBack;
        }

        back = newBack;
        n++;
    }

    // remove and return the item from the front
    public E removeFirst() {
        if (front == null) {
            throw new EmptyQueueException();
        }
        E item = front.item;
        front = front.next;
        n--;

        if (front == null) {
            back = null;
        } else {
            front.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public E removeLast() {
        if (front == null) {
            throw new EmptyQueueException();
        }
        E item = back.item;

        if (front != back) {
            back = back.prev;
            back.next = null;
        } else {
            front = null;
            back = null;
        }

        n--;
        return item;
    }

    public E getFirst() {
        if (front == null) {
            throw new EmptyQueueException();
        }
        return front.item;
    }

    public E getLast() {
        if (front == null) {
            throw new EmptyQueueException();
        }
        return back.item;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return front == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    public void clear() {
        front = null;
        back = null;
        n = 0;
    }

    private class DLNode<E> {

        // Campos (Atributos)
        E item;
        DLNode<E> prev;
        DLNode<E> next;

        // Métodos (Comportamientos)
        public DLNode(E item) {
            this.item = item;
            prev = null;
            next = null;
        }
    }
}
