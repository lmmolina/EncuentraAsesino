package findassassin.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

@Entity
public class Filtros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String id_usuario;
    @Pattern(regexp = "altura|peso|edad|sexo|vellofacial|gafas|colorpelo|tonopiel|nacionalidad|complexion")
    private String campo;
    private Object valor;
    private int turno;

    public Filtros(long id, String campo, Object valor) {
        this.id = id;
        this.campo = campo;
        this.valor = valor;
    }

    public Filtros() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
}
