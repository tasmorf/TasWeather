package com.example.metis.tasweather.model.bean;

import android.util.SparseArray;

import java.util.List;

/**
 * Represents the forecast for a single day.
 */
public class DayForecast {
    private String title;
    private boolean isToday;
    private List<WeatherInfo> hourlyWeatherInfos;

    private DayForecast(Builder builder) {
        title = builder.title;
        isToday = builder.isToday;
        hourlyWeatherInfos = builder.hourlyWeatherInfos;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getTitle() {
        return title;
    }

    public List<WeatherInfo> getHourlyWeatherInfos() {
        return hourlyWeatherInfos;
    }

    public boolean isToday() {
        return isToday;
    }
    public static final class Builder {
        private String title;
        private boolean isToday;
        private List<WeatherInfo> hourlyWeatherInfos;

        private Builder() {
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder isToday(boolean val) {
            isToday = val;
            return this;
        }

        public Builder hourlyWeatherInfos(List<WeatherInfo> val) {
            hourlyWeatherInfos = val;
            return this;
        }

        public DayForecast build() {
            return new DayForecast(this);
        }
    }
}
