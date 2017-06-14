package com.github.nf1213.choreapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by Nicole Felch on 6/8/17.
 */
public class ChoreFactory extends ViewModelProvider.NewInstanceFactory {

    private ChoreApplication application;

    public ChoreFactory(ChoreApplication application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof ChoreComponent.Injectable) {
            ((ChoreComponent.Injectable) t).inject(application.getChoreComponent());
        }
        return t;
    }
}