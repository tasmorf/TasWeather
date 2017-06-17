package com.example.metis.tasweather.model.bean.server;


public class ServerWindInfo {

    private double speed;

    private double deg;

    public ServerWindInfo() {
        // required for Gson
    }

    public ServerWindInfo(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}
