package com.example.metis.tasweather.module;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.ForecastService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.metis.tasweather.module.ApplicationModule.resources;
import static com.example.metis.tasweather.module.OkHttpModule.okHttpClient;

public class RetrofitModule {
    private static Retrofit openWeatherRetrofit;

    public static ForecastService forecastService() {
        return openWeatherRetrofit().create(ForecastService.class);
    }

    private static Retrofit openWeatherRetrofit() {
        if (openWeatherRetrofit == null) {
            openWeatherRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient())
                    .baseUrl(resources().getString(R.string.open_weather_api_endpoint))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return openWeatherRetrofit;
    }

}
