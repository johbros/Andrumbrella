package com.sdu.andrumbrella.utilities;

import android.net.Uri;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Michael on 21-Mar-18.
 */

public class NetworkUtils {

    private static final String BASE_API_CURRENT_DAY_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "appid";
    private static final String api_key = "9286d1d64052c5ea55e0a13f7eba1d4a";
    private static final String LOCATION_QUERY = "q";

    public static URL buildUrl(String city, String countryCode) {
        String location = city + "," + countryCode;
        Uri builtUri = Uri.parse(BASE_API_CURRENT_DAY_URL).buildUpon().appendQueryParameter(LOCATION_QUERY, location)
                .appendQueryParameter(API_KEY, api_key).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
