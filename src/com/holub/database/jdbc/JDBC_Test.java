package com.holub.database.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JDBC_Test {

    // given
    String[] data =
            {"(1,  'John',   'Mon', 1, 'JustJoe')",
                    "(2,  'JS',     'Mon', 1, 'Cappuccino')",
                    "(3,  'Marie',  'Mon', 2, 'CaffeMocha')",
            };
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;

    @Before
    public void setUp() throws Exception {
        Class.forName("com.holub.database.jdbc.JDBCDriver").newInstance();
        connection = DriverManager.getConnection(            //{=JDBCTest.getConnection}
                "file:/Users/bb8/2023_Fall/DesignPattern/new-Term-Project/Term-Project/src/com/holub/database/jdbc/Dbase",
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

        for (String d : data){
            statement.executeUpdate(
                    "insert into test VALUES " + d);
        }
    }

    @Test
    public void batchTest() throws Exception{
        /*TEST: batch implementation*/
        connection.setAutoCommit( false );
        statement.addBatch(
                "insert into test VALUES "+
                        "(4, 'Minki', 'Wed', 1, 'Americano')" );
        statement.addBatch(
                "insert into test VALUES "+"(5, 'kang', 'thu', 2, 'CaffeLatte')");
        int[] batch_result = statement.executeBatch();
        connection.commit();
        connection.rollback();
        connection.setAutoCommit( true );

        ResultSet result1 = statement.executeQuery("select * from test");
        StringBuilder str_result = new StringBuilder();
        while (result1.next()) {
            str_result.append(result1.getInt("Entry")).append(", ")
                    .append(result1.getString("Customer")).append(", ")
                    .append(result1.getString("DOW")).append(", ")
                    .append(result1.getInt("Cups")).append(", ")
                    .append(result1.getString("Type")).append("\n");
        }
        String answer = "1, John, Mon, 1, JustJoe\n" +
                "2, JS, Mon, 1, Cappuccino\n" +
                "3, Marie, Mon, 2, CaffeMocha\n" +
                "4, Minki, Wed, 1, Americano\n" +
                "5, kang, thu, 2, CaffeLatte\n";
        assertThat(str_result.toString(),equalTo(answer));
        statement.clearBatch();
        /*Finish batch implementation test*/
    }

    @Test
    public void preparedTest() throws SQLException {
        String pstmt_sql = "insert into test VALUES ( ?, ?, ?, ?, ? )";
        preparedStatement = connection.prepareStatement(pstmt_sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "\'kang\'");
        preparedStatement.setString(3, "\'thu\'");
        preparedStatement.setInt(4, 2);
        preparedStatement.setString(5, "\'black tea\'");
        preparedStatement.executeUpdate();

        ResultSet result = preparedStatement.executeQuery("select * from test");
        StringBuilder str_result = new StringBuilder();
        while (result.next()) {
            str_result.append(result.getInt("Entry")).append(", ")
                    .append(result.getString("Customer")).append(", ")
                    .append(result.getString("DOW")).append(", ")
                    .append(result.getInt("Cups")).append(", ")
                    .append(result.getString("Type")).append("\n");
        }
        String answer = "1, John, Mon, 1, JustJoe\n" +
                "2, JS, Mon, 1, Cappuccino\n" +
                "3, Marie, Mon, 2, CaffeMocha\n" +
                "1, kang, thu, 2, black tea\n";
        assertThat(str_result.toString(),equalTo(answer));
        preparedStatement.clearBatch();
        preparedStatement.close();
    }

    @Test
    public void preparedBatchTest() throws SQLException {
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
        StringBuilder str_result = new StringBuilder();
        while (result.next()) {
            str_result.append(result.getInt("Entry")).append(", ")
                    .append(result.getString("Customer")).append(", ")
                    .append(result.getString("DOW")).append(", ")
                    .append(result.getInt("Cups")).append(", ")
                    .append(result.getString("Type")).append("\n");
        }
        String answer= "1, John, Mon, 1, JustJoe\n" +
                "2, JS, Mon, 1, Cappuccino\n" +
                "3, Marie, Mon, 2, CaffeMocha\n" +
                "7, kang, thu, 2, black tea\n";
        assertThat(str_result.toString(),equalTo(answer));
        preparedStatement.clearBatch();
        preparedStatement.close();
    }

    @After
    public void setOff() throws SQLException {
        if (statement != null) statement.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }
}