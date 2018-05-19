package com.sdu.andrumbrella;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.sdu.andrumbrella.utilities.JsonUtils;
import com.sdu.andrumbrella.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class WeatherLoader extends AsyncTaskLoader<String[]> {

    private String mCity;
    private String mCountry;
    public WeatherLoader(@NonNull Context context, String city, String country) {
        super(context);
       mCity= city;
       mCountry=country;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public String[] loadInBackground() {
        URL weatherUrl = NetworkUtils.buildUrl(mCity, mCountry);
        String weatherResults;
        try{
            weatherResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            String[] parsedWeatherList = JsonUtils.getWeatherFromJson(weatherResults);
            return parsedWeatherList;
        }catch (Exception e) {
            return null;
        }
    }
}
