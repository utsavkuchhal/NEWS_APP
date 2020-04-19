package com.utsav.sqliteexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News>  getNews(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("everything")
    Call<News>  getspecificdata(@Query("q") String query, @Query("apiKey") String apiKey);

}
