package Clases;

public class Cliente extends Persona {
    private String correo;
    private String direccion;

    // Constructor
    public Cliente(int id_persona, String nombre_p, String paterno_p, String materno_p, String telefono, String f_nacimiento) {
        super(id_persona, nombre_p, paterno_p, materno_p, telefono, f_nacimiento, "cliente");
        this.correo = correo;
        this.direccion = direccion;
    }

    public Cliente(int id_persona, String nombre_p, String paterno_p, String materno_p, String telefono, String f_nacimiento, String correo, String direccion) {
        super(id_persona, nombre_p, paterno_p, materno_p, telefono, f_nacimiento, "cliente");
        this.correo = correo;
        this.direccion = direccion;
    }
    
    
    // Getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}