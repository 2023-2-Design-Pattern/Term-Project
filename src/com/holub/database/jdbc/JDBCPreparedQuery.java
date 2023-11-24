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
        // 1. space로 파싱한다.
        System.out.println("입력된 sql: "+ sql);
        this.queryBindings = new JDBCMyQueryBindings(sql);
        /*
        this.parsedSQL = sql.split(" ");
        this.parameterCount = (int) Arrays.stream(parsedSQL)
                .filter(s -> s.equals("?"))
                .count();
        if (parameterCount == 0){
            System.out.println("parameter count = "+parameterCount);
            allParametersBind = true;
        }
        else{
            this.bindValues = new Object[parameterCount]; // 공간을 미리 만들어두고, set할 때 여기 넣어준다.
            //this.queryBindings = this.queryBindings.clone(); // PATTERN - PROTOTYPE: 이미 생성되어 있는 query bindings객체를 이용하는 프로토타입 패턴 적용
            this.queryBindings = new JDBCMyQueryBindings(sql, parameterCount);
        }*/
    }

    public String getBindedQuery() {
        // TODO: 여기서 query Bidnings 에서 bindValue를 받아오면 아무의미가 없어지려나?
        // TODO: 그렇다면 그냥 queryBindings에 query String을 합치는 역할을 넘겨줘서 문장을 반환해달라고 하는게 낫지 않나?
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
