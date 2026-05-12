package com.torneos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo deportivo con su plantilla de jugadores.
 * Tiene composición con Jugador (un equipo tiene 1..* jugadores).
 */
public class Equipo {

    private int idEquipo;
    private String nombre;
    private String ciudad;
    private String entrenador;
    private List<Jugador> jugadores;

    public Equipo(int idEquipo, String nombre, String ciudad, String entrenador) {
        this.idEquipo   = idEquipo;
        this.nombre     = nombre;
        this.ciudad     = ciudad;
        this.entrenador = entrenador;
        this.jugadores  = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────────
    public int getIdEquipo()       { return idEquipo; }
    public String getNombre()      { return nombre; }
    public String getCiudad()      { return ciudad; }
    public String getEntrenador()  { return entrenador; }

    // ── Setters ──────────────────────────────────────────────────
    public void setEntrenador(String entrenador) { this.entrenador = entrenador; }

    /**
     * Agrega un jugador a la plantilla del equipo.
     * @param jugador el jugador a incorporar
     */
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        System.out.println("Jugador " + jugador.getNombre() +
                           " agregado al equipo " + nombre);
    }

    /**
     * Retorna la lista completa de jugadores del equipo.
     */
    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }

    @Override
    public String toString() {
        return "Equipo{" + nombre + " | " + ciudad +
               " | DT: " + entrenador +
               " | Jugadores: " + jugadores.size() + "}";
    }
}
