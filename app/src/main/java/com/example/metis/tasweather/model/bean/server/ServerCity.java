package com.example.metis.tasweather.model.bean.server;

public class ServerCity {

    private String id;

    private String name;

    private String country;

    // required for Gson
    public ServerCity() {

    }

    public ServerCity(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
