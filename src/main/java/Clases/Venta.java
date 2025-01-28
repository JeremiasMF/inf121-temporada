package Clases;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id_venta;
    private Empleado empleado;
    private Cliente cliente;
    private List<Producto> productos;
    private double total;

    //Constructor
    public Venta(int id_venta, Empleado empleado, Cliente cliente, List<Producto> productos, double total) {
        this.id_venta = id_venta;
        this.empleado = empleado;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.total = total;
    }

    //Getters
    public int getId_venta() {
        return id_venta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }

    //Setters
    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void setTotal(double total) {
        this.total = total;
    }
      
}
