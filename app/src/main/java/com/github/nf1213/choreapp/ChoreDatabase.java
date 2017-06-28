package com.github.nf1213.choreapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by Nicole Felch on 6/7/17.
 */
@Database(entities = {Chore.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class ChoreDatabase extends RoomDatabase {
    public abstract ChoreDao choreDao();
}