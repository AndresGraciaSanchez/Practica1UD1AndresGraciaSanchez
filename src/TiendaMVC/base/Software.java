package TiendaMVC.base;

import java.time.LocalDate;

public class Software extends Producto {
    private String sistemaOperativo;
    private String requerimientos;

    public Software(){

    }

    public Software(String nombre, String id, String descripcion, double precio, String tipoLicencia, String categoria, int stock, LocalDate fechaPublicacion, String version, String repositorio, String sistemaOperativo, String requerimientos) {
        super(nombre, id, descripcion, precio, tipoLicencia, categoria, stock, fechaPublicacion, version, repositorio);
        this.sistemaOperativo = sistemaOperativo;
        this.requerimientos = requerimientos;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }

    @Override
    public String toString() {
        return "Software{" +
                "sistemaOperativo='" + sistemaOperativo + '\'' +
                ", requerimientos='" + requerimientos + '\'' +
                '}';
    }
}
