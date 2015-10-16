package com.ml.hackathon.domain;

import java.util.Date;

/**
 * Created by gurodriguez on 10/10/15.
 */
public class Shipper {

    private int id;

    private boolean active;

    private String name;

    private String vehicle;

    private String email;

    private Integer reputation;

    private Double latitude;

    private Double longitude;

    private String shipperType;

    private String token;
    private Date lastSeen;

    public Shipper(int id,String name,boolean active,String shipperType,String vehicle,String email,Integer reputation,Double latitude,Double longitude,String token, Date lastSeen){
        this.id=id;
        this.name=name;
        this.active=active;
        this.shipperType=shipperType;
        this.vehicle = vehicle;
        this.email=email;
        this.reputation=reputation;
        this.latitude=latitude;
        this.longitude=longitude;
        this.token=token;
        this.lastSeen = lastSeen;
    }

    public Shipper(){

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getShipperType() {
        return shipperType;
    }

    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLastSeen (Date ts) { lastSeen = ts; }
    public Date getLastSeen () { return lastSeen; }
}
