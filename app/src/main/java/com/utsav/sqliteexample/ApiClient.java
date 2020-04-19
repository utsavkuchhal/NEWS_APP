package com.utsav.sqliteexample;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static Retrofit retrofit;
    private static ApiClient apiClient;

    public  ApiClient()
    {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public static synchronized ApiClient getInstance(){
        if(apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;

    }

    public  ApiInterface getapi(){
        return retrofit.create(ApiInterface.class);
    }
}
