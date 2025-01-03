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

/**
 *
 * @author Oscar Arenas
 */
public class Persona implements Serializable {

    public static final int FEMENINO = 1;
    public static final int MASCULINO = 2;

    //
    private String nuip;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private int genero;
    private String numeroTelefonico;
    private String email;
    private Fecha fechaNacimiento;

    public Persona(String nuip, String nombre, String primerApellido,
            String segundoApellido, String numeroTelefonico,
            String email, Fecha fechaNacimiento, int genero) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nuip = nuip;
        this.numeroTelefonico = numeroTelefonico;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;

        if (genero == FEMENINO || genero == MASCULINO) {
            this.genero = genero;
        } else {
            throw new IllegalArgumentException("Género no válido " + genero);
        }
    }

    public Persona() {
        this("", "", "", "", "", "", Fecha.hoy(), FEMENINO);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNuip() {
        return nuip;
    }

    public void setNuip(String nuip) {
        this.nuip = nuip;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public Fecha getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Fecha fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int calcularEdad() {
        return fechaNacimiento.calcularDiferenciaEnMeses(Fecha.hoy()) / 12;
    }

    public String getCSV() {
        String cadena = nuip + "," + nombre + "," + primerApellido + ",";

        cadena += segundoApellido + "," + genero + "," + email + ",";
        cadena += numeroTelefonico + "," + fechaNacimiento.getCSV();

        return cadena;
    }

    @Override
    public String toString() {
        String cadena = "NUIP: " + nuip + "\n";

        cadena += "Nombre: " + nombre + "\n";
        cadena += "Apellidos: " + primerApellido + " " + segundoApellido + "\n";
        cadena += "Género: " + (genero == FEMENINO ? "Femenino" : "Masculino") + "\n";
        cadena += "Fecha nacimiento: " + fechaNacimiento.toString() + "\n";
        cadena += "Teléfono: " + numeroTelefonico + "\n";
        cadena += "Correo: " + email;

        return cadena;
    }
}
