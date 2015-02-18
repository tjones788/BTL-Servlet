/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usd.btl.ontology;

import java.util.ArrayList;

/**
 *
 * @author Xinghua
 */
public class BioPortalElement {
    private String name;
    private String URI;
    private ArrayList<String> parents = new ArrayList();
    
    public BioPortalElement(String name, String URI, String parents){
        this.name = name;
        this.URI = URI;
        this.parents.add(parents);
    }

    public BioPortalElement() {
       
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getURI(){
        return this.URI;
    }
    
    public void setURI(String URI){
        this.URI = URI;
    }
    
    public ArrayList getParent(){
        return this.parents;
    }
    
    public boolean compareElementID(String objectURI){
        return this.URI.equals(objectURI);
    }
    
    public void addParent(String parent){
        this.parents.add(parent);
    }

}
