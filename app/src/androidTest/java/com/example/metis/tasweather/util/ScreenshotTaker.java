package com.example.metis.tasweather.util;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.jraska.falcon.Falcon;

import java.io.File;

public class ScreenshotTaker {
    private static final String TAG = ScreenshotTaker.class.getSimpleName();

    public static void takeScreenshot(String name) {
        String folderPath = "/test-screenshots/" + android.os.Build.MODEL + "/";
        String fullFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + folderPath;
        File dir = new File(fullFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final String path = fullFolderPath + name + ".png";
        final File imageFile = new File(path);
        Activity currentActivity = CurrentActivityGetter.getCurrentActivity();
        currentActivity.runOnUiThread(() -> {
            try {
                Falcon.takeScreenshot(currentActivity, imageFile);
            } catch (RuntimeException e) {
                // Do nothing, just log the exception
                String msg = "Unable to get screenshot " + imageFile.getAbsolutePath();
                Log.e(TAG, msg, e);
            }
        });
    }
}