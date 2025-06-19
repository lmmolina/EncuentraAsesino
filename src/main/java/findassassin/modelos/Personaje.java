package findassassin.modelos;

import jakarta.persistence.*;

import java.util.Random;

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
        Random r = new Random();
        this.altura = r.nextInt(140, 210);
        this.peso = r.nextInt(30, 120);
        this.edad = r.nextInt(18, 90);
        this.sexo = r.nextBoolean() ? 'F' : 'M';
        this.velloFacial = r.nextBoolean();
        this.gafas = r.nextBoolean();
        this.colorPelo = obtenerColorDePelo(r.nextInt(7));
        this.tonoPiel = obtenerColorDePiel(r.nextInt(7));
        this.nacionalidad = obtenerNacionalidad(r.nextInt(12));
        this.complexion = obtenerComplexion(r.nextInt(5));
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

    private String obtenerColorDePelo(int codigo) {
        switch (codigo) {
            case 1:
                return "Negro";
            case 2:
                return "Castaño";
            case 3:
                return "Rubio";
            case 4:
                return "Pelirrojo";
            case 5:
                return "Gris";
            case 6:
                return "Blanco";
            default:
                return "Calvo";
        }
    }

    public String obtenerColorDePiel(int codigo) {
        switch (codigo) {
            case 1:
                return "Blanco";
            case 2:
                return "Negro";
            case 3:
                return "Moreno";
            case 4:
                return "Asiático";
            case 5:
                return "Indio";
            case 6:
                return "Latino";
            default:
                return "Medio Oriente";
        }
    }

    public String obtenerNacionalidad(int codigo) {
        switch (codigo) {
            case 1:
                return "Cuba";
            case 2:
                return "España";
            case 3:
                return "Venezuela";
            case 4:
                return "Ecuador";
            case 5:
                return "Francia";
            case 6:
                return "Alemania";
            case 7:
                return "Egipto";
            case 8:
                return "Marruecos";
            case 9:
                return "India";
            case 10:
                return "Pakistán";
            case 11:
                return "China";
            default:
                return "Japón";
        }
    }

    public String obtenerComplexion(int codigo) {
        switch (codigo) {
            case 1:
                return "Delgada";
            case 2:
                return "Media";
            case 3:
                return "Atlética";
            case 4:
                return "Robusta";
            default:
                return "Obesa";
        }
    }
}
