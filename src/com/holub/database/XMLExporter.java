package main.java.com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {
	private final Writer out;
	private String tableName;
	private String[] columnNames;

	public XMLExporter(Writer out) {
        this.out = out;
    }
	
	public void startTable() throws IOException {
		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
	}
	
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException  {
		this.tableName = tableName;
		int idx = 0;
		this.columnNames = new String[width];
		while(columnNames.hasNext()) {
			this.columnNames[idx++] = columnNames.next().toString();
		}	
		out.write(tableName == null ? "<anonymous>" : "<" + tableName + ">" );
	}
	
	public void storeRow(Iterator data) throws IOException {
		out.write("<data>");
		int idx = 0;
		while(data.hasNext()) {
			out.write("<" + columnNames[idx] + ">");
			out.write(data.next().toString());
			out.write("</" + columnNames[idx++] + ">");
		}
		out.write("</data>");
	}
	
	public void endTable() throws IOException {
		out.write(tableName == null ? "</anonymous>" : "</" + tableName + ">" );
	}
}