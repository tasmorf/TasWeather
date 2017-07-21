package com.example.metis.tasweather.model.bean.realm;

import io.realm.RealmObject;

public class WeatherInfo extends RealmObject {
    private String mainTemp;
    private String iconUrl;
    private String cloudiness;
    private String rainVolume;
    private String windInfo;
    private String snowVolume;
    private String pressureInfo;
    private String humidity;
    private String time;

    public String getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(String mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }

    public String getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(String rainVolume) {
        this.rainVolume = rainVolume;
    }

    public String getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(String windInfo) {
        this.windInfo = windInfo;
    }

    public String getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(String snowVolume) {
        this.snowVolume = snowVolume;
    }

    public String getPressureInfo() {
        return pressureInfo;
    }

    public void setPressureInfo(String pressureInfo) {
        this.pressureInfo = pressureInfo;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
