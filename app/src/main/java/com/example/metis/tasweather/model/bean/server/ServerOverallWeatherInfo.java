package com.example.metis.tasweather.model.bean.server;

public class ServerOverallWeatherInfo {

    private String id;

    private String icon;

    private String description;

    private String main;

    public ServerOverallWeatherInfo() {
        // required for Gson
    }

    public ServerOverallWeatherInfo(String id, String icon, String description, String main) {
        this.id = id;
        this.icon = icon;
        this.description = description;
        this.main = main;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }
}
