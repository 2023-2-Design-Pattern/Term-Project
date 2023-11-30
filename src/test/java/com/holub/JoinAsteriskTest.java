package com.holub;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class JoinAsteriskTest {
	String[] data = {
		"(1,  13,  'a', 'gogo')",
		"(2,  2,   'g', 'ace' )",
		"(3,  549, 'd', 'game')",
		"(4,  49,  'c', 'game')",
		"(5,  49,  'c', 'game')",
	};
	String[] data2 = {
		"(20, 21, 1)",
		"(30, 31, 3)",
	};

	Connection connection;
	Statement statement;

	@Before
	public void setUp() throws Exception {
		Class.forName("com.holub.database.jdbc.JDBCDriver").newInstance();

		connection = DriverManager.getConnection("file:/C:/dp2023");
		statement = connection.createStatement();
		statement.executeUpdate(
			"create table test (" +
				"  One      INTEGER      NOT NULL" +
				", Two      INTEGER      NOT NULL" +
				", Three    VARCHAR (10) NOT NULL" +
				", Four     VARCHAR (10) NOT NULL" +
				", PRIMARY KEY( Entry )" +
				")"
		);

		statement.executeUpdate(
			"create table test2 (" +
				"  Five      INTEGER      NOT NULL" +
				", Six      INTEGER      NOT NULL" +
				", One_fk      INTEGER      NOT NULL" +
				", PRIMARY KEY( Entry )" +
				")"
		);

		for (String row : data) {
			statement.executeUpdate("insert into test VALUES " + row);
		}
		for (String row : data2) {
			statement.executeUpdate("insert into test2 VALUES " + row);
		}
	}

	@Test
	public void testJoinAsterisk() throws SQLException {
		String query = "select * from test, test2 where test.One = test2.One_fk";
		ResultSet rs = statement.executeQuery(query);

		ArrayList<Object[]> expect = new ArrayList<>();
		Object[] row1 = {13, "a", "gogo", "20", "21"};
		Object[] row2 = {549, "d", "game", "30", "31"};

		expect.add(row1);
		expect.add(row2);

		int row = 0;
		while (rs.next()) {
			assertEquals(expect.get(row)[0], rs.getInt("Two"));
			assertEquals(expect.get(row)[1], rs.getString("Three"));
			assertEquals(expect.get(row)[2], rs.getString("Four"));
            assertEquals(expect.get(row)[3], rs.getString("Five"));
            assertEquals(expect.get(row)[4], rs.getString("Six"));
			row++;
		}
		assertEquals(2, row);
	}
}
