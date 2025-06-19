package findassassin.modelos;

import jakarta.persistence.*;

import java.util.List;

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
    private int turnos;
    @OneToOne
    @JoinColumn(name = "id_ganador")
    private Usuario ganador;

    public Juego(List<Usuario> usuarios, int turnos, Usuario ganador) {
        this.usuarios = usuarios;
        this.turnos = turnos;
        this.ganador = ganador;
    }

    public Juego() {
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
}
