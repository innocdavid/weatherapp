package com.robotsfoundation.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.robotsfoundation.weatherapp.Common.Common;
import com.robotsfoundation.weatherapp.Model.WeatherResult;
import com.robotsfoundation.weatherapp.Retrofit.IOpenWeatherMap;
import com.robotsfoundation.weatherapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class City extends Fragment {

    private List<String> cityLists;
    private MaterialSearchBar materialSearchBar;

    ImageView img_weather;
    TextView txt_city_name,txt_humidity,txt_sunrise,txt_sunset,txt_pressure,txt_tempreture,txt_description,txt_date_time,txt_wind,txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static City instance;

    public static City getInstance(){

        if(instance == null)
            instance = new City();
        return instance;
    }


    public City() {
        // Required empty public constructor

        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View itemView = inflater.inflate(R.layout.fragment_city, container, false);

        img_weather=(ImageView)itemView.findViewById(R.id.img_weather);
        txt_city_name=(TextView)itemView.findViewById(R.id.txt_city_name);
        txt_humidity=(TextView)itemView.findViewById(R.id.txt_humidity);
        txt_sunrise=(TextView)itemView.findViewById(R.id.txt_sunrise);
        txt_sunset=(TextView)itemView.findViewById(R.id.txt_sunset);
        txt_pressure=(TextView)itemView.findViewById(R.id.txt_pressure);
        txt_tempreture=(TextView)itemView.findViewById(R.id.txt_temperature);
        txt_description=(TextView)itemView.findViewById(R.id.txt_description);
        txt_date_time=(TextView)itemView.findViewById(R.id.txt_date_time);
        txt_wind =(TextView)itemView.findViewById(R.id.txt_wind);
        txt_geo_coord=(TextView)itemView.findViewById(R.id.txt_geo_coord);

        weather_panel = (LinearLayout)itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar)itemView.findViewById(R.id.loading);

        materialSearchBar = (MaterialSearchBar)itemView.findViewById(R.id.search_bar);
        materialSearchBar.setEnabled(false);

        new Loadcities().execute();

        return itemView;
    }

    private class Loadcities extends SimpleAsyncTask<List<String>> {

        @Override
        protected List<String> doInBackgroundSimple() {
            cityLists = new ArrayList<>();

            try {
                StringBuilder builder = new StringBuilder();
                InputStream inputStream = getResources().openRawResource(R.raw.city_list);
                GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

                InputStreamReader reader =  new InputStreamReader(gzipInputStream);
                BufferedReader in = new BufferedReader(reader);

                String readed;

                while ((readed = in.readLine()) !=null)
                    builder.append(readed);
                cityLists = new Gson().fromJson(builder.toString(),new TypeToken<List<String>>(){}.getType());

            }catch (IOException e){
                e.printStackTrace();

            }

            return cityLists;
        }

        @Override
        protected void onSuccess(final List<String> listCities) {
            super.onSuccess(listCities);

            materialSearchBar.setEnabled(true);

            materialSearchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    List<String> list =  new ArrayList<>();

                    for (String search:listCities){
                        if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                            list.add(search);
                    }

                    materialSearchBar.setLastSuggestions(list);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {

                }

                @Override
                public void onSearchConfirmed(CharSequence text) {
                    getWeatherinformation(text.toString());

                    materialSearchBar.setLastSuggestions(listCities);

                }

                @Override
                public void onButtonClicked(int buttonCode) {

                }
            });

            materialSearchBar.setLastSuggestions(listCities);

            loading.setVisibility(View.GONE);
            //weather_panel.setVisibility(View.VISIBLE);


        }
    }

    private void getWeatherinformation(String cityName) {
        compositeDisposable.add(mService.getWeatherByCity(cityName,
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(img_weather);

                        txt_city_name.setText(weatherResult.getName());
                        txt_description.setText(new StringBuilder("Weather in ")
                                .append(weatherResult.getName()));

                        txt_tempreture.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                                .append("°C").toString());

                        txt_date_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());

                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                        txt_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                        txt_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                        txt_geo_coord.setText(new StringBuilder(weatherResult.getCoord().toString()).toString());


                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        );

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
