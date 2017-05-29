package com.example.metis.tasweather;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.metis.tasweather.model.bean.DayForecast;
import com.example.metis.tasweather.model.bean.WeatherInfo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * A View representing the forecast for a single day.
 */
public class DailyForecastLayout extends ScrollView implements RatingBar.OnRatingBarChangeListener {

    @Bind(R.id.timeline_chooser)
    RatingBar timelineChooser;
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
    }

    public void setForecast(DayForecast forecast) {
        this.forecast = forecast;
        resetForecastInfo();
    }

    private void resetForecastInfo() {
        WeatherInfo weatherInfo = forecast.getHourlyWeatherInfos().get(timelineChooser.getProgress());
        mainTempText.setText(weatherInfo.getMainTemp());
        String iconUrl = weatherInfo.getIconUrl();
        if(!TextUtils.isEmpty(iconUrl)) {
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
    public void onRatingChanged(RatingBar ratingBar, float value, boolean byUser) {
        resetForecastInfo();
    }
}
