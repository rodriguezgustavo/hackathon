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

    public Session(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public String getSessionId() {
        return sessionId;
    }


}
