package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLImporter implements Table.Importer {
    private BufferedReader in;
    private String tableName;
    private List<String> columnNames;

    private Document xmlDoc;
    private List<Node> rowList;
    private int curIdx = 0;

    public XMLImporter(File xmlFile) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlDoc = builder.parse(xmlFile);
        xmlDoc.getDocumentElement().normalize();
    }

    public XMLImporter(Reader in) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlDoc = builder.parse(new InputSource(in));
        xmlDoc.getDocumentElement().normalize();
    }

    public void startTable() throws IOException {
        Element root = xmlDoc.getDocumentElement();                 // root: <tableName>
        tableName = root.getNodeName();
        NodeList list = root.getChildNodes();
        rowList = new ArrayList<>();                                // <data> ... </data> List
        for(int i = 0; i < list.getLength(); i++){
            if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
                rowList.add(list.item(i));
            }
        }

        Node n = rowList.get(0);                                    // <data> ... </data>
        columnNames = new ArrayList<>();
        list = n.getChildNodes();
        for(int i = 0; i < list.getLength(); i++){
            if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
                columnNames.add(list.item(i).getNodeName());
            }
        }
    }

    public String loadTableName() throws IOException {
        return tableName;
    }

    public int loadWidth() throws IOException {
        return columnNames.size();
    }

    public Iterator loadColumnNames() throws IOException {
        return columnNames.iterator();
    }

    public Iterator loadRow() throws IOException {
        Iterator row = null;

        if(curIdx < rowList.size()){
            Node n = rowList.get(curIdx);
            NodeList list = n.getChildNodes();
            List<String> data = new ArrayList<>();
            for(int i=0; i<list.getLength(); i++) {
                if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    data.add(list.item(i).getTextContent().trim());
                }
            }
            row = data.iterator();
            curIdx++;
        }
        return row;
    }

    public void endTable() throws IOException { }
}