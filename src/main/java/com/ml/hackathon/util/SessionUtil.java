package com.ml.hackathon.util;

import com.ml.hackathon.domain.Session;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by gurodriguez
 */
public class SessionUtil {
    private static final String SESSION_INFO="SESSION_INFO";

    public static Map<String,Session> openSessions=new HashMap<>();

    private static ExternalContext getExternalContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static void addCurrentSession(Session session){
        openSessions.put(session.getSessionId(), session);
        getExternalContext().getSessionMap().put(SessionUtil.SESSION_INFO,session);
    }

    public static Session getCurrentSession(){
        return (Session)getExternalContext().getSessionMap().get(SESSION_INFO);
    }

    public static List<Session> getOpenSessions(){
        return new ArrayList<>(openSessions.values());
    }
}
