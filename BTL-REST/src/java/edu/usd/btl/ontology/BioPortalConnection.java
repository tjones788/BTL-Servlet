/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usd.btl.ontology;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;


/**
 *
 * @author Xinghua
 */
public class BioPortalConnection {
    
    private final String sparqlService = "http://sparql.bioontology.org/sparql";
    private final String apikey = "YourApiKey";
    
    String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                   "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                   "PREFIX oboOther: <http://purl.obolibrary.org/obo/>\n" +
                   "PREFIX oboInOwl: <http://www.geneontology.org/formats/oboInOwl#>\n" +
                   "\n" +
                   "SELECT ?s (str(?label) as ?slabel) ?parent \n" +
                   "FROM <http://bioportal.bioontology.org/ontologies/SWO>\n" +
                   "FROM <http://bioportal.bioontology.org/ontologies/EDAM>\n" +
                   "WHERE {\n" +
                   "?s rdfs:label ?label.\n" +
                   "OPTIONAL {?s rdfs:subClassOf ?parent.}\n" +
                   "}";
    
    private ArrayList<BioPortalElement> elements;
   
    public BioPortalConnection(){
        elements = new ArrayList();
    }
    
    public ArrayList<BioPortalElement> executeQuery() throws Exception{
        //connect to the server
        OntologyQuery ontologyQuery = new OntologyQuery(sparqlService,apikey);
        //run sparql query
        ResultSet results = ontologyQuery.executeQuery(query); 
        
        //store the query result into objects arraylist
        BioPortalElement elementTemp = null;
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution();
            RDFNode ontUri = soln.get("s");
            Literal name = soln.getLiteral("slabel");
            RDFNode parent = soln.get("parent");
            //-------------------------------------------------------------------------------------------------
            if(elementTemp != null){
                //if it's the same Node with different parent
                if(elementTemp.compareElementID(ontUri.toString())){
                    //if parent exists in the list
                    if (!elementTemp.getParent().contains(checkRootNode(parent))) {                       
                        elementTemp.addParent(checkRootNode(parent));
                    }                   
                    //check if it's the last Node
                    if (!results.hasNext()) {
                        elements.add(elementTemp);
                    }               
                }else{
                    elements.add(elementTemp);
                    elementTemp =  new BioPortalElement(name.toString(), ontUri.toString(), checkRootNode(parent));
                }
            }else{
                //the first node in the loop
                elementTemp =  new BioPortalElement(name.toString(), ontUri.toString(), checkRootNode(parent));
            }
        }
        return elements;
    }
    
    //check if the node is root, so it doesn't have parent
    private String checkRootNode(RDFNode object){
        if(object == null){
                return "NULL";
            }else{
                return object.toString();
            }   
    }
        
}
