package com.holub.rmi;

import com.holub.rmi.test.SerializableTest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HolubInterface extends Remote {
    public String testMethod() throws RemoteException;
    public SerializableTest testSerializable(String userId, String userName) throws RemoteException;
}
