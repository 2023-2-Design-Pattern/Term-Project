package com.holub.database.jdbc;

import org.junit.Test;

import java.sql.*;

public class MyJDBCTest {
    // given
    static String[] data =
            {"(1,  'John',   'Mon', 1, 'JustJoe')",
                    "(2,  'JS',     'Mon', 1, 'Cappuccino')",
                    "(3,  'Marie',  'Mon', 2, 'CaffeMocha')",
            };
    static Connection connection = null;
    static Statement statement = null;
    static PreparedStatement preparedStatement = null;

    public static void main(String[] args) throws Exception {
        Class.forName("com.holub.database.jdbc.JDBCDriver") //{=JDBCTest.forName}
                .newInstance();

        try {
            connection = DriverManager.getConnection(            //{=JDBCTest.getConnection}
                    "file:/c:/src/com/holub/database/jdbc/Dbase",
                    "harpo", "swordfish");

            statement = connection.createStatement();

            statement.executeUpdate(
                    "create table test (" +
                            "  Entry      INTEGER      NOT NULL" +
                            ", Customer   VARCHAR (20) NOT NULL" +
                            ", DOW        VARCHAR (3)  NOT NULL" +
                            ", Cups       INTEGER      NOT NULL" +
                            ", Type       VARCHAR (10) NOT NULL" +
                            ", PRIMARY KEY( Entry )" +
                            ")"
            );

            for (int i = 0; i < data.length; ++i)
                statement.executeUpdate(
                        "insert into test VALUES " + data[i]);

            // Test Autocommit stuff. If everything's working
            // correctly, there James should be in the databse,
            // but Fred should not.

            connection.setAutoCommit(false);
            statement.executeUpdate(
                    "insert into test VALUES " +
                            "(4, 'James',  'Thu', 1, 'Cappuccino')");
            connection.commit();
            connection.rollback();
            connection.setAutoCommit(true);

            // Print everything.
            ResultSet result = statement.executeQuery("select * from test");
            while (result.next()) {
                System.out.println
                        (result.getInt("Entry") + ", "
                                + result.getString("Customer") + ", "
                                + result.getString("DOW") + ", "
                                + result.getInt("Cups") + ", "
                                + result.getString("Type")
                        );
            }
            System.out.println("\nbatch test start");
            batchTest(connection, statement);
            System.out.println("batch test end\n");

            System.out.println("\nprepared statement test start");
            preparedTest(connection, statement, preparedStatement);
            System.out.println("prepared statement test end\n");

            System.out.println("\nprepared statement batch test start");
            preparedBatchTest(connection, statement, preparedStatement);
            System.out.println("prepared statement batch test end\n");


        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
            }
        }
    }

    @Test
    static void batchTest(Connection connection, Statement statement) throws Exception{
        /*TEST: batch implementation*/
        connection.setAutoCommit( false );
        statement.addBatch(
                "insert into test VALUES "+
                        "(5, 'Minki', 'Wed', 1, 'Americano')" );
        statement.addBatch(
                "insert into test VALUES "+"(6, 'yeon', 'thu', 2, 'CaffeLatte')");
        int[] batch_result = statement.executeBatch();
        connection.commit();
        connection.rollback();
        connection.setAutoCommit( true );

        ResultSet result = statement.executeQuery("select * from test");
        while (result.next()) {
            System.out.println
                    (result.getInt("Entry") + ", "
                            + result.getString("Customer") + ", "
                            + result.getString("DOW") + ", "
                            + result.getInt("Cups") + ", "
                            + result.getString("Type")
                    );
        }
        statement.clearBatch();
        /*Finish batch implementation test*/
    }

    @Test
    static void preparedTest(Connection connection, Statement statement, PreparedStatement preparedStatement) throws SQLException {
        System.out.println("Prepared Statement 테스트");
//        String pstmt_sql = "select * from test where Entry = ?";

        String pstmt_sql = "insert into test VALUES ( ?, ?, ?, ?, ? )";
        preparedStatement = connection.prepareStatement(pstmt_sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "\'kang\'");
        preparedStatement.setString(3, "\'thu\'");
        preparedStatement.setInt(4, 2);
        preparedStatement.setString(5, "\'black tea\'");
        preparedStatement.executeUpdate();

        ResultSet result = preparedStatement.executeQuery("select * from test");
        while( result.next() )
        {	System.out.println
                (	  result.getInt("Entry")		+ ", "
                        + result.getString("Customer")	+ ", "
                        + result.getString("DOW")		+ ", "
                        + result.getInt("Cups")			+ ", "
                        + result.getString("Type")
                );
        }
        preparedStatement.clearBatch();
        preparedStatement.clearParameters();

        preparedStatement.close();

    }

    @Test
    static void preparedBatchTest(Connection connection, Statement statement, PreparedStatement preparedStatement) throws SQLException {
        String pstmt_sql = "insert into test VALUES ( ?, ?, ?, ?, ? )";
        preparedStatement = connection.prepareStatement(pstmt_sql);
        preparedStatement.setInt(1, 7);
        preparedStatement.setString(2, "\'kang\'");
        preparedStatement.setString(3, "\'thu\'");
        preparedStatement.setInt(4, 2);
        preparedStatement.setString(5, "\'black tea\'");
        preparedStatement.addBatch();
        int[] batch_result = preparedStatement.executeBatch();
        ResultSet result = preparedStatement.executeQuery("select * from test");
        while( result.next() )
        {	System.out.println
                (	  result.getInt("Entry")		+ ", "
                        + result.getString("Customer")	+ ", "
                        + result.getString("DOW")		+ ", "
                        + result.getInt("Cups")			+ ", "
                        + result.getString("Type")
                );
        }
        preparedStatement.clearBatch();
        preparedStatement.clearParameters();
        preparedStatement.close();

    }
}