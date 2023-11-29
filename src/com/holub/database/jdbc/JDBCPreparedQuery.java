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
    public MyQueryBindings getQueryBindings() {
        return this.queryBindings;
    }
    @Override
    public void setQueryBindings(MyQueryBindings myQueryBindings) {
        this.queryBindings = myQueryBindings;
    }
}
