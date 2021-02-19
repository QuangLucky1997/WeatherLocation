package com.example.weather01.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;


import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather01.Api.AppLocationService;

import com.example.weather01.Model.weatherRespone;
import com.example.weather01.R;
import com.example.weather01.Api.apiCall;
import com.example.weather01.Api.apiService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherTodayActivity extends AppCompatActivity {
//    AppLocationService appLocationService;
//    String result;
    @BindView(R.id.btn5Days)
    Button btnfiveDay;
    @BindView(R.id.txtCityName)
    TextView txtCityName;
    @BindView(R.id.txtCountry)
    TextView txtcountryName;
    @BindView(R.id.txtTemp)
    TextView txtTemp;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.textViewHumidity)
    TextView txthumidity;
    @BindView(R.id.textViewCloudiness)
    TextView txtCloud;
    @BindView(R.id.textViewWind)
    TextView txtWind;
    @BindView(R.id.txtDay)
    TextView txtDay;
    @BindView(R.id.txtday)
    TextView txtDay2;
    weatherRespone weatherRespone;
    @BindView(R.id.iconweather)
    ImageView imgWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_today);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Intent intent1=getIntent();
        String rs=intent1.getStringExtra("cityName");
        LoadDataFromApi(rs);
        btnfiveDay.setOnClickListener(view -> {
            Intent intent = new Intent(WeatherTodayActivity.this, Weather5Day.class);
            intent.putExtra("city", rs);
            startActivity(intent);
        });
        //        btnfiveDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WeatherTodayActivity.this, Weather5Day.class);
//                intent.putExtra("city", result);
//                startActivity(intent);
//
//            }
//        appLocationService = new AppLocationService(WeatherTodayActivity.this);
//        Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
//        if (location != null) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//            try {
//                List<Address> addressList = geocoder.getFromLocation(
//                        latitude, longitude, 1);
//                if (addressList != null && addressList.size() > 0) {
//                    Address address = addressList.get(0);
//                    StringBuilder sb = new StringBuilder();
//                    sb.append(address.getLocality()).append("\n");
//                    result = sb.toString();
//                }
//            } catch (IOException e) {
//                Log.e("Log", "Unable connect to Geocoder", e);
//            }
        }
//        LoadDataFromApi(result);
//        btnfiveDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WeatherTodayActivity.this, Weather5Day.class);
//                intent.putExtra("city", result);
//                startActivity(intent);
//
//            }
//
//
//        });

    //}

    private void LoadDataFromApi(String cityName) {
        String key_api = "your token key open weather map";
        Retrofit retrofit = apiService.getApi();
        apiCall apiCall = retrofit.create(com.example.weather01.Api.apiCall.class);
        Call<weatherRespone> call = apiCall.getWeatherByCity(cityName, key_api);
        call.enqueue(new Callback<com.example.weather01.Model.weatherRespone>() {
            @Override
            public void onResponse(Call<com.example.weather01.Model.weatherRespone> call, Response<com.example.weather01.Model.weatherRespone> response) {
                weatherRespone = response.body();
                if (weatherRespone != null) {
                    txtCityName.setText(weatherRespone.getName());
                    txtcountryName.setText(weatherRespone.getSys().getCountry());
                    Double t = Double.valueOf(weatherRespone.getMain().getTemp() - 273.15);
                    String temp = String.valueOf(t.intValue());
                    txtTemp.setText(temp + "°C");
                    String day = String.valueOf(weatherRespone.getDt());
                    long l = Long.valueOf(day);
                    Date date = new Date(l * 1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE,d MMMM", Locale.ENGLISH);
                    String Day = simpleDateFormat.format(date);
                    txtDay.setText(Day);
                    txthumidity.setText(weatherRespone.getMain().getHumidity() + "%");
                    txtWind.setText(weatherRespone.getWind().getSpeed() + "m/s");
                    txtCloud.setText(weatherRespone.getClouds().getAll() + "%");
                    txtStatus.setText(response.body().getWeather().get(0).getMain());
                    String icon = response.body().getWeather().get(0).getIcon();
                    Picasso.get().load("http://openweathermap.org/img/wn/" + icon + ".png").into(imgWeather);


                }
            }

            @Override
            public void onFailure(Call<com.example.weather01.Model.weatherRespone> call, Throwable t) {

            }
        });
    }
}