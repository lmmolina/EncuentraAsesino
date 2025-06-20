package findassassin.servicios;

import findassassin.modelos.Juego;
import findassassin.modelos.Usuario;
import findassassin.repositorios.JuegoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class JuegoService {
    private final JuegoRepository juegoRepo;
    private List<Juego> juegos;

    public JuegoService(JuegoRepository juegoRepo) {
        this.juegoRepo = juegoRepo;
        juegos = juegoRepo.obtenerJuegosNoTerminados();
    }

    public int UnirseJuego(Usuario u) {
        IniciarJuego();
        Juego j = juegos.stream().filter(juego -> !juego.isIniciado()).findFirst().orElse(null);
        if (j == null) {
            j = new Juego();
            j.inicarJuego();
            j.getUsuarios().add(u);
            juegos.add(j);
        } else {
            if (!j.getUsuarios().contains(u)) {
                j.getUsuarios().add(u);
            }
        }
        juegoRepo.save(j);
        return j.getId();
    }

    public String EstadoJuego(int id, String user_id){
        Juego j = juegos.stream().filter(juego -> juego.getId() == id).findFirst().orElse(null);
        if (j == null){
            return "TERMINADO";
        }
        if (!j.isIniciado()){
            return "NO INICIADO";
        }
        if(j.getAcciones().stream().filter(acccion -> acccion.getTurno()== j.getTurnos() && acccion.getId_usuario().equals(user_id)).count()==0){
            return "ESPERANDO ACCION";
        }
        return "ESPERANDO TURNO";
    }

    private void IniciarJuego() {
        for (Juego j : juegos) {
            if (!j.isIniciado()) {
                if (j.getUsuarios().size() >= 10) {
                    j.setIniciado(true);
                } else if (j.getUsuarios().size() >= 4 && Duration.between(j.getTiempo(), LocalDateTime.now()).toMinutes() >= 5) {
                    j.setIniciado(true);
                }
            }
        }
    }
}
