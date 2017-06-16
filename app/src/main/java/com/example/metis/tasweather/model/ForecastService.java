package com.example.metis.tasweather.model;

import com.example.metis.tasweather.model.bean.Forecast;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {

    //Only supporting metric units for now
    @GET("data/2.5/forecast?units=metric")
    Observable<Forecast> getFiveDayForecast(@Query("appid") String appId, @Query("id") String cityId);

}
