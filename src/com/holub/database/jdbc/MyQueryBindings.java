package com.holub.database.jdbc;

public interface MyQueryBindings {
    MyQueryBindings clone();

//    void setBoolean(int parameterIndex, boolean x);

    void setFloat(int parameterIndex, float x);

    void setInt(int parameterIndex, int x);

    void setLong(int parameterIndex, long x);

    void setString(int parameterIndex, String x);
}
