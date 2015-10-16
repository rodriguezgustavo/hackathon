package com.ml.hackathon.algorithms;

import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.domain.ShipperScore;
import com.ml.hackathon.domain.Order;

import java.util.*;


public class Scorer {

    private static final double MAX_DISTANCE   = 100; // km
    private static final double MIN_REPUTATION = 0;
    private static final int IDLE_MINUTES = 60;

    public static List<ShipperScore> getShippersForOrder (Order order, List<Shipper> candidates) {

        List<ShipperScore> scored = new ArrayList<>();

        LatLon target = new LatLon(-34, -54); // TODO sacar de order

        for (Shipper s: candidates) {

            if (!isAllowed(s)) continue;

            LatLon pos  = new LatLon(s.getLatitude(), s.getLongitude());
            double dist = distanceBetween(target, pos);
            if (dist > MAX_DISTANCE) continue;

            double score = calculateScore (s, dist);
            scored.add(new ShipperScore(s, score, dist));
        }

        Collections.sort(scored, SORT_BY_SCORE_DESC);

        return scored;
    }

    private static final Comparator<ShipperScore> SORT_BY_SCORE_DESC = new Comparator<ShipperScore>() {
        @Override
        public int compare(ShipperScore o1, ShipperScore o2) {
            int score = - o1.getScore().compareTo(o2.getScore());
            if (score == 0) {
                return o1.getDistance().compareTo(o2.getDistance());
            } else {
                return score;
            }
        }
    };

    private static boolean isAllowed (Shipper s) {
        return s.isActive() && s.getReputation() > MIN_REPUTATION && isAlive(s.getLastSeen());
    }

    private static boolean isAlive (Date lastSeen) {
        if (lastSeen == null) return false;
        long diff = new Date().getTime() - lastSeen.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        return diffMinutes < IDLE_MINUTES;

    }

    private static double calculateScore (Shipper s, double distanceTo) {
        // very smart algorithm
        DistanceRanking dr   = getDistanceRanking (distanceTo);
        ReputationRanking rr = getReputationRanking(s.getReputation());
        return 0.5 * rr.getWeight() + 0.5 * dr.getWeight();
    }

    private static DistanceRanking getDistanceRanking(double distanceTo) {
        if (distanceTo > 10) {
            return DistanceRanking.VERY_FAR;
        } else if (distanceTo > 8) {
            return DistanceRanking.FAR;
        } else if (distanceTo > 6) {
            return DistanceRanking.MIDDLE;
        } else if (distanceTo > 4) {
            return  DistanceRanking.CLOSE;
        } else {
            return DistanceRanking.VERY_CLOSE;
        }
    }

    private  static ReputationRanking getReputationRanking (int reputation) {
        if (reputation >= 5) {
            return ReputationRanking.HIGH;
        } else if (reputation >= 3) {
            return ReputationRanking.MIDDLE;
        } else {
            return ReputationRanking.LOW;
        }
    }

    private enum ReputationRanking {

        HIGH(5), MIDDLE(3), LOW(1);
        private int weight;
        int getWeight () { return weight; }

        ReputationRanking (int reputation) {
            weight = reputation;
        }
    }

    private enum DistanceRanking {
        VERY_CLOSE(5), CLOSE(4), MIDDLE(3), FAR(2), VERY_FAR(1);
        private int weight;

        int getWeight () { return weight; }

         DistanceRanking (int w) {
            weight = w;
        }
    }

    private static class LatLon {
        final double lat;
        final double lon;

        LatLon(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }


    private static double distanceBetween (LatLon x, LatLon y) {
        return distance(x.lat, x.lon, y.lat, y.lon, 'K');
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
