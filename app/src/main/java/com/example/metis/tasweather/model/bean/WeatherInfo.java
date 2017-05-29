package com.example.metis.tasweather.model.bean;

public class WeatherInfo {
    private final String mainTemp;
    private final String iconUrl;
    private final String hiLoTemp;
    private final String cloudiness;
    private final String rainVolume;
    private final String windInfo;
    private final String snowVolume;
    private final String pressureInfo;
    private final String humidity;

    private WeatherInfo(Builder builder) {
        mainTemp = builder.mainTemp;
        iconUrl = builder.iconUrl;
        hiLoTemp = builder.hiLoTemp;
        cloudiness = builder.cloudiness;
        rainVolume = builder.rainVolume;
        windInfo = builder.windInfo;
        snowVolume = builder.snowVolume;
        pressureInfo = builder.pressureInfo;
        humidity = builder.humidity;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getMainTemp() {
        return mainTemp;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getHiLoTemp() {
        return hiLoTemp;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public String getRainVolume() {
        return rainVolume;
    }

    public String getWindInfo() {
        return windInfo;
    }

    public String getSnowVolume() {
        return snowVolume;
    }

    public String getPressureInfo() {
        return pressureInfo;
    }

    public String getHumidity() {
        return humidity;
    }

    public static final class Builder {
        private String mainTemp;
        private String iconUrl;
        private String hiLoTemp;
        private String cloudiness;
        private String rainVolume;
        private String windInfo;
        private String snowVolume;
        private String pressureInfo;
        private String humidity;

        private Builder() {
        }

        public Builder mainTemp(String val) {
            mainTemp = val;
            return this;
        }

        public Builder iconUrl(String val) {
            iconUrl = val;
            return this;
        }

        public Builder hiLoTemp(String val) {
            hiLoTemp = val;
            return this;
        }

        public Builder cloudiness(String val) {
            cloudiness = val;
            return this;
        }

        public Builder rainVolume(String val) {
            rainVolume = val;
            return this;
        }

        public Builder windInfo(String val) {
            windInfo = val;
            return this;
        }

        public Builder snowVolume(String val) {
            snowVolume = val;
            return this;
        }

        public Builder pressureInfo(String val) {
            pressureInfo = val;
            return this;
        }

        public Builder humidity(String val) {
            humidity = val;
            return this;
        }

        public WeatherInfo build() {
            return new WeatherInfo(this);
        }
    }
}
