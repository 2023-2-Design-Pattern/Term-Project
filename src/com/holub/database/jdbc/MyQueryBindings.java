package com.holub.database.jdbc;

public interface MyQueryBindings {

//    void setBoolean(int parameterIndex, boolean x);

    MyQueryBindings clone(String sql);

    void setFloat(int parameterIndex, float x);

    void setInt(int parameterIndex, int x);

    void setLong(int parameterIndex, long x);

    void setString(int parameterIndex, String x) throws Exception;

    String makeFinishedSQL();
}
