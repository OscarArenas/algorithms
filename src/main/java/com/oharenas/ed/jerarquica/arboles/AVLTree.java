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

import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 * @param <E>
 */
public class AVLTree<E extends Comparable<E>> {

    private AVLTreeNode<E> root;
    private int n;

    public boolean add(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (root != null) {
            AVLTreeNode<E> current = root;
            AVLTreeNode<E> parent = null;
            while (current != null) {
                if (item.compareTo(current.item) < 0) {
                    parent = current;
                    current = current.leftChild;
                } else if (item.compareTo(current.item) > 0) {
                    parent = current;
                    current = current.rightChild;
                } else {// No se permiten datos repetidos
                    return false;
                }

            }// Fin del WHILE 
            if (item.compareTo(parent.item) < 0) {
                parent.leftChild = new AVLTreeNode<>(item);
            } else {
                parent.rightChild = new AVLTreeNode<>(item);
            }
            balancePath(item);
        } else {
            root = new AVLTreeNode<>(item);
        }
        n++;
        return true;
    }

    public boolean remove(E item) {
        if (root == null || item == null) {
            return false;
        }

        AVLTreeNode<E> current = root;
        AVLTreeNode<E> parent = null;

        while (current != null) {
            int comparador = item.compareTo(current.item);
            if (comparador < 0) {
                parent = current;
                current = current.leftChild;
            } else if (comparador > 0) {
                parent = current;
                current = current.rightChild;
            } else {
                break;
            }
        }// Fin WHILE

        if (current == null) {
            return false;
        }
        // Case 1: current has no left children
        if (!current.hasLeftChild()) {
            if (parent == null) {
                root = current.rightChild;
            } else {
                if (item.compareTo(parent.item) < 0) {
                    parent.leftChild = current.rightChild;
                } else {
                    parent.rightChild = current.rightChild;
                }
                balancePath(parent.item);
            }
        } else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            AVLTreeNode<E> parentOfRightMost = current;
            AVLTreeNode<E> rightMost = current.leftChild;

            while (rightMost.hasRightChild()) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.rightChild;
            }
            // Replace the element in current by the element in rightMost
            current.item = rightMost.item;

            // Eliminate rightmost node
            if (parentOfRightMost.rightChild == rightMost) {
                parentOfRightMost.rightChild = rightMost.leftChild;
            } else {// Special case: parentOfRightMost is current
                parentOfRightMost.leftChild = rightMost.leftChild;
            }

