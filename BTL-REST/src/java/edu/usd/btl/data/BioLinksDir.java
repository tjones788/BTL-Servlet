package edu.usd.btl.data;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Xinghua
 */
public class BioLinksDir {

    public BioLinksDir() {
    }
    
    public static void main(String[] args) throws IOException {
        BioLinksDir instance = new BioLinksDir();
//        String inputFile = "C:\\Users\\Xinghua\\Desktop\\dna.json";
//        String outputFolderDir = "C:\\Users\\Xinghua\\Desktop\\dna";
//        instance.SplitJsonFile(inputFile, outputFolderDir);
        if ((args.length == 1)&&(args[0].equals("-help"))) {
            System.out.println("help information: \n  suppose you have an input json file: C:\\Work\\myfile.json \n"
                    + "the output folder is: C:\\myfolder \n"
                    + "so, you can type:  -o C:\\Work\\myfile.json C:\\myfolder");
        }else if((args.length == 3)&&(args[0].equals("-o"))){
            instance.SplitJsonFile(args[1],args[2]);
        }else {
            System.out.println("wrong paramenter number, type -help");
        }
        
//        String a = instance.FormatJsonString(ReadFile(inputFile));
    }

    
    public void SplitJsonFile(String inputFile, String outputFolderDir){
        boolean no_next_node = false;      
        int startFileNameIndex;
        int endFileNameIndex;
        int hasNode;
        int endNode;
        int nextNode;
        String write2File;
        
        String inputContext = BioLinksDir.ReadFile(inputFile);
        //clean the outside Brace       
        inputContext = inputContext.substring(1, inputContext.length() - 1);
        //begin to find the first node
        while (true) {            
           //empty file
           hasNode = inputContext.indexOf("\"type\"");
           if(hasNode != -1){
               //get file name
               startFileNameIndex = inputContext.indexOf("\"") + 1;
               endFileNameIndex  =inputContext.indexOf("\"", startFileNameIndex);
               String fileName = inputContext.substring(startFileNameIndex, endFileNameIndex);
               //find end of the node           
               nextNode = inputContext.indexOf("\"type\"", hasNode + "\"type\"".length());
               if (nextNode == -1) {
                   no_next_node = true;
                   endNode = inputContext.length() - 1;
               } else {
                   endNode = inputContext.lastIndexOf(",", nextNode);
               }
               //write file
               String jsonFileName = outputFolderDir + "\\" + fileName + ".json";
               write2File = "{" + inputContext.substring(0, endNode) + "}";
               inputContext = inputContext.substring(endNode + 1);
               outputJsonFile(FormatJsonString(write2File), jsonFileName);
               //no more node
               if (no_next_node) {
                   break;
               }
           } else {
               break;
           }
        }
    }
    
    public static String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
            laststr += tempString;
        }
        reader.close();
        }catch(IOException e){
            System.out.println(e.toString());
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
        return laststr;
    }

    public String FormatJsonString(String inputContext){
        int hasNode = inputContext.indexOf("\"type\"");
        if (hasNode != -1) {
            return inputContext.substring(hasNode - 1,inputContext.length() - 1);
        }else{
            return null;
        }
    } 
    
    public void outputJsonFile(String outputContext, String outputFile){
        FileWriter writer;
        try {
                   writer = new FileWriter(outputFile);
                   writer.write(outputContext);
                   writer.close();
               }catch(IOException e){
                   System.out.println(e.toString());
               }
    }
}
    