package com.torneos.service;

import com.torneos.interfaces.IJugable;
import com.torneos.interfaces.INotificable;
import com.torneos.model.Competencia;
import com.torneos.model.Partido;
import java.util.List;

/**
 * Gestiona la ejecución de partidos dentro de un torneo.
 *
 * SRP: única responsabilidad → coordinar el ciclo de vida de los partidos.
 * DIP: depende de IJugable (abstracción) e INotificable (abstracción),
 *      no de implementaciones concretas.
 */
public class GestorPartidos {

    private INotificable servicioNotificacion;

    /**
     * @param servicioNotificacion abstracción inyectada por constructor (DIP)
     */
    public GestorPartidos(INotificable servicioNotificacion) {
        this.servicioNotificacion = servicioNotificacion;
    }

    /**
     * Ejecuta un partido: lo juega y lo finaliza.
     * LSP: recibe IJugable → funciona con cualquier clase que implemente la interfaz.
     *
     * @param jugable objeto que implementa IJugable (actualmente Partido)
     */
    public void disputarPartido(IJugable jugable) {
        jugable.jugar();
        jugable.finalizarPartido();

        if (jugable instanceof Partido) {
            Partido p = (Partido) jugable;
            servicioNotificacion.enviarNotificacion(
                    p.getEquipoLocal().getNombre() + " y " + p.getEquipoVisitante().getNombre(),
                    "Resultado: " + p.getEquipoLocal().getNombre() + " " +
                    p.getResultado().getGolesLocal() + "-" +
                    p.getResultado().getGolesVisitante() + " " +
                    p.getEquipoVisitante().getNombre(),
                    "RESULTADO"
            );
        }
    }

    /**
     * Juega todos los partidos pendientes de una competencia.
     */
    public void jugarJornada(Competencia competencia) {
        List<Partido> partidos = competencia.getPartidos();
        System.out.println("\n DISPUTANDO PARTIDOS DE: " + competencia.getNombre());
        boolean huboPendientes = false;
        for (Partido p : partidos) {
            if ("pendiente".equals(p.getResultado().getEstado())) {
                disputarPartido(p);
                huboPendientes = true;
            }
        }
        if (!huboPendientes) {
            System.out.println("No hay partidos pendientes en " + competencia.getNombre());
        }
    }

    /**
     * Muestra los resultados de todos los partidos de una competencia.
     */
    public void mostrarResultados(Competencia competencia) {
        System.out.println("\n RESULTADOS — " + competencia.getNombre());
        for (Partido p : competencia.getPartidos()) {
            System.out.println(" • " + p);
        }
    }
}
