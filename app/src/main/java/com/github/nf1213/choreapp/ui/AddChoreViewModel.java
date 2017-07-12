package com.github.nf1213.choreapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.github.nf1213.choreapp.ChoreComponent;
import com.github.nf1213.choreapp.datastorage.Chore;
import com.github.nf1213.choreapp.datastorage.ChoreRepository;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by Nicole Felch on 6/7/17.
 */
public class AddChoreViewModel extends ViewModel implements ChoreComponent.Injectable {

    @Inject
    ChoreRepository choreRepository;

    public Completable addChore(Chore chore) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 1);
        chore.date = c.getTime();
        return choreRepository.addChore(chore);
    }

    public LiveData<Chore> getChore(int id) {
        return choreRepository.getChore(id);
    }

    public void inject(ChoreComponent choreComponent) {
        choreComponent.inject(this);
    }
}
