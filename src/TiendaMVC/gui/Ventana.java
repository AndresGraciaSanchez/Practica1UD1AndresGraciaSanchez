package TiendaMVC.gui;

import TiendaMVC.base.Producto;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class Ventana {
    private JPanel panel1;
    public JRadioButton herramientaRadioButton;
    public JRadioButton softwareRadioButton;
    public JTextField textFieldNombre;
    public JTextField textFieldPrecio;
    public JTextField textFieldCategoria;
    public JSpinner spinnerStock;
    public JTextField textFieldVersion;
    public JTextField textFieldRepositorio;
    public JTextField textFieldDescripcion;
    public JTextField textFieldSistemaOperativo;
    public JTextField textFieldRequerimientos;
    public JButton nuevoButton;
    public JButton exportarButton;
    public JButton importarButton;
    public JList list1;
    public DatePicker fechaPublicacion;
    public JTextField textFieldTipoLicencia;
    public JTextField textFieldId;

    //Creados por mi
    public JFrame frame;
    public DefaultListModel<Producto> dlmProducto;

    public Ventana() {
        frame = new JFrame("Tienda software y herramientas");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents(){
        dlmProducto = new DefaultListModel<Producto>();
        list1.setModel(dlmProducto);
    }

}
