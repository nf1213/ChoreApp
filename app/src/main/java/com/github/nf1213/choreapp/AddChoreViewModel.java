package com.github.nf1213.choreapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import javax.inject.Inject;

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

    public void putChore(Chore chore) {
        choreRepository.putChore(chore).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.v("TAG", "onComplete - successfully added event");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("TAG", "onError - add:", e);
                    }
                });
    }

    public LiveData<Chore> getChore(int id) {
        return choreRepository.getChore(id);
    }

    public void inject(ChoreComponent choreComponent) {
        choreComponent.inject(this);
    }
}
