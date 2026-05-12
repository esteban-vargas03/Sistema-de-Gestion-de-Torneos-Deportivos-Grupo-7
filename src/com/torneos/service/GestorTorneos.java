package com.torneos.service;

import com.torneos.interfaces.INotificable;
import com.torneos.model.Competencia;
import com.torneos.model.Equipo;
import com.torneos.model.TorneoLiga;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las competencias registradas en el sistema.
 *
 * SRP: única responsabilidad → administrar el ciclo de vida de los torneos.
 * OCP: trabaja con Competencia abstracta; acepta cualquier subtipo (Liga, Eliminación, etc.)
 *      sin necesidad de modificar este gestor.
 * DIP: depende de INotificable (abstracción), no de una implementación concreta.
 */
public class GestorTorneos {

    private List<Competencia> torneos;
    private INotificable servicioNotificacion;

    /**
     * @param servicioNotificacion abstracción inyectada por constructor (DIP)
     */
    public GestorTorneos(INotificable servicioNotificacion) {
        this.torneos              = new ArrayList<>();
        this.servicioNotificacion = servicioNotificacion;
    }

    /**
     * Registra un nuevo torneo en el sistema.
     */
    public void registrarTorneo(Competencia torneo) {
        torneos.add(torneo);
        System.out.println("  Torneo registrado: " + torneo.getNombre());
        servicioNotificacion.enviarNotificacion(
                "Sistema",
                "Nuevo torneo creado: " + torneo.getNombre(),
                "REGISTRO"
        );
    }

    /**
     * Inscribe un equipo en un torneo específico.
     */
    public void inscribirEquipo(Competencia torneo, Equipo equipo) {
        torneo.agregarEquipo(equipo);
        servicioNotificacion.enviarNotificacion(
                equipo.getNombre(),
                "Inscrito en el torneo: " + torneo.getNombre(),
                "INSCRIPCION"
        );
    }

    /**
     * Inicia un torneo generando su fixture.
     */
    public void iniciarTorneo(Competencia torneo) {
        System.out.println("\n Iniciando torneo: " + torneo.getNombre());
        torneo.generarFixture();
        servicioNotificacion.enviarNotificacion(
                "Equipos",
                "El torneo '" + torneo.getNombre() + "' ha comenzado. Fixture disponible.",
                "INICIO"
        );
    }

    /**
     * Muestra todos los torneos registrados (polimorfismo).
     * OCP en acción: funciona para cualquier subtipo de Competencia presente o futuro.
     */
    public void mostrarTorneos() {
        System.out.println("\n══ TORNEOS REGISTRADOS ══");
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos registrados.");
            return;
        }
        for (Competencia c : torneos) {
            System.out.println(" • " + c.mostrarInformacion()); // Polimorfismo
        }
    }

    /**
     * Busca un torneo por nombre.
     */
    public Competencia buscarTorneo(String nombre) {
        for (Competencia c : torneos) {
            if (c.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                return c;
            }
        }
        return null;
    }

    public List<Competencia> getTorneos() { return torneos; }
}
