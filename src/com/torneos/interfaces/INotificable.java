package com.torneos.interfaces;

/**
 * Abstracción para el envío de notificaciones del sistema.
 * Principio DIP: los servicios de alto nivel dependen de esta
 * abstracción, no de una implementación concreta (email, consola, SMS).
 */
public interface INotificable {

    /**
     * Envía una notificación sobre un evento del torneo.
     * @param destinatario nombre del equipo o persona a notificar
     * @param mensaje      contenido de la notificación
     * @param tipo         tipo de evento (RESULTADO, FIXTURE, CLASIFICACION)
     */
    void enviarNotificacion(String destinatario, String mensaje, String tipo);
}
