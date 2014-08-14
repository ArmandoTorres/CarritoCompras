package com.carritocompras.managedbean;

import java.util.Map;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Fotos;
import org.primefaces.model.UploadedFile;
import com.carritocompras.dao.impl.FotosDao;
import org.primefaces.event.FileUploadEvent;
import com.carritocompras.entities.Productos;
import com.carritocompras.dao.impl.ProductosDao;

/**
 * ManagedBean utilizado para controlar las acciones realizadas en la pagina de
 * AgregarImagenesProducto.xhtml
 * @author Armando
 * @version 1.0
 */
@ManagedBean(name="addImage")
@ViewScoped
public class AgregarImagenesProductoMB implements Serializable {
    
    //Objeto producto al cual se le van a agregar las fotos.
    private Productos producto;
    
    //Implementacion del dao con las funciones a realizar sobre el producto.
    private transient ProductosDao productosDao;
    
    //Implementacion del dao con las funciones a realizar sobre las fotos.
    private FotosDao fotosDao;

    public Productos getProducto() {
        return producto;
    }
    
    /**
     * Metodo que inicializa los objetos requeridos por la pagina. Tambien se encarga
     * de buscar en la BD el producto por el ID suministrado en el request.
     */
    @PostConstruct
    public void init(){
        productosDao = new ProductosDao();
        fotosDao = new FotosDao();
        try {
            producto = productosDao.findById(Integer.parseInt((String)JSFUtil.getParameterFromRequestMap("SelectedProducto")));
        } catch (Exception e) {
        }
    }
    
    /**
     * Metodo que funciona como listener que se mantiene escuchando las peticiones
     * que realiza el tag de <p:fileUpload/> este se encarga de cargar las fotos
     * al servidor.
     * @param event - evento que contiene el archivo a cargar.
     */
    public void handleFileUpload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        if (file != null){
            try {
                //Se procede a guardar el archivo en el sistema.
                Map<String, String> archivo = JSFUtil.saveFile(file);
                //Se crea el registro del archivo en la BD.
                Fotos foto = new Fotos(producto, archivo.get("archivo"),archivo.get("ruta"));
                //Se inserta el registro.
                fotosDao.insert(foto);
            } catch (IOException ex) {
                Logger.getLogger(AgregarImagenesProductoMB.class.getName()).log(Level.SEVERE, null, ex);
                JSFUtil.showErrorMessage("Error al grabar el archivo", ex.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(AgregarImagenesProductoMB.class.getName()).log(Level.SEVERE, null, ex);
                JSFUtil.showErrorMessage("Error al grabar la foto en la BD", ex.getMessage());
            }
        }
    }
}