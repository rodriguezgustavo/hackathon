package com.ml.hackathon.domain;

import com.ml.hackathon.db.UserDao;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by gurodriguez on 5/25/15.
 */
public class Session  implements Serializable{

    private String sessionId= UUID.randomUUID().toString();

    private User user;

    private List<Shipper> shippers;

    public Session(User user, List<Shipper> shippers) {
        this.user = user;
        this.shippers=shippers;
    }

    public User getUser() {
        return user;
    }


    public String getSessionId() {
        return sessionId;
    }

    public List<Shipper> getShippers() {
        return shippers;
    }



}
