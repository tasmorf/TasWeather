package com.example.metis.tasweather.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.ForecastRepository;
import com.example.metis.tasweather.model.bean.realm.DayForecast;
import com.example.metis.tasweather.model.bean.realm.Forecast;
import com.example.metis.tasweather.model.bean.realm.WeatherInfo;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static com.example.metis.tasweather.module.PagerAdapterModule.dayPagerAdapter;
import static com.example.metis.tasweather.module.RealmModule.realm;
import static com.example.metis.tasweather.module.RepositoryModule.forecastRealmRetrofitRepository;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_DAY = "selectedDay";
    private static final String TAG = "REALM";
    @Bind(R.id.container)
    ViewPager viewPager;
    @Bind(R.id.error_layout)
    View errorLayout;
    @Bind(R.id.progress)
    View progress;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    private final ForecastRepository forecastRepository;
    private final DayPagerAdapter pagerAdapter;
    private Realm realm;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int selectedDay;

    public MainActivity() {
        this(forecastRealmRetrofitRepository(), dayPagerAdapter(), realm());
    }

    public MainActivity(ForecastRepository forecastRepository, DayPagerAdapter pagerAdapter, Realm realm) {
        this.forecastRepository = forecastRepository;
        this.pagerAdapter = pagerAdapter;
        this.realm = realm;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_DAY, viewPager.getCurrentItem());
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedDay = savedInstanceState.getInt(SELECTED_DAY);
    }

    private void loadData() {
        compositeDisposable.add(forecastRepository.getFiveDayForecast(getString(R.string.city_id_athens))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));

        progress.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
    }

    private void handleResponse(Forecast forecast) {
        setTitle(getString(R.string.title_city, forecast.getCity()));
        pagerAdapter.setDaysForecast(forecast.getForecastList());
        progress.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(selectedDay);
    }

    private void handleError(Throwable throwable) {
        progress.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
    }
}

