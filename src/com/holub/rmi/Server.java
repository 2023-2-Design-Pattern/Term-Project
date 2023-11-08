package com.holub.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements HolubInterface {
    public Server() {}

    @Override
    public String testMethod() {
        return "test rmi";
    }

    public static void main(String args[]) {
        try {
            Server server = new Server();
            HolubInterface stub = (HolubInterface) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RMITest", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
