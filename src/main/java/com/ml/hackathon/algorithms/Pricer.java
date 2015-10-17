package com.ml.hackathon.algorithms;

import com.ml.hackathon.domain.ShipperScore;

/**
 * Created by ekohan on 10/16/15.
 */
public class Pricer {

    private static final Double FIXED_COST = 20.0;
    private static final Double KM_COST    = 5.0;

    public static Double getPriceForShipping (ShipperScore score) {
        return FIXED_COST + Math.ceil(score.getDistance()) * KM_COST;
    }
}
