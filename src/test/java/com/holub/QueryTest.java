package com.holub;

import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.*;

public class QueryTest {
    String[] data = {
            "(1,  'John',   'Mon', 1, 'JustJoe')",
            "(2,  'JS',     'Mon', 1, 'Cappuccino')",
            "(3,  'Marie',  'Mon', 2, 'CaffeMocha')",
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
                    "  Entry      INTEGER      NOT NULL"   +
                    ", Customer   VARCHAR (20) NOT NULL"   +
                    ", DOW        VARCHAR (3)  NOT NULL"   +
                    ", Cups       INTEGER      NOT NULL"   +
                    ", Type       VARCHAR (10) NOT NULL"   +
                    ", PRIMARY KEY( Entry )"               +
                    ")"
        );
    }
}
