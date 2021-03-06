package com.example.metis.tasweather.model;

import com.example.metis.tasweather.model.bean.server.ServerForecast;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {

    //Only supporting metric units for now
    @GET("data/2.5/forecast?units=metric")
    Single<Response<ServerForecast>> getFiveDayForecast(@Query("appid") String appId, @Query("id") String cityId);

}
