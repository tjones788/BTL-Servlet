/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usd.btl.REST;

import edu.usd.btl.toolbridge.ToolBridgeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tyler
 */
@Stateless
@Path("bridges")
@Consumes({
//    MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON
})
@Produces({
//    MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON
})
public class ToolBridgeEntityFacadeREST extends AbstractFacade<ToolBridgeEntity> {
    @PersistenceContext(unitName = "BTL-RESTPU")
    private EntityManager em;

    public ToolBridgeEntityFacadeREST() {
        super(ToolBridgeEntity.class);
    }
    
    @GET
    public String getAllBridges() {
        List<ToolBridgeEntity> temp = super.findAll();
        List<String> tbes = new ArrayList();
        //ugly hack, no really, check the function
        for(ToolBridgeEntity tbe : temp) {
            tbes.add(buildBridgeString(tbe));
        }
        
        return tbes.toString();
    }
    
    @POST
    @Override
    public void create(ToolBridgeEntity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    public void edit(@PathParam("id") Integer id, ToolBridgeEntity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    public String find(@PathParam("id") Integer id) {
        return buildBridgeString(super.find(id));
    }

//    @GET
//    @Override
//    public List<ToolBridgeEntity> findAll() {
//        return super.findAll();
//    }

    @GET
    @Path("{from}/{to}")
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<ToolBridgeEntity> tbes = super.findRange(new int[]{from, to});
        List<String> tbeStrings = new ArrayList();
        for(ToolBridgeEntity tbe : tbes) {
            tbeStrings.add(buildBridgeString(tbe));
        }
        return tbeStrings.toString();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countBridges() {
        return String.valueOf(super.count());
    }
    
    //afforementioned ugly hack...
    private String buildBridgeString(ToolBridgeEntity tbe) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"id\":\"").append(tbe.getId());
        sb.append("\",\"ontologyId\":\"").append(tbe.getOntologyId());
        sb.append("\",\"toolId\":\"").append(tbe.getToolId().getId());
        sb.append("\"}");
        return sb.toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
