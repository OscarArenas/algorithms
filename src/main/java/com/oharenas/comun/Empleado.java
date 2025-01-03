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
public class Empleado implements Serializable {

    public static final int FEMENINO = 1;
    public static final int MASCULINO = 2;

    // Campos (Atributos)
    private String nuip;
    private String nombre;
    private String apellido;
    private int genero;
    private double salario;
    private Fecha fechaNacimiento;
    private Fecha fechaIngreso;

    // Métodos (Operaciones)
    public Empleado(String nuip, String nombre,
            String apellido, int genero,
            double salario, Fecha fechaNacimiento,
            Fecha fechaIngreso) {
        this.nuip = nuip;
        this.nombre = nombre;
        this.apellido = apellido;

        if (genero == FEMENINO || genero == MASCULINO) {
            this.genero = genero;
        } else {
            throw new IllegalArgumentException("Género no válido " + genero);
        }

        if (salario >= 0) {
            this.salario = salario;
        } else {
            throw new IllegalArgumentException("Salario debe ser mayor que cero");
        }

        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
    }

    public Empleado() {
        this("", "", "", FEMENINO, 0, Fecha.hoy(), Fecha.hoy());
    }

    public String getNuip() {
        return nuip;
    }

    public void setNuip(String nuip) {
        this.nuip = nuip;
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

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Fecha getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Fecha fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Fecha getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Fecha fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int calcularEdad() {
        return fechaNacimiento.calcularDiferenciaEnMeses(Fecha.hoy()) / 12;
    }

    public int calcularAntigueadad() {
        return fechaIngreso.calcularDiferenciaEnMeses(Fecha.hoy());
    }

    public double calcularPrestaciones() {
        return salario * calcularAntigueadad() / 12.0;
    }

    public boolean esMujer() {
        return genero == FEMENINO;
    }

    public boolean esHombre() {
        return genero == MASCULINO;
    }

    public boolean esIgual(Empleado empleado) {
        return nuip.compareTo(empleado.nuip) == 0;
    }

    public Empleado clonar() {
        return new Empleado(nuip, nombre, apellido, genero, salario, fechaNacimiento.clonar(), fechaIngreso.clonar());
    }

    public String getCSV() {
        String cadena = nuip + "," + nombre + "," + apellido + ",";

        cadena += genero + "," + salario + "," + fechaNacimiento.getCSV() + "," + fechaIngreso.getCSV();

        return cadena;
    }

    @Override
    public String toString() {
        String cadena = "Nombre: " + nombre + "\n";

        cadena = cadena + "Apellido: " + apellido + "\n";
        cadena = cadena + "NUIP: " + nuip + "\n";
        cadena += "Género: ";

        if (genero == FEMENINO) {
            cadena += "Femenino" + "\n";
        } else {
            cadena += "Masculino" + "\n";
        }

        cadena += "Fecha nacimiento: " + fechaNacimiento + "\n";
        cadena += "Fecha ingreso: " + fechaIngreso + "\n";

        cadena += "Salario: " + salario + "\n";

        return cadena;
    }

    public String jsonString() {
        String cadena = "\"nuip\":\"" + nuip + "\",\n";

        cadena += "\"nombre\":\"" + nombre + "\",\n";
        cadena += "\"apellido\":\"" + apellido + "\",\n";
        cadena += "\"genero\":" + genero + ",\n";
        cadena += "\"salario\":" + salario + ",\n";
        cadena += "\"fechaNacimiento\":" + fechaNacimiento.jsonString() + ",\n";
        cadena += "\"fechaIngreso\":" + fechaIngreso.jsonString();

        return "{\n" + cadena + "\n}";
    }
}
