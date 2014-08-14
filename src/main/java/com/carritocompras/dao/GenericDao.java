package com.carritocompras.dao;

import com.carritocompras.util.Coneccion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Clase abstracta generica que contiene las funciones de CRUD mas genericas 
 * Que seran utilizadas por las demas Clases que la hereden.
 * @param <A> Clase generica que sera sustituida por los hijos.
 * @author Armando
 * @version 1.0
 */
public abstract class GenericDao <A> {
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**Objeto que provee la coneccion a la base de datos.*/
    protected EntityManagerFactory EMF;
    /**Objecto que provee las opciones para realizar operaciones sobre la base
     * De datos.*/
    protected EntityManager EM;
    /**Objeto que meneja la transacciones que se realizan a la base de datos.*/
    protected EntityTransaction ET;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**Default Constructor utilizado para instancias la coneccion y los demas 
     objetos necesario para interactuar con la BD*/
    protected GenericDao(){
        EMF = Coneccion.getConeccion();
        EM = EMF.createEntityManager();
        ET = EM.getTransaction();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**Metodo utilizado para insertar objetos genericos a la base de datos,
     * Estos objetos sera luego sustituidos por cada implementacion de la clase.
     * @return Objeto generico luego de ser persistido.
     * @param obj : objeto generico que representa un registro en la tbl deseada.
     * @throws Exception : posible exception que se pueda dar en la persistencia.
     */
    public A insert(A obj) throws Exception {
        try {
            ET.begin();
            EM.persist(obj);
            ET.commit();
        } catch (Exception ex){
            ET.rollback();
            EM.flush();
            throw new Exception(ex.getMessage());
        }    
        return obj;
    }
    
    /**
     * Metodo utilizado para actualizar objectos genericos en la base de datos,
     * Estos objectos seran luego sustituidos por cada implementacion de la clase.
     * @return Objeto generico luego de ser actualizado.
     * @param obj : objeto generico que representa un registro en la tbl deseada.
     * @throws Exception : posible exception que se pueda dar en la persistencia.
     */
    public A update(A obj) throws Exception {
        try {
            ET.begin();
            EM.merge(obj);
            ET.commit();
        } catch (Exception ex){
            ET.rollback();
            EM.flush();
            throw new Exception(ex.getMessage());
        }    
        return obj;
    }

    /**
     * Metodo utilizado para borrar objectos genericos en la base de datos,
     * Estos objectos seran luego sustituidos por cada implementacion de la clase.
     * @param obj : objeto generico que representa un registro en la tbl deseada.
     * @throws Exception : posible exception que se pueda dar en la persistencia.
     */    
    public void delete(A obj) throws Exception {
        try {
            ET.begin();
            EM.remove(EM.merge(obj));
            ET.commit();
        } catch (Exception ex){
            ET.rollback();
            EM.flush();
            throw new Exception(ex.getMessage());
        }    
    }
    //</editor-fold>
}