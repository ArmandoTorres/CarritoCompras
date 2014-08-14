package com.carritocompras.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase encargada de carga el archivo de persistencia, y retornar el objecto
 * EntityManagerFactory para poder interactuar con la base de datos.
 * @author Armando
 * @version 1.0
 */
public class Coneccion {
    //<editor-fold defaultstate="collapsed" desc="codigos">
    
    /**Objeto que representa la factoria de objetos necesarios para 
     * realizar transacciones en la base de datos.*/
    private static EntityManagerFactory EMF = null;
    
    /**Constructor privado para que la clase no pueda ser instanciada.*/
    private Coneccion(){}
    
    /**
     * Metodo encargado de retornar la factoria que creara los objetos
     * para interactuar con la base de datos.
     * @return EMF : objecto EntityManagerFactory.
     */
    public static EntityManagerFactory getConeccion(){
        if (EMF == null){
            EMF = Persistence.createEntityManagerFactory("CarritoComprasPU");
        }
        return EMF;
    }
    //</editor-fold>
}