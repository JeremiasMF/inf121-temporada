package Clases;

public class Empleado extends Persona {
    private String puesto;
    private double salario;

    // Constructor
    public Empleado(int id_persona, String nombre_p, String paterno_p, String materno_p, String telefono, String f_nacimiento) {
        super(id_persona, nombre_p, paterno_p, materno_p, telefono, f_nacimiento, "empleado");
        this.puesto = puesto;
        this.salario = salario;
    }
    
    public Empleado(int id_persona, String nombre_p, String paterno_p, String materno_p, String telefono, String f_nacimiento, String puesto, Double salario) {
        super(id_persona, nombre_p, paterno_p, materno_p, telefono, f_nacimiento, "empleado");
        this.puesto = puesto;
        this.salario = salario;
    }
    

    
    
    // Getters y setters
    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}