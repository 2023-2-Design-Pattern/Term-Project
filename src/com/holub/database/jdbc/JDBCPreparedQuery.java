package com.holub.database.jdbc;

import java.util.Arrays;

public class JDBCPreparedQuery implements PreparedQuery{
    protected QueryInfo queryInfo;
    protected MyQueryBindings queryBindings;
    protected int parameterCount;
    protected String sql;

    public JDBCPreparedQuery(String sql) {
        this.sql = sql;
        this.queryInfo = new QueryInfo(sql);
        // SELECT * FROM userGame WHERE userId = ? ORDER BY gameBoardId DESC, status DESC
        this.queryBindings = new JDBCMyQueryBindings(sql);
    }

    public String getBindedQuery() {
        String test = this.queryBindings.makeFinishedSQL();
        return test;
    }

    @Override
    public void setInt(int parameterIndex, int x) {
        this.queryBindings.setInt(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) {
        this.queryBindings.setFloat(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, Long x) {
        this.queryBindings.setLong(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws Exception {
        this.queryBindings.setString(parameterIndex, x);
    }

    @Override
    public void clearParameters() {
        this.queryBindings.clearParameters();
    }


    @Override
    public QueryInfo getQueryInfo() {
        return this.queryInfo;
    }
    @Override
    public void setQueryInfo(QueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }
    @Override
    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }
    @Override
    public void setQueryBindings(MyQueryBindings myQueryBindings) {
        this.queryBindings = myQueryBindings;
    }
}
