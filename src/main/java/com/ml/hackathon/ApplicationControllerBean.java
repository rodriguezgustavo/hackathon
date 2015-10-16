package com.ml.hackathon;


// Import required java libraries

import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.db.UserDao;
import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.Session;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.domain.User;
import com.ml.hackathon.util.SessionUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurodriguez
 */
@ManagedBean(name = ApplicationControllerBean.BEAN_NAME, eager = true)
@ApplicationScoped
public class ApplicationControllerBean implements Serializable {
    public static final String BEAN_NAME="application";

    private  List<Shipper> shippers;

    @PostConstruct
    public void init() {
        shippers=ShippersDao.getShippers();
        //TODO: get orders
    }

    public List<Shipper> getShippers(){
        return shippers;
    }

    public List<Order> getOrders(){
        return new ArrayList<>();
    }

}