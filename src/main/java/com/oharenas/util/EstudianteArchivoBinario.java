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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public class EstudianteArchivoBinario {

    public static String ruta = "lista.dat";

    // CRUD: Operaciones básicas sobre datos
    // Create (C): crear archivo y/o agregar estudiante
    // Read   (R): leer, consultar
    // Update (U): actualizar, modificar
    // Delete (D): borrar, eliminar
    //
    // Create: crea archivo y/o agregar estudiante
    public static void agregar(Estudiante estudiante) {
        File archivo = new File(ruta);
        boolean adicionar = archivo.exists();

        try (DataOutputStream salida
                = new DataOutputStream(new FileOutputStream(archivo, adicionar))) {
            salida.writeUTF(estudiante.getNuip());
            salida.writeUTF(estudiante.getNombre());
            salida.writeUTF(estudiante.getApellido());
            salida.writeInt(estudiante.getGenero());
            salida.writeDouble(estudiante.getNota());
        } catch (IOException exc) {
        }
    }

    // Read
    public static Estudiante leer(String nuip) {
        return null;
    }

    // Read
    public static Estudiante leer(int indice) {
        return null;
    }

    // Update
    public static boolean actualizar(String nuip, Estudiante nuevo) {
        return false;
    }

    // Update
    public static boolean actualizar(Estudiante anterior, Estudiante nuevo) {
        return false;
    }

    // Delete
    public static boolean eliminar(String nuip) {
        return false;
    }

    // Delete
    public static boolean eliminar(Estudiante estudiante) {
        return false;
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

    // Read
    public static ArrayList<Estudiante> leer() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("No existe el archivo " + ruta);
            return lista;
        }

        try (DataInputStream entrada
                = new DataInputStream(new FileInputStream(archivo))) {
            while (true) {
                String nuip = entrada.readUTF();
                String nombre = entrada.readUTF();
                String apellido = entrada.readUTF();
                int genero = entrada.readInt();
                double nota = entrada.readDouble();

                lista.add(new Estudiante(nuip, nombre, apellido, genero, nota));
            }
        } catch (EOFException exc) {
            System.out.println("Se leyeron todos los datos!");
        } catch (IOException exc) {
        }

        return lista;
    }

    // Create: crea archivo y/o agregar estudiantes
    public static void agregar(ArrayList<Estudiante> lista) {
        File archivo = new File(ruta);
        boolean adicionar = archivo.exists();

        try (DataOutputStream salida
                = new DataOutputStream(new FileOutputStream(archivo, adicionar))) {

            for (Estudiante estudiante : lista) {
                salida.writeUTF(estudiante.getNuip());
                salida.writeUTF(estudiante.getNombre());
                salida.writeUTF(estudiante.getApellido());
                salida.writeInt(estudiante.getGenero());
                salida.writeDouble(estudiante.getNota());
            }
        } catch (IOException exc) {
        }
    }
}
