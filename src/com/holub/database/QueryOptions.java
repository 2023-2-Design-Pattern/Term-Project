package com.holub.database;

import java.util.List;

public class QueryOptions {
    private boolean distinct = false;
    private boolean orderBy = false;

    private List orderByColumns;
    private boolean orderByASC = false;

    public QueryOptions() {
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct() {
        this.distinct = true;
    }

    public boolean isOrderBy() {
        return orderBy;
    }

    public void setOrderBy() {
        this.orderBy = true;
    }

    public List getOrderByColumns() {
        return orderByColumns;
    }

    public void setOrderByColumns(List orderByColumns) {
        this.orderByColumns = orderByColumns;
    }

    public boolean isOrderByASC() {
        return orderByASC;
    }

    public void setOrderByASC(boolean bool) {
        this.orderByASC = bool;
    }
}
