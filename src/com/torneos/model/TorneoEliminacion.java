package com.torneos.model;

import com.torneos.interfaces.IClasificable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Torneo en formato eliminación directa (copa).
 * Extiende Competencia e implementa IClasificable.
 *
 * Principio OCP: agrega comportamiento de eliminación sin modificar Competencia.
 * Principio LSP: puede sustituir a Competencia en cualquier contexto.
 */
public class TorneoEliminacion extends Competencia implements IClasificable {

    protected int rondas;
    protected int equiposClasificados;
    private List<Equipo> equiposEliminados;

    public TorneoEliminacion(int id, String nombre) {
        super(id, nombre);
        this.rondas               = 0;
        this.equiposClasificados  = 0;
        this.equiposEliminados    = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────────
    public int getRondas()               { return rondas; }
    public int getEquiposClasificados()  { return equiposClasificados; }

    /**
     * Genera el fixture por eliminación directa (cruce de equipos).
     */
    @Override
    public void generarFixture() {
        partidos.clear();
        rondas = (int) (Math.log(equipos.size()) / Math.log(2));
        int idPartido = 1;

        System.out.println("\n Fixture eliminación directa — " + nombre +
                           " (" + equipos.size() + " equipos, " + rondas + " rondas):");

        for (int i = 0; i < equipos.size() - 1; i += 2) {
            Equipo local     = equipos.get(i);
            Equipo visitante = equipos.get(i + 1);
            Partido p = new Partido(idPartido++, new Date(), local, visitante);
            partidos.add(p);
            System.out.println("  Partido #" + p.getIdPartido() + " (Ronda 1): " +
                               local.getNombre() + " vs " + visitante.getNombre());
        }
    }

    /**
     * Calcula los clasificados de la ronda actual.
     */
    @Override
    public void calcularTabla() {
        equiposClasificados = 0;
        System.out.println("\n CLASIFICADOS — " + nombre);
        for (Partido p : partidos) {
            if ("jugado".equals(p.getResultado().getEstado())) {
                Equipo ganador = p.getResultado()
                                  .calcularGanador(p.getEquipoLocal(), p.getEquipoVisitante());
                if (ganador != null) {
                    System.out.println("   Clasificado: " + ganador.getNombre());
                    equiposClasificados++;
                } else {
                    System.out.println("    Empate — se necesita prórroga/penales");
                }
            }
        }
    }

    @Override
    public String mostrarInformacion() {
        return "[ELIMINACIÓN] " + nombre +
               " | Equipos: " + equipos.size() +
               " | Rondas: " + rondas +
               " | Clasificados: " + equiposClasificados;
    }
}
