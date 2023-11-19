package com.holub;

import com.holub.rmi.HolubInterface;
import com.holub.rmi.serialobject.SerializableTest;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIClientTest {
    public static void main(String[] args) {
        new RMIClientTest().go();
    }

    public void go() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HolubInterface service = (HolubInterface) registry.lookup("RMITest");

            //System.out.println(service.testMethod());

            //SerializableTest serial = service.testSerializable("testId", "testName");
            //System.out.println(serial.userId + " " + serial.userName);
            System.out.println("Test");
            ArrayList arrayList = service.executeQuery("select one from existing").rowSet;

            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println(arrayList.get(i));
            }
            //System.out.println(service.executeQuery("select one from existing").getMetaData());
            //service.executeUpdate("insert into existing VALUES "+
            //        "('dooo', 'whap',  'choo')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
