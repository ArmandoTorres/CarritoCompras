package com.carritocompras.managedbean;

import java.util.List;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ViewScoped;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Direccion;
import com.carritocompras.dao.impl.DireccionDao;


/**
 * ManagedBean creado para trabajar con las acciones de la vista DireccionesEnvio.xhtml
 * @author Armando
 * @version 1.0
 */

@ManagedBean(name="dirMB")
@ViewScoped
public class DireccionEnvioMB implements Serializable {
    
    //Objeto usado para trabajar con la tbl de Direccion.
    private transient DireccionDao direccionDao;
    //Listado de direcciones a mostrar en pantalla.
    private List<Direccion> listaDireccion;

    public List<Direccion> getListaDireccion() {
        return listaDireccion;
    }
    
    /**
     * Metodo usado para crear la instancia de los objetos requeridos y cargar
     * la lista de direcciones por usuario.
     */
    @PostConstruct
    public void init(){
        direccionDao = new DireccionDao();
        listaDireccion = direccionDao.findAllByUser(JSFUtil.getCurrentUser());
    }
    
    /**
     * Metodo usuado para limpiar los objetos al momento de destruir el managedBean.
     */
    @PreDestroy
    public void clearAll(){
        direccionDao = null;
        listaDireccion.clear();
    }
    
    /**
     * Metodo encargado de eliminar la direccion seleccionada por el usuario.
     * @param event - Event disparado por el usuario, este contiene como atributo 
     * un objeto Direccion el cual sera eliminado de la BD.
     */
    public void eliminarDireccion(ActionEvent event){
        //Se obtiene el Objeto Direccion del Componente.
        Direccion dir = (Direccion) event.getComponent().getAttributes().get("selected");
        try {
            //Se elimina el registro de la BD.
            direccionDao.delete(dir);
            //Se remueve el objeto de la lista en pantalla.
            listaDireccion.remove(dir);
            JSFUtil.showInfoMessage("Direccion Removida", null);
        } catch (Exception ex) {
            Logger.getLogger(DireccionEnvioMB.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.showErrorMessage("Error", "Ha ocurrido un error al momento de eliminar la direccion.");
        }
    }
    
    /**
     * Metodo utilizado para volver la direccion seleccionada como la direccion 
     * por defecto para los envios.
     * @param event - Evento disparado por el usuario en ese viaje como atributo
     * un objeto Direccion que sera la que se marcara como direccion por defecto.
     */
    public void makeDefault(ActionEvent event){
        //Se obtiene la direccion del atributo
        Direccion dir = (Direccion) event.getComponent().getAttributes().get("selected");
        //Se marcar como principal.
        dir.setPrincipal(Boolean.TRUE);
        try {
            //Se actualiza el registro en la BD.
            direccionDao.update(dir);
            JSFUtil.showInfoMessage("Direccion Actualizada", null);
        } catch (Exception ex) {
            Logger.getLogger(DireccionEnvioMB.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.showErrorMessage("Error", "Ha ocurrido un error al momento de actualiza la direccion.");
        }
    }
    
}