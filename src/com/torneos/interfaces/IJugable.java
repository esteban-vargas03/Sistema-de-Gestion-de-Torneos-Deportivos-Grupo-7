package com.torneos.interfaces;

/**
 * Contrato para entidades que pueden ser jugadas/disputadas.
 * Principio ISP: interfaz pequeña y específica para partidos jugables.
 */
public interface IJugable {

    /**
     * Ejecuta el partido y genera el resultado.
     */
    void jugar();

    /**
     * Finaliza el partido y registra el resultado oficial.
     */
    void finalizarPartido();
}
