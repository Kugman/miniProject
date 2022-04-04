package renderer;

import geometries.Geometries;
import geometries.Geometry;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class XMLReader {
    private String fileName;
    private DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document document;

    public XMLReader(String fileName) {
        this.fileName = fileName;
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }
    }

//    public void readFile(){
//        InputStream is = readXmlFileIntoInputStream(fileName);
//        try{
//            document = db.parse(is);
//            Class classDefinition = Class.forName(document.getDocumentElement().getNodeName());
//            Geometry g = classDefinition.newInstance();
//
//
//
//            objectTree = new Tree<Geometry>(g);
//        }catch (SAXException | IOException | ClassNotFoundException e){
//            e.printStackTrace();
//        }
//    }

    private InputStream readXmlFileIntoInputStream(String fileName) {
        return XMLReader.class.getClassLoader().getResourceAsStream(fileName);
    }
}
