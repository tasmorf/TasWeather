package com.example.metis.tasweather.model;

import android.content.res.Resources;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.bean.Forecast;
import com.example.metis.tasweather.model.bean.server.ServerForecast;

import io.reactivex.Single;
import retrofit2.http.Query;

public class ForecastRealmRetrofitRepository implements ForecastRepository {
    private final Resources res;
    private final ForecastService forecastService;
    private final Converter<ServerForecast, Forecast> forecastConverter;

    public ForecastRealmRetrofitRepository(Resources res, ForecastService forecastService, Converter<ServerForecast, Forecast> forecastConverter) {
        this.res = res;
        this.forecastService = forecastService;
        this.forecastConverter = forecastConverter;
    }


    @Override
    public Single<Forecast> getFiveDayForecast(@Query("id") String cityId) {
        return forecastService.getFiveDayForecast(res.getString(R.string.open_weather_app_id), cityId)
                .map(forecastConverter::convert);
    }
}
