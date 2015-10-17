package com.ml.hackathon.services;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.ml.hackathon.config.Config;
import com.ml.hackathon.domain.LatLon;

/**
 * Created by ekohan on 10/16/15.
 */
public class Geocoding {

    private static class AddressInfo {
        public String country;
        public String state;
        public String city;
        public String address_line;
        public String zip_code;

        @Override
        public String toString() {

            String s = address_line.trim();
            if (city != null && city.trim() != "") {
                s += ", " + city.trim();
            }

            if (state != null && state.trim() != "") {
                s += ", " + state.trim();
            }

            if (country != null && country.trim() != "") {
                s += ", " + country.trim();
            }

            return s;
        }
    }

    public static LatLon geocode (String address) throws Exception {
        GeoApiContext context = new GeoApiContext().setApiKey(Config.GEOCODING_KEY);

        try {

            AddressInfo info = new Gson().fromJson(address, AddressInfo.class);


            GeocodingResult[] results = GeocodingApi.newRequest(context)
                    .region("ar")
                    .address(info.toString())
                    .await();
            Double lat = results[0].geometry.location.lat;
            Double lon = results[0].geometry.location.lng;
            return new LatLon(lat, lon);

        } catch (Exception e) {
            throw e;
        }

    }
}
