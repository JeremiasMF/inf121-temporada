package Clases;

public class Persona {
    protected int id_persona;
    protected String nombre_p;
    protected String paterno_p;
    protected String materno_p;
    protected String telefono;
    protected String f_nacimiento;
    protected String tipo;

    // Constructor
    public Persona(int id_persona, String nombre_p, String paterno_p, String materno_p, String telefono, String f_nacimiento, String tipo) {
        this.id_persona = id_persona;
        this.nombre_p = nombre_p;
        this.paterno_p = paterno_p;
        this.materno_p = materno_p;
        this.telefono = telefono;
        this.f_nacimiento = f_nacimiento;
        this.tipo = tipo;
    }

    // Getters y setters
    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre_p() {
        return nombre_p;
    }

    public void setNombre_p(String nombre_p) {
        this.nombre_p = nombre_p;
    }

    public String getPaterno_p() {
        return paterno_p;
    }

    public void setPaterno_p(String paterno_p) {
        this.paterno_p = paterno_p;
    }

    public String getMaterno_p() {
        return materno_p;
    }

    public void setMaterno_p(String materno_p) {
        this.materno_p = materno_p;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getF_nacimiento() {
        return f_nacimiento;
    }

    public void setF_nacimiento(String f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}