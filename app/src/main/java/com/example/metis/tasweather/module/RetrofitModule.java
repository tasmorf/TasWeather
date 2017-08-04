package com.example.metis.tasweather.module;

import android.support.annotation.NonNull;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.ForecastService;

import okhttp3.HttpUrl;
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
            openWeatherRetrofit = createRetrofit(resources().getString(R.string.open_weather_api_endpoint));
        }
        return openWeatherRetrofit;
    }

    @NonNull
    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static void changeBaseUrl(String baseUrl) {
        openWeatherRetrofit = createRetrofit(baseUrl);
    }

}
