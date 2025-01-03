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
package com.oharenas.util;

import com.oharenas.aleatorio.Aleatorio;
import com.oharenas.ed.lineal.matrizdispersa.MatrizDispersa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Oscar Arenas
 */
public class Archivo {

    //*************************************CREAR Y LEER ARCHIVOS*****************************************************************
    /**
     * Lee un archivo de texto por renglones (líneas). El texto de cada renglón
     * se almacena en un vector de tipo String en el orden de lectura.
     *
     * @param nombreArchivo
     * @return
     */
    public static String[] leerCadaLinea(String nombreArchivo) {
        String[] vector = null;
        File archivo = cargarArchivo(nombreArchivo);

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            ArrayList<String> datos = new ArrayList<>();
            // Lectura del archivo
            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }
            if (datos.isEmpty()) {
//                Consola.mostrarMensajeDeError("El archivo esta vacío.");
                return null; //return new String[0];
            }

            vector = new String[datos.size()];
            int i = 0;
            for (String fila : datos) {
                vector[i++] = fila;
            }
        } catch (FileNotFoundException exc) {
            Consola.mostrarMensajeDeError("No existe el archivo: " + archivo.getAbsoluteFile().getAbsolutePath());
        } catch (IOException exc) {
        } catch (Exception exc) {
        }
        return vector;
    }

    private static File cargarArchivo(String nombreArchivo) {
        if (!(nombreArchivo.endsWith(".txt") || nombreArchivo.endsWith(".csv") || nombreArchivo.endsWith(".grf"))) {
            Consola.printRed("Error: archivo con extensión incorrecta '" + nombreArchivo + "'");
            return null;
        }
//
//        String currentDir = System.getProperty("user.dir");
//        String ruta = currentDir + File.separator + nombreArchivo;
//
//        String OS = System.getProperty("os.name").toLowerCase();
//
//        if (OS.contains("win")) {
//            ruta = currentDir + "\\" + nombreArchivo;
//        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
//            ruta = currentDir + "/" + nombreArchivo;
//        } else if (OS.contains("mac")) {
//            ruta = currentDir + "/" + nombreArchivo;
//        }
        return new File(nombreArchivo);
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en un
     * vector de tipo String. El archivo solo debe tener valores separados por
     * un punto y coma.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @return Retorna una vector de tipo String.
     */
    public static String[] leerVector(String nombreArchivo) {
        return leerVector(nombreArchivo, " ");
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en un
     * vector de tipo String.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @param separador Caracter que separa los valores por columnas.
     * @return Retorna una vector de tipo String. Si no hay datos en el archivo
     * retorna null.
     */
    public static String[] leerVector(String nombreArchivo, String separador) {
        if (separador == null || separador.isEmpty()) {
            separador = " ";
        }
        String[] vector = leerCadaLinea(nombreArchivo);

        if (vector != null) {
            ArrayList<String> datos = new ArrayList<>();

            for (String fila : vector) {
                String[] cadenaDivida = fila.split(separador);
                for (String cadenaDivida1 : cadenaDivida) {
                    String cadena = cadenaDivida1.trim();
                    if (cadena.length() > 0) {
                        datos.add(cadena);
                    }
                }
            }
            if (datos.size() > 0) {
                // Creamos vector y le asignamos los valores cargados del 
                // archivo de texto
                vector = new String[datos.size()];
                int k = 0;
                for (String valor : datos) {
                    vector[k++] = valor;
                }
                return vector;
            }
        }
        return null;
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en un
     * vector de tipo entero. El archivo solo debe tener valores enteros
     * separados espacio en blanco o cambio de línea.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @return Retorna una vector de tipo entero.
     */
    public static int[] leerVectorEntero(String nombreArchivo) {
        return leerVectorEntero(nombreArchivo, " ");
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en un
     * vector de tipo entero. El archivo solo debe tener valores enteros
     * separados por un espacio en blanco o cambio de línea.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @param separador
     * @return Retorna una vector de tipo entero.
     */
    public static int[] leerVectorEntero(String nombreArchivo, String separador) {
        String[] datos = leerVector(nombreArchivo, separador);
        int[] vector = null;

        if (datos != null) {
            vector = new int[datos.length];
            int k = 0;
            for (String valor : datos) {
                try {
                    valor = Util.eliminarEspacios(valor);
                    vector[k] = Integer.parseInt(valor);
                    k++;
                } catch (NumberFormatException exc) {
                    Consola.printRed("Error al leer archivo: El valor '" + valor + "' no es un número entero. Solo se admiten valores enteros.");
                    return null;
                }
            }
        }
        return vector;
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en un
     * vector de tipo real. El archivo solo debe tener valores reales o enteros
     * separados por un espacio en blanco o cambio de línea.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @return Retorna una vector de tipo real.
     */
    public static double[] leerVectorReal(String nombreArchivo) {
        return leerVectorReal(nombreArchivo, " ");
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en un
     * vector de tipo real. El archivo solo debe tener valores reales o enteros
     * separados por un espacio en blanco o cambio de línea.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @param separador Caracter que separa los valores por columnas.
     * @return Retorna una vector de tipo real.
     */
    public static double[] leerVectorReal(String nombreArchivo, String separador) {
        String[] datos = leerVector(nombreArchivo, separador);
        double[] vector = null;

        if (datos != null) {
            vector = new double[datos.length];
            int k = 0;
            for (String valor : datos) {
                try {
                    if (valor != null) {
                        valor = Util.eliminarEspacios(valor);
                    }
                    if (valor == null || valor.isEmpty() || valor.compareTo("NULL") == 0) {
                        vector[k] = Double.NaN;
                    } else {
                        vector[k] = Double.parseDouble(valor);
                    }
                    k++;
                } catch (NumberFormatException exc) {
                    Consola.printRed("Error al leer archivo: El valor '" + valor + "' no es un número real. Solo se admiten valores reales.");
                    return null;
                }
            }
        }
        return vector;
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en una
     * matriz de tipo String.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @return Retorna una matriz de tipo String.
     */
    public static String[][] leerMatriz(String nombreArchivo) {
        return leerMatriz(nombreArchivo, " ");
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en una
     * matriz de tipo String. El archivo solo debe tener valores String
     * dispuestos en filas. Cada fila no vacía del archivo representa una fila
     * en la matriz.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @param separador Caracter que separa los valores por columnas.
     * @return Retorna una matriz de tipo String.
     */
    public static String[][] leerMatriz(String nombreArchivo, String separador) {
        if (separador == null || separador.isEmpty()) {
            separador = " ";
        }

        String[][] matriz = null;
        String[] renglonesArchivo = leerCadaLinea(nombreArchivo);

        if (renglonesArchivo != null) {
            ArrayList<String> datos = new ArrayList<>();

            // Borramos espacios adicionales en blanco de cada fila y 
            // también eliminamos filas en blanco
            for (String fila : renglonesArchivo) {
                fila = Util.eliminarEspacios(fila);
                if (fila.length() > 0) {
                    datos.add(fila);
                }
            }

            if (datos.size() > 0) {
                datos.clear();
                String[][] cadenaDivida = new String[datos.size()][];
                int k = 0;
                int columnas = 0;
                // Verificamos que las columnas tengan el mismo tamaño.
                for (String fila : datos) {
                    cadenaDivida[k] = fila.split(separador);
                    if (k == 0) {
                        columnas = cadenaDivida[k].length;
                    } else if (columnas != cadenaDivida[k].length) {
                        Consola.mostrarMensajeDeError("Al leer archivo a una matriz: La filas no tienen el mismo tamaño. Fila: " + (k + 1));
                        return null;
                    }
                    ++k;
                }
                // Creamos matriz y le asignamos los valores cargados del archivo de texto
                matriz = new String[datos.size()][columnas];
                k = 0;
                for (String[] fila : cadenaDivida) {
                    for (int i = 0; i < fila.length; i++) {
                        try {
                            if (fila[i] != null) {
                                fila[i] = Util.eliminarEspacios(fila[i]);
                            }
                            if (fila[i] != null && !fila[i].isEmpty()) {
                                matriz[k][i] = fila[i];
                            } else {
                                matriz[k][i] = "NULL";
                            }
                        } catch (NumberFormatException exc) {
                            Consola.mostrarMensajeDeError("Al leer archivo: El valor '" + fila[i] + "' no es un número entero. Solo se admiten valores enteros.");
                            return null;
                        }
                    }
                    ++k;
                }
            } else {
                Consola.mostrarMensajeDeError("Al leer archivo a una matriz: No hay filas");
                return null;
            }
        }

        return matriz;
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en una
     * matriz de tipo entero. El archivo solo debe tener valores enteros
     * dispuestos en filas y separados por un espacio en blanco. Cada fila no
     * vacía del archivo representa una fila en la matriz de enteros.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @return Retorna una matriz de tipo entero.
     */
    public static int[][] leerMatrizEntera(String nombreArchivo) {
        return leerMatrizEntera(nombreArchivo, " ");
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en una
     * matriz de tipo entero. El archivo solo debe tener valores enteros
     * dispuestos en filas y separados por un espacio en blanco. Cada fila no
     * vacía del archivo representa una fila en la matriz de enteros.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @param separador Caracter que separa los valores por columnas.
     * @return Retorna una matriz de tipo entero.
     */
    public static int[][] leerMatrizEntera(String nombreArchivo, String separador) {
        String[][] datos = leerMatriz(nombreArchivo, separador);
        int[][] matriz = null;

        if (datos != null) {
            matriz = new int[datos.length][datos[0].length];
            int k = 0;
            for (String[] fila : datos) {
                for (int i = 0; i < fila.length; i++) {
                    try {
                        matriz[k][i] = Integer.parseInt(fila[i]);
                    } catch (NumberFormatException exc) {
                        Consola.printRed("Error al leer archivo: El valor '" + fila[i] + "' no es un número entero. Solo se admiten valores enteros.");
                        return null;
                    }
                }
                ++k;
            }
        }
        return matriz;
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en una
     * matriz de tipo real. El archivo solo debe tener valores reales o enteros
     * dispuestos en filas y separados por un espacio en blanco. Cada fila no
     * vacía del archivo representa una fila en la matriz de enteros.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @return Retorna una matriz de tipo real.
     */
    public static double[][] leerMatrizReal(String nombreArchivo) {
        return leerMatrizReal(nombreArchivo, " ");
    }

    /**
     * Copia los valores de un archivo de texto plano y los almacena en una
     * matriz de tipo real. El archivo solo debe tener valores reales o enteros
     * dispuestos en filas y separados por un espacio en blanco. Cada fila no
     * vacía del archivo representa una fila en la matriz de enteros.
     *
     * @param nombreArchivo Cadena con el nombre del archivo y la extensión
     * '.txt'
     * @param separador Caracter que separa los valores por columnas
     * @return Retorna una matriz de tipo real.
     */
    public static double[][] leerMatrizReal(String nombreArchivo, String separador) {
        String[][] datos = leerMatriz(nombreArchivo, separador);
        double[][] matriz = null;

        if (datos != null) {
            matriz = new double[datos.length][datos[0].length];
            int k = 0;
            for (String[] fila : datos) {
                for (int i = 0; i < fila.length; i++) {
                    try {
                        if (fila[i] != null) {
                            fila[i] = Util.eliminarEspacios(fila[i]);
                        }
                        if (fila[i] == null || fila[i].isEmpty()) {
                            matriz[k][i] = Double.NaN;
                        } else {
                            matriz[k][i] = Double.parseDouble(fila[i]);
                        }
                    } catch (NumberFormatException exc) {
                        Consola.printRed("Error al leer archivo: El valor '" + fila[i] + "' no es un número real. Solo se admiten valores reales.");
                        return null;
                    }
                }
                ++k;
            }
        }
        return matriz;
    }

    public static void crearTexto(String datos[], String nombreArchivo) {
        File archivo = cargarArchivo(nombreArchivo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String dato : datos) {
                bw.write(dato + "\n");
            }
        } catch (Exception e) {
            Consola.printRed("Error al crear el archivo \'" + nombreArchivo + ".txt\'");
        }
    }

    public static void crearTexto(ArrayList<String> datos, String nombreArchivo) {
        File archivo = cargarArchivo(nombreArchivo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String dato : datos) {
                bw.write(dato + "\n");
            }
        } catch (Exception e) {
            Consola.printRed("Error al crear el archivo \'" + nombreArchivo + ".txt\'");
        }
    }

    public static void crearTexto(String datos, String nombreArchivo) {
        crearTexto(datos.split("\n"), nombreArchivo);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores enteros de datos. El
     * archivo resultante separa los valores mediante un espacio en blanco. Si
     * el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Vector que contiene los valores enteros que se almacenarán
     * en el archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(int[] datos, String nombreArchivo) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores enteros del vector datos.
     * El archivo resultante separa los valores mediante el separador indicado.
     * Si el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Vector que contiene los valores enteros que se almacenarán
     * en el archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     * @param separador Caracter que separa los valores por columnas
     */
    public static void crearArchivo(int[] datos, String nombreArchivo, String separador) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo, separador);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores reales del vector datos.
     * El archivo resultante separa los valores mediante un punto y coma. Si el
     * archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Vector que contiene los valores reales que se almacenarán en
     * el archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(double[] datos, String nombreArchivo) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores reales del vector datos.
     * El archivo resultante separa los valores mediante el separador indicado.
     * Si el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Vector que contiene los valores reales que se almacenarán en
     * el archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     * @param separador Caracter que separa los valores por columnas
     */
    public static void crearArchivo(double[] datos, String nombreArchivo, String separador) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo, separador);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores del vector datos. El
     * archivo resultante separa los valores mediante un espacio en blanco. Si
     * el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Vector que contiene los valores que se almacenarán en el
     * archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(String[] datos, String nombreArchivo) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo, ";");
    }

    public static void crearArchivo(String[] datos, String nombreArchivo, String separador) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo, separador);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores enteros del arreglo
     * datos. El archivo resultante separa los valores mediante un punto y coma.
     * Si el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Matriz que contiene los valores enteros que se almacenarán
     * en el archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(int[][] datos, String nombreArchivo) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo);
    }

    public static void crearArchivo(byte[][] datos, String nombreArchivo) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores reales del arreglo datos.
     * El archivo resultante separa los valores mediante un punto y coma. Si el
     * archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Matriz que contiene los valores reales que se almacenarán en
     * el archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(double[][] datos, String nombreArchivo) {
        crearArchivo(Arreglo.convertirAMatrizString(datos), nombreArchivo);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores de datos y la primera
     * fila contiene los elementos del parametro encabezado. El archivo
     * resultante puede separar los datos por columnas mediante un punto y coma.
     * Si el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Matriz que contiene los valores que se almacenarán en el
     * archivo.
     * @param encabezado Nombres de cada una de las columnas de los datos.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(String[][] datos, String[] encabezado, String nombreArchivo) {
        if (datos[0].length == encabezado.length) {
            String[][] datosConEncabezado = new String[datos.length + 1][encabezado.length];

            for (int i = 0; i < encabezado.length; i++) {
                datosConEncabezado[0][i] = encabezado[i];
            }
            for (int i = 1; i < datosConEncabezado.length; i++) {
                for (int j = 0; j < datosConEncabezado[i].length; j++) {
                    datosConEncabezado[i][j] = datos[i - 1][j];
                }
            }
            datos = datosConEncabezado;
        }
        crearArchivo(datos, nombreArchivo);
    }

    /**
     * Crea un archivo de texto (.txt) con los valores de datos . El archivo
     * resultante puede separar los datos por columnas mediante un punto y coma.
     * Si el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Matriz que contiene los valores que se almacenarán en el
     * archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     */
    public static void crearArchivo(String[][] datos, String nombreArchivo) {
        crearArchivo(datos, nombreArchivo, ";");
    }

    /**
     * Crea un archivo de texto (.txt) con los valores de datos . El archivo
     * resultante puede separar los datos por columnas mediante un punto y coma.
     * Si el archivo ya existe lo borra y lo crea nuevamente.
     *
     * @param datos Matriz que contiene los valores que se almacenarán en el
     * archivo.
     * @param nombreArchivo Nombre del archivo de texto donde se guardarán los
     * datos.
     * @param separador Caracter que separa los valores por columnas
     */
    public static void crearArchivo(String[][] datos, String nombreArchivo, String separador) {
        File archivo = cargarArchivo(nombreArchivo);

        int maximoColumnas = 0;
        for (int i = 0; i < datos.length; i++) {
            if (datos[i].length > maximoColumnas) {
                maximoColumnas = datos[i].length;
            }
        }

        if (separador == null || separador.isEmpty()) {
            separador = " ";
        }

        int[] maximoPorColumna = new int[maximoColumnas];
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                String elemento = datos[i][j];
                elemento = Util.eliminarEspacios(elemento);
                if (elemento.isEmpty()) {
                    elemento = "NULL";
                    datos[i][j] = "NULL";
                }
                int longitud = elemento.length();
                if (longitud > maximoPorColumna[j]) {
                    maximoPorColumna[j] = longitud;
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 0; i < datos.length; i++) {
                String registro = "";
                for (int j = 0; j < datos[i].length - 1; j++) {
                    int longitud = datos[i][j].length();
                    int delta = maximoPorColumna[j] - longitud;
                    for (int k = 0; k < delta; k++) {
                        registro += " ";
                    }
                    registro += datos[i][j] + separador + " ";

                }
                registro += datos[i][datos[i].length - 1];
                if (i != datos.length - 1) {
                    registro += "\n";
                }
                bw.write(registro);
            }
        } catch (Exception exc) {
            Consola.printRed("Error al crear el archivo \'" + nombreArchivo + ".txt\'");
        }
    }

    public static void crearArchivo(String datos, String rutaArchivo) {
        try (FileWriter salida = new FileWriter(rutaArchivo)) {
            salida.write(datos);
        } catch (IOException exc) {
        }
    }

    public static void crearCSV(String[][] datos, String nombreArchivo) {
        crearArchivo(datos, nombreArchivo, ",");
    }

    public static String[][] leerCSV(String nombreArchivo) {
        return leerMatriz(nombreArchivo, ",");
    }

    public static void crearArchivoMatrizDensa(int filas, int columnas, int a, int b, int decimales, String rutaAbsoluta) {
        if (filas < 1 || columnas < 1) {
            Consola.printRed("Error al crear el archivo \'" + rutaAbsoluta + ".txt\'");
            return;
        }
        long n = Math.abs(filas * columnas);

        double random = Aleatorio.real(1, 40, 2) / (double) 100;
        random /= 10;
        int m = (int) (0.001 * n);

        MatrizDispersa matriz = new MatrizDispersa(filas, columnas);
        System.out.println("T: " + m);
        while (m-- > 0) {
            int f = Aleatorio.entero(filas);
            int c = Aleatorio.entero(columnas);
            double v = Aleatorio.real(a, b, decimales);
            matriz.agregar(f, c, v);
        }
//        System.out.println(matriz);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaAbsoluta))) {
            bw.write(filas + " " + columnas + "\n");
            for (int i = 0; i < filas; i++) {
                String valor = Util.realAString(matriz.obtener(i, 0));
                bw.write(valor);
                for (int j = 1; j < columnas; j++) {
                    valor = Util.realAString(matriz.obtener(i, j));
                    bw.write(" " + valor);
                }
                bw.write("\n");
            }
        } catch (Exception exc) {
            Consola.printRed("Error al crear el archivo \'" + rutaAbsoluta + ".txt\'");
        }
    }
}
