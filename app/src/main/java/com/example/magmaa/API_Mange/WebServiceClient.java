package com.example.magmaa.API_Mange;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {

    private static Retrofit retrofit = null ;
    public static final String BASE_URL  = "http://192.168.43.241:8000/api/" ;
    public static final String PHOTO_URL = "http://192.168.43.241:8000/upload/" ;
    public static  Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}