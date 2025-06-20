package findassassin.controladores;

import findassassin.modelos.Juego;
import findassassin.modelos.Usuario;
import findassassin.repositorios.JuegoRepository;
import findassassin.repositorios.UsuarioRepository;
import findassassin.servicios.JuegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/estado/{id_juego}")
    public ResponseEntity<?> ObtenerEstadoJuego(@PathVariable int id_juego) {
        if (juegoRepo.existsById(id_juego)){

        }
        return ResponseEntity.status(404).body("Juego no encontrado");
    }
}
