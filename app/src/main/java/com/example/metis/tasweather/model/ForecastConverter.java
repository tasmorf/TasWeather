package com.example.metis.tasweather.model;

import android.content.res.Resources;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.bean.DayForecast;
import com.example.metis.tasweather.model.bean.Forecast;
import com.example.metis.tasweather.model.bean.WeatherInfo;
import com.example.metis.tasweather.model.bean.server.ServerCity;
import com.example.metis.tasweather.model.bean.server.ServerForecast;
import com.example.metis.tasweather.model.bean.server.ServerVolumeInfo;
import com.example.metis.tasweather.model.bean.server.ServerWeatherDataPoint;
import com.example.metis.tasweather.model.bean.server.ServerWindInfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;


class ForecastConverter implements Converter<ResponseBody, Forecast> {
    private static final int MAX_DAYS_FORECAST = 5;
    private static final int LAST_AVAILABLE_HOUR = 21;
    private final Gson gson;
    private final Resources resources;
    private final DateHandler dateHandler;
    public static final String[] DIRECTIONS = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public ForecastConverter(Gson gson, Resources resources, DateHandler dateHandler) {
        this.gson = gson;
        this.resources = resources;
        this.dateHandler = dateHandler;
    }

    @Override
    public Forecast convert(ResponseBody value) throws IOException {
        ServerForecast serverForecast = gson.fromJson(value.charStream(), ServerForecast.class);
        List<DayForecast> forecastList = new ArrayList<>();

        int minIndex = 0;
        if (dateHandler.getCurrentHourOfDay() > LAST_AVAILABLE_HOUR) {
            minIndex = 1;
        }
        for (int i = minIndex; i < MAX_DAYS_FORECAST; i++) {
            DayForecast.Builder builder = DayForecast.newBuilder()
                    .title(dateHandler.getDayOfWeekFromOffset(i))
                    .isToday(i == 0);
            List<WeatherInfo> weatherInfoList = new ArrayList<>();
            for (ServerWeatherDataPoint serverWeatherDataPoint : serverForecast.getWeatherDataPoints()) {
                if (dateHandler.getDaysOffsetForTimestamp(serverWeatherDataPoint.getTimestamp()) != i
                        || dateHandler.isBeforeNow(serverWeatherDataPoint.getTimestamp())) {
                    // ignore data points not within the day we care about
                    continue;
                }
                double temp = serverWeatherDataPoint.getMain().getTemp();
                int cloudiness = serverWeatherDataPoint.getClouds().getAll();
                int humidity = serverWeatherDataPoint.getMain().getHumidity();
                double pressure = serverWeatherDataPoint.getMain().getPressure();

                WeatherInfo.Builder weatherInfoBuilder = WeatherInfo.newBuilder()
                        .mainTemp(String.format("%.1f", temp))
                        .time(dateHandler.getTimeStringForTimeStamp(serverWeatherDataPoint.getTimestamp()))
                        .iconUrl(resources.getString(R.string.icon_url, serverWeatherDataPoint.getOverallWeather().get(0).getIcon()))
                        .cloudiness(resources.getString(R.string.percentage_int, cloudiness))
                        .humidity(resources.getString(R.string.percentage_int, humidity))
                        .pressureInfo(resources.getString(R.string.pressure_with_units, pressure));

                ServerVolumeInfo rain = serverWeatherDataPoint.getRain();
                if (rain != null) {
                    weatherInfoBuilder.rainVolume(resources.getString(R.string.volume_with_units, rain.getVolume()));
                }

                ServerVolumeInfo snow = serverWeatherDataPoint.getSnow();
                if (snow != null) {
                    weatherInfoBuilder.snowVolume(resources.getString(R.string.volume_with_units, snow.getVolume()));
                }

                ServerWindInfo windInfo = serverWeatherDataPoint.getWind();
                if (windInfo != null) {
                    String direction = DIRECTIONS[(int) Math.round(((windInfo.getDeg() % 360) / 45)) % 8];
                    weatherInfoBuilder.windInfo(resources.getString(R.string.wind_info, direction, windInfo.getSpeed()));
                }
                // This data point belongs to this offset
                weatherInfoList.add(weatherInfoBuilder.build());
            }
            builder.hourlyWeatherInfos(weatherInfoList);
            forecastList.add(builder.build());
        }

        ServerCity city = serverForecast.getCity();
        return Forecast.newBuilder()
                .city(city.getName() + ", " + city.getCountry())
                .forecastList(forecastList)
                .build();
    }
}
