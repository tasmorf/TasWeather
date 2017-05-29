package com.example.metis.tasweather;

import android.util.SparseArray;

/**
 * Represents the forecast for a single day.
 */
public class DayForecast {
    private String title;
    private WeatherInfo averageDayWeatherInfo;
    private WeatherInfo averageNightWeatherInfo;
    private SparseArray<WeatherInfo> hourlyWeatherInfos;

    private DayForecast(Builder builder) {
        title = builder.title;
        averageDayWeatherInfo = builder.averageDayWeatherInfo;
        averageNightWeatherInfo = builder.averageNightWeatherInfo;
        hourlyWeatherInfos = builder.hourlyWeatherInfos;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public WeatherInfo getAverageDayWeatherInfo() {
        return averageDayWeatherInfo;
    }

    public WeatherInfo getAverageNightWeatherInfo() {
        return averageNightWeatherInfo;
    }

    public SparseArray<WeatherInfo> getHourlyWeatherInfos() {
        return hourlyWeatherInfos;
    }

    public static final class Builder {
        private String title;
        private WeatherInfo averageDayWeatherInfo;
        private WeatherInfo averageNightWeatherInfo;
        private SparseArray<WeatherInfo> hourlyWeatherInfos;

        private Builder() {
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder averageDayWeatherInfo(WeatherInfo val) {
            averageDayWeatherInfo = val;
            return this;
        }

        public Builder averageNightWeatherInfo(WeatherInfo val) {
            averageNightWeatherInfo = val;
            return this;
        }

        public Builder hourlyWeatherInfos(SparseArray<WeatherInfo> val) {
            hourlyWeatherInfos = val;
            return this;
        }

        public DayForecast build() {
            return new DayForecast(this);
        }
    }
}