            // Balance the tree if necessary
            balancePath(parentOfRightMost.item);
        }
        n--;
        return true;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void clear() {
        root = null;
        n = 0;
    }

    public int size() {
        return n;
    }

    private void balancePath(E key) {
        ArrayList<AVLTreeNode<E>> lista = path(key);
        int indiceUltimo = lista.size() - 1;

        for (int i = indiceUltimo; i >= 0; i--) {
            AVLTreeNode<E> nodeA = lista.get(i);
            nodeA.updateHeight();

            AVLTreeNode<E> parentA = null;

            if (nodeA != root) {
                parentA = lista.get(i - 1);
            }

            if (nodeA.balanceFactor() == -2) {
                if (nodeA.leftChild.balanceFactor() <= 0) {
                    balanceLL(nodeA, parentA);
                } else {
                    balanceLR(nodeA, parentA);
                }
            } else if (nodeA.balanceFactor() == 2) {
                if (nodeA.rightChild.balanceFactor() >= 0) {
                    balanceRR(nodeA, parentA); // Ejecuta rotacion RR
                } else {
                    balanceRL(nodeA, parentA); // Ejecuta rotacion RL
                }
            }
        }
    }

    private void balanceLL(AVLTreeNode<E> nodeA, AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> nodeB = nodeA.leftChild; // A is left-heavy and B is left-heavy

        if (nodeA == root) {
            root = nodeB;
        } else if (parentOfA.leftChild == nodeA) {
            parentOfA.leftChild = nodeB;
        } else {
            parentOfA.rightChild = nodeB;
        }

        nodeA.leftChild = nodeB.rightChild; // Make T2 the left subtree of A
        nodeB.rightChild = nodeA; // Make A the left child of B

        nodeA.updateHeight();
        nodeB.updateHeight();
    }

    private void balanceLR(AVLTreeNode<E> nodeA, AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> nodeB = nodeA.leftChild; // A is left-heavy
        AVLTreeNode<E> nodeC = nodeB.rightChild; // B is right-heavy

        if (nodeA == root) {
            root = nodeC;
        } else if (parentOfA.leftChild == nodeA) {
            parentOfA.leftChild = nodeC;
        } else {
            parentOfA.rightChild = nodeC;
        }

        nodeA.leftChild = nodeC.rightChild; // Make T3 the left subtree of A
        nodeB.rightChild = nodeC.leftChild; // Make T2 the right subtree of B
        nodeC.leftChild = nodeB;
        nodeC.rightChild = nodeA;

        // Adjust heights
        nodeA.updateHeight();
        nodeB.updateHeight();
        nodeC.updateHeight();
    }

    private void balanceRR(AVLTreeNode<E> nodeA, AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> nodeB = nodeA.rightChild; // A is right-heavy and B is right-heavy

        if (nodeA == root) {
            root = nodeB;
        } else if (parentOfA.leftChild == nodeA) {
            parentOfA.leftChild = nodeB;
        } else {
            parentOfA.rightChild = nodeB;
        }

        nodeA.rightChild = nodeB.leftChild; // Make T2 the right subtree of A
        nodeB.leftChild = nodeA;
        nodeA.updateHeight();
        nodeB.updateHeight();
    }

    private void balanceRL(AVLTreeNode<E> nodeA, AVLTreeNode<E> parentOfA) {
        AVLTreeNode<E> nodeB = nodeA.rightChild; // A is right-heavy
        AVLTreeNode<E> nodeC = nodeB.leftChild; // B is left-heavy

        if (nodeA == root) {
            root = nodeC;
        } else if (parentOfA.leftChild == nodeA) {
            parentOfA.leftChild = nodeC;
        } else {
            parentOfA.rightChild = nodeC;
        }

        nodeA.rightChild = nodeC.leftChild; // Make T2 the right subtree of A
        nodeB.leftChild = nodeC.rightChild; // Make T3 the left subtree of B
        nodeC.leftChild = nodeA;
        nodeC.rightChild = nodeB;

        // Adjust heights
        nodeA.updateHeight();
        nodeB.updateHeight();
        nodeC.updateHeight();
    }

    private ArrayList<  AVLTreeNode<E>> path(E key) {
        ArrayList<  AVLTreeNode<E>> lista = new ArrayList<>();

        AVLTreeNode<E> current = root;

        while (current != null) {
            lista.add(current);
            int comp = key.compareTo(current.item);

            if (comp < 0) {
                current = current.leftChild;
            } else if (comp > 0) {
                current = current.rightChild;
            } else {
                break;
            }
        }

        if (current == null) {
            lista.clear();
        }

        return lista;
    }

    private class AVLTreeNode<E> {

        E item;
        AVLTreeNode<E> leftChild;
        AVLTreeNode<E> rightChild;
        int height;

        public AVLTreeNode(E item) {
            this.item = item;
            leftChild = rightChild = null;
            height = 1;
        }

        boolean isLeaf() {
            return leftChild == null && rightChild == null;
        }

        boolean hasLeftChild() {
            return leftChild != null;
        }

        public boolean hasRightChild() {
            return rightChild != null;
        }

        void updateHeight() {
            if (isLeaf()) {
                height = 1;
            } else if (rightChild == null) {
                height = 1 + leftChild.height;
            } else if (leftChild == null) {
                height = 1 + rightChild.height;
            } else {
                height = 1 + Math.max(leftChild.height, rightChild.height);
            }
        }

        int balanceFactor() {
            if (isLeaf()) {
                return 0;
            } else if (rightChild == null) {
                return -leftChild.height;
            } else if (leftChild == null) {
                return rightChild.height;
            } else {
                return rightChild.height - leftChild.height;
            }
        }
    }
}
