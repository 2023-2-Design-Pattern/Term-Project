package com.holub.database.jdbc;

import com.holub.database.Database;
import com.holub.database.jdbc.adapters.StatementAdapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCPreparedStatement extends StatementAdapter {
    private Database database;
    private List<String> sql_batch = new ArrayList<>();

    public JDBCPreparedStatement(Database database)
    {	this.database = database;
    }
    public void addBatch(String sql) throws SQLException {
        sql_batch.add(sql);
    }
    public int[] executeBatch() throws SQLException {
        int count[] = new int[sql_batch.size()];
        int cnt=0;
        try
        {
            for(String sql : sql_batch){
                count[cnt] = executeUpdate(sql);
                cnt++;
            }
            return count;
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    public void clearBatch() throws SQLException {throw new SQLException("Statement.clearBatch() not supported");}

}
