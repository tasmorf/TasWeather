package com.example.metis.tasweather.module;


import android.content.Context;
import android.content.res.Resources;

import com.example.metis.tasweather.WeatherApplication;

public class ApplicationModule {
    private static WeatherApplication application;

    public static void setApplication(WeatherApplication application) {
        ApplicationModule.application = application;
    }

    public static Resources resources() {
        return application.getResources();
    }

    public static Context applicationContext() {
        return application;
    }
}
