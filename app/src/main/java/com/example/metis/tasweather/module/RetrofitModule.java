package com.example.metis.tasweather.module;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.ForecastService;
import com.example.metis.tasweather.model.WeatherConverterFactory;

import retrofit2.Retrofit;

import static com.example.metis.tasweather.module.ApplicationModule.resources;
import static com.example.metis.tasweather.module.DateHandlerModule.jodaDateHandler;
import static com.example.metis.tasweather.module.GsonModule.gson;
import static com.example.metis.tasweather.module.OkHttpModule.okHttpClient;

public class RetrofitModule {
    private static Retrofit openWeatherRetrofit;

    public static ForecastService forecastService() {
        return openWeatherRetrofit().create(ForecastService.class);
    }

    public static Retrofit openWeatherRetrofit() {
        if (openWeatherRetrofit == null) {
            openWeatherRetrofit = new Retrofit.Builder()
                    .addConverterFactory(weatherConverterFactory())
                    .client(okHttpClient())
                    .baseUrl(resources().getString(R.string.open_weather_api_endpoint))
                    .build();
        }
        return openWeatherRetrofit;
    }

    private static WeatherConverterFactory weatherConverterFactory() {
        return new WeatherConverterFactory(gson(), resources(), jodaDateHandler());
    }

}
