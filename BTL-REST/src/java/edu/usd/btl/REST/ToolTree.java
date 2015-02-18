/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.REST;

import edu.usd.btl.toolTree.OntoToTree;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import edu.usd.btl.toolTree.OntoToTree;

/**
 * REST Web Service
 *
 * @author Tyler_000
 */
@Path("tooltree")
@RequestScoped
public class ToolTree {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public ToolTree() {
    }

    /**
     * Retrieves representation of an instance of edu.usd.btl.REST.TestResource
     *
     * @return an instance of java.lang.String
     * @throws java.lang.Exception
     */
    @GET
    @Produces("application/json")
    public String getJson() throws Exception {
        OntoToTree tree = new OntoToTree();
        String testJson = tree.getOntoJson(); //get EDAM ontology nodes as JSON
        tree.jsonToJava(testJson);
        return testJson;
    }
    
    @GET
    @Path("/get")
    @Produces("application/json")
    public String getTree() throws Exception {
        OntoToTree tree = new OntoToTree();
        String testJson = tree.getOntoJson(); //get EDAM ontology nodes as JSON
        
        return "jsonToJava";
    }

    /**
     * PUT method for updating or creating an instance of TestResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content
    ) {
    }
}
