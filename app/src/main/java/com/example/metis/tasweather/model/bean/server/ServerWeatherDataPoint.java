package com.example.metis.tasweather.model.bean.server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerWeatherDataPoint {
    @SerializedName("dt")
    private long timestamp;

    private ServerMainWeatherInfo main;

    private ServerCloudsInfo clouds;

    private ServerWindInfo wind;

    private ServerVolumeInfo rain;

    private ServerVolumeInfo snow;

    @SerializedName("weather")
    private List<ServerOverallWeatherInfo> overallWeather;

    // needed for Gson
    public ServerWeatherDataPoint() {

    }

    public ServerWeatherDataPoint(long timestamp, ServerMainWeatherInfo main, ServerCloudsInfo clouds, ServerWindInfo wind, ServerVolumeInfo rain, ServerVolumeInfo snow, List<ServerOverallWeatherInfo> overallWeather) {
        this.timestamp = timestamp;
        this.main = main;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.overallWeather = overallWeather;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public ServerMainWeatherInfo getMain() {
        return main;
    }

    public ServerCloudsInfo getClouds() {
        return clouds;
    }

    public ServerWindInfo getWind() {
        return wind;
    }

    public ServerVolumeInfo getRain() {
        return rain;
    }

    public ServerVolumeInfo getSnow() {
        return snow;
    }

    public List<ServerOverallWeatherInfo> getOverallWeather() {
        return overallWeather;
    }
}
