package com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;

    public HTMLExporter(Writer out) {
        this.out = out;
    }

    public void startTable() throws IOException {
        out.write("<html>\n");
        out.write("<body>\n");
    }
	
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
    	out.write("<table>\n");
    	out.write("<caption>");
    	out.write(tableName == null ? "anonymous" : tableName);		// title of the table
    	out.write("</caption>");
   
    	out.write("<thead>\n");
    	out.write("<tr>\n");
    	
    	while(columnNames.hasNext()) {
    		out.write("<th scope=\"col\">");
    		out.write(columnNames.next().toString());
            out.write("</th>\n");
    	}
    	
    	out.write("</tr>\n");
    	out.write("</thead>\n");
    	out.write("<tbody>\n");
    }

    public void storeRow(Iterator data) throws IOException {
    	out.write("<tr>\n");
    	while(data.hasNext()) {
    		out.write("<td>");
    		out.write(data.next().toString());
            out.write("</td>\n");
    	}
    	out.write("</tr>\n");
    }


    public void endTable() throws IOException {
    	out.write("</tbody>\n");
        out.write("</table>\n");
        out.write("</body>\n");
        out.write("</html>\n");

        out.close();
    }

}