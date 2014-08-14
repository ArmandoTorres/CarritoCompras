package com.carritocompras.managedbean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Fotos;
import com.carritocompras.entities.Carrito;
import com.carritocompras.entities.Productos;
import com.carritocompras.dao.impl.CarritoDao;
import com.carritocompras.dao.impl.ProductosDao;

/**
 * Managed Bean encargado de manejar los eventos ocurridos en la pagina de DetalleProducto.xhtml
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class DetalleProductoMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**Objeto utilizado para realizar acciones sobre la tbl de carrito*/
    private transient CarritoDao carritoDao;
    
    /**Objeto utilizado para realizar acciones sobre la tbl de productos*/
    private transient ProductosDao productosDao;
    
    /**Objeto que representa el producto que actualmente esta en pantalla.*/
    private Productos producto;
    
    public Productos getProducto() {
        return producto;
    }
    
    /**
     * Metodo que se encarga de buscar el id del producto seleccionado dentro del
     * mapa de objeto del Request y luego buscar el producto en la BD para mostrarlo
     * en la pagina DetalleProducto.xhtml.
     */
    @PostConstruct
    public void init() {
        carritoDao = new CarritoDao();
        productosDao = new ProductosDao();
        //Se consigue el id del producto seleccionado
        try {
            int id = Integer.parseInt((String)JSFUtil.getParameterFromRequestMap("selectedProduct"));
            producto = productosDao.findById(id);
        } catch (java.lang.ClassCastException ex){
            JSFUtil.showFatalMessage("Error", "No se pudo conseguir el id del producto seleccionado.");
        } catch (javax.persistence.NoResultException exe){
            JSFUtil.showErrorMessage("Data not found", "No se encontro el producto especificado.");
        }
    }
    
    /**
     * Metodo encargado de agregar un producto al carrito del usuario actual.
     * Se verifica si el usuario esta logiando y si ya existe el producto en su carrito.
     * @param event : evento disparado por el action listener.
     */
    public void agregarAlCarrito(ActionEvent event) {
        if (JSFUtil.getCurrentUser() != null){
            if (!carritoDao.findDuplicity(JSFUtil.getCurrentUser(), producto)){
                //Se crea un nuevo objeto de carrito.
                Carrito c = new Carrito();
                c.setProducto(producto);
                //Se le setea el usuario actual.
                c.setUsuario(JSFUtil.getCurrentUser());
                try {
                    //Se crea el registro en la tbl
                    carritoDao.insert(c);
                    //Se actualiza el objeto CarritoItems en la session.
                    JSFUtil.addItemToSession("CarritoItems", "("+carritoDao.findAllByUser(JSFUtil.getCurrentUser()).size()+")");
                    JSFUtil.showInfoMessage("Producto Agregado!", "El producto ha sido agregado al carrito.");
                } catch (Exception ex) {
                    JSFUtil.showFatalMessage("Error!!!", "Ha ocurrido un error al agregar le producto al carrito: "+ex.getMessage());
                    Logger.getLogger(DetalleProductoMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JSFUtil.showInfoMessage("Producto Existente Carrito", "El producto seleccionado actualmente se encuentra agregado en su carrito.");
            }    
        } else {
            JSFUtil.showWarmingMessage("Mensaje!", "Debe de esta autenticado para añadir el producto a su carrito.");
        }
    }
    
    /**
     * Metodo utilizado para agregar el producto al carrito y luego redirigir al usuario 
     * a la vista de GenerarFactura.xhtml para que pueda proceder con la compra del producto.
     * @return URL a la cual sera redigirida la pagina.
     */
    public String comprarProducto(){
        //Pregunta si esta logiado
        if (JSFUtil.getCurrentUser() != null){
            //Pregunta si el item ya existe en el carrito.
            if (!carritoDao.findDuplicity(JSFUtil.getCurrentUser(), producto)){
                //Se crea un nuevo objeto de carrito.
                Carrito c = new Carrito();
                c.setProducto(producto);
                //Se le setea el usuario actual.
                c.setUsuario(JSFUtil.getCurrentUser());
                try {
                    //Se crea el registro en la tbl
                    carritoDao.insert(c);
                    //Se actualiza el objeto CarritoItems en la session.
                    JSFUtil.addItemToSession("CarritoItems", "("+carritoDao.findAllByUser(JSFUtil.getCurrentUser()).size()+")");
                    return "/pages/logged/GenerarFactura?faces-redirect=true";
                } catch (Exception ex) {
                    JSFUtil.showFatalMessage("Error!!!", "Ha ocurrido un error al agregar el producto al carrito: "+ex.getMessage());
                    Logger.getLogger(DetalleProductoMB.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            } else{
                return "/pages/logged/GenerarFactura?faces-redirect=true";
            }
        } else {
            return "/pages/public/Login?faces-redirect=true";
        }
    }
    /**
     * Metodo usado para obtener la ruta de la images a mostrar en pantalla
     * @return ruta relativa de la imagen a mostrar.
     * @param f - Foto a mostrar.
     */
    public String getOneFoto(Fotos f) {
        try {
            return JSFUtil.getInitParam("IMAGE_SHOW_PATH")+f.getRuta()+"/"+f.getFoto();
        } catch (IndexOutOfBoundsException ex){
            return "/resources/images/imageNotFound.jpg";
        }
    }
}