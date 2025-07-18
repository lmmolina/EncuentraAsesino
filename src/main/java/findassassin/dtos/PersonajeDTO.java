package findassassin.dtos;

import findassassin.modelos.Asesino;
import findassassin.modelos.Personaje;
import findassassin.modelos.Usuario;
import jakarta.persistence.*;

import java.util.Random;

public class PersonajeDTO {
    private String id_usuario;
    private String nombre;
    private Integer id_personaje;
    private Integer altura;
    private Integer peso;
    private Integer edad;
    private Character sexo;
    private Boolean velloFacial;
    private Boolean gafas;
    private String colorPelo;
    private String tonoPiel;
    private String nacionalidad;
    private String complexion;
    private String imagen;

    public PersonajeDTO(String id_usuario, String nombre, int id_personaje, int altura, int peso, int edad, char sexo, boolean velloFacial, boolean gafas, String colorPelo, String tonoPiel, String nacionalidad, String complexion, String imagen) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.id_personaje = id_personaje;
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
        this.sexo = sexo;
        this.velloFacial = velloFacial;
        this.gafas = gafas;
        this.colorPelo = colorPelo;
        this.tonoPiel = tonoPiel;
        this.nacionalidad = nacionalidad;
        this.complexion = complexion;
        this.imagen = imagen;
    }

    public PersonajeDTO(Usuario u) {
        id_usuario = u.getUid();
        nombre = u.getNombre();
        this.id_personaje = u.getPersonaje().getId();
        this.altura = u.getPersonaje().getAltura();
        this.peso = u.getPersonaje().getPeso();
        this.edad = u.getPersonaje().getEdad();
        this.sexo = u.getPersonaje().getSexo();
        this.velloFacial = u.getPersonaje().isVelloFacial();
        this.gafas = u.getPersonaje().isGafas();
        this.colorPelo = u.getPersonaje().getColorPelo();
        this.tonoPiel = u.getPersonaje().getTonoPiel();
        this.nacionalidad = u.getPersonaje().getNacionalidad();
        this.complexion = u.getPersonaje().getComplexion();
    }

    public PersonajeDTO() {
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId_personaje() {
        return id_personaje;
    }

    public void setId_personaje(Integer id_personaje) {
        this.id_personaje = id_personaje;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Boolean getVelloFacial() {
        return velloFacial;
    }

    public void setVelloFacial(Boolean velloFacial) {
        this.velloFacial = velloFacial;
    }

    public Boolean getGafas() {
        return gafas;
    }

    public void setGafas(Boolean gafas) {
        this.gafas = gafas;
    }

    public String getColorPelo() {
        return colorPelo;
    }

    public void setColorPelo(String colorPelo) {
        this.colorPelo = colorPelo;
    }

    public String getTonoPiel() {
        return tonoPiel;
    }

    public void setTonoPiel(String tonoPiel) {
        this.tonoPiel = tonoPiel;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getComplexion() {
        return complexion;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

