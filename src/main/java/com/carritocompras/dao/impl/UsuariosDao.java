package com.carritocompras.dao.impl;

import java.util.List;
import com.carritocompras.util.Estados;
import com.carritocompras.dao.GenericDao;
import com.carritocompras.entities.Usuarios;

/**
 * Clase utilizada para realizar acciones sobre la tbl de Usuario, esta clase
 * Hereda de GenericDao sustituyendo su tipo generico por un objeto usuario,
 * Realizando de esta manera operaciones solo sobre la tbl deseada.
 * @author Armando
 * @version 1.0
 */
public class UsuariosDao extends GenericDao<Usuarios> {
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Metodo utilizado para buscar todos los usuarios en la base de datos.
     * @return Lista de usuarios existentes.
     * @exception javax.persistence.NoResultException : No se encontraron resultados para la busqueda
     */
    public List<Usuarios> findAll() throws javax.persistence.NoResultException {
        return EM.createNamedQuery(Usuarios.FIND_ALL_USUARIOS).getResultList();
    }
    
    /**
     * Metodo usado para buscar solo los usuarios activos contenidos en la BD.
     * Nota si el usuario en el campo estado es igual a 'I' no sera incluido en la lista.
     * @return Lista de usuario activos en la base de datos.
     * @exception javax.persistence.NoResultException : No se encontraron resultados para la busqueda
     */
    public List<Usuarios> findAllActive() throws javax.persistence.NoResultException {
        return EM.createNamedQuery(Usuarios.FIND_ALL_ACTIVE_USUARIOS)
                 .setParameter("p2", Estados.ACTIVO)
                 .getResultList();
    }
    
    /**
     * Metodo usado para buscar por nombre al usuario en la BD.
     * @param name : parametro de busqueda obligatorio usado para buscar filtrar.
     * @return usuario que se encontro por nombre en la BD.
     * @exception javax.persistence.NoResultException : No se encontraron resultados para la busqueda
     */
    public Usuarios findByName(String name) throws javax.persistence.NoResultException {
        return (Usuarios)EM.createNamedQuery(Usuarios.FIND_USER_BY_EMAIL)
                           .setParameter("p1", name)
                           .getSingleResult();
    }
    
    /**
     * Metodo usado para autenticar al usuario, si retorna true significa que el 
     * usuario y contrasena es correcto en caso contrario no es un usuario valido.
     * @return true si pasa la autenticacion y false en caso de que no.
     * @param name : nombre por el cual sera filtrado el usuario.
     * @param pass : clave de acceso para acceder a la aplicacion.
     * @exception javax.persistence.NoResultException : No se encontraron resultados para la busqueda
     */
    public boolean autenticar(String name, String pass) throws javax.persistence.NoResultException {
        Usuarios user = findByName(name);
        return user.getPassword().equals(pass);
    }
    //</editor-fold>
    
        @Override
    public String toString() {
        return "UsuariosDao Object";
    }
    
}