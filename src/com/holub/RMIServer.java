package com.holub;

import com.holub.rmi.HolubInterface;
import com.holub.rmi.serialobject.SerializableTest;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer implements HolubInterface {
    public RMIServer() {}

    @Override
    public String testMethod() {
        return "test rmi";
    }

    @Override
    public SerializableTest testSerializable(String userId, String userName) {
        SerializableTest serial = new SerializableTest(userId, userName);
        return serial;
    }

    public static void main(String args[]) {
        try {
            RMIServer server = new RMIServer();
            HolubInterface stub = (HolubInterface) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RMITest", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
