package edu.usd.btl.data;

import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Xinghua
 */
public class BETSFiles {

    public static void main(String[] args) {
        String rootPath = "C:\\Users\\tyler.jones\\Documents\\GitHub\\BTL-REST\\BTL-REST\\data_files\\";
        String folderName = "seq";
        fileConveter(rootPath, folderName);
    }

    public static void fileConveter(String rootPath, String folderName) {

        String filePath = rootPath + folderName + "\\outputs";
        File f = null;
        f = new File(filePath);
        File[] files = f.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (folderName.equals("galaxy")) {
                //new RunGalaxyTest(filePath + "\\" + fileName);
                //use bets converter classes for RunGalaxyTest
                
            } else if (folderName.equals("seq")) {
                //new RunSeqTest(filePath + "\\" + fileName);
                //use bets converter classes for RunSeqTest
                
            }
        }
    }

}


