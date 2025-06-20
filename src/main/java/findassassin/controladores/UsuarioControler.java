package findassassin.controladores;

import findassassin.modelos.Usuario;
import findassassin.repositorios.UsuarioRepository;
import findassassin.servicios.JuegoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {
    private final UsuarioRepository userRepo;
    private JuegoService servicio;

    public UsuarioControler(UsuarioRepository userRepo, JuegoService servicio) {
        this.userRepo = userRepo;
        this.servicio = servicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ObtenerUsuario(@PathVariable String id) {
        try {
            Usuario u = userRepo.findById(id).get();
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> InsertarUsuario(@Valid @RequestBody Usuario user) {
        userRepo.save(user);
        return ResponseEntity.status(201).body(user.getUid());
    }

    @PutMapping("/")
    public ResponseEntity<?> ModificarUsuario(@Valid @RequestBody Usuario user) {
        if (userRepo.existsById(user.getUid())) {
            userRepo.save(user);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }
}
