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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Oscar Arenas
 */
public class PanelOperaciones extends JPanel implements ActionListener {

    private VentanaGraficaFrecuencia ventanaGraficaFrecuencia;
    private JButton botonEstadisticas;
    private JButton botonDistribucionNormal;

    public PanelOperaciones(VentanaGraficaFrecuencia ventanaGraficaFrecuencia) {
        this.ventanaGraficaFrecuencia = ventanaGraficaFrecuencia;
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        botonEstadisticas = new JButton("Estadísticas");
        botonEstadisticas.setFocusable(false);
        botonEstadisticas.addActionListener(this);

        botonDistribucionNormal = new JButton("Distribución normal");
        botonDistribucionNormal.setFocusable(false);
        botonDistribucionNormal.addActionListener(this);

        add(botonEstadisticas);
//        add(botonDistribucionNormal);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonEstadisticas) {
            ventanaGraficaFrecuencia.mostrarEstadisticas();
        } else if (e.getSource() == botonDistribucionNormal) {
            ventanaGraficaFrecuencia.mostrarDistribucionNormal();
        }
    }
}
