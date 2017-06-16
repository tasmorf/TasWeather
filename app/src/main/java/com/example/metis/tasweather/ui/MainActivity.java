package com.example.metis.tasweather.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.ForecastService;
import com.example.metis.tasweather.model.bean.Forecast;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.metis.tasweather.module.PagerAdapterModule.dayPagerAdapter;
import static com.example.metis.tasweather.module.RetrofitModule.forecastService;

public class MainActivity extends AppCompatActivity {

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
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        RxView.clicks(findViewById(R.id.retry_button)).subscribe(o -> loadData());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void loadData() {
        compositeDisposable.add(forecastService.getFiveDayForecast(getString(R.string.open_weather_app_id),
                getString(R.string.city_id_athens))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));

        progress.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
    }


    private void handleResponse(Forecast forecast) {
        if (forecast == null) {
            handleError(null);
        } else {
            setTitle(getString(R.string.title_city, forecast.getCity()));
            pagerAdapter.setDaysForecast(forecast.getForecastList());

            progress.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }
    }


    private void handleError(Throwable throwable) {

        progress.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
    }
}

