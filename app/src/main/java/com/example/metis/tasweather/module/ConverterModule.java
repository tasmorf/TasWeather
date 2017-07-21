package com.example.metis.tasweather.module;


import com.example.metis.tasweather.model.Converter;
import com.example.metis.tasweather.model.ForecastConverter;
import com.example.metis.tasweather.model.bean.realm.Forecast;
import com.example.metis.tasweather.model.bean.server.ServerForecast;

import retrofit2.Response;

import static com.example.metis.tasweather.module.ApplicationModule.resources;
import static com.example.metis.tasweather.module.DateHandlerModule.jodaDateHandler;

public class ConverterModule {
    public static Converter<Response<ServerForecast>, Forecast> forecastConverter() {
        return new ForecastConverter(resources(), jodaDateHandler());
    }
}
