package com.example.metis.tasweather.util;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.example.metis.tasweather.ui.MainActivity;

/**
 * The bulk of this class was copied over from {@link android.support.test.rule.ActivityTestRule}.
 * What it does is launch and finish an activity, and it also makes it possible to set Intent traps.
 */
public class IntentLauncher {

    private static final String TAG = "IntentLauncher";
    private static final Class MAIN_ACTIVITY_CLASS = MainActivity.class;

    public static void launchActivity(@Nullable Intent startIntent) {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        String targetPackage = instrumentation.getTargetContext().getPackageName();
        if (startIntent == null) {
            startIntent = getDefaultIntent();
        }
        startIntent.setClassName(targetPackage, MAIN_ACTIVITY_CLASS.getName());
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d(TAG, String.format("Launching activity %s", MAIN_ACTIVITY_CLASS.getName()));
        instrumentation.startActivitySync(startIntent);
        instrumentation.waitForIdleSync();
    }

    public static void launchApp() {
        launchActivity(getDefaultIntent());
    }

    private static Intent getDefaultIntent() {
        return new Intent("android.intent.action.MAIN");
    }
}
