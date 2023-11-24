//package com.holub.database.jdbc.queryBinding;
///*
// * Copyright (c) 2017, 2023, Oracle and/or its affiliates.
// *
// * This program is free software; you can redistribute it and/or modify it under
// * the terms of the GNU General Public License, version 2.0, as published by the
// * Free Software Foundation.
// *
// * This program is also distributed with certain software (including but not
// * limited to OpenSSL) that is licensed under separate terms, as designated in a
// * particular file or component or in included license documentation. The
// * authors of MySQL hereby grant you an additional permission to link the
// * program and your derivative works with the separately licensed software that
// * they have included with MySQL.
// *
// * Without limiting anything contained in the foregoing, this file, which is
// * part of MySQL Connector/J, is also subject to the Universal FOSS Exception,
// * version 1.0, a copy of which can be found at
// * http://oss.oracle.com/licenses/universal-foss-exception.
// *
// * This program is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
// * FOR A PARTICULAR PURPOSE. See the GNU General Public License, version 2.0,
// * for more details.
// *
// * You should have received a copy of the GNU General Public License along with
// * this program; if not, write to the Free Software Foundation, Inc.,
// * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301  USA
// */
//
//import com.holub.database.jdbc.HolubsqlType;
//
//import java.io.InputStream;
//import java.io.Reader;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.sql.Blob;
//import java.sql.Clob;
//import java.sql.Date;
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.time.Duration;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.OffsetDateTime;
//import java.time.OffsetTime;
//import java.time.ZonedDateTime;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//public class NativeQueryBindings implements QueryBindings {
//
//    /** Bind values for individual fields */
//    private BindValue[] bindValues;
//
//    private int numberOfExecutions = 0;
//
//    /** Is this query a LOAD DATA query? */
//    private boolean isLoadDataQuery = false;
//
//
//    /** Do we need to send/resend types to the server? */
//    private AtomicBoolean sendTypesToServer = new AtomicBoolean(false); // specific to ServerPreparedQuery
//
//    private BindValue bindValueConstructor;
//    /**
//     * Flag indicating whether or not the long parameters have been 'switched' back to normal parameters.
//     * We cannot execute() if clearParameters() has not been called in this case.
//     */
//    private boolean longParameterSwitchDetected = false; // specific to ServerPreparedQuery
//
//    public NativeQueryBindings(int parameterCount) {
//        this.bindValues = new BindValue[parameterCount];
//        for (int i = 0; i < parameterCount; i++) {
//            this.bindValues[i] = null;//null object pattern
//        }
//    }
//
//    @Override
//    public QueryBindings clone() {
//        NativeQueryBindings newBindings = new NativeQueryBindings(this.bindValues.length);
//        BindValue[] bvs = new BindValue[this.bindValues.length];
//        for (int i = 0; i < this.bindValues.length; i++) {
//            bvs[i] = this.bindValues[i].clone();
//        }
//        newBindings.setBindValues(bvs);
//        newBindings.isLoadDataQuery = this.isLoadDataQuery;
//        newBindings.sendTypesToServer.set(this.sendTypesToServer.get());
//        newBindings.setLongParameterSwitchDetected(this.isLongParameterSwitchDetected());
//        return newBindings;
//    }
//
//    @Override
//    public BindValue[] getBindValues() {
//        return this.bindValues;
//    }
//
//    @Override
//    public void setBindValues(BindValue[] bindValues) {
//        this.bindValues = bindValues;
//    }
//
////    @Override
////    public boolean clearBindValues() {
////        boolean hadLongData = false;
////
////        if (this.bindValues != null) {
////            for (int i = 0; i < this.bindValues.length; i++) {
////                if (this.bindValues[i] != null && this.bindValues[i].isStream()) {
////                    hadLongData = true;
////                }
////                this.bindValues[i].reset();
////            }
////        }
////
////        return hadLongData;
////    }
//
//    @Override
//    public void checkParameterSet(int columnIndex) {
//        if (!this.bindValues[columnIndex].isSet()) {
//            throw new IllegalStateException("check parameter set error");
//        }
//    }
//
////    @Override
////    public void checkAllParametersSet() {
////        for (int i = 0; i < this.bindValues.length; i++) {
////            checkParameterSet(i);
////        }
////    }
//
//    @Override
//    public boolean isLongParameterSwitchDetected() {
//        return this.longParameterSwitchDetected;
//    }
//
//    @Override
//    public void setLongParameterSwitchDetected(boolean longParameterSwitchDetected) {
//        this.longParameterSwitchDetected = longParameterSwitchDetected;
//    }
//
//    /**
//     * Returns the structure representing the value that (can be)/(is)
//     * bound at the given parameter index.
//     *
//     * @param parameterIndex
//     *            0-based
//     * @param forLongData
//     *            is this for a stream?
//     * @return BindValue
//     */
//    @Override
//    public BindValue getBinding(int parameterIndex, boolean forLongData) {
//        if (this.bindValues[parameterIndex] != null && this.bindValues[parameterIndex].isStream() && !forLongData) {
//            this.longParameterSwitchDetected = true;
//        }
//        return this.bindValues[parameterIndex];
//    }
//
////    @Override
////    public void setFromBindValue(int parameterIndex, BindValue bv) {
////        BindValue binding = getBinding(parameterIndex, false);
////        binding.setBinding(bv.getValue(), bv.getMysqlType(), this.numberOfExecutions, this.sendTypesToServer);
////        binding.setKeepOrigNanos(bv.keepOrigNanos());
////        binding.setCalendar(bv.getCalendar());
////        binding.setEscapeBytesIfNeeded(bv.escapeBytesIfNeeded());
////        binding.setIsNational(bv.isNational());
////        binding.setField(bv.getField());
////        binding.setScaleOrLength(bv.getScaleOrLength());
////    }
//    static Map<Class<?>, HolubsqlType> DEFAULT_MYSQL_TYPES = new HashMap<>();
//    static {
//        DEFAULT_MYSQL_TYPES.put(BigDecimal.class, HolubsqlType.DECIMAL);
//        DEFAULT_MYSQL_TYPES.put(BigInteger.class, HolubsqlType.BIGINT);
//        DEFAULT_MYSQL_TYPES.put(Blob.class, HolubsqlType.BLOB);
//        DEFAULT_MYSQL_TYPES.put(Boolean.class, HolubsqlType.BOOLEAN);
//        DEFAULT_MYSQL_TYPES.put(Byte.class, HolubsqlType.TINYINT);
//        DEFAULT_MYSQL_TYPES.put(byte[].class, HolubsqlType.BINARY);
//        DEFAULT_MYSQL_TYPES.put(Calendar.class, HolubsqlType.TIMESTAMP);
//        DEFAULT_MYSQL_TYPES.put(Clob.class, HolubsqlType.TEXT);
//        DEFAULT_MYSQL_TYPES.put(Date.class, HolubsqlType.DATE);
//        DEFAULT_MYSQL_TYPES.put(java.util.Date.class, HolubsqlType.TIMESTAMP);
//        DEFAULT_MYSQL_TYPES.put(Double.class, HolubsqlType.DOUBLE);
//        DEFAULT_MYSQL_TYPES.put(Duration.class, HolubsqlType.TIME);
//        DEFAULT_MYSQL_TYPES.put(Float.class, HolubsqlType.FLOAT);
//        DEFAULT_MYSQL_TYPES.put(InputStream.class, HolubsqlType.BLOB);
//        DEFAULT_MYSQL_TYPES.put(Instant.class, HolubsqlType.TIMESTAMP);
//        DEFAULT_MYSQL_TYPES.put(Integer.class, HolubsqlType.INT);
//        DEFAULT_MYSQL_TYPES.put(LocalDate.class, HolubsqlType.DATE);
//        DEFAULT_MYSQL_TYPES.put(LocalDateTime.class, HolubsqlType.DATETIME); // default JDBC mapping is TIMESTAMP, see B-4
//        DEFAULT_MYSQL_TYPES.put(LocalTime.class, HolubsqlType.TIME);
//        DEFAULT_MYSQL_TYPES.put(Long.class, HolubsqlType.BIGINT);
//        DEFAULT_MYSQL_TYPES.put(OffsetDateTime.class, HolubsqlType.TIMESTAMP); // default JDBC mapping is TIMESTAMP_WITH_TIMEZONE, see B-4
//        DEFAULT_MYSQL_TYPES.put(OffsetTime.class, HolubsqlType.TIME); // default JDBC mapping is TIME_WITH_TIMEZONE, see B-4
//        DEFAULT_MYSQL_TYPES.put(Reader.class, HolubsqlType.TEXT);
//        DEFAULT_MYSQL_TYPES.put(Short.class, HolubsqlType.SMALLINT);
//        DEFAULT_MYSQL_TYPES.put(String.class, HolubsqlType.VARCHAR);
//        DEFAULT_MYSQL_TYPES.put(Time.class, HolubsqlType.TIME);
//        DEFAULT_MYSQL_TYPES.put(Timestamp.class, HolubsqlType.TIMESTAMP);
//        DEFAULT_MYSQL_TYPES.put(ZonedDateTime.class, HolubsqlType.TIMESTAMP); // no JDBC mapping is defined
//    }
//
//    @Override
//    public void setBoolean(int parameterIndex, boolean x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.BOOLEAN, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//    //@Override
////    public void setDouble(int parameterIndex, double x) {
////        if (!this.session.getPropertySet().getBooleanProperty(PropertyKey.allowNanAndInf).getValue()
////                && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
////            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.64", new Object[] { x }),
////                    this.session.getExceptionInterceptor());
////        }
////        getBinding(parameterIndex, false).setBinding(x, MysqlType.DOUBLE, this.numberOfExecutions, this.sendTypesToServer);
////    }
//
//    @Override
//    public void setFloat(int parameterIndex, float x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.FLOAT, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//    @Override
//    public void setInt(int parameterIndex, int x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.INT, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//    @Override
//    public void setLong(int parameterIndex, long x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.BIGINT, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//
//    //@Override
//    public synchronized void setNull(int parameterIndex) {
//        BindValue binding = getBinding(parameterIndex, false);
//        binding.setBinding(null, HolubsqlType.NULL, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//
//    //@Override
//    public void setShort(int parameterIndex, short x) {
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.SMALLINT, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//    @Override
//    public void setString(int parameterIndex, String x) {
//        if (x == null) {
//            setNull(parameterIndex);
//            return;
//        }
//        getBinding(parameterIndex, false).setBinding(x, HolubsqlType.VARCHAR, this.numberOfExecutions, this.sendTypesToServer);
//    }
//
//}