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
package com.oharenas.math;

/**
 * Polinomio implementado mediante una lista simplemente enlazada ordenada. Los
 * términos se almacenan de menor a mayor exponente.
 *
 * @author Oscar Arenas
 */
public class PolinomioSE {

    // Campos (Atributos)
    private Nodo primero;
    private int n;

    // Métodos
    public PolinomioSE() {    // Constructor
        primero = null;
        n = 0;
    }

    // Agrega un termino y los ordena de menor a mayor exponente
    public boolean agregar(double coeficiente, int exponente) {
        if (coeficiente == 0 || exponente < 0) {
            return false;
        }
        Nodo actual = primero;
        Nodo anterior = null;

        while (actual != null && actual.exponente < exponente) {
            anterior = actual;
            actual = actual.siguiente; // Para recorrer los nodos
        }
        if (actual != null && actual.exponente == exponente) {
            return false; // No se permiten exponentes repetidos
        }
        Nodo nuevoNodo = new Nodo(coeficiente, exponente, actual);

        if (anterior == null) {
            primero = nuevoNodo;
        } else {
            anterior.siguiente = nuevoNodo;
        }

        n++;
        return true;
    }

    public boolean adicionar(double coeficiente, int exponente) {
        if (coeficiente == 0 || exponente < 0) {
            return false;
        }
        Nodo actual = primero;
        Nodo anterior = null;

        while (actual != null && actual.exponente < exponente) {
            anterior = actual;
            actual = actual.siguiente;
        }
        if (actual != null && actual.exponente == exponente) {
            coeficiente += actual.coeficiente;
            if (coeficiente != 0) {
                actual.coeficiente = coeficiente;
            } else {
                if (anterior == null) {
                    primero = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }

                n--;
            }
            return true;
        } else {
            Nodo nuevoNodo = new Nodo(coeficiente, exponente, actual);

            if (anterior == null) {
                primero = nuevoNodo;
            } else {
                anterior.siguiente = nuevoNodo;
            }
            n++;
            return true;
        }
    }

    /**
     *
     * @param exponente
     * @return Retorna true si existe el término con el exponente indicado en el
     * parámetro y false en caso contrario.
     */
    public boolean contiene(int exponente) {
        Nodo actual = primero;

        while (actual != null && actual.exponente < exponente) {
            actual = actual.siguiente;
        }
        return actual != null && actual.exponente == exponente;
    }

    public boolean eliminar(int exponente) {
        if (primero != null && exponente >= 0) {
            Nodo actual = primero;
            Nodo anterior = null;

            while (actual != null && actual.exponente < exponente) {
                anterior = actual;
                actual = actual.siguiente;
            }
            if (actual != null && actual.exponente == exponente) {
                if (anterior == null) {
                    primero = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }

                n--;
                return true;
            }
        }
        return false;
    }

    public boolean modificar(double coeficiente, int exponente) {
        if (coeficiente != 0) {
            Nodo actual = primero;

            while (actual != null && actual.exponente < exponente) {
                actual = actual.siguiente;
            }
            if (actual != null && actual.exponente == exponente) {
                actual.coeficiente = coeficiente;
                return true;
            }
        }
        return false;
    }

    public double coeficiente(int exponente) {
        Nodo actual = primero;

        while (actual != null && actual.exponente < exponente) {
            actual = actual.siguiente;
        }
        if (actual != null && actual.exponente == exponente) {
            return actual.coeficiente;
        }
        throw new IllegalArgumentException("No existeel término con exponente: " + exponente);
    }

    public int cantidadTerminos() {
        return n;
    }

    public int grado() {
        if (n > 0) {
            Nodo actual = primero;

            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            return actual.exponente;
        }
        return -1;
    }

    public boolean esVacio() {
        return primero == null;
    }

    public double[] aVector() {
        double[] vector = new double[2 * n];

        Nodo auxiliar = primero;
        int i = 0;
        while (auxiliar != null) {
            vector[i++] = auxiliar.coeficiente;
            vector[i++] = auxiliar.exponente;

            auxiliar = auxiliar.siguiente;
        }

        return vector;
    }

    public PolinomioSE sumar(PolinomioSE otro) {
        PolinomioSE resultado = new PolinomioSE();

        Nodo auxiliar = primero;
        while (auxiliar != null) {
            resultado.agregar(auxiliar.coeficiente, auxiliar.exponente);
            auxiliar = auxiliar.siguiente;
        }

        auxiliar = otro.primero;
        while (auxiliar != null) {
            double nuevoCoeficiente = auxiliar.coeficiente;
            int exponente = (int) auxiliar.exponente;

            if (resultado.contiene(exponente)) {
                nuevoCoeficiente += resultado.coeficiente(exponente);

                if (nuevoCoeficiente != 0) {
                    resultado.modificar(nuevoCoeficiente, exponente);
                } else {
                    resultado.eliminar(exponente);
                }
            } else {
                resultado.agregar(nuevoCoeficiente, exponente);
            }
            auxiliar = auxiliar.siguiente;
        }
        return resultado;
    }

    public PolinomioSE restar(PolinomioSE otro) {
        return sumar(otro.inverso());
    }

    public PolinomioSE inverso() {
        PolinomioSE inverso = new PolinomioSE();
        Nodo actual = primero;

        while (actual != null) {
            inverso.agregar(-actual.coeficiente, actual.exponente);
            actual = actual.siguiente;
        }

        return inverso;
    }

    public void borrar() {
        primero = null;
        n = 0;
    }

    /**
     * Genera una representación en cadena (String) del polinomio. La cadena
     * contiene todos los términos del polinomio en orden descendente de los
     * exponentes.
     *
     * @return Cadena que representa el polinomio.
     */
    @Override
    public String toString() {
        String cadena = "";
        Nodo actual = primero;

        while (actual != null) {
            double coeficiente = actual.coeficiente;
            String signo = "";

            if (actual.siguiente != null) {
                if (coeficiente < 0) {
                    signo = " - ";
                } else {
                    signo = " + ";
                }
            } else if (coeficiente < 0) {
                signo = "-";
            }
            cadena = signo + convertirTermino(coeficiente, actual.exponente) + cadena;
            actual = actual.siguiente;
        }
        return cadena;
    }

    /*
     * Convierte los parámetros en una representación en cadena (String) del 
     * término en función de x. Por ejemplo, si el coeficiente es 5 y el 
     * exponente es 3 el método retorna la cadena "5x^3"
     */
    private String convertirTermino(double coeficiente, int exponente) {
        coeficiente = Math.abs(coeficiente);
        int valorEntero = (int) coeficiente;
        String cadena = "";

        if (valorEntero == coeficiente) {
            cadena += valorEntero;
        } else {
            cadena += coeficiente;
        }
        if (exponente > 0) {
            if (coeficiente == 1) {
                cadena = "";
            }
            if (exponente == 1) {
                cadena += "x";
            } else {
                cadena += "x^" + exponente;
            }
        }
        return cadena;
    }

    private class Nodo {

        // **** ATRIBUTOS ****
        // Termino
        double coeficiente;
        int exponente;
        // Enlace
        Nodo siguiente;

        // **** METODOS ****
        // Constructor
        public Nodo(double coeficiente, int exponente) {
            this.coeficiente = coeficiente;
            this.exponente = exponente;
            siguiente = null;
        }

        public Nodo(double coeficiente, int exponente, Nodo siguiente) {
            this.coeficiente = coeficiente;
            this.exponente = exponente;
            this.siguiente = siguiente;
        }
    }
}
