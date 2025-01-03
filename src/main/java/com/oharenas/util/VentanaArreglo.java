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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Oscar Arenas
 */
class VentanaArreglo extends JFrame {

    private JScrollPane scrollPane;
    private Canvas canvas;
    private String titulo;

    public VentanaArreglo(String[][] arreglo) {
        canvas = new Canvas(arreglo, this);
        iniciarComponentes();
    }

    public VentanaArreglo(String[][] arreglo, String titulo) {
        canvas = new Canvas(arreglo, this);
        iniciarComponentes();
    }

    public VentanaArreglo(int[][] arreglo) {
        String[][] aux = new String[arreglo.length][];

        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] != null) {
                aux[i] = new String[arreglo[i].length];
                for (int j = 0; j < arreglo[i].length; j++) {
                    aux[i][j] = arreglo[i][j] + "";
                }
            } else {
                aux[i] = new String[0];
            }
        }
        canvas = new Canvas(aux, this);
        iniciarComponentes();
    }

    public VentanaArreglo(double[][] arreglo) {
        String[][] aux = new String[arreglo.length][];

        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] != null) {
                aux[i] = new String[arreglo[i].length];
                for (int j = 0; j < arreglo[i].length; j++) {
                    aux[i][j] = Util.realAString(arreglo[i][j]);
                }
            } else {
                aux[i] = new String[0];
            }
        }
        canvas = new Canvas(aux, this);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        scrollPane = new JScrollPane(canvas);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //
        add(scrollPane, BorderLayout.CENTER);

        setTitle("Arreglo");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    class Canvas extends JPanel {

        private VentanaArreglo ventanaArreglo;
        private String[][] arreglo;
        private int valorMayorColumna;
        private int[] tamanioFila;
        private String cadenaMayorLongitud;
        private int maximaColumna;
        private int x0;
        private int y0;
        private boolean esMatriz;
        private Font tipoLetraCadenaValores;
        private Font tipoLetraIndices;
        private Color colorFondo;

        private int anchoPanel;
        private int alturaPanel;

        public Canvas(String[][] arreglo, VentanaArreglo ventanaArreglo) {
            this.ventanaArreglo = ventanaArreglo;
            iniciarComponentes(arreglo);
        }

        private void iniciarComponentes(String[][] array) {
            anchoPanel = 100;
            alturaPanel = 50;
            setBackground(Color.white);
            this.arreglo = new String[array.length][];
            tamanioFila = new int[array.length];
            valorMayorColumna = 0;
            esMatriz = true;

            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    this.arreglo[i] = array[i].clone();
                    tamanioFila[i] = this.arreglo[i].length;
                    if (this.arreglo[i].length > valorMayorColumna) {
                        valorMayorColumna = this.arreglo[i].length;
                    }
                    if (this.arreglo[i].length != valorMayorColumna) {
                        esMatriz = false;
                    }
                } else {
                    this.arreglo[i] = new String[0];
                    esMatriz = false;
                }
            }

            // Para calcular el ancho máximo de las columnas
            cadenaMayorLongitud = valorMayorColumna + "";
            int anchoMaximoColumnas = cadenaMayorLongitud.length();

            for (int i = 0; i < this.arreglo.length; i++) {
                if (this.arreglo[i].length > maximaColumna) {
                    maximaColumna = this.arreglo[i].length;
                }
                for (int j = 0; j < this.arreglo[i].length; j++) {
                    int v = this.arreglo[i][j].length();
                    if (v > anchoMaximoColumnas) {
                        anchoMaximoColumnas = v;
                        cadenaMayorLongitud = this.arreglo[i][j];
                    }
                }
            }

            x0 = 50;
            y0 = 70;
            tipoLetraCadenaValores = new Font(Font.MONOSPACED, Font.BOLD, 26);
            tipoLetraIndices = new Font(Font.SERIF, Font.BOLD, 22);
            colorFondo = new Color(255, 255, 204);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g.setFont(tipoLetraCadenaValores);
            int anchoFont = g2.getFontMetrics().stringWidth(cadenaMayorLongitud);
            int alturaFont = g2.getFontMetrics().getHeight() / 2;

            if (esMatriz && valorMayorColumna > 0) {
                int anchoCelda = anchoFont + 10;
                int alturaCelda = 40;

                if (anchoCelda < alturaCelda) {
                    anchoCelda = alturaCelda;
                }

                int mayorAnchoNombresFilas = 0;
                for (int i = 0; i < arreglo.length; i++) {
                    int anchoFont2 = g2.getFontMetrics().stringWidth(i + "");
                    if (anchoFont2 > mayorAnchoNombresFilas) {
                        mayorAnchoNombresFilas = anchoFont2;
                    }
                }

                int margenX = 40;
                int delta = 5;
                x0 = margenX + mayorAnchoNombresFilas + 2 * delta;

                g.setColor(colorFondo);
                g.fillRect(x0, y0, anchoCelda * arreglo[0].length, alturaCelda * arreglo.length);

                g.setColor(Color.black);
                g.drawRect(x0, y0, anchoCelda * arreglo[0].length, alturaCelda * arreglo.length);

                // Lineas horizontales
                for (int i = 1; i < arreglo.length; i++) {
                    int y = y0 + alturaCelda * i;
                    g.drawLine(x0, y, x0 + anchoCelda * arreglo[0].length, y);
                }

                g.setFont(tipoLetraIndices);
                // Nombres filas
                for (int i = 0; i < arreglo.length; i++) {
                    int y = y0 + alturaCelda * i + (alturaCelda + alturaFont) / 2;
                    g2.drawString(i + "", x0 - mayorAnchoNombresFilas - delta, y);
                }

                // Lineas verticales
                for (int i = 1; i < arreglo[0].length; i++) {
                    int x = x0 + anchoCelda * i;
                    g.drawLine(x, y0, x, y0 + alturaCelda * arreglo.length);
                }

                for (int i = 0; i < arreglo[0].length; i++) {
                    int anchoFont2 = g2.getFontMetrics().stringWidth(i + "");
                    int x = x0 + anchoCelda * i + (anchoCelda - anchoFont2) / 2;
                    g2.drawString(i + "", x, y0 - 4);
                }

                boolean cambio = false;
                if (2 * x0 + mayorAnchoNombresFilas + anchoCelda * arreglo[0].length > anchoPanel) {
                    anchoPanel = 2 * x0 + mayorAnchoNombresFilas + anchoCelda * arreglo[0].length;
                    cambio = true;
                }
                if (2 * y0 + alturaCelda * (arreglo.length + 1) > alturaPanel) {
                    alturaPanel = 2 * y0 + alturaCelda * (arreglo.length + 1);
                    cambio = true;
                }
                if (cambio) {
                    ventanaArreglo.setSize(anchoPanel, alturaPanel);
                    ventanaArreglo.setTitle("Arreglo " + arreglo.length + "x" + arreglo[0].length);
                    ventanaArreglo.setLocationRelativeTo(null);
                }

                g.setFont(tipoLetraCadenaValores);
                g.setColor(Color.blue);
                for (int i = 0; i < arreglo.length; i++) {
                    int y = y0 + alturaCelda * i + (alturaCelda + alturaFont) / 2;
                    for (int j = 0; j < arreglo[0].length; j++) {
                        int anchoFont2 = g2.getFontMetrics().stringWidth(arreglo[i][j]);
                        int x = x0 + anchoCelda * j + (anchoCelda - anchoFont2) / 2;
                        g2.drawString(arreglo[i][j], x, y);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(anchoPanel, alturaPanel); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class ArregloBidimensional {

        private String[][] arreglo;

        public ArregloBidimensional(String[][] arreglo) {
            this.arreglo = arreglo;
        }

    }
}
