package com.holub.database.jdbc;

import com.holub.database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCPreparedStatement extends JDBCStatement{
    private Database database;
    private String sqlQuery;
    private JDBCConnection connection;

    public JDBCPreparedStatement(String sql, Database database)
    {
        super(database);
        this.database = database;
    	this.sqlQuery = sql;
    }
    public JDBCPreparedStatement(JDBCConnection c, String sql, Database database){
        super(c, database);
        this.connection = c;
        this.database = database;
        this.sqlQuery = sql;
    }

    protected static PreparedStatement getInstance(JDBCConnection conn, String sql, Database db) throws SQLException {
        return new JDBCPreparedStatement(conn, sql, db);
    }
}
