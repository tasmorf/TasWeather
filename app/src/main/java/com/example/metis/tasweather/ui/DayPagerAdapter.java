package com.example.metis.tasweather.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.bean.realm.DayForecast;
import com.nightlynexus.viewstatepageradapter.ViewStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter shows a different view per day
 */
public class DayPagerAdapter extends ViewStatePagerAdapter {

    private List<DayForecast> dayForecasts = new ArrayList<>();

    @Override
    public int getCount() {
        return dayForecasts.size();
    }

    @Override
    protected View createView(ViewGroup container, int position) {
        Context context = container.getContext();
        DailyForecastLayout view = (DailyForecastLayout) LayoutInflater.from(context).inflate(R.layout.view_daily_forecast, container, false);
        view.setForecast(dayForecasts.get(position));
        return view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dayForecasts.get(position).getTitle();
    }

    public void setDaysForecast(List<DayForecast> days) {
        dayForecasts.clear();
        dayForecasts.addAll(days);
        notifyDataSetChanged();
    }
}
