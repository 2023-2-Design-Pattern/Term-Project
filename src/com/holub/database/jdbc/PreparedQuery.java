package com.holub.database.jdbc;

public interface PreparedQuery extends Query{
    QueryInfo getQueryInfo();
    public QueryBindings getQueryBindings();
    public void setQueryBindings(QueryBindings queryBindings);
}
