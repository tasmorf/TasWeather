package com.example.metis.tasweather.model;

import com.example.metis.tasweather.model.bean.realm.Forecast;

import io.reactivex.Flowable;
import retrofit2.http.Query;

public interface ForecastRepository {

    Flowable<Forecast> getFiveDayForecast(@Query("id") String cityId);
}
