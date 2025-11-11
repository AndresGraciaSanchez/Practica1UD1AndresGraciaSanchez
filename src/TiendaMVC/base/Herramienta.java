package TiendaMVC.base;

import java.time.LocalDate;

public class Herramienta extends Producto{
    private String tipoHerramienta;
    private String compatibilidad;

    public Herramienta(){

    }

    public Herramienta(String nombre, String id, String descripcion, double precio, String tipoLicencia, String categoria, int stock, LocalDate fechaPublicacion, String version, String repositorio, String tipoHerramienta, String compatibilidad) {
        super(nombre, id, descripcion, precio, tipoLicencia, categoria, stock, fechaPublicacion, version, repositorio);
        this.tipoHerramienta = tipoHerramienta;
        this.compatibilidad = compatibilidad;
    }

    public String getTipoHerramienta() {
        return tipoHerramienta;
    }

    public void setTipoHerramienta(String tipoHerramienta) {
        this.tipoHerramienta = tipoHerramienta;
    }

    public String getCompatibilidad() {
        return compatibilidad;
    }

    public void setCompatibilidad(String compatibilidad) {
        this.compatibilidad = compatibilidad;
    }

    @Override
    public String toString() {
        return "Herramienta{" +
                "tipoHerramienta='" + tipoHerramienta + '\'' +
                ", compatibilidad='" + compatibilidad + '\'' +
                '}';
    }
}
