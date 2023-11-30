package com.holub.database;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HTMLExporterTest {
    @Test
    public void HTML() throws IOException {
        Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });

        Writer out = new FileWriter("c:/dp2023/people.html");
        people.export(new HTMLExporter(out));
        out.close();

        StringBuilder contentBuilder = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new FileReader("c:/dp2023/people.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {

        }

        String content = contentBuilder.toString();

        assertEquals(content,
                "<html><body><table><caption>people</caption><thead><tr>"
                        + "<th scope=\"col\">last</th><th scope=\"col\">first</th><th scope=\"col\">addrId</th></tr></thead><tbody>"
                        + "<tr><td>Holub</td><td>Allen</td><td>1</td></tr>"
                        + "<tr><td>Flintstone</td><td>Wilma</td><td>2</td></tr>"
                        + "</tbody></table></body></html>");

    }
}