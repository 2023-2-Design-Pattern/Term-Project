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
			{		"(1,  'John',   'Mon', 1, 'JustJoe')",
					"(2,  'JS',     'Mon', 1, 'Cappuccino')",
					"(3,  'Marie',  'Mon', 2, 'CaffeMocha')",
			};

	public static void main(String[] args) throws Exception
	{
		Class.forName( "com.holub.database.jdbc.JDBCDriver" ) //{=JDBCTest.forName}
				.newInstance();

		Connection connection = null;
		Statement  statement  = null;
		PreparedStatement preparedStatement = null;
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

			/*TEST: batch implementation*/
			connection.setAutoCommit( false );
			statement.addBatch(
					"insert into test VALUES "+
							"(4, 'James',  'Thu', 1, 'Cappuccino')" );
			statement.addBatch(
					"insert into test (Customer) VALUES('Fred')");
			int[] batch_result = statement.executeBatch();
			connection.commit();
			connection.rollback();
			connection.setAutoCommit( true );
			/*Finish batch implementation test*/

			/*TEST: Prepared Statement, PSTMT */
			System.out.println("Prepared Statement 테스트");
			String pstmt_sql = "select * from test where Entry = 1";
			preparedStatement = connection.prepareStatement(pstmt_sql);
//			preparedStatement.setInt(1, 1);
//			preparedStatement.setString(2, "Mon");
			ResultSet result = preparedStatement.executeQuery();
			System.out.println("----err1?");
//			while(rs.next()){
//				System.out.println(rs.getInt(1)+ "\t" + rs.getString(2));
//			}
			//rs.close();

			preparedStatement.close();
			/*Finish prepared statement test*/

			// Print everything.

			//ResultSet result = statement.executeQuery( "select * from test" );
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