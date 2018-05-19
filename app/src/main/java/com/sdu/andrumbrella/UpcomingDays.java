package com.sdu.andrumbrella;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.sdu.andrumbrella.utilities.DateUtils;
import com.sdu.andrumbrella.utilities.JsonUtils;
import com.sdu.andrumbrella.utilities.NetworkUtils;

import java.net.URL;
import java.util.LinkedHashSet;

public class UpcomingDays extends AppCompatActivity implements UpcomingDaysAdapter.DayClickListener, LoaderManager.LoaderCallbacks<String[]> {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private UpcomingDaysAdapter mAdapter;
    private String countryCode;
    private String cityName;
    private boolean metric;
    public static String[] weatherData;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_days);

        mRecyclerView = findViewById(R.id.weather_recyclerview);
        mErrorMessage = findViewById(R.id.error_message);
        mProgressBar = findViewById(R.id.loading_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new UpcomingDaysAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Divider decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Intent searchIntent = getIntent();
        if (searchIntent.hasExtra("COUNTRY_CODE")) {
            countryCode = searchIntent.getStringExtra("COUNTRY_CODE");
        }
        if (searchIntent.hasExtra("CITY_NAME")) {
            cityName = searchIntent.getStringExtra("CITY_NAME");
        }
        if (searchIntent.hasExtra("SWITCH_STATE")) {
            metric = searchIntent.getBooleanExtra("SWITCH_STATE", metric);
        }

        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null, this);
        }

        loadDays();
    }


    @Override
    public void onDayClickListener(int clickedDay) {
        TextView clickedTextView = mRecyclerView.findViewHolderForAdapterPosition(clickedDay).itemView.findViewById(R.id.days);
        String clickedTextViewtoString = clickedTextView.getText().toString();
        Intent passForecast = new Intent(UpcomingDays.this, Forecast.class);
        passForecast.putExtra(Intent.EXTRA_TEXT, clickedTextViewtoString.split("\\s")[1]);
        passForecast.putExtra("SWITCH_STATE", metric);
        startActivity(passForecast);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.andrumbrella_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAdapter.removeItems();
        loadDays();
        return super.onOptionsItemSelected(item);
    }


    public void loadDays(){
        showWeather();
        mProgressBar.setVisibility(View.VISIBLE);
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryCity", cityName);
        queryBundle.putString("queryCountry",countryCode);
        getSupportLoaderManager().restartLoader(0,queryBundle,this);

    }


    private void showWeather(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);


    }

    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public Loader<String[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new WeatherLoader(this,args.getString("queryCity"),args.getString("queryCountry"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String[]> loader, String[] parsedWeatherResults) {
        if(parsedWeatherResults != null && !parsedWeatherResults.equals("")){
            showWeather();
            LinkedHashSet<String> upcomingDays = new LinkedHashSet<>();
            for(int i = 0;i < parsedWeatherResults.length;i++) {
                upcomingDays.add(DateUtils.getDayByName(parsedWeatherResults[i].split("\\s")[0]) + " " +

                        String.valueOf(DateUtils.getDayByNumber(parsedWeatherResults[i].split("\\s")[0])) + " "
                        + DateUtils.getMonthByName(parsedWeatherResults[i].split("\\s")[0], context));
            }
            weatherData = parsedWeatherResults;
            mProgressBar.setVisibility(View.INVISIBLE);
            mAdapter.setWeatherData(upcomingDays.toArray(new String[upcomingDays.size()]));
        }else{
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String[]> loader) {

    }
}