/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usd.btl.ontology;

/**
 *
 * @author Tyler
 */
public class OntologyNode {
    
    private String name;
    private String[] parent;
    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getParent() {
        return parent;
    }

    public void setParent(String[] parent) {
        this.parent = parent;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    
    
}
