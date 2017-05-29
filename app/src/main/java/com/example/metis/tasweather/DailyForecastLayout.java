package com.example.metis.tasweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A View representing the forecast for a single day.
 */
public class DailyForecastLayout extends LinearLayout {

    @Bind(R.id.section_label) TextView label;

    public DailyForecastLayout(Context context) {
        super(context);
    }

    public DailyForecastLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyForecastLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setForecast(DayForecast forecast) {
        label.setText(forecast.getTitle());
    }
}
