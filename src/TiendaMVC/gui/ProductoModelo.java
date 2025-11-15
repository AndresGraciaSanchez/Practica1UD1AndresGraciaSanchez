package TiendaMVC.gui;

import TiendaMVC.base.Producto;
import TiendaMVC.base.Software;
import TiendaMVC.base.Herramienta;

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

    public ProductoModelo(){
        listaProductos = new ArrayList<>();
    }

    public ArrayList<Producto> obtenerProductos(){
        return listaProductos;
    }

    public void altaSoftware(String nombre, String id, String descripcion, double precio,
                             String tipoLicencia, String categoria, int stock,
                             LocalDate fechaPublicacion, String version, String repositorio,
                             String sistemaOperativo, String requerimientos){

        Software nuevo = new Software(nombre,id,descripcion,precio,tipoLicencia,categoria,
                stock,fechaPublicacion,version,repositorio,sistemaOperativo,requerimientos);

        listaProductos.add(nuevo);
    }

    public void altaHerramienta(String nombre, String id, String descripcion, double precio,
                                String tipoLicencia, String categoria, int stock,
                                LocalDate fechaPublicacion, String version, String repositorio,
                                String tipoHerramienta, String compatibilidad){

        Herramienta nuevo = new Herramienta(nombre,id,descripcion,precio,tipoLicencia,categoria,
                stock,fechaPublicacion,version,repositorio,tipoHerramienta,compatibilidad);

        listaProductos.add(nuevo);
    }

    public boolean existeId(String id){
        for (Producto p : listaProductos){
            if (p.getId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {

        listaProductos = new ArrayList<>();

        Software s = null;
        Herramienta h = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fichero);

        NodeList listaElementos = document.getElementsByTagName("*");

        for (int i = 0; i < listaElementos.getLength(); i++){

            Element nodo = (Element) listaElementos.item(i);

            if (nodo.getTagName().equals("Software")){
                s = new Software();

                s.setNombre(nodo.getChildNodes().item(0).getTextContent());
                s.setId(nodo.getChildNodes().item(1).getTextContent());
                s.setDescripcion(nodo.getChildNodes().item(2).getTextContent());
                s.setPrecio(Double.parseDouble(nodo.getChildNodes().item(3).getTextContent()));
                s.setTipoLicencia(nodo.getChildNodes().item(4).getTextContent());
                s.setCategoria(nodo.getChildNodes().item(5).getTextContent());
                s.setStock(Integer.parseInt(nodo.getChildNodes().item(6).getTextContent()));
                s.setFechaPublicacion(LocalDate.parse(nodo.getChildNodes().item(7).getTextContent()));
                s.setVersion(nodo.getChildNodes().item(8).getTextContent());
                s.setRepositorio(nodo.getChildNodes().item(9).getTextContent());
                s.setSistemaOperativo(nodo.getChildNodes().item(10).getTextContent());
                s.setRequerimientos(nodo.getChildNodes().item(11).getTextContent());

                listaProductos.add(s);
            }

            else if (nodo.getTagName().equals("Herramienta")){
                h = new Herramienta();

                h.setNombre(nodo.getChildNodes().item(0).getTextContent());
                h.setId(nodo.getChildNodes().item(1).getTextContent());
                h.setDescripcion(nodo.getChildNodes().item(2).getTextContent());
                h.setPrecio(Double.parseDouble(nodo.getChildNodes().item(3).getTextContent()));
                h.setTipoLicencia(nodo.getChildNodes().item(4).getTextContent());
                h.setCategoria(nodo.getChildNodes().item(5).getTextContent());
                h.setStock(Integer.parseInt(nodo.getChildNodes().item(6).getTextContent()));
                h.setFechaPublicacion(LocalDate.parse(nodo.getChildNodes().item(7).getTextContent()));
                h.setVersion(nodo.getChildNodes().item(8).getTextContent());
                h.setRepositorio(nodo.getChildNodes().item(9).getTextContent());
                h.setTipoHerramienta(nodo.getChildNodes().item(10).getTextContent());
                h.setCompatibilidad(nodo.getChildNodes().item(11).getTextContent());

                listaProductos.add(h);
            }
        }
    }

    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();

        Document document = dom.createDocument(null,"Productos",null);
        Element raiz = document.getDocumentElement();

        Element nodoProducto = null;
        Element nodoDatos = null;
        Text texto = null;

        for (Producto p : listaProductos){

            if (p instanceof Software){
                nodoProducto = document.createElement("Software");
            }
            else{
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

            if (p instanceof Software){
                Software s = (Software) p;

                nodoDatos = document.createElement("sistemaOperativo");
                nodoProducto.appendChild(nodoDatos);
                texto = document.createTextNode(s.getSistemaOperativo());
                nodoDatos.appendChild(texto);

                nodoDatos = document.createElement("requerimientos");
                nodoProducto.appendChild(nodoDatos);
                texto = document.createTextNode(s.getRequerimientos());
                nodoDatos.appendChild(texto);
            }
            else{
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


            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(fichero));
        }
    }
}
