package com.example.metis.tasweather.model.bean.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DayForecast extends RealmObject {
    private String title;
    private boolean isToday;
    private RealmList<WeatherInfo> hourlyWeatherInfos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public RealmList<WeatherInfo> getHourlyWeatherInfos() {
        return hourlyWeatherInfos;
    }

    public void setHourlyWeatherInfos(RealmList<WeatherInfo> hourlyWeatherInfos) {
        this.hourlyWeatherInfos = hourlyWeatherInfos;
    }
}
