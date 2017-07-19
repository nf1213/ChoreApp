package com.github.nf1213.choreapp;

import com.github.nf1213.choreapp.ui.appliance.ApplianceDetailsViewModel;
import com.github.nf1213.choreapp.ui.chore.AddChoreViewModel;
import com.github.nf1213.choreapp.ui.chore.ChoreListViewModel;
import com.github.nf1213.choreapp.ui.chore.ChoreModule;
import com.github.nf1213.choreapp.ui.appliance.ApplianceSearchViewModel;

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

    void inject(ApplianceSearchViewModel applianceSearchViewModel);

    void inject(ApplianceDetailsViewModel applianceDetailsViewModel);

    interface Injectable {
        void inject(ChoreComponent choreComponent);
    }
}