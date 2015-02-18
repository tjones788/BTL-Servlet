/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Xinghua
 */
public class SeqData {
    //-----
    public static void main(String[] args) {
        SeqData me = new SeqData();
        me.readSeq();
    }
    public void readSeq() {
        SeqData instance = new SeqData();
        String inputTable1 = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq\\AuthHits.txt";
        String inputTable2 = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq\\CitedRefs.txt";
        String inputFile = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq\\result.json";
        String outputFolderDir = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq\\outputs";
        ArrayList table_AuthHits = instance.readTableFile(inputTable1);
        ArrayList table_CitedRefs = instance.readTableFile(inputTable2);
        instance.SplitJsonFile(inputFile, table_AuthHits, table_CitedRefs, outputFolderDir);
        System.out.println("test information");
    }
    
    public SeqData(){        
    }
    
    public void SplitJsonFile(String inputJsonFile, ArrayList table_AuthHits, ArrayList table_CitedRefs, String outputFolderDir){
        boolean no_next_node = false; 
        int hasNode;
        int startFileNameIndex;
        int endFileNameIndex;
        int nextNode;
        int endNode;
        String write2File;
        String inputContext = readJsonFile(inputJsonFile);
        while (true) {            
            hasNode = inputContext.indexOf("\"label\"");
            if (hasNode != -1) {
                startFileNameIndex = inputContext.indexOf("\"", hasNode + "\"label\"".length()) + 1;
                endFileNameIndex = inputContext.indexOf("\"", startFileNameIndex);
                String fileName = inputContext.substring(startFileNameIndex, endFileNameIndex);
                //find end of the node           
                nextNode = inputContext.indexOf("\"label\"", hasNode + "\"label\"".length());
                if (nextNode == -1) {
                   no_next_node = true;
                   endNode = inputContext.length() - 2;
                } else {
                   endNode = inputContext.lastIndexOf(",", nextNode);
                }
                String jsonFileName = outputFolderDir + "\\" + fileName + ".json";
                write2File = "{" + addTableContent(table_AuthHits, table_CitedRefs, fileName) + inputContext.substring(hasNode, endNode);
                inputContext = inputContext.substring(endNode + 1);
                outputJsonFile(write2File, jsonFileName);
                //no more node
                if (no_next_node) {
                    break;
                }
            }else{
                break;
            }
        }
    }
    
    public String addTableContent(ArrayList table_AuthHits, ArrayList table_CitedRefs, String label){
        String content = "";
        String[] node;
        if (!table_AuthHits.isEmpty()) {
            for (int i = 0; i < table_AuthHits.size(); i++) {
                node = (String[])table_AuthHits.get(i);
                if (label.equals(node[0])) {
                    String node2 = stripCommas(node[2]);
                    String node3 = stripCommas(node[3]);
                    content += "\"author\": " + "\"" + node2 + "\"," + "\"hits\": " + "\"" + node3 + "\",";
                }
            }
        }
        if (!table_CitedRefs.isEmpty()) {
            for (int i = 0; i < table_CitedRefs.size(); i++) {
                node = (String[])table_CitedRefs.get(i);
                if (label.equals(node[0])) {
                    String node2 = stripCommas(node[2]);
                    String node3 = stripCommas(node[3]);
                    content += "\"cited\": " + "\"" + node2 + "\"," + "\"refs\": " + "\"" + node3 + "\",";
                }
            }
        }
        return content;
    }
    
    public ArrayList readTableFile(String Path){
        ArrayList tableArrayList = new ArrayList();
        String[] toolInfo;
        String tempString;
        BufferedReader reader = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            while((tempString = reader.readLine()) != null){
                toolInfo = tempString.split("\\t");
                if (toolInfo.length == 4) {
                    tableArrayList.add(toolInfo);
                }
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
        return tableArrayList;
    }
    
    public String readJsonFile(String Path){
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
    
    public String stripCommas(String stringToStrip) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i<stringToStrip.length(); i++) {
            String substring = stringToStrip.substring(i,i+1);
            if(Character.isDigit(substring.charAt(0))) {
                newString.append(substring);
            }
        }
        return newString.toString();
    }
    
}