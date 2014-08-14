package com.carritocompras.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.carritocompras.dao.GenericDao;
import javax.persistence.NoResultException;
import com.carritocompras.entities.Usuarios;
import com.carritocompras.entities.Direccion;
import javax.persistence.NonUniqueResultException;

/**
 * Clase encargada de manejar la entidad de Direccion. Hereda los metodos necesarios
 * para interactuar con la entidad.
 * @author Armando
 * @version 1.0
 */
public class DireccionDao extends GenericDao<Direccion> {
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Metodo encargado de retornar todas las direccion almacendas en la BD.
     * @return Lista de direcciones almacendas en la BD.
     * Exception NoResultException posible excepcion lanzada cuando no se encuentran datos.
     */
    public List<Direccion> findAll() throws NoResultException {
        return EM.createNamedQuery(Direccion.FIND_ALL,Direccion.class)
                 .getResultList();
    }
    
    /**
     * Metodo encargado de retornar todas las direccion almacendas en la BD
     * filtradas por un usuario en especifico.
     * @return Lista de direcciones almacendas en la BD.
     * @param user : usuario por el cual se filtrara el query.
     * Exception NoResultException posible excepcion lanzada cuando no se encuentran datos.
     */
    public List<Direccion> findAllByUser(Usuarios user) throws NoResultException {
        return EM.createNamedQuery(Direccion.FIND_ALL_BY_USER, Direccion.class)
                 .setParameter("p1", user)
                 .getResultList();
    }
    
    /**
     * Metodo encargado de retornar una sola direccion que seria la principal para el usuario.
     * @return direccion por defecto para el usuario.
     * @param user : usuario a filtrar.
     * @exception NoResultException posible excepcion lanzada cuando no se encuentran datos.
     * @exception NonUniqueResultException posible exception en caso de que el usuario tenga mas
     * de una direccion por defecto.
     */
    public Direccion findDireccionPrincipal(Usuarios user) throws NoResultException, NonUniqueResultException{
        return EM.createNamedQuery(Direccion.FIND_PRINCIPAL,Direccion.class)
                 .setParameter("p2", true)
                 .setParameter("p3", user)
                 .getSingleResult();
    }   
    
    /**
     * Metodo encargado de retornar una sola direccion por su identificador
     * @return direccion por defecto para el usuario.
     * @param id : identificador unico del registro.
     * @exception NoResultException posible excepcion lanzada cuando no se encuentran datos.
     * @exception NonUniqueResultException posible exception en caso de que el usuario tenga mas
     * de una direccion por defecto.
     */
    public Direccion findDireccionById(int id) throws NoResultException, NonUniqueResultException {
        return EM.createNamedQuery(Direccion.FIND_BY_ID,Direccion.class)
                 .setParameter("p4", id)
                 .getSingleResult();
    }

    /**
     * Se sobre escribe este metodo para lograr que siempre exista
     * una direccion por defecto o principal.
     * @param obj : objeto a eliminar de la BD.
     * @exception Exception posible excepcion al tratar de borrar.
     */
    @Override
    public void delete(Direccion obj) throws Exception {
        super.delete(obj);
        marcarPrincipal(obj);
    }

    /**
     * Se sobre escribe este metodo para lograr que siempre exista
     * una direccion por defecto o principal.
     * @param obj : objeto a actualizar de la BD.
     * @return objeto direccion luego de ser actualizado.
     * @exception Exception posible excepcion al tratar de actualizar.
     */
    @Override
    public Direccion update(Direccion obj) throws Exception {
        super.update(obj); 
        marcarPrincipal(obj);
        return obj;
    }

    /**
     * Se sobre escribe este metodo para lograr que siempre exista
     * una direccion por defecto o principal.
     * @param obj : objeto a insertar de la BD.
     * @return objeto direccion luego de ser persistido.
     * @exception Exception posible excepcion al tratar de insertar.
     */
    @Override
    public Direccion insert(Direccion obj) throws Exception {
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
        marcarPrincipal(obj);
        return obj;
    }
    
    /**
     * Este metodo es el encargado de asegurarse de que siempre exista una 
     * direccion princiapal de envio.
     * @param obj objeto Direccion utilizado para buscar las direccion que 
     * tiene el usuario asignada.
     */
    private void marcarPrincipal(Direccion obj){
        
        //Si el usuario dueno de la direccion no tiene direccion principal de
        //envio entonce se procede a asignar una.
        try {
            this.findDireccionPrincipal(obj.getUsuario());
        } catch (NoResultException e) {
                //Se recorren las direccion que tiene el usuario.
                for (Direccion d : findAllByUser(obj.getUsuario())){
                //Siempre que la direccion actual no sea la principal
                // y El objeto no sea el mismo se procede.
                if ((!d.getPrincipal()) && (!d.equals(obj)) ){
                    d.setPrincipal(Boolean.TRUE);
                    try {
                        //se actualiza esta direccion como la principal.
                        super.update(d);
                    } catch (Exception ex) {
                        Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Se sale del bucle ya que solo se necesita una direccion principal.
                    break;
                }
            }
        } catch (NonUniqueResultException ex) {
              //En caso de que ya existan dos direcciones marcadas como principales.
              //Se procede a desmarcar la que es direfente de la que se esta actualiando.
              for(Direccion d : findAllByUser(obj.getUsuario())){
                  if ( d.getPrincipal() && (!d.equals(obj)) ){
                      d.setPrincipal(Boolean.FALSE);
                      try {
                          super.update(d);
                      } catch (Exception ex1) {
                          Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex1);
                      }
                  }
              }  
        }
    }
    //</editor-fold>
}