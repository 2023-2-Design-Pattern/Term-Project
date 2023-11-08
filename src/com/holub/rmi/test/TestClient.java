package com.holub.rmi.test;

import com.holub.rmi.HolubInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public static void main(String[] args) {
        new TestClient().go();
    }

    public void go() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HolubInterface service = (HolubInterface) registry.lookup("RMITest");

            System.out.println(service.testMethod());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
