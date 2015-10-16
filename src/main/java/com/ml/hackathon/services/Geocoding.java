package com.ml.hackathon.services;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.ml.hackathon.config.Config;
import com.ml.hackathon.domain.LatLon;

/**
 * Created by ekohan on 10/16/15.
 */
public class Geocoding {

    public static LatLon geocode (String address) throws Exception {
        GeoApiContext context = new GeoApiContext().setApiKey(Config.GEOCODING_KEY);

        try {
            GeocodingResult[] results = GeocodingApi.newRequest(context)
                    .region("ar")
                    .address(address)
                    .await();
            Double lat = results[0].geometry.location.lat;
            Double lon = results[0].geometry.location.lng;
            return new LatLon(lat, lon);

        } catch (Exception e) {
            throw e;
        }

    }
}
