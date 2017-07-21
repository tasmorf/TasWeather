package com.example.metis.tasweather.ui;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.bean.realm.DayForecast;
import com.example.metis.tasweather.model.bean.realm.WeatherInfo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A View representing the forecast for a single day.
 */
public class DailyForecastLayout extends ScrollView implements SeekBar.OnSeekBarChangeListener {

    private static final int FORECAST_STEP_SIZE = 3;
    private static final int DATA_POINTS_PER_DAY = 8;
    @Bind(R.id.timeline_label)
    TextView timelineLabel;
    @Bind(R.id.timeline_chooser)
    SeekBar timelineChooser;
    @Bind(R.id.weather_icon)
    ImageView weatherIcon;
    @Bind(R.id.main_temp)
    TextView mainTempText;
    @Bind(R.id.clouds)
    TextView cloudText;
    @Bind(R.id.info_strip_cloudiness)
    LinearLayout infoStripCloudiness;
    @Bind(R.id.rain)
    TextView rainText;
    @Bind(R.id.info_strip_rain)
    LinearLayout infoStripRain;
    @Bind(R.id.wind)
    TextView windText;
    @Bind(R.id.info_strip_wind)
    LinearLayout infoStripWind;
    @Bind(R.id.snow)
    TextView snowText;
    @Bind(R.id.info_strip_snow)
    LinearLayout infoStripSnow;
    @Bind(R.id.pressure_sea_ground)
    TextView pressureSeaGroundText;
    @Bind(R.id.humidity)
    TextView humidityText;

    private DayForecast forecast;

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
        timelineChooser.setOnSeekBarChangeListener(this);
        timelineChooser.setKeyProgressIncrement(FORECAST_STEP_SIZE);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    public void setForecast(DayForecast forecast) {
        this.forecast = forecast;
        if (forecast.isToday()) {
            timelineChooser.setProgress(todaysProgress());
        } else {
            resetForecastInfo(0);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
        if (forecast.isToday()) {
            if (value < todaysProgress()) {
                timelineChooser.setProgress(todaysProgress());
                resetForecastInfo(0);
            } else {
                resetForecastInfo(value - todaysProgress());
            }
        } else {
            resetForecastInfo(value);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void resetForecastInfo(int value) {
        WeatherInfo weatherInfo = forecast.getHourlyWeatherInfos().get(value);
        if (forecast.isToday() && value == 0) {
            timelineLabel.setText(getResources().getString(R.string.now));
        } else {
            timelineLabel.setText(weatherInfo.getTime());
        }
        mainTempText.setText(weatherInfo.getMainTemp());
        String iconUrl = weatherInfo.getIconUrl();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(getContext()).load(iconUrl).into(weatherIcon);
        }
        handleOptionalStrip(weatherInfo.getCloudiness(), infoStripCloudiness, cloudText);
        handleOptionalStrip(weatherInfo.getRainVolume(), infoStripRain, rainText);
        handleOptionalStrip(weatherInfo.getWindInfo(), infoStripWind, windText);
        handleOptionalStrip(weatherInfo.getSnowVolume(), infoStripSnow, snowText);
        pressureSeaGroundText.setText(weatherInfo.getPressureInfo());
        humidityText.setText(weatherInfo.getHumidity());
    }

    private void handleOptionalStrip(String text, LinearLayout layout, TextView textView) {
        if (TextUtils.isEmpty(text)) {
            layout.setVisibility(GONE);
        } else {
            layout.setVisibility(VISIBLE);
            textView.setText(text);
        }
    }

    private int todaysProgress() {
        return DATA_POINTS_PER_DAY - forecast.getHourlyWeatherInfos().size();
    }
}
