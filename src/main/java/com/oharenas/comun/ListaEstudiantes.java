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

import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public class ListaEstudiantes {

    private final ArrayList<Estudiante> estudiantes;

    public ListaEstudiantes() {
        estudiantes = new ArrayList<>();
    }

    public boolean agregar(Estudiante estudiante) {
        if (estudiante != null && !existe(estudiante.getNuip())) {
            return estudiantes.add(estudiante.clonar());
        }
        return false;
    }

    /**
     * Retorna una copia del objeto ubicado en la posición especificada.
     *
     * @param indice Posición del objeto a retornar.
     * @return Una copia del objeto ubicado en la posición específicada.
     */
    public Estudiante obtener(int indice) {
        if (indice >= 0 && indice < cantidadEstudiantes()) {
            return estudiantes.get(indice).clonar();
        }
        return null;
    }

    public boolean existe(String nuip) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNuip().compareTo(nuip) == 0) {
                return true;
            }
        }
        return false;
    }

    public Estudiante eliminar(String nuip) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getNuip().compareTo(nuip) == 0) {
                return estudiantes.remove(i);
            }
        }
        return null;
    }

    /**
     * Modifica cualquier atributo excepto el NUIP.
     *
     * @param estudiante Objeto con los nuevos valores a cambiar pero su NUIP
     * debe existir en la lista.
     * @return true si modificó algún campo y false en caso contrario.
     */
    public boolean modificar(Estudiante estudiante) {
        Estudiante aux = estudiante.clonar();

        for (Estudiante iterador : estudiantes) {
            if (iterador.getNuip().compareTo(estudiante.getNuip()) == 0) {
                iterador.setNombre(aux.getNombre());
                iterador.setApellido(aux.getApellido());
                iterador.setNota(aux.getNota());
                iterador.setGenero(aux.getGenero());
                return true;
            }
        }

        return false;
    }

    public boolean modificarNuip(String nuip, String nuevoNuip) {
        if (!existe(nuevoNuip)) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getNuip().compareTo(nuip) == 0) {
                    estudiante.setNuip(nuevoNuip);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean modificarNombre(String nuip, String nombre) {
        nombre = nombre.trim();
        if (!nombre.isEmpty()) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getNuip().compareTo(nuip) == 0) {
                    estudiante.setNombre(nombre);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean modificarApellido(String nuip, String apellido) {
        apellido = apellido.trim();
        if (!apellido.isEmpty()) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getNuip().compareTo(nuip) == 0) {
                    estudiante.setApellido(apellido);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean modificarGenero(String nuip, int genero) {
        if (genero == Estudiante.FEMENINO || genero == Estudiante.MASCULINO) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getNuip().compareTo(nuip) == 0) {
                    estudiante.setGenero(genero);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean modificarNota(String nuip, double nota) {
        if (nota >= 0 && nota <= 5) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getNuip().compareTo(nuip) == 0) {
                    estudiante.setNota(nota);
                    return true;
                }
            }
        }
        return false;
    }

    public int cantidadEstudiantes() {
        return estudiantes.size();
    }

    public boolean esVacia() {
        return estudiantes.isEmpty();
    }

    public void vaciar() {
        estudiantes.clear();
    }

    public void mostrarEstudiantes() {
        int i = 1;
        for (Estudiante estudiante : estudiantes) {
            System.out.println("Estudiante " + i + ":");
            System.out.println(estudiante);
            i++;
        }
    }
}
