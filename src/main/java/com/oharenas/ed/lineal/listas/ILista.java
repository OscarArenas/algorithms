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
package com.oharenas.ed.lineal.listas;

/**
 *
 * @author Oscar Arenas
 */
public interface ILista {

    /// Operaciones primitivas
    public void agregar(double dato);

    public boolean eliminar(int indice);

    public double obtener(int indice);

    public int tamanio();

    // Operaciones Complementarias
    public boolean contiene(double dato);

    public boolean modificar(int indice, double dato);

    public boolean esVacia();

    public void vaiciar();
}
