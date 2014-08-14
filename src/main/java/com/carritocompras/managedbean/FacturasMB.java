package com.carritocompras.managedbean;

import java.util.List;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import com.carritocompras.util.JSFUtil;
import com.carritocompras.entities.Factura;
import com.carritocompras.dao.impl.FacturaDao;

/**
 * ManagedBena creado para sostener los procesos de la vista de Facturas.xhtml
 * @author Armando
 */
@ManagedBean
@ViewScoped
public class FacturasMB implements Serializable {
    
    //Dao de factura.
    private FacturaDao facturaDao;
    
    //Listado de Factura en pantalla
    private List<Factura> listaFactura;

    public List<Factura> getListaFactura() {
        return listaFactura;
    }
    
    //Metodo que inicializa las objetos.
    @PostConstruct
    public void init(){
        facturaDao = new FacturaDao();
        listaFactura = facturaDao.findByUsuario(JSFUtil.getCurrentUser());
    }
    
}