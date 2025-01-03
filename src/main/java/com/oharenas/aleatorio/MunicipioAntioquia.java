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

import java.util.Random;

/**
 * Contiene un método que retorna de forma aleatoria el nombre de alguno de los
 * 125 municipios antioqueños.
 *
 * @author Oscar Arenas
 */
public class MunicipioAntioquia {

    private static String[] nombresMunicipios = null;
    private static final Random R;

    static { // Se ejecuta cuando se carga la clase (cuando se importa)
        R = new Random();
        cargar();
    }

    private static void cargar() {
        if (nombresMunicipios == null) {
            nombresMunicipios = new String[125];

            cargarNombres();
        }
    }

    /**
     * Retorna de forma aleatoria el nombre de alguno de los 125 municipios
     * antioqueños.
     *
     * @return Una cadena que representa el nombre de un municipio antioqueño.
     */
    public static String generarNombre() {
        return nombresMunicipios[R.nextInt(nombresMunicipios.length)];
    }

    /**
     * Municipios del area metropolitana del Valle de Aburra (AMVA).
     *
     * @return Un vector de tipo String con los nombres de los municipios del
     * area metropolitana del Valle de Aburra (AMVA).
     */
    public static String[] obtenerMunicipiosAMVA() {
        String[] ciudades = {"Caldas", "Barbosa", "Girardota",
            "Copacabana", "Bello", "Medellín", "Itagüí", "Envigado", "Sabaneta", "La Estrella"};

        return ciudades.clone();
    }

    public static String[] obtenerMunicipios() {
        return nombresMunicipios.clone();
    }

