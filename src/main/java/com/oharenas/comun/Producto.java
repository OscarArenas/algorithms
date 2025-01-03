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
package com.oharenas.comun;

import com.oharenas.util.Util;
import java.io.Serializable;

/**
 *
 * @author Oscar Arenas
 */
public class Producto implements Serializable {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private double iva;

    public Producto(String codigo, String nombre, String descripcion, double precioUnitario, double iva) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;

        if (precioUnitario <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor que cero, " + precioUnitario);
        }

        if (iva < 0) {
            throw new IllegalArgumentException("IVA debe ser no negativo, " + iva);
        }

        this.precioUnitario = precioUnitario;
        this.iva = iva;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario > 0) {
            this.precioUnitario = precioUnitario;
        }
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        if (iva >= 0) {
            this.iva = iva;
        }
    }

    @Override
    public String toString() {
        String cadena = "Código: " + codigo + "\n";

        cadena += "Nombre: " + nombre + "\n";
        cadena += "Descripción: " + descripcion + "\n";
        cadena += "Precio unitario: " + Util.realAString(precioUnitario) + "\n";
        cadena += "IVA: " + Util.realAString(iva);

        return cadena;
    }
}
