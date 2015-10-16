package com.ml.hackathon.domain;

/**
 * Created by ekohan on 10/16/15.
 */
public class ShipperScore {
    public final Shipper shipper;
    public final Double  score;
    public final Double  distance;

    public ShipperScore (Shipper s, double score, double distance) {
        shipper = s;
        this.score = score;
        this.distance = distance;
    }
}
