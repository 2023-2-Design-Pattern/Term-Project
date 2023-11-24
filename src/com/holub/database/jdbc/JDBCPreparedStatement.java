package com.holub.database.jdbc;

import com.holub.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPreparedStatement extends JDBCStatement{
    private Database database;
    private String sqlQuery;
    private JDBCConnection connection;
    private Query query;

    public JDBCPreparedStatement(String sql, Database database)
    {
        super(database);
        this.database = database;
    	this.sqlQuery = sql;
        this.query = new JDBCPreparedQuery(sql);

    }
    public JDBCPreparedStatement(JDBCConnection c, String sql, Database database){
        super(c, database);
        this.connection = c;
        this.database = database;
        this.sqlQuery = sql;
        this.query = new JDBCPreparedQuery(sql);
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        String finishedSQL= ((PreparedQuery)this.query).getBindedQuery();
        //System.out.println("Finished SQL은 "+finishedSQL);
        return super.executeQuery(finishedSQL);
    }

    protected static PreparedStatement getInstance(JDBCConnection conn, String sql, Database db) throws SQLException {
        return new JDBCPreparedStatement(conn, sql, db);
    }

    /*https://github.com/mysql/mysql-connector-j/blob/release/8.x/src/main/user-impl/java/com/mysql/cj/jdbc/ClientPreparedStatement.java*/

    public void setInt(int parameterIndex, int x) {
        ((PreparedQuery) this.query).getQueryBindings().setInt(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        ((PreparedQuery) this.query).getQueryBindings().setFloat(parameterIndex, x);
    }

//    public void setDouble(int parameterIndex, double x) throws SQLException {
//        synchronized (checkClosed().getConnectionMutex()) {
//            ((PreparedQuery) this.query).getQueryBindings().setDouble(getCoreParameterIndex(parameterIndex), x);
//        }
//    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        ((PreparedQuery) this.query).getQueryBindings().setLong(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        ((PreparedQuery) this.query).getQueryBindings().setString(parameterIndex, x);
    }
}
