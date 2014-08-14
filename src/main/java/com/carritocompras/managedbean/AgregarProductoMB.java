package com.carritocompras.managedbean;

import java.util.Map;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import org.primefaces.model.UploadedFile;
import com.carritocompras.entities.Fotos;
import com.carritocompras.dao.impl.FotosDao;
import com.carritocompras.entities.Productos;
import com.carritocompras.dao.impl.ProductosDao;

/**
 * Managed bean encargado de manejar las acciones de la pagina de AgregarProductos.xhtml
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class AgregarProductoMB implements Serializable {
    
    //Implementacion del dao de productos.
    private transient ProductosDao productosDao;
    
    //Producto el cual se va agregar en la BD.
    private Productos producto;
    
    //Archivo que es una foto que sera guardado en el sistema.
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
    
    /**
     * Metodo que inicializa los objetos requeridos por la pagina.
     */
    @PostConstruct
    public void init(){
        productosDao = new ProductosDao();
        producto = new Productos();
    }
    
    /**
     * Metodo encargado de crear el producto y guardar la imagen seleccionada.
     * @param event - evento disparado por el actionListener.
     * @exception IOException posible exception lanzada por ulgun error a la hora de guardar el archivo.
     */
    public void guardar(ActionEvent event) throws IOException {
        try {
            //Si el archivo no esta null
            if (file != null) {
                //Se guarda la imagen y se recibe el nombre y la ruta donde se guardo.
                Map<String, String> archivo = JSFUtil.saveFile(file);
                //Se inserta el producto y se consige su id.
                producto = productosDao.insert(producto);
                //Se crea un registro de foto con el producto, nombre de archivo y ruta del mismo.
                Fotos foto = new Fotos(producto,archivo.get("archivo"),archivo.get("ruta"));
                //Se inserta la foto
                new FotosDao().insert(foto);
                //Se limpia la pantalla.
                producto = new Productos();
                JSFUtil.showInfoMessage("Producto guardado", "Producto creado correctamente.");            
            } else {
                JSFUtil.showWarmingMessage("", "Debe seleccionar una foto para el producto.");
            }
        } catch (IOException ex) {
            JSFUtil.showErrorMessage("Error en el archivo.", ex.toString());
        } catch (Exception e) {
            JSFUtil.showErrorMessage("Error", "Ocurrio un error al guardar el producto :" +e);
        }
    }
}