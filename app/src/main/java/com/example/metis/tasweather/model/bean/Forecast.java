package com.example.metis.tasweather.model.bean;

import java.util.List;

public class Forecast {

    private String city;
    private List<DayForecast> forecastList;

    private Forecast(Builder builder) {
        city = builder.city;
        forecastList = builder.forecastList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<DayForecast> getForecastList() {
        return forecastList;
    }

    public String getCity() {
        return city;
    }

    public static final class Builder {
        private String city;
        private List<DayForecast> forecastList;

        private Builder() {
        }

        public Builder city(String val) {
            city = val;
            return this;
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
