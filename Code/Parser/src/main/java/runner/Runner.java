package runner;

import decompiler.JDCLI;
import objects.Class;
import objects.Methods;
import objects.OntologyNode;
import parser.Parser;
import parser.Utils;
import parser.XMLParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;
import static java.lang.System.setOut;

/**
 * @author samin on 11/14/21
 * @project aardroid
 */
public class Runner {

    static Parser p;
    static HashMap<String, Class> parsed;
    static HashMap<String, Methods> methods;
    static  XMLParser xml;
    static String filename;

    static final String ontologyFileDirectory = "resource/ontology.xml";
    static final String synonymFileDirectory = "resource/synonyms.xml";


    public static  void main (String[] args){

        try{

            System.out.println("Initializing AARDroid. . .");

            checkArguments(args);
            initDirectories();
            String aarFileDir = args[0];
            checkFileExists(aarFileDir);
            copyFileToDirectory(aarFileDir);
            String aarName = getFileName(aarFileDir);
            filename = aarName;
            unzipFile(aarName);
            extractJar();
            parseOntologies();
            p = runASMParser();
            methods  = p.getRelevant_methods();
            //outputRelevantMethods();
            //p.generateCSVFileForRelevantObjects(aarName.substring(0,aarName.length()-4));
            buildDummyActivities();


        }catch (Exception e){
            System.out.println("Operation aborting. . .");
            System.out.println(e);
        }


    }

    static void initDirectories() throws Exception {

        System.out.println("Removing old temporary directories. . . ");

        Runtime.getRuntime().exec("rm -r temp");

        System.out.println("Creating new temporary directories. . . ");

        TimeUnit.SECONDS.sleep(2);
        Runtime.getRuntime().exec("mkdir temp");

    }

    static void checkArguments(String[] args){

        if(args.length!=1){
            System.out.println("Wrong arguments/ No arguments passed System terminating");
            System.exit(0);
        }
    }

    static void checkFileExists(String aarFileDir){

        System.out.println("Checking if the .aar file exists . . .");

        File f = new File(aarFileDir);
        if(!f.exists()){
            System.out.println("AAR file does not exist");
            exit(1);
        }

        System.out.println("File found in directory "+ aarFileDir);

    }

    static String getFileName(String aarFileDir){

        String[] strs = aarFileDir.split("/");
        String aarName = strs[strs.length-1];
        return aarName;
    }

    static void copyFileToDirectory(String aarFileDir) throws Exception{

        System.out.println("Copying .aar file into temp directory . . . ");
        TimeUnit.SECONDS.sleep(2);
        Runtime.getRuntime().exec("cp "+aarFileDir+" temp");
        System.out.println("File copy successful");

    }

    static void unzipFile(String aarName) throws Exception{

        System.out.println("Unzipping .aar file . . . ");
        TimeUnit.SECONDS.sleep(2);
        Runtime.getRuntime().exec("unzip temp/"+aarName+" -d temp/unzip");
        System.out.println("Unzipping successful.");

    }

    static void extractJar() throws Exception{
        System.out.println("Extracting classes.jar into temp/jar . . .");
        TimeUnit.SECONDS.sleep(10);
        Runtime.getRuntime().exec("unzip temp/unzip/classes.jar -d temp/jar");
        System.out.println("Extraction successful. Classes extracted to temp/jar");
    }

