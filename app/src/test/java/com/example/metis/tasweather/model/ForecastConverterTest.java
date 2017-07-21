package com.example.metis.tasweather.model;

import android.app.Application;
import android.content.res.Resources;

import com.example.metis.tasweather.BuildConfig;
import com.example.metis.tasweather.WeatherApplication;
import com.example.metis.tasweather.model.bean.realm.Forecast;
import com.example.metis.tasweather.model.bean.realm.WeatherInfo;
import com.example.metis.tasweather.model.bean.server.ServerCity;
import com.example.metis.tasweather.model.bean.server.ServerCloudsInfo;
import com.example.metis.tasweather.model.bean.server.ServerForecast;
import com.example.metis.tasweather.model.bean.server.ServerMainWeatherInfo;
import com.example.metis.tasweather.model.bean.server.ServerOverallWeatherInfo;
import com.example.metis.tasweather.model.bean.server.ServerVolumeInfo;
import com.example.metis.tasweather.model.bean.server.ServerWeatherDataPoint;
import com.example.metis.tasweather.model.bean.server.ServerWindInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = Application.class, constants = BuildConfig.class, sdk = 21)
public class ForecastConverterTest {

    private static final String A_CITY_NAME = "aCityName";
    private static final String A_COUNTRY_NAME = "aCountryName";
    private static final String A_TIME_STRING = "12:40";
    private static final long A_TIMESTAMP = 1496080800L;
    private static final String A_DAY_NAME = "aDayName";
    private static final ServerCity A_CITY = new ServerCity("", A_CITY_NAME, A_COUNTRY_NAME);
    private static final ServerMainWeatherInfo A_MAIN_INFO = new ServerMainWeatherInfo(32.4, 12, 22.2);
    private static final ServerCloudsInfo A_CLOUD_INFO = new ServerCloudsInfo(11);
    private static final ServerWindInfo A_WIND_INFO = new ServerWindInfo(12.43, 7.50464);
    private static final ServerVolumeInfo A_RAIN_INFO = new ServerVolumeInfo(0.44);
    private static final ServerVolumeInfo A_SNOW_INFO = new ServerVolumeInfo(0.12);
    private static final ServerOverallWeatherInfo AN_OVERALL_WEATHER = new ServerOverallWeatherInfo("", "anIconId", "", "");
    private static final List<ServerOverallWeatherInfo> AN_OVERALL_WEATHER_LIST = Arrays.asList(AN_OVERALL_WEATHER);
    private static final ServerWeatherDataPoint A_DATA_POINT = new ServerWeatherDataPoint(A_TIMESTAMP, A_MAIN_INFO,
            A_CLOUD_INFO, A_WIND_INFO, A_RAIN_INFO, A_SNOW_INFO, AN_OVERALL_WEATHER_LIST);
    private static final List<ServerWeatherDataPoint> DATA_POINT_LIST = Arrays.asList(A_DATA_POINT);

    private Resources resources = RuntimeEnvironment.application.getResources();

    @Mock
    DateHandler dateHandler;

    private ForecastConverter converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new ForecastConverter(resources, dateHandler);
    }

    @Test
    public void whenHourBefore21ReturnsListWithFiveItems() throws ConversionException {
        when(dateHandler.getCurrentHourOfDay()).thenReturn(20);
        Forecast result = convert();
        assertThat(result.getForecastList()).hasSize(5);
    }

    @Test
    public void whenHourAfter21ReturnsListWithFourItems() throws ConversionException {
        when(dateHandler.getCurrentHourOfDay()).thenReturn(22);
        Forecast result = convert();
        assertThat(result.getForecastList()).hasSize(4);
    }

    @Test
    public void theCityNameIsConvertedCorrectly() throws ConversionException {
        Forecast result = convert();
        assertThat(result.getCity()).isEqualTo(A_CITY_NAME + ", " + A_COUNTRY_NAME);
    }

    @Test
    public void eachItemHasTheDayOfTheWeekAsATitle() throws ConversionException {
        for (int i = 0; i < 5; i++) {
            when(dateHandler.getDayOfWeekFromOffset(i)).thenReturn(A_DAY_NAME + i);
        }

        Forecast result = convert();

        for (int i = 0; i < 5; i++) {
            assertThat(result.getForecastList().get(i).getTitle()).isEqualTo(A_DAY_NAME + i);
        }
    }

    @Test
    public void todaysItemHasTheFlagOn() throws ConversionException {
        Forecast result = convert();

        assertThat(result.getForecastList().get(0).isToday()).isTrue();
        for (int i = 1; i < 5; i++) {
            assertThat(result.getForecastList().get(i).isToday()).isFalse();
        }
    }

    @Test
    public void ignoresPastDataPoints() throws ConversionException {
        when(dateHandler.isBeforeNow(A_TIMESTAMP)).thenReturn(true);

        Forecast result = convert();

        assertThat(result.getForecastList().get(0).getHourlyWeatherInfos()).isEmpty();
    }

    @Test
    public void ignoresDataPointsOutsideTheSameDay() throws ConversionException {
        when(dateHandler.isBeforeNow(A_TIMESTAMP)).thenReturn(false);
        when(dateHandler.getDaysOffsetForTimestamp(A_TIMESTAMP)).thenReturn(1);

        Forecast result = convert();

        assertThat(result.getForecastList().get(0).getHourlyWeatherInfos()).isEmpty();
    }

    @Test
    public void populatesWithDataPointInfo() throws ConversionException {
        when(dateHandler.isBeforeNow(A_TIMESTAMP)).thenReturn(false);
        when(dateHandler.getDaysOffsetForTimestamp(A_TIMESTAMP)).thenReturn(0);
        when(dateHandler.getTimeStringForTimeStamp(A_TIMESTAMP)).thenReturn(A_TIME_STRING);

        Forecast result = convert();

        WeatherInfo info = result.getForecastList().get(0).getHourlyWeatherInfos().get(0);
        assertThat(info.getTime()).isEqualTo(A_TIME_STRING);
        assertThat(info.getCloudiness()).isEqualTo("11%");
        assertThat(info.getHumidity()).isEqualTo("12%");
        assertThat(info.getRainVolume()).isEqualTo("0.44 mm");
        assertThat(info.getSnowVolume()).isEqualTo("0.12 mm");
        assertThat(info.getPressureInfo()).isEqualTo("22.20 hPa");
        assertThat(info.getWindInfo()).isEqualTo("N - 12.43 m/s");
        assertThat(info.getIconUrl()).isEqualTo("http://openweathermap.org/img/w/anIconId.png");
        assertThat(info.getMainTemp()).isEqualTo("32.4");
    }

    private Forecast convert() throws ConversionException {
        return converter.convert(new ServerForecast(DATA_POINT_LIST, A_CITY));
    }

}