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

import com.oharenas.util.Util;

/**
 *
 * @author Oscar Arenas
 */
public class Vector {

    private double[] data;
    private int n;

    public Vector() {
        this(2);
    }

    public Vector(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("" + capacity);
        }
        data = new double[capacity];
        n = 0;
    }

    public void add(double item) {
        stretch();
        data[n++] = item;
    }

    public Vector add(Vector other) {
        Vector ans = new Vector();

        for (int i = 0; i < n; i++) {
            ans.add(data[i] + other.data[i]);
        }
        return ans;
    }

    public double dot(Vector other) {
        int min = n;

        if (other.n < min) {
            min = other.n;
        }

        double sum = 0;
        for (int i = 0; i < min; i++) {
            sum += data[i] * other.data[i];
        }
        return sum;
    }

    public double dot() {
        double sum = 0;

        for (int i = 0; i < n; i++) {
            sum += data[i] * data[i];
        }
        return sum;
    }

    public double angle(Vector other) {
        double p = dot(other);
        double sr = size();
        double ss = other.size();
        double c = p / (sr * ss);

        return Math.acos(c);
    }

    public double size() {
        return Math.sqrt(dot());
    }

    public Vector toBasis(Vector[] base) {
        if (n == base.length) {
            double[] coef = new double[n];
            Vector ans = new Vector();
            
            for (int i = 0; i < n; i++) {
                coef[i] = this.dot(base[i]) / base[i].dot();
                ans.add(coef[i]);
            }return ans;
        }
        throw new IllegalArgumentException();
    }

    private void stretch() {
        if (n == data.length) {
            double[] newArray = new double[2 * data.length];

            for (int i = 0; i < n; i++) {
                newArray[i] = data[i];
            }
            data = newArray;
        }
    }

    public int dimension() {
        return n;
    }

    public String toString() {
        String cadena = "";

        if (n > 0) {
            for (int i = 0; i < n - 1; i++) {
                cadena += Util.realAString(data[i]) + ", ";
            }

            cadena += Util.realAString(data[n - 1]);
        }
        return "[" + cadena + "]";
    }
}
