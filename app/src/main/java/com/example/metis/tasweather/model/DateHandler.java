package com.example.metis.tasweather.model;


public interface DateHandler {
    /**
     * Given a timestamp, how many days after today is it
     */
    int getDaysOffsetForTimestamp(long timestamp);

    /**
     * Will return 'Today' for 0, 'Tomorrow' for 1, and the day of the week for other offsets from
     * today
     * @param offset
     */
    String getDayOfWeekFromOffset(int offset);

    /**
     * Will return a time liek xx:xx based on the  hour
     * @param timeStamp
     */
    String getTimeStringForTimeStamp(long timeStamp);

    int getCurrentHourOfDay();

    boolean isBeforeNow(long timestamp);
}
