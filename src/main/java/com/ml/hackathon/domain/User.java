package com.ml.hackathon.domain;

import java.io.Serializable;

/**
 * Created by gurodriguez on 5/18/15.
 */
public class User implements Serializable {
    private int userId;
    private String userName;
    private String name;
    private String surname;

    public User(int userId,String userName,String name,String surname){
        this.userId=userId;
        this.userName=userName;
        this.name=name;
        this.surname=surname;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getUserId() { return userId; }


}
