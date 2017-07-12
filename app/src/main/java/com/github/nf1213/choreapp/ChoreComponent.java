package com.github.nf1213.choreapp;

import com.github.nf1213.choreapp.ui.AddChoreViewModel;
import com.github.nf1213.choreapp.ui.ChoreListViewModel;
import com.github.nf1213.choreapp.ui.ChoreModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nicole Felch on 6/7/17.
 */

@Singleton
@Component(modules = {ChoreModule.class})
public interface ChoreComponent {

    void inject(AddChoreViewModel addChoreViewModel);

    void inject(ChoreListViewModel choreListViewModel);

    interface Injectable {
        void inject(ChoreComponent choreComponent);
    }
}