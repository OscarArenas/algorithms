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
package com.oharenas.ed.jerarquica.arboles;

/**
 *
 * @author Oscar Arenas
 * @param <E>
 */
public class RedBlackTree<E extends Comparable<E>> {

    private BinaryNode<E> root;
    private int n;

    public RedBlackTree() {
        root = null;
        n = 0;
    }

    public boolean add(E item) {
        return false;
    }

    public boolean remove(E item) {
        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    private class BinaryNode<E> {

        E item;
        BinaryNode<E> leftChild;
        BinaryNode<E> rightChild;

        public BinaryNode(E item) {
            this.item = item;
            leftChild = rightChild = null;
        }

        public boolean isLeaf() {
            return leftChild == null && rightChild == null;
        }

        public boolean hasLeftChild() {
            return leftChild != null;
        }

        public boolean hasRightChild() {
            return rightChild != null;
        }
    }
}
