package Clases;

import java.util.ArrayList;
import java.util.List;

public class Tienda {

    private int id_tienda;
    private String nombre_t;
    private String direccion_t;
    private List<Empleado> empleados;
    private List<Cliente> clientes;
    private List<Producto> productos;
    private List<Venta> ventas;
    private List<Factura> facturas;

    //Constructor
    public Tienda(int id_tienda, String nombre_t, String direccion_t, List<Empleado> empleados, List<Cliente> clientes, List<Producto> productos, List<Venta> ventas, List<Factura> facturas) {
        this.id_tienda = id_tienda;
        this.nombre_t = nombre_t;
        this.direccion_t = direccion_t;
        this.empleados = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.facturas = new ArrayList<>();
    }

    //Getter
    public int getId_tienda() {
        return id_tienda;
    }

    public String getNombre_t() {
        return nombre_t;
    }

    public String getDireccion_t() {
        return direccion_t;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    //Setter
    public void setId_tienda(int id_tienda) {
        this.id_tienda = id_tienda;
    }

    public void setNombre_t(String nombre_t) {
        this.nombre_t = nombre_t;
    }

    public void setDireccion_t(String direccion_t) {
        this.direccion_t = direccion_t;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

}
