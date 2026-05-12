package com.torneos.model;

/**
 * Representa el resultado de un partido disputado.
 * Encapsula los goles y el estado del encuentro.
 */
public class Resultado {

    private int golesLocal;
    private int golesVisitante;
    private String estado;   // "jugado" | "pendiente"

    public Resultado(int golesLocal, int golesVisitante, String estado) {
        this.golesLocal     = golesLocal;
        this.golesVisitante = golesVisitante;
        this.estado         = estado;
    }

    // ── Getters y Setters ─────────────────────────────────────────
    public int getGolesLocal()       { return golesLocal; }
    public int getGolesVisitante()   { return golesVisitante; }
    public String getEstado()        { return estado; }
    public void setGolesLocal(int g)     { this.golesLocal = g; }
    public void setGolesVisitante(int g) { this.golesVisitante = g; }
    public void setEstado(String e)      { this.estado = e; }

    /**
     * Determina y muestra quién ganó el partido.
     */
    public void calcularGanador() {
        if (golesLocal > golesVisitante) {
            System.out.println("Ganador: equipo LOCAL (" + golesLocal + "-" + golesVisitante + ")");
        } else if (golesVisitante > golesLocal) {
            System.out.println("Ganador: equipo VISITANTE (" + golesLocal + "-" + golesVisitante + ")");
        } else {
            System.out.println("Resultado: EMPATE (" + golesLocal + "-" + golesVisitante + ")");
        }
    }

    /**
     * Retorna el equipo ganador o null si es empate.
     * @param local     equipo local
     * @param visitante equipo visitante
     * @return equipo ganador o null en caso de empate
     */
    public Equipo calcularGanador(Equipo local, Equipo visitante) {
        if (golesLocal > golesVisitante)   return local;
        if (golesVisitante > golesLocal)   return visitante;
        return null;
    }

    /**
     * Indica si el partido terminó en empate.
     */
    public boolean esEmpate() {
        return golesLocal == golesVisitante;
    }

    @Override
    public String toString() {
        return "Resultado{" + golesLocal + "-" + golesVisitante +
               " | Estado: " + estado + "}";
    }
}
