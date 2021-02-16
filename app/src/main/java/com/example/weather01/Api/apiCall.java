package com.example.weather01.Api;

import com.example.weather01.Model.weatherRespone;
import com.example.weather01.fiveDay.ForecastWeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiCall {
    @GET("/data/2.5/weather")
    Call<weatherRespone> getWeatherByCity(@Query("q")String city, @Query("appid")String apiKey);
    @GET("/data/2.5/forecast")
    Call<ForecastWeatherResponse>getWeather5day(@Query("q")String city, @Query("appid")String apiKey);
}
