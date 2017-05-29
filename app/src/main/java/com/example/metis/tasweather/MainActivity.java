package com.example.metis.tasweather;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.container)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    private DayPagerAdapter mDayPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mDayPagerAdapter = new DayPagerAdapter();
        viewPager.setAdapter(mDayPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDayPagerAdapter.setDaysForecast(Arrays.asList(DayForecast
                        .newBuilder()
                        .title("Today")
                        .build(),
                DayForecast
                        .newBuilder()
                        .title("Tomorrow")
                        .build(),
                DayForecast
                        .newBuilder()
                        .title("Wednesday")
                        .build(),
                DayForecast
                        .newBuilder()
                        .title("Thursday")
                        .build(),
                DayForecast
                        .newBuilder()
                        .title("Friday")
                        .build()
        ));
    }
}

