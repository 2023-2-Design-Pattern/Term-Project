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

/***
 * @include /etc/license.txt
 */

public class JDBCTest
{
	static String[] data =
	{	"(1,  'John',   'Mon', 1, 'JustJoe')",
		"(2,  'JS',     'Mon', 1, 'Cappuccino')",
		"(3,  'Marie',  'Mon', 2, 'CaffeMocha')",
	};
	
	private static Connection connection = null;
	private static Statement  statement  = null;

	public static boolean batchExecution(String sql, String[] datas) throws Exception{
		int[] count = new int[datas.length];
		boolean result = true;

		try {
			connection = DriverManager.getConnection(			//{=JDBCTest.getConnection}
					"file:/c:/src/com/holub/database/jdbc/Dbase",
					"harpo", "swordfish" );
//			statement = connection.createStatement();

			statement.executeUpdate(
					"create table test (" +
							"  Entry      INTEGER      NOT NULL"   +
							", Customer   VARCHAR (20) NOT NULL"   +
							", DOW        VARCHAR (3)  NOT NULL"   +
							", Cups       INTEGER      NOT NULL"   +
							", Type       VARCHAR (10) NOT NULL"   +
							", PRIMARY KEY( Entry )"               +
							")"
			);
//			for( int i = 0; i < data.length; ++i )
//				statement.executeUpdate(
//						"insert into test VALUES "+ data[i] );
			// autoCommit off
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);		// sql + "(?, ?, ?, ?, ?)" 
			for (String s : datas) {
				statement.addBatch(s);                    // PreparedStatement - addBatch()
				System.out.println("statement test: "+sql+s);
			}
			count = statement.executeBatch();
			connection.commit();
		} catch (SQLException e){
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
		} finally{
			try{
				connection.setAutoCommit(true);
			}catch (SQLException e){
				e.printStackTrace();
			}
			connection.close();
		}

		for (int j : count) {
			System.out.println("실행결과: "+j);
			// over 0, -2 success
			if (j < 0) {
				if (j != -2)
					result = false;
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception
	{
		Class.forName( "com.holub.database.jdbc.JDBCDriver" ) //{=JDBCTest.forName}
												.newInstance();
		//boolean result = batchExecution("insert into test VALUES ", data);
		//System.out.println("result:" + result);
		try
		{	connection = DriverManager.getConnection(			//{=JDBCTest.getConnection}
							"file:/c:/src/com/holub/database/jdbc/Dbase",
							"harpo", "swordfish" );

			statement = connection.createStatement();
		
			statement.executeUpdate(
				"create table test (" +
				 "  Entry      INTEGER      NOT NULL"   +
				 ", Customer   VARCHAR (20) NOT NULL"   +
				 ", DOW        VARCHAR (3)  NOT NULL"   +
				 ", Cups       INTEGER      NOT NULL"   +
				 ", Type       VARCHAR (10) NOT NULL"   +
				 ", PRIMARY KEY( Entry )"               +
				 ")"
			);

			for( int i = 0; i < data.length; ++i )
				statement.executeUpdate(
						"insert into test VALUES "+ data[i] );

			// Test Autocommit stuff. If everything's working
			// correctly, there James should be in the databse,
			// but Fred should not.

			connection.setAutoCommit( false );
			statement.executeUpdate(
						"insert into test VALUES "+
						"(4, 'James',  'Thu', 1, 'Cappuccino')" );
			connection.commit();

			statement.executeUpdate(
						"insert into test (Customer) VALUES('Fred')");
			connection.rollback();
			connection.setAutoCommit( true );

			// Print everything.

			ResultSet result = statement.executeQuery( "select * from test" );
			while( result.next() )
			{	System.out.println
				(	  result.getInt("Entry")		+ ", "
					+ result.getString("Customer")	+ ", "
					+ result.getString("DOW")		+ ", "
					+ result.getInt("Cups")			+ ", "
					+ result.getString("Type")
				);
			}
		}
		finally
		{
		  try{ if(statement != null) statement.close(); }catch(Exception e){}
		  try{ if(connection!= null) connection.close();}catch(Exception e){}
		}
	}
}
