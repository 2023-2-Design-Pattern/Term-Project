package com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;
    private int width = 0;

    public HTMLExporter(Writer out) {
        this.out = out;
    }

    public void startTable() throws IOException {
        out.write("<html>\n<body>");
    }
	
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
    }


    @Override
    public void storeRow(Iterator data) throws IOException {
    }


    public void endTable() throws IOException {
        out.write("</table>\n</body>\n</html>");
        out.close();
    }

}