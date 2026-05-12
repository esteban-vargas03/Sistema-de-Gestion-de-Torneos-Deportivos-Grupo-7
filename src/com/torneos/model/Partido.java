package com.torneos.model;

import com.torneos.interfaces.IJugable;
import java.util.Date;
import java.util.Random;

/**
 * Representa un partido entre dos equipos.
 * Implementa IJugable: un partido puede ser jugado y finalizado.
 *
 * Principio LSP: Partido puede usarse como IJugable en cualquier
 * contexto sin alterar el comportamiento esperado.
 */
public class Partido implements IJugable {

    private int idPartido;
    private Date fecha;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Resultado resultado;

    public Partido(int idPartido, Date fecha, Equipo equipoLocal, Equipo equipoVisitante) {
        this.idPartido        = idPartido;
        this.fecha            = fecha;
        this.equipoLocal      = equipoLocal;
        this.equipoVisitante  = equipoVisitante;
        this.resultado        = new Resultado(0, 0, "pendiente");
    }

    // ── Getters ──────────────────────────────────────────────────
    public int getIdPartido()           { return idPartido; }
    public Date getFecha()              { return fecha; }
    public Equipo getEquipoLocal()      { return equipoLocal; }
    public Equipo getEquipoVisitante()  { return equipoVisitante; }
    public Resultado getResultado()     { return resultado; }

    // ── Implementación de IJugable ────────────────────────────────

    /**
     * Simula la disputa del partido generando goles al azar.
     * Principio OCP: se puede extender (ej: PartidoSimulado) sin
     * modificar esta clase.
     */
    @Override
    public void jugar() {
        Random rnd = new Random();
        int golesL = rnd.nextInt(5);
        int golesV = rnd.nextInt(5);
        resultado.setGolesLocal(golesL);
        resultado.setGolesVisitante(golesV);
        System.out.println(" Partido en juego: " +
                           equipoLocal.getNombre() + " vs " +
                           equipoVisitante.getNombre() +
                           "  →  " + golesL + "-" + golesV);
    }

    /**
     * Cierra el partido y registra el resultado como oficial.
     */
    @Override
    public void finalizarPartido() {
        resultado.setEstado("jugado");
        System.out.println(" Partido finalizado: " +
                           equipoLocal.getNombre() + " " +
                           resultado.getGolesLocal() + " - " +
                           resultado.getGolesVisitante() + " " +
                           equipoVisitante.getNombre());
        resultado.calcularGanador();
    }

    @Override
    public String toString() {
        return "Partido#" + idPartido + " [" +
               equipoLocal.getNombre() + " vs " +
               equipoVisitante.getNombre() + "] " +
               resultado;
    }
}
