/*
 * Copyright (C) 2018 Oscar Arenas
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
public class CuentaDeAhorros implements Comparable<CuentaDeAhorros>, Serializable {

    public static final int SALDO_MINIMO = 12000;

    private int codigo; // Campo de clasificacion o clave
    private String nombre;
    private String apellido;
    private double saldo;

    public CuentaDeAhorros(int codigo, String nombre, String apellido, double saldo) {
        if (codigo < 1) {
            throw new IllegalArgumentException("El código debe ser mayor que cero.");
        }
        if (saldo < SALDO_MINIMO) {
            throw new IllegalArgumentException("El saldo debe ser mayor o igual a $" + SALDO_MINIMO);
        }
        if (nombre == null) {
            throw new IllegalArgumentException("Falta indicar el nombre");
        } else {
            nombre = nombre.trim();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("Falta indicar el nombre");
            }
        }
        if (apellido == null) {
            throw new IllegalArgumentException("Falta indicar el apellido");
        } else {
            apellido = apellido.trim();
            if (apellido.isEmpty()) {
                throw new IllegalArgumentException("Falta indicar el apellido");
            }
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.saldo = saldo;
    }

    public CuentaDeAhorros() {
        this.codigo = 0;
        this.nombre = "";
        this.apellido = "";
        this.saldo = SALDO_MINIMO;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
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

    public double getSaldo() {
        return saldo;
    }

    public boolean consignar(double cantidad) {
        boolean valido = cantidad > 0;

        if (valido) {
            saldo += cantidad;
        }
        return valido;
    }

    public boolean retirar(double cantidad) {
        boolean valido = cantidad > 0 && (saldo - cantidad) >= SALDO_MINIMO;

        if (valido) {
            saldo -= cantidad;
        }
        return valido;
    }

    @Override
    public int compareTo(CuentaDeAhorros cuenta) {
        return this.codigo - cuenta.codigo;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + "\nNombre: " + nombre + "\nApellido: " + apellido
                + "\nSaldo: $" + saldo;
    }

    public String getCSV() {
        return codigo + "," + nombre + "," + apellido
                + "," + saldo;
    }
}
