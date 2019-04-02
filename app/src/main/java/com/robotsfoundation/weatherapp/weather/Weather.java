package com.robotsfoundation.weatherapp.weather;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class Weather {

    public static final String APP_ID = " c418491452923bfe74a802b16f3ea876";
    public static Location current_location = null;

    public static String convertUnixToDate(long dt){
        Date date=new Date(dt*1000lL);
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm EEE MM yyy");
        String formatted =sdf.format(date);
        return formatted;
    }
    public static String convertUnitToHour(int dt){

        Date date=new Date(dt*1000lL);
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        String formatted =sdf.format(date);
        return  formatted;
    }
}
