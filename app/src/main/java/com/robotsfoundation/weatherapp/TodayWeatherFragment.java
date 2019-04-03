package com.robotsfoundation.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.robotsfoundation.weatherapp.Retrofit.RetrofitClient;
import com.robotsfoundation.weatherapp.Retrofit.WeathApp;
import com.robotsfoundation.weatherapp.WeatherResult.Weather;
import com.robotsfoundation.weatherapp.WeatherResult.WeatherResult;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {



    ImageView img_weather;
    TextView txt_city_name,txt_humidity,txt_sunrise,txt_sunset,txt_pressure,txt_tempreture,txt_description,txt_date_time,txt_wind,txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;

CompositeDisposable compositeDisposable;
WeathApp mService;
    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance(){

        if(instance == null)
            instance = new TodayWeatherFragment();
        return instance;
    }

    public TodayWeatherFragment() {
       compositeDisposable =new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        mService=retrofit.create(WeathApp.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View itemView =inflater.inflate(R.layout.fragment_today_weather, container, false);

    img_weather=(ImageView)itemView.findViewById(R.id.img_weather);
    txt_city_name=(TextView)itemView.findViewById(R.id.txt_eity_name);
    txt_pressure=(TextView)itemView.findViewById(R.id.txt_pressure);
    txt_date_time=(TextView)itemView.findViewById(R.id.txt_date_time);
    txt_description=(TextView)itemView.findViewById(R.id.txt_description);
    txt_tempreture=(TextView)itemView.findViewById(R.id.txt_temperature);
    txt_sunset=(TextView)itemView.findViewById(R.id.txt_sunset);
    txt_sunrise=(TextView)itemView.findViewById(R.id.txt_sunrise);
    txt_humidity=(TextView)itemView.findViewById(R.id.txt_humidity);
    txt_geo_coord=(TextView)itemView.findViewById(R.id.txt_geo_coord);


    weather_panel=(LinearLayout) itemView.findViewById(R.id.weather_panel);
    loading =(ProgressBar)itemView.findViewById(R.id.loading);


    return itemView;

    }

    private void getWeatherInformation(){
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(com.robotsfoundation.weatherapp.weather.Weather.current_location),String.valueOf(com.robotsfoundation.weatherapp.weather.Weather.current_location.getLatitude()), com.robotsfoundation.weatherapp.weather.Weather.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<WeatherResult>() {
                               @Override
                               public void accept(WeatherResult weatherResult) throws Exception {
                                   Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                           .append(weatherResult.getWeather().get(0).getIcon())
                                           .append(".png ").toString()).into(img_weather);


                                   txt_city_name.setText(weatherResult.getName());
                                   txt_description.setText(new StringBuilder("Weather in")
                                           .append(weatherResult.getName().toString())
                                   );
                                   txt_tempreture.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                                           .append("°C").toString());

                                   txt_date_time.setText(Weather.convertUnixToDate(weatherResult.getDt()));
                                   txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());

                                   txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                                   txt_sunrise.setText(Weather.convertUnixToHour(weatherResult.getSys().getSunrise()));
                                   txt_sunset.setText(Weather.convertUnixToHour(weatherResult.getSys().getSunset()));
                                   txt_geo_coord.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());


                                   weather_panel.setVisibility(View.VISIBLE);
                                   loading.setVisibility(View.GONE);
                               }

                           },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
        );

    }


    }

