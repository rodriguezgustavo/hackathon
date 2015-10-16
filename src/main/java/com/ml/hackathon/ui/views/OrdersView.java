package com.ml.hackathon.ui.views;

import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by gurodriguez on 10/12/15.
 */
@ManagedBean(name= OrdersView.BEAN_NAME)
@ViewScoped
public class OrdersView implements Serializable{
    public static final String BEAN_NAME="ordersView";

    private String message="Sin arrancar";

    private boolean refreshing;

    public String getMessage(){
        return message;
    }

    public boolean isRefreshing(){
        return refreshing;
    }

    public void process(){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    refreshing=true;
                    message="Primer intento";
                    Thread.sleep(5000);
                    message="Segundo intento";
                    Thread.sleep(5000);
                    message="Tercer intento";
                    Thread.sleep(5000);
                    message="Terminado";
                    refreshing=false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

}
