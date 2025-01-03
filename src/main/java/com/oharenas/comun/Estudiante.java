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
package com.oharenas.comun;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Oscar Arenas
 */
public class Estudiante implements Serializable {

    public static final int FEMENINO = 1;
    public static final int MASCULINO = 2;

    // Campos (Atributos)
    private String nuip;
    private String nombre;
    private String apellido;
    private int genero;
    private double nota;
    private boolean repitente;

    // Métodos (Comportamientos)
    public Estudiante(String nuip, String nombre, String apellido, int genero, double nota) {
        if (nombre == null) {
            throw new IllegalArgumentException("Falta indicar el nombre.");
        } else {
            nombre = nombre.trim();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("Falta indicar el nombre.");
            }
        }
        if (apellido == null) {
            throw new IllegalArgumentException("Falta indicar el apellido.");
        } else {
            apellido = apellido.trim();
            if (apellido.isEmpty()) {
                throw new IllegalArgumentException("Falta indicar el apellido.");
            }
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.nuip = nuip;
        repitente = false;

        if (genero == FEMENINO || genero == MASCULINO) {
            this.genero = genero;
        } else {
            throw new IllegalArgumentException("Género no válido " + genero);
        }

        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("La nota " + nota + " no pertenece al intervalo [0, 5]");
        } else {
            this.nota = nota;
        }
    }

    public Estudiante() {
        nuip = "";
        nombre = "";
        apellido = "";
        genero = 1;
        nota = 0;
        repitente = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNuip() {
        return nuip;
    }

    public boolean isRepitente() {
        return repitente;
    }

    public void setNuip(String nuip) {
        this.nuip = nuip;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        if (genero == FEMENINO || genero == MASCULINO) {
            this.genero = genero;
        } else {
            throw new IllegalArgumentException("Género no válido " + genero);
        }
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if (nota >= 0 && nota <= 5) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("La nota " + nota + " no pertenece al intervalo [0, 5]");
        }
    }

    public void setRepitente(boolean repitente) {
        this.repitente = repitente;
    }

    public boolean esMujer() {
        return genero == FEMENINO;
    }

    public boolean esHombre() {
        return genero == MASCULINO;
    }

    public boolean aprobo() {
        return nota >= 3;
    }

    /**
     * Verifica si dos estudiantes son iguales. Dos estudiantes son iguales si
     * tienen el mismo valor de NUIP.
     *
     * @param estudiante elemento con el cual se compara este objeto.
     * @return true si los estudiantes son iguales.
     */
    public boolean esIgual(Estudiante estudiante) {
        return estudiante != null && nuip.compareTo(estudiante.nuip) == 0;
    }

    public Estudiante clonar() {
        Estudiante estudiante = new Estudiante(nuip, nombre, apellido, genero, nota);

        estudiante.repitente = repitente;

        return estudiante;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) obj;

            return nuip.equals(estudiante.nuip);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nuip);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.apellido);
        hash = 97 * hash + this.genero;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.nota) ^ (Double.doubleToLongBits(this.nota) >>> 32));
        hash = 97 * hash + (this.repitente ? 1 : 0);
        return hash;
    }

    @Override
    public Estudiante clone() throws CloneNotSupportedException {
        super.clone();
        return clonar();
    }

    // Método sobrescrito (Heredado de la clase Object)
    @Override
    public String toString() {
        String cadena = "NUIP: " + nuip + "\n";

        cadena = cadena + "Nombre: " + nombre + "\n";
        cadena = cadena + "Apellido: " + apellido + "\n";
        cadena += "Género: ";

        if (genero == FEMENINO) {
            cadena += "Femenino" + "\n";
        } else {
            cadena += "Masculino" + "\n";
        }

        cadena += "Nota: " + nota + "\n";
        cadena += "Repitente: " + (repitente ? "Si" : "No") + "\n";

        return cadena;
    }

    public String getCSV() {
        return nuip + "," + nombre + "," + apellido + "," + genero + "," + nota + "," + repitente;
    }

    public String jsonString() {
        String cadena = "\"nuip\":\"" + nuip + "\",\n";

        cadena += "\"nombre\":\"" + nombre + "\",\n";
        cadena += "\"apellido\":\"" + apellido + "\",\n";
        cadena += "\"genero\":" + genero + ",\n";
        cadena += "\"nota\":" + nota + ",\n";
        cadena += "\"repitente\":" + repitente;

        return "{\n" + cadena + "\n}";
    }

    public String[] toStringArray() {
        String[] cadena = {nuip, nombre, apellido, genero + "", nota + "", repitente + ""};

        return cadena;
    }

    public int cantidadBytes() {
        int suma = 2 * (nuip.length() + nombre.length() + apellido.length());
        suma += 4 + 8 + 1;

        return suma;
    }
}
