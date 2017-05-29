package com.example.metis.tasweather.model;


import android.content.res.Resources;

import com.example.metis.tasweather.model.bean.Forecast;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class WeatherConverterFactory extends Converter.Factory {

    private final Gson gson;
    private final Resources resources;
    private final DateHandler dateHandler;

    public WeatherConverterFactory(Gson gson, Resources resources, DateHandler dateHandler) {
        this.gson = gson;
        this.resources = resources;
        this.dateHandler = dateHandler;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == Forecast.class) {
            return new ForecastConverter(gson, resources, dateHandler);
        }
        // We'd have different converter classes for different beans here
        throw new RuntimeException("No converter registered for this response type!");
    }
}
