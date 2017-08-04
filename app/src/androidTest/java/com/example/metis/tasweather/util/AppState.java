package com.example.metis.tasweather.util;


import com.example.metis.tasweather.module.OkHttpModule;
import com.example.metis.tasweather.module.RetrofitModule;

import java.io.IOException;

public class AppState {
    public static void cleanAppState() {
        try {
            OkHttpModule.okHttpClient().cache().evictAll();
        } catch (IOException e) {
        }
        RetrofitModule.changeBaseUrl("http://localhost:9999");
    }
}
