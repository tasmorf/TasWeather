package com.example.metis.tasweather.module;

import android.support.v4.view.PagerAdapter;

import com.example.metis.tasweather.DayPagerAdapter;

public class PagerAdapterModule {

    public static DayPagerAdapter dayPagerAdapter() {
        return new DayPagerAdapter();
    }
}
