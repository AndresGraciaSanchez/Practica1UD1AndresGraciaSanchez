package TiendaMVC.gui;

import TiendaMVC.base.Herramienta;
import TiendaMVC.base.Producto;
import TiendaMVC.base.Software;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductoModelo {
    private ArrayList<Producto> listaProductos;
    private int ultimoId = 0;

    public ProductoModelo() {
        listaProductos = new ArrayList<>();
    }

    public ArrayList<Producto> obtenerProductos() {
        return listaProductos;
    }

    private String generarId() {
        ultimoId++;
        return String.valueOf(ultimoId);
    }

    public void altaSoftware(String nombre, String descripcion, double precio, String tipoLicencia, String categoria, int stock,
                             LocalDate fechaPublicacion, String version, String repositorio,
                             String sistemaOperativo, String requerimientos) {
        Software s = new Software(nombre, generarId(), descripcion, precio, tipoLicencia,
                categoria, stock, fechaPublicacion, version, repositorio, sistemaOperativo, requerimientos);
        listaProductos.add(s);
    }

    public void altaHerramienta(String nombre, String descripcion, double precio, String tipoLicencia, String categoria, int stock,
                                LocalDate fechaPublicacion, String version, String repositorio,
                                String tipoHerramienta, String compatibilidad) {
        Herramienta h = new Herramienta(nombre, generarId(), descripcion, precio, tipoLicencia,
                categoria, stock, fechaPublicacion, version, repositorio, tipoHerramienta, compatibilidad);
        listaProductos.add(h);
    }

    public boolean existeProducto(String id){
        for (Producto unProducto: listaProductos){
            if (unProducto.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    //XML

    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {

        listaProductos = new ArrayList<Producto>();
        ultimoId = 0;

        Software s = null;
        Herramienta h = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fichero);

        NodeList listaElementos = document.getElementsByTagName("*");

        for (int i = 0; i < listaElementos.getLength(); i++) {

            Element nodoProducto = (Element) listaElementos.item(i);


            if (nodoProducto.getTagName().equals("Software")) {

                s = new Software();

                s.setNombre(nodoProducto.getChildNodes().item(0).getTextContent());
                s.setId(nodoProducto.getChildNodes().item(1).getTextContent());
                s.setDescripcion(nodoProducto.getChildNodes().item(2).getTextContent());
                s.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(3).getTextContent()));
                s.setCategoria(nodoProducto.getChildNodes().item(4).getTextContent());
                s.setStock(Integer.parseInt(nodoProducto.getChildNodes().item(5).getTextContent()));
                s.setFechaPublicacion(LocalDate.parse(nodoProducto.getChildNodes().item(6).getTextContent()));
                s.setVersion(nodoProducto.getChildNodes().item(7).getTextContent());
                s.setRepositorio(nodoProducto.getChildNodes().item(8).getTextContent());
                s.setSistemaOperativo(nodoProducto.getChildNodes().item(9).getTextContent());
                s.setRequerimientos(nodoProducto.getChildNodes().item(10).getTextContent());

                listaProductos.add(s);
            }


            else if (nodoProducto.getTagName().equals("Herramienta")) {

                h = new Herramienta();

                h.setNombre(nodoProducto.getChildNodes().item(0).getTextContent());
                h.setId(nodoProducto.getChildNodes().item(1).getTextContent());
                h.setDescripcion(nodoProducto.getChildNodes().item(2).getTextContent());
                h.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(3).getTextContent()));
                h.setCategoria(nodoProducto.getChildNodes().item(4).getTextContent());
                h.setStock(Integer.parseInt(nodoProducto.getChildNodes().item(5).getTextContent()));
                h.setFechaPublicacion(LocalDate.parse(nodoProducto.getChildNodes().item(6).getTextContent()));
                h.setVersion(nodoProducto.getChildNodes().item(7).getTextContent());
                h.setRepositorio(nodoProducto.getChildNodes().item(8).getTextContent());
                h.setTipoHerramienta(nodoProducto.getChildNodes().item(9).getTextContent());
                h.setCompatibilidad(nodoProducto.getChildNodes().item(10).getTextContent());

                listaProductos.add(h);
            }
        }


        ultimoId = listaProductos.size();
    }



    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        Document document = dom.createDocument(null, "xml", null);
        // Nodo raíz
        Element raiz = document.createElement("Producto");
        document.getDocumentElement().appendChild(raiz);

        Element nodoProducto=null;
        Element nodoDatos=null;
        Text texto=null;

        for (Producto p : listaProductos) {
            if (p instanceof Software) {
                nodoProducto = document.createElement("Software");
            } else {
                nodoProducto = document.createElement("Herramienta");
            }
            raiz.appendChild(nodoProducto);

            nodoDatos = document.createElement("nombre");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getNombre());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("id");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getId());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("descripcion");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getDescripcion());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("precio");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(String.valueOf(p.getPrecio()));
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("tipoLicencia");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getTipoLicencia());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("categoria");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getCategoria());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("stock");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(String.valueOf(p.getStock()));
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("fechaPublicacion");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getFechaPublicacion().toString());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("version");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getVersion());
            nodoDatos.appendChild(texto);

            nodoDatos = document.createElement("repositorio");
            nodoProducto.appendChild(nodoDatos);

            texto = document.createTextNode(p.getRepositorio());
            nodoDatos.appendChild(texto);


            if (p instanceof Software) {
                Software s = (Software) p;

                nodoDatos = document.createElement("sistemaOperativo");
                nodoProducto.appendChild(nodoDatos);
                texto = document.createTextNode(s.getSistemaOperativo());
                nodoDatos.appendChild(texto);

                nodoDatos = document.createElement("requerimientos");
                nodoProducto.appendChild(nodoDatos);
                texto = document.createTextNode(s.getRequerimientos());
                nodoDatos.appendChild(texto);
            } else if (p instanceof Herramienta) {
                Herramienta h = (Herramienta) p;

                nodoDatos = document.createElement("tipoHerramienta");
                nodoProducto.appendChild(nodoDatos);
                texto = document.createTextNode(h.getTipoHerramienta());
                nodoDatos.appendChild(texto);

                nodoDatos = document.createElement("compatibilidad");
                nodoProducto.appendChild(nodoDatos);
                texto = document.createTextNode(h.getCompatibilidad());
                nodoDatos.appendChild(texto);
            }
        }


        Source source = new DOMSource(document);
        Result result = new StreamResult(fichero);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.transform(source, result);
    }

}