    private static void cargarNombres() {
        nombresMunicipios[0] = "Abejorral";
        nombresMunicipios[1] = "Abriaquí";
        nombresMunicipios[2] = "Alejandría";
        nombresMunicipios[3] = "Amagá";
        nombresMunicipios[4] = "Amalfi";
        nombresMunicipios[5] = "Andes";
        nombresMunicipios[6] = "Angelópolis";
        nombresMunicipios[7] = "Angostura";
        nombresMunicipios[8] = "Anorí";
        nombresMunicipios[9] = "Anzá";
        nombresMunicipios[10] = "Apartadó";
        nombresMunicipios[11] = "Arboletes";
        nombresMunicipios[12] = "Argelia";
        nombresMunicipios[13] = "Armenia";
        nombresMunicipios[14] = "Barbosa";
        nombresMunicipios[15] = "Bello";
        nombresMunicipios[16] = "Belmira";
        nombresMunicipios[17] = "Betania";
        nombresMunicipios[18] = "Betulia";
        nombresMunicipios[19] = "Briceño";
        nombresMunicipios[20] = "Buriticá";
        nombresMunicipios[21] = "Cáceres";
        nombresMunicipios[22] = "Caicedo";
        nombresMunicipios[23] = "Caldas";
        nombresMunicipios[24] = "Campamento";
        nombresMunicipios[25] = "Cañasgordas";
        nombresMunicipios[26] = "Caracolí";
        nombresMunicipios[27] = "Caramanta";
        nombresMunicipios[28] = "Carepa";
        nombresMunicipios[29] = "Carolina del Príncipe";
        nombresMunicipios[30] = "Caucasia";
        nombresMunicipios[31] = "Chigorodó";
        nombresMunicipios[32] = "Cisneros";
        nombresMunicipios[33] = "Ciudad Bolívar";
        nombresMunicipios[34] = "Cocorná";
        nombresMunicipios[35] = "Concepción";
        nombresMunicipios[36] = "Concordia";
        nombresMunicipios[37] = "Copacabana";
        nombresMunicipios[38] = "Dabeiba";
        nombresMunicipios[39] = "Donmatías";
        nombresMunicipios[40] = "Ebéjico";
        nombresMunicipios[41] = "El Bagre";
        nombresMunicipios[42] = "El Carmen de Viboral";
        nombresMunicipios[43] = "El Peñol";
        nombresMunicipios[44] = "El Retiro";
        nombresMunicipios[45] = "El Santuario";
        nombresMunicipios[46] = "Entrerríos";
        nombresMunicipios[47] = "Envigado";
        nombresMunicipios[48] = "Fredonia";
        nombresMunicipios[49] = "Frontino";
        nombresMunicipios[50] = "Giraldo";
        nombresMunicipios[51] = "Girardota";
        nombresMunicipios[52] = "Gómez Plata";
        nombresMunicipios[53] = "Granada";
        nombresMunicipios[54] = "Guadalupe";
        nombresMunicipios[55] = "Guarne";
        nombresMunicipios[56] = "Guatapé";
        nombresMunicipios[57] = "Heliconia";
        nombresMunicipios[58] = "Hispania";
        nombresMunicipios[59] = "Itagüí";
        nombresMunicipios[60] = "Ituango";
        nombresMunicipios[61] = "Jardín";
        nombresMunicipios[62] = "Jericó";
        nombresMunicipios[63] = "La Ceja";
        nombresMunicipios[64] = "La Estrella";
        nombresMunicipios[65] = "La Pintada";
        nombresMunicipios[66] = "La Unión";
        nombresMunicipios[67] = "Liborina";
        nombresMunicipios[68] = "Maceo";
        nombresMunicipios[69] = "Marinilla";
        nombresMunicipios[70] = "Medellín";
        nombresMunicipios[71] = "Montebello";
        nombresMunicipios[72] = "Murindó";
        nombresMunicipios[73] = "Mutatá";
        nombresMunicipios[74] = "Nariño";
        nombresMunicipios[75] = "Nechí";
        nombresMunicipios[76] = "Necoclí";
        nombresMunicipios[77] = "Olaya";
        nombresMunicipios[78] = "Peque";
        nombresMunicipios[79] = "Pueblorrico";
        nombresMunicipios[80] = "Puerto Berrío";
        nombresMunicipios[81] = "Puerto Nare";
        nombresMunicipios[82] = "Puerto Triunfo";
        nombresMunicipios[83] = "Remedios";
        nombresMunicipios[84] = "Rionegro";
        nombresMunicipios[85] = "Sabanalarga";
        nombresMunicipios[86] = "Sabaneta";
        nombresMunicipios[87] = "Salgar";
        nombresMunicipios[88] = "San Andrés de Cuerquia";
        nombresMunicipios[89] = "San Carlos";
        nombresMunicipios[90] = "San Francisco";
        nombresMunicipios[91] = "San Jerónimo";
        nombresMunicipios[92] = "San José de la Montaña";
        nombresMunicipios[93] = "San Juan de Urabá";
        nombresMunicipios[94] = "San Luis";
        nombresMunicipios[95] = "San Pedro de los Milagros";
        nombresMunicipios[96] = "San Pedro de Urabá";
        nombresMunicipios[97] = "San Rafael";
        nombresMunicipios[98] = "San Roque";
        nombresMunicipios[99] = "San Vicente";
        nombresMunicipios[100] = "Santa Bárbara";
        nombresMunicipios[101] = "Santa Rosa de Osos";
        nombresMunicipios[102] = "Santa Fe de Antioquia";
        nombresMunicipios[103] = "Santo Domingo";
        nombresMunicipios[104] = "Segovia";
        nombresMunicipios[105] = "Sonsón";
        nombresMunicipios[106] = "Sopetrán";
        nombresMunicipios[107] = "Támesis";
        nombresMunicipios[108] = "Tarazá";
        nombresMunicipios[109] = "Tarso";
        nombresMunicipios[110] = "Titiribí";
        nombresMunicipios[111] = "Toledo";
        nombresMunicipios[112] = "Turbo";
        nombresMunicipios[113] = "Uramita";
        nombresMunicipios[114] = "Urrao";
        nombresMunicipios[115] = "Valdivia";
        nombresMunicipios[116] = "Valparaíso";
        nombresMunicipios[117] = "Vegachí";
        nombresMunicipios[118] = "Venecia";
        nombresMunicipios[119] = "Vigía del Fuerte";
        nombresMunicipios[120] = "Yalí";
        nombresMunicipios[121] = "Yarumal";
        nombresMunicipios[122] = "Yolombó";
        nombresMunicipios[123] = "Yondó";
        nombresMunicipios[124] = "Zaragoza";
    }
}
