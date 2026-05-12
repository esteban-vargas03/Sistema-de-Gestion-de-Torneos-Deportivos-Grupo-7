package com.torneos.service;

import com.torneos.interfaces.INotificable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del servicio de notificaciones.
 *
 * DIP: implementa INotificable → puede ser reemplazada por cualquier otra
 * implementación (email, SMS, push) sin modificar GestorTorneos ni GestorPartidos.
 * SRP: única responsabilidad → registrar y mostrar notificaciones.
 */
public class ServicioNotificacion implements INotificable {

    private List<String> historial;

    public ServicioNotificacion() {
        this.historial = new ArrayList<>();
    }

    /**
     * Envía una notificación por consola y la guarda en el historial.
     */
    @Override
    public void enviarNotificacion(String destinatario, String mensaje, String tipo) {
        String notif = "[" + tipo + " → " + destinatario + "] " + mensaje;
        historial.add(notif);
        System.out.println(notif);
    }

    /**
     * Muestra el historial completo de notificaciones enviadas.
     */
    public void mostrarHistorial() {
        System.out.println("\n══ HISTORIAL DE NOTIFICACIONES ══");
        if (historial.isEmpty()) {
            System.out.println("Sin notificaciones.");
            return;
        }
        for (int i = 0; i < historial.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + historial.get(i));
        }
    }

    public List<String> getHistorial() { return historial; }
}
