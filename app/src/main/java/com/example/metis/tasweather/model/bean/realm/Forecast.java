package com.example.metis.tasweather.model.bean.realm;


import io.realm.RealmList;
import io.realm.RealmObject;

public class Forecast extends RealmObject {
    private String city;
    private RealmList<DayForecast> forecastList;
    private boolean isError;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealmList<DayForecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(RealmList<DayForecast> forecastList) {
        this.forecastList = forecastList;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
