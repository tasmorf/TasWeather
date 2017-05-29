package com.example.metis.tasweather.model.bean;

import java.util.List;

public class Forecast {

    private List<DayForecast> forecastList;

    private Forecast(Builder builder) {
        forecastList = builder.forecastList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<DayForecast> getForecastList() {
        return forecastList;
    }


    public static final class Builder {
        private List<DayForecast> forecastList;

        private Builder() {
        }

        public Builder forecastList(List<DayForecast> val) {
            forecastList = val;
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }
    }
}
