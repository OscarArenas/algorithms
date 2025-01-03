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

import com.oharenas.util.Util;
import java.util.Iterator;

/**
 *
 * @author Oscar Arenas
 */
public class DoubleArrayList implements Iterable<Double> {

    private double[] data;
    private int n; // cantidad de elementos de la lista (tamaño)
    private static final int CAPACIDAD_INICIAL = 8;

    public DoubleArrayList() {
        data = new double[CAPACIDAD_INICIAL];
        n = 0;
    }

    public DoubleArrayList(int capacity) throws IllegalArgumentException {
        if (capacity < 1) {
            throw new IllegalArgumentException("Argumento no válido " + capacity);
        }
        data = new double[capacity];
        n = 0;
    }

    public DoubleArrayList(double[] data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Argumento null");
        }
        if (data.length > 0) {
            this.data = data.clone();
            n = data.length;
        } else {
            this.data = new double[CAPACIDAD_INICIAL];
            n = 0;
        }
    }

    /**
     * Adiciona el element especificado al final de la lista. Permite almacenar
     * valores repetidos.
     *
     * @param element valor que se agregará al final de esta lista
     */
    public void add(double element) {
        if (data.length == n) {
            resize(2 * n);
        }
        data[n++] = element;
    }

    /**
     * Borra de la lista el valor ubicado en el índice especificado. El borrado
     * se realiza desplazando todos los elementos que estan después del ubicado
     * en el índice especificado una posición a la izquierda.
     *
     * @param index Entero que indica la posición del valor a borrar
     * @return Retorna <tt>true</tt> si elimina el valor y false en caso
     * contrario
     */
    public boolean remove(int index) {
        if (index >= 0 && index < n) {
            n--;
            while (index < n) {
                data[index] = data[index + 1];
                index++;
            }
            if (data.length / 4 == n) {
                resize(data.length / 2);
            }
            return true;
        }
        return false;
    }

    public boolean remove(double element) {
        for (int i = 0; i < n; i++) {
            if (data[i] == element) {
                return remove(i);
            }
        }
        return false;
    }

    /**
     * Borra de la lista el valor ubicado en el índice especificado.
     *
     * @param index Entero que indica la posición del element a borrar.
     * @return Retorna el elemento que se borró de esta lista.
     * @throws IndexOutOfBoundsException Lanza la excepción en el caso que no
     * exista el índice.
     */
    public double delete(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            double element = data[index];
            n--;
            while (index < n) {
                data[index] = data[index + 1];
                index++;
            }
            if (data.length / 4 == n) {
                resize(data.length / 2);
            }
            return element;
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
            double[] nuevoVector = new double[nc];

            for (int i = 0; i < n; i++) {
                nuevoVector[i] = data[i];
            }
            data = nuevoVector;
        }
    }

    /**
     * Agrega a esta lista un element en el índice especificado.
     *
     * @param index Entero que indica la posición donde se quiere insertar el
     * nuevo element.
     * @param element Valor a insertar en la lista.
     * @return <tt>true</tt> si inserta el element y false en caso contrario.
     */
    public boolean add(int index, double element) {
        if (index >= 0 && index <= n) {
            if (data.length == n) {
                resize(2 * n);
            }
            for (int i = n; i > index; i--) {
                data[i] = data[i - 1];
            }
            data[index] = element;
            n++;
            return true;
        }
        return false;
    }

    /**
     * Reemplaza el elemento en la posición especificada con el element
     * especificado.
     *
     *
     * @param index Entero que indica la posición del elemento que se modificará
     * con el nuevo element.
     * @param element Valor para modificar un elemento en la lista.
     * @return <tt>true</tt> si modifica el elemento y false en caso contrario.
     */
    public boolean set(int index, double element) {
        if (index >= 0 && index < n) {
            data[index] = element;
            return true;
        }
        return false;
    }

    /**
     * Retorna el elemento de la posición especificada.
     *
     * @param index entero que indica la posición del element a recuperar.
     * @return el número real ubicado en la posición esepecificada.
     * @throws IndexOutOfBoundsException si la posición no existe. La posición
     * no existe si index es menor que cero o mayor o igual al tamaño de la
     * lista.
     */
    public double get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < n) {
            return data[index];
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
    }

    /**
     * Retorna true si la lista tiene el element especificado.
     *
     * @param element valor a buscar en la lista
     * @return <tt>true</tt> si el element existe en la lista y false en caso
     * contrario.
     */
    public boolean contains(double element) {
        for (int i = 0; i < n; i++) {
            if (data[i] == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina todos los elementos de la lista.
     */
    public void clear() {
        data = new double[CAPACIDAD_INICIAL];
        n = 0;
    }

    /**
     * Retorna <tt>true</tt> si la lista no tiene elementos.
     *
     * @return <tt>true</tt> si la lista no tiene elementos y false en caso
     * contrario.
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
        if (n > 0 && data.length > n) {
            resize(n);
        }
    }

    /**
     * Indica la cantidad de datos que se pueden almacenar en la lista sin
     * necesidad de aumentar el tamaño del vector interno.
     *
     * @return entero que indica el tamaño actual del vector interno.
     */
    public int capacity() {
        return data.length;
    }

    /**
     * Devuelve una representación de cadena (String ) de la lista. En general,
     * el método toString devuelve una cadena que "representa textualmente" este
     * objeto. El resultado debe ser una representación concisa pero informativa
     * que sea fácil de leer para una persona.
     *
     * @return Una representación de cadena de este objeto.
     */
    @Override
    public String toString() {
        String texto = "";

        if (n > 0) {
            texto += Util.realAString(data[0]);
            for (int i = 1; i < n; i++) {
                texto += ", " + Util.realAString(data[i]);
            }
        }
        return "[" + texto + "]";
    }

    @Override
    public Iterator<Double> iterator() {
        return new MiIterador();
    }

    private class MiIterador implements Iterator<Double> {

        private int posicion;

        public MiIterador() {
            posicion = 0;
        }

        @Override
        public boolean hasNext() {
            return posicion < n;
        }

        @Override
        public Double next() {
            posicion++;
            return data[posicion - 1];
        }
    }
}
