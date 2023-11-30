package com.holub.database.jdbc;

import java.util.ArrayList;
import java.util.Arrays;

public class JDBCMyQueryBindings implements MyQueryBindings{
    private Object[] bindValues;
    private String sql;
    private int parameterCount;
    private int allParametersBind = 0;
    StringBuilder finishedSQL = null;

    public JDBCMyQueryBindings(String sql) {
        this.sql = sql;
        this.parameterCount =(int) sql.chars().filter(ch -> ch == '?').count();
        if (parameterCount == 0){
            this.allParametersBind = 0;
            finishedSQL = new StringBuilder(sql);
        }
        else{
            this.bindValues = new Object[parameterCount];
            this.allParametersBind = parameterCount;
        }
//        System.out.println("JDBCMyQueryBindings객체 생성: sql= "+sql+", param count = "+this.allParametersBind);
    }

    public String makeFinishedSQL(){
        // param count == 0일 때!
        if (finishedSQL!= null)
            return finishedSQL.toString();

        if (allParametersBind != 0)
            throw new IllegalArgumentException("모든 파라미터가 바인딩되지 않았습니다.");

        String finishedSQL = this.sql;
        for (Object bindValue : this.bindValues) {
            String bindString = null;
            if (bindValue == null)
                throw new NullPointerException("bind value에 NUll 값이 들어왔습니다.");
            if (bindValue instanceof String)
                bindString = (String) bindValue;
            else
                bindString =bindValue.toString();
            finishedSQL = finishedSQL.replaceFirst("\\?", bindString);
        }
        return finishedSQL;
    }
    @Override
    public void setFloat(int parameterIndex, float x) {
        this.bindValues[parameterIndex - 1] = x;
        this.allParametersBind--;
    }

    @Override
    public void setInt(int parameterIndex, int x) {
//        System.out.println("set Int 함수의 실행, (param, x) = ("+parameterIndex+", "+x+"), allParametersBind = "+this.allParametersBind);
        this.bindValues[parameterIndex - 1] = x;
        this.allParametersBind--;
    }

    @Override
    public void setLong(int parameterIndex, long x) {
        this.bindValues[parameterIndex -1 ] = x;
        this.allParametersBind--;
    }

    @Override
    public void setString(int parameterIndex, String x) throws Exception {
        if (x == null)
            throw new Exception("parameter string values are null!!");
//        System.out.println("set String 함수의 실행, (param, x) = ("+parameterIndex+", "+x+"), allParametersBind = "+this.allParametersBind);
        this.bindValues[parameterIndex - 1]= x;
        this.allParametersBind--;
    }

    public void clearParameters(){
        this.allParametersBind = 0;
        this.parameterCount = 0;
        this.bindValues = null;
    }
}
