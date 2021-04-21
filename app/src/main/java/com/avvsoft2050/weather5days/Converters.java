package com.avvsoft2050.weather5days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {
    public static String dateTime(String dateTxt, String format){
        String dateStr;
        DateFormat dfStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date date = dfStr.parse(dateTxt);
            DateFormat df = new SimpleDateFormat(format);
            dateStr = df.format(date);
        }catch (ParseException e){
            dateStr = "error";
        }
        return dateStr;
    }

    public static double celsiusToFahrenheit(double temperatureC){
        double temperatureF = 1.8 * temperatureC + 32;
        return temperatureF;
    }

    public static int getIconId(String icon){
        switch (icon){
            case "01d":
                return R.drawable.f01d;
            case "01n":
                return R.drawable.f01n;
            case "02d":
                return R.drawable.f02d;
            case "02n":
                return R.drawable.f02n;
            case "03d":
                return R.drawable.f03d;
            case "03n":
                return R.drawable.f03n;
            case "04d":
                return R.drawable.f04d;
            case "04n":
                return R.drawable.f04n;
            case "09d":
                return R.drawable.f09d;
            case "09n":
                return R.drawable.f09n;
            case "10d":
                return R.drawable.f10d;
            case "10n":
                return R.drawable.f10n;
            case "11d":
                return R.drawable.f11d;
            case "11n":
                return R.drawable.f11n;
            case "13d":
                return R.drawable.f13d;
            case "13n":
                return R.drawable.f13n;
            case "50d":
                return R.drawable.f50d;
            case "50n":
                return R.drawable.f50n;
        }
        return R.drawable.f01d;
    }
}
