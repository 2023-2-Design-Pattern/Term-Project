package com.holub;

import com.holub.database.jdbc.JDBCResultSet;
import com.holub.rmi.HolubInterface;
import com.holub.rmi.serialobject.RMIResultSetAdapter;
import com.holub.rmi.serialobject.SerializableTest;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class RMIServer implements HolubInterface {
    Connection connection = null;

    public RMIServer() {
        JDBCConnect();
    }

    private void JDBCConnect() {
        try {
            Class.forName( "com.holub.database.jdbc.JDBCDriver" ) //{=JDBCTest.forName}
                    .newInstance();
            connection = DriverManager.getConnection(			//{=JDBCTest.getConnection}
                    "file:/C:/dp2023",
                    "harpo", "swordfish" );
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String testMethod() {
        return "test rmi";
    }

    @Override
    public SerializableTest testSerializable(String userId, String userName) {
        SerializableTest serial = new SerializableTest(userId, userName);
        return serial;
    }

    @Override
    public RMIResultSetAdapter executeQuery(String sqlQuery) throws RemoteException {
        try {
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sqlQuery);
            return new RMIResultSetAdapter((JDBCResultSet) result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int executeUpdate(String sqlString) throws RemoteException {
        try {
            Statement statement = connection.createStatement();

            int result = statement.executeUpdate(sqlString);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String args[]) {
        try {
            RMIServer server = new RMIServer();
            HolubInterface stub = (HolubInterface) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RMITest", stub);

            System.out.println("RMI server start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
