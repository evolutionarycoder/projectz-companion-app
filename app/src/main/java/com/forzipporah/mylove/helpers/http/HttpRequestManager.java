package com.forzipporah.mylove.helpers.http;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prince on 1/27/16.
 */
public class HttpRequestManager {
    private Uri.Builder mBuilder;
    private String      mRequestType;

    public HttpRequestManager(String folder, String file, String queryParam) {
        mBuilder = new Uri.Builder();
        mRequestType = "GET";
        mBuilder.scheme(Http.SCHEME)
                .authority(Http.DOMAIN)
                .appendPath("web")
                .appendPath("projectz")
                .appendPath("cms")
                .appendPath("Backend")
                .appendPath("api")
                .appendPath(folder)
                .appendPath(file)
                .appendQueryParameter(queryParam, "");
    }

    public String connect() throws IOException {
        URL               syncUrl    = new URL(getUrl());
        StringBuilder     sb         = null;
        HttpURLConnection connection = (HttpURLConnection) syncUrl.openConnection();
        connection.setRequestMethod(mRequestType);
        connection.setInstanceFollowRedirects(true);
        connection.setDefaultUseCaches(false);
        connection.setUseCaches(false);
        HttpURLConnection.setFollowRedirects(true);
        connection.connect();

        // means connection successful
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response.close();
            reader.close();
        }
        connection.disconnect();

        if (sb != null && sb.toString().length() != 0) {
            return sb.toString();
        }
        return null;
    }

    public void setRequestType(String requestType) {
        mRequestType = requestType;
    }

    public String getUrl() {
        return mBuilder.toString();
    }

}
