package findassassin.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

@Entity
public class Usuario {
    @Id
    private String uid;
    @NotEmpty
    private String nombre;
    @NotEmpty
    @Email
    private String correo;
    private int puntos;
    @OneToOne
    @JoinColumn(name = "id")
    private Personaje personaje;

    public Usuario(String uid, String nombre, String correo, int puntos) {
        this.uid = uid;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = puntos;
    }

    public Usuario() {
        uid = UUID.randomUUID().toString().substring(0, 8);
        puntos = 0;
        personaje = new Personaje();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
