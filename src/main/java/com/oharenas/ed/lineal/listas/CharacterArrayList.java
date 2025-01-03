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
public class CharacterArrayList implements Iterable<Character> {

    private char[] caracteres;
    private int n;

    public CharacterArrayList() {
        caracteres = new char[1];
        n = 0;
    }

    public CharacterArrayList(int capacidadInicial) throws IllegalArgumentException {
        if (capacidadInicial < 1) {
            throw new IllegalArgumentException("Argumento no válido " + capacidadInicial);
        }
        caracteres = new char[capacidadInicial];
        n = 0;
    }

    public CharacterArrayList(char[] caracteres) throws IllegalArgumentException {
        if (caracteres == null) {
            throw new IllegalArgumentException("Argumento null");
        }
        if (caracteres.length > 0) {
            this.caracteres = caracteres.clone();
            n = caracteres.length;
        } else {
            this.caracteres = new char[1];
            n = 0;
        }
    }

    public CharacterArrayList(String string) {
        if (string != null) {
            caracteres = string.toCharArray().clone();
            n = string.length();
        } else {
            caracteres = new char[1];
            n = 0;
        }
    }

    /**
     * Adiciona el caracter especificado al final de la lista. Permite almacenar
     * caracteres repetidos.
     *
     * @param character valor que se agregará al final de esta lista
     */
    public void add(char character) {
        if (caracteres.length == n) {
            resize(2 * n);
        }
        caracteres[n++] = character;
    }

    /**
     * Borra de la lista el caracter ubicado en el índice especificado. El
     * borrado se realiza desplazando todos los caracteres que estan después del
     * ubicado en el índice especificado una posición a la izquierda.
     *
     * @param index Entero que indica la posición del caracter a borrar
     * @return Retorna true si elimina el caracter y false en caso contrario
     */
    public boolean remove(int index) {
        if (index >= 0 && index < n) {
            n--;
            while (index < n) {
                caracteres[index] = caracteres[index + 1];
                index++;
            }
            if (caracteres.length / 4 == n) {
                resize(caracteres.length / 2);
            }
            return true;
        }
        return false;
    }

    public boolean remove(char character) {
        for (int i = 0; i < n; i++) {
            if (caracteres[i] == character) {
                return remove(i);
            }
        }
        return false;
    }

    /**
     * Borra de la lista el caracter ubicado en el índice especificado.
     *
     * @param index Entero que indica la posición del caracter a borrar.
     * @return Retorna el elemento que se borró de esta lista.
     * @throws IndexOutOfBoundsException Lanza la excepción en el caso que no
     * exista el índice.
     */
    public char delete(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            char dato = caracteres[index];
            n--;
            while (index < n) {
                caracteres[index] = caracteres[index + 1];
                index++;
            }
            if (caracteres.length / 4 == n) {
                resize(caracteres.length / 2);
            }
            return dato;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
    }

    /**
     * Cambia el tamaño del vector interno. El tamaño del vector interno
     * determina la capacidad de almacenamiento de la lista. La capacidad
     * siempre es mayor que cero, y mayor o igual al tamaño de la lista.
     *
     * @param nc entero que indica el nuevo tamaño del vector interno. El
     * parámetro nc es la abreviación de nueva capacidad.
     */
    private void resize(int nc) {
        if (nc > 0 && nc >= n) {
            char[] nuevoVector = new char[nc];

            for (int i = 0; i < n; i++) {
                nuevoVector[i] = caracteres[i];
            }
            caracteres = nuevoVector;
        }
    }

    /**
     * Agrega a esta lista un caracter en el índice especificado.
     *
     * @param index Entero que indica la posición donde se quiere insertar el
     * nuevo caracter.
     * @param character Valor a insertar en la lista.
     * @return true si inserta el caracter y false en caso contrario.
     */
    public boolean add(int index, char character) {
        if (index >= 0 && index <= n) {
            if (caracteres.length == n) {
                resize(2 * n);
            }
            for (int i = n; i > index; i--) {
                caracteres[i] = caracteres[i - 1];
            }
            caracteres[index] = character;
            n++;
            return true;
        }
        return false;
    }

    /**
     * Elimina todos los elementos de la lista.
     */
    public void clear() {
        caracteres = new char[1];
        n = 0;
    }

    /**
     * Retorna true si la lista no tiene elementos.
     *
     * @return true si la lista no tiene elementos y false en caso contrario.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Retorna un número entero que indica la cantidad de elementos que tiene
     * esta lista.
     *
     * @return entero que indica la cantidad de elementos que tiene la lista.
     */
    public int size() {
        return n;
    }

    /**
     * Recorta la capacidad de esta instancia de la lista para que sea igual al
     * tamaño de la lista.
     */
    public void trimToSize() {
        if (n > 0 && caracteres.length > n) {
            resize(n);
        }
    }

    /**
     * Reemplaza el elemento en la posición especificada con el caracter
     * especificado.
     *
     *
     * @param index Entero que indica la posición donde se quiere modificar el
     * nuevo dato.
     * @param character Valor para modificar un elemento en la lista.
     * @return true si modifica el elemento y false en caso contrario.
     */
    public boolean set(int index, char character) {
        if (index >= 0 && index < n) {
            caracteres[index] = character;
            return true;
        }
        return false;
    }

    /**
     * Retorna el elemento de la posición especificada.
     *
     * @param index entero que indica la posición del dato a recuperar.
     * @return el caracter ubicado en la posición esepecificada.
     * @throws IndexOutOfBoundsException si la posición no existe. La posición
     * no existe si index es menor que cero o mayor o igual al tamaño de la
     * lista.
     */
    public char get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            return caracteres[index];
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
    }

    /**
     * Retorna true si la lista tiene el caracter especificado.
     *
     * @param character valor a buscar en la lista
     * @return true si el caracter existe en la lista y false en caso contrario.
     */
    public boolean contains(char character) {
        for (int i = 0; i < n; i++) {
            if (caracteres[i] == character) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(char character) {
        for (int i = 0; i < n; i++) {
            if (caracteres[i] == character) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Indica la cantidad de caracteres que se pueden almacenar en la lista sin
     * necesidad de aumentar el tamaño del vector interno.
     *
     * @return entero que indica el tamaño actual del vector interno.
     */
    public int capacity() {
        return caracteres.length;
    }

    /**
     * Devuelve una representación de cadena de la lista. En general, el método
     * toString devuelve una cadena que "representa textualmente" este objeto.
     * El resultado debe ser una representación concisa pero informativa que sea
     * fácil de leer para una persona.
     *
     * @return Una representación de cadena de este objeto.
     */
    @Override
    public String toString() {
        String texto = "";

        for (int i = 0; i < n; i++) {
            texto += caracteres[i];
        }
        return texto;
    }

    @Override
    public Iterator<Character> iterator() {
        return new MiIterador();
    }

    private class MiIterador implements Iterator<Character> {

        private int posicion;

        public MiIterador() {
            posicion = 0;
        }

        @Override
        public boolean hasNext() {
            return posicion < n;
        }

        @Override
        public Character next() {
            return caracteres[posicion++];
        }
    }
}
