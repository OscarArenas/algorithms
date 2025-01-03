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
package com.oharenas.ed.jerarquica.grafos;

import java.util.Stack;

/**
 *
 * @author Oscar Arenas
 */
public class DepthFirstSearch {

    private IGraph grafo;
    private final boolean[] visitado;
    private int[] edgeTo; // last vertex on known path to this vertex
    private final int origen;
    private int contador;

    public DepthFirstSearch(IGraph grafo, int origen) {
        if (grafo == null) {
            throw new IllegalArgumentException("Argumento nulo.");
        }
        if (!grafo.containsVertex(origen)) {
            throw new IllegalArgumentException("No existe el vértice " + origen);
        }
        this.grafo = grafo;
        visitado = new boolean[grafo.verteces()];
        edgeTo = new int[grafo.verteces()];
        this.origen = origen;

        dfs(origen);
    }

    private void dfs(int v) {
        visitado[v] = true;
        contador++;

        for (int w : grafo.adjacents(v)) {
            if (!visitado[w]) {
                edgeTo[w] = v;
                dfs(w);
            }
        }
    }

    public boolean existeRutaA(int v) {
        return visitado[v];
    }

    public int cantidadConectados() {
        return contador;
    }

    public boolean esConectado() {
        return contador == visitado.length;
    }

    public Iterable<Integer> ruta(int v) {
        if (!existeRutaA(v)) {
            return null;
        }
        Stack<Integer> camino = new Stack<>();

        for (int x = v; x != origen; x = edgeTo[x]) {
            camino.push(x);
        }
        camino.push(origen);

        return camino;
    }
}
