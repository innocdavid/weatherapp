package com.robotsfoundation.weatherapp.Retrofit;

import com.robotsfoundation.weatherapp.Model.WeatherForecastResult;
import com.robotsfoundation.weatherapp.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lon,
                                                 @Query("appid") String appid,
                                                 @Query("units") String units);

    @GET("weather")
    Observable<WeatherResult> getWeatherByCity(@Query("lat") String name,
                                                 @Query("appid") String id,
                                                 @Query("units") String units);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                                 @Query("lon") String lon,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String units);
}
