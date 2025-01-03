/*
 * Copyright (C) 2018 Oscar Arenas
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

import com.oharenas.graficas.frecuencia.VentanaGraficaFrecuencia;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Oscar Arenas
 */
public class EstadisticaDescriptiva {

    public static int rango(int[] datos) {
        int[] r = minMax(datos);

        return r[1] - r[0];
    }

    public static double rango(double[] datos) {
        double[] r = minMax(datos);

        return r[1] - r[0];
    }

    public static double mediaAritmetica(int[] datos) {
        int sumatoria = 0;
        for (int i = 0; i < datos.length; i++) {
            sumatoria += datos[i];
        }
        return sumatoria / datos.length;
    }

    public static double mediaAritmetica(double[] datos) {
        double sumatoria = 0;
        for (int i = 0; i < datos.length; i++) {
            sumatoria += datos[i];
        }
        return sumatoria / datos.length;
    }

    public static double desviacionMedia(int[] datos) {
        double sumatoria = 0;
        double media = mediaAritmetica(datos);
        for (int i = 0; i < datos.length; i++) {
            sumatoria += Math.abs(datos[i] - media);
        }
        return sumatoria / datos.length;
    }

    public static double desviacionMedia(double[] datos) {
        double sumatoria = 0;
        double media = mediaAritmetica(datos);
        for (int i = 0; i < datos.length; i++) {
            sumatoria += Math.abs(datos[i] - media);
        }
        return sumatoria / datos.length;
    }

    public static double desviacionEstandar(int[] datos) {
        double sumatoria = 0;
        double media = mediaAritmetica(datos);
        for (int i = 0; i < datos.length; i++) {
            double delta = datos[i] - media;
            sumatoria += delta * delta;
        }
        return sumatoria / (datos.length - 1);
    }

    public static double desviacionEstandar(double[] datos) {
        double sumatoria = 0;
        double media = mediaAritmetica(datos);
        for (int i = 0; i < datos.length; i++) {
            double delta = datos[i] - media;
            sumatoria += delta * delta;
        }
        return sumatoria / (datos.length - 1);
    }

    public static double desviacionEstandarTipica(int[] datos) {
        double sumatoria = 0;
        double media = mediaAritmetica(datos);
        for (int i = 0; i < datos.length; i++) {
            double delta = datos[i] - media;
            sumatoria += delta * delta;
        }
        return sumatoria / datos.length;
    }

    public static double desviacionEstandarTipica(double[] datos) {
        double sumatoria = 0;
        double media = mediaAritmetica(datos);
        for (int i = 0; i < datos.length; i++) {
            double delta = datos[i] - media;
            sumatoria += delta * delta;
        }
        return sumatoria / datos.length;
    }

    // ********************************* GRAFICAS *********************************************
    public static void histogramaNotas(double[] notas) {
        int[] f = new int[6];

        for (double nota : notas) {
            if (nota >= 0 && nota <= 5) {
                if (nota < 1) {
                    f[0]++;
                } else if (nota < 2) {
                    f[1]++;
                } else if (nota < 3) {
                    f[2]++;
                } else if (nota < 4) {
                    f[3]++;
                } else if (nota < 5) {
                    f[4]++;
                } else {
                    f[5]++;
                }
            } else {
                System.out.println("Nota fuera de rango: " + nota);
                return;
            }
        }

        int mayor = calcularMayor(f);
        int longitud = (mayor + "").length();

        String ejeX = "";
        for (int i = 0; i < longitud + 2; i++) {
            ejeX += " ";
        }
        ejeX += "0 1 2 3 4 5 ";

        if (mayor > 19) {
            Consola.printRed(ejeX);
        }

        for (int i = mayor; i > 0; i--) {
            String fila = i + "";
            for (int j = 0; j < longitud - fila.length(); j++) {
                System.out.print(" ");
            }
            Consola.print(i + ": ", Consola.RED);

            for (int j = 0; j < f.length; j++) {
                if (i == f[j]) {
                    f[j]--;
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }

        Consola.printRed(ejeX);
    }

    public static int calcularMenor(int[] vector) {
        int menor = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < menor) {
                menor = vector[i];
            }
        }
        return menor;
    }

    public static double calcularMenor(double[] vector) {
        double menor = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < menor) {
                menor = vector[i];
            }
        }
        return menor;
    }

    public static int calcularMayor(int[] vector) {
        int mayor = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] > mayor) {
                mayor = vector[i];
            }
        }
        return mayor;
    }

    public static double calcularMayor(double[] vector) {
        double mayor = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] > mayor) {
                mayor = vector[i];
            }
        }
        return mayor;
    }

    public static int[] minMax(int[] vector) {
        int[] aux = new int[2];

        aux[0] = vector[0];
        aux[1] = vector[0];

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < aux[0]) {
                aux[0] = vector[i];
            } else if (vector[i] > aux[1]) {
                aux[1] = vector[i];
            }
        }
        return aux;
    }

    public static double[] minMax(double[] vector) {
        double[] aux = new double[2];

        aux[0] = vector[0];
        aux[1] = vector[0];

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < aux[0]) {
                aux[0] = vector[i];
            } else if (vector[i] > aux[1]) {
                aux[1] = vector[i];
            }
        }
        return aux;
    }

    public static void graficarHistograma(int[] datos) {
        graficarHistograma(datos, "");
    }

    public static void graficarHistograma(int[] datos, String tituloEjeHorizontal) {
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }
        VentanaGraficaFrecuencia ventana = new VentanaGraficaFrecuencia(datos, tituloEjeHorizontal);
    }
}
