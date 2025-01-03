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

import com.oharenas.ed.lineal.colas.ArrayQueue;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author Oscar Arenas
 * @param <Key>
 * @param <Value>
 */
public class SymbolTable<Key extends Comparable<Key>, Value> {

    private Node<Key, Value> root;
    private int n;

    public SymbolTable() {
        root = null;
        n = 0;
    }

    public void put(Key key, Value value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (root != null) {
            Node<Key, Value> current = root;
            Node<Key, Value> parent = null;

            while (current != null) {
                int comp = key.compareTo(current.key);

                if (comp < 0) {
                    parent = current;
                    current = current.left;
                } else if (comp > 0) {
                    parent = current;
                    current = current.right;
                } else {
                    current.value = value;
                    return;
                }
            }
            if (key.compareTo(parent.key) < 0) {
                parent.left = new Node<>(key, value);
            } else {
                parent.right = new Node<>(key, value);
            }
            balancePath(key);
        } else {
            root = new Node<>(key, value);
        }
        n++;
    }

    public void put(Key[] keys, Value[] values) {
        if (keys == null || values == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Arrays with diferent sizes");
        }
        for (int i = 0; i < keys.length; i++) {
            put(keys[i], values[i]);
        }
    }

    public boolean remove(Key key) {
        if (root == null || key == null) {
            return false;
        }

        Node<Key, Value> current = root;
        Node<Key, Value> parent = null;

        while (current != null) {
            int comparator = key.compareTo(current.key);

            if (comparator < 0) {
                parent = current;
                current = current.left;
            } else if (comparator > 0) {
                parent = current;
                current = current.right;
            } else {
                break;
            }
        }

        if (current == null) {
            return false;
        }
        // Caso 1: El actual no tiene hijo izquierdo
        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else {
                if (key.compareTo(parent.key) < 0) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
                //  Balancear si es necesario
                balancePath(parent.key);
            }
        } else {// Caso 2: El actual tiene hijo izquierdo
            Node<Key, Value> parentOfRightMost = current;
            Node<Key, Value> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            // Replazar la clave y el valor
            current.key = rightMost.key;
            current.value = rightMost.value;

            // Eliminar el rightMost
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else {// Caso especial: left no tiene hijo derecho
                parentOfRightMost.left = rightMost.left;
            }

            // Balancear si es necesario
            balancePath(parentOfRightMost.key);
        }
        n--;
        return true;
    }

    public boolean contains(Key key) {
        if (key == null) {
            return false;
        }
        Node<Key, Value> current = root;

        while (current != null) {
            int comp = key.compareTo(current.key);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public Key min() {
        if (root != null) {
            Node<Key, Value> current = root;
            while (current.left != null) {
                current = current.left;
            }
            return current.key;
        }
        throw new NoSuchElementException();
    }

    public Key max() {
        if (root != null) {
            Node<Key, Value> current = root;
            while (current.right != null) {
                current = current.right;
            }
            return current.key;
        }
        throw new NoSuchElementException();
    }

    public Value get(Key key) {
        Node<Key, Value> current = root;

        while (current != null) {
            int comp = key.compareTo(current.key);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public int size() {
        return n;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<Key, Value> r) {
        if (r != null) {
            return 1 + Math.max(height(r.left), height(r.right));
        }
        return 0;
    }

    public int rank(Key key) {
        Node<Key, Value> current = root;

        while (current != null) {
            int comp = key.compareTo(current.key);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                return count(current) - 1;
            }
        }
        return 0;
    } //number of keys less than key

    public Key select(int k) {
        return null;
    } // kth smallest key in symbol table

    // largest key less than or equal to key
    // encuentre la clave más grande que sea menor o igual que la clave dada
    public Key floor(Key key) {
        Node<Key, Value> current = root;

        while (current != null) {
            int comp = key.compareTo(current.key);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                if (current.left != null) {
                    current = current.left;
                    while (current.right != null) {
                        current = current.right;
                    }
                }
                return current.key;
            }
        }
        return null;
    }

    // smallest key greater than or equal to key
    //encuentre la clave más pequeña que sea mayor o igual que la clave dada
    public Key ceiling(Key key) {
        Node<Key, Value> current = root;

        while (current != null) {
            int comp = key.compareTo(current.key);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                if (current.right != null) {
                    current = current.right;
                    while (current.left != null) {
                        current = current.left;
                    }
                }
                return current.key;
            }
        }
        return null;
    }

    public void clear() {
        root = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private int count(Node<Key, Value> r) {
        if (r != null) {
            return 1 + count(r.left) + count(r.right);
        }
        return 0;
    }

    private void balancePath(Key key) {
        ArrayList<Node<Key, Value>> lista = path(key);
        int indiceUltimo = lista.size() - 1;

        for (int i = indiceUltimo; i >= 0; i--) {
            Node<Key, Value> nodoA = lista.get(i);
            nodoA.updateHeight();

            Node<Key, Value> fatherA = null;

            if (nodoA != root) {
                fatherA = lista.get(i - 1);
            }

            if (nodoA.factorBalance() == -2) {
                if (nodoA.left.factorBalance() <= 0) {
                    balanceLL(nodoA, fatherA);
                } else {
                    balanceLR(nodoA, fatherA);
                }
            } else if (nodoA.factorBalance() == 2) {
                if (nodoA.right.factorBalance() >= 0) {
                    balanceRR(nodoA, fatherA); // Ejecuta rotacion RR
                } else {
                    balanceRL(nodoA, fatherA); // Ejecuta rotacion RL
                }
            }
        }
    }

    private void balanceLL(Node<Key, Value> nodoA, Node<Key, Value> fatherDeA) {
        Node<Key, Value> nodoB = nodoA.left; // A is left-heavy and B is left-heavy

        if (nodoA == root) {
            root = nodoB;
        } else if (fatherDeA.left == nodoA) {
            fatherDeA.left = nodoB;
        } else {
            fatherDeA.right = nodoB;
        }

        nodoA.left = nodoB.right; // Make T2 the left subtree of A
        nodoB.right = nodoA; // Make A the left child of B

        nodoA.updateHeight();
        nodoB.updateHeight();
    }

    private void balanceLR(Node<Key, Value> nodoA, Node<Key, Value> fatherDeA) {
        Node<Key, Value> nodoB = nodoA.left; // A is left-heavy
        Node<Key, Value> nodoC = nodoB.right; // B is right-heavy

        if (nodoA == root) {
            root = nodoC;
        } else if (fatherDeA.left == nodoA) {
            fatherDeA.left = nodoC;
        } else {
            fatherDeA.right = nodoC;
        }

        nodoA.left = nodoC.right; // Make T3 the left subtree of A
        nodoB.right = nodoC.left; // Make T2 the right subtree of B
        nodoC.left = nodoB;
        nodoC.right = nodoA;

        // Adjust heights
        nodoA.updateHeight();
        nodoB.updateHeight();
        nodoC.updateHeight();
    }

    private void balanceRR(Node<Key, Value> nodoA, Node<Key, Value> fatherDeA) {
        Node<Key, Value> nodoB = nodoA.right; // A is right-heavy and B is right-heavy

        if (nodoA == root) {
            root = nodoB;
        } else if (fatherDeA.left == nodoA) {
            fatherDeA.left = nodoB;
        } else {
            fatherDeA.right = nodoB;
        }

        nodoA.right = nodoB.left; // Make T2 the right subtree of A
        nodoB.left = nodoA;
        nodoA.updateHeight();
        nodoB.updateHeight();
    }

    private void balanceRL(Node<Key, Value> nodoA, Node<Key, Value> fatherDeA) {
        Node<Key, Value> nodoB = nodoA.right; // A is right-heavy
        Node<Key, Value> nodoC = nodoB.left; // B is left-heavy

        if (nodoA == root) {
            root = nodoC;
        } else if (fatherDeA.left == nodoA) {
            fatherDeA.left = nodoC;
        } else {
            fatherDeA.right = nodoC;
        }

        nodoA.right = nodoC.left; // Make T2 the right subtree of A
        nodoB.left = nodoC.right; // Make T3 the left subtree of B
        nodoC.left = nodoA;
        nodoC.right = nodoB;

        // Adjust heights
        nodoA.updateHeight();
        nodoB.updateHeight();
        nodoC.updateHeight();
    }

    private ArrayList<Node<Key, Value>> path(Key key) {
        ArrayList<Node<Key, Value>> lista = new ArrayList<>();

        Node<Key, Value> current = root;

        while (current != null) {
            lista.add(current);
            int comp = key.compareTo(current.key);

            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                break;
            }
        }

        if (current == null) {
            lista.clear();
        }

        return lista;
    }

    // Recorre por nivel, cambiar para recorrer inorder
    public Iterable<Key> dict() {
        ArrayList<Key> lista = new ArrayList<>();

        if (root != null) {
            ArrayQueue<Node<Key, Value>> cola = new ArrayQueue<>();
            Node<Key, Value> current = root;

            cola.enqueue(current);

            while (!cola.isEmpty()) {
                current = cola.dequeue();
                lista.add(current.key);

                if (current.left != null) {
                    cola.enqueue(current.left);
                }
                if (current.right != null) {
                    cola.enqueue(current.right);
                }
            }
        }
        return lista;
    }

    public Iterable<Key> keys() {
        ArrayList<Key> lista = new ArrayList<>();
        keys(root, lista);

        return lista;
    }

    private void keys(Node<Key, Value> r, ArrayList<Key> lista) {
        if (r != null) {
            keys(r.left, lista);
            lista.add(r.key);
            keys(r.right, lista);
        }
    }

    private class Node<Key, Value> {

        Key key;
        Value value;
        //
        Node<Key, Value> left;
        Node<Key, Value> right;
        int height;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

        void updateHeight() {
            if (isLeaf()) {
                height = 1;
            } else if (right == null) {
                height = 1 + left.height;
            } else if (left == null) {
                height = 1 + right.height;
            } else {
                height = 1 + Math.max(left.height, right.height);
            }
        }

        int factorBalance() {
            if (isLeaf()) {
                return 0;
            } else if (right == null) {
                return -left.height;
            } else if (left == null) {
                return right.height;
            } else {
                return right.height - left.height;
            }
        }
    }
}
