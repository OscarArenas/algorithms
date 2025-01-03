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
package com.oharenas.aleatorio;

import com.oharenas.util.Arreglo;
import com.oharenas.util.ISolution;
import com.oharenas.util.Punto2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Oscar Arenas
 */
public class Laberinto {

    private char[][] maze;
    private int filaInicial;
    private int columnaInicial;
    //
    private ISolution solucion;

    public Laberinto(int n) {
        if (n < 10) {
            throw new IllegalArgumentException();
        }
        maze = new char[n][n];
        inicial();
    }

//    public Laberinto(int filas, int columnas) throws NegativeArraySizeException {
//        maze = new char[filas][columnas];
//        inicial();
//    }
    private void inicial() {
        for (int i = 0; i < maze.length; i++) {
            maze[i][0] = '#';
            maze[i][maze[0].length - 1] = '#';
        }
        for (int j = 0; j < maze[0].length; j++) {
            maze[0][j] = '#';
            maze[maze.length - 1][j] = '#';
        }

        for (int i = 1; i < maze.length - 1; i++) {
            for (int j = 1; j < maze[0].length - 1; j++) {
                if ((i + j) % 2 == 0) {
                    maze[i][j] = '.';
                } else {
                    maze[i][j] = '#';
                }
            }
        }
    }

    /*Recorrer un laberinto. Escribir varias versiones:
    1. Indique si el laberinto tiene solución o no.
    2. Retonar la ruta de la solución.
    3. Indicar si tiene varias soluciones.
     */
    public void recorrer(char[][] maze, int entradaX, int entradaY) {

    }

    public Punto2D getPuntoInicial() {
        return new Punto2D(filaInicial, columnaInicial);
    }

    public int getFilaInicial() {
        return filaInicial;
    }

    public int getColumnaInicial() {
        return columnaInicial;
    }

    public void generar1(int filas, int columnas) {

    }

    public void generar2(int filas, int columnas) {

    }

    public void generar3(int filas, int columnas) {

    }

    public void solucionar(ISolution solucion) {
        this.solucion = solucion;
    }

    public char[][] get() {
        char[][] copia = new char[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                copia[i][j] = maze[i][j];
            }
        }
        return copia;
    }

    public void imprimir() {
        Arreglo.imprimirMatriz(maze);
    }

    public String toString() {
        String cadena = "";

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                cadena += maze[i][j];
            }
            cadena += "\n";
        }
        return cadena;
    }

    private class PanelDibujo extends JPanel {

        Color colorSolucion;
        boolean graficarSolucion;

        public PanelDibujo() {
            iniciarComponentes();
        }

        private void iniciarComponentes() {
            setBackground(Color.white);
            colorSolucion = Color.red;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g); //To change body of generated methods, choose Tools | Templates.
            if (graficarSolucion) {
                g.setColor(colorSolucion);
                g.drawRect(10, 10, 100, 70);
            }
        }
    }

    private class Ventana extends JFrame implements ActionListener {

        PanelDibujo panelDibujo;
        JButton botonSolucionar;
        JButton botonLimpiar;

        public Ventana() throws HeadlessException {
            iniciarComponentes();
        }

        private void iniciarComponentes() {
            setTitle("Laberinto " + maze.length + "x" + maze.length + "  Por: Oscar Arenas");
            botonSolucionar = new JButton("Solucionar");
            botonLimpiar = new JButton("Limpiar");

            botonSolucionar.addActionListener(this);
            botonSolucionar.setFocusable(false);

            botonLimpiar.addActionListener(this);
            botonLimpiar.setFocusable(false);

            JPanel panelBotones = new JPanel();
            panelBotones.setBorder(BorderFactory.createEtchedBorder());
            panelBotones.add(botonSolucionar);
            panelBotones.add(botonLimpiar);

            panelDibujo = new PanelDibujo();

            add(panelDibujo, BorderLayout.CENTER);
            add(panelBotones, BorderLayout.PAGE_END);

            setSize(500, 400);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botonSolucionar) {
                if (solucion != null) {
                    panelDibujo.graficarSolucion = true;
                    repaint();
                } else {
                    mostrarMensajeError("Falta la solución");
                }
            } else if (e.getSource() == botonLimpiar) {
                panelDibujo.graficarSolucion = false;
                repaint();
            }
        }

        public void mostrarMensajeError(String mensaje) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void dibujar() {
        try {
            // Se establece el aspecto de la interfaz gráfica de acuerdo
            // al sistema operativo (SO) en el que se ejecuta la aplicación.
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exc) {
        }

        new Ventana().setVisible(true);
    }
}
