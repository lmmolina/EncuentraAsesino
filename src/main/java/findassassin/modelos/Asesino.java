package findassassin.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Asesino{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Integer id;

    private int acciones;
    private List<String> cambios;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asesino(int acciones, List<String> cambios) {
        this.acciones = acciones;
        this.cambios = cambios;
    }

    public Asesino(int acciones) {
        this.acciones = acciones;
        cambios = new ArrayList<>();

    }

    public Asesino() {
    }

    public int getAcciones() {
        return acciones;
    }

    public void setAcciones(int acciones) {
        this.acciones = acciones;
    }

    public List<String> getCambios() {
        return cambios;
    }

    public void setCambios(List<String> cambios) {
        this.cambios = cambios;
    }
}
