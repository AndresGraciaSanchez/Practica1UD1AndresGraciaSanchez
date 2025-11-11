package TiendaMVC.base;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private int id;
    private Cliente cliente;
    private List<Producto> productos;
    private LocalDate fecha;
    private double total;

    public Venta(){

    }

    public Venta(int id, Cliente cliente, List<Producto> productos, LocalDate fecha, double total) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", productos=" + productos +
                ", fecha=" + fecha +
                ", total=" + total +
                '}';
    }
}
