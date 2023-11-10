package main.java.com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;

    public HTMLExporter(Writer out) {
        this.out = out;
    }

    public void startTable() throws IOException {
        out.write("<html>");
        out.write("<body>");
    }
	
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
    	out.write("<table>");
    	out.write("<caption>");
    	out.write(tableName == null ? "anonymous" : tableName);		// title of the table
    	out.write("</caption>");
   
    	out.write("<thead>");
    	out.write("<tr>");
    	
    	while(columnNames.hasNext()) {
    		out.write("<th scope=\"col\">");
    		out.write(columnNames.next().toString());
            out.write("</th>");
    	}
    	
    	out.write("</tr>");
    	out.write("</thead>");
    	out.write("<tbody>");
    }

    public void storeRow(Iterator data) throws IOException {
    	out.write("<tr>");
    	while(data.hasNext()) {
    		out.write("<td>");
    		out.write(data.next().toString());
            out.write("</td>");
    	}
    	out.write("</tr>");
    }


    public void endTable() throws IOException {
    	out.write("</tbody>");
        out.write("</table>");
        out.write("</body>");
        out.write("</html>");

        out.close();
    }

}