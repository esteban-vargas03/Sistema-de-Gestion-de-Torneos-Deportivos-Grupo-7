#  Sistema de Gestión de Torneos Deportivos

##  Descripción del Sistema

Sistema orientado a objetos en Java que modela la gestión de competencias deportivas. Permite administrar torneos en formato **liga** (todos contra todos) y **eliminación directa** (copa), incluyendo equipos, jugadores, partidos y resultados. El sistema genera fixtures automáticamente, calcula tablas de posiciones y registra notificaciones de cada evento, aplicando los cinco principios SOLID, herencia, polimorfismo e interfaces.

---

##  Integrantes

| Nombre | Correo |
|--------|--------|
| Juan Esteban Muñoz Vargas | jemunoz-2025a@corhuila.edu.co |
| Santiago Marin Franco | samarin-2025a@corhuila.edu.co |

---

##  Diagrama de Clases UML

![Diagrama de Clases](<img width="1198" height="1794" alt="SistemadeGestiondeTorneosDeportivos 3 0 drawio" src="https://github.com/user-attachments/assets/639bb340-266e-4b33-bd65-755f4d4043d2" />
)

---

##  Estructura del Proyecto

```
src/
└── com/
    └── torneos/
        ├── interfaces/
        │   ├── IJugable.java
        │   ├── IClasificable.java
        │   └── INotificable.java
        ├── model/
        │   ├── Competencia.java        (Clase abstracta)
        │   ├── TorneoLiga.java
        │   ├── TorneoEliminacion.java
        │   ├── Equipo.java
        │   ├── Jugador.java
        │   ├── Partido.java
        │   ├── Resultado.java
        │   └── TablaPosiciones.java
        ├── service/
        │   ├── GestorTorneos.java
        │   ├── GestorPartidos.java
        │   └── ServicioNotificacion.java
        └── Main.java
```

---

##  Explicación de Relaciones

| Relación | Tipo | Descripción |
|----------|------|-------------|
| `TorneoLiga` → `Competencia` | Herencia | Liga especializa la competencia con puntos y jornadas |
| `TorneoEliminacion` → `Competencia` | Herencia | Eliminación especializa con rondas y clasificados |
| `TorneoLiga` → `IClasificable` | Implementación | Liga genera fixture y calcula tabla de posiciones |
| `TorneoEliminacion` → `IClasificable` | Implementación | Eliminación genera fixture y calcula clasificados |
| `Partido` → `IJugable` | Implementación | El partido puede ser jugado y finalizado |
| `ServicioNotificacion` → `INotificable` | Implementación | Envía y registra notificaciones del sistema |
| `Competencia` ◇── `Equipo` | Composición | Una competencia tiene 1..* equipos inscritos |
| `Competencia` ◇── `Partido` | Composición | Una competencia tiene 1..* partidos generados |
| `Equipo` ◇── `Jugador` | Composición | Un equipo tiene 1..* jugadores en su plantilla |
| `Partido` → `Resultado` | Asociación | Un partido genera un resultado con goles y estado |
| `GestorTorneos` → `INotificable` | Dependencia | Usa la interfaz para notificar eventos del torneo |
| `GestorPartidos` → `INotificable` | Dependencia | Usa la interfaz para notificar resultados de partidos |

---

##  Clases Implementadas

