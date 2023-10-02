package Java.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Aspirantes")
public class Aspirantes {

    @DatabaseField(id = true)
    private String cedula;
    @DatabaseField
    private String nombreCompleto;
    @DatabaseField
    private int edad;
    @DatabaseField
    private int experienciaAnios;
    @DatabaseField
    private String profesion;
    @DatabaseField
    private String telefono;

    // Constructor y métodos getter/setter

    public Aspirantes(String cedula,String nombreCompleto,int edad, int experienciaAnios,String profesion,String telefono){
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.experienciaAnios = experienciaAnios;
        this.profesion = profesion;
        this.telefono = telefono;
    }
    public Aspirantes() {
        // COnstructor vacio, necesario para ORMLite
        this.cedula ="";
        this.nombreCompleto = "";
        this.edad = 0;
        this.experienciaAnios = 0;
        this.profesion = "";
        this.telefono = "";
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getExperienciaAnios() {
        return experienciaAnios;
    }

    public void setExperienciaAnios(int experienciaAnios) {
        this.experienciaAnios = experienciaAnios;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}


