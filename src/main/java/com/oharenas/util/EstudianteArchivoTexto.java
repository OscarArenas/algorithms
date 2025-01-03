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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Oscar Arenas
 */
public class EstudianteArchivoTexto {

    public static String ruta = "lista.txt";
    public static String delimitador = ",";

    // CRUD: Operaciones básicas sobre datos en archivos
    // Create (C): crear archivo y/o agregar estudiante
    // Read   (R): leer, consultar
    // Update (U): actualizar, modificar
    // Delete (D): borrar, eliminar
    //
    // Create: crear archivo y/o agregar estudiante
    public static void agregar(Estudiante estudiante) {
        File archivo = new File(ruta);
        boolean adicionar = archivo.exists();

        try (FileWriter salida = new FileWriter(archivo, adicionar)) {
            salida.write(estudiante.getNuip() + delimitador);
            salida.write(estudiante.getNombre() + delimitador);
            salida.write(estudiante.getApellido() + delimitador);
            salida.write(estudiante.getGenero() + delimitador);
            salida.write(estudiante.getNota() + "\n");
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo: " + ruta);
        } catch (Exception ex) {
        }
    }

    // Read
    public static Estudiante leer(String nuip) {
        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("No existe el archivo: " + ruta);
            return null;
        }

        try (BufferedReader entrada
                = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                String[] datos = linea.split(delimitador);

                if (nuip.compareTo(datos[0]) == 0) {
                    int genero = Integer.parseInt(datos[3]);
                    double nota = Double.parseDouble(datos[4]);

                    Estudiante estudiante = new Estudiante(datos[0], datos[1],
                            datos[2], genero, nota);
                    entrada.close();
                    return estudiante;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontró el archivo: " + ruta);
        } catch (IOException exc) {
        } finally {
        }
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
            System.out.println("No existe el archivo: " + ruta);
            return false;
        }
        return archivo.delete();
    }

    // Create: crear archivo y/o agregar estudiantes
    public static void agregar(ArrayList<Estudiante> lista) {
        File archivo = new File(ruta);
        boolean adicionar = archivo.exists();

        try (FileWriter salida = new FileWriter(archivo, adicionar)) {
            for (Estudiante estudiante : lista) {
                salida.write(estudiante.getNuip() + delimitador);
                salida.write(estudiante.getNombre() + delimitador);
                salida.write(estudiante.getApellido() + delimitador);
                salida.write(estudiante.getGenero() + delimitador);
                salida.write(estudiante.getNota() + "\n");
            }
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo: " + ruta);
        } catch (Exception ex) {
        }
    }

    // Read
    public static ArrayList<Estudiante> leer() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("No existe el archivo: " + ruta);
            return lista;
        }

        try (BufferedReader entrada
                = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                String[] datos = linea.split(delimitador);
                int genero = Integer.parseInt(datos[3]);
                double nota = Double.parseDouble(datos[4]);

                Estudiante estudiante = new Estudiante(datos[0], datos[1],
                        datos[2], genero, nota);
                lista.add(estudiante);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontró el archivo: " + ruta);
        } catch (IOException exc) {
        } finally {
        }
        return lista;
    }

    public static void agregar2(ArrayList<Estudiante> lista) {
        File archivo = new File(ruta);

        try (PrintWriter salida = new PrintWriter(archivo)) {
            for (Estudiante estudiante : lista) {
                salida.print(estudiante.getNuip() + delimitador);
                salida.print(estudiante.getNombre() + delimitador);
                salida.print(estudiante.getApellido() + delimitador);
                salida.print(estudiante.getGenero() + delimitador);
                salida.print(estudiante.getNota() + "\n");
            }
        } catch (IOException ex) {
        } catch (Exception ex) {
        }
    }

    public static ArrayList<Estudiante> leer2() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("No existe archivo: " + ruta);
            return lista;
        }
        try (Scanner entrada = new Scanner(archivo)) {
            while (entrada.hasNext()) {
                String linea = entrada.nextLine();
                String[] datos = linea.split(delimitador);

                int genero = Integer.parseInt(datos[3]);
                double nota = Double.parseDouble(datos[4]);

                Estudiante estudiante = new Estudiante(datos[0], datos[1],
                        datos[2], genero, nota);
                lista.add(estudiante);
            }
        } catch (FileNotFoundException exc) {
            System.err.println("Error al abrir el archivo: " + ruta);
        } catch (NumberFormatException exc) {
            System.err.println("Cadena no se puede convertir a entero o real");
        } catch (InputMismatchException exc) {
            System.err.println("Tipo de dato incorrecto");
        }
        return lista;
    }
}
