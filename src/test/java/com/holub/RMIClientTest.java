package com.holub;

import com.holub.rmi.HolubInterface;
import com.holub.rmi.serialobject.SerializableTest;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RMIClientTest {
    Registry registry;
    HolubInterface service;

    @Before
    public void setUp() {
        /**
         * RMIServer 클래스의 main 함수 실행 후 별도 jvm으로 실행
         */
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            service = (HolubInterface) registry.lookup("RMITest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTestMethod() throws RemoteException {
        String result = service.testMethod();
        assertEquals("test rmi", result);
    }

    @Test
    public void testTestSerializable() throws RemoteException {
        SerializableTest result = service.testSerializable("123", "lsj");
        assertNotNull(result);
        assertEquals("123", result.userId);
        assertEquals("lsj", result.userName);
    }

    @Test
    public void testExecuteQuery() throws RemoteException {
        ArrayList<Object[]> arrayList = service.executeQuery("select * from existing").rowSet;

        assertEquals("doo", arrayList.get(0)[0]);
        assertEquals("wha", arrayList.get(0)[1]);
        assertEquals("ditty", arrayList.get(0)[2]);
    }

    @Test
    public void testExecuteUpdate() throws RemoteException {
        service.executeUpdate("insert into existing VALUES (\"doo\", \"whap\", \"choo-ah\")");
        ArrayList<Object[]> arrayList = service.executeQuery("select * from existing").rowSet;

        assertEquals("doo", arrayList.get(arrayList.size() - 1)[0]);
        assertEquals("whap", arrayList.get(arrayList.size() - 1)[1]);
        assertEquals("choo-ah", arrayList.get(arrayList.size() - 1)[2]);
    }
}