    static void parseOntologies(){

        System.out.println("Parsing ontology files. . .");
        xml = new XMLParser(ontologyFileDirectory, synonymFileDirectory);
        xml.parseOntology();
        xml.setAncestors();
        xml.parseSynonyms();
        HashMap<String, OntologyNode> ontologyMap = xml.getOntologyMap();
        for (String n :
                ontologyMap.keySet()) {
            OntologyNode node = ontologyMap.get(n);
            //System.out.println(node.getName());

        }
        HashMap<String,String> synonyms = xml.getSynonyms();
        for (String s :
                synonyms.keySet()) {
            //System.out.println(s);
        }
        System.out.println("Ontology parsing complete. . .");
        /*
        try {
            matchParameterNameWithSynonyms("nonmatchingterms.txt",xml.getSynonyms());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }

    static Parser runASMParser() throws Exception{

        System.out.println("Initializing ASM Parser. . .");

        TimeUnit.SECONDS.sleep(10);

        Parser p = new Parser("temp/jar",xml);

        System.out.println("Generating SS file. . .");

        //printing all methods

        //p.printAllMethods();

        //printing all non public methods
        String nonPub = p.printAllNonPublicMethods();
        writeOnFile("substantiate/non-public/"+filename.substring(0,filename.length()-4), nonPub);

        //printing all non public methods
        String noParam =p.printAllNoParamMethods();
        writeOnFile("substantiate/no-param/"+filename.substring(0,filename.length()-4), noParam);

        //printing all pubic methods
        //p.printAllPublicMethods();

        //generate SS

        p.generateSSFileForRelevantObjects();
        p.generateSensitivityFileForRelevantObjects();
        parsed  = p.getParsed();
        System.out.println("SS file generated in temp/SS.txt ");


        return p;

    }

    static void writeOnFile(String name, String content){


        PrintWriter writer;

        try {
            writer = new PrintWriter(name+ ".txt", "UTF-8");
            writer.println(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void buildDummyActivities() throws InterruptedException, IOException {


        //build dummyActivities
        String activityDir = "app/src/main/java/com/example/app";
        String manifestDir = "app/src/main/AndroidManifest.xml";

        System.out.println("Removing old files from activity directory: "+activityDir);

        TimeUnit.SECONDS.sleep(2);
        Runtime.getRuntime().exec("rm -r "+activityDir);
        TimeUnit.SECONDS.sleep(2);

        System.out.println("Creating new activity directory: "+activityDir);
        Runtime.getRuntime().exec("mkdir "+activityDir);
        TimeUnit.SECONDS.sleep(2);

        System.out.println("Copying activity template in activity directory. . ");
        Runtime.getRuntime().exec("cp template/MainActivity.java "+activityDir);

        TimeUnit.SECONDS.sleep(2);
        System.out.println("Removing old manifest file . . .");
        Runtime.getRuntime().exec("rm  "+manifestDir);

        TimeUnit.SECONDS.sleep(2);



        BufferedReader br = new BufferedReader(new FileReader("template/AndroidManifest.xml"));
        StringBuilder sb = new StringBuilder();
        File manifest = new File(manifestDir);
        FileWriter fw = new FileWriter(manifest);
        String line;
        while ((line = br.readLine()) != null) {
            // process the line.
            sb.append(line+"\n");
            fw.write(line+"\n");
            if(line.contains("</activity>")){
                for (int i = 0; i < p.getCount(); i++) {
                    String className  = "DummyActivity"+String.valueOf(i+1);
                    String manifestInsert = "        <activity android:name=\"."+className+"\"></activity>\n";
                    sb.append(manifestInsert);
                    fw.write(manifestInsert+"\n");


                }
            }
        }
        //System.out.println(sb.toString());


        fw.close();
        br.close();

        System.out.println("Creating new manifest file with "+ p.getCount()+" activities . . ");

        BufferedReader br2 = new BufferedReader(new FileReader("template/DummyActivity.java"));
        StringBuilder sb2 = new StringBuilder();

        while ((line = br2.readLine()) != null) {

            sb2.append(line+"\n");

        }

        String content = sb2.toString();

        System.out.println("Manifest generated in "+manifestDir+" directory. . . ");

        System.out.println("Creating dummy activities . . ");

        for (int i = 0; i < p.getCount(); i++) {
            String className  = "DummyActivity"+String.valueOf(i+1);
            String replaced = content.replace("DummyActivity", className);
            File fl = new File(activityDir+"/"+className+".java");
            FileWriter fileWriter = new FileWriter(fl);
            fileWriter.write(replaced);
            fileWriter.close();


        }

        br2.close();

        System.out.println("Dummy  activities created . . ");

    }


    static void outputRelevantMethods(){

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Printing relevant methods. . .");
        for (String s :
                methods.keySet()) {
            Methods m = methods.get(s);
            StringBuilder sb = new StringBuilder();
            sb.append(m.getClazz().className+":");
            sb.append(m.getMethodName()+" (");
            for (int i = 0; i < m.getParameterCount(); i++) {
                sb.append(m.getParameters().get(i)+" ");
                sb.append(m.getParameterNames().get(i));
                sb.append(", ");

            }
            sb.append(") ");
            sb.append(m.getReturnType());
            System.out.println(sb.toString());
        }

    }

    static void outputRelevantTextAsCSV(){


        for (String s :
                methods.keySet()) {
            Methods m = methods.get(s);
            StringBuilder sb = new StringBuilder();
            sb.append(m.getClazz().className+":");
            sb.append(m.getMethodName()+" (");
            for (int i = 0; i < m.getParameterCount(); i++) {
                sb.append(m.getParameters().get(i)+" ");
                sb.append(m.getParameterNames().get(i));
                sb.append(", ");

            }
            sb.append(") ");
            sb.append(m.getReturnType());
            System.out.println(sb.toString());
        }

    }

    static void matchParameterNameWithSynonyms(String  paramFile,  HashMap<String, String> synonnyms) throws IOException {

        ArrayList<String> refinedSynonyms = new ArrayList<>();
        for (String s :
                synonnyms.keySet()) {
            refinedSynonyms.add(Utils.processSynonym(s));
        }



        BufferedReader br = new BufferedReader(new FileReader(paramFile));
        String line;
        while ((line = br.readLine()) != null) {

            //System.out.println(Utils.processLiteral(line));
            if(!refinedSynonyms.contains(Utils.processLiteral(line))){
                System.out.println(Utils.processLiteral(line));
            }
        }

        System.out.println("ss");


    }



}
