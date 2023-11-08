package com.holub.rmi.test;

import java.io.Serializable;

public class SerializableTest implements Serializable {
    public String userId;
    public String userName;

    public SerializableTest(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
