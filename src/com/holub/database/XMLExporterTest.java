package com.holub.database;

import java.io.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class XMLExporterTest {
	@Test
    public void XML() throws IOException {
		Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
		people.insert(new Object[] { "Holub", "Allen", "1" });
		people.insert(new Object[] { "Flintstone", "Wilma", "2" });

        Writer out = new FileWriter("c:/dp2023/people.xml");
        people.export(new XMLExporter(out));
        out.close();
        
        StringBuilder contentBuilder = new StringBuilder();
        
        try {
            BufferedReader in = new BufferedReader(new FileReader("c:/dp2023/people.xml"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        	
        }

        String content = contentBuilder.toString();
        
        assertEquals(content, 
        		"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>"
        		+ "<people>"
        		+ "<data><last>Holub</last><first>Allen</first><addrId>1</addrId></data>"
        		+ "<data><last>Flintstone</last><first>Wilma</first><addrId>2</addrId></data>"
        		+ "</people>");
    }
}