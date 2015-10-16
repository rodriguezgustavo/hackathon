package com.ml.hackathon.domain;

import java.io.Serializable;

/**
 * Created by gurodriguez on 10/16/15.
 */
public class Order implements Serializable{

    private Integer id;
    private String orderId;

    public Order(Integer id,String orderId){
        this.id=id;
        this.orderId=orderId;
    }

    public Integer getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }


}
