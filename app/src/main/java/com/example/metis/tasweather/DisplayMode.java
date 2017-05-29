package com.example.metis.tasweather;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.example.metis.tasweather.DisplayMode.AVERAGE_DAY;
import static com.example.metis.tasweather.DisplayMode.AVERAGE_NIGHT;
import static com.example.metis.tasweather.DisplayMode.TIMELINE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({AVERAGE_DAY, AVERAGE_NIGHT, TIMELINE})
public @interface DisplayMode {
    int AVERAGE_DAY = 0;
    int AVERAGE_NIGHT = 1;
    int TIMELINE = 2;
}
