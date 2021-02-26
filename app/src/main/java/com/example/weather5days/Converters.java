package com.example.weather5days;

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
}
