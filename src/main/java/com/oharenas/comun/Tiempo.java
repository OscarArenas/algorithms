/*
 * Copyright (C) 2019 Oscar Arenas
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
package com.oharenas.comun;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Oscar Arenas
 */
public class Tiempo implements Comparable<Tiempo>, Serializable {

    // Campos
    private int horas;   //[0-23]
    private int minutos; //[0-59]
    private int segundos;//[0-59]

    // Métodos
    public Tiempo() {//Constructor predeterminado
        horas = minutos = segundos = 0;
    }

    public Tiempo(int horas, int minutos, int segundos) {
        establecer(horas, minutos, segundos);
    }

    // Método auxiliar
    public final void establecer(int horas, int minutos, int segundos) throws IllegalArgumentException {//Constructor sobrecargado (parametrizado)
        if (horas < 0) {
            throw new IllegalArgumentException("La hora debe pertenecer al intervalo [0-23]");
        }
        if (minutos < 0) {
            throw new IllegalArgumentException("Los minutos deben pertenecer al intervalo [0-59]");
        }
        if (segundos < 0) {
            throw new IllegalArgumentException("Los segundos deben pertenecer al intervalo [0-59]");
        }
        this.segundos = segundos;
        if (segundos >= 60) {
            this.segundos = segundos % 60;
            minutos = minutos + segundos / 60;
        }

        this.minutos = minutos;
        if (minutos >= 60) {
            this.minutos = minutos % 60;
            horas = horas + minutos / 60;
        }
        this.horas = horas % 24;
    }

    public static Tiempo actual() {
        GregorianCalendar gc = new GregorianCalendar();

        int h = gc.get(Calendar.HOUR_OF_DAY);
        int m = gc.get(Calendar.MINUTE);
        int s = gc.get(Calendar.SECOND);

        return new Tiempo(h, m, s);
    }

    public int convertirASegundos() {
        return horas * 60 * 60 + minutos * 60 + segundos;
    }

    public void avanzar() {
        ++segundos;
        //establecer(horas, minutos, segundos);
        if (segundos == 60) {
            segundos = 0;
            ++minutos;

            if (minutos == 60) {
                minutos = 0;
                ++horas;
            }
            horas = horas % 24;
        }
    }

    public void retroceder() {
        --segundos;
        if (segundos == -1) {
            segundos = 59;
            --minutos;

            if (minutos == -1) {
                minutos = 59;
                --horas;
            }
            if (horas == -1) {
                horas = 23;
            }
            horas = horas % 24;
        }
    }

    public Tiempo aumentar(int horas) throws IllegalArgumentException {
        if (horas < 0) {
            throw new IllegalArgumentException("El argumento debe ser no negativo.");
        }
        int h = (this.horas + horas) % 24;

        return new Tiempo(h, minutos, segundos);
    }

    public boolean esIgual(Tiempo objeto) {
        return horas == objeto.horas && minutos == objeto.minutos && segundos == objeto.segundos;
    }

    public boolean esMenor(Tiempo objeto) {
        if (horas < objeto.horas) {
            return true;
        } else if (horas == objeto.horas) {
            if (minutos < objeto.minutos) {
                return true;
            } else if (minutos == objeto.minutos) {
                if (segundos < objeto.segundos) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean esMayor(Tiempo objeto) {
        return !esIgual(objeto) && !esMenor(objeto);
    }

    /**
     * Retorna una representación en cadena del objeto.
     *
     * @return Cadena que representa la hora en formato extendido de 24 horas
     */
    @Override
    public String toString() {
        String cadena = "";

        if (horas < 10) {
            cadena = "0";
        }
        cadena += horas + ":";

        if (minutos < 10) {
            cadena += "0";
        }
        cadena += minutos + ":";

        if (segundos < 10) {
            cadena += "0";
        }
        cadena += segundos;

        return cadena; //return horas + ":" + minutos + ":" + segundos
    }

    /**
     * Retorna una representación en cadena del objeto.
     *
     * @return Cadena que representa la hora en formato extendido de 12 horas
     */
    public String convertirAFormatoAM_PM() {
        String cadena = "", formato;
        int h = horas;

        if (horas < 12) {
            formato = "a.m.";
        } else if (horas == 12) {
            formato = "m.";
        } else {
            formato = "p.m.";
            h = horas % 12;
        }

        if (h < 10) {
            cadena = "0";
        }
        cadena += h + ":";

        if (minutos < 10) {
            cadena += "0";
        }
        cadena += minutos + ":";

        if (segundos < 10) {
            cadena += "0";
        }
        cadena += segundos;

        return cadena + " " + formato;
    }

    @Override
    public int compareTo(Tiempo otro) {
        if (esIgual(otro)) {
            return 0;
        } else if (esMayor(otro)) {
            return 1;
        } else {
            return -1;
        }
    }
}
