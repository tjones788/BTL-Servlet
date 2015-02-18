/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.usd.btl.toolbets.ToolBetsEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.usd.edu.btl.betsconverter.BETSV1.BETSV1;

/**
 *
 * @author Tyler
 */
@Stateless
@Path("tools")
@Consumes({
    //    MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON
})
@Produces({
    //    MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON
})
public class ToolBetsEntityFacadeREST extends AbstractFacade<ToolBetsEntity> {

    @PersistenceContext(unitName = "BTL-RESTPU")
    private EntityManager em;

    public ToolBetsEntityFacadeREST() {
        super(ToolBetsEntity.class);
    }

    @GET
    public String getAllTools() throws Exception {
//        List<ToolBetsEntity> tools = super.findAll();
//        List<BETSV1> betsobjects = new ArrayList();
//        for (ToolBetsEntity tool : tools) {
//            String text = new String(tool.getBets());
//            ObjectMapper mapper = new ObjectMapper();
//            BETSV1 bet = mapper.readValue(text, BETSV1.class);
//            betsobjects.add(bet);
//        }
//        GenericEntity<List<BETSV1>> entity = new GenericEntity<List<BETSV1>>(betsobjects) {
//        };
        List<ToolBetsEntity> tools = super.findAll();
        List<String> bets = new ArrayList();
        for (ToolBetsEntity tool : tools) {
            bets.add(new String(tool.getBets()));
        }
//        GenericEntity<List<ToolBetsEntity>> entity = new GenericEntity<List<ToolBetsEntity>>(tools) {
//        };
        return bets.toString();
        //return Response.ok(entity).build();
    }

    @GET
    @Path("{id}")
    public String find(@PathParam("id") Integer id) throws Exception {
        ToolBetsEntity tool = this.em.find(ToolBetsEntity.class, id);
//        if (tool != null) {
//            ObjectMapper mapper = new ObjectMapper();
//            BETSV1 bets = mapper.readValue(new String(tool.getBets()), BETSV1.class);
//            return Response.ok(bets).build();
//        } else {
//            return Response.noContent().build();
//        }
        //return Response.ok(tool).build();
        return new String(tool.getBets());
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countTools() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
