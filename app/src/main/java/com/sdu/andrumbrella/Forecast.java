package com.sdu.andrumbrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.sdu.andrumbrella.utilities.WeatherUtils;

public class Forecast extends AppCompatActivity {

    private RecyclerView forecastRecyclerView;
    private TextView forecast_error;
    private ForecastAdapter fAdapter;
    private Intent dayIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        forecastRecyclerView = findViewById(R.id.rv_forecast);
        forecast_error = findViewById(R.id.forecast_error_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        forecastRecyclerView.setLayoutManager(layoutManager);
        fAdapter = new ForecastAdapter();
        forecastRecyclerView.setAdapter(fAdapter);

        //Divider decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(forecastRecyclerView.getContext(),
                layoutManager.getOrientation());
        forecastRecyclerView.addItemDecoration(dividerItemDecoration);
        dayIndex = getIntent();
        loadForecast();
    }




    public void loadForecast() {
        forecast_error.setVisibility(View.INVISIBLE);
        forecastRecyclerView.setVisibility(View.VISIBLE);
        if (dayIndex.hasExtra(Intent.EXTRA_TEXT) && dayIndex.hasExtra("SWITCH_STATE")) {
            fAdapter.setForecastData(WeatherUtils.getWeatherData(dayIndex.getStringExtra(Intent.EXTRA_TEXT), dayIndex.getBooleanExtra("SWITCH_STATE", false)));
        }
    }


}
