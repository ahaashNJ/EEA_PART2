package com.example.eea_part2.API;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class API {

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8040/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit;
    }

}
