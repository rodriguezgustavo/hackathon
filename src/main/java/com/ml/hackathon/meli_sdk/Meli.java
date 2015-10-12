package com.ml.hackathon.meli_sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import org.apache.http.HttpStatus;

public class Meli {
    public static String apiUrl = "https://api.mercadolibre.com";
    private String accessToken;
    private String refreshToken;
    private Long clientId;
    private String clientSecret;

    private Date lastRefreshTokenDate;
    private Long mlUserId;

    private AsyncHttpClient http;

    {
        AsyncHttpClientConfig cf = new AsyncHttpClientConfig.Builder()
                .setUserAgent("Mozilla/4.0").build();
        http = new AsyncHttpClient(cf);
    }

    public Meli(Long clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Meli(Long clientId, String clientSecret, String accessToken,
                String refreshToken,Date lastRefreshTokenDate) {
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.lastRefreshTokenDate=lastRefreshTokenDate;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public Date getLastRefreshTokenDate() { return lastRefreshTokenDate; }

    public long getMlUserId(){ return mlUserId; }

    public Response get(String path) throws MeliException {
        return get(path, new FluentStringsMap());
    }

    private BoundRequestBuilder prepareGet(String path, FluentStringsMap params) {
        return http.prepareGet(apiUrl + path)
                .addHeader("Accept", "application/json")
                .setQueryParameters(params);
    }

    private BoundRequestBuilder prepareDelete(String path,
                                              FluentStringsMap params) {
        return http.prepareDelete(apiUrl + path)
                .addHeader("Accept", "application/json")
                .setQueryParameters(params);
    }

    private BoundRequestBuilder preparePost(String path,
                                            FluentStringsMap params, String body) {
        return http.preparePost(apiUrl + path)
                .addHeader("Accept", "application/json")
                .setQueryParameters(params)
                .setHeader("Content-Type", "application/json").setBody(body)
                .setBodyEncoding("UTF-8");
    }

    private BoundRequestBuilder preparePut(String path,
                                           FluentStringsMap params, String body) {
        return http.preparePut(apiUrl + path)
                .addHeader("Accept", "application/json")
                .setQueryParameters(params)
                .setHeader("Content-Type", "application/json").setBody(body)
                .setBodyEncoding("UTF-8");
    }

    private BoundRequestBuilder preparePost(String path, FluentStringsMap params) {
        return http.preparePost(apiUrl + path)
                .addHeader("Accept", "application/json")
                .setQueryParameters(params);
    }

    public Response get(String path, FluentStringsMap params)
            throws MeliException {
        BoundRequestBuilder r = prepareGet(path, params);

        Response response;
        try {
            response = r.execute().get();
        } catch (Exception e) {
            throw new MeliException(e);
        }
        if (params.containsKey("access_token") && this.hasRefreshToken()
                && (response.getStatusCode() == HttpStatus.SC_UNAUTHORIZED || response.getStatusCode() == HttpStatus.SC_FORBIDDEN  || response.getStatusCode() == HttpStatus.SC_NOT_FOUND)) {
            try {
                refreshAccessToken();
            } catch (AuthorizationFailure e1) {
                return response;
            }
            params.replace("access_token", this.accessToken);
            r = prepareGet(path, params);

            try {
                response = r.execute().get();
            } catch (Exception e) {
                throw new MeliException(e);
            }
        }
        return response;
    }

    private void refreshAccessToken() throws AuthorizationFailure {
        FluentStringsMap params = new FluentStringsMap();
        params.add("grant_type", "refresh_token");
        params.add("client_id", String.valueOf(this.clientId));
        params.add("client_secret", this.clientSecret);
        params.add("refresh_token", this.refreshToken);
        BoundRequestBuilder req = preparePost("/oauth/token", params);

        parseToken(req);
    }

    public String getAuthUrl(String callback) {
        try {
            return "https://auth.mercadolibre.com.ar/authorization?response_type=code&client_id="
                    + clientId
                    + "&redirect_uri="
                    + URLEncoder.encode(callback, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "https://auth.mercadolibre.com.ar/authorization?response_type=code&client_id="
                    + clientId + "&redirect_uri=" + callback;
        }
    }

    public void authorize(String code, String redirectUri)
            throws AuthorizationFailure {
        FluentStringsMap params = new FluentStringsMap();

        params.add("grant_type", "authorization_code");
        params.add("client_id", String.valueOf(clientId));
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUri);

        BoundRequestBuilder r = preparePost("/oauth/token", params);

        parseToken(r);
    }

    private void parseToken(BoundRequestBuilder r) throws AuthorizationFailure {
        Response response = null;
        String responseBody = "";
        try {
            response = r.execute().get();
            responseBody = response.getResponseBody();
        } catch (InterruptedException e) {
            throw new AuthorizationFailure(e);
        } catch (ExecutionException e) {
            throw new AuthorizationFailure(e);
        } catch (IOException e) {
            throw new AuthorizationFailure(e);
        }

        JsonParser p = new JsonParser();
        JsonObject object;

        try {
            object = p.parse(responseBody).getAsJsonObject();
        } catch (JsonSyntaxException e) {
            throw new AuthorizationFailure(responseBody);
        }

        if (response.getStatusCode() == HttpStatus.SC_OK) {

            this.accessToken = object.get("access_token").getAsString();
            this.mlUserId=object.get("user_id").getAsLong();
            JsonElement jsonElement = object.get("refresh_token");
            this.refreshToken = jsonElement != null ? object.get(
                    "refresh_token").getAsString() : null;
            this.lastRefreshTokenDate=new Date();
        } else {
            throw new AuthorizationFailure(object.get("message").getAsString());
        }

    }

    private boolean hasRefreshToken() {
        return this.refreshToken != null && !this.refreshToken.isEmpty();
    }

    public Response post(String path, FluentStringsMap params, String body)
            throws MeliException {
        BoundRequestBuilder r = preparePost(path, params, body);

        Response response;
        try {
            response = r.execute().get();
        } catch (Exception e) {
            throw new MeliException(e);
        }
        if (params.containsKey("access_token") && this.hasRefreshToken()
                && response.getStatusCode() == 404) {
            try {
                refreshAccessToken();
            } catch (AuthorizationFailure e1) {
                return response;
            }
            params.replace("access_token", this.accessToken);
            r = preparePost(path, params, body);

            try {
                response = r.execute().get();
            } catch (Exception e) {
                throw new MeliException(e);
            }
        }
        return response;
    }

    public Response put(String path, FluentStringsMap params, String body)
            throws MeliException {
        BoundRequestBuilder r = preparePut(path, params, body);

        Response response;
        try {
            response = r.execute().get();
        } catch (Exception e) {
            throw new MeliException(e);
        }
        if (params.containsKey("access_token") && this.hasRefreshToken()
                && response.getStatusCode() == 404) {
            try {
                refreshAccessToken();
            } catch (AuthorizationFailure e1) {
                return response;
            }
            params.replace("access_token", this.accessToken);
            r = preparePut(path, params, body);

            try {
                response = r.execute().get();
            } catch (Exception e) {
                throw new MeliException(e);
            }
        }
        return response;
    }

    public Response delete(String path, FluentStringsMap params)
            throws MeliException {
        BoundRequestBuilder r = prepareDelete(path, params);

        Response response;
        try {
            response = r.execute().get();
        } catch (Exception e) {
            throw new MeliException(e);
        }
        if (params.containsKey("access_token") && this.hasRefreshToken()
                && response.getStatusCode() == 404) {
            try {
                refreshAccessToken();
            } catch (AuthorizationFailure e1) {
                return response;
            }
            params.replace("access_token", this.accessToken);
            r = prepareDelete(path, params);

            try {
                response = r.execute().get();
            } catch (Exception e) {
                throw new MeliException(e);
            }
        }
        return response;
    }

    public BoundRequestBuilder head(String path) {
        return null;
    }

    public BoundRequestBuilder options(String path) {
        return null;
    }
}