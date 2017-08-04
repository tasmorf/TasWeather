package com.example.metis.tasweather.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Handler;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.MonitoringInstrumentation;
import android.util.Log;

import com.example.metis.tasweather.module.OkHttpModule;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.plugins.RxJavaPlugins;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.not;

/**
 * This is is the only rule that is required to set up the environment for everything you'll need to perform a test.
 * It does the following:
 * <p/>
 * <ul>
 * <li>Clear the app state</li>
 * <li>Setup and teardown the mock server</li>
 * <li>Set up a capture for all intents</li>
 * <li>Take screenshots</li>
 * </ul>
 */
public class WeatherRule extends ExternalResource {

    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private final boolean launchActivity;
    private final WeatherTestRunner instrumentation;
    private String className;
    private String methodName;

    public WeatherRule(final boolean launchApp) {
        this.launchActivity = launchApp;
        instrumentation = (WeatherTestRunner) InstrumentationRegistry.getInstrumentation();
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        Intents.init();
        AppState.cleanAppState();
        FakeServer.setup();
        Instrumentation.ActivityResult intentResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        Espresso.registerIdlingResources(OkHttp3IdlingResource.create("OkHttp3IdlingResource", OkHttpModule.okHttpClient()));
        RxEspressoScheduleHandler rxEspressoScheduleHandler = new RxEspressoScheduleHandler();
        RxJavaPlugins.setScheduleHandler(rxEspressoScheduleHandler);
        Espresso.registerIdlingResources(rxEspressoScheduleHandler.getIdlingResource());

        intending(not(isInternal())).respondWith(intentResult);
        if (launchActivity) {
            IntentLauncher.launchApp();
        }
    }

    @Override
    public Statement apply(Statement base, Description description) {
        className = description.getTestClass().getSimpleName();
        methodName = description.getMethodName();
        return super.apply(base, description);
    }

    @Override
    protected void after() {
        super.after();
        Intents.release();
        ScreenshotTaker.takeScreenshot(className + "_" + methodName);
        MonitoringInstrumentation.ActivityFinisher activityFinisher = instrumentation.new ActivityFinisher();
        mainThreadHandler.post(activityFinisher);
        instrumentation.waitForAllActivitiesToComplete();
    }
}