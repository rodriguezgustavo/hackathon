package com.ml.hackathon.domain;

/**
 * @author mlabarinas
 */
public enum OrderStatus {

    PENDING("PENDING"),
    PROCESSED("PROCESSED"),
    ERROR("ERROR");

    private static final long serialVersionUID = 1L;

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}