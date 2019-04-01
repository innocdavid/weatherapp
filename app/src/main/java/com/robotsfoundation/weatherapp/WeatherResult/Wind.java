package com.robotsfoundation.weatherapp.WeatherResult;

public class Wind {

    private double speed;
    private int deg;

    //Constructor.
    public Wind(){

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
