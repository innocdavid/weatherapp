package com.robotsfoundation.weatherapp.WeatherResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class Weather {

    private int id;
    private String main;
    private String description;
    private String  icon;

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


    //Constructor.....


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
