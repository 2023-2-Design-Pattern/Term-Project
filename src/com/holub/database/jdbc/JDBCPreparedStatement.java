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

    public void setInt(int parameterIndex, int x) {
        ((PreparedQuery) this.query).getQueryBindings().setInt(getCoreParameterIndex(parameterIndex), x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        ((PreparedQuery) this.query).getQueryBindings().setFloat(getCoreParameterIndex(parameterIndex), x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        synchronized (checkClosed().getConnectionMutex()) {
            ((PreparedQuery) this.query).getQueryBindings().setDouble(getCoreParameterIndex(parameterIndex), x);
        }
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        synchronized (checkClosed().getConnectionMutex()) {
            ((PreparedQuery) this.query).getQueryBindings().setLong(getCoreParameterIndex(parameterIndex), x);
        }
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        synchronized (checkClosed().getConnectionMutex()) {
            ((PreparedQuery) this.query).getQueryBindings().setString(getCoreParameterIndex(parameterIndex), x);
        }
    }
}
