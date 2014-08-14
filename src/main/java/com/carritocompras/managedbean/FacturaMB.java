package com.carritocompras.managedbean;

import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Factura;
import com.carritocompras.dao.impl.FacturaDao;

/**
 * ManagedBean encargado de manejar las acciones en la vista FacturaMB.xhtml
 * @author Armando
 */
@ManagedBean
@ViewScoped
public class FacturaMB implements Serializable {
    
    //Dao de factura
    private transient FacturaDao facturaDao;
    
    //Factura a mostrar en pantalla.
    private Factura factura;

    public Factura getFactura() {
        return factura;
    }
    
    /**
     * Metodo que instancia a factura Dao y se encarga de buscar la factura
     * seleccionada por el usuario segun el id que se encuentra en el requestParameterMap.
     */
    @PostConstruct 
    public void init(){
        facturaDao = new FacturaDao();
        factura = facturaDao.findById(Integer.parseInt(JSFUtil.getParameterFromRequestMap("SelectedFactura").toString()));
    }
    
}