/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.usd.btl.toolbets.ToolBetsEntity;
import edu.usd.btl.toolbets.ToolBetsEntityFacade;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.usd.edu.btl.betsconverter.BETSV1.BETSV1;
import org.usd.edu.btl.betsconverter.SeqV1.SeqV1;
import org.usd.edu.btl.converters.SeqConverter;
import edu.usd.btl.data.SeqData;

/**
 *
 * @author Tyler
 */
@ManagedBean
@ApplicationScoped
public class DataEntry implements Serializable{
    @EJB
    private ToolBetsEntityFacade toolFacade;
    
    private ToolBetsEntity toolEntity;
    
    private String betsJSON;
    private Integer randInt;
   // private static BioLinksDir bioDirInst = new BioLinksDir();
    
    /*
    Main Program
    */
    public static void main(String[] args) {
        SeqData test = new SeqData();
        test.readSeq();
        System.out.println("RAN!!!!");
        
//        DataEntry data = new DataEntry();
//        data.writeToDb();
    }
    
    /*
    WRITE TO DATABASE
    */
    public void writeToDb() {
        System.out.println("Writing to DB....");

        randInt = getRandInt(1, 1000); //get a random integer
        ToolBetsEntity locBETS = new ToolBetsEntity(); //create BetsEntity object
        locBETS.setName("Test Name" + randInt);
        locBETS.setSummary("Test Summary");
        locBETS.setVersion("1.0");
        String testJson = convertToBETS();
        locBETS.setBets(testJson.getBytes());  //convert input file to BETS...setBets to entity
        
        try{
        toolFacade.create(locBETS); //create the entity.
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("RANDOM INT === " + randInt);

    }
    
     /**
     * ==================================== METHODS
     * ====================================
     *
     * @return
     */
    public String convertToBETS() {
        System.out.println("******** IN testRunController convertToBETS() *************");
        File input = new File("data_files/seq/outputs/A5.json"); //iPlantInput
        ObjectMapper mapper = new ObjectMapper(); //create new Jackson Mapper
        
        SeqV1 seqTool;
        //BETSV1 betsTool;

        try {
            //map input json files to iplant class
            seqTool = mapper.readValue(input, SeqV1.class);

            //map input json file to Bets class
            //betsTool = mapper.readValue(betsInput, BETSV1.class);
            //call iplantToBets()
            BETSV1 bets = SeqConverter.toBETS(seqTool); //pass the iplant tool spec, convert to bets
            /*===============PRINT JSON TO CONSOLE AND FILES =================== */
            System.out.println("************************************************\n"
                    + "*********PRINTING OUT FIRST CONVERSION************\n"
                    + "************************************************\n");
            //print objects as Json using jackson
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            betsJSON = ow.writeValueAsString(bets); //write Json as String

            System.out.println("=== SEQ TO BETS JSON === \n"
                    + betsJSON);

            //write to files
            //ow.writeValue(new File("bets_Converted_toIplant.json"), betsJson);
            //ow.writeValue(new File("iPlant_OUTPUT.json"), iPlantJson);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return betsJSON; //return BETS JSON as byte[]
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

    public ToolBetsEntityFacade getToolFacade() {
        return toolFacade;
    }

    public void setToolFacade(ToolBetsEntityFacade toolFacade) {
        this.toolFacade = toolFacade;
    }

    public ToolBetsEntity getToolEntity() {
        return toolEntity;
    }

    public void setToolEntity(ToolBetsEntity toolEntity) {
        this.toolEntity = toolEntity;
    }

    public String getBetsJSON() {
        return betsJSON;
    }

    public void setBetsJSON(String betsJSON) {
        this.betsJSON = betsJSON;
    }

    public Integer getRandInt() {
        return randInt;
    }

    public void setRandInt(Integer randInt) {
        this.randInt = randInt;
    }
    
    
}
