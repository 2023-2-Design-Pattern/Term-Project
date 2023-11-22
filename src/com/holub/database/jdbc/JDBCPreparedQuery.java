package com.holub.database.jdbc;

public class JDBCPreparedQuery implements PreparedQuery{
    protected QueryInfo queryInfo;
    protected QueryBindings queryBindings = null;
    protected int parameterCount;
    protected String sql;

    public JDBCPreparedQuery(String sql) {
        this.sql = sql;
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
    public QueryBindings getQueryBindings() {
        return this.queryBindings;
    }
    @Override
    public void setQueryBindings(QueryBindings queryBindings) {
        this.queryBindings = queryBindings;
    }
}
