package findassassin.controladores;

import findassassin.modelos.Usuario;
import findassassin.repositorios.PersonajeRepository;
import findassassin.repositorios.UsuarioRepository;
import findassassin.servicios.JuegoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {
    private final UsuarioRepository userRepo;
    private final PersonajeRepository perRepo;

    public UsuarioControler(UsuarioRepository userRepo, PersonajeRepository perRepo) {
        this.userRepo = userRepo;
        this.perRepo = perRepo;
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
        perRepo.save(user.getPersonaje());
        userRepo.save(user);
        return ResponseEntity.status(201).body(user.getUid());
    }

    @PutMapping("/")
    public ResponseEntity<?> ModificarUsuario(@Valid @RequestBody Usuario user) {
        System.out.println(user.getUid() + " " + user.getPersonaje());
        if (userRepo.existsById(user.getUid())) {
            perRepo.save(user.getPersonaje());
            userRepo.save(user);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }
}
