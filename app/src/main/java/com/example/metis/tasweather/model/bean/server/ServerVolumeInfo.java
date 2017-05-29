package com.example.metis.tasweather.model.bean.server;


import com.google.gson.annotations.SerializedName;

public class ServerVolumeInfo {
    @SerializedName("3h")
    private double volume;

    public double getVolume() {
        return volume;
    }
}
