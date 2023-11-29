package com.holub.database.jdbc;


public interface PreparedQuery extends Query{
    QueryInfo getQueryInfo();
    void setQueryInfo(QueryInfo queryInfo);
    void setParameterCount(int parameterCount);
    public void setQueryBindings(MyQueryBindings queryBindings);
    String getBindedQuery();
    public void setInt(int parameterIndex, int x);
    public void setFloat(int parameterIndex, float x);
    public void setLong(int parameterIndex, Long x);
    public void setString(int parameterIndex, String x) throws Exception;
    void clearParameters();
}
