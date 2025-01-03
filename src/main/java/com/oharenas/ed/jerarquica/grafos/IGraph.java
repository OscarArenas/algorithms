/*
 * Copyright (C) 2021 Oscar Arenas
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
package com.oharenas.ed.jerarquica.grafos;

import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public interface IGraph {

    boolean addEdge(int v, int w);

    boolean removeEdge(int v, int w);

    void addVertex();

    boolean removeVertex(int v);

    ArrayList<Integer> adjacents(int v);

    boolean containsEdge(int v, int w);

    boolean containsVertex(int v);

    boolean setWeight(int v, int w, int weight);

    boolean isWeighted();

    boolean hasNegativeWeights();

    boolean isEmpty();

    void clear();

    int degree(int v);

    int verteces();

    int edges();

}
