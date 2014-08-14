package com.carritocompras.dao.impl;

import java.util.List;
import com.carritocompras.dao.GenericDao;
import javax.persistence.NoResultException;
import com.carritocompras.entities.Productos;
import javax.persistence.NonUniqueResultException;

/**
 * Clase que representa la herencia de GenericDao pasandole un objeto Producto
 * para poder utilizar los metodos abstraidos de su clase padre y ademas poder
 * implementar otros metodos necesarios para interactuar con la tbl producto.
 * @author Armando
 * @version 1.0
 */
public class ProductosDao extends GenericDao<Productos> {

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
    /**
     * Metodo utilizado para extraer de la base de datos todos los productos
     * que se encuentren contenidos en la tbl de productos.
     * @return lista de productos.
     * @exception NoResultException Excepcion causado por que la consulta no retorno datos.
     */
    public List<Productos> findAll() throws NoResultException {
       return EM.createNamedQuery(Productos.FIND_ALL_PRODUCTOS).getResultList();
    }
    /**
     * Metodo utilizado para extraer de la base de datos todos los productos
     * que se encuentren contenidos en la tbl de productos que tengan existencia.
     * @return lista de productos.
     * @exception NoResultException Excepcion causado por que la consulta no retorno datos.
     */
    public List<Productos> findAllWithStock() throws NoResultException {
        return EM.createNamedQuery(Productos.FIND_ALL_PRODUCTOS_WITH_STOCK).getResultList();
    }
    /**
     * Metodo utilizado para extraer de la base de datos un producto por id.
     * @return lista de productos.
     * @param id : parametro de busqueda del query.
     * @exception NoResultException : Excepcion causado por que la consulta no retorno datos.
     * @exception NonUniqueResultException : La consulta retorno mas de una instancia de objeto producto.
     */
    public Productos findById(int id) throws NoResultException, NonUniqueResultException {
        return (Productos)EM.createNamedQuery(Productos.FIND_PRODUCTOS_BY_ID)
                            .setParameter("p1", id)
                            .getSingleResult();
    }
    
    /**
     * Metodo que retorna una lista de productos por los parametros suministrados.
     * @return Lista de productos.
     * @param parameter : parametros de busqueda que puede ser cualquier string contenido
     * en las columnas de titulo o descripcion de la tbl de productos.
     * @exception NoResultException posible excepcion encaso de no encontrar resultados.
     */
    public List<Productos> findByParameter (String parameter) throws NoResultException{
        
        //En caso de el parametro esta vacio.
        if (parameter.isEmpty() || parameter.length() == 0){
            return findAllWithStock();
        } 

        return (List<Productos>) EM.createNamedQuery(Productos.FIND_BY_PARAMETER, Productos.class)
                                   .setParameter("p2", "%"+parameter+"%")
                                   .getResultList();
    }
    //</editor-fold>
    
    @Override
    public String toString() {
        return "ProductosDao Object";
    }
}