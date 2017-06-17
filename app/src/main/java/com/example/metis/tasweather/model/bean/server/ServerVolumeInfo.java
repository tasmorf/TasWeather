package com.example.metis.tasweather.model.bean.server;


import com.google.gson.annotations.SerializedName;

public class ServerVolumeInfo {
    @SerializedName("3h")
    private double volume;

    public ServerVolumeInfo() {
        // required for Gson
    }

    public ServerVolumeInfo(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }
}
