///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.usd.btl.REST;
//
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonNode;
//import edu.usd.btl.ontology.BioPortalElement;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.UriInfo;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PUT;
//import javax.enterprise.context.RequestScoped;
//import edu.usd.btl.ontology.TestFileRead;
//import java.util.ArrayList;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//
///**
// * REST Web Service
// *
// * @author Tyler.Jones
// */
//@Path("/categories")
//@RequestScoped
//public class CategoriesResource {
//
//    @Context
//    private UriInfo context;
//
//    /**
//     * Creates a new instance of CategoriesResource
//     */
//    public CategoriesResource() {
//    }
//
//    TestFileRead test = new edu.usd.btl.ontology.TestFileRead();
//
//    /**
//     * Retrieves representation of an instance of
//     * edu.usd.btl.REST.CategoriesResource
//     *
//     * @return an instance of java.lang.String
//     * @throws java.lang.Exception
//     */
//    @GET
//    @Produces("application/json")
//    public String getJson() throws Exception {
//        ArrayList<BioPortalElement> nodeList = test.readOntologyFile();
//        System.out.println("NodeList SIZE= " + nodeList.size());
//        //BioPortalElement testElem = nodeList.get(0);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        return mapper.writeValueAsString(nodeList);
//        //throw new UnsupportedOperationException();
//    }
//    
//    @Path("/test")
//    @GET
//    @Produces("application/json")
//    public String getTestJson() throws Exception {  
//        // Create the node factory that gives us nodes.
//        JsonNodeFactory factory = new JsonNodeFactory(false);
// 
//        // create a json factory to write the treenode as json. for the example
//        // we just write to console
//        JsonFactory jsonFactory = new JsonFactory();
//        JsonGenerator generator = jsonFactory.createGenerator(System.out);
//        ObjectMapper mapper = new ObjectMapper();
// 
//        // the root node - album
//        JsonNode album = factory.objectNode();
//        mapper.writeTree(generator, album);
// 
//        return "null";
//    }
//
//}