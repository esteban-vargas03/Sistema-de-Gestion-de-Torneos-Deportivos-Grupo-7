package com.torneos;

import com.torneos.interfaces.IClasificable;
import com.torneos.interfaces.INotificable;
import com.torneos.model.*;
import com.torneos.service.*;

/**
 * Clase principal que demuestra el funcionamiento completo del
 * Sistema de Gestión de Torneos Deportivos.
 *
 * Demuestra:
 *  - Herencia: TorneoLiga y TorneoEliminacion extienden Competencia
 *  - Polimorfismo: listas de Competencia, llamadas a mostrarInformacion()
 *  - Interfaces: IJugable, IClasificable, INotificable
 *  - DIP: inyección de ServicioNotificacion como INotificable
 *  - SRP: cada gestor tiene una única responsabilidad
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE TORNEOS DEPORTIVOS   ║");
        System.out.println("║   Entrega 2 — Principios SOLID               ║");
        System.out.println("╚══════════════════════════════════════════════╝\n");

        // ── 1. DIP: crear abstracción e inyectarla en los gestores ────────────
        INotificable notificaciones = new ServicioNotificacion();
        GestorTorneos  gestorTorneos  = new GestorTorneos(notificaciones);
        GestorPartidos gestorPartidos = new GestorPartidos(notificaciones);

        // ── 2. Crear equipos ──────────────────────────────────────────────────
        Equipo atletico  = new Equipo(1, "Atlético Huila",     "Neiva",    "Carlos Peña");
        Equipo tolima    = new Equipo(2, "Deportes Tolima",    "Ibagué",   "Hernán Torres");
        Equipo nacional  = new Equipo(3, "Atlético Nacional",  "Medellín", "Alejandro Restrepo");
        Equipo america   = new Equipo(4, "América de Cali",    "Cali",     "Jorge Da Silva");

        // ── 3. Agregar jugadores (composición Equipo ◇── Jugador) ────────────
        atletico.agregarJugador(new Jugador(1, "Juan Pérez",    24, "Delantero",  9,  "Colombiano"));
        atletico.agregarJugador(new Jugador(2, "Luis Gómez",    27, "Mediocampista", 8, "Colombiano"));
        tolima.agregarJugador(  new Jugador(3, "Andrés Torres", 22, "Portero",    1,  "Colombiano"));
        nacional.agregarJugador(new Jugador(4, "Felipe Ríos",   26, "Defensor",   4,  "Colombiano"));
        america.agregarJugador( new Jugador(5, "Carlos Ruiz",   29, "Delantero",  10, "Colombiano"));

        System.out.println();

        // ── 4. Crear torneos (OCP: subclases de Competencia) ─────────────────
        TorneoLiga       liga = new TorneoLiga(1, "Liga BetPlay 2026", 3, 1);
        TorneoEliminacion copa = new TorneoEliminacion(2, "Copa Colombia 2026");

        // ── 5. Registrar torneos en el gestor ─────────────────────────────────
        gestorTorneos.registrarTorneo(liga);
        gestorTorneos.registrarTorneo(copa);

        // ── 6. Inscribir equipos ──────────────────────────────────────────────
        System.out.println();
        gestorTorneos.inscribirEquipo(liga, atletico);
        gestorTorneos.inscribirEquipo(liga, tolima);
        gestorTorneos.inscribirEquipo(liga, nacional);
        gestorTorneos.inscribirEquipo(liga, america);

        gestorTorneos.inscribirEquipo(copa, atletico);
        gestorTorneos.inscribirEquipo(copa, tolima);
        gestorTorneos.inscribirEquipo(copa, nacional);
        gestorTorneos.inscribirEquipo(copa, america);

        // ── 7. Mostrar todos los torneos (POLIMORFISMO) ───────────────────────
        gestorTorneos.mostrarTorneos(); // llama mostrarInformacion() de cada subclase

        // ── 8. Iniciar torneos (genera fixture) ───────────────────────────────
        gestorTorneos.iniciarTorneo(liga);
        gestorTorneos.iniciarTorneo(copa);

        // ── 9. Disputar partidos (IJugable + polimorfismo) ────────────────────
        gestorPartidos.jugarJornada(liga);
        gestorPartidos.jugarJornada(copa);

        // ── 10. Mostrar resultados ────────────────────────────────────────────
        gestorPartidos.mostrarResultados(liga);
        gestorPartidos.mostrarResultados(copa);

        // ── 11. IClasificable (ISP): calcular tabla solo en torneos que aplica ─
        System.out.println("\n══ TABLAS Y CLASIFICADOS ══");
        for (Competencia c : gestorTorneos.getTorneos()) {
            if (c instanceof IClasificable) {           // ISP en acción
                IClasificable clas = (IClasificable) c;
                clas.calcularTabla();
                if (c instanceof TorneoLiga) {
                    ((TorneoLiga) c).mostrarTablaPosiciones();
                }
            }
        }

        // ── 12. Polimorfismo con lista de Competencia ─────────────────────────
        System.out.println("\n══ RESUMEN POLIMÓRFICO DE COMPETENCIAS ══");
        for (Competencia c : gestorTorneos.getTorneos()) {
            System.out.println(" • " + c.mostrarInformacion()); // cada subtipo muestra lo suyo
        }

        // ── 13. Demostración de anotarGol (Jugador) ───────────────────────────
        System.out.println("\n══ GOLES DESTACADOS ══");
        atletico.obtenerJugadores().get(0).anotarGol();
        america.obtenerJugadores().get(0).anotarGol();

        // ── 14. Historial de notificaciones ──────────────────────────────────
        ((ServicioNotificacion) notificaciones).mostrarHistorial();

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println  ("║   Demostración completada con éxito          ║");
        System.out.println  ("╚══════════════════════════════════════════════╝");
    }
}