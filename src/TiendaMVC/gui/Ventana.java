package TiendaMVC.gui;

import TiendaMVC.base.Producto;

import javax.swing.*;

public class Ventana {
    private JPanel panel1;
    private JRadioButton softwareRadioButton;
    private JRadioButton herramientaRadioButton;

    //Creados por mi
    public JFrame frame;
    public DefaultListModel<Producto> dlmProducto;

    public Ventana() {
        frame = new JFrame("Tienda software y herramientas");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents(){
        dlmProducto = new DefaultListModel<Producto>();
    }
}
