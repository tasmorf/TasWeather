package com.example.metis.tasweather.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.DateHandler;
import com.example.metis.tasweather.model.bean.DayForecast;
import com.example.metis.tasweather.model.bean.WeatherInfo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.metis.tasweather.module.DateHandlerModule.jodaDateHandler;

/**
 * A View representing the forecast for a single day.
 */
public class DailyForecastLayout extends ScrollView implements SeekBar.OnSeekBarChangeListener {

    public static final int STEP_SIZE = 3;
    private static final int HOURS_IN_DAY = 24;
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

    private DateHandler dateHandler;

    private DayForecast forecast;

    public DailyForecastLayout(Context context) {
        super(context);
        init(jodaDateHandler());
    }

    public DailyForecastLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(jodaDateHandler());
    }

    public DailyForecastLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(jodaDateHandler());
    }

    private void init(DateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        timelineChooser.setOnSeekBarChangeListener(this);
        timelineChooser.setKeyProgressIncrement(STEP_SIZE);
    }

    public void setForecast(DayForecast forecast) {
        this.forecast = forecast;
        timelineChooser.setMax(HOURS_IN_DAY / STEP_SIZE - 1);
        if (forecast.isToday()) {
            timelineChooser.setProgress(todaysProgress());
        }
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

    private int todaysProgress() {
        return dateHandler.getCurrentHourOfDay() / STEP_SIZE + 1;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
