package com.example.metis.tasweather;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.example.metis.tasweather.DisplayMode.AVERAGE;
import static com.example.metis.tasweather.DisplayMode.TIMELINE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({AVERAGE, TIMELINE})
public @interface DisplayMode {
    int AVERAGE = 0;
    int TIMELINE = 1;
}
