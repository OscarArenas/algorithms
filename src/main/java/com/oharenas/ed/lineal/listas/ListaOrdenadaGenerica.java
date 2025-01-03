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
package com.oharenas.ed.lineal.listas;

/**
 * Almacena los datos ordenados de menor a mayor.
 *
 * @author Oscar Arenas
 * @param <E>
 */
public class ListaOrdenadaGenerica<E extends Comparable<E>> {

    // Campos (Atributos)
    private E[] datos;
    private int n; // Tamaño de la lista
    private static int CAPACIDAD_INICIAL = 8;

    // Métodos (Comportamientos)
    public ListaOrdenadaGenerica() {
        datos = (E[]) new Object[CAPACIDAD_INICIAL];
        n = 0;
    }

    public ListaOrdenadaGenerica(int capacidad) {
        if (capacidad < 1) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        datos = (E[]) new Object[capacidad];
        n = 0;
    }

    public void agregar(E dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        if (datos.length == n) {
            cambiarCapacidad(2 * n);
        }
        // Indice del último elemento de la lista
        int i = n - 1;

        while (i >= 0 && dato.compareTo(datos[i]) < 0) {
            datos[i + 1] = datos[i];
            i--;
        }

        datos[i + 1] = dato;
        n++;
    }

    public boolean eliminar(int indice) {
        if (indice >= 0 && indice < n) {
            n--;
            for (int i = indice; i < n; i++) {
                datos[i] = datos[i + 1];
            }
            if (datos.length / 4 == n) {
                cambiarCapacidad(datos.length / 2);
            }
            return true;
        }
        return false;
    }

    public boolean eliminar(E dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        for (int i = 0; i < n; i++) {
            if (datos[i].compareTo(dato) == 0) {
                n--;
                for (; i < n; i++) {
                    datos[i] = datos[i + 1];
                }
                if (datos.length / 4 == n) {
                    cambiarCapacidad(datos.length / 2);
                }
                return true;
            }
        }
        return false;
    }

    public boolean contiene(E dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Null argument.");
        }
        int bajo = 0;
        int alto = n - 1;

        while (bajo <= alto) {
            int central = bajo + (alto - bajo) / 2;
            if (dato.compareTo(datos[central]) < 0) {
                alto = central - 1;
            } else if (dato.compareTo(datos[central]) > 0) {
                bajo = central + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public void agregar(E[] datos) {
        for (int i = 0; i < datos.length; i++) {
            agregar(datos[i]);
        }
    }

    private void cambiarCapacidad(int nc) {
        if (nc > 0 && nc >= n) {
            E[] nuevoVector = (E[]) new Object[nc];

            for (int i = 0; i < n; i++) {
                nuevoVector[i] = datos[i];
            }
            datos = nuevoVector;
        }
    }

//    @Override
//    public String toString() {
//        String cadena = "";
//
//        if (n > 0) {
//            for (int i = 0; i < n - 1; i++) {
//                cadena += datos[i] + ", ";
//            }
//
//            cadena += datos[n - 1];
//        }
//        return "[" + cadena + "]";
//    }
    public void borrar() {
        datos = (E[]) new Object[CAPACIDAD_INICIAL];
        n = 0;
    }

    public boolean esVacia() {
        return n == 0;
    }

    public E obtener(int indice) {
        if (indice >= 0 && indice < n) {
            return datos[indice];
        }
        throw new IndexOutOfBoundsException();
    }

    public int tamanio() {
        return n;
    }
}
