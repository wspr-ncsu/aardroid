package objects;

import java.util.ArrayList;

/**
 * @author samin on 12/2/21
 * @project aardroid
 */
public class OntologyNode {

    String name;
    ArrayList<String> child;
    ArrayList<OntologyNode> parent;
    ArrayList<String> synonyms;

    public OntologyNode(){
        child = new ArrayList<>();
        parent = new ArrayList<>();
        synonyms = new ArrayList<>();
    }

    public void setName(String name){
        this.name = name;
    }

    public void addChild(String child){

        this.child.add(child);

    }

    public void addParent(OntologyNode parent){

        this.parent.add(parent);

    }

    public void addSynonym(String synonym) {
        this.synonyms.add(synonym);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getChild() {
        return child;
    }

    public ArrayList<OntologyNode> getParent() {
        return parent;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }
}
