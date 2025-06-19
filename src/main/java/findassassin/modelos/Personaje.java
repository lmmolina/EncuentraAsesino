package findassassin.modelos;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int altura;
    private int peso;
    private int edad;
    private char sexo;
    private boolean velloFacial;
    private boolean gafas;
    private String colorPelo;
    private String tonoPiel;
    private String nacionalidad;
    private String complexion;
    private String imagen;

    public Personaje(int id, int altura, int peso, int edad, char sexo, boolean velloFacial, boolean gafas, String colorPelo, String tonoPiel, String nacionalidad, String complexion) {
        this.id = id;
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
    }

    public Personaje() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isVelloFacial() {
        return velloFacial;
    }

    public void setVelloFacial(boolean velloFacial) {
        this.velloFacial = velloFacial;
    }

    public boolean isGafas() {
        return gafas;
    }

    public void setGafas(boolean gafas) {
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
