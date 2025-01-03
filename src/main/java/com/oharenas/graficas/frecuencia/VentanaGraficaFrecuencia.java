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

import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Oscar Arenas
 */
public class VentanaGraficaFrecuencia extends JFrame {

    private PanelGrafica panelGrafica;
    private PanelOperaciones panelOperaciones;
    private JScrollPane scrollPane;
    //
    private int[] datos;

    public VentanaGraficaFrecuencia(int[] datos) {
        this.datos = datos.clone();

        Arrays.sort(this.datos);
        panelGrafica = new PanelGrafica(this.datos);
        iniciarComponentes();
    }

    public VentanaGraficaFrecuencia(int[] datos, String titulo) {
        this.datos = datos.clone();
        Arrays.sort(this.datos);
        panelGrafica = new PanelGrafica(this.datos, titulo);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setTitle("Gráfico de Frecuencia");

        scrollPane = new JScrollPane(panelGrafica);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panelOperaciones = new PanelOperaciones(this);

        add(scrollPane, BorderLayout.CENTER);
        add(panelOperaciones, BorderLayout.PAGE_END);

        //
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void mostrarEstadisticas() {
        JOptionPane.showMessageDialog(this, panelGrafica.getEstadisticas(), "Estadística descriptiva", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarDistribucionNormal() {
//        JOptionPane.showMessageDialog(this, panelGrafica.getEstadisticas(), "Estadística descriptiva", JOptionPane.INFORMATION_MESSAGE);
    }
}
