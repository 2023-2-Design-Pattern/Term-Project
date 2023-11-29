package com.holub;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class QueryTest {
    String[] data = {
            "(1,  13,  'a', 'gogo')",
            "(2,  2,   'g', 'ace' )",
            "(3,  549, 'd', 'game')",
            "(4,  49,  'c', 'game')",
            "(5,  49,  'c', 'game')",
    };

    Connection connection;
    Statement statement;

    @Before
    public void setUp() throws Exception {
        Class.forName( "com.holub.database.jdbc.JDBCDriver" ).newInstance();

        connection = DriverManager.getConnection("file:/C:/dp2023");
        statement = connection.createStatement();
        statement.executeUpdate(
            "create table test (" +
                    "  One      INTEGER      NOT NULL"   +
                    ", Two      INTEGER      NOT NULL"   +
                    ", Three    VARCHAR (10) NOT NULL"   +
                    ", Four     VARCHAR (10) NOT NULL"   +
                    ", PRIMARY KEY( Entry )"             +
                    ")"
        );

        for(String row : data) {
            statement.executeUpdate("insert into test VALUES "+ row);
        }
    }

    @Test
    public void testDistinct() throws SQLException {
        String query = "select distinct Two, Three, Four from test";
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Object[]> expect = new ArrayList<>();
        Object[] row1 = {13, "a", "gogo"};
        Object[] row2 = {2, "g", "ace"};
        Object[] row3 = {549, "d", "game"};
        Object[] row4 = {49, "c", "game"};

        expect.add(row1);
        expect.add(row2);
        expect.add(row3);
        expect.add(row4);

        int row = 0;
        while(rs.next()) {
            assertEquals(expect.get(row)[0], rs.getInt("Two"));
            assertEquals(expect.get(row)[1], rs.getString("Three"));
            assertEquals(expect.get(row)[2], rs.getString("Four"));
            row++;
        }
        assertEquals(4, row);
    }

    @Test
    public void testOrderByDefault() throws SQLException {
        String query = "select * from test order by Two, One";
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Object[]> expect = new ArrayList<>();
        Object[] row1 = {1, 13, "a", "gogo"};
        Object[] row2 = {2, 2, "g", "ace"};
        Object[] row3 = {3, 549, "d", "game"};
        Object[] row4 = {4, 49, "c", "game"};
        Object[] row5 = {5, 49, "c", "game"};

        expect.add(row2);
        expect.add(row1);
        expect.add(row4);
        expect.add(row5);
        expect.add(row3);

        int row = 0;
        while(rs.next()) {
            assertEquals(expect.get(row)[0], rs.getInt("One"));
            assertEquals(expect.get(row)[1], rs.getInt("Two"));
            assertEquals(expect.get(row)[2], rs.getString("Three"));
            assertEquals(expect.get(row)[3], rs.getString("Four"));
            row++;
        }
        assertEquals(5, row);
    }

    @Test
    public void testOrderByDesc() throws SQLException {
        String query = "select * from test order by Two, One desc";
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Object[]> expect = new ArrayList<>();
        Object[] row1 = {1, 13, "a", "gogo"};
        Object[] row2 = {2, 2, "g", "ace"};
        Object[] row3 = {3, 549, "d", "game"};
        Object[] row4 = {4, 49, "c", "game"};
        Object[] row5 = {5, 49, "c", "game"};

        expect.add(row3);
        expect.add(row5);
        expect.add(row4);
        expect.add(row1);
        expect.add(row2);

        int row = 0;
        while(rs.next()) {
            assertEquals(expect.get(row)[0], rs.getInt("One"));
            assertEquals(expect.get(row)[1], rs.getInt("Two"));
            assertEquals(expect.get(row)[2], rs.getString("Three"));
            assertEquals(expect.get(row)[3], rs.getString("Four"));
            row++;
        }
        assertEquals(5, row);
    }

    @Test
    public void testOrderByString() throws SQLException {
        String query = "select * from test order by Three, One asc";
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Object[]> expect = new ArrayList<>();
        Object[] row1 = {1, 13, "a", "gogo"};
        Object[] row2 = {2, 2, "g", "ace"};
        Object[] row3 = {3, 549, "d", "game"};
        Object[] row4 = {4, 49, "c", "game"};
        Object[] row5 = {5, 49, "c", "game"};

        expect.add(row1);
        expect.add(row4);
        expect.add(row5);
        expect.add(row3);
        expect.add(row2);

        int row = 0;
        while(rs.next()) {
            assertEquals(expect.get(row)[0], rs.getInt("One"));
            assertEquals(expect.get(row)[1], rs.getInt("Two"));
            assertEquals(expect.get(row)[2], rs.getString("Three"));
            assertEquals(expect.get(row)[3], rs.getString("Four"));
            row++;
        }
        assertEquals(5, row);
    }
}
