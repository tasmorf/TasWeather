package com.example.metis.tasweather.model;

import android.content.res.Resources;

import com.example.metis.tasweather.R;
import com.example.metis.tasweather.model.bean.realm.DayForecast;
import com.example.metis.tasweather.model.bean.realm.Forecast;
import com.example.metis.tasweather.model.bean.realm.WeatherInfo;
import com.example.metis.tasweather.model.bean.server.ServerCity;
import com.example.metis.tasweather.model.bean.server.ServerForecast;
import com.example.metis.tasweather.model.bean.server.ServerVolumeInfo;
import com.example.metis.tasweather.model.bean.server.ServerWeatherDataPoint;
import com.example.metis.tasweather.model.bean.server.ServerWindInfo;

import io.realm.RealmList;
import retrofit2.Response;

public class ForecastConverter implements Converter<Response<ServerForecast>, Forecast> {
    private static final int MAX_DAYS_FORECAST = 5;
    private static final int LAST_AVAILABLE_HOUR = 21;
    private final Resources resources;
    private final DateHandler dateHandler;
    private static final String[] DIRECTIONS = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public ForecastConverter(Resources resources, DateHandler dateHandler) {
        this.resources = resources;
        this.dateHandler = dateHandler;
    }

    @Override
    public Forecast convert(Response<ServerForecast> serverForecastResponse) throws ConversionException {
        Forecast result = new Forecast();
        if(!serverForecastResponse.isSuccessful()) {
            result.setError(true);
            return result;
        }
        ServerForecast serverForecast = serverForecastResponse.body();
        RealmList<DayForecast> forecastList = new RealmList<>();

        int minIndex = 0;
        if (dateHandler.getCurrentHourOfDay() >= LAST_AVAILABLE_HOUR) {
            minIndex = 1;
        }
        for (int i = minIndex; i < MAX_DAYS_FORECAST; i++) {
            DayForecast dayForecast = new DayForecast();
            dayForecast.setTitle(dateHandler.getDayOfWeekFromOffset(i));
            dayForecast.setToday(i == 0);
            RealmList<WeatherInfo> weatherInfoList = new RealmList<>();
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

                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setMainTemp(String.format("%.1f", temp));
                weatherInfo.setTime(dateHandler.getTimeStringForTimeStamp(serverWeatherDataPoint.getTimestamp()));
                weatherInfo.setIconUrl(resources.getString(R.string.icon_url, serverWeatherDataPoint.getOverallWeather().get(0).getIcon()));
                weatherInfo.setCloudiness(resources.getString(R.string.percentage_int, cloudiness));
                weatherInfo.setHumidity(resources.getString(R.string.percentage_int, humidity));
                weatherInfo.setPressureInfo(resources.getString(R.string.pressure_with_units, pressure));

                ServerVolumeInfo rain = serverWeatherDataPoint.getRain();
                if (rain != null) {
                    weatherInfo.setRainVolume(resources.getString(R.string.volume_with_units, rain.getVolume()));
                }

                ServerVolumeInfo snow = serverWeatherDataPoint.getSnow();
                if (snow != null) {
                    weatherInfo.setSnowVolume(resources.getString(R.string.volume_with_units, snow.getVolume()));
                }

                ServerWindInfo windInfo = serverWeatherDataPoint.getWind();
                if (windInfo != null) {
                    String direction = DIRECTIONS[(int) Math.round(((windInfo.getDeg() % 360) / 45)) % 8];
                    weatherInfo.setWindInfo(resources.getString(R.string.wind_info, direction, windInfo.getSpeed()));
                }
                // This data point belongs to this offset
                weatherInfoList.add(weatherInfo);
            }
            dayForecast.setHourlyWeatherInfos(weatherInfoList);
            forecastList.add(dayForecast);
        }

        ServerCity city = serverForecast.getCity();

        result.setCity(city.getName() + ", " + city.getCountry());
        result.setForecastList(forecastList);
        return result;
    }
}
