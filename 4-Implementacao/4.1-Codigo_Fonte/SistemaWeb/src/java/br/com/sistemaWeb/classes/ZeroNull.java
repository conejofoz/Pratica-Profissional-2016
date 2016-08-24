/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemaWeb.classes;

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  

/**
 *
 * @author conejo
 */

    
  
public class ZeroNull implements ServletContextListener {  
  
    @Override  
    public void contextDestroyed(ServletContextEvent sce) {  
  
    }  
  
    @Override  
    public void contextInitialized(ServletContextEvent sce) {  
        System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");  
    }  
}  
    

