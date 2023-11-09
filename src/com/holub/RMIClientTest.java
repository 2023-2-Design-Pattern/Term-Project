package com.holub;

import com.holub.rmi.HolubInterface;
import com.holub.rmi.serialobject.SerializableTest;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClientTest {
    public static void main(String[] args) {
        new RMIClientTest().go();
    }

    public void go() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HolubInterface service = (HolubInterface) registry.lookup("RMITest");

            System.out.println(service.testMethod());

            SerializableTest serial = service.testSerializable("testId", "testName");
            System.out.println(serial.userId + " " + serial.userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
