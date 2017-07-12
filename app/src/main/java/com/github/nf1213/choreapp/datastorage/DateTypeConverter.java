package com.github.nf1213.choreapp.datastorage;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Nicole Felch on 6/27/17.
 */
public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}