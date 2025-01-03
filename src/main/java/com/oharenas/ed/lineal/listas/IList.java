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
 * @param <E>
 */
public interface IList<E> {

    void add(E element);

    boolean add(int index, E element);

    boolean remove(int index);

    boolean remove(E element);

    E get(int index) throws IndexOutOfBoundsException;

    boolean set(int index, E element);

    boolean isEmpty();

    void clear();

    int size();
}
