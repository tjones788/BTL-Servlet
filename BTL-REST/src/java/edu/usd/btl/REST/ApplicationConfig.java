/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.REST;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Tyler.Jones
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(edu.usd.btl.REST.CorsResponseFilter.class);
        resources.add(edu.usd.btl.REST.ToolBetsEntityFacadeREST.class);
        resources.add(edu.usd.btl.REST.ToolBridgeEntityFacadeREST.class);
        resources.add(edu.usd.btl.REST.ToolTree.class);
    }
    
}
