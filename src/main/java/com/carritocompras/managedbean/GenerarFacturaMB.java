package com.carritocompras.managedbean;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedBean;
import com.carritocompras.dao.impl.*;
import com.carritocompras.entities.*;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import javax.faces.event.ValueChangeEvent;


/**
 * ManagedBean usado para manejar las acciones en la pagina de Factura.xhtml
 * La pagina es de tipo wizard de tres pasos:
 * 1-) Se le muestra al usuario la posibilidad de escoger la cantidad de productos a comprar.
 * 2-) Se le muestra al usuario la lista de direcciones que tiene para el envio.
 * 3-) Se confirma la compra y se crea la factura.
 * @author Armando
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class GenerarFacturaMB implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    
    //Dao utilizado para interactuar con los productos que tiene el cliente agregado.
    private transient CarritoDao carritoDao;
    //Dao utilizado para tomar la lista de direccion de que tiene el usuario.
    private transient DireccionDao direccionDao;
    //Dao utilizado para manejar los cambios realizados en los productos del carrito.
    private transient ProductosDao productoDao;
    
    //Lista de los productos contenidos en el carrito actual.
    private List<Carrito> listaCarrito;
    
   //id de la direccion seleccionada por el usuario.
    private int direccion;
    
    //Direccion que se le muestra al usuario en pantalla, cambia segun el id seleccionado.
    private Direccion dir;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's && Set's">
    public Direccion getDir() {
        return dir;
    }

    public void setDir(Direccion dir) {
        this.dir = dir;
    }
    
    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public List<Carrito> getListaCarrito() {
        return listaCarrito;
    }
    
    public void setListCarrito(List<Carrito> lista){
        this.listaCarrito = lista;
    }
    //</editor-fold>
    
    /**
     * Metodo que se dispara luego del constructor del managedbean y inicializa los objetos necesarios para generar la factura.
     */
    @PostConstruct
    public void init(){
        carritoDao = new CarritoDao();
        direccionDao = new DireccionDao();
        productoDao = new ProductosDao();
        
        //Se llena la lista con los item del usuario actual.
        listaCarrito = carritoDao.findAllByUser(JSFUtil.getCurrentUser());
        //Se llena la lista con la direccion principal del usuario.
        dir = direccionDao.findDireccionPrincipal(JSFUtil.getCurrentUser());
        //Se llena el id con la direccion por defecto.
        direccion = dir.getId();
   }
    
    /**
     * Metodo que maneja los selectOneItem y se usa para mostar todas las cantidades
     * disponibles del articulo, para que el usuario pueda seleccionar la cantidad deseada.
     * @return lista de tipo SelectItem con valor e indice de tipo integer.
     * @param id del producto para mostrar la cantidad.
     */
    public List<SelectItem> getAllQuantities(int id){
        List<SelectItem> items = new ArrayList<SelectItem>();
        int cant = productoDao.findById(id).getCantidad();
        for (int i = 1; i <= cant; i++){
            items.add(new SelectItem(i,Integer.toString(i)));
        }
        return items;
    }
    
    /**
     * Metodo usado para conseguir las direcciones que tiene el usuario asociadas.
     * @return Lista de tipo SelectItem con el id y la descripcion de la calle.
     */
    public List<SelectItem> getDirecciones(){
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (Direccion d : direccionDao.findAllByUser(JSFUtil.getCurrentUser())){
            items.add(new SelectItem(d.getId(),d.getCalle1()));
        }
        return items;
    }
    
    /**Metodo usado para setiar la direccion actual seleccionada por el usuario.
     * @param e valueChangeEvent
     */
    public void setCurretDireccion(ValueChangeEvent e){
        //Se consigue la direccion de con el id seleccionado.
        dir = direccionDao.findDireccionById(direccion);
    }
    
    /**
     * Metodo encargado de generar la factura.
     * @return url con la ruta de la factura ya generada.*/
    public String confirmarCompra(){

        try {
            //Se crea el encabezado la factura.
            Factura facEnc = new Factura();
            //Fecha actual
            facEnc.setFecha(new Date());
            //Direccion de envio
            facEnc.setDireccion(dir.getCalle1()+", "+dir.getCalle2()+", "+dir.getCiudad()+", "+dir.getZipCode()+", "+dir.getPais().getNombre());
            //Usuario actual
            facEnc.setUsuario(JSFUtil.getCurrentUser());
            FacturaDao facDao = new FacturaDao();
            
            //Variable que almacena el total de la factura. Se inicializa en 0.
            BigDecimal total = BigDecimal.ZERO;
            
            //Se procede a grabar el encabezado con los datos necesarios y se consigue el objeto con el id.
            facEnc = facDao.insert(facEnc);
            
            //Se verifica si la factura consiguio el id para poder proceder con el detalle.
            if (facEnc.getId() > 0) {
                //Se crean los detalles para insertar en la factura.
                List<FacturaDet> listFacDet = new ArrayList<FacturaDet>();
                //Por cada uno de los items en el carrito se genera un registro de detalle en la factura.
                for (Carrito c : getListaCarrito()){
                    FacturaDet facDet = new FacturaDet();
                    facDet.setCantidad(c.getProducto().getCantidad());
                    facDet.setFactura(facEnc);
                    facDet.setPrecio(c.getProducto().getPrecio());
                    facDet.setProducto(c.getProducto());
                    facDet.setTotal( c.getProducto()
                                      .getPrecio()
                                      .multiply( new BigDecimal( String.valueOf( c.getProducto().getCantidad() ) ) ) 
                                    );
                    //Se agrega la factura al listado.
                    listFacDet.add(facDet);
                    total = total.add(facDet.getTotal());
                }
                //Se agrega el detalle de productos al encabezado
                facEnc.setDetalleFactura(listFacDet);
                //Se actualiza el total de la factura.
                facEnc.setTotal(total);
                //Se actualiza la factura.
                facDao.update(facEnc);
                
                //Se procede a rebajar la existencia a los productos comprados.
                for (Carrito c : getListaCarrito()){
                    Productos p = productoDao.findById(c.getProducto().getId());
                    p.setCantidad(p.getCantidad() - c.getProducto().getCantidad());
                    productoDao.update(p);
                }
                
                //Se procede a eliminar los datos del carrito.
                for (Carrito c : getListaCarrito()){
                    carritoDao.delete(c);
                }
                listaCarrito.clear();
                JSFUtil.addItemToSession("CarritoItems", "("+ listaCarrito.size() +")" );
                JSFUtil.showInfoMessage("Exito", "Su factura ha sido generada.");
                //Se redirige la pagina con el id de la factura generada.
                return "/pages/logged/Factura?faces-redirect=true&SelectedFactura="+facEnc.getId();
            }    
        } catch (Exception ex) {
            Logger.getLogger(GenerarFacturaMB.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.showErrorMessage("Error", "Se ha produccido un erro al intertar crear la factura.");
            return null;
        }
        return null;
    }

    /**
     * Metodo usado para obtener la ruta de la images a mostrar en pantalla por
     * cada producto suministrado.
     * @return ruta relativa de la imagen a mostrar.
     * @param p - Producto del cual se requiere mostrar la imagen.
     */
    public String getOneFoto(Productos p) {
        try {
            Fotos foto = p.getFotos().get(0);
            return JSFUtil.getInitParam("IMAGE_SHOW_PATH")+foto.getRuta()+"/"+foto.getFoto();
        } catch (IndexOutOfBoundsException ex){
            return "/resources/images/imageNotFound.jpg";
        }
    }
}