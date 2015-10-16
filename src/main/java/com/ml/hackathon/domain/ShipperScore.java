package com.ml.hackathon.domain;

/**
 * Created by ekohan on 10/16/15.
 */
public class ShipperScore {

    private Shipper shipper;
    private Double  score;
    private Double  distance;

    public ShipperScore (Shipper s, double score, double distance) {
        shipper = s;
        this.score = score;
        this.distance = distance;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}