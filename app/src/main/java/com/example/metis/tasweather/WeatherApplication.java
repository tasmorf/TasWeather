package com.example.metis.tasweather;


import android.app.Application;

import com.example.metis.tasweather.module.ApplicationModule;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationModule.setApplication(this);
    }
}
