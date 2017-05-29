package com.example.metis.tasweather;

/**
 * Represents the forecast for a single day.
 */
public class DayForecast {
    private String title;

    private DayForecast(Builder builder) {
        title = builder.title;
    }

    public String getTitle() {
        return title;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;

        private Builder() {
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public DayForecast build() {
            return new DayForecast(this);
        }
    }
}
