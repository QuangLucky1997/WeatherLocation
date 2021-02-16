package com.example.weather01.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiService {
    public static final String base_url = "http://api.openweathermap.org";
    private static Retrofit retrofit = null;

    public static Retrofit getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
