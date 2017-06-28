package com.github.nf1213.choreapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
