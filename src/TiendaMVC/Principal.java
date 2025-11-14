package TiendaMVC;

import TiendaMVC.gui.ProductoControlador;
import TiendaMVC.gui.ProductoModelo;
import TiendaMVC.gui.Ventana;

public class Principal {
    public static void main(String[] args) {
        Ventana vista = new Ventana();
        ProductoModelo modelo = new ProductoModelo();
        ProductoControlador controlador = new ProductoControlador(vista,modelo);
    }
}
