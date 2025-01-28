package Clases;

public class Categoria {
    private int id_categoria;
    private String nombre_c;
    private String descripcion_c;

    //Constructor
    public Categoria(int id_categoria, String nombre_c, String descripcion_c) {
        this.id_categoria = id_categoria;
        this.nombre_c = nombre_c;
        this.descripcion_c = descripcion_c;
    }

    //Getters
    public int getId_categoria() {
        return id_categoria;
    }

    public String getNombre_c() {
        return nombre_c;
    }

    public String getDescripcion_c() {
        return descripcion_c;
    }

    //Setters
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public void setNombre_c(String nombre_c) {
        this.nombre_c = nombre_c;
    }

    public void setDescripcion_c(String descripcion_c) {
        this.descripcion_c = descripcion_c;
    }

    @Override
    public String toString() {
        return nombre_c; // This will be displayed in the ComboBox
    }
    
}
