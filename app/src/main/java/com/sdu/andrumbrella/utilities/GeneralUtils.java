package com.sdu.andrumbrella.utilities;


import android.content.Context;
import android.support.annotation.Nullable;

import com.sdu.andrumbrella.R;
import com.sdu.andrumbrella.UpcomingDays;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.StrictMath.abs;

/**
 * Created by Michael on 25-Mar-18.
 */

public class GeneralUtils {


    public static ArrayList<String> getWeatherData(String day, Boolean metric) {
        ArrayList<String> clickedDayData = new ArrayList<>();
        boolean flag = false;
        if (Integer.valueOf(day) < 10) {
            day = "0" + day;
        }
        for (int i = 0; i < UpcomingDays.weatherData.length; i++) {
            if (UpcomingDays.weatherData[i].split("\\s")[0].split("-")[2].equals(day)) {
                if (metric) {
                    if (Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 < 0) {
                        flag = true;
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From: " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 + diff) + ":00"
                                + "        To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                                + "Min:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    } else {
                        clickedDayData.add("From: " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3) + ":00"
                                + "        To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                                + "Min:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    }
                } else {
                    if (Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 < 0) {
                        flag = true;
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From: " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3 + diff) + ":00"
                                + "         To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°F\n"
                                + "Min:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°F\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    } else {
                        clickedDayData.add("From: " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3) + ":00"
                                + "         To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                                + "Max:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°F\n"
                                + "Min:    " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°F\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));
                    }
                }
            }
        }

        if (flag) {
            day = String.valueOf(Integer.valueOf(day) + 1);
            for (int i = 0; i < UpcomingDays.weatherData.length; i++) {
                if (UpcomingDays.weatherData[i].split("\\s")[0].split("-")[2].equals(day)) {
                    if (metric) {
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From: " + String.valueOf(24 - diff) + ":00"
                                + "        To: " + "24:00" + "\n\n\n"
                                + "Max:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                                + "Min:    " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                                + "Description: " + UpcomingDays.weatherData[i].split("\\s")[4].replace("/", " "));

                    } else {
                        int diff = abs(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3);
                        clickedDayData.add("From: " + String.valueOf(24 - diff) + ":00"
                                + "        To: " + "24:00" + "\n\n\n"
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


    //Get Name of a month
    public static String getMonthByName(String date, Context context) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = inFormat.parse(date);
            SimpleDateFormat outFormat = new SimpleDateFormat("MM");
            return getMonth(outFormat.format(newDate), context);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get Name of a day
    public static String getDayByName(String date) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = inFormat.parse(date);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            return outFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Parse date
    @Nullable
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    //Convert UTC to Local Time
    public static String convertInLocalTime(String serverDate) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            TimeZone utcZone = TimeZone.getTimeZone("UTC");
            sdf.setTimeZone(utcZone);// Set UTC time zone
            Date myDate = sdf.parse(serverDate);
            sdf.setTimeZone(TimeZone.getDefault());// Set device time zone
            strDate = sdf.format(myDate);
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
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

    //Get number of a day
    public static int getDayByNumber(String date) {
        Date newDate = parseDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    //Get month name
    private static String getMonth(String month, Context context) {
        String monthString;
        switch (month) {
            case "01":
                monthString = context.getResources().getString(R.string.month1);
                break;
            case "02":
                monthString = context.getResources().getString(R.string.month2);
                break;
            case "03":
                monthString = context.getResources().getString(R.string.month3);
                break;
            case "04":
                monthString = context.getResources().getString(R.string.month4);
                break;
            case "05":
                monthString = context.getResources().getString(R.string.month5);
                break;
            case "06":
                monthString = context.getResources().getString(R.string.month6);
                break;
            case "07":
                monthString = context.getResources().getString(R.string.month7);
                break;
            case "08":
                monthString = context.getResources().getString(R.string.month8);
                break;
            case "09":
                monthString = context.getResources().getString(R.string.month9);
                break;
            case "10":
                monthString = context.getResources().getString(R.string.month10);
                break;
            case "11":
                monthString = context.getResources().getString(R.string.month11);
                break;
            case "12":
                monthString = context.getResources().getString(R.string.month12);
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
    }


}

