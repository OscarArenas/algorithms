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
public class CuentaBancaria implements Comparable<CuentaBancaria>, Serializable {

    public static final int CUENTA_DE_AHORROS = 1;
    public static final int CUENTA_CORRIENTE = 2;
    private static final double SALDO_MINIMO = 20000;
    //
    private int codigo;
    private Persona titular;
    private int tipoCuenta;
    private double saldo;

    public CuentaBancaria(int codigo, Persona titular, int tipoCuenta, double saldo) {
        this.codigo = codigo;
        this.titular = titular;
        this.tipoCuenta = tipoCuenta;
        if (saldo >= SALDO_MINIMO) {
            this.saldo = saldo;
        } else {
            this.saldo = SALDO_MINIMO;
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Persona getTitular() {
        return titular;
    }

    public void setTitular(Persona titular) {
        this.titular = titular;
    }

    public int getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(int tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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
    public int compareTo(CuentaBancaria otra) {
        return codigo - otra.codigo;
    }
}
