package com.holub.database.jdbc;

import com.holub.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCPreparedStatement extends JDBCStatement{
    private Database database;
    private String sqlQuery;
    private String finalSql;
    private JDBCConnection connection;
    private Query query;
    private static List<String> sql_batch = new ArrayList<>();

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

    public int executeUpdate() throws SQLException {
        String finishedSQL= ((PreparedQuery)this.query).getBindedQuery();
        this.finalSql = finishedSQL;
        int result = super.executeUpdate(this.finalSql);
        return result;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        String finishedSQL= ((PreparedQuery)this.query).getBindedQuery();
        this.finalSql = finishedSQL;
        return super.executeQuery(finishedSQL);
    }

    @Override
    public void addBatch() throws SQLException {
        String finishedSQL= ((PreparedQuery)this.query).getBindedQuery();
        this.finalSql = finishedSQL;
        if (finishedSQL != null){
            sql_batch.add(finishedSQL);
        }
    }

    public int[] executeBatch() throws SQLException {
        int updateCounts[] = new int[sql_batch.size()];
        try {
            if (sql_batch != null) {
                int nbrCommands = sql_batch.size();
                for (int i = 0; i < nbrCommands; i++) {
                    updateCounts[i] = EXECUTE_FAILED; // set error value(-3)
                }
                int commandIndex = 0;
                for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) {
                    try {
                        String sql = sql_batch.get(commandIndex);
                        updateCounts[commandIndex] = executeUpdate(sql);
                    } catch (SQLException ex) {
                        updateCounts[commandIndex] = EXECUTE_FAILED;
                    }
                }
            }
            return updateCounts != null ? updateCounts : new int[0];
        }catch (Exception e) {
            System.out.println("error!");
            throw new SQLException(e.getMessage());
        }finally {
            clearBatch();
        }
    }

    public void clearBatch() throws SQLException {
        sql_batch.clear();
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

    public void setLong(int parameterIndex, long x) throws SQLException {
        ((PreparedQuery) this.query).getQueryBindings().setLong(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        try {
            ((PreparedQuery) this.query).getQueryBindings().setString(parameterIndex, x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearParameters() {

    }
}
