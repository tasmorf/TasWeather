package com.example.metis.tasweather;

import com.example.metis.tasweather.util.FakeServer;
import com.example.metis.tasweather.util.IntentLauncher;
import com.example.metis.tasweather.util.WeatherRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 */

public class MainScreenTest {

    @Rule
    public WeatherRule rule = new WeatherRule(false);

    @Test
    public void makeSureAllLooksRight() throws Exception {
        FakeServer.enqueueSuccessResponse("/data/2.5/forecast", "forecast");

        IntentLauncher.launchApp();

        onView(allOf(withId(R.id.main_temp), isDisplayed())).check(matches(withText("34.2")));
    }
}
