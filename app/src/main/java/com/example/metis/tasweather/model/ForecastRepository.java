package com.example.metis.tasweather.model;

import com.example.metis.tasweather.model.bean.Forecast;

import io.reactivex.Single;
import retrofit2.http.Query;

public interface ForecastRepository {

    Single<Forecast> getFiveDayForecast(@Query("id") String cityId);
}
