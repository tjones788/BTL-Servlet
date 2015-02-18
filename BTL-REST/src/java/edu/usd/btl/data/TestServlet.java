/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.usd.btl.ontology.TestFileRead;
import edu.usd.btl.toolbets.ToolBetsEntity;
import edu.usd.btl.toolbets.ToolBetsEntityFacade;
import edu.usd.btl.toolbridge.ToolBridgeEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.usd.edu.btl.betsconverter.BETSV1.BETSV1;
import org.usd.edu.btl.betsconverter.BLDV1.BLDV1;
import org.usd.edu.btl.betsconverter.SeqV1.SeqV1;
import org.usd.edu.btl.betsconverter.GalaxyV1.Tool;
import org.usd.edu.btl.betsconverter.iPlantV1.IplantV1;
import org.usd.edu.btl.converters.BLDConverter;
import org.usd.edu.btl.converters.GalaxyConverter;
import org.usd.edu.btl.converters.IplantConverter;
import org.usd.edu.btl.converters.SeqConverter;

/**
 *
 * @author billconn
 */
@ManagedBean
public class TestServlet extends HttpServlet {

    @EJB
    private ToolBetsEntityFacade toolFacade;

    private ArrayList<File> inputFiles = new ArrayList();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");

            try {
                writeToDb();
            } catch (Exception e) {
                
            }
        }
    }

    /**
     * writeToDb class simply writes to the db whatever files are read in.
     * currently it reads 4 individual files but galaxy and bld converter is
     * broken so the galaxy skips reading and the bld one throws a spectatular
     * error so I skip entering it.
     * @throws java.lang.Exception
     */
    public void writeToDb() throws Exception {
        ObjectMapper mapper = new ObjectMapper(); //create new Jackson Mapper
        System.out.println("Writing to DB....");
        List<File> files = new ArrayList<>();

        //files.add(new File("/data_files/test_galaxy.xml"));
        //files.add(new File("C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq.json"));
        //files.add(new File("C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\test_galaxy.xml"));
        //files.add(new File("C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq.json"));
        //files.add(new File("/Users/Tyler/Documents/GitHub/BTL-REST/BTL-REST/data_files/A5.json"));
        //files.add(new File("/Users/Tyler/Documents/GitHub/BTL-REST/BTL-REST/data_files/test_iplant.json"));
        // files.add(new File("/Users/Tyler/Documents/GitHub/BTL-REST/BTL-REST/data_files/test_BLD.json"));


//==============================================================================
//      SEQANSWERS
//==============================================================================
//        File[] seqFiles = getSeqFiles();
//        for (File seqFile : seqFiles) {
//            inputFiles.add(seqFile); //add all seq files to gloabl File array
//        }
        System.out.println("INPUT FILE SIZE = " + inputFiles.size());
        TestFileRead ontoRead = new TestFileRead();
        //for (int i = 0; i < inputFiles.size(); i++) {
        for (File file : inputFiles){   
            File seqFile = file;
            try {
                BETSV1 tmpBETS = seqToBETS(seqFile);
                ToolBetsEntity dbBETS = new ToolBetsEntity();

                dbBETS.setName(tmpBETS.getName());
                dbBETS.setSummary(tmpBETS.getSummary());
                dbBETS.setVersion(tmpBETS.getVersion());
                ObjectWriter ow = mapper.writer();
                dbBETS.setBets(ow.writeValueAsString(tmpBETS).getBytes());
                List<ToolBridgeEntity> bridges = ontoRead.readOntologyFile(dbBETS);

                dbBETS.setToolBridgeEntityList(bridges);
                toolFacade.create(dbBETS);
                System.out.println("BETS CREATED");
            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                System.out.println("For:"+file.toString());
            }
        }

        inputFiles.clear(); //clear files from inputFiles

//==============================================================================
//      GALAXY
//==============================================================================
//        File[] galaxyFiles = getGalaxyFiles();
//        for (File file : galaxyFiles) {
//            inputFiles.add(file);
//        }
        System.out.println("INPUT FILE SIZE = " + inputFiles.size());
        ontoRead = new TestFileRead();
        
        for (File file : inputFiles){   
            File galaxyFile = file;
            BETSV1 tmpBETS = null;
            try {
                tmpBETS = galaxyToBETS(galaxyFile);
                ToolBetsEntity dbBETS = new ToolBetsEntity();

                dbBETS.setName(tmpBETS.getName());
                dbBETS.setSummary(tmpBETS.getSummary());
                dbBETS.setVersion(tmpBETS.getVersion());
                ObjectWriter ow = mapper.writer();
                dbBETS.setBets(ow.writeValueAsString(tmpBETS).getBytes());

                List<ToolBridgeEntity> bridges = null;
                //while( bridges == null) {
                    bridges = ontoRead.readOntologyFile(dbBETS);
                //}
                dbBETS.setToolBridgeEntityList(bridges);
                toolFacade.create(dbBETS);
                System.out.println("BETS CREATED");
            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                System.out.println("For:"+file.toString());
            }
        }
        inputFiles.clear();
        
//==============================================================================
//      BIOINFORMATICS LINK DIRECTORY
//==============================================================================
//        File[] bldFiles = getBldFiles();
//        for (File file : bldFiles) {
//            inputFiles.add(file);
//        }
        
        System.out.println("INPUT FILE SIZE = " + inputFiles.size());
        ontoRead = new TestFileRead();
        
        for (File file : inputFiles){   
            File bldFile = file;
            BETSV1 tmpBETS = null;
            try {
                tmpBETS = bldToBETS(bldFile);
                ToolBetsEntity dbBETS = new ToolBetsEntity();

                dbBETS.setName(tmpBETS.getName());
                dbBETS.setSummary(tmpBETS.getSummary());
                dbBETS.setVersion(tmpBETS.getVersion());
                ObjectWriter ow = mapper.writer();
                dbBETS.setBets(ow.writeValueAsString(tmpBETS).getBytes());

                List<ToolBridgeEntity> bridges = null;
                //while( bridges == null) {
                    bridges = ontoRead.readOntologyFile(dbBETS);
                //}
                dbBETS.setToolBridgeEntityList(bridges);
                if(dbBETS.getName() != null) {
                    toolFacade.create(dbBETS);
                } else {
                    System.out.println("NULL NAME");
                    break;
                }
                System.out.println("BETS CREATED");
            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                System.out.println("For:"+file.toString());
            }
        }
        inputFiles.clear();

//==============================================================================
//      IPLANT
//==============================================================================
//        File[] iplantFiles = getIplantFiles();
//        for (File file : iplantFiles) {
//            inputFiles.add(file);
//        }
        
        System.out.println("INPUT FILE SIZE = " + inputFiles.size());
        ontoRead = new TestFileRead();
        
        for (File file : inputFiles){   
            File iplantFile = file;
            BETSV1 tmpBETS = null;
            try {
                tmpBETS = iplantToBETS(iplantFile);
                ToolBetsEntity dbBETS = new ToolBetsEntity();

                dbBETS.setName(tmpBETS.getName());
                dbBETS.setSummary(tmpBETS.getSummary());
                dbBETS.setVersion(tmpBETS.getVersion());
                ObjectWriter ow = mapper.writer();
                dbBETS.setBets(ow.writeValueAsString(tmpBETS).getBytes());

                List<ToolBridgeEntity> bridges = null;
                //while( bridges == null) {
                    bridges = ontoRead.readOntologyFile(dbBETS);
                //}
                dbBETS.setToolBridgeEntityList(bridges);
                if(dbBETS.getName() != null) {
                    toolFacade.create(dbBETS);
                } else {
                    System.out.println("NULL NAME");
                    break;
                }
                System.out.println("BETS CREATED");
            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                System.out.println("For:"+file.toString());
            }
        }
        inputFiles.clear();
        
        
//        for (int i = 0; i < 2; i++) {
//            ToolBetsEntity dbBETS = new ToolBetsEntity();
//            BETSV1 tmpBETS = null;
//            if (i == 0) {
//                tmpBETS = seqToBETS(files.get(i));
//            }
//            if (i == 1) {
//                tmpBETS = iplantToBETS(files.get(i));
//            }
////          if (i==2) tmpBETS = galaxyToBETS(files.get(i));
////          if (i==3) tmpBETS = bldToBETS(files.get(i));
//
//            if (tmpBETS != null) {
//                dbBETS.setName(tmpBETS.getName());
//                dbBETS.setSummary(tmpBETS.getSummary());
//                dbBETS.setVersion(tmpBETS.getVersion());
//                dbBETS.setBets(tmpBETS.toString().getBytes());
//                List<ToolBridgeEntity> bridges = null;
//                //MUST GET BRIDGE ENTITES FROM XINGHUA's CODE
//                //they cacade so we don't need to insert them by hand
//                dbBETS.setToolBridgeEntityList(bridges);
//                toolFacade.create(dbBETS);
//            }
//        }
    }

    
    
    /* 
    HELPER METHODS
    */
    public static File[] getSeqFiles() {
        String rootPath = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\seq\\outputs/";
        File f = new File(rootPath);
        File[] files = f.listFiles();
        System.out.println("---Seq files have been created!---");
        return files;
    }
    public static File[] getGalaxyFiles() {
        String rootPath = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\galaxy\\outputs/";
        File f = new File(rootPath);
        File[] files = f.listFiles();
        System.out.println("---galaxy files have been created!---");
        return files;
    }
    
    public static File[] getBldFiles() {
        String rootPath = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\bld\\outputs/";
        File f = new File(rootPath);
        File[] files = f.listFiles();
        System.out.println("---bld files have been created!---");
        return files;
    }
    
    public static File[] getIplantFiles() {
        String rootPath = "C:\\Users\\Bill.Conn\\Documents\\NetBeansProjects\\BTL-REST\\BTL-REST\\data_files\\iPlant\\outputs/";
        File f = new File(rootPath);
        File[] files = f.listFiles();
        System.out.println("---iPlant files have been created!---");
        return files;
    }

