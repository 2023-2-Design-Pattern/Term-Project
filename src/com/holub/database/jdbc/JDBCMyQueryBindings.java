package com.holub.database.jdbc;

import java.util.ArrayList;
import java.util.Arrays;

public class JDBCMyQueryBindings implements MyQueryBindings{
    private Object[] bindValues;
    private String sql;
    private String[] parsedSQL;
    private int parameterCount;
    private int allParametersBind = 0;
    StringBuilder finishedSQL = null;

    public JDBCMyQueryBindings(String sql) {
        System.out.println("JDBCMyQueryBindings객체 생성: sql, param count = "+sql+","+parameterCount);
        // 여기서 sql문 파싱하는 걸 넣어준다.
        this.sql = sql;
        this.parsedSQL = sql.split(" ");
        this.parameterCount = (int) Arrays.stream(parsedSQL)
                .filter(s -> s.equals("?"))
                .count();
        if (parameterCount == 0){
            System.out.println("parameter count = "+parameterCount);
            allParametersBind = 0;
            finishedSQL = new StringBuilder(sql);
        }
        else{
            this.bindValues = new Object[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                this.bindValues[i] = null;//null object pattern
            }
            this.allParametersBind = parameterCount;
            //this.queryBindings = this.queryBindings.clone(); // PATTERN - PROTOTYPE: 이미 생성되어 있는 query bindings객체를 이용하는 프로토타입 패턴 적용
        }

    }

    //    public JDBCMyQueryBindings(int parameterCount) {
//        this.parameterCount = parameterCount;
//        System.out.println("JDBCMyQueryBindings객체 생성, param count = "+parameterCount);
//        this.bindValues = new Object[parameterCount];
//        for (int i = 0; i < parameterCount; i++) {
//            this.bindValues[i] = null;//null object pattern
//        }
//    }


    @Override
    public MyQueryBindings clone(String sql) {
        //        BindValue[] bvs = new BindValue[this.bindValues.length];
//        for (int i = 0; i < this.bindValues.length; i++) {
//            bvs[i] = this.bindValues[i].clone();
//        }
//        newBindings.setBindValues(bvs);
//        newBindings.isLoadDataQuery = this.isLoadDataQuery;
//        newBindings.sendTypesToServer.set(this.sendTypesToServer.get());
//        newBindings.setLongParameterSwitchDetected(this.isLongParameterSwitchDetected());
        return new JDBCMyQueryBindings(sql);
    }

//    @Override
//    public void setBoolean(int parameterIndex, boolean x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.BOOLEAN, this.numberOfExecutions, this.sendTypesToServer);
//    }

    public String makeFinishedSQL(){
        // param count == 0일 때!
        if (finishedSQL!= null)
            return finishedSQL.toString();

        for (Object bindValue : bindValues) {
            System.out.println("bindValue: "+  bindValue);
            if (bindValue == null) {
                break;
            }
        }
        if (allParametersBind==0){
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
    public void setFloat(int parameterIndex, float x) {
        this.bindValues[parameterIndex - 1] = x;
        this.allParametersBind--;
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.FLOAT, this.numberOfExecutions, this.sendTypesToServer);
    }

    @Override
    public void setInt(int parameterIndex, int x) {
        System.out.println("set Int 함수의 실행, (param, x) = ("+parameterIndex+", "+x+")");
        this.bindValues[parameterIndex - 1] = x;
        this.allParametersBind--;
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.INT, this.numberOfExecutions, this.sendTypesToServer);
    }

    @Override
    public void setLong(int parameterIndex, long x) {
        this.bindValues[parameterIndex -1 ] = x;
        this.allParametersBind--;

//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.BIGINT, this.numberOfExecutions, this.sendTypesToServer);
    }

    @Override
    public void setString(int parameterIndex, String x) {
        this.bindValues[parameterIndex - 1]= x;
        this.allParametersBind--;

//        if (x == null) {
//            setNull(parameterIndex);
//            return;
//        }
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.VARCHAR, this.numberOfExecutions, this.sendTypesToServer);
    }
}
