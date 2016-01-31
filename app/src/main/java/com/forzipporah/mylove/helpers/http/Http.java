package com.forzipporah.mylove.helpers.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by prince on 1/10/16.
 * Class to hold Http Methods such as checking if there's an internet connection
 */
public class Http {

    // http://192.168.0.5/versatile/projectz/cms/Backend/api/poem
    public static final String HOST_DOMAIN = "http://192.168.0.5/";

    // http://192.168.0.5/ - local
    // http://myprincess.esy.es/ - production
    public static final  String SCHEME = "http";
    private static final String ENV    = "local";
    // 192.168.1.5 - local
    // myprincess.esy.es - production
    public static        String DOMAIN = "192.168.0.5";

    static {
        if (ENV.equals("local")) {
            DOMAIN = "192.168.0.5";
        } else {
            DOMAIN = "myprincess.esy.es";
        }
    }

    /**
     * Check for network connectivity
     *
     * @return boolean True on success false on failure
     */
    public static boolean isNetworkAvailable(Context context) {
        // Connectivity manager                                 // Get the connectivity service
        ConnectivityManager manager     = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         networkInfo = manager.getActiveNetworkInfo(); // check to see if network is present and active

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

}
