package com.holub.database;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import static org.junit.Assert.*;

public class XMLImporterTest {
    @Test
    public void XML() throws IOException, ParserConfigurationException, SAXException {

        Reader in = new FileReader("c:/dp2023/people.xml");
        XMLImporter importer = new XMLImporter(in);
        importer.startTable();

        String tableName = importer.loadTableName();
        assertEquals(tableName, "people");


        int width = importer.loadWidth();
        assertEquals(width, 3);

        Iterator columns = importer.loadColumnNames();
        String[] columnNames = new String[width];
        for (int i = 0; columns.hasNext();){
            columnNames[i++] = (String) columns.next();
        }
        assertEquals(columnNames[0], "last");
        assertEquals(columnNames[1], "first");
        assertEquals(columnNames[2], "addrId");

        int idx = 0;
        String[][] rowValues = new String[2][width];
        while ((columns = importer.loadRow()) != null) {
            for (int i = 0; columns.hasNext();)
                rowValues[idx][i++] = (String) columns.next();
            idx++;
        }

        assertEquals(rowValues[0][0], "Holub");
        assertEquals(rowValues[0][1], "Allen");
        assertEquals(rowValues[0][2], "1");
        assertEquals(rowValues[1][0], "Flintstone");
        assertEquals(rowValues[1][1], "Wilma");
        assertEquals(rowValues[1][2], "2");

        importer.endTable();
    }
}