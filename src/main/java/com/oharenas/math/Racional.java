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
package com.oharenas.math;

import java.math.BigInteger;

/**
 * Clase para representar los números racionales (fraccionarios).
 *
 * @author Oscar Arenas
 */
public class Racional {

    private BigInteger numerador;
    private BigInteger denominador;

    public Racional() {
        this.numerador = BigInteger.valueOf(0);
        this.denominador = BigInteger.valueOf(1);
    }

    public Racional(long numerador) {
        this();
        set(BigInteger.valueOf(numerador), BigInteger.valueOf(1));
    }

    public Racional(long numerador, long denominador) {
        this();
        set(BigInteger.valueOf(numerador), BigInteger.valueOf(denominador));
    }

    public Racional(double valor) {
        this();
        int signo = 1;
        if (valor < 0) {
            valor = -valor;
            signo = -1;
        }
        String ct = valor + "";
        String[] div = ct.split("\\.");

        int p = div[1].length();
        long den = (int) Math.pow(10, p);
        long num = (long) Math.ceil(valor * den);

        set(BigInteger.valueOf(signo * num), BigInteger.valueOf(den));
    }

    public Racional(float valor) {
        this((double) valor);
    }

    public Racional(BigInteger numerador) {
        this();
        set(new BigInteger(numerador.toString()), BigInteger.valueOf(1));
    }

    public Racional(BigInteger numerador, BigInteger denominador) {
        this();
        set(new BigInteger(numerador.toString()), new BigInteger(denominador.toString()));
    }

    /**
     *
     * @param numerador
     * @param denominador
     * @throws ArithmeticException
     */
    public final void set(BigInteger numerador, BigInteger denominador) throws ArithmeticException {
        if (denominador.signum() != 0) {
            BigInteger divisor = numerador.gcd(denominador);

            this.numerador = numerador.divide(divisor);
            this.denominador = denominador.divide(divisor);

            if (this.denominador.signum() < 0) {
                this.numerador = this.numerador.negate();
                this.denominador = this.denominador.negate();
            }
        } else {
            throw new ArithmeticException("División entre cero. El denominador debe ser diferente de cero.");
        }
    }

    public BigInteger getNumerador() {
        return numerador;
    }

    public void setNumerador(BigInteger numerador) {
        set(numerador, denominador);
    }

    public BigInteger getDenominador() {
        return denominador;
    }

    public void setDenominador(BigInteger denominador) {
        set(numerador, denominador);
    }

    public Racional sumar(Racional b) {
        BigInteger termino1 = numerador.multiply(b.denominador);
        BigInteger termino2 = b.numerador.multiply(denominador);

        BigInteger num = termino1.add(termino2);
        BigInteger den = denominador.multiply(b.denominador);

        return new Racional(num, den);
    }

    public Racional restar(Racional b) {
        return sumar(b.negar());
    }

    public Racional mult(Racional b) {
        BigInteger num = numerador.multiply(b.numerador);
        BigInteger den = denominador.multiply(b.denominador);

        return new Racional(num, den);
    }
//

    public Racional negar() {
        return new Racional(numerador.negate(), denominador);
    }

    public Racional reciproco() {
        return new Racional(denominador, numerador);
    }

    public Racional div(Racional b) {
        return mult(b.reciproco());
    }

    public boolean esIgual(Racional b) {
        return this.equals(b);
    }

    public boolean esDiferente(Racional b) {
        return !this.equals(b);
    }

    public boolean esMenor(Racional b) {
        BigInteger p1 = numerador.multiply(b.denominador);
        BigInteger p2 = b.numerador.multiply(denominador);

        return p1.subtract(p2).signum() < 0;
    }

    public boolean esMenorOIgual(Racional b) {
        return this.equals(b) || this.esMenor(b);
    }

    public boolean esMayor(Racional b) {
        return !esMenorOIgual(b);
    }

    public boolean esMayorOIgual(Racional b) {
        return !esMenor(b);
    }

    public boolean esCero() {
        return numerador.equals(BigInteger.ZERO);
    }

    public boolean esUno() {
        return numerador.equals(BigInteger.ONE);
    }

    /**
     * Se puede perder información.
     *
     * @return Retorna un real que es aproximado a la division de los enteros.
     */
    public double razon() {
        return numerador.doubleValue() / (denominador).doubleValue();
    }

    public Racional copia() {
        return new Racional(numerador, denominador);
    }

    @Override
    public String toString() {
        if (denominador.equals(BigInteger.ONE)) {
            return numerador + "";
        }
        return numerador + "/" + denominador;
    }
}
