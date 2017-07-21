package com.example.metis.tasweather;


import android.app.Application;

import com.example.metis.tasweather.module.ApplicationModule;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        Realm.init(this);
        ApplicationModule.setApplication(this);
    }
}
