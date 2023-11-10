package com.holub.database;

public class QueryOptions {
    private boolean distinct = false;

    public QueryOptions() {
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct() {
        this.distinct = true;
    }
}
