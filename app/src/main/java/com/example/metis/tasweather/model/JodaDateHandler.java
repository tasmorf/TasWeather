package com.example.metis.tasweather.model;

import android.content.res.Resources;

import com.example.metis.tasweather.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;


public class JodaDateHandler implements DateHandler {
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
        return DateTimeFormat.forPattern("EEEE").print(new LocalDate().plusDays(offset));
    }

    @Override
    public int getHourOffsetFromStartOfDay(long timestamp) {
        return new DateTime(timestamp * 1000, DateTimeZone.UTC).getHourOfDay();
    }
}
