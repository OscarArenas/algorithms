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
public interface ICollection<E> {

    boolean add(E element);

    boolean addAll(ICollection<? extends E> collection);

    boolean contains(Object obj);

    void clear();

    boolean isEmpty();

    boolean remove(Object obj);

    boolean removeAll(ICollection<?> collection);

    int size();
}
