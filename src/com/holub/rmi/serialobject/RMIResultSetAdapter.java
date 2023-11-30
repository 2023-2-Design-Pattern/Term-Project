package com.holub.rmi.serialobject;

import com.holub.database.Cursor;
import com.holub.database.jdbc.JDBCResultSet;
import com.holub.database.jdbc.adapters.ResultSetAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class RMIResultSetAdapter extends ResultSetAdapter implements Serializable {
    private transient JDBCResultSet resultSet;
    public ArrayList<Object[]> rowSet = new ArrayList<>();

    public RMIResultSetAdapter(JDBCResultSet rs) {
        resultSet = rs;

        Cursor cursor = resultSet.getCursor();
        while (cursor.advance()) {
            Object[] copyRow = new Object[cursor.columnCount()];
            for (int i = 0; i < cursor.columnCount(); i++) {
                copyRow[i] = cursor.column(cursor.columnName(i));
            }
            rowSet.add(copyRow);
        }
    }
}
