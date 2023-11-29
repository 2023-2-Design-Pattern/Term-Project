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
        // sql 문은 항상 아래와 같이 들어온다고 가정한다.
        // SELECT * FROM userGame WHERE userId = ? ORDER BY gameBoardId DESC, status DESC
        System.out.println("입력된 sql: "+ sql);
        this.queryBindings = new JDBCMyQueryBindings(sql);
    }

    public String getBindedQuery() {
        // TODO: 그렇다면 그냥 queryBindings에 query String을 합치는 역할을 넘겨줘서 문장을 반환해달라고 하는게 맞음
        String test = this.queryBindings.makeFinishedSQL();
        System.out.println("finished query : "+test);
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
