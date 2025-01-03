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
package com.oharenas.aleatorio;

/**
 * Genera nombres de deportes y valores aleatorios.
 *
 * @author Oscar Arenas
 */
public class Deporte {

    private static String[] deportes = null;

    static { // Se ejecuta cuando se carga la clase (cuando se importa)
        cargar();
    }

    private static void cargar() {
        if (deportes == null) {
            deportes = new String[36];

            deportes[0] = "Atletismo";
            deportes[1] = "Bádminton";
            deportes[2] = "Baloncesto";
            deportes[3] = "Balonmano";
            deportes[4] = "Boxeo";
            deportes[5] = "BMX";
            deportes[6] = "Ciclismo de montaña";
            deportes[7] = "Ciclismo en pista";
            deportes[8] = "Ciclismo en ruta";
            deportes[9] = "Natación";
            deportes[10] = "Natación sincronizada";
            deportes[11] = "Salto";
            deportes[12] = "Waterpolo";
            deportes[13] = "Esgrima";
            deportes[14] = "Equitación";
            deportes[15] = "Fútbol";
            deportes[16] = "Cama elástica";
            deportes[17] = "Gimnasia artística";
            deportes[18] = "Gimnasia rítmica";
            deportes[19] = "Halterofilia";
            deportes[20] = "Hockey hierba";
            deportes[21] = "Judo";
            deportes[22] = "Lucha";
            deportes[23] = "Pentatlón moderno";
            deportes[24] = "Aguas bravas";
            deportes[25] = "Aguas tranquilas";
            deportes[26] = "Remo";
            deportes[27] = "Taekwondo";
            deportes[28] = "Tenis";
            deportes[29] = "Tenis de mesa";
            deportes[30] = "Tiro con arco";
            deportes[31] = "Tiro deportivo";
            deportes[32] = "Triatlón";
            deportes[33] = "Vela";
            deportes[34] = "Voleibol";
            deportes[35] = "Vóley playa";
        }
    }

    /**
     * Genera el nombre de un deporte seleccionado aleatoriamente de una lista.
     *
     * @return El nombre de un deporte.
     */
    public static String getNombreDeporte() {
        return deportes[Aleatorio.entero(deportes.length)];
    }

    /**
     * Par (Professional Average Result) de cada uno de los hoyos del campo de
     * golf Augusta National Golf Club donde se jugó el US Open en el 2017.
     * https://en.wikipedia.org/wiki/Augusta_National_Golf_Club
     *
     * @return Vector con el par de cada hoyo del campo de golf Augusta National
     * Golf Club
     */
    public static int[] parAugusta() {
        int[] par = {4, 5, 4, 3, 4, 3, 4, 5, 4, 4, 4, 3, 5, 4, 5, 3, 4, 4};
        return par.clone();
    }

    /**
     * Par (Professional Average Result) de cada uno de los hoyos del campo de
     * golf Erin hills donde se jugó el US Open en el 2017.
     * https://en.wikipedia.org/wiki/Erin_Hills
     *
     * @return Vector con el par de cada hoyo del campo de golf Erin Hills
     */
    public static int[] parUSOpen() {
        int[] par = {5, 4, 4, 4, 4, 3, 5, 4, 3, 4, 4, 4, 3, 5, 4, 3, 4, 5};
        return par.clone();
    }

    /**
     * Par (Professional Average Result) de cada uno de los hoyos del campo de
     * golf Royal Birkdale Golf Club donde se jugó el British Open en el 2017.
     * https://en.wikipedia.org/wiki/Royal_Birkdale_Golf_Club
     *
     * @return Vector con el par de cada hoyo del campo de golf Royal Birkdale
     * Golf Club
     */
    public static int[] parBritishOpen() {
        int[] par = {4, 4, 4, 3, 4, 4, 3, 4, 4, 4, 4, 3, 4, 3, 5, 4, 5, 4};
        return par.clone();
    }

    /**
     * Par (Professional Average Result) de cada uno de los hoyos del campo de
     * golf Quail Hollow Club donde se jugó el PGA Championship en el 2017. PGA
     * (Professional Golfers' Association of America)
     *
     * @return Vector con el par de cada hoyo del campo de golf Quail Hollow
     * Club
     */
    public static int[] parPGAChampionship() {
        int[] par = {4, 4, 4, 3, 4, 3, 5, 4, 4, 5, 4, 4, 3, 4, 5, 4, 3, 4};
        return par.clone();
    }
}
