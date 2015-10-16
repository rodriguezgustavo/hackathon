package com.ml.hackathon.domain;

/**
 * Created by ekohan on 10/16/15.
 */
public class LatLon {
    private final Double lat;
    private final Double lon;

    public LatLon(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLatitude() {
        return lat;
    }

    public Double getLongitude() {
        return lon;
    }
}
