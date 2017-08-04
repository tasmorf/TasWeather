package com.example.metis.tasweather.util;

public class Waiter {
    public static void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
