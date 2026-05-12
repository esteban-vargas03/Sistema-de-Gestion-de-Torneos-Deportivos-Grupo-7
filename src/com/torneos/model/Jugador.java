package com.torneos.model;

/**
 * Representa un jugador perteneciente a un equipo.
 * Clase concreta con atributos y comportamientos básicos del jugador.
 */
public class Jugador {

    private int idJugador;
    private String nombre;
    private int edad;
    private String posicion;
    private int numero;
    private String nacionalidad;

    public Jugador(int idJugador, String nombre, int edad,
                   String posicion, int numero, String nacionalidad) {
        this.idJugador   = idJugador;
        this.nombre      = nombre;
        this.edad        = edad;
        this.posicion    = posicion;
        this.numero      = numero;
        this.nacionalidad = nacionalidad;
    }

    // ── Getters ──────────────────────────────────────────────────
    public int getIdJugador()       { return idJugador; }
    public String getNombre()       { return nombre; }
    public int getEdad()            { return edad; }
    public String getPosicion()     { return posicion; }
    public int getNumero()          { return numero; }
    public String getNacionalidad() { return nacionalidad; }

    // ── Setters ──────────────────────────────────────────────────
    public void setNombre(String nombre)         { this.nombre = nombre; }
    public void setPosicion(String posicion)     { this.posicion = posicion; }
    public void setNumero(int numero)            { this.numero = numero; }

    /**
     * Registra un gol anotado por este jugador.
     */
    public void anotarGol() {
        System.out.println(" Gol anotado por " + nombre +
                           " (#" + numero + " - " + posicion + ")");
    }

    @Override
    public String toString() {
        return "Jugador{#" + numero + " " + nombre +
               " | " + posicion +
               " | " + nacionalidad +
               " | Edad: " + edad + "}";
    }
}
