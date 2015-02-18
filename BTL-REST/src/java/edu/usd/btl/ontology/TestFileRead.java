/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usd.btl.ontology;

import edu.usd.btl.toolbets.ToolBetsEntity;
import edu.usd.btl.toolbridge.ToolBridgeEntity;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Tyler.Jones
 */
public class TestFileRead {
  
    
    public ArrayList readOntologyFile(ToolBetsEntity tool) {
        //read owl ontology file
        OntologyFileRead ontFileRead = new OntologyFileRead();
        ArrayList<BioPortalElement> nodeList = ontFileRead.readFile("C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\ontology_files\\EDAM_1.3.owl");
        ArrayList<ToolBridgeEntity> randomNodeList = new ArrayList<>();
//print
        //get 5 random nodes
        for(int i=0; i < 5; i++){
            BioPortalElement node = nodeList.get(getRandInt(1,4));
            ToolBridgeEntity bridgeEntity = new ToolBridgeEntity();
            bridgeEntity.setOntologyId(node.getURI());
            bridgeEntity.setToolId(tool);
            //System.out.println("Bridge OntologyID =" + bridgeEntity.getOntologyId());
            randomNodeList.add(bridgeEntity);
        }

        return randomNodeList;
    }
        public Integer getRandInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
