package parser;


import objects.Class;
import objects.Fields;
import objects.Methods;
import objects.OntologyNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.lang.System.exit;

/**
 * @author samin on 10/27/21
 * @project jparser
 */
public class Parser {

    String jarDirectory;
    HashMap<String, Class> parsed;
    HashMap<String, Boolean> hasSensitive;
    HashMap<String, Methods> relevant_methods;
    HashSet<Integer> set;
    int count;
    ArrayList<String> refinedSynonyms;
    HashMap<String, String> synonyms;
    XMLParser xml;

    public Parser(String dir, XMLParser xml){

        jarDirectory = dir;
        parsed = new HashMap<String, Class>();
        hasSensitive = new HashMap<String, Boolean>();
        relevant_methods = new HashMap<String, Methods>();
        set = new HashSet<Integer>();
        this.xml = xml;
        synonyms = xml.getSynonyms();
        refinedSynonyms = new ArrayList<>();
        for (String s :
                xml.getSynonyms().keySet()) {
            refinedSynonyms.add(Utils.processSynonym(s));

        }

        //Creating a File object for directory
        File file = new File(dir);

        if(!file.exists()){
            System.out.println("Jar file does not exist");
            exit(1);
        }

        iterateFiles(file);
        System.out.println("Processing done");
        //printAllMethods();
        //printMethodDescriptor();
        //generateSSFileForRelevantObjects();
        //generateSSFile();
        //System.out.println(set);




    }

    public int getCount(){
        return count;
    }

    public HashMap<String, Class> getParsed() {
        return parsed;
    }

    public HashMap<String, Methods> getRelevant_methods() {
        return relevant_methods;
    }

