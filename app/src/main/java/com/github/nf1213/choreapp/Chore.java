package com.github.nf1213.choreapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static java.text.DateFormat.DATE_FIELD;

/**
 * Created by Nicole Felch on 6/7/17.
 */
@Entity
public class Chore {
    public static final String DATE_FIELD = "date";

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public boolean isChecked;
    @ColumnInfo(name = DATE_FIELD)
    public Date date;

    @Ignore
    public Chore(String name) {
        this.name = name;
    }

    public Chore() { /**/ }
}
