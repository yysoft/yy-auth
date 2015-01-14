/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-16
 */
package net.caiban.auth.dashboard.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-16
 */
public class CommonBindingInitializer implements WebBindingInitializer{
	
	public void initBinder(WebDataBinder binder, WebRequest request) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));  
    }
}
