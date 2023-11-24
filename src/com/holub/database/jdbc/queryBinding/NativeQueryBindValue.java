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
//import java.sql.Blob;
//import java.sql.Clob;
//import java.util.Calendar;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//public class NativeQueryBindValue implements BindValue {
//
//    /** NULL indicator */
//    protected boolean isNull;
//
//    protected boolean isNational = false;
//
//    protected HolubsqlType targetType = HolubsqlType.NULL;
//
//    /** The value to store */
//    public Object value;
//
//    /** has this parameter been set? */
//    protected boolean isSet = false;
//
//    /* Calendar to be used for DATE and DATETIME values storing */
//    public Calendar calendar;
//
//    protected boolean escapeBytesIfNeeded = true;
//
//    /** Is this query a LOAD DATA query? */
//    protected boolean isLoadDataQuery = false;
//    protected boolean keepOrigNanos = false;
//    protected long scaleOrLength = -1;
//    protected long boundBeforeExecutionNum = 0; // specific to ServerPreparedQuery
//
//    /** The query attribute name */
//    private String name;
//    protected ValueEncoder valueEncoder = null;
//
//
//    @Override
//    public NativeQueryBindValue clone() {
//        return new NativeQueryBindValue(this);
//    }
//
//    protected NativeQueryBindValue(NativeQueryBindValue copyMe) {
//        this.isNull = copyMe.isNull;
//        this.targetType = copyMe.targetType;
//        if (copyMe.value != null && copyMe.value instanceof byte[]) {
//            this.value = new byte[((byte[]) copyMe.value).length];
//            System.arraycopy(copyMe.value, 0, this.value, 0, ((byte[]) copyMe.value).length);
//        } else {
//            this.value = copyMe.value;
//        }
//        this.isSet = copyMe.isSet;
//        this.calendar = copyMe.calendar;
//        this.escapeBytesIfNeeded = copyMe.escapeBytesIfNeeded;
//        this.isLoadDataQuery = copyMe.isLoadDataQuery;
//        this.isNational = copyMe.isNational;
//        this.keepOrigNanos = copyMe.keepOrigNanos;
//        this.valueEncoder = copyMe.valueEncoder;
//        this.scaleOrLength = copyMe.scaleOrLength;
//        this.boundBeforeExecutionNum = copyMe.boundBeforeExecutionNum;
//    }
//
//    private boolean resetToType(HolubsqlType newTargetType) { // specific to ServerPreparedQuery
//        // clear any possible old value
//        reset();
//
//        if (newTargetType == HolubsqlType.NULL) {
//            // preserve the previous type to (possibly) avoid sending types at execution time
//        } else if (this.targetType != newTargetType) {
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public void setBinding(Object obj, HolubsqlType type, int numberOfExecutions, AtomicBoolean sendTypesToServer) {
//        if (sendTypesToServer != null) {
//            sendTypesToServer.compareAndSet(false, resetToType(type)); // specific to ServerPreparedQuery
//        }
//
//        this.value = obj;
//        this.targetType = type;
//        this.boundBeforeExecutionNum = numberOfExecutions;
//
//        this.isNull = this.targetType == HolubsqlType.NULL;
//        this.isSet = true;
//        this.escapeBytesIfNeeded = true;
//
//    }
//
//    public void reset() {
//        this.isNull = false;
//        this.targetType = HolubsqlType.NULL;
//        this.value = null;
//        this.isSet = false;
//        this.calendar = null; // TODO how is it set again?
//        this.escapeBytesIfNeeded = true;
//        this.isLoadDataQuery = false;
//        this.isNational = false;
//        this.keepOrigNanos = false;
//        this.scaleOrLength = -1;
//    }
//
//    @Override
//    public boolean isNull() {
//        return this.isNull;
//    }
//
//    @Override
//    public void setNull(boolean isNull) {
//        this.isNull = isNull;
//        if (isNull) {
//            this.targetType = HolubsqlType.NULL;
//        }
//        this.isSet = true;
//    }
//
//    @Override
//    public boolean isStream() {
//        return this.value instanceof InputStream || this.value instanceof Reader || this.value instanceof Clob || this.value instanceof Blob;
//    }
//
//    @Override
//    public boolean isNational() {
//        return this.isNational;
//    }
//
//    @Override
//    public void setIsNational(boolean isNational) {
//        this.isNational = isNational;
//    }
//
//    @Override
//    public Object getValue() {
//        return this.value;
//    }
////
////    @Override
////    public boolean keepOrigNanos() {
////        return this.keepOrigNanos;
////    }
////
////    @Override
////    public void setKeepOrigNanos(boolean value) {
////        this.keepOrigNanos = value;
////    }
////
//    @Override
//    public HolubsqlType getMysqlType() {
//        return this.targetType;
//    }
////
////    @Override
////    public void setMysqlType(MysqlType type) {
////        this.targetType = type;
////    }
////
////    @Override
////    public boolean escapeBytesIfNeeded() {
////        return this.escapeBytesIfNeeded;
////    }
////
////    @Override
////    public void setEscapeBytesIfNeeded(boolean val) {
////        this.escapeBytesIfNeeded = val;
////    }
//
//    @Override
//    public boolean isSet() {
//        return this.isSet;
//    }
//
//    @Override
//    public int getFieldType() {
//        switch (this.targetType) {
//            case NULL:
//                return HolubsqlType.FIELD_TYPE_NULL;
//            case DECIMAL:
//            case DECIMAL_UNSIGNED:
//                return HolubsqlType.FIELD_TYPE_NEWDECIMAL;
//            case DOUBLE:
//            case DOUBLE_UNSIGNED:
//                return HolubsqlType.FIELD_TYPE_DOUBLE;
//            case BIGINT:
//            case BIGINT_UNSIGNED:
//                return HolubsqlType.FIELD_TYPE_LONGLONG;
//            case BIT:
//            case BOOLEAN:
//            case TINYINT:
//            case TINYINT_UNSIGNED:
//                return HolubsqlType.FIELD_TYPE_TINY;
//            case BINARY:
//            case VARBINARY:
//            case CHAR:
//            case VARCHAR:
//                return HolubsqlType.FIELD_TYPE_VAR_STRING;
//            case FLOAT:
//            case FLOAT_UNSIGNED:
//                return HolubsqlType.FIELD_TYPE_FLOAT;
//            case SMALLINT:
//            case SMALLINT_UNSIGNED:
//            case MEDIUMINT:
//            case MEDIUMINT_UNSIGNED:
//                return HolubsqlType.FIELD_TYPE_SHORT;
//            case INT:
//            case INT_UNSIGNED:
//            case YEAR:
//                return HolubsqlType.FIELD_TYPE_LONG;
//            case DATE:
//                return HolubsqlType.FIELD_TYPE_DATE;
//            case TIME:
//                return HolubsqlType.FIELD_TYPE_TIME;
//            case TIMESTAMP:
//                return HolubsqlType.FIELD_TYPE_TIMESTAMP;
//            case DATETIME:
//                return HolubsqlType.FIELD_TYPE_DATETIME;
//            case BLOB:
//            case TEXT:
//                return HolubsqlType.FIELD_TYPE_BLOB;
//            case TINYBLOB:
//            case TINYTEXT:
//                return HolubsqlType.FIELD_TYPE_TINY_BLOB;
//            case MEDIUMBLOB:
//            case MEDIUMTEXT:
//                return HolubsqlType.FIELD_TYPE_MEDIUM_BLOB;
//            case LONGBLOB:
//            case LONGTEXT:
//                return HolubsqlType.FIELD_TYPE_LONG_BLOB;
//            //            case JSON:
//            //            case ENUM:
//            //            case SET:
//            //            case GEOMETRY:
//            default:
//                return HolubsqlType.FIELD_TYPE_VAR_STRING;
//        }
//    }
//
//    @Override
//    public String getString() {
//        if (this.valueEncoder == null) {
//            return "** NOT SPECIFIED **";
//        }
//        return this.valueEncoder.getString(this);
//    }
//
//    @Override
//    public String getName() {
//        return this.name;
//    }
//
//    @Override
//    public void setName(String name) {
//        this.name = name;
//    }
//
//}