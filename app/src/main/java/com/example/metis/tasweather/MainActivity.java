package com.example.metis.tasweather;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.metis.tasweather.model.ForecastService;
import com.example.metis.tasweather.model.bean.Forecast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.metis.tasweather.module.PagerAdapterModule.dayPagerAdapter;
import static com.example.metis.tasweather.module.RetrofitModule.forecastService;

public class MainActivity extends AppCompatActivity implements Callback<Forecast> {

    @Bind(R.id.container)
    ViewPager viewPager;
    @Bind(R.id.error_layout)
    View errorLayout;
    @Bind(R.id.progress)
    View progress;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    private final ForecastService forecastService;
    private final DayPagerAdapter pagerAdapter;

    public MainActivity() {
        this(forecastService(), dayPagerAdapter());
    }

    public MainActivity(ForecastService forecastService, DayPagerAdapter pagerAdapter) {
        this.forecastService = forecastService;
        this.pagerAdapter = pagerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        loadData();
    }

    @OnClick(R.id.retry_button)
    public void onRetry() {
        loadData();
    }

    @Override
    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
        Forecast forecast = response.body();
        if (forecast == null) {
            showError();
        } else {
            setTitle(getString(R.string.title_city, forecast.getCity()));
            pagerAdapter.setDaysForecast(forecast.getForecastList());

            progress.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Call<Forecast> call, Throwable t) {
        showError();
    }

    private void showError() {
        progress.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
    }

    private void loadData() {
        forecastService.getFiveDayForecast(getString(R.string.open_weather_app_id),
                getString(R.string.city_id_athens)).enqueue(this);

        progress.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
    }

}

