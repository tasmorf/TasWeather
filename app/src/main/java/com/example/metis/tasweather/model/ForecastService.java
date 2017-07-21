package com.example.metis.tasweather.model;

import com.example.metis.tasweather.model.bean.server.ServerForecast;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {

    //Only supporting metric units for now
    @GET("data/2.5/forecast?units=metric")
    Maybe<ServerForecast> getFiveDayForecast(@Query("appid") String appId, @Query("id") String cityId);

}
