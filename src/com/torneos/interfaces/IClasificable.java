package com.torneos.interfaces;

/**
 * Contrato para competencias que generan una clasificación/tabla.
 * Principio ISP: separada de IJugable porque no todos los jugables
 * generan tabla de posiciones (ej: partidos amistosos).
 */
public interface IClasificable {

    /**
     * Calcula y actualiza la tabla de posiciones.
     */
    void calcularTabla();

    /**
     * Genera el fixture (calendario de partidos) de la competencia.
     */
    void generarFixture();
}
