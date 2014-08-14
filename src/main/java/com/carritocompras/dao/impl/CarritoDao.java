package com.carritocompras.dao.impl;

import java.util.List;
import com.carritocompras.dao.GenericDao;
import javax.persistence.NoResultException;
import com.carritocompras.entities.Carrito;
import com.carritocompras.entities.Usuarios;
import com.carritocompras.entities.Productos;

/**
 * Clase encargada de interactura con la tbl de carrito en la base de datos.
 * Realizar las operaciones de insert, update y delete, ademas de extraer la 
 * informacion que se requiere para esta aplicacion.
 * @author Armando
 * @version 1.0
 */
public class CarritoDao extends GenericDao<Carrito> {
    
    /**
     * Metodo utilizado pare extraer todos los productos que un usuario tiene
     * actualmente agregado en su carrito de compras.
     * @return Lista : Listado de productos.
     * @param user : Usuario por el cual se va a buscar en la BD.
     * @exception NoResultException : Posible exception si no encuentra datos.
     */
    public List<Carrito> findAllByUser(Usuarios user) throws NoResultException {
        return EM.createNamedQuery(Carrito.FIND_ALL, Carrito.class)
                 .setParameter("p1", user)
                 .getResultList();
    }
    
    /**
     * Metodo utilizado para buscar en la BD un carrito por Id.
     * @return entidad carrito a buscar.
     * @param id : Identificar por el cual se buscara en la BD.
     * @exception NoResultException : Posible exception si no encuentra datos.
     */
    public Carrito findById(int id) throws NoResultException {
        return EM.createNamedQuery(Carrito.FIND_BY_ID, Carrito.class).setParameter("p2",id).getSingleResult();
    }
    
    /**
     * Metodo utilizado para comprobar si el usuario ya tiene asociado ese producto
     * en su carrito actualmente. En caso de que el query retorne datos significa
     * que ya tiene agregado dicho producto en caso de que ocurra una excepcion del
     * tipo NoResultException o que el query no retorne datos entonces se puede 
     * agregar el producto al carrito.
     * @return Boolean : true en caso de que encuentre la duplicidad.
     *                 : false en caso de que no encuentre duplicidad.
     * @param usr : usuario por el cual se va a buscar.
     * @param prd : producto a buscar.
     */
    public boolean findDuplicity(Usuarios usr, Productos prd){
        try {
            return (EM.createNamedQuery(Carrito.VERIFY_DUPLICITY,Carrito.class)
                      .setParameter("p3", prd)
                      .setParameter("p4", usr)
                      .getSingleResult() != null);
        } catch (NoResultException e) {
            return false;
        }
    }
    
}