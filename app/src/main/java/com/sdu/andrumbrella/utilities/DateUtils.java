package com.sdu.andrumbrella.utilities;

import android.content.Context;
import android.support.annotation.Nullable;

import com.sdu.andrumbrella.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

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
