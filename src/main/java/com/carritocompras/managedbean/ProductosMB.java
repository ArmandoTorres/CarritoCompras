package com.carritocompras.managedbean;

import java.util.List;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Productos;
import com.carritocompras.dao.impl.ProductosDao;

/**
 * ManagedBean encargado de manejar las acciones en la pagina de Productos.xhtml
 * @author Armando
 */
@ManagedBean
@ViewScoped
public class ProductosMB implements Serializable {
    
    //Dao de productos
    private transient ProductosDao productoDao;
    
    //Listado de productos en pantalla.
    private List<Productos> listaProductos;
    
    private Productos producto;

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
    
    public List<Productos> getListaProductos() {
        return listaProductos;
    }

    /**
     * Metodo usado para inicializar los objetos del manejador de productos 
     * y llenar la lista con los productos contenidos en la BD.
     */
    @PostConstruct
    public void init(){
        productoDao = new ProductosDao();
        listaProductos = productoDao.findAll();
    }
    
    /**
     * Metodo que se encarga de sumar 1 a la cantidad del producto indicado.
     * @return String con la url en este caso vacia para refrescar la pagina.
     * @param pro : producto que sera actualizado.
     */
    public String agregar(Productos pro){
        int cant = pro.getCantidad();
        pro.setCantidad(++cant);
        try {
            productoDao.update(pro);
        } catch (Exception ex) {
            Logger.getLogger(ProductosMB.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.showErrorMessage("Ocurrio un error!", "Ha ocurrido un error al tratar de actualiza el producto.");
        }
        JSFUtil.showInfoMessage("Producto Actualizado", "Producto actualizado correctamente.");
        return null;        
    }
    
}