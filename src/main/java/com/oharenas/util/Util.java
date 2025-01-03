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

import com.oharenas.comun.Persona;

/**
 * @author Oscar Arenas
 */
public class Util {

    public static String realAString(double real) {
        int entero = (int) real;
        String cadena = real + "";

        if (real == entero) {
            cadena = entero + "";
        }
        return cadena;
    }

    /**
     * Elimina los espacios adicionales que hay en una cadena. Solo deja un
     * espacio en blanco entre las subcadenas de la cadena. También elimina
     * todos los espacios en blanco al principio y al final de la cadena.
     *
     * @param cadena Valor de tipo String con espacios
     * @return Retorna cadena sin mas de un espacio entre las subcadenas.
     */
    public static String eliminarEspacios(String cadena) {
        boolean espacio = false;
        String nuevaCadena = "";
        cadena = cadena.trim();

        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == ' ' || cadena.charAt(i) == '\t') {
                espacio = true;
            } else if (espacio) {
                nuevaCadena += " " + cadena.charAt(i);
                espacio = false;
            } else {
                nuevaCadena += cadena.charAt(i);
            }
        }

        return nuevaCadena.trim();
    }

    public static String eliminar(char caracter, String cadena) {
        String nuevaCadena = "";

        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) != caracter) {
                nuevaCadena += cadena.charAt(i);
            }
        }

        return nuevaCadena;
    }

    public static String removeAllWhitespaces(String text) {
        String newText = "";
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            if (symbol != ' ' && symbol != '\t') {
                newText += symbol;
            }
        }
        return newText;
    }

    public static String reverse(String text) {
        String newText = "";
        for (int i = 0; i < text.length(); i++) {
            newText = text.charAt(i) + newText;
        }
        return newText;
    }

    public static String eliminarPuntuacion(String word) {
        word = word.replace(".", "");
        word = word.replace(",", "");
        word = word.replace(";", "");
        word = word.replace(":", "");
        word = word.replace("!", "");
        word = word.replace("¡", "");
        word = word.replace("¿", "");
        word = word.replace("?", "");
        word = word.replace("(", "");
        word = word.replace(")", "");
        word = word.replace("—", "");
        word = word.replace("«", "");
        word = word.replace("»", "");
        return word;
    }

    /**
     * Indica si el parametro anio es un año bisiesto.
     *
     * @param anio Valor entero no negativo que indica un año.
     * @return Retorna true si anio es bisiesto y false en caso contrario.
     */
    public static boolean esBisiesto(int anio) {
        return (anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0));
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * @param fecha
     * @return
     */
    public static int getDia(String fecha) {
        String[] div = fecha.split("/");
        //String dia = fecha.substring(0, 2);

        return Integer.parseInt(div[0]);
    }

    /**
     * @param fecha
     * @return
     */
    public static int getMes(String fecha) {
        String[] div = fecha.split("/");
        //String mes = fecha.substring(3, 5);

        return Integer.parseInt(div[1]);
    }

    /**
     * Obtiene el año de una fecha con el formato dd/mm/aaaa.
     *
     * @param fecha Cadena de caracteres con el formato dd/mm/aaaa que
     *              representa una fecha.
     * @return Retorna el dia del parametro como valor entero.
     */
    public static int getAnio(String fecha) {
        String[] div = fecha.split("/");
        //String anio = fecha.substring(6, 10);

        return Integer.parseInt(div[2]);
    }

    /**
     * Obtiene la hora como valor entero de una cadena pasada como argumento con
     * el formato hh:mm:ss o hh:mm
     *
     * @param hora Cadena con el formato hh:mm:ss o hh:mm
     * @return Retorna un valor entero en el intervalo [0, 23]
     */
    public static int getHora(String hora) {
        String[] div = hora.split("/");
        //String h = hora.substring(0, 2);

        return Integer.parseInt(div[0]);
    }

    /**
     * Obtiene los minutos como valor entero de una cadena pasada como argumento
     * con el formato hh:mm:ss o hh:mm
     *
     * @param hora Cadena con el formato hh:mm:ss o hh:mm
     * @return Retorna un valor entero en el intervalo [0, 59]
     */
    public static int getMinutos(String hora) {
        String[] div = hora.split("/");
        //String m = hora.substring(3, 5);

        return Integer.parseInt(div[1]);
    }

    /**
     * Obtiene los segundos como valor entero de una cadena pasada como
     * argumento con el formato hh:mm:ss. Si la cadena tiene el formato hh:mm
     * retorna cero.
     *
     * @param hora Cadena con el formato hh:mm:ss o hh:mm
     * @return Retorna un valor entero en el intervalo [0, 59]
     */
    public static int getSegundos(String hora) {
        String[] div = hora.split("/");
        String s = (div.length > 2) ? div[2] : "0";

        return Integer.parseInt(s);
    }

    /**
     * Indica si el valor pasado corresponde a una mujer.
     *
     * @param genero Valor entero que solo puede ser FEMENINO o MASCULINO
     * @return Retorna true solo si el argumento corresponde a FEMENINO.
     */
    public static boolean esMujer(int genero) {
        return genero == Persona.FEMENINO;
    }

    /**
     * Indica si el valor pasado corresponde a un hombre.
     *
     * @param genero Valor entero que solo puede ser FEMENINO o MASCULINO
     * @return Retorna true solo si el argumento corresponde a MASCULINO.
     */
    public static boolean esHombre(int genero) {
        return genero == Persona.MASCULINO;
    }

    public static String darFormato(int valor, int cifras) {
        String cadena = valor + "";

        if (cadena.length() < cifras) {
            int delta = cifras - cadena.length();

            for (int i = 0; i < delta; i++) {
                cadena = "0" + cadena;
            }
        }

        return cadena;
    }

    public static String eliminarTilde(String cadena) {
        StringBuilder newString = new StringBuilder();

        cadena = cadena.toLowerCase();

        for (int i = 0; i < cadena.length(); i++) {
            newString.append(removeTilde(cadena.charAt(i)));
        }

        return newString.toString();
    }

    public static char removeTilde(char character) {
        final char letterA = '\u00E1';
        final char letterE = '\u00E9';
        final char letterI = '\u00ED';
        final char letterO = '\u00F3';
        final char letterU = '\u00FA';
        final char letterN = '\u00F1';
        final char letterUt = '\u00FC';

        switch (character) {
            case letterA:
                return 'a';
            case letterE:
                return 'e';
            case letterI:
                return 'i';
            case letterO:
                return 'o';
            case letterU, letterUt:
                return 'u';
            case letterN:
            case ' ':
                return ' ';
            default:
                return character;
        }
    }

}
