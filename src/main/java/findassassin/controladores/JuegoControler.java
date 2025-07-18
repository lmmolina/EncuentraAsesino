package findassassin.controladores;

import com.fasterxml.jackson.databind.JsonNode;
import findassassin.dtos.PersonajeDTO;
import findassassin.modelos.*;
import findassassin.repositorios.JuegoRepository;
import findassassin.repositorios.UsuarioRepository;
import findassassin.servicios.JuegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/juego")
public class JuegoControler {
    private final UsuarioRepository userRepo;
    private final JuegoRepository juegoRepo;
    private JuegoService servicio;

    public JuegoControler(UsuarioRepository userRepo, JuegoRepository juegoRepo, JuegoService servicio) {
        this.userRepo = userRepo;
        this.juegoRepo = juegoRepo;
        this.servicio = servicio;
    }

    @GetMapping("/unirse/{id_user}")
    public ResponseEntity<?> UnirseJuego(@PathVariable String id_user) {
        try {
            Usuario u = userRepo.findById(id_user).get();
            return ResponseEntity.accepted().body(servicio.UnirseJuego(u));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @GetMapping("/{id_juego}/estado")
    public ResponseEntity<?> ObtenerEstadoJuego(@PathVariable int id_juego, @RequestParam String jugador) {
        if (juegoRepo.existsById(id_juego)) {
            return ResponseEntity.ok(servicio.EstadoJuego(id_juego, jugador));
        }
        return ResponseEntity.status(404).body("Juego no encontrado");
    }

    @GetMapping("/{id_juego}/tiempo")
    public ResponseEntity<?> ObtenerTiempoJuego(@PathVariable int id_juego) {
        Juego j = juegoRepo.findById(id_juego).orElse(null);
        if (j != null) {
            long t = 120 - Duration.between(j.getTiempo(), LocalDateTime.now()).getSeconds();
            return ResponseEntity.ok(t > 0 ? t : 0);
        }
        return ResponseEntity.status(404).body("Juego no encontrado");
    }

    @GetMapping("/{id_juego}/personajes")
    public ResponseEntity<?> ObtenerPersonajesJuego(@PathVariable int id_juego) {
        Juego j = juegoRepo.findById(id_juego).orElse(null);
        if (j != null) {
            List<PersonajeDTO> personajes = servicio.ObtenerPersonajesJuego(j);
            return ResponseEntity.ok(personajes);
        }
        return ResponseEntity.status(404).body("Juego no encontrado");
    }

    @GetMapping("/personajes/{jugador}")
    public ResponseEntity<?> ObtenerPersonajesJuego(@PathVariable String jugador) {
        Usuario u = userRepo.findById(jugador).orElse(null);
        if (u != null) {
            return ResponseEntity.ok(new PersonajeDTO(u));
        }
        return ResponseEntity.status(404).body("Jugador no encontrado");
    }

    @GetMapping("/{id_juego}/personajes/{jugador}")
    public ResponseEntity<?> ObtenerPersonajesJuego(@PathVariable int id_juego, @PathVariable String jugador) {
        Juego j = juegoRepo.findById(id_juego).orElse(null);
        if (j != null) {
            List<PersonajeDTO> personajes = servicio.ObtenerPersonajesJuego(j, jugador);
            return ResponseEntity.ok(personajes);
        }
        return ResponseEntity.status(404).body("Juego no encontrado");
    }

    @GetMapping("/{id_juego}/soyasesino")
    public ResponseEntity<?> SoyAsesino(@PathVariable int id_juego, @RequestParam String jugador) {
        Usuario u = userRepo.findById(jugador).orElse(null);
        if (u != null) {
            if (juegoRepo.existsById(id_juego)) {
                return ResponseEntity.ok(u.getPersonaje().getAsesino() != null);
            }
            return ResponseEntity.status(404).body("Juego no encontrado");
        }
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @PostMapping("/{id_juego}")
    public ResponseEntity<?> RealizarJugada(@PathVariable int id_juego, @RequestBody Filtros filtro) {
        if (userRepo.existsById(filtro.getId_usuario())) {
            Juego j = juegoRepo.findById(id_juego).orElse(null);
            if (j != null) {
                if (servicio.juegoTerminados(j)) {
                    return ResponseEntity.badRequest().body("El juego ya ha terminado");
                }
                servicio.RealizarJugada(j, filtro);
            }
            return ResponseEntity.status(404).body("Juego no encontrado");
        }
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @PostMapping("/{id_juego}/asesino")
    public ResponseEntity<?> RealizarJugadaAsesino(@PathVariable int id_juego, @RequestBody JsonNode filtro) {
        if (userRepo.existsById(filtro.get("id_usuario").asText())) {
            Juego j = juegoRepo.findById(id_juego).orElse(null);
            if (j != null) {
                if (servicio.juegoTerminados(j)) {
                    return ResponseEntity.badRequest().body("El juego ya ha terminado");
                }
                Personaje p = servicio.RealizarJugadaAsesino(j, filtro.get("cambio").asText(), filtro.get("id_usuario").asText());
                if (p == null) {
                    Set<String> aplicados = new HashSet<>(j.getAcciones().stream().map(f -> f.getCampo()).toList());
                    return ResponseEntity.status(400).body(aplicados);
                } else {
                    return ResponseEntity.ok(p);
                }
            }
            return ResponseEntity.status(404).body("Juego no encontrado");
        }
        return ResponseEntity.status(404).body("Jugador no encontrado");
    }

    @PutMapping("/{id_juego}")
    public ResponseEntity<?> AdivinarAsesino(@PathVariable int id_juego, @RequestBody Usuario asesino, @RequestParam String jugador, @RequestParam int turno) {
        if (userRepo.existsById(asesino.getUid()) && userRepo.existsById(jugador)) {
            Juego j = juegoRepo.findById(id_juego).orElse(null);
            if (j != null && j.getUsuarios().stream().anyMatch(us -> us.getUid().equals(asesino.getUid())) && j.getUsuarios().stream().anyMatch(us -> us.getUid().equals(jugador))) {
                if (servicio.juegoTerminados(j)) {
                    return ResponseEntity.badRequest().body("El juego ya ha terminado");
                }
                switch (servicio.AdivinarAsesino(j, jugador, asesino.getUid(), turno)) {
                    case 0 -> {
                        return ResponseEntity.ok("Has adivina, pero lo siento, no eres el primer jugador en adivinar");
                    }
                    case 1 -> {
                        return ResponseEntity.ok("Has adivinado correctamente al asesino, ¡Felicidades!");
                    }
                    case -1 -> {
                        return ResponseEntity.status(406).body("Lo siento, el jugador que has adivinado no es un asesino");
                    }
                    case -2 -> {
                        return ResponseEntity.badRequest().body("Lo siento, no puedes adivinar en este turno");
                    }
                }
            }
            return ResponseEntity.status(404).body("Juego no encontrado");
        }
        return ResponseEntity.status(404).body("Jugador no encontrado");
    }

    @GetMapping("/{id_juego}/ganador")
    public ResponseEntity<?> ObtenerGanador(@PathVariable int id_juego) {
        Juego j = juegoRepo.findById(id_juego).orElse(null);
        if (j != null) {
            if (j.getGanador() != null) {
                return ResponseEntity.ok(j.getGanador());
            } else {
                return ResponseEntity.status(400).body("El juego aún no ha terminado");
            }
        }
        return ResponseEntity.status(404).body("Juego no encontrado");
    }

}
