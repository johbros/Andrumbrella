package com.sdu.andrumbrella.utilities;


import android.util.Log;

import com.sdu.andrumbrella.UpcomingDays;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.StrictMath.abs;

/**
 * Created by Michael on 25-Mar-18.
 */

public class WeatherUtils {


    public static ArrayList<String> getWeatherData(String day, Boolean metric) {
        ArrayList<String> clickedDayData = new ArrayList<>();
        boolean flag;
        if (Integer.valueOf(day) < 10) {
            day = "0" + day;
        }

        //Get Current Day
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());
        //Check if user picked current date
        if(currentDate.split("-")[2].equals(day)){
            flag = true;
        }else{
            flag = false;
        }

        for (int i = 0; i < UpcomingDays.weatherData.length; i++) {
            if (UpcomingDays.weatherData[i].split("\\s")[0].split("-")[2].equals(day)) {
                //If Metric
                if (metric) {
                    //If time slots are ok
                    if (Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 < 0) {
                        flag = true;
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From:  " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 + diff) + ":00       "
                                + "To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                                + "Min:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    //If time slots are not ok
                    } else {
                        clickedDayData.add("From:  " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3) + ":00       "
                                + "To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                                + "Min:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    }
                //If Imperial
                } else {
                    //If time slots are ok
                    if (Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 < 0) {
                        flag = true;
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From:  " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 + diff) + ":00       "
                                + "To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°F\n"
                                + "Min:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°F\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    //If time slots are not ok
                    } else {
                        clickedDayData.add("From:  " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3) + ":00       "
                                + "To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°F\n"
                                + "Min:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°F\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    }
                }
            }
        }

        //Fill in time slot till 24:00
        if (flag) {
            day = String.valueOf(Integer.valueOf(day) + 1);
            for (int i = 0; i < UpcomingDays.weatherData.length; i++) {
                if (UpcomingDays.weatherData[i].split("\\s")[0].split("-")[2].equals(day)) {
                    if (metric) {
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From:  " + String.valueOf(24 - diff) + ":00       "
                                + "To: " + "24:00" + "\n\n\n"
                                + "Max:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                                + "Min:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    } else {
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From:  " + String.valueOf(24 - diff) + ":00       "
                                + "To: " + "24:00" + "\n\n\n"
                                + "Max:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°F\n"
                                + "Min:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°F\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    }
                    break;
                }
            }
        }
        return clickedDayData;
    }


    //Convert Kelvin to Celsius
    private static float kToC(float kelvinDegrees) {
        float celsius = kelvinDegrees - 273.15F;
        return Float.parseFloat(String.format(Locale.US, "%.2f", celsius));
    }

    //Convert Kelvin to Fahrenheit
    private static float kToF(float kelvinDegrees) {
        float fahrenheit = (float) 1.8 * (kelvinDegrees - 273) + 32;
        return Float.parseFloat(String.format(Locale.US, "%.2f", fahrenheit));
    }
}

