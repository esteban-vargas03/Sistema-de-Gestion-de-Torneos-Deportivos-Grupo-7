package com.torneos.model;

/**
 * Representa la fila de un equipo en la tabla de posiciones de una liga.
 * SRP: única responsabilidad → llevar el registro estadístico de un equipo.
 */
public class TablaPosiciones {

    private Equipo equipo;
    private int golesLocal;
    private int puntos;
    private int partidosJugados;
    private int victorias;
    private int derrotas;
    private int empates;

    public TablaPosiciones(Equipo equipo) {
        this.equipo          = equipo;
        this.golesLocal      = 0;
        this.puntos          = 0;
        this.partidosJugados = 0;
        this.victorias       = 0;
        this.derrotas        = 0;
        this.empates         = 0;
    }

    // ── Getters ──────────────────────────────────────────────────
    public Equipo getEquipo()         { return equipo; }
    public int getGolesLocal()        { return golesLocal; }
    public int getPuntos()            { return puntos; }
    public int getPartidosJugados()   { return partidosJugados; }
    public int getVictorias()         { return victorias; }
    public int getDerrotas()          { return derrotas; }
    public int getEmpates()           { return empates; }

    /**
     * Actualiza los puntos según el resultado del partido.
     * @param puntosSumados puntos a agregar (3=victoria, 1=empate, 0=derrota)
     */
    public void actualizarPuntos(int puntosSumados) {
        this.puntos += puntosSumados;
        this.partidosJugados++;
    }

    /**
     * Registra el resultado de un partido para este equipo.
     * @param resultado resultado del partido
     * @param fueLocal  true si este equipo jugó como local
     * @param puntosVictoria puntos que otorga una victoria en este torneo
     * @param puntosEmpate   puntos que otorga un empate
     */
    public void calcularPosicion(Resultado resultado, boolean fueLocal,
                                 int puntosVictoria, int puntosEmpate) {
        int golesF = fueLocal ? resultado.getGolesLocal()
                              : resultado.getGolesVisitante();
        int golesC = fueLocal ? resultado.getGolesVisitante()
                              : resultado.getGolesLocal();
        this.golesLocal += golesF;
        partidosJugados++;

        if (golesF > golesC) {
            victorias++;
            puntos += puntosVictoria;
        } else if (golesF == golesC) {
            empates++;
            puntos += puntosEmpate;
        } else {
            derrotas++;
        }
    }

    /**
     * Muestra la fila de la tabla en consola.
     */
    public void mostrarTabla() {
        System.out.printf("  %-22s | PJ: %2d | V: %2d | E: %2d | D: %2d | Pts: %3d%n",
                equipo.getNombre(), partidosJugados, victorias,
                empates, derrotas, puntos);
    }

    /**
     * Actualiza todos los campos con los valores de otra tabla (para refrescar).
     */
    public void actualizarTabla(TablaPosiciones nueva) {
        this.puntos          = nueva.puntos;
        this.partidosJugados = nueva.partidosJugados;
        this.victorias       = nueva.victorias;
        this.empates         = nueva.empates;
        this.derrotas        = nueva.derrotas;
        this.golesLocal      = nueva.golesLocal;
    }

    @Override
    public String toString() {
        return equipo.getNombre() + " | Pts:" + puntos +
               " | PJ:" + partidosJugados +
               " | V:" + victorias + " E:" + empates + " D:" + derrotas;
    }
}