| Clase / Interfaz | Paquete | Tipo | Descripción |
|------------------|---------|------|-------------|
| `IJugable` | interfaces | Interfaz | Contrato para disputar y finalizar partidos |
| `IClasificable` | interfaces | Interfaz | Contrato para generar fixture y tabla |
| `INotificable` | interfaces | Interfaz | Abstracción de notificaciones (DIP) |
| `Competencia` | model | Clase abstracta | Base de todos los torneos con métodos abstractos |
| `TorneoLiga` | model | Clase concreta | Torneo formato todos contra todos |
| `TorneoEliminacion` | model | Clase concreta | Torneo formato eliminación directa |
| `Equipo` | model | Clase concreta | Equipo con plantilla de jugadores |
| `Jugador` | model | Clase concreta | Jugador perteneciente a un equipo |
| `Partido` | model | Clase concreta | Partido entre dos equipos, implementa IJugable |
| `Resultado` | model | Clase concreta | Resultado con goles y estado del partido |
| `TablaPosiciones` | model | Clase concreta | Registro estadístico de un equipo en la liga |
| `GestorTorneos` | service | Servicio | Administra el ciclo de vida de los torneos |
| `GestorPartidos` | service | Servicio | Coordina la disputa de partidos |
| `ServicioNotificacion` | service | Servicio | Implementa INotificable, registra notificaciones |
| `Main` | — | Entrada | Demuestra el sistema completo en consola |

---

##  Aplicación de Principios SOLID

###  S — Single Responsibility Principle (Responsabilidad Única)

**Definición:** Cada clase debe tener una única razón para cambiar.

**¿Dónde se aplica?** Paquete `service`: cada clase gestiona un solo aspecto del sistema.
- `GestorTorneos` → registrar, buscar e iniciar torneos
- `GestorPartidos` → coordinar la disputa de partidos
- `ServicioNotificacion` → enviar y registrar notificaciones

**Código:**
```java
// GestorPartidos solo coordina partidos.
// Si cambia la lógica de notificaciones, solo se modifica ServicioNotificacion.
public class GestorPartidos {
    private INotificable servicioNotificacion; // abstracción, no implementación

    public void disputarPartido(IJugable jugable) { ... }
    public void jugarJornada(Competencia competencia) { ... }
    public void mostrarResultados(Competencia competencia) { ... }
}
```

**Justificación:** Si se necesita cambiar cómo se juegan los partidos, solo se modifica `GestorPartidos`. Si cambia la forma de notificar, solo se toca `ServicioNotificacion`. Ningún cambio afecta a las demás clases.

---

###  O — Open/Closed Principle (Abierto/Cerrado)

**Definición:** Las clases deben estar abiertas para extensión y cerradas para modificación.

**¿Dónde se aplica?** `Competencia` (clase abstracta) y sus subclases.

**Código:**
```java
// Competencia define el contrato con métodos abstractos
public abstract class Competencia {
    public abstract void generarFixture();
    public abstract void calcularTabla();
    public abstract String mostrarInformacion(); // cada subclase lo implementa diferente
}

// GestorTorneos funciona sin cambios para cualquier subtipo nuevo:
public void mostrarTorneos() {
    for (Competencia c : torneos) {
        System.out.println(c.mostrarInformacion()); // polimorfismo → OCP
    }
}
```

**Justificación:** Si se necesita agregar un `TorneoGrupos`, solo se crea la nueva clase que extiende `Competencia`. No se modifica `GestorTorneos`, `GestorPartidos` ni `Main`.

---

###  L — Liskov Substitution Principle (Sustitución de Liskov)

**Definición:** Los objetos de una subclase deben poder sustituir a los de la superclase sin alterar el comportamiento correcto.

**¿Dónde se aplica?** `GestorTorneos` y `GestorPartidos` trabajan con `Competencia` e `IJugable` como tipos de referencia.

**Código:**
```java
// Funciona para TorneoLiga o TorneoEliminacion sin cambios → LSP
public void iniciarTorneo(Competencia torneo) {
    torneo.generarFixture(); // comportamiento correcto en cualquier subclase
}

// Funciona con cualquier IJugable → LSP
public void disputarPartido(IJugable jugable) {
    jugable.jugar();
    jugable.finalizarPartido();
}
```

**Justificación:** `TorneoLiga` y `TorneoEliminacion` cumplen completamente el contrato de `Competencia`. `Partido` cumple el contrato de `IJugable`. Ninguna subclase lanza excepciones inesperadas ni rompe el flujo.

