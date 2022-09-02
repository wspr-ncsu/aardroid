package parser;
import objects.OntologyNode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * @author samin on 12/2/21
 * @project aardroid
 */
public class XMLParser {

    static final String NAME = "term";
    static final String NODE = "node";
    static final String CHILD = "child";
    static final String SYNONYM = "synonym";

    String ontologyFile = null;
    String synonymFile = null;
    HashMap<String, OntologyNode> ontology;
    HashMap<String, String> synonyms;

    @SuppressWarnings({ "unchecked", "null" })

    public XMLParser(String ontologyFile, String synonymFile){

        this.ontologyFile = ontologyFile;
        this.synonymFile = synonymFile;

    }

    public void parseOntology() {
        ontology = new HashMap<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(ontologyFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            OntologyNode node = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    String elementName = startElement.getName().getLocalPart();
                    switch (elementName) {
                        case NODE:

                            node = new OntologyNode();
                            // We read the attributes from this tag and add the date
                            // attribute to our object
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    node.setName(attribute.getValue());
                                }
                            }
                            break;
                        case CHILD:
                            event = eventReader.nextEvent();
                            Iterator<Attribute> childAttributes = startElement.getAttributes();
                            while (childAttributes.hasNext()) {
                                Attribute attribute = childAttributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    node.addChild(attribute.getValue());
                                }
                            }
                            break;

                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(NODE)) {
                        ontology.put(node.getName(),node);
                    }
                }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void setAncestors(){

        for (String item : ontology.keySet()
             ) {
            OntologyNode node = ontology.get(item);
            for (String childName:  node.getChild()
                 ) {
                OntologyNode child = ontology.get(childName);
                child.addParent(node);
            }
        }
        System.out.println();

    }

    public void parseSynonyms(){

        synonyms = new HashMap<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(synonymFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            OntologyNode node = null;
            String nodeName = "";

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    String elementName = startElement.getName().getLocalPart();
                    switch (elementName) {
                        case NODE:


                            // We read the attributes from this tag and add the date
                            // attribute to our object
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    node = ontology.get(attribute.getValue());

                                    if(node==null) {
                                        //System.out.println(attribute.getValue()+" not present in ontology");
                                        nodeName = attribute.getValue();
                                    }
                                }
                            }
                            break;
                        case SYNONYM:
                            event = eventReader.nextEvent();
                            Iterator<Attribute> childAttributes = startElement.getAttributes();
                            while (childAttributes.hasNext()) {
                                Attribute attribute = childAttributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    if(synonyms.containsKey(attribute.getValue())){
                                        //System.out.println("Exists "+attribute.getValue());
                                    }
                                    if(node !=null){
                                        node.addSynonym(attribute.getValue());
                                        synonyms.put(attribute.getValue(),node.getName());
                                    }
                                    else  synonyms.put(attribute.getValue(),nodeName);

                                }
                            }
                            break;

                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(NODE)) {
                        //synonyms.put(node.getName(),node);
                    }
                }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }


    }

    public HashMap<String, OntologyNode> getOntologyMap() {
        return ontology;
    }

    public HashMap<String, String> getSynonyms() {
        return synonyms;
    }
}



