package com.example.metis.tasweather.model;

import android.content.res.Resources;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.bean.realm.Forecast;
import com.example.metis.tasweather.model.bean.server.ServerForecast;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import retrofit2.http.Query;

public class ForecastRealmRetrofitRepository implements ForecastRepository {
    private final Resources res;
    private final ForecastService forecastService;
    private final Converter<ServerForecast, Forecast> forecastConverter;
    private final Realm realm;

    public ForecastRealmRetrofitRepository(Resources res, ForecastService forecastService, Converter<ServerForecast, Forecast> forecastConverter, Realm realm) {
        this.res = res;
        this.forecastService = forecastService;
        this.forecastConverter = forecastConverter;
        this.realm = realm;
    }


    @Override
    public Flowable<Forecast> getFiveDayForecast(@Query("id") String cityId) {
        Forecast cachedRealmForecast = realm.where(Forecast.class).findFirst();
        return forecastService.getFiveDayForecast(res.getString(R.string.open_weather_app_id), cityId)
                .onErrorReturn((Throwable e) -> null)
                .map(forecastConverter::convert)
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext((forecast) -> realm.executeTransactionAsync(instance -> instance.copyToRealm(forecast)))
                .mergeWith(cachedRealmForecast == null ? Flowable.empty() : Flowable.just(cachedRealmForecast));
    }

}
