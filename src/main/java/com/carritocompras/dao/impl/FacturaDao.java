package com.carritocompras.dao.impl;

import com.carritocompras.dao.GenericDao;
import com.carritocompras.entities.Factura;
import com.carritocompras.entities.Usuarios;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TemporalType;

/**
 * Clase que representa el Objeto que sera utilizado para acceder a los datos de
 * de la tbl de facturas y realizar las operaciones requeridas sobre la misma.
 * @author Armando
 * @version 1.0
 */
public class FacturaDao extends GenericDao<Factura> {

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Metodo utilizado para retornar todas las facturas contenidas en la BD.
     * @return Listado de facturas.
     * @exception NoResultException : Excepcion lansada cuando no se hayan encontrado datos.
     */
    public List<Factura> findAll() throws NoResultException {
        return EM.createNamedQuery(Factura.FIND_ALL, Factura.class).getResultList();
    }
    
    /**
     * Metodo utilizado para retornar una unica factura.
     * @return Factura : Instancia de Factura que se busca en la base de datos.
     * @param id : Identificador por el cual se buscara la factura.
     * @exception NoResultException : Exception lanzada cuando no se encuentran datos.
     * @exception NonUniqueResultException : Se ha recuperado mas de una factura.
     */
    public Factura findById(int id) throws NoResultException, NonUniqueResultException {
        return EM.createNamedQuery(Factura.FIND_BY_ID,Factura.class)
                .setParameter("p1", id)
                .getSingleResult();
    }
    
    /**
     * Metodo utilizado para retornar todas las facturas contenidas en la BD por usuario.
     * @return Listado de facturas.
     * @param usuario : Usuario por el cual se buscaran las facturas.
     * @exception NoResultException : Excepcion lanzada cuando no se hayan encontrado datos.
     */
    public List<Factura> findByUsuario(Usuarios usuario) throws NoResultException{
        return EM.createNamedQuery(Factura.FIND_BY_USUARIO,Factura.class)
                 .setParameter("p2", usuario)
                 .getResultList();
    }
    
    /**
     * Metodo utilizado para retornar todas las facturas contenidas en la BD por Fecha y usuario.
     * @return Listado de facturas.
     * @param fecha1 : Fecha de inicio por la cual se empezara a buscar.
     * @param fecha2 : fecha de Fin por la cual se terminara de buscar.
     * @param usuario : Usuario por el cual se buscaran las facturas.
     * @exception NoResultException : Excepcion lansada cuando no se hayan encontrado datos.
     */
    public List<Factura> findByDate(Date fecha1, Date fecha2, Usuarios usuario) throws NoResultException {
        return EM.createNamedQuery(Factura.FIND_BY_FECHA,Factura.class)
                 .setParameter("p3",fecha1,TemporalType.DATE)
                 .setParameter("p4", fecha2, TemporalType.DATE)
                 .setParameter("p5", usuario)
                 .getResultList();
    }

    //</editor-fold>
    @Override
    public String toString() {
        return "FacturaDao Object";
    }
    
}