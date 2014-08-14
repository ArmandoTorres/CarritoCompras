package com.carritocompras.dao.impl;

import com.carritocompras.entities.Pais;
import com.carritocompras.dao.GenericDao;
import com.carritocompras.util.Estados;
import java.util.List;
import javax.persistence.NoResultException;

/**
 * Clase encargada de manejar la entidad de Pais.
 * @author Armando
 * @version 1.0
 */
public class PaisDao extends GenericDao<Pais> {
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Metodo utilizado para extraer todos los paises activos de la BD.
     * @return Listado de paises almacenados.
     * @exception NoResultException : posible excepcion en caso de que el query 
     * no retorne resultados
     */
    public List<Pais> findAll() throws NoResultException {
        return EM.createNamedQuery(Pais.FIND_ALL,Pais.class)
                 .setParameter("p1", Estados.ACTIVO)
                 .getResultList();
    }
    /**
     * Metodo utilizado para extraer un solo pais activo de la BD.
     * @return pais almacenado.
     * @param id identificador del pais a buscar.
     * @exception NoResultException : posible excepcion en caso de que el query 
     * no retorne resultados
     */
    public Pais findById(int id) throws NoResultException {
        return EM.createNamedQuery(Pais.FIND_BY_ID, Pais.class)
                 .setParameter("p2", id)
                 .setParameter("p3", Estados.ACTIVO)
                 .getSingleResult();
    }
    //</editor-fold>
    
}