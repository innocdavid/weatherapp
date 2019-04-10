package com.robotsfoundation.weatherapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

<<<<<<< HEAD
    public static Retrofit instance;
=======
    private static Retrofit instance;
>>>>>>> d5ae016495d8ec575927951c495cef5eb6b040ae

    public static Retrofit getInstance(){

        if(instance == null)
<<<<<<< HEAD

=======
>>>>>>> d5ae016495d8ec575927951c495cef5eb6b040ae
            instance = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
<<<<<<< HEAD


        return  instance;
=======
        return instance;
>>>>>>> d5ae016495d8ec575927951c495cef5eb6b040ae
    }
}
