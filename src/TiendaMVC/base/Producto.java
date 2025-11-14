package TiendaMVC.base;

import java.time.LocalDate;

public class Producto {
    private String nombre;
    private String id;
    private String descripcion;
    private double precio;
    private String tipoLicencia;
    private String categoria;
    private int stock;
    private LocalDate fechaPublicacion;
    private String version;
    private String repositorio;

    public Producto(){

    }

    public Producto(String nombre, String id, String descripcion, double precio, String tipoLicencia, String categoria, int stock, LocalDate fechaPublicacion, String version, String repositorio) {
        this.nombre = nombre;
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoLicencia = tipoLicencia;
        this.categoria = categoria;
        this.stock = stock;
        this.fechaPublicacion = fechaPublicacion;
        this.version = version;
        this.repositorio = repositorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }
}
