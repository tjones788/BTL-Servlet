/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.ontology;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Xinghua
 */
public class OntSearch {

    private final String sparqlService = "http://sparql.bioontology.org/sparql";
    private final String apikey = "YourApiKey";

    private final String queryPreFix = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "PREFIX oboOther: <http://purl.obolibrary.org/obo/>\n"
            + "PREFIX oboInOwl: <http://www.geneontology.org/formats/oboInOwl#>\n"
            + "\n"
            + "SELECT (str(?p) as ?plabel)  ?o \n";

    private final String queryEDAM = "FROM <http://bioportal.bioontology.org/ontologies/EDAM>\n"
            + "WHERE {<";

    private final String querySWO = "FROM <http://bioportal.bioontology.org/ontologies/SWO>\n"
            + "WHERE {<";

    private final String querySuffix = "> ?p ?o.\n"
            + "}";

    public OntSearch() {

    }

    public String searchElementByURI(String URI) throws Exception {
        OntologyQuery ontologyQuery = new OntologyQuery(sparqlService, apikey);
        String query, response;
        ResultSet results;
        response = "------------------------------------EDAM-------------------------------------\n";
        query = queryPreFix + queryEDAM + URI + querySuffix;
        results = ontologyQuery.executeQuery(query);
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Literal label = soln.getLiteral("plabel");
            RDFNode value = soln.get("o");
            response += suffixLabel(label.toString()) + "  :  " + value.toString() + "\n";
        }

        response += "------------------------------------SWO--------------------------------------\n";
        query = queryPreFix + querySWO + URI + querySuffix;
        results = ontologyQuery.executeQuery(query);
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Literal label = soln.getLiteral("plabel");
            RDFNode value = soln.get("o");
            response += suffixLabel(label.toString()) + "  :  " + value.toString() + "\n";
        }
        return response;
    }

    public String suffixLabel(String str) throws IOException {
        String temp = str;
        int index;
        if (str.contains("#")) {
            index = str.indexOf("#");
            return str.substring(index + 1);
        }
        while (true) {
            if (temp.contains("/")) {
                index = temp.indexOf("/");
                temp = temp.substring(index + 1);
                continue;
            }
            return temp;
        }
    }

    public String searchNodeFromFile(String URI, String ontFile) {
        String response;
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            ontModel.read(new FileInputStream(ontFile), "RDF/XML");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }

        for (ExtendedIterator<? extends OntResource> instances = ontModel.listClasses(); instances.hasNext();) {
            OntResource ontInstance = instances.next();

            // find out the resources that link to the instance
            for (StmtIterator stmts = ontModel.listStatements(null, null, ontInstance); stmts.hasNext();) {
                Individual ind = stmts.next().getSubject().as(Individual.class);
                if (URI.equals(ind.getURI())) {
                    response = "";
                    for (StmtIterator j = ind.listProperties(); j.hasNext();) {
                        Statement s = j.next();
                        response += s.getPredicate().getLocalName() + " : ";
                        if (s.getObject().isLiteral()) {
                            response += s.getLiteral().getLexicalForm() + "\n";
                        } else {
                            response += s.getObject() + "\n";
                        }
                    }
                    return response;
                }
            }

        }
        return null;

    }

    public String findAllTopics() throws Exception {
        OntologyQuery ontologyQuery = new OntologyQuery(sparqlService, apikey);
        String query, response;
        
        
        String topicQuery =   "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "PREFIX oboOther: <http://purl.obolibrary.org/obo/>\n"
            + "PREFIX oboInOwl: <http://www.geneontology.org/formats/oboInOwl#>\n"
                + "PREFIX edamOnto: <http://bioportal.bioontology.org/ontologies/EDAM>\n"
            + "\n"
            + "SELECT *"
            + "WHERE {?label edamOnto:label 'Yeast'"
            + "}";

    response  = "------------------------------------EDAM-------------------------------------\n";
    ResultSet results = ontologyQuery.executeQuery(topicQuery);


    return results.toString();
     }
     
}
