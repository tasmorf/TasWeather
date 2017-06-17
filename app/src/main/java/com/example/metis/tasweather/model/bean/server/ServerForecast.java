package com.example.metis.tasweather.model.bean.server;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerForecast {

    @SerializedName("list")
    private List<ServerWeatherDataPoint> weatherDataPoints;

    @SerializedName("city")
    private ServerCity city;

    // required for Gson
    public ServerForecast() {

    }

    public ServerForecast(List<ServerWeatherDataPoint> weatherDataPoints, ServerCity city) {
        this.weatherDataPoints = weatherDataPoints;
        this.city = city;
    }

    public List<ServerWeatherDataPoint> getWeatherDataPoints() {
        return weatherDataPoints;
    }

    public ServerCity getCity() {
        return city;
    }
}