    public void printAllPublicMethods(){

        int i =1;
        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
                    ) {
                if(m != null && (m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getClazz().className+";");
                    sb.append(m.getClazz().superClassName+";");
                    sb.append(m.getMethodName()+";");
                    for (String param :
                            m.getParameterNames()) {
                        sb.append(param+";");
                    }
                    System.out.println(sb.toString());
                    i++;
                }

            }
        }

    }

    public String printAllNoParamMethods(){

        int i =1;
        StringBuilder s = new StringBuilder();
        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
            ) {
                if(m != null && m.getParameterCount()==0){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getClazz().className+";");
                    sb.append(m.getClazz().superClassName+";");
                    sb.append(m.getMethodName()+";");
                    for (String param :
                            m.getParameterNames()) {
                        sb.append(param+";");
                    }
                    //System.out.println(sb.toString());
                    sb.append("\n");
                    i++;
                    s.append(sb);
                }

            }
        }
        return s.toString();

    }

    public String printAllNonPublicMethods(){

        int i =1;
        StringBuilder s = new StringBuilder();
        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
            ) {
                if(m != null && !(m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getClazz().className+";");
                    sb.append(m.getClazz().superClassName+";");
                    sb.append(m.getMethodName()+";");
                    for (String param :
                            m.getParameterNames()) {
                        sb.append(param+";");
                    }
                    //System.out.println(sb.toString());
                    sb.append("\n");
                    i++;

                    s.append(sb);
                }

            }
        }

        return s.toString();
    }

    public void printAllMethods(){

        int i =1;
        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
            ) {
                if(m != null){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getClazz().className+";");
                    sb.append(m.getClazz().superClassName+";");
                    sb.append(m.getMethodName()+";");
                    for (String param :
                            m.getParameterNames()) {
                        sb.append(param+";");
                    }
                    System.out.println(sb.toString());
                    i++;
                }

            }
        }

    }

    void printMethodDescriptor(){

        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
            ) {
                if(m != null && (m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getSignature()+" ");
                    String type ="";
                    System.out.println(sb.toString());

                }

            }
        }

    }

    void generateSSFile(){

        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
            ) {
                if(m != null && (m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getSignature()+" ");
                    String type ="";
                    if(m.getAccessFlag() == AccessFlagCodes.public_nonstatic) type ="SENSITIVE_INFO";
                    else type ="SENSITIVE_INFO_STATIC";
                    sb.append(type+" -> _SOURCE_ ");
                    //taint parameters
                    String param = "1";
                    sb.append(param);
                    for (int i=2; i <= m.getParameterCount() ; i++) {
                     sb.append("|"+ String.valueOf(i));
                    }

                    System.out.println(sb.toString());

                }

            }
        }

    }

    void generateSSFileForStrings(){

        for (String name :
                parsed.keySet()) {
            Class c = parsed.get(name);
            for (Methods m  : c.methods
            ) {
                if(m != null && (m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                    StringBuilder sb = new StringBuilder();
                    sb.append(m.getSignature()+" ");
                    String type ="";
                    if(m.getAccessFlag() == AccessFlagCodes.public_nonstatic) type ="SENSITIVE_INFO";
                    else type ="SENSITIVE_INFO_STATIC";
                    sb.append(type+" -> _SOURCE_ ");
                    //taint parameters
                    String param = "1";
                    sb.append(param);
                    for (int i=2; i <= m.getParameterCount() ; i++) {
                        sb.append("|"+ String.valueOf(i));
                    }

                    ArrayList<String> parameters = m.getParameters();
                    if (parameters.contains("Ljava/lang/String")) System.out.println(sb.toString());


                }

            }
        }

    }

    public void generateSSFileForRelevantObjects(){


        try {
            File myObj = new File("temp/SS.txt");
            FileWriter myWriter = new FileWriter(myObj);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            count =0;

            for (String name :
                    parsed.keySet()) {
                Class c = parsed.get(name);


                for (Methods m  : c.methods
                ) {

                    if(m != null && (m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                        //push parameter names

                        StringBuilder sb = new StringBuilder();
                        sb.append(m.getSignature()+" ");
                        String type ="";
                        if(m.getAccessFlag() == AccessFlagCodes.public_nonstatic) type ="SENSITIVE_INFO";
                        else type ="SENSITIVE_INFO_STATIC";
                        sb.append(type+" -> _SOURCE_ ");
                        //taint parameters
                        //ArrayList<Integer> paramSet = new ArrayList<Integer>();
                        StringBuilder param = new StringBuilder();
                        //sb.append(param);
                        for (int i=0; i < m.getParameterCount() ; i++) {
                            if(isRelevantParam(m.getParameters().get(i), m.getParameterNames().get(i))){
                                param.append("|"+ String.valueOf(i+1));
                                //paramSet.add(i+1);
                            }

                        }
                        if(param.length()!=0){
                            param.deleteCharAt(0);
                            sb.append(param);
                            System.out.println(sb.toString());
                            relevant_methods.put(sb.toString(),m);
                            myWriter.write(sb.toString()+'\n');
                            myWriter.flush();
                            count++;


                        }



                    }

                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing in SS file.");
            e.printStackTrace();
        }



    }

    public void generateSensitivityFileForRelevantObjects(){


        try {
            File myObj = new File("temp/Sensitivity.txt");
            FileWriter myWriter = new FileWriter(myObj);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }


            for (String name :
                    parsed.keySet()) {
                Class c = parsed.get(name);
                for (Methods m  : c.methods
                ) {
                    if(m != null && (m.getAccessFlag() == AccessFlagCodes.public_nonstatic || m.getAccessFlag() == AccessFlagCodes.public_static) && m.getParameterCount()>0){

                        //push parameter names

                        StringBuilder sb = new StringBuilder();
                        sb.append(m.getSignature()+",");
                        String type ="";

                        //taint parameters
                        //ArrayList<Integer> paramSet = new ArrayList<Integer>();
                        StringBuilder param = new StringBuilder();
                        //sb.append(param);
                        for (int i=0; i < m.getParameterCount() ; i++) {
                            if(isRelevantParam(m.getParameters().get(i), m.getParameterNames().get(i))){
                                String ontology_node = existsInOntology2(m.getParameterNames().get(i));
                                param.append(","+ String.valueOf(i+1)+":"+ontology_node);
                                //paramSet.add(i+1);
                            }

                        }
                        if(param.length()!=0){
                            param.deleteCharAt(0);
                            sb.append(param);
                            System.out.println(sb.toString());
                            //relevant_methods.put(sb.toString(),m);
                            myWriter.write(sb.toString()+'\n');
                            myWriter.flush();


                        }



                    }

                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing in Sensitivity file.");
            e.printStackTrace();
        }



    }

    public void generateCSVFileForRelevantObjects(String file){


        try {

            File myObj = new File("output/"+file+".csv");
            FileWriter myWriter = new FileWriter(myObj);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }


            for (String s  : relevant_methods.keySet()
            ) {

                Methods m = relevant_methods.get(s);
                if(m.getParameterNames().contains("unassigned")) continue;
                StringBuilder sb = new StringBuilder();
                sb.append(file+",");
                sb.append(m.getClazz().className+",");
                sb.append(m.getMethodName()+",");
                for (int i = 0; i < m.getParameterCount(); i++) {
                    sb.append(m.getParameterNames().get(i)+",");
                }
                sb.append("\n");
                myWriter.write(sb.toString());

            }




            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing in SS.csv file.");
            e.printStackTrace();
        }



    }

    public void compileOutputsIntoOneFile(String file){


        try {


            //Need to have the output.csv file existing
            for (String s  : relevant_methods.keySet()
            ) {

                Methods m = relevant_methods.get(s);
                if(m.getParameterNames().contains("unassigned")) continue;
                StringBuilder sb = new StringBuilder();
                sb.append(file+",");
                sb.append(m.getClazz().className+",");
                sb.append(m.getMethodName()+",");
                for (int i = 0; i < m.getParameterCount(); i++) {
                    sb.append(m.getParameterNames().get(i)+",");
                }
                sb.append("\n");
                Files.write(Paths.get("output/output.csv"), sb.toString().getBytes(), StandardOpenOption.APPEND);


            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing in SS.csv file.");
            e.printStackTrace();
        }



    }


    boolean findRelevanceUsingBFS(String desc, String name){

        boolean isRelevant = false;

        Vector<Vector<String>> queue = new Vector<>();
        Vector<String> element =  new Vector<>();
        element.add(desc);
        element.add(name);
        queue.add(element);

        while(queue.size()!=0){

            Vector<String> obj = queue.get(0);

            //ignore primitive types
            if(!obj.get(0).startsWith("L")) continue;

            if(obj.get(0).startsWith("Ljava/lang/String")) {
                isRelevant = existsInOntology(name);
                if(isRelevant) return isRelevant;
            }
            //add all other variables, super
            else{
                Class c = parsed.get(obj.get(0).substring(1));
                if(c  == null) continue;

                //add members
                ArrayList<Fields> fields  = c.fields;
                for (Fields f :
                        fields) {
                    Vector<String> var =  new Vector<>();
                    element.add(f.getDescriptor());
                    element.add(f.getFieldName());
                    queue.add(element);

                }
                //add super
                String sup = c.superClassName;
                Vector<String> var =  new Vector<>();
                element.add(sup);
                //name doesnt matter if it is an object
                element.add("");
                queue.add(element);

            }

        }

        return false;
    }


    boolean isRelevantParam(String s, String name){


        boolean isRelevant = false;

        //remove leading [
        String obj = s.replace("[","");

        //primitive type
        if(!obj.startsWith("L")) return false;

        //strings
        if(obj.startsWith("Ljava/lang/String")) {
            return existsInOntology(name);
        }

        //java/android library objects
        if(obj.startsWith("Ljava") ||
                obj.startsWith("Landroid") ||
                obj.startsWith("Lorg")){
            return false;
        }

        //other objects could ve relevant. reasoning: sensitive info related objects would be user defined classes

        Class c = parsed.get(obj.substring(1));


        //System.out.println("Class:" +obj);
        //System.out.println();
        //callback classes will not have fields

        if(c  == null) return false;
        else{
            ArrayList<Fields> fields  = c.fields;
            for (Fields f :
                    fields) {
                if(f.getDescriptor().equals("Ljava/lang/String;")){
                    isRelevant= existsInOntology(name);
                }
                //System.out.println(f.getFieldName());
            }
        }


        return isRelevant;


    }


    boolean recursiveSearch(String desc, String name){

        boolean ans = false;

        if(desc.contains("net/authorize/acceptsdk/datamodel/transaction/CardData")){
            System.out.println("bingo");
        }

        //remove leading [

        String obj = desc.replace("[","");


        //strings
        if(obj.startsWith("Ljava/lang/String")) {
            boolean r  =existsInOntology(name);
            return r;
        }

        //primitive type
        if(!obj.startsWith("L")) return false;

        //java/android library objects
        if(obj.startsWith("Ljava") ||
                obj.startsWith("Landroid") ||
                obj.startsWith("Lorg")){
            return false;
        }

        //other objects could ve relevant. reasoning: sensitive info related objects would be user defined classes

        Class c = parsed.get(obj.substring(1));
        if(c == null) c = parsed.get(obj.substring(1).replace(";",""));


        //System.out.println("Class:" +obj);
        //System.out.println();
        //callback classes will not have fields

        if(c  == null) return false;
        else{
            ArrayList<Fields> fields  = c.fields;
            for (Fields f :
                    fields) {

                //String n = .replace(";","");
                ans = ans | recursiveSearch( f.getDescriptor(),f.getFieldName());
                if(ans) return true;
                //System.out.println(f.getFieldName());
            }
            /*
            //objects with 0 fields
            if(!ans){
                String sup = "L"+c.superClassName.replace(";","");
                ans = ans | recursiveSearch(sup, "");
            }
            */

        }


        return ans;

    }




    public boolean existsInOntology(String name){
        if(xml == null) return false;

        if(refinedSynonyms.contains(Utils.processLiteral(name))){
            return true;
        }

        return false;

    }

    public String existsInOntology2(String name){
        if(xml == null) return null;

        for (String s :
                synonyms.keySet()) {
            String refined_key = Utils.processSynonym(s);
            if(refined_key.equals(Utils.processLiteral(name))){

                String value = synonyms.get(s);
                //OntologyNode n = xml.getOntologyMap().get(value);
                //find parent
                return Utils.processSynonym(value);
            }


        }

        return null;
    }


    public  void iterateFiles(File dirPath){
        File filesList[] = dirPath.listFiles();
        for(File file : filesList) {
            if(file == null) continue;
            if(file.isFile() && file.getName().endsWith(".class")) {
                processFiles(file.getPath());
            } else if(file.isDirectory()){
                iterateFiles(file);
            }
        }
    }

    void processFiles(String path){



        InputStream is = null;
        try {
            is = new FileInputStream(new File(path));


            ClassReader cr = new ClassReader(is);
            ClassIterator cp = new ClassIterator();
            cr.accept(cp,0);

            ClassNode classNode=new ClassNode();
            cr.accept(classNode, 0);
            setLocalVariables(cp.getParsedClass(),classNode);
            for (Methods m :
                    cp.getParsedClass().methods) {
                inferMethodParameters(m);
            }


            parsed.put(cp.name,cp.getParsedClass());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found "+path);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("Parsing exception for class "+path);
        }


    }

    void setLocalVariables(Class c, ClassNode cn){

        List<MethodNode> methods=(List<MethodNode>)cn.methods;
        for (Methods m :
                c.methods) {
            for (int i = 0; i < methods.size(); i++) {
                if(methods.get(i).name.equals(m.getMethodName()) && methods.get(i).desc.equals(m.getDescriptor())){
                    m.setLocalVariables(methods.get(i).localVariables);
                    break;
                }
            }
        }

    }

    void inferMethodParameters(Methods m){

        ArrayList<String> parameters = new ArrayList<>();

        //for classes with no local variable data (Interfaces) return all unassigned
        if(m.getLocal_variables()==null){
            for (int i = 0; i < m.getParameters().size(); i++) {
                parameters.add("unassigned");
            }
            m.setParameterNames(parameters);
            return;
        }

        ArrayList<LocalVariableNode> stack = new ArrayList<>();

        for (int i = m.getLocal_variables().size()-1; i >=0 ; i--) {
            stack.add( m.getLocal_variables().get(i));
        }

        for (int j = m.getParameters().size()-1; j >=0 ; j--) {
            String p = m.getParameters().get(j);
            boolean found = false;
            if(p.startsWith("L")|| p.startsWith("[L") || p.startsWith("[[L"))p = p+";";
            for (int i = 0; i < stack.size(); i++) {
                if(p.equals(stack.get(i).desc)){
                    parameters.add(stack.get(i).name);
                    stack.remove(i);
                    found = true;
                    break;
                }
            }
            if(!found)parameters.add("unassigned");
        }

        Collections.reverse(parameters);

        m.setParameterNames(parameters);

    }


    public static  void main (String[] args){

        System.out.println("Hello");
        //Parser p = new Parser("");

    }





}
