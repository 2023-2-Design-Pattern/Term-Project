package com.holub.database.jdbc;

import java.util.ArrayList;

public class JDBCMyQueryBindings implements MyQueryBindings{
    private Object[] bindValues;

    public JDBCMyQueryBindings(){
        this.bindValues = new ArrayList<>().toArray();
    }
    public JDBCMyQueryBindings(int parameterCount) {
        this.bindValues = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            this.bindValues[i] = null;//null object pattern
        }
    }

    @Override
    public MyQueryBindings clone() {
        //        BindValue[] bvs = new BindValue[this.bindValues.length];
//        for (int i = 0; i < this.bindValues.length; i++) {
//            bvs[i] = this.bindValues[i].clone();
//        }
//        newBindings.setBindValues(bvs);
//        newBindings.isLoadDataQuery = this.isLoadDataQuery;
//        newBindings.sendTypesToServer.set(this.sendTypesToServer.get());
//        newBindings.setLongParameterSwitchDetected(this.isLongParameterSwitchDetected());
        return new JDBCMyQueryBindings(this.bindValues.length);
    }

//    @Override
//    public void setBoolean(int parameterIndex, boolean x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.BOOLEAN, this.numberOfExecutions, this.sendTypesToServer);
//    }

    @Override
    public void setFloat(int parameterIndex, float x) {
        this.bindValues[parameterIndex] = x;
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.FLOAT, this.numberOfExecutions, this.sendTypesToServer);
    }

    @Override
    public void setInt(int parameterIndex, int x) {
        this.bindValues[parameterIndex] = x;
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.INT, this.numberOfExecutions, this.sendTypesToServer);
    }

    @Override
    public void setLong(int parameterIndex, long x) {
        this.bindValues[parameterIndex] = x;
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.BIGINT, this.numberOfExecutions, this.sendTypesToServer);
    }

    @Override
    public void setString(int parameterIndex, String x) {
        this.bindValues[parameterIndex]= x;
//        if (x == null) {
//            setNull(parameterIndex);
//            return;
//        }
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.VARCHAR, this.numberOfExecutions, this.sendTypesToServer);
    }
}
