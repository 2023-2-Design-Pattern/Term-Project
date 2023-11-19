package com.holub.rmi.serialobject;

import com.holub.database.Cursor;
import com.holub.database.jdbc.JDBCResultSet;
import com.holub.database.jdbc.adapters.ResultSetAdapter;

import java.util.LinkedList;
import java.util.ListIterator;

public class RMIResultSetAdapter extends ResultSetAdapter {
    LinkedList<Object[]> rowSet = new LinkedList<Object[]>();
    ListIterator<Object[]> rowSetIterator;
    private Object[] row = null;

    RMIResultSetAdapter(JDBCResultSet rs) {
        Cursor cursor = rs.getCursor();
        while (cursor.advance()) {
            Object[] copyRow = new Object[cursor.columnCount()];
            for (int i = 0; i < cursor.columnCount(); i++) {
                copyRow[i] = cursor.columnName(i);
            }
            rowSet.add(copyRow);
        }
        rowSetIterator = rowSet.listIterator();
    }

    public boolean next() {
        if (rowSetIterator.hasNext()) {
            row = (Object[]) rowSetIterator.next();
            return true;
        }
        return false;
    }
}
