package com.holub.database.jdbc;

import java.util.Arrays;

public class JDBCPreparedQuery implements PreparedQuery{
    protected QueryInfo queryInfo;
    protected MyQueryBindings queryBindings = new JDBCMyQueryBindings();
    protected int parameterCount;
    protected String sql;
    protected String[] parsedSQL;
    protected Object[] bindValues;
    protected boolean allParametersBind = false;

    public JDBCPreparedQuery(String sql) {
        this.sql = sql;
        this.queryInfo = new QueryInfo(sql);
        // sql 문은 항상 아래와 같이 들어온다고 가정한다.
        // SELECT * FROM userGame WHERE userId = ? ORDER BY gameBoardId DESC, status DESC
        // 1. space로 파싱한다.
        this.parsedSQL = sql.split(" ");
        this.parameterCount = (int) Arrays.stream(parsedSQL)
                .filter(s -> s.equals("?"))
                .count();
        if (parameterCount == 0){
            allParametersBind = true;
        }
        else{
            this.bindValues = new Object[parameterCount]; // 공간을 미리 만들어두고, set할 때 여기 넣어준다.
            //this.queryBindings = this.queryBindings.clone(); // PATTERN - PROTOTYPE: 이미 생성되어 있는 query bindings객체를 이용하는 프로토타입 패턴 적용
            this.queryBindings = new JDBCMyQueryBindings(parameterCount);
        }
    }

    public String makeFinishedQuery() {
        if (allParametersBind){
            StringBuilder finishedSQL = new StringBuilder();
            int bindIndex = 0;

            for (int i = 0; i < parsedSQL.length; i++) {
                if (parsedSQL[i].equals("?")) {
                    // parsedSQL에서 "?" 문자를 찾으면 bindValues의 값으로 대체합니다.
                    finishedSQL.append(bindValues[bindIndex].toString());
                    bindIndex++;
                } else {
                    // "?" 문자가 아닌 경우 원래의 문자를 그대로 사용합니다.
                    finishedSQL.append(parsedSQL[i]);
                }
                // 원소들 사이에 띄어쓰기를 추가합니다.
                if (i < parsedSQL.length - 1) {
                    finishedSQL.append(" ");
                }
            }
            return finishedSQL.toString();
        }else
            throw new IllegalArgumentException("모든 파라미터가 바인딩되지 않았습니다.");
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
