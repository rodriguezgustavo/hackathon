package com.ml.hackathon.domain;

/**
 * @author mlabarinas
 */
public enum OrderStatus {

    PENDING("PENDING"),
    INVITATION_SENT("INVITATION_SENT"),
    ACCEPTED("ACCEPTED"),
    PICKED_UP("PICKED_UP"),
    DELIVERED("DELIVERED");

    private static final long serialVersionUID = 1L;

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}