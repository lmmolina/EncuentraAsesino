package findassassin.modelos;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    @JoinTable(
            name = "usuario_juego",
            joinColumns = @JoinColumn(name = "juego_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios;
    @OneToMany
    @JoinColumn(name = "id_juego")
    private List<Filtros> acciones;
    private int turnos;
    @OneToOne
    @JoinColumn(name = "id_ganador")
    private Usuario ganador;
    private LocalDateTime tiempo;
    private boolean iniciado;

    public Juego(List<Usuario> usuarios, int turnos, Usuario ganador) {
        this.usuarios = usuarios;
        this.turnos = turnos;
        this.ganador = ganador;
    }

    public Juego() {
    }

    public void inicarJuego() {
        usuarios = new ArrayList<>();
        turnos = 0;
        tiempo = LocalDateTime.now();
        iniciado = false;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public Usuario getGanador() {
        return ganador;
    }

    public void setGanador(Usuario ganador) {
        this.ganador = ganador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTiempo() {
        return tiempo;
    }

    public void setTiempo(LocalDateTime tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public List<Filtros> getAcciones() {
        return acciones;
    }

    public void AvanzarTurno(){
        turnos++;
    }
}