---

###  I — Interface Segregation Principle (Segregación de Interfaces)

**Definición:** Es mejor tener interfaces pequeñas y específicas que una sola interfaz grande.

**¿Dónde se aplica?** Tres interfaces separadas en el paquete `interfaces`.

**Código:**
```java
// Interfaz específica para partidos disputables
public interface IJugable {
    void jugar();
    void finalizarPartido();
}

// Interfaz específica para competencias con clasificación
public interface IClasificable {
    void calcularTabla();
    void generarFixture();
}

// Partido implementa solo IJugable (no necesita IClasificable)
public class Partido implements IJugable { ... }

// TorneoLiga implementa IClasificable (no necesita IJugable directamente)
public class TorneoLiga extends Competencia implements IClasificable { ... }
```

**Justificación:** Si hubiera una sola interfaz con todos los métodos, `Partido` estaría obligado a implementar `calcularTabla()` con un cuerpo vacío, lo que no tiene sentido. Las interfaces segregadas hacen que cada clase implemente solo lo que realmente puede hacer.

---

###  D — Dependency Inversion Principle (Inversión de Dependencias)

**Definición:** Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones.

**¿Dónde se aplica?** `GestorTorneos` y `GestorPartidos` dependen de `INotificable`, no de `ServicioNotificacion`.

**Código:**
```java
// GestorTorneos depende de la abstracción → DIP
public class GestorTorneos {
    private INotificable servicioNotificacion; // abstracción

    // Inyección de dependencia por constructor
    public GestorTorneos(INotificable servicioNotificacion) {
        this.servicioNotificacion = servicioNotificacion;
    }
}

// En Main se inyecta la implementación concreta:
INotificable notif = new ServicioNotificacion();
GestorTorneos gestor = new GestorTorneos(notif);
// Cambiar a email no requiere tocar GestorTorneos
```

**Justificación:** Si se quiere notificar por email en lugar de consola, solo se crea `ServicioEmailNotificacion implements INotificable` y se inyecta en el `Main`. `GestorTorneos` y `GestorPartidos` no se modifican en absoluto.

---

##  Conclusiones

- **SRP facilitó el mantenimiento del código:** Al separar las responsabilidades en clases de servicio, cualquier cambio en la lógica de notificaciones o de gestión de partidos afecta únicamente a la clase correspondiente. Esto nos permitió trabajar en partes del sistema de forma independiente sin generar errores en otras áreas.

- **OCP nos enseñó a pensar en extensibilidad:** Gracias a la clase abstracta `Competencia` con métodos abstractos, entendimos que un buen diseño no necesita modificarse para crecer. Si quisiéramos agregar un formato de torneo nuevo como grupos o eliminatoria doble, solo crearíamos una nueva clase sin tocar nada existente.

- **La mayor dificultad fue aplicar ISP correctamente:** Al inicio pensamos en una sola interfaz con todos los métodos del sistema, pero nos dimos cuenta que `Partido` estaría obligado a implementar `calcularTabla()`, lo cual no tiene sentido. Dividir en `IJugable`, `IClasificable` e `INotificable` resolvió el problema y dejó el código mucho más limpio.

- **DIP e ISP se complementan entre sí:** Para aplicar DIP necesitamos una interfaz bien definida (ISP) sobre la cual invertir la dependencia. Sin `INotificable` como abstracción pequeña y clara, no habría sido posible inyectar el servicio de notificación por constructor. Esto nos demostró que los principios SOLID no son independientes, sino que se refuerzan mutuamente.

---

##  Repositorio

[https://github.com/esteban-vargas03/Sistema-de-Gestion-de-Torneos-Deportivos-Grupo-7](https://github.com/esteban-vargas03/Sistema-de-Gestion-de-Torneos-Deportivos-Grupo-7)

---

*Proyecto de Programación y Diseño Orientado a Objetos — Corhuila 2026*
