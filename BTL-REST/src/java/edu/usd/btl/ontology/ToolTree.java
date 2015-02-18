/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.ontology;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.usd.btl.toolTree.OntoToTree;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class ToolTree {

    public static void main(String[] args) throws Exception {
        try {
            //File ontoInput = new File(".\\ontology_files\\EDAM_1.3.owl");
            ObjectMapper mapper = new ObjectMapper();
            OntologyFileRead ontFileRead = new OntologyFileRead();
            ArrayList<edu.usd.btl.ontology.BioPortalElement> nodeList = ontFileRead.readFile("C:\\Users\\Tyler\\Desktop\\GitHub\\BTL-REST\\BTL-REST\\src\\ontology_files\\EDAM_1.3.owl");
            //write nodelist to JSON string
            ObjectWriter treeWriter = mapper.writer().withDefaultPrettyPrinter();
            String edamJSON = mapper.writeValueAsString(nodeList);

            JsonNode rootNode = mapper.readValue(edamJSON, JsonNode.class);
            System.out.println("IsNull" + rootNode.toString());
            
            OntSearch ontSearch = new OntSearch();
            System.out.println(nodeList.get(0).getURI());
            String result  =  ontSearch.searchElementByURI("http://edamontology.org/topic_2817");
            System.out.println("RESULT = " + result);
            
            String topicSearchResult = ontSearch.findAllTopics();
            System.out.println("Topics Result = " + topicSearchResult);
            
            File ontFile = new File(".\\ontology_files\\EDAM_1.3.owl");
            String searchFromFileResult = ontSearch.searchNodeFromFile("http://edamontology.org/topic_2817", ".\\ontology_files\\EDAM_1.3.owl");
            System.out.println("File Response = " + searchFromFileResult.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Hashmap stuff
//        OntologyFileRead ontFileRead = new OntologyFileRead();
//        ArrayList<edu.usd.btl.ontology.BioPortalElement> nodeList = ontFileRead.readFile(".\\ontology_files\\EDAM_1.3.owl");
//        
//        HashMap hm = new HashMap();
//        
//        //find topics
//        ArrayList<OntologyNode> ontoTopicList = new ArrayList();
//        
//        for(BioPortalElement node : nodeList){
//            //System.out.println("****" + node.getURI());
//            hm.put(node.getURI(), node.getName());
//        }
//        
//        Set set = hm.entrySet();
//        Iterator i = set.iterator();
//        while(i.hasNext()){
//            Map.Entry me = (Map.Entry)i.next();
//            System.out.println(me.getKey() + ": " + me.getValue());
//        }
//        System.out.println("HashMap Size = " + hm.size());
    }
}
