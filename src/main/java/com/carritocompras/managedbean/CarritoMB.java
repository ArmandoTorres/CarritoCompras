package com.carritocompras.managedbean;

import java.util.List;
import java.math.BigDecimal;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Fotos;
import com.carritocompras.entities.Carrito;
import com.carritocompras.entities.Productos;
import com.carritocompras.dao.impl.CarritoDao;

/**
 * ManagedBean encargado de gestionar los eventos que ocurran en la pagina /pages/logged/Carrito.xhtml  
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class CarritoMB implements Serializable {
    
    /**Objeto encargado de interactuar con la tbl de Carrito.*/
    private transient CarritoDao carritoDao;
    
    /**Objeto que represanta cada uno de los registros de la tbl de carrito.*/
    private List<Carrito> listaCarrito;
    
    public List<Carrito> getListaCarrito() {
        return listaCarrito;
    }
    
    /**Metodo que se ejecuta luego del constructor del managed bean, 
     * encargado de iniciar el dao de carrito y la lista de productos.*/
    @PostConstruct
    public void init(){
        //carritoDao = (CarritoDao) DaoDispacher.getDao(CarritoDao.class);
        carritoDao = new CarritoDao();
        listaCarrito = carritoDao.findAllByUser(JSFUtil.getCurrentUser());
    }
    
    /**
     * Metodo encargado de eliminar del carrito un producto seleccionado.
     * Al eliminar el producto se refresca la lista en pantalla.
     * @param event : Evento que dispara el componente se utiliza para conseguir 
     * carrito seleccionado.
     */
    public void borrarDelCarrito(ActionEvent event){
        Carrito carrito = (Carrito) event.getComponent().getAttributes().get("selected");
        try {
            carritoDao.delete(carrito);
            listaCarrito = carritoDao.findAllByUser(JSFUtil.getCurrentUser());
            //Se actualiza el objeto CarritoItems en la session.
            JSFUtil.addItemToSession("CarritoItems", "("+carritoDao.findAllByUser(JSFUtil.getCurrentUser()).size()+")");
            JSFUtil.showInfoMessage("Producto Removido", "El producto fue removido del carrito.");
        } catch (Exception e) {
            JSFUtil.showErrorMessage("Error!", "Ha ocurrido un error tratando de eliminar el producto.");
        }
    }
    /**
     * Metodo utilizado para calcular el total de los items contenidos en el carrito.
     * @return string que representa la moneda y el monto.
     */
    public BigDecimal getTotalCarrito(){
        BigDecimal sum = new BigDecimal(0);
        for(Carrito c : listaCarrito){
            sum = sum.add(c.getProducto().getPrecio());
        }
        return sum;
    }
    
    /**
     * Metodo usado para saber cuando el carrito tiene items para mostrar algunos 
     * componentes.
     * @return true : tiene items || false : no tiene items
     */
    public boolean isCarritoItem(){
        try {
            return (!listaCarrito.isEmpty());
        } catch (Exception e)    {
            return false;
        }
    }
    
    /**
     * Metodo usado para obtener la ruta de la images a mostrar en pantalla por
     * cada producto suministrado.
     * @return ruta relativa de la imagen a mostrar.
     * @param p - Producto del cual se requiere mostrar la imagen.
     */
    public String getOneFoto(Productos p) {
        try {
            //Se busca la primera foto de la lista.
            Fotos foto = p.getFotos().get(0);
            //La ruta se componen por el valor de INITPARAM(WEB.xml)/ruta de la BD/nombre de la imagen.
            return JSFUtil.getInitParam("IMAGE_SHOW_PATH")+foto.getRuta()+"/"+foto.getFoto();
        } catch (IndexOutOfBoundsException ex){
            //En caso de no encontrar la imagen se retorna una imagen por defecto.
            return "/resources/images/imageNotFound.jpg";
        }
    }
}