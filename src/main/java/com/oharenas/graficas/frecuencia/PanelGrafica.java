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
package com.oharenas.graficas.frecuencia;

import com.oharenas.util.Arreglo;
import com.oharenas.util.EstadisticaDescriptiva;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Oscar Arenas
 */
public class PanelGrafica extends JPanel {

    private String titulo;
    private Color colorEjes;
    private Color colorCajas;
    //
    private int origenX;
    private int origenY;
    private int separacionX;
    private int separacionY;
    //
    private int[] valoresDistintos;
    private int[] frecuencias;
    private int menorFrecuencia;
    private int mayorFrecuencia;

    // Medidas de tendencia central
    private double mediaAritmetica;
    private double mediana;
    private String moda;

    // Medidas de dispersión
    private int rango;
    private double desviacionMedia;
    private double desviacionEstandarTipica;
    private double desviacionEstandar;

    //
    private int[] datos;

    public PanelGrafica(int[] datos) {
        this.datos = datos;
        titulo = "";
        iniciarComponentes();
    }

    public PanelGrafica(int[] datos, String titulo) {
        this(datos);
        this.titulo = "" + titulo;
    }

    private void iniciarComponentes() {
        setBackground(Color.white);
        calcularLimites();
        calcular();
//        distribucionNormal();

        origenX = 50;
        origenY = 40;
        separacionX = 30;
        separacionY = 30;

        colorEjes = Color.black;
        colorCajas = Color.blue;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(separacionX * 2 * valoresDistintos.length, separacionY * 2 * mayorFrecuencia);
    }

    private void graficarDatos(Graphics g) {

    }

    private void graficarHistograma(Graphics g) {
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        String factor = valoresDistintos[valoresDistintos.length - 1] + "";

        if (factor.length() > 2) {
            separacionX = 10 * factor.length();
        }
        int[] cx = {origenX - 5, origenX + 5, origenX}, cy = {origenY, origenY, origenY - 11};

        g.setColor(colorEjes);
        g.drawString("f", origenX - 5, origenY - 12);
        // Cabeza flecha en el eje vertical
        g.fillPolygon(cx, cy, 3);

        // Graficar eje Y
        int distanciaY = (mayorFrecuencia + 2) * separacionY;
        g.drawLine(origenX, origenY, origenX, distanciaY);

        int distanciaX = (valoresDistintos.length + 1) * separacionX;

        // Divisiones eje Y
        for (int i = 1; i < mayorFrecuencia + 1; i++) {
            int y = distanciaY - i * separacionY;
            g.setColor(colorEjes);
            g.drawLine(origenX - 10, y, origenX, y);
            g.drawString("" + i, origenX - 30, y);
        }

        // Valores eje X
        for (int i = 0; i < valoresDistintos.length; i++) {
            int x = origenX + separacionX + i * separacionX;
            g.setColor(colorEjes);
            String cadena = "" + valoresDistintos[i];
            g.drawString(cadena, x - (5 * cadena.length()) / 2, distanciaY + 20);
//            g.setColor(colorCajas);
//            g.drawLine(x, distanciaY, x, distanciaY - frecuencias[i] * separacionY);
//            if (i % 2 == 0) {
//                 g.setColor(Color.lightGray);
//                g.fillRect(x - separacionX / 2, distanciaY - frecuencias[i] * separacionY, separacionX, frecuencias[i] * separacionY);
//            }
            g.setColor(colorCajas);
            g.drawRect(x - separacionX / 2, distanciaY - frecuencias[i] * separacionY, separacionX, frecuencias[i] * separacionY);
        }

        g.setColor(colorEjes);
        g.drawLine(origenX, distanciaY, origenX + distanciaX, distanciaY);

        // Cabeza flecha en el eje horizontal
        int[] cx2 = {origenX + distanciaX, origenX + distanciaX, origenX + distanciaX + 11}, cy2 = {distanciaY - 5, distanciaY + 5, distanciaY};
        g.fillPolygon(cx2, cy2, 3);

        if (titulo.length() > 0) {
            g.drawString(titulo, origenX + (distanciaX - 5 * titulo.length()) / 2, distanciaY + 40);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        graficarHistograma(g);
    }

    private void calcularLimites() {
        valoresDistintos = Arreglo.calcularValoresDistintos(datos);
        frecuencias = new int[valoresDistintos.length];

        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < valoresDistintos.length; j++) {
                if (datos[i] == valoresDistintos[j]) {
                    frecuencias[j]++;
                    break;
                }
            }
        }
        //
        menorFrecuencia = frecuencias[0];
        mayorFrecuencia = menorFrecuencia;

