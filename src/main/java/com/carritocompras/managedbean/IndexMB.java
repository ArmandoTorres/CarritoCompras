package com.carritocompras.managedbean;

import java.util.List;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Fotos;
import com.carritocompras.entities.Productos;
import com.carritocompras.dao.impl.ProductosDao;

/**
 * Managed bean creado para lidiar con los objetos de la pagina principal
 * o index.xhtml
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class IndexMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**Implementacion del Dao de productos que provee de los metodos necesarios
     * Para acceder a la tabla de productos.*/
    private ProductosDao productosDao;
    
    /**Lista de productos para mostrar en el index*/
    private List<Productos> listaProductos;
    
    /**Metodo que provee al index de la lista de productos
     * @return Lista de producto a mostrar.*/
    public List<Productos> getListaProductos() {
        return listaProductos;
    }
    
    /**Metodo que se inicia luego de que el contenedor haya creado la instancia
     * del managedBean, este se encarga de obtener la instancia del Dao de productos
     * y de cargar los productos existentes en la lista para mostrar en pantalla.
     */
    @PostConstruct
    public void init () {
        productosDao = new ProductosDao();
        try {
            //Se llena la lista con los producto que tengan stock > 0;
            listaProductos = productosDao.findAllWithStock();
        } catch (javax.persistence.NoResultException e) {
            JSFUtil.showFatalMessage("Error", "No se han encontrado productos para mostrar.");
        } catch (Exception ex) {
            JSFUtil.showFatalMessage("Error", "Ha ocurrido un error Fatal.");
            Logger.getLogger(IndexMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    public void close(){
        productosDao = null;
        listaProductos.clear();
    }
    
    public String getOneFoto(Productos p) {
        try {
            Fotos foto = p.getFotos().get(0);
            return JSFUtil.getInitParam("IMAGE_SHOW_PATH")+foto.getRuta()+"/"+foto.getFoto();
        } catch (IndexOutOfBoundsException ex){
            return "/resources/images/imageNotFound.jpg";
        }
    }
}