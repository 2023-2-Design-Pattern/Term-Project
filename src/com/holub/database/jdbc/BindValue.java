package com.holub.database.jdbc;

import java.util.concurrent.atomic.AtomicBoolean;

public interface BindValue{
    BindValue clone();
    void setBinding(Object obj, MysqlType type, int numberOfExecutions, AtomicBoolean sendTypesToServer);

    void reset();

    boolean isNull();

    void setNull(boolean isNull);

    boolean isStream();

    boolean isNational();

    void setIsNational(boolean isNational);

    Object getValue();

    //
    //    @Override
    //    public boolean keepOrigNanos() {
    //        return this.keepOrigNanos;
    //    }
    //
    //    @Override
    //    public void setKeepOrigNanos(boolean value) {
    //        this.keepOrigNanos = value;
    //    }
    //
    MysqlType getMysqlType();

    boolean isSet();

    int getFieldType();

    String getString();

    String getName();

    void setName(String name);
}
