package com.example.metis.tasweather.model.bean.server;

public class ServerMainWeatherInfo {

    private double temp;
    private int humidity;
    private double pressure;

    public ServerMainWeatherInfo() {
        // required for Gson
    }

    public ServerMainWeatherInfo(double temp, int humidity, double pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }
}
