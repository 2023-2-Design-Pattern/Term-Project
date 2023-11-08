package com.holub.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HolubInterface extends Remote {
    public String testMethod() throws RemoteException;
}
