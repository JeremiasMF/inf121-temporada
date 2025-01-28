package Clases;

public class Factura {
    private int id_factura;
    private Venta venta;
    private String fecha;
    private double total;

    //Constructor
    public Factura(int id_factura, Venta venta, String fecha, double total) {
        this.id_factura = id_factura;
        this.venta = venta;
        this.fecha = fecha;
        this.total = total;
    }
    
    //Getters
    public int getId_factura() {
        return id_factura;
    }

    public Venta getVenta() {
        return venta;
    }

    public String getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }
    
    //Setters
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
