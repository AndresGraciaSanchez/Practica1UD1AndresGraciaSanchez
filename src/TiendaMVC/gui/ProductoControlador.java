package TiendaMVC.gui;

import TiendaMVC.base.Herramienta;
import TiendaMVC.base.Producto;
import TiendaMVC.base.Software;
import TiendaMVC.util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class ProductoControlador implements ActionListener, ListSelectionListener, WindowListener {

    private Ventana vista;
    private ProductoModelo modelo;
    private File ultimaRutaExportada;

    public ProductoControlador(Ventana vista, ProductoModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        try {
            cargarConfiguracion();
        } catch (IOException e) {
            System.out.println("No existe el fichero de configuración: " + e.getMessage());
        }

        addActionListeners(this);
        addWindowListener(this);
        addListSelectionListener(this);
    }

    private void addActionListeners(ActionListener listener) {
        vista.nuevoButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.importarButton.addActionListener(listener);
        vista.softwareRadioButton.addActionListener(listener);
        vista.herramientaRadioButton.addActionListener(listener);
    }

    private boolean hayCamposVacios() {
        // Campos comunes de Producto
        if (vista.textFieldNombre.getText().isEmpty()
                || vista.textFieldDescripcion.getText().isEmpty()
                || vista.textFieldPrecio.getText().isEmpty()
                || vista.textFieldCategoria.getText().isEmpty()
                || vista.textFieldVersion.getText().isEmpty()
                || vista.textFieldRepositorio.getText().isEmpty()
                || vista.textFieldTipoLicencia.getText().isEmpty()
                || vista.fechaPublicacion.getText().isEmpty()
                || ((Integer) vista.spinnerStock.getValue()) <= 0) {
            return true;
        }

        // Campos específicos según tipo de producto
        if (vista.softwareRadioButton.isSelected()) {
            if (vista.textFieldSistemaOperativo.getText().isEmpty()
                    || vista.textFieldRequerimientos.getText().isEmpty()) {
                return true;
            }
        } else if (vista.herramientaRadioButton.isSelected()) {
            if (vista.textFieldSistemaOperativo.getText().isEmpty()  // Aquí se usan los mismos campos pero para Herramienta
                    || vista.textFieldRequerimientos.getText().isEmpty()) {
                return true;
            }
        }

        return false;
    }




    private void addListSelectionListener(ListSelectionListener listener) {
        vista.list1.addListSelectionListener(listener);
    }

    private void addWindowListener(WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    private void limpiarCampos() {
        vista.textFieldNombre.setText("");
        vista.textFieldDescripcion.setText("");
        vista.textFieldPrecio.setText("");
        vista.textFieldCategoria.setText("");
        vista.spinnerStock.setValue(0);
        vista.textFieldVersion.setText("");
        vista.textFieldRepositorio.setText("");
        vista.textFieldSistemaOperativo.setText("");
        vista.textFieldRequerimientos.setText("");
        vista.fechaPublicacion.clear();
    }

    private void refrescar() {
        vista.dlmProducto.clear();
        for (Producto p : modelo.obtenerProductos()) {
            vista.dlmProducto.addElement(p);
        }
    }

    private void cargarConfiguracion() throws IOException {
        Properties config = new Properties();
        config.load(new FileReader("producto.conf"));
        ultimaRutaExportada = new File(config.getProperty("ultimaRutaExportada"));
    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada){
        this.ultimaRutaExportada = ultimaRutaExportada;
    }

    private void guardarConfiguracion() throws IOException {
        Properties config = new Properties();
        config.setProperty("ultimaRutaExportada", ultimaRutaExportada.getAbsolutePath());
        config.store(new PrintWriter("producto.conf"), "Configuración de productos");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Nuevo":
                if (hayCamposVacios()) {
                    Util.mensajeError("Los siguientes campos no pueden estar vacíos:\n"
                            + "Nombre\nDescripción\nPrecio\nCategoría\nVersión\nRepositorio\nTipo de Licencia\nFecha de Publicación\nStock\n"
                            + (vista.softwareRadioButton.isSelected() ? "Sistema Operativo\nRequerimientos" : "Tipo de Herramienta\nCompatibilidad"));
                    break;
                }

                if (modelo.existeProducto(vista.textFieldNombre.getText())) {
                    Util.mensajeError("Ya existe un producto con este nombre: " + vista.textFieldNombre.getText());
                    break;
                }

                if (vista.softwareRadioButton.isSelected()) {
                    modelo.altaSoftware(
                            vista.textFieldNombre.getText(),
                            vista.textFieldDescripcion.getText(),
                            Double.parseDouble(vista.textFieldPrecio.getText()),
                            vista.textFieldTipoLicencia.getText(),
                            vista.textFieldCategoria.getText(),
                            (Integer) vista.spinnerStock.getValue(),
                            vista.fechaPublicacion.getDate(),
                            vista.textFieldVersion.getText(),
                            vista.textFieldRepositorio.getText(),
                            vista.textFieldSistemaOperativo.getText(),
                            vista.textFieldRequerimientos.getText()
                    );
                } else if (vista.herramientaRadioButton.isSelected()) {
                    modelo.altaHerramienta(
                            vista.textFieldNombre.getText(),
                            vista.textFieldDescripcion.getText(),
                            Double.parseDouble(vista.textFieldPrecio.getText()),
                            vista.textFieldTipoLicencia.getText(),
                            vista.textFieldCategoria.getText(),
                            (Integer) vista.spinnerStock.getValue(),
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
                JFileChooser selectorFichero = Util.crearSelectorFichero(ultimaRutaExportada, "Archivos XML", "xml");
                int opt = selectorFichero.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.importarXML(selectorFichero.getSelectedFile());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    refrescar();
                }
                break;

            case "Exportar":
                JFileChooser selectorFichero2 = Util.crearSelectorFichero(ultimaRutaExportada, "Archivos XML", "xml");
                int opt2 = selectorFichero2.showSaveDialog(null);
                if (opt2 == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.exportarXML(selectorFichero2.getSelectedFile());
                        actualizarDatosConfiguracion(selectorFichero2.getSelectedFile());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    refrescar();
                }
                break;

            case "Software":
                vista.textFieldSistemaOperativo.setToolTipText("Sistema Operativo del Software");
                vista.textFieldRequerimientos.setToolTipText("Requerimientos del Software");
                break;

            case "Herramienta":
                vista.textFieldSistemaOperativo.setToolTipText("Tipo de Herramienta");
                vista.textFieldRequerimientos.setToolTipText("Compatibilidad de la Herramienta");
                break;
        }
    }



    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Producto p = (Producto) vista.list1.getSelectedValue();
            if (p == null) return;

            vista.textFieldNombre.setText(p.getNombre());
            vista.textFieldDescripcion.setText(p.getDescripcion());
            vista.textFieldPrecio.setText(String.valueOf(p.getPrecio()));
            vista.textFieldCategoria.setText(p.getCategoria());
            vista.spinnerStock.setValue(p.getStock());
            vista.fechaPublicacion.setDate(p.getFechaPublicacion());
            vista.textFieldVersion.setText(p.getVersion());
            vista.textFieldRepositorio.setText(p.getRepositorio());

            if (p instanceof Software) {
                Software s = (Software) p;
                vista.softwareRadioButton.setSelected(true);
                vista.textFieldSistemaOperativo.setText(s.getSistemaOperativo());
                vista.textFieldRequerimientos.setText(s.getRequerimientos());
            } else if (p instanceof Herramienta) {
                Herramienta h = (Herramienta) p;
                vista.herramientaRadioButton.setSelected(true);
                vista.textFieldSistemaOperativo.setText(h.getTipoHerramienta());
                vista.textFieldRequerimientos.setText(h.getCompatibilidad());
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.mensajeConfirmacion("¿Quieres cerrar la aplicación?", "Salir");
        if (resp == JOptionPane.OK_OPTION) {
            try {
                guardarConfiguracion();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    // No usados
    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
