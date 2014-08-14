package com.carritocompras.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * JSF Converter para la conversion de String a formato de telefono (999) 999-9999
 * @author Armando
 */
@FacesConverter("com.carritocompras.util.PhoneConverter")
public class PhoneConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        String texto = (String) value;
        
        return ( texto.length() == 10 ? ( "("+texto.substring(0, 3)+")"+ texto.substring(3,6)+"-"+texto.substring(6) ) : texto );
        
    }
    
}