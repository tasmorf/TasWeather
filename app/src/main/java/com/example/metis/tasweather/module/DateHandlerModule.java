package com.example.metis.tasweather.module;


import com.example.metis.tasweather.model.DateHandler;
import com.example.metis.tasweather.model.JodaDateHandler;

import static com.example.metis.tasweather.module.ApplicationModule.resources;

public class DateHandlerModule {

    public static DateHandler jodaDateHandler() {
        return new JodaDateHandler(resources());
    }
}
