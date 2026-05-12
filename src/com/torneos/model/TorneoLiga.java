package com.torneos.model;

import com.torneos.interfaces.IClasificable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Torneo en formato liga: todos contra todos.
 * Extiende Competencia e implementa IClasificable.
 *
 * Principio OCP: agrega comportamiento específico de liga sin modificar Competencia.
 * Principio LSP: puede sustituir a Competencia en cualquier contexto.
 */
public class TorneoLiga extends Competencia implements IClasificable {

    protected int jornadas;
    protected int puntosPorVictoria;
    protected int puntosPorEmpate;
    private List<TablaPosiciones> tabla;

    public TorneoLiga(int id, String nombre, int puntosPorVictoria, int puntosPorEmpate) {
        super(id, nombre);
        this.puntosPorVictoria = puntosPorVictoria;
        this.puntosPorEmpate   = puntosPorEmpate;
        this.jornadas          = 0;
        this.tabla             = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────────
    public int getJornadas()          { return jornadas; }
    public int getPuntosPorVictoria() { return puntosPorVictoria; }
    public int getPuntosPorEmpate()   { return puntosPorEmpate; }

    /**
     * Genera el fixture de todos contra todos (round-robin).
     * Principio OCP: lógica exclusiva de TorneoLiga, sin tocar Competencia.
     */
    @Override
    public void generarFixture() {
        partidos.clear();
        tabla.clear();

        // Inicializar tabla
        for (Equipo e : equipos) {
            tabla.add(new TablaPosiciones(e));
        }

        int idPartido = 1;
        jornadas = equipos.size() - 1;
        System.out.println("\n Fixture generado para " + nombre +
                           " (" + equipos.size() + " equipos, " + jornadas + " jornadas):");

        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Partido p = new Partido(idPartido++, new Date(),
                                       equipos.get(i), equipos.get(j));
                partidos.add(p);
                System.out.println("  Partido #" + p.getIdPartido() + ": " +
                                   equipos.get(i).getNombre() + " vs " +
                                   equipos.get(j).getNombre());
            }
        }
    }

    /**
     * Calcula la tabla de posiciones basada en los resultados.
     */
    @Override
    public void calcularTabla() {
        // Reiniciar tabla
        tabla.clear();
        for (Equipo e : equipos) {
            tabla.add(new TablaPosiciones(e));
        }

        for (Partido p : partidos) {
            if ("jugado".equals(p.getResultado().getEstado())) {
                TablaPosiciones tablaLocal     = buscarFila(p.getEquipoLocal());
                TablaPosiciones tablaVisitante = buscarFila(p.getEquipoVisitante());

                if (tablaLocal != null)
                    tablaLocal.calcularPosicion(p.getResultado(), true,
                                                puntosPorVictoria, puntosPorEmpate);
                if (tablaVisitante != null)
                    tablaVisitante.calcularPosicion(p.getResultado(), false,
                                                    puntosPorVictoria, puntosPorEmpate);
            }
        }
    }

    private TablaPosiciones buscarFila(Equipo equipo) {
        for (TablaPosiciones t : tabla) {
            if (t.getEquipo().getIdEquipo() == equipo.getIdEquipo()) return t;
        }
        return null;
    }

    /**
     * Muestra la tabla de posiciones ordenada por puntos.
     */
    public void mostrarTablaPosiciones() {
        calcularTabla();
        tabla.sort((a, b) -> b.getPuntos() - a.getPuntos());
        System.out.println(" TABLA DE POSICIONES — " + nombre);
        System.out.println("  " + "─".repeat(60));
        int pos = 1;
        for (TablaPosiciones fila : tabla) {
            System.out.print("  " + pos++ + ". ");
            fila.mostrarTabla();
        }
        System.out.println("  " + "─".repeat(60));
    }

    @Override
    public String mostrarInformacion() {
        return "[LIGA] " + nombre +
               " | Equipos: " + equipos.size() +
               " | Jornadas: " + jornadas +
               " | Pts Victoria: " + puntosPorVictoria +
               " | Pts Empate: " + puntosPorEmpate;
    }
}
