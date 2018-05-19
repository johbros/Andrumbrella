package com.sdu.andrumbrella.utilities;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michael on 22-Mar-18.
 */

public final class JsonUtils {

    public static String[] getWeatherFromJson(String jsonString) throws JSONException{

        final String LIST = "list";
        final String DATE = "dt_txt";
        final String MAIN = "main";
        final String WEATHER = "weather";
        final String DESCRIPTION = "description";
        final String ICON = "icon";
        final String MIN_TMP = "temp_min";
        final String MAX_TMP = "temp_max";
        final String MESSAGE_CODE = "cod";

        String[] parsedWeatherData;

        JSONObject jweather = new JSONObject(jsonString);

        if(jweather.has(MESSAGE_CODE)){
            int errorCode = jweather.getInt(MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }


        JSONArray weatherList = jweather.getJSONArray(LIST);
        parsedWeatherData = new String[weatherList.length()];
        for(int i = 0;i < weatherList.length();i++){
            JSONObject dayWeather = weatherList.getJSONObject(i);
            String date = dayWeather.getString(DATE);
            JSONObject main = dayWeather.getJSONObject(MAIN);
            JSONArray weatherDetails = dayWeather.getJSONArray(WEATHER);
            String description = null;
            String icon = null;
            for(int j = 0;j < weatherDetails.length();j++) {
                JSONObject details = weatherDetails.getJSONObject(j);
                description = details.getString(DESCRIPTION);
                icon = details.getString(ICON);
            }
            String max_temp = main.getString(MAX_TMP);
            String min_temp = main.getString(MIN_TMP);

            date = DateUtils.convertInLocalTime(date);

            if(date.split("\\s")[1].split(":")[0].equals("00")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date myDate = dateFormat.parse(date);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(myDate);
                    cal1.add(Calendar.DAY_OF_YEAR, -1);
                    Date previousDate = cal1.getTime();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String previousDate2 = df.format(previousDate);
                   String finaldDate = previousDate2.replace(previousDate2.split("\\s")[1], "24:00:00");
                   parsedWeatherData[i] = finaldDate + " "
                           + max_temp + " " + min_temp + " " + description.replace(" ", "/")  + "-" + icon;
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }else {
                parsedWeatherData[i] = date + " "
                        + max_temp + " " + min_temp + " " + description.replace(" ", "/") +  "-" + icon;
            }
        }
        return parsedWeatherData;
    }
}
