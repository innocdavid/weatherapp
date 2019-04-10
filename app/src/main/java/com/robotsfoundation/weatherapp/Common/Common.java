package com.robotsfoundation.weatherapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String APP_ID = "d6f8874abd4dffcef602636c9fb0bd46";
    public static Location current_location = null;

    public static String convertUnixToDate(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm EEE MM yyy");
        String formatted =sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        String formatted =sdf.format(date);
        return  formatted;
    }

}