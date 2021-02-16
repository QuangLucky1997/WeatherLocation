package com.example.weather01.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather01.Adapter.AdapterRecycleWeather;
import com.example.weather01.R;
import com.example.weather01.Api.apiCall;
import com.example.weather01.Api.apiService;
import com.example.weather01.fiveDay.ForecastWeatherResponse;
import com.squareup.picasso.Picasso;

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

public class Weather5Day extends AppCompatActivity {
    @BindView(R.id.imgright)
    ImageView imgBack;
    @BindView(R.id.iconweather)
    ImageView imgIconweather;
    @BindView(R.id.txtdayUI2)
    TextView txtDay;
    @BindView(R.id.txtTemp)
    TextView txttempmax;
    @BindView(R.id.txttempMin)
    TextView txttempmin;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.textViewHumidity)
    TextView txthumidity;
    @BindView(R.id.textViewCloudiness)
    TextView txtclouds;
    @BindView(R.id.textViewWind)
    TextView txtwind;
    @BindView(R.id.rvWeather)
    RecyclerView recyclerViewWeather;
    AdapterRecycleWeather adapterRecycleWeather;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather5_day);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        imgBack.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        result = intent.getStringExtra("city");
        Retrofit retrofit = apiService.getApi();
        apiCall apiCall = retrofit.create(com.example.weather01.Api.apiCall.class);
        Call<ForecastWeatherResponse> fiveDayResponeCall = apiCall.getWeather5day(result, "5f29e35711ac92b01c4115be53dc09b5");
        fiveDayResponeCall.enqueue(new Callback<ForecastWeatherResponse>() {
            @Override
            public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                if (response.isSuccessful()) {
                    ForecastWeatherResponse fiveDayRespone = response.body();
                    List<com.example.weather01.fiveDay.List> lists5Day = fiveDayRespone.getList();
                    String dayFirst = String.valueOf(lists5Day.get(5).getDt());
                    long l = Long.valueOf(dayFirst);
                    Date date = new Date(l * 1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE,d MMM", Locale.ENGLISH);
                    String Day = simpleDateFormat.format(date);
                    txtDay.setText(Day);
                    Double tempMax2 = Double.valueOf(lists5Day.get(5).getMain().getTempMax() - 273.15);
                    String temp = String.valueOf(tempMax2.intValue());
                    txttempmax.setText(temp);
                    Double tempMin2 = Double.valueOf(lists5Day.get(5).getMain().getTempMin() - 273.15);
                    String temp2 = String.valueOf(tempMin2.intValue());
                    txttempmin.setText("/" + temp2 + "°C");
                    txtStatus.setText(lists5Day.get(5).getWeather().get(0).getMain());
                    String icon = lists5Day.get(5).getWeather().get(0).getIcon();
                    Picasso.get().load("http://openweathermap.org/img/wn/" + icon + ".png").into(imgIconweather);
                    txthumidity.setText(lists5Day.get(5).getMain().getHumidity() + "%");
                    txtclouds.setText(lists5Day.get(5).getClouds().getAll() + "%");
                    txtwind.setText(lists5Day.get(5).getWind().getSpeed() + " m/S ");
                    recyclerViewWeather.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                    recyclerViewWeather.setLayoutManager(linearLayoutManager);
                    adapterRecycleWeather = new AdapterRecycleWeather(lists5Day, getApplicationContext());
                    adapterRecycleWeather.notifyDataSetChanged();
                    recyclerViewWeather.setAdapter(adapterRecycleWeather);
                }
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


