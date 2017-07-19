package com.github.nf1213.choreapp;

import android.app.Application;

import com.github.nf1213.choreapp.ui.chore.ChoreModule;

/**
 * Created by Nicole Felch on 6/7/17.
 */
public class ChoreApplication extends Application {
    private final ChoreComponent choreComponent = createChoreComponent();

    protected ChoreComponent createChoreComponent() {
        return DaggerChoreComponent.builder()
                .choreModule(new ChoreModule(this))
                .build();
    }

    public ChoreComponent getChoreComponent() {
        return choreComponent;
    }
}
