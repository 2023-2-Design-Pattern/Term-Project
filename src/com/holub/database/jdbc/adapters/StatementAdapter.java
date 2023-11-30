/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database.jdbc.adapters;
import com.holub.database.jdbc.JDBCConnection;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/***
 * @include /etc/license.txt
 */
public class StatementAdapter implements java.sql.Statement, java.sql.PreparedStatement
{
	public StatementAdapter() {}

	public void setFetchSize(int fetchSize) throws SQLException {throw new SQLException("Statement.setFetchSize(int fetchSize) not supported");}
	public int getFetchSize() throws SQLException {throw new SQLException("Statement.getFetchSize() not supported");}
	public int getMaxRows() throws SQLException {throw new SQLException("Statement.getMaxRows() not supported");}
	public void setMaxRows(int max) throws SQLException {throw new SQLException("Statement.setMaxRows(int max) not supported");}
	public void setFetchDirection(int fetchDirection) throws SQLException {throw new SQLException("Statement.setFetchDirection(int fetchDirection) not supported");}
	public int getFetchDirection() throws SQLException {throw new SQLException("Statement.getFetchDirection() not supported");}
	public int getResultSetConcurrency() throws SQLException {throw new SQLException("Statement.getResultSetConcurrency() not supported");}
	public int getResultSetHoldability() throws SQLException {throw new SQLException("Statement.getResultSetHoldability() not supported");}
	public int getResultSetType() throws SQLException {throw new SQLException("Statement.getResultSetType() not supported");}
	public void setQueryTimeout(int seconds) throws SQLException {throw new SQLException("Statement.setQueryTimeout(int seconds) not supported");}
	public int getQueryTimeout() throws SQLException {throw new SQLException("Statement.getQueryTimeout() not supported");}
	public ResultSet getResultSet() throws SQLException {throw new SQLException("Statement.getResultSet() not supported");}
	public ResultSet executeQuery(String sql) throws SQLException {		throw new SQLException("Statement.executeQuery(String sql) not supported");}
	public int executeUpdate(String sql, int i) throws SQLException {throw new SQLException("Statement.executeUpdate(String sql, int i) not supported");}
	public int executeUpdate(String sql, String[] cols) throws SQLException {throw new SQLException("Statement.executeUpdate(String sql, String[] cols) not supported");}
	public boolean execute(String sql) throws SQLException {throw new SQLException("Statement.execute(String sql) not supported");}
	public boolean execute(String sql, String[] cols) throws SQLException {throw new SQLException("Statement.execute(String sql, String[] cols) not supported");}
	public boolean execute(String sql, int i) throws SQLException {throw new SQLException("Statement.execute(String sql, int i) not supported");}
	public boolean execute(String sql, int[] cols) throws SQLException {throw new SQLException("Statement.execute(String sql, int[] cols) not supported");}
	public void cancel() throws SQLException {throw new SQLException("Statement.cancel() not supported");}
	public void clearWarnings() throws SQLException {throw new SQLException("Statement.clearWarnings() not supported");}
	public Connection getConnection() throws SQLException {throw new SQLException("Statement.getConnection() not supported");}
	public ResultSet getGeneratedKeys() throws SQLException {throw new SQLException("Statement.getGeneratedKeys() not supported");}
	public void addBatch(String sql) throws SQLException {throw new SQLException("Statement.addBatch(String sql) not supported");}
	public int[] executeBatch() throws SQLException {throw new SQLException("not supported");}
	public void clearBatch() throws SQLException {throw new SQLException("Statement.clearBatch() not supported");}
	public void close() throws SQLException {throw new SQLException("Statement.close() not supported");}
	public int executeUpdate(String sql, int[] i) throws SQLException {throw new SQLException("Statement.executeUpdate(String sql, int[] i) not supported");}
	public int executeUpdate(String sql) throws SQLException {throw new SQLException("Statement.executeUpdate(String sql) not supported");}
	public int getMaxFieldSize() throws SQLException {throw new SQLException("Statement.getMaxFieldSize() not supported");}
	public boolean getMoreResults() throws SQLException {throw new SQLException("Statement.getMoreResults() not supported");}
	public boolean getMoreResults(int i) throws SQLException {throw new SQLException("Statement.getMoreResults(int i) not supported");}
	public int getUpdateCount() throws SQLException {throw new SQLException("Statement.getUpdateCount() not supported");}
	public SQLWarning getWarnings() throws SQLException {throw new SQLException("Statement.getWarnings() not supported");}
	public void setCursorName(String name) throws SQLException {throw new SQLException("Statement.setCursorName(String name) not supported");}
	public void setEscapeProcessing(boolean enable) throws SQLException {throw new SQLException("Statement.setEscapeProcessing(boolean enable) not supported");}
	public void setMaxFieldSize(int max) throws SQLException {throw new SQLException("Statement.setMaxFieldSize(int max) not supported");}
	// checkClosed(): public -> protected, void -> JDBCConnection
	protected JDBCConnection checkClosed() throws SQLException {throw new SQLException("Statement.checkClosed() not supported");}
	// adding PreparedStatement
	public <T> T unwrap(Class<T> iface) throws SQLException {throw new SQLException("PreparedStatement.unwrap() not supported");}
	public boolean isWrapperFor(Class<?> iface) throws SQLException {throw new SQLException("PreparedStatement.isWrapperFor() not supported");}
	public boolean isClosed() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setPoolable(boolean poolable) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public boolean isPoolable() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void closeOnCompletion() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public boolean isCloseOnCompletion() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public ResultSet executeQuery() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public int executeUpdate() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setNull(int parameterIndex, int sqlType) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setByte(int parameterIndex, byte x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setShort(int parameterIndex, short x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setInt(int parameterIndex, int x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setLong(int parameterIndex, long x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setFloat(int parameterIndex, float x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setDouble(int parameterIndex, double x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setString(int parameterIndex, String x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setDate(int parameterIndex, Date x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setTime(int parameterIndex, Time x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void clearParameters() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setObject(int parameterIndex, Object x) throws SQLException {throw new SQLException("PreparedStatement.setObject() not supported");}
	public boolean execute() throws SQLException {throw new SQLException("PreparedStatement.execute() not supported");}
	public void addBatch() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setRef(int parameterIndex, Ref x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBlob(int parameterIndex, Blob x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setClob(int parameterIndex, Clob x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setArray(int parameterIndex, Array x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public ResultSetMetaData getMetaData() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setURL(int parameterIndex, URL x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public ParameterMetaData getParameterMetaData() throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setRowId(int parameterIndex, RowId x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setNString(int parameterIndex, String value) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setNClob(int parameterIndex, NClob value) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {throw new SQLException("PreparedStatement.isClosed() not supported");}
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {throw new SQLException("PreparedStatement.setBinaryStream() not supported");}
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {throw new SQLException("PreparedStatement.setCharacterStream() not supported");}
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {throw new SQLException("PreparedStatement.setNCharacterStream() not supported");}
	public void setClob(int parameterIndex, Reader reader) throws SQLException {throw new SQLException("PreparedStatement.setClob() not supported");}
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {throw new SQLException("PreparedStatement.setBlob() not supported");}
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {throw new SQLException("PreparedStatement.setNClob() not supported");}}
