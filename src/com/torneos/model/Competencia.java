package com.torneos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta base para todas las competencias deportivas.
 * Define el contrato que deben cumplir TorneoLiga y TorneoEliminacion.
 *
 * Principio OCP: está cerrada para modificación y abierta para extensión.
 * Cualquier nuevo tipo de torneo (ej: TorneoGrupos) solo requiere crear
 * una nueva subclase sin modificar esta clase.
 *
 * Principio LSP: TorneoLiga y TorneoEliminacion pueden sustituir a
 * Competencia en cualquier contexto sin alterar el comportamiento.
 */
public abstract class Competencia {

    protected int id;
    protected String nombre;
    protected List<Equipo> equipos;
    protected List<Partido> partidos;

    public Competencia(int id, String nombre) {
        this.id       = id;
        this.nombre   = nombre;
        this.equipos  = new ArrayList<>();
        this.partidos = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────────
    public int getId()         { return id; }
    public String getNombre()  { return nombre; }
    public List<Equipo> getEquipos()   { return equipos; }
    public List<Partido> getPartidos() { return partidos; }

    /**
     * Agrega un equipo a la competencia.
     * @param equipo el equipo a inscribir
     */
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
        System.out.println("Equipo '" + equipo.getNombre() +
                           "' inscrito en " + nombre);
    }

    /**
     * Genera el calendario de partidos.
     * Cada tipo de competencia lo implementa a su manera (OCP).
     */
    public abstract void generarFixture();

    /**
     * Calcula y muestra la tabla de posiciones o clasificación.
     * Cada tipo de competencia lo implementa a su manera (OCP).
     */
    public abstract void calcularTabla();

    /**
     * Muestra la información general de la competencia.
     * Método abstracto: cada subclase muestra sus propios atributos (polimorfismo).
     */
    public abstract String mostrarInformacion();

    @Override
    public String toString() {
        return "Competencia{id=" + id + ", nombre='" + nombre +
               "', equipos=" + equipos.size() + "}";
    }
}
