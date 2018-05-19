package com.sdu.andrumbrella;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Michael on 25-Mar-18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{

    private ArrayList<String> forecastData;

    public void setForecastData(ArrayList<String> data){
        forecastData = data;
        notifyDataSetChanged();
    }


    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForecast = R.layout.forecast_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForecast, parent, false);
        return new ForecastAdapter.ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        final String url = "http://openweathermap.org/img/w/";
        String mForecastData = forecastData.get(position).split("-")[0];
        String iconId = forecastData.get(position).split("-")[1];
        holder.mForecast.setText(mForecastData);
        Picasso.get().load(url + iconId + ".png").into(holder.mWeatherIcon);
    }



    @Override
    public int getItemCount() {
        if(forecastData == null) {
            return 0;
        }else{
            return forecastData.size();
        }
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{

        final private TextView mForecast;
        final private ImageView mWeatherIcon;

        public ForecastViewHolder(View view){
            super(view);
            mForecast = view.findViewById(R.id.forecast);
            mWeatherIcon = view.findViewById(R.id.weatherIcon);

        }

    }


}
