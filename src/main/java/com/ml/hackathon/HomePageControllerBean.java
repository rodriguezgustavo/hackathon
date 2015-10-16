package com.ml.hackathon;


// Import required java libraries

import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.db.UserDao;
import com.ml.hackathon.domain.*;
import com.ml.hackathon.util.SessionUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gurodriguez
 */
@ManagedBean(name = HomePageControllerBean.BEAN_NAME)
@SessionScoped
public class HomePageControllerBean implements Serializable {
    public static final String BEAN_NAME="homepage";

    public static final String INDEX_URL="../index.xhtml";

    private String centerPage="locations";

    private String sessionId;

    private ExternalContext getExternalContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    @PostConstruct
    public void init() {
        User currentUser=UserDao.getUserInfo(getExternalContext().getRemoteUser());
        Session currentSession=new Session(currentUser,ShippersDao.getShippers());
        SessionUtil.addCurrentSession(currentSession);
        sessionId=currentSession.getSessionId();
        System.out.println("Account Session started for: " + currentSession.getUser().getUserName() + "," + sessionId);
        centerPage="locations";
    }

    @PreDestroy
    public void finish() {
        if(SessionUtil.openSessions.remove(sessionId)!=null){
            System.out.println("Account Session removed: " +sessionId);
        }
    }

    public String getName(){
        return SessionUtil.getCurrentSession().getUser().getName();
    }

    public void doNav() {
        setCenterPage(FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("page"));
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("itemsView");
    }

    public String getCenterPage(){
        return centerPage;
    }

    public void setCenterPage(String centerPage){
        this.centerPage=centerPage;
    }

    public void doLogout() throws IOException {
        try {
            ExternalContext ec = getExternalContext();
            ec.invalidateSession();
            ec.redirect(INDEX_URL);
        }catch(NullPointerException e){
            System.out.println("Exception at logout");
        }
    }

    public List<Shipper> getShippers(){
        return SessionUtil.getCurrentSession().getShippers();
    }

}