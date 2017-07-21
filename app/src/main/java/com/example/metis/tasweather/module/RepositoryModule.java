package com.example.metis.tasweather.module;


import com.example.metis.tasweather.model.ForecastRealmRetrofitRepository;
import com.example.metis.tasweather.model.ForecastRepository;

import static com.example.metis.tasweather.module.ApplicationModule.resources;
import static com.example.metis.tasweather.module.RealmModule.realm;
import static com.example.metis.tasweather.module.RetrofitModule.forecastService;
import static com.example.metis.tasweather.module.ConverterModule.forecastConverter;

public class RepositoryModule {

    public static ForecastRepository forecastRealmRetrofitRepository() {
        return new ForecastRealmRetrofitRepository(resources(), forecastService(), forecastConverter(), realm());
    }
}
