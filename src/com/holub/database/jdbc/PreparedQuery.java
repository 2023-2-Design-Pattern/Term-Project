package com.holub.database.jdbc;


public interface PreparedQuery extends Query{
    QueryInfo getQueryInfo();
    void setQueryInfo(QueryInfo queryInfo);

    void setParameterCount(int parameterCount);

    public MyQueryBindings getQueryBindings();
    public void setQueryBindings(MyQueryBindings queryBindings);

    String getBindedQuery();
}
