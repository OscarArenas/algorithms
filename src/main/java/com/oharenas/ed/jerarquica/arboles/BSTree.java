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

import com.oharenas.aleatorio.Aleatorio;
import com.oharenas.ed.lineal.colas.ArrayQueue;

/**
 *
 * @author Oscar Arenas
 * @param <E>
 */
public class BSTree<E extends Comparable<E>> {

    private BinaryNode<E> root;
    private int n;

    public BSTree() {
        root = null;
        n = 0;
    }

    public boolean add(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Null argument.");
        }

        BinaryNode<E> current = root;
        BinaryNode<E> parent = null;

        while (current != null) {
            parent = current;
            if (item.compareTo(current.item) < 0) {
                current = current.leftChild;
            } else if (item.compareTo(current.item) > 0) {
                current = current.rightChild;
            } else {
                return false; // No se permiten datos repetidos
            }
        }
        BinaryNode<E> newBinaryNode = createNewNode(item);

        if (parent == null) {
            root = newBinaryNode;
        } else if (item.compareTo(parent.item) < 0) {
            parent.leftChild = newBinaryNode;
        } else {
            parent.rightChild = newBinaryNode;
        }
        n++;
        return true;
    }

//    public boolean insert(E item) {
//        int a = n;
//        if (root != null) {
//            insert(root, item);
//        }
//
//        n++;
//        root = createNewNode(item);
//        return a != n;
//    }
//
//    private BinaryNode<E> insert(BinaryNode<E> r, E item) {
//        if (r != null) {
//            int comp = item.compareTo(r.item);
//            if (comp < 0) {
//                r.leftChild = insert(r.leftChild, item);
//            } else if (comp > 0) {
//                r.rightChild = insert(r.rightChild, item);
//            }
//            return r;
//        } else {
//            n++;
//        }
//        return new BinaryNode<>(item);
//    }

    private BinaryNode<E> createNewNode(E item) {
        return new BinaryNode<>(item);
    }

    public boolean remove(E item) {
        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return n;
    }

    public void preorden() {
        preorden(root);
        System.out.println();
    }

    private void preorden(BinaryNode<E> r) {
        if (r != null) {
            System.out.print(r.item + " ");
            preorden(r.leftChild);
            preorden(r.rightChild);
        }
    }

    public void inorden() {
        inorden(root);
        System.out.println();
    }

    private void inorden(BinaryNode<E> r) {
        if (r != null) {
            inorden(r.leftChild);
            System.out.print(r.item + " ");
            inorden(r.rightChild);
        }
    }

    public void postorden() {
        postorden(root);
        System.out.println();
    }

    private void postorden(BinaryNode<E> r) {
        if (r != null) {
            postorden(r.leftChild);
            postorden(r.rightChild);
            System.out.print(r.item + " ");
        }
    }

    public void levelOrden() {
        if (root != null) {
            ArrayQueue<BinaryNode> q = new ArrayQueue<>();
            q.enqueue(root);

            while (!q.isEmpty()) {
                BinaryNode<E> node = q.dequeue();

                System.out.print(node.item + " ");

                if (node.leftChild != null) {
                    q.enqueue(node.leftChild);
                }
                if (node.rightChild != null) {
                    q.enqueue(node.rightChild);
                }
            }
            System.out.println();
        }
    }

    public int countLeaf() {
        return countLeaf(root);
    }

    private int countLeaf(BinaryNode<E> r) {
        if (r != null) {
            if (r.isLeaf()) {
                return 1;
            }
            return countLeaf(r.leftChild) + countLeaf(r.rightChild);
        }
        return 0;
    }

    public int height() {
        return height(root);
    }

    private int height(BinaryNode<E> r) {
        if (r != null) {
            return 1 + Math.max(height(r.leftChild), height(r.rightChild));
        }
        return 0;
    }

    public int[] levelNodes() {
        int[] nodes = null;

        if (root != null) {
            nodes = new int[height()];

            levelNodes(root, nodes, 0);
        }

        return nodes;
    }

    private void levelNodes(BinaryNode<E> r, int[] nodes, int level) {
        if (r != null) {
            nodes[level]++;
            levelNodes(r.leftChild, nodes, level + 1);
            levelNodes(r.rightChild, nodes, level + 1);
        }
    }

    public void print() {
        if (root.item instanceof Integer) {
            print(root, "");
        }
    }

    private void print(BinaryNode<E> r, String espacios) {
        if (r != null) {
            print(r.rightChild, espacios + "   ");
            System.out.println(espacios + r.item);
            print(r.leftChild, espacios + "   ");
        }
    }

    public static BSTree<Integer> createRandomBSTree(int n) {
        BSTree<Integer> bst = new BSTree<>();

        int i = n;
        while (i > 0) {
            int item = Aleatorio.entero(10 * n);
            if (bst.add(item)) {
                i--;
            }
        }
        return bst;
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
