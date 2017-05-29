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
     * Will return 0, 3, 6, 9, 12, 15, 18, 21 depending on how long this is
     * from the start of a day
     * @param timestamp
     */
    int getHourOffsetFromStartOfDay(long timestamp);
}
