package com.github.nf1213.choreapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Nicole Felch on 6/7/17.
 */
@Entity
public class Chore {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @Ignore
    public Chore(String name) {
        this.name = name;
    }

    public Chore() {
    }
}
