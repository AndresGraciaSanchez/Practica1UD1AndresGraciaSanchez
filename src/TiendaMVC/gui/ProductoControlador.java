package TiendaMVC.gui;

import TiendaMVC.base.Producto;
import TiendaMVC.base.Software;
import TiendaMVC.base.Herramienta;
import TiendaMVC.util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;

import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class ProductoControlador implements ActionListener, ListSelectionListener, WindowListener {

    private Ventana vista;
    private ProductoModelo modelo;
    private File ultimaRutaExportada;

    public ProductoControlador(Ventana vista, ProductoModelo modelo){
        this.vista=vista;
        this.modelo=modelo;

        try {
            cargarDatoConfiguracion();
        } catch (IOException e) {
            System.out.println("No existe el fichero de configuracion "+e.getMessage());
        }

        addActionListener(this);
        addWindowListener(this);
        addListSelectionListener(this);
    }

    private void addActionListener(ActionListener listener){
        vista.nuevoButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.importarButton.addActionListener(listener);
        vista.softwareRadioButton.addActionListener(listener);
        vista.herramientaRadioButton.addActionListener(listener);
    }

    private void addWindowListener(WindowListener listener){
        vista.frame.addWindowListener(listener);
    }

    private void addListSelectionListener(ListSelectionListener listener){
        vista.list1.addListSelectionListener(listener);
    }

    private boolean hayCamposVacios(){
        return vista.textFieldNombre.getText().isEmpty()
                || vista.textFieldId.getText().isEmpty()
                || vista.textAreaDescripcion.getText().isEmpty()
                || vista.textFieldPrecio.getText().isEmpty()
                || vista.textFieldTipoLicencia.getText().isEmpty()
                || vista.textFieldCategoria.getText().isEmpty()
                || vista.fechaPublicacion.getText().isEmpty()
                || vista.textFieldVersion.getText().isEmpty()
                || vista.textFieldRepositorio.getText().isEmpty();
    }

    private void limpiarCampos(){
        vista.textFieldNombre.setText(null);
        vista.textFieldId.setText(null);
        vista.textAreaDescripcion.setText(null);
        vista.textFieldPrecio.setText(null);
        vista.textFieldTipoLicencia.setText(null);
        vista.textFieldCategoria.setText(null);
        vista.spinnerStock.setValue(0);
        vista.fechaPublicacion.setText(null);
        vista.textFieldVersion.setText(null);
        vista.textFieldRepositorio.setText(null);
        vista.textFieldSistemaOperativo.setText(null);
        vista.textFieldRequerimientos.setText(null);
    }

    private void refrescar(){
        vista.dlmProducto.clear();
        for(Producto p : modelo.obtenerProductos()){
            vista.dlmProducto.addElement(p);
        }
    }

    private void cargarDatoConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.load(new FileReader("producto.conf"));
        ultimaRutaExportada = new File(configuracion.getProperty("ultimaRutaExportada"));
    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada){
        this.ultimaRutaExportada = ultimaRutaExportada;
    }

    private void guardarConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultimaRutaExportada", ultimaRutaExportada.getAbsolutePath());
        configuracion.store(new PrintWriter("producto.conf"),"Datos configuración productos");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String ac = e.getActionCommand();

        switch(ac){

            case "Software":
                vista.sistemaOperativoLabel.setText("Sistema operativo");
                vista.requerimientosLabel.setText("Requerimientos");
                break;

            case "Herramienta":
                vista.sistemaOperativoLabel.setText("Tipo de herramienta");
                vista.requerimientosLabel.setText("Compatibilidad");
                break;

            case "Nuevo":
                if (hayCamposVacios()){
                    Util.mensajeError("Existen campos vacíos.");
                    break;
                }

                if (modelo.existeId(vista.textFieldId.getText())){
                    Util.mensajeError("Ya existe un producto con este ID.");
                    break;
                }

                if (vista.softwareRadioButton.isSelected()){
                    modelo.altaSoftware(
                            vista.textFieldNombre.getText(),
                            vista.textFieldId.getText(),
                            vista.textAreaDescripcion.getText(),
                            Double.parseDouble(vista.textFieldPrecio.getText()),
                            vista.textFieldTipoLicencia.getText(),
                            vista.textFieldCategoria.getText(),
                            (int)vista.spinnerStock.getValue(),
                            vista.fechaPublicacion.getDate(),
                            vista.textFieldVersion.getText(),
                            vista.textFieldRepositorio.getText(),
                            vista.textFieldSistemaOperativo.getText(),
                            vista.textFieldRequerimientos.getText()
                    );
                }
                else{
                    modelo.altaHerramienta(
                            vista.textFieldNombre.getText(),
                            vista.textFieldId.getText(),
                            vista.textAreaDescripcion.getText(),
                            Double.parseDouble(vista.textFieldPrecio.getText()),
                            vista.textFieldTipoLicencia.getText(),
                            vista.textFieldCategoria.getText(),
                            (int)vista.spinnerStock.getValue(),
                            vista.fechaPublicacion.getDate(),
                            vista.textFieldVersion.getText(),
                            vista.textFieldRepositorio.getText(),
                            vista.textFieldSistemaOperativo.getText(),
                            vista.textFieldRequerimientos.getText()
                    );
                }

                limpiarCampos();
                refrescar();
                break;

            case "Importar":
                JFileChooser selector1 = Util.crearSelectorFichero(ultimaRutaExportada,"Archivos XML","xml");
                int opt1 = selector1.showOpenDialog(null);

                if (opt1 == JFileChooser.APPROVE_OPTION){
                    try {
                        modelo.importarXML(selector1.getSelectedFile());
                        refrescar();
                    } catch (ParserConfigurationException | IOException | SAXException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case "Exportar":
                JFileChooser selector2 = Util.crearSelectorFichero(ultimaRutaExportada,"Archivos XML","xml");
                int opt2 = selector2.showSaveDialog(null);

                if (opt2 == JFileChooser.APPROVE_OPTION){
                    try {
                        modelo.exportarXML(selector2.getSelectedFile());
                        actualizarDatosConfiguracion(selector2.getSelectedFile());
                        guardarConfiguracion();
                        refrescar();
                    } catch (ParserConfigurationException | TransformerException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (e.getValueIsAdjusting()) return;

        Producto p = (Producto) vista.list1.getSelectedValue();

        if (p == null) return;

        vista.textFieldNombre.setText(p.getNombre());
        vista.textFieldId.setText(p.getId());
        vista.textAreaDescripcion.setText(p.getDescripcion());
        vista.textFieldPrecio.setText(String.valueOf(p.getPrecio()));
        vista.textFieldTipoLicencia.setText(p.getTipoLicencia());
        vista.textFieldCategoria.setText(p.getCategoria());
        vista.spinnerStock.setValue(p.getStock());
        vista.fechaPublicacion.setDate(p.getFechaPublicacion());
        vista.textFieldVersion.setText(p.getVersion());
        vista.textFieldRepositorio.setText(p.getRepositorio());

        if (p instanceof Software){
            Software s = (Software) p;

            vista.softwareRadioButton.doClick();
            vista.textFieldSistemaOperativo.setText(s.getSistemaOperativo());
            vista.textFieldRequerimientos.setText(s.getRequerimientos());
        }
        else{
            Herramienta h = (Herramienta) p;

            vista.herramientaRadioButton.doClick();
            vista.textFieldSistemaOperativo.setText(h.getTipoHerramienta());
            vista.textFieldRequerimientos.setText(h.getCompatibilidad());
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.mensajeConfirmacion("¿Quieres cerrar la ventana?", "Salir");

        if (resp == JOptionPane.OK_OPTION){
            try {
                guardarConfiguracion();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
