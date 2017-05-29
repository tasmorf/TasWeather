package com.example.metis.tasweather;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.metis.tasweather.model.bean.DayForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter shows a different view per day
 */
public class DayPagerAdapter extends PagerAdapter {

    private List<DayForecast> dayForecasts = new ArrayList<>();

    @Override
    public int getCount() {
        return dayForecasts.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Context context = container.getContext();
        DailyForecastLayout view = (DailyForecastLayout) LayoutInflater.from(context).inflate(R.layout.view_daily_forecast, container, false);
        view.setForecast(dayForecasts.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
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
