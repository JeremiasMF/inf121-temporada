package Clases;

public class Producto {
    private int id_producto;
    private String nombre_prod;
    private double precio;
    private int cantidad;
    private Categoria categoria;

    //Constructor
    public Producto(int id_producto, String nombre_prod, double precio, int cantidad, Categoria categoria) {
        this.id_producto = id_producto;
        this.nombre_prod = nombre_prod;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    //Getters
    public int getId_producto() {
        return id_producto;
    }

    public String getNombre_prod() {
        return nombre_prod;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    //Setters
    public void setId(int id_producto) {
        this.id_producto = id_producto;
    }

    public void setNombre_prod(String nombre_prod) {
        this.nombre_prod = nombre_prod;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