        for (int j = 1; j < frecuencias.length; j++) {
            if (frecuencias[j] < menorFrecuencia) {
                menorFrecuencia = frecuencias[j];
            } else if (frecuencias[j] > mayorFrecuencia) {
                mayorFrecuencia = frecuencias[j];
            }
        }
    }

    private void calcular() {
        mediaAritmetica = mediaAritmetica();
        mediana = calcularMediana();
        moda = calcularModa();

        //
        rango = valoresDistintos[valoresDistintos.length - 1] - valoresDistintos[0];
        desviacionMedia = EstadisticaDescriptiva.desviacionMedia(datos);
        desviacionEstandarTipica = EstadisticaDescriptiva.desviacionEstandarTipica(datos);
        desviacionEstandar = EstadisticaDescriptiva.desviacionEstandar(datos);
    }

    private double mediaAritmetica() {
        double sumatoria = 0;
        int n = 0;

        for (int i = 0; i < valoresDistintos.length; i++) {
            sumatoria += valoresDistintos[i] * frecuencias[i];
            n += frecuencias[i];
        }

        return sumatoria / n;
    }

    private double calcularMediana() {
        double me = 0;
        int n = datos.length / 2;

        if (datos.length % 2 == 0) {
            me = (datos[n - 1] + datos[n]) / 2;
        } else {
            me = datos[n];
        }

        return me;
    }

    private String calcularModa() {
        int mayor = frecuencias[0];
        String valoresMayores = "" + valoresDistintos[0];

        for (int i = 1; i < frecuencias.length; i++) {
            if (frecuencias[i] > mayor) {
                mayor = frecuencias[i];
                valoresMayores = "" + valoresDistintos[i];
            } else if (frecuencias[i] == mayor) {
                valoresMayores += ", " + valoresDistintos[i];
            }
        }
        return valoresMayores;
    }

    public String getEstadisticas() {
        String cadena = "Cantidad datos: " + datos.length;

        cadena += "\n\nMedidas de tendencia central:";
        cadena += "\n   - Media aritmética: " + mediaAritmetica;
        cadena += "\n   - Mediana: " + mediana;
        cadena += "\n   - Moda: " + moda;

        cadena += "\n\nMedidas de dispersión:";
        cadena += "\n   - Rango: " + valoresDistintos[valoresDistintos.length - 1] + " - " + valoresDistintos[0] + " = " + rango;
        cadena += "\n   - Desviación media: " + desviacionMedia;
        cadena += "\n   - Desviación estándar típica: " + desviacionEstandarTipica;
        cadena += "\n   - Desviación estándar: " + desviacionEstandar;
        cadena += "\n   - Varianza: " + (desviacionEstandar * desviacionEstandar);

        return cadena;
    }

    private void distribucionNormal() {
        for (int i = 0; i < valoresDistintos.length; i++) {
            double y = funcionNormal(valoresDistintos[i]);
//            System.out.println(valoresDistintos[i] + " => " + (y * mayorFrecuencia));
        }
    }

    private double funcionNormal(double x) {
        double factor = 1 / (desviacionEstandar * Math.sqrt(2 * Math.PI));
        double delta = x - mediaAritmetica;
        double exponente = -delta * delta / (2 * desviacionEstandar * desviacionEstandar);

        return factor * Math.exp(exponente);
    }
}
