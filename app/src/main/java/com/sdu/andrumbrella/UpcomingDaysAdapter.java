package com.sdu.andrumbrella;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Michael on 19-Mar-18.
 */

public class UpcomingDaysAdapter extends RecyclerView.Adapter<UpcomingDaysAdapter.UpcomingDaysViewHolder> {

    private String[] upcomingDays;
    private final DayClickListener mOnClickListener;


    public UpcomingDaysAdapter(DayClickListener listener){
        mOnClickListener = listener;
    }

    public void setWeatherData(String[] day){
        upcomingDays = day;
        notifyDataSetChanged();
    }

    public void removeItems(){
        upcomingDays = null;
        notifyDataSetChanged();
    }

    public interface DayClickListener{
        void onDayClickListener(int clickedDay);
    }


    public class UpcomingDaysViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final private TextView mWeather;

        public UpcomingDaysViewHolder(View view){
            super(view);
            mWeather = view.findViewById(R.id.days);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onDayClickListener(clickedPosition);
        }
    }

    @NonNull
    @Override
    public UpcomingDaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListDays = R.layout.upcoming_days_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForListDays, parent, false);
        return new UpcomingDaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingDaysViewHolder holder, int position) {
        String mWeatherData = upcomingDays[position];
        holder.mWeather.setText(mWeatherData);
    }

    @Override
    public int getItemCount() {
        if(upcomingDays == null) {
            return 0;
        }else{
            return upcomingDays.length;
        }
    }
}
