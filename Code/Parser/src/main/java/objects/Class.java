package objects;

import org.objectweb.asm.Attribute;

import java.util.ArrayList;

/**
 * @author samin on 10/28/21
 * @project jparser
 */
public class Class {

    public int classAccessFlag;
    public String className;
    public String superClassName;
    public String signature;
    public String[] interfaces;
    public String sourceFile;
    public String outterClassName;
    public String outterClassDesc;
    public String outterClassOwner;
    public ArrayList<Attribute> attributes;
    public ArrayList<Fields> fields;
    public ArrayList<Methods> methods;

    public Class(){
        attributes = new ArrayList<Attribute>();
        fields = new ArrayList<Fields>();
        methods = new ArrayList<Methods>();
    }



}
