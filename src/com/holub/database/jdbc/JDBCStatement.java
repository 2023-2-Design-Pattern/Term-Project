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
package com.holub.database.jdbc;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.holub.database.*;
import com.holub.database.jdbc.adapters.*;

/***
 * @include /etc/license.txt
 */
public class JDBCStatement extends StatementAdapter
{
	private Database database;
	private static List<String> sql_batch = new ArrayList<>();
	protected volatile JDBCConnection connection = null;

	public JDBCStatement(Database database)
	{	this.database = database;
	}

	public JDBCStatement(JDBCConnection c, Database database) {
		this.connection = c;
		this.database =database;
	}

	public int executeUpdate(String sqlString) throws SQLException
	{	try
	{	database.execute( sqlString );
		return database.affectedRows();
	}
	catch( Exception e )
	{	throw new SQLException( e.getMessage() );
	}
	}

	public ResultSet executeQuery(String sqlQuery) throws SQLException
	{	try
	{	Table result = database.execute( sqlQuery );
		return new JDBCResultSet( result.rows() );
	}
	catch( Exception e )
	{	throw new SQLException( e.getMessage() );
	}
	}

	public void close() throws SQLException
	{	// does nothing.
	}

	public void addBatch(String sql) throws SQLException {
		if (sql != null){
			sql_batch.add(sql);
		}
	}

	public int[] executeBatch() throws SQLException {
		int updateCounts[] = new int[sql_batch.size()];
		try {
			if (sql_batch != null) {
				int nbrCommands = sql_batch.size();
				for (int i = 0; i < nbrCommands; i++) {
					updateCounts[i] = EXECUTE_FAILED; // set error value(-3)
				}
				int commandIndex = 0;
				for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) {
					try {
						String sql = sql_batch.get(commandIndex);
						updateCounts[commandIndex] = executeUpdate(sql);
					} catch (SQLException ex) {
						updateCounts[commandIndex] = EXECUTE_FAILED;
					}
				}
			}
			return updateCounts != null ? updateCounts : new int[0];
		}catch (Exception e) {
			System.out.println("error!");
			throw new SQLException(e.getMessage());
		}finally {
			clearBatch();
		}
	}

	public void clearBatch() throws SQLException {
		sql_batch.clear();
	}

	protected JDBCConnection checkClosed() throws SQLException {
		JDBCConnection c = this.connection;

		if (c == null){
			throw new SQLException("Statement.AlreadyClosed");
		}
		return c;
	}

}
