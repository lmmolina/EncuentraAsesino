package findassassin.servicios;

import findassassin.dtos.PersonajeDTO;
import findassassin.modelos.*;
import findassassin.repositorios.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class JuegoService {
    private final JuegoRepository juegoRepo;
    private final UsuarioRepository usuarioRepo;
    private final PersonajeRepository personajeRepo;
    private final FiltrosRepository filtroRepo;
    private final AsesinoRepository asesinoRepo;
    private List<Juego> juegos;

    public JuegoService(JuegoRepository juegoRepo, UsuarioRepository usuarioRepo, PersonajeRepository personajeRepo, FiltrosRepository filtroRepo, AsesinoRepository asesinoRepo) {
        this.juegoRepo = juegoRepo;
        this.usuarioRepo = usuarioRepo;
        this.personajeRepo = personajeRepo;
        this.filtroRepo = filtroRepo;
        this.asesinoRepo = asesinoRepo;
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

    public String EstadoJuego(int id, String user_id) {
        IniciarJuego();
        Juego j = juegoRepo.findById(id).orElse(null);//juegos.stream().filter(juego -> juego.getId() == id).findFirst().orElse(null);
        if (j == null) {
            return "TERMINADO";
        }
        if (!j.isIniciado()) {
            return "NO INICIADO";
        }
        if (j.getAcciones().stream().filter(acccion -> acccion.getTurno() == j.getTurnos() && acccion.getId_usuario().equals(user_id)).count() == 0) {
            return "ESPERANDO ACCION";
        }
        return "ESPERANDO TURNO";
    }

    private void IniciarJuego() {
        for (Juego j : juegos) {
            if (!j.isIniciado()) {
                j.setUsuarios(juegoRepo.findById(j.getId()).get().getUsuarios());//IMPORTANTE CUANDO TIENES DATOS RECURRENTES EN ALGUNAS OCASIONES PARA EVITAR SOBRE CARGA NO SE AÑADEN DIRECTAMENTE AL MODELO Y HAY QUE VOLVERLOS A CALGAR
                if (j.getUsuarios().size() >= 10) {
                    j.setIniciado(true);
                    AsginarAsesino(j);
                    juegoRepo.save(j);
                } else if (j.getUsuarios().size() >= 4 && Duration.between(j.getTiempo(), LocalDateTime.now()).toMinutes() >= 5) {
                    j.setIniciado(true);
                    AsginarAsesino(j);
                    juegoRepo.save(j);
                }
            }
        }
    }

    private void AsginarAsesino(Juego j) {
        int asePos = new Random().nextInt(j.getUsuarios().size());
        Asesino asesino = new Asesino(j.getUsuarios().size() / 2);
        j.getUsuarios().get(asePos).getPersonaje().setAsesino(asesino);
        asesinoRepo.save(asesino);
        personajeRepo.save(j.getUsuarios().get(asePos).getPersonaje());
    }

    public List<PersonajeDTO> ObtenerPersonajesJuego(Juego j) {
        return j.getUsuarios().stream().map(usuario -> new PersonajeDTO(usuario)).toList();
    }

    public List<PersonajeDTO> ObtenerPersonajesJuego(Juego j, String id_usuario) {
        List<PersonajeDTO> personajes = j.getUsuarios().stream().map(usuario -> new PersonajeDTO(usuario)).toList();
        List<Filtros> filtros = j.getAcciones().stream().filter(acc -> acc.getId_usuario().equals(id_usuario)).toList();
        Personaje asesino = j.getUsuarios().stream().filter(u -> u.getPersonaje().getAsesino() != null)
                .map(Usuario::getPersonaje).findFirst().get();
        for (Filtros f : filtros) {
            switch (f.getCampo()) {
                case "altura":
                    personajes = personajes.stream().filter(p -> {
                        int valor = Integer.parseInt(f.getCampo());
                        if (valor > asesino.getAltura() && valor > p.getAltura()) {
                            return true;
                        }
                        if (valor < asesino.getAltura() && valor < p.getAltura()) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "peso":
                    personajes = personajes.stream().filter(p -> {
                        int valor = Integer.parseInt(f.getCampo());
                        if (valor > asesino.getPeso() && valor > p.getPeso()) {
                            return true;
                        }
                        if (valor < asesino.getPeso() && valor < p.getPeso()) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "edad":
                    personajes = personajes.stream().filter(p -> {
                        int valor = Integer.parseInt(f.getCampo());
                        if (valor > asesino.getEdad() && valor > p.getEdad()) {
                            return true;
                        }
                        if (valor < asesino.getEdad() && valor < p.getEdad()) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "sexo":
                    personajes = personajes.stream().filter(p -> {
                        char valor = f.getCampo().charAt(0);
                        if (valor == 'M' && p.getSexo() == 'M' && asesino.getSexo() == 'M') {
                            return true;
                        }
                        if (valor == 'F' && p.getSexo() == 'F' && asesino.getSexo() == 'F') {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "velloFacial":
                    personajes = personajes.stream().filter(p -> {
                        boolean valor = Boolean.parseBoolean(f.getCampo());
                        if (valor && p.getVelloFacial() && asesino.isVelloFacial()) {
                            return true;
                        }
                        if (!valor && !p.getVelloFacial() && !asesino.isVelloFacial()) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "gafas":
                    personajes = personajes.stream().filter(p -> {
                        boolean valor = Boolean.parseBoolean(f.getCampo());
                        if (valor && p.getGafas() && asesino.isGafas()) {
                            return true;
                        }
                        if (!valor && !p.getGafas() && !asesino.isGafas()) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "colorPelo":
                    personajes = personajes.stream().filter(p -> {
                        String valor = f.getCampo();
                        if (valor.equals(p.getColorPelo()) && valor.equals(asesino.getColorPelo())) {
                            return true;
                        }
                        if (!valor.equals(p.getColorPelo()) && !valor.equals(asesino.getColorPelo())) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "tonoPiel":
                    personajes = personajes.stream().filter(p -> {
                        String valor = f.getCampo();
                        if (valor.equals(p.getTonoPiel()) && valor.equals(asesino.getTonoPiel())) {
                            return true;
                        }
                        if (!valor.equals(p.getTonoPiel()) && !valor.equals(asesino.getTonoPiel())) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "nacionalidad":
                    personajes = personajes.stream().filter(p -> {
                        String valor = f.getCampo();
                        if (valor.equals(p.getNacionalidad()) && valor.equals(asesino.getNacionalidad())) {
                            return true;
                        }
                        if (!valor.equals(p.getNacionalidad()) && !valor.equals(asesino.getNacionalidad())) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
                case "complexion":
                    personajes = personajes.stream().filter(p -> {
                        String valor = f.getCampo();
                        if (valor.equals(p.getComplexion()) && valor.equals(asesino.getComplexion())) {
                            return true;
                        }
                        if (!valor.equals(p.getComplexion()) && !valor.equals(asesino.getComplexion())) {
                            return true;
                        }
                        return false;
                    }).toList();
                    break;
            }
        }
        return personajes;
    }

    public void RealizarJugada(Juego j, Filtros f) {
        AdelantarTurno(j);
        Usuario u = usuarioRepo.getReferenceById(f.getId_usuario());
        if (j.getTurnos() == f.getTurno() && u.getPersonaje().getAsesino() == null) {
            j.getAcciones().add(f);
            filtroRepo.save(f);
            juegoRepo.save(j);
        }
    }

    private void AdelantarTurno(Juego j) {
        /*if (Duration.between(j.getTiempo(), LocalDateTime.now()).toMinutes() >= 2) {
            j.AvanzarTurno();
            juegoRepo.save(j);
        } else*/
        if (j.getAcciones().stream().filter(acc -> acc.getTurno() == j.getTurnos()).count() == j.getUsuarios().size()) {
            j.AvanzarTurno();
            juegoRepo.save(j);
        }
    }

    public Personaje RealizarJugadaAsesino(Juego j, String cambio, String id_usuario) {
        Usuario u = usuarioRepo.getReferenceById(id_usuario);
        List<Filtros> filtros = j.getAcciones();
        if(filtros.stream().anyMatch(fil->fil.getCampo().equals(cambio))){
            return null;
        }
        if (u.getPersonaje().getAsesino() != null) {
            AdelantarTurno(j);
            Asesino asesino = u.getPersonaje().getAsesino();
            if (asesino.getAcciones() > asesino.getCambios().size() && !asesino.getCambios().contains(cambio)) {
                u.getPersonaje().cambiarAtributoRandom(cambio);
                asesino.getCambios().add(cambio);
                asesinoRepo.save(asesino);
                Filtros f = new Filtros("asesino", cambio);
                j.getAcciones().add(f);
                filtroRepo.save(f);
                juegoRepo.save(j);
            }
            return u.getPersonaje();
        }
        return null;
    }

    public int AdivinarAsesino(Juego j, String id_usuario, String id_asesino, int turno) {
        AdelantarTurno(j);
        Usuario usuario = usuarioRepo.getReferenceById(id_usuario);
        Usuario asesino = usuarioRepo.getReferenceById(id_asesino);
        if (j.getTurnos() == turno && j.getAcciones().stream().filter(f -> f.getId_usuario().equals(id_usuario) && f.getTurno() == turno).count() == 0) {
            return -2;
        }
        if (asesino.getPersonaje().getAsesino() != null) {
            Filtros f = new Filtros("adivinar", id_asesino);
            filtroRepo.save(f);
            j.getAcciones().add(f);
            if (j.getGanador() != null) {
                j.setGanador(usuario);
                juegoRepo.save(j);
                return 1;
            }
            juegoRepo.save(j);
            return 0;
        }
        return -1;
    }

    public boolean juegoTerminados(Juego j) {
        if (j.getGanador() != null || j.getTurnos() >= 10) {
            j.getUsuarios().forEach(u -> u.getPersonaje().setAsesino(null));
            return true;
        }
        return false;
    }
}
