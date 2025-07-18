package findassassin.controladores;

import findassassin.modelos.Usuario;
import findassassin.repositorios.PersonajeRepository;
import findassassin.repositorios.UsuarioRepository;
import findassassin.servicios.ImageGenerator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuario")
public class UsuarioControler {
    private final UsuarioRepository userRepo;
    private final PersonajeRepository perRepo;
    private final ImageGenerator generator;

    public UsuarioControler(UsuarioRepository userRepo, PersonajeRepository perRepo, ImageGenerator generator) {
        this.userRepo = userRepo;
        this.perRepo = perRepo;
        this.generator = generator;
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
        Usuario userbd= userRepo.findByCorreo(user.getCorreo());
        if(userbd!=null){
            return ResponseEntity.status(201).body(userbd);
        }
        perRepo.save(user.getPersonaje());
        try {
            generator.generateAndSaveImage(user.getPersonaje(),user.getUid());
            user.getPersonaje().setImagen("imagen/" + user.getUid()+".jpeg");
        } catch (Exception e) {
            System.out.println("Error generando la imagen.");
            e.printStackTrace();
        }
        userRepo.save(user);
        return ResponseEntity.status(201).body(user);
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
