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

            System.out.println("Test Start");

            System.out.println(service.testMethod());

            SerializableTest serial = service.testSerializable("testId", "testName");
            System.out.println(serial.userId + " " + serial.userName);

            ArrayList<Object[]> arrayList = service.executeQuery("select * from existing").rowSet;

            for (Object[] objects : arrayList) {
                for (int j = 0; j < arrayList.get(0).length; j++) {
                    System.out.print(objects[j] + " ");
                }
                System.out.println();
            }
            /*
            System.out.println(service.executeQuery("select one from existing").getMetaData());
            service.executeUpdate("insert into existing VALUES "+
                    "('dooo', 'whap',  'choo')");
            */

            System.out.println("Test End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