//    /*
//     Returns File[] of seq .json files
//     */
//    public static File[] fileConverter(String path) {
//        String filePath = path;
//        File f = new File(path);
//        File[] files = f.listFiles();
//        return files;
//    }
    // <editor-fold defaultstate="collapsed" desc="String converter methods.">
    /**
     * iplantToBets takes an iplant JSON file and returns a BETS object
     *
     * @param iplantJSON File
     * @return bets
     */
    public BETSV1 iplantToBETS(File iplantJSON) {
        String betsJSON;
        BETSV1 bets = null;
        ObjectMapper mapper = new ObjectMapper(); //create new Jackson Mapper
        IplantV1 ipTool;

        try {
            //map input json files to iplant class
            ipTool = mapper.readValue(iplantJSON, IplantV1.class);
            bets = IplantConverter.toBETS(ipTool); //pass the iplant tool spec, convert to bets
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            betsJSON = ow.writeValueAsString(bets); //write Json as String

//            System.out.println("=== IPLANT TO BETS JSON === \n" + betsJSON);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bets;
    }

    /**
     * seqToBETS takes a seq JSON File and returns a BETS object
     *
     * @param seqJSON File
     * @return BETS
     */
    public BETSV1 seqToBETS(File seqJSON) {
        String betsJSON;
        BETSV1 bets = null;
        ObjectMapper mapper = new ObjectMapper(); //create new Jackson Mapper
        SeqV1 seqTool;

        try {
            //map input json files to iplant class
            seqTool = mapper.readValue(seqJSON, SeqV1.class);
            bets = SeqConverter.toBETS(seqTool); //convert the seq tool
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            betsJSON = ow.writeValueAsString(bets); //write Json as String

//            System.out.println("=== SEQ TO BETS JSON === \n" + betsJSON);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bets;
    }

    /**
     * bldToBETS takes a BLD JSON File and returns a BETS object
     *
     * @param bldJSON File
     * @return BETS
     */
    public BETSV1 bldToBETS(File bldJSON) {
        String betsJSON;
        BETSV1 bets = null;
        ObjectMapper mapper = new ObjectMapper(); //create new Jackson Mapper
        BLDV1 bldTool;

        try {
            //map input json files to bld class
            bldTool = mapper.readValue(bldJSON, BLDV1.class);
            bets = BLDConverter.toBETS(bldTool); //convert he bld tool
            //need to do some ugly hacks here, should fix converter
            bets.setName(bets.getDisplay_name());
            bets.setSummary(bets.getDescription());
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            betsJSON = ow.writeValueAsString(bets); //write Json as String

//            System.out.println("=== BLD TO BETS JSON === \n" + betsJSON);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bets;
    }

    /**
     * galaxyToBETS takes a galaxy XML file and returns a BETS object
     *
     * @param galaxyXML File
     * @return BETS
     */
    public BETSV1 galaxyToBETS(File galaxyXML) throws Exception {
        String betsJSON;
        BETSV1 bets = null;
        ObjectMapper mapper = new ObjectMapper(); //create new Jackson Mapper
        Tool galaxyTool; //Galaxy main class is stupidly named 'Tool'

        InputStream infile = null;
        try {
            //map input json files to galaxy class
            JAXBContext jaxbContext = JAXBContext.newInstance(Tool.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller(); //Unmarshalling – Conversion XML content into a Java Object.
            infile = new FileInputStream(galaxyXML);
            galaxyTool = (Tool) unmarshaller.unmarshal(infile);
            bets = GalaxyConverter.toBETS(galaxyTool);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            betsJSON = ow.writeValueAsString(bets); //write Json as String
//            System.out.println("=== GALAXY TO BETS JSON === \n" + betsJSON);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.fillInStackTrace());
        }
        finally {
            try{
                infile.close();
            }catch (Exception e) {
                System.out.println("Terrible things afoot");
            }
        }
        return bets;
    }//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
