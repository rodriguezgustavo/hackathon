package com.ml.hackathon.ui.views;

import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.ui.views.util.LazyOrdersDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by gurodriguez on 10/12/15.
 */
@ManagedBean(name= OrdersView.BEAN_NAME)
@ViewScoped
public class OrdersView implements Serializable{
    public static final String BEAN_NAME="ordersView";

    private LazyOrdersDataModel ordersDataModel;
    private ApplicationControllerBean appBean;

    @PostConstruct
    public void init(){
        appBean=(ApplicationControllerBean) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(ApplicationControllerBean.BEAN_NAME);
        ordersDataModel= new LazyOrdersDataModel(appBean.getOrders());
    }

    private String message="Sin arrancar";

    private boolean refreshing;

    public String getMessage(){
        return message;
    }

    public boolean isRefreshing(){
        return refreshing;
    }


    public void process() {

    }

}