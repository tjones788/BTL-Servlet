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
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Xinghua
 */
public class OntologyFileRead {
    
    private ArrayList<BioPortalElement> elements;
    
    public OntologyFileRead(){
        elements = new ArrayList();
        
    }
        
    public ArrayList<BioPortalElement> readFile(String ontFile) {
        BioPortalElement elementTemp;
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            ontModel.read(new FileInputStream(ontFile), "RDF/XML");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        
        for (ExtendedIterator<? extends OntResource> instances = ontModel.listClasses(); instances.hasNext(); ) {
            OntResource ontInstance = instances.next();
            
            // find out the resources that link to the instance
            for (StmtIterator stmts = ontModel.listStatements( null, null, ontInstance ); stmts.hasNext(); ) {
                Individual ind = stmts.next().getSubject().as( Individual.class );
                if (ind.getURI()==null) {
                    continue;
                }
                elementTemp = new BioPortalElement();
                elementTemp.setURI(ind.getURI());
                // show the properties of this individual
                for (StmtIterator j = ind.listProperties(); j.hasNext(); ) {
                    Statement s = j.next();
                    if (s.getPredicate().getLocalName().contains("label")) {
                        elementTemp.setName(s.getLiteral().getLexicalForm());   
                    }
                    if (s.getPredicate().getLocalName().contains("inSubset")) {
                        elementTemp.addParent(s.getLiteral().getLexicalForm());
                    }                                      
                }
                elements.add(elementTemp);
            }
            
        }
        //remove existence
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = elements.size() - 1; j > i; j--) {
                if (elements.get(j).getURI().equals(elements.get(i).getURI())) {
                    elements.remove(j);
                }
            }
        }
        return elements;
    }
         
 
}
