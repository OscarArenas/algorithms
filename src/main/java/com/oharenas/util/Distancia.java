/*
 * Copyright (C) 2019 Oscar Arenas
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
package com.oharenas.util;

/**
 *
 * @author Oscar Arenas
 */
public class Distancia implements Comparable<Distancia> {

    public double distancia;
    public int previo;
    public int vertice;

    public Distancia(double distancia, int vertice) {
        this.distancia = distancia;
        this.vertice = vertice;
    }

    public Distancia(double distancia, int previo, int vertice) {
        this.distancia = distancia;
        this.vertice = vertice;
        this.previo = previo;
    }

    public Distancia(int vertice) {
        this(0, vertice);
    }

    @Override
    public int compareTo(Distancia obj) {
        if (distancia < obj.distancia) {
            return -1;
        } else if (distancia > obj.distancia) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "(" + distancia + ", " + vertice + ")"; //To change body of generated methods, choose Tools | Templates.
    }

}
