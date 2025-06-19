package findassassin.modelos;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Asesino extends Personaje {
    private int acciones;
    private List<String> cambios;

    public Asesino(int id, int altura, int peso, int edad, char sexo, boolean velloFacial, boolean gafas, String colorPelo, String tonoPiel, String nacionalidad, String complexion, int acciones, List<String> cambios) {
        super(id, altura, peso, edad, sexo, velloFacial, gafas, colorPelo, tonoPiel, nacionalidad, complexion);
        this.acciones = acciones;
        this.cambios = cambios;
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
