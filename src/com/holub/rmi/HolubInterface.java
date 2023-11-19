package com.holub.rmi;

import com.holub.rmi.serialobject.RMIResultSetAdapter;
import com.holub.rmi.serialobject.SerializableTest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HolubInterface extends Remote {
    public String testMethod() throws RemoteException;
    public SerializableTest testSerializable(String userId, String userName) throws RemoteException;
    public RMIResultSetAdapter executeQuery(String sqlQuery) throws RemoteException;
    public int executeUpdate(String sqlString) throws RemoteException;
}
