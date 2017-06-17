package com.example.metis.tasweather.model.bean.server;


public class ServerCloudsInfo {
    private int all;

    public ServerCloudsInfo() {
        // required for Gson
    }

    public ServerCloudsInfo(int all) {
        this.all = all;
    }

    public int getAll() {
        return all;
    }
}
