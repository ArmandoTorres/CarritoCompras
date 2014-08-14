package com.carritocompras.dao.impl;

import com.carritocompras.dao.GenericDao;
import com.carritocompras.entities.FacturaDet;

/**
 * Clase encargada de realizar las operaciones de insert, update y delete en la tbl
 * de Detalle_Factura en la BD.
 * @author Armando
 * @version 1.0
 */
public class FacturaDetDao extends GenericDao<FacturaDet> {
 
    @Override
    public String toString() {
        return "FacturaDetDao Object";
    }

}
