package com.example.metis.tasweather.model;

import android.content.res.Resources;

import com.example.metis.tasweather.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class JodaDateHandler implements DateHandler {
    private static final long MILLIS_IN_HOUR = 60 * 60 * 1000;
    private Resources resources;

    public JodaDateHandler(Resources resources) {
        this.resources = resources;
    }

    @Override
    public int getDaysOffsetForTimestamp(long timestamp) {
        return Days.daysBetween(DateTime.now(), new DateTime(timestamp * 1000, DateTimeZone.UTC)).getDays();
    }

    @Override
    public String getDayOfWeekFromOffset(int offset) {
        if (offset == 0) {
            return resources.getString(R.string.today);
        } else if (offset == 1) {
            return resources.getString(R.string.tomorrow);
        }
        return DateTimeFormat.forPattern("EEEE").print(DateTime.now().plusDays(offset));
    }

    @Override
    public String getTimeStringForTimeStamp(long timeStamp) {
        return DateTimeFormat.forPattern("HH:mm").print(new DateTime(timeStamp * 1000, DateTimeZone.UTC).getHourOfDay() * MILLIS_IN_HOUR);
    }

    @Override
    public int getCurrentHourOfDay() {
        return DateTime.now().getHourOfDay();
    }

    @Override
    public boolean isBeforeNow(long timestamp) {
        return DateTime.now().isAfter(new DateTime(timestamp * 1000, DateTimeZone.UTC));
    }
}
