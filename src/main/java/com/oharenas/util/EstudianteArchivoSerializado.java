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
package com.oharenas.util;

import com.oharenas.comun.Estudiante;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public class EstudianteArchivoSerializado {

    public static String ruta = "lista.ser";

    // Create: crea archivo y/o agregar estudiante
    public static void agregar(Estudiante estudiante) {
        File archivo = new File(ruta);
        boolean adicionar = archivo.exists();

        try (ObjectOutputStream output
                = new ObjectOutputStream(new FileOutputStream(archivo, adicionar))) {

            output.writeObject(estudiante);
        } catch (IOException exc) {
        }
    }

    // Delete
    public static boolean eliminar() {
        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("No existe el archivo " + ruta);
            return false;
        }
        return archivo.delete();
    }

    public static ArrayList<Estudiante> leer() {
        File archivo = new File(ruta);
        ArrayList<Estudiante> lista = new ArrayList<>();

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("No existe el archivo " + ruta);
            return lista;
        }

        try (ObjectInputStream input
                = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                Estudiante estudiante = (Estudiante) input.readObject();
                lista.add(estudiante);
            }
        } catch (EOFException exc) {
            System.out.println("Se han leído todos los objetos!");
        } catch (ClassNotFoundException exc) {
            System.out.println("No se encontró el archivo!");
        } catch (IOException exc) {
        }
        return lista;
    }

    public static void agregar(ArrayList<Estudiante> listaEstudiantes) {
        File archivo = new File(ruta);
        boolean adicionar = archivo.exists();

        try (ObjectOutputStream output
                = new ObjectOutputStream(new FileOutputStream(archivo, adicionar))) {
            for (Estudiante estudiante : listaEstudiantes) {
                output.writeObject(estudiante);
            }
        } catch (IOException exc) {
        }
    }
}
