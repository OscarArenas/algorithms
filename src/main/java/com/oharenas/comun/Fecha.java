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
package com.oharenas.comun;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Oscar Arenas
 */
public final class Fecha implements Comparable<Fecha>, Serializable {

    // Campos (Atributos)
    public final int dia;
    public final int mes;
    public final int anio;
    //
    public static final String[] DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    public static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    // Metodos
    // Constructor parametrizado.
    public Fecha(int dia, int mes, int anio) {
        if (anio < 0) {
            throw new IllegalArgumentException("El año debe ser no negativo.");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe pertenecer al intervalo [1, 12].");
        }
        this.anio = anio;
        this.mes = mes;
        int maximoDias;
        // 28 o 29: febrero
        // 30 dias: abril, junio, sept., nov.
        // 31 dias: enero, marzo, mayo, julio, agosto, octubre, diciembre
        switch (mes) {
            case 2:
                maximoDias = (esBisiesto() ? 29 : 28);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maximoDias = 30;
                break;
            default:
                maximoDias = 31;
        }
        if (dia < 1 || dia > maximoDias) {
            throw new IllegalArgumentException("El día debe pertenecer al intervalo [1, " + maximoDias + "]");
        }

        this.dia = dia;
    }

    public Fecha(Fecha fecha) {
        dia = fecha.dia;
        mes = fecha.mes;
        anio = fecha.anio;
    }

    public Fecha(String cadenaFecha) {
        String regex = "[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]";

        if (!cadenaFecha.matches(regex)) {
            throw new IllegalArgumentException("La fecha no tiene el formato dd/mm/aaaa");
        }
        String[] fecha = cadenaFecha.split("/");

        if (fecha.length != 3) {
            throw new IllegalArgumentException("La fecha no tiene el formato dd/mm/aaaa");
        }
        int day = Integer.parseInt(fecha[0]);
        int month = Integer.parseInt(fecha[1]);
        int year = Integer.parseInt(fecha[2]);

        if (year < 0) {
            throw new IllegalArgumentException("El año debe ser no negativo.");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("El mes debe pertenecer al intervalo [1, 12].");
        }
        this.anio = year;
        this.mes = month;
        int maximoDias;
        // 28 o 29: febrero
        // 30 dias: abril, junio, sept., nov.
        // 31 dias: enero, marzo, mayo, julio, agosto, octubre, diciembre
        switch (mes) {
            case 2:
                maximoDias = (esBisiesto() ? 29 : 28);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maximoDias = 30;
                break;
            default:
                maximoDias = 31;
        }
        if (day < 1 || day > maximoDias) {
            throw new IllegalArgumentException("El día debe pertenecer al intervalo [1, " + maximoDias + "]");
        }

        this.dia = day;
    }

    /**
     * Retorna una objeto de esta clase que representa la fecha actual.
     *
     * @return Retorna una objeto de esta clase que representa la fecha actual.
     */
    public static Fecha hoy() {
        GregorianCalendar gc = new GregorianCalendar();

        // Obtenemos los valores del día, mes y año del calendario.
        int d = gc.get(Calendar.DAY_OF_MONTH);
        int m = gc.get(Calendar.MONTH) + 1;
        int a = gc.get(Calendar.YEAR);

        return new Fecha(d, m, a);
    }

    /**
     * Calcula la diferencia en meses entre dos fechas.
     *
     * @param fecha
     * @return Entero positivo que representa la diferencia en meses.
     */
    public int calcularDiferenciaEnMeses(Fecha fecha) {
        int diferencia;

        if (compareTo(fecha) < 0) {
            // Calcula la diferencia en meses.
            diferencia = 12 * (fecha.anio - anio) + (fecha.mes - mes);

            //Si el día no es mayor, resta un mes
            if (fecha.dia < dia) {
                diferencia--;
            }
        } else {
            // Calcula la diferencia en meses.
            diferencia = 12 * (anio - fecha.anio) + (mes - fecha.mes);

            //Si el día no es mayor, resta un mes
            if (dia < fecha.dia) {
                diferencia--;
            }
        }
        return diferencia;
    }

    public boolean esIgual(Fecha otraFecha) {
        return this == otraFecha || (anio == otraFecha.anio && mes == otraFecha.mes && dia == otraFecha.dia);
    }

    public boolean esMayor(Fecha otraFecha) {
        if (anio > otraFecha.anio) {
            return true;
        } else if (anio == otraFecha.anio) {
            if (mes > otraFecha.mes) {
                return true;
            } else if (mes == otraFecha.mes) {
                return dia > otraFecha.dia;
            }
        }
        return false;
    }

    public boolean esMenor(Fecha otraFecha) {
        return !esIgual(otraFecha) && !esMayor(otraFecha);
    }

    public boolean esBisiesto() {
        return (anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0));
    }

    public Fecha clonar() {
        return new Fecha(dia, mes, anio);
    }

    public String getCSV() {
        return dia + "," + mes + "," + anio;
    }

    public Fecha aumentarDia(int dias) {
        Fecha fecha = clonar();

        for (int i = 0; i < dias; i++) {
            fecha = fecha.aumentarDia();
        }
        return fecha;
    }

    public Fecha aumentarDia() {
        int nuevoDia = dia, nuevoMes = mes, nuevoAnio = anio;
        // 28 o 29: febrero
        // 30 dias: abril, junio, sept., nov.
        // 31 dias: enero, marzo, mayo, julio, agosto, octubre, diciembre
        switch (mes) {
            case 2:
                if (esBisiesto()) {
                    if (dia == 29) {
                        nuevoDia = 1;
                        nuevoMes++;
                    } else {
                        nuevoDia++;
                    }
                } else if (dia == 28) {
                    nuevoDia = 1;
                    nuevoMes++;
                } else {
                    nuevoDia++;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (dia == 30) {
                    nuevoDia = 1;
                    nuevoMes++;
                } else {
                    nuevoDia++;
                }
                break;
            default:
                if (dia == 31) {
                    nuevoDia = 1;
                    nuevoMes++;
                    if (nuevoMes == 13) {
                        nuevoMes = 1;
                        nuevoAnio++;
                    }
                } else {
                    nuevoDia++;
                }
        }
        return new Fecha(nuevoDia, nuevoMes, nuevoAnio);
    }

    @Override
    public String toString() {
        String cadena = "";

        if (dia < 10) {
            cadena = "0";
        }
        cadena += dia + "/";

        if (mes < 10) {
            cadena += "0";
        }
        cadena += mes + "/" + anio;
        return cadena;
    }

    public String jsonString() {
        String cadena = "\"dia\":" + dia + ",\n";

        cadena += "\"mes\":" + mes + ",\n";
        cadena += "\"anio\":" + anio;

        return "{\n" + cadena + "\n}";
    }

    @Override
    public int compareTo(Fecha otra) {
        if (esIgual(otra)) {
            return 0;
        } else if (esMayor(otra)) {
            return 1;
        } else {
            return -1;
        }
    }
}
