package com.github.nf1213.choreapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nicole Felch on 6/8/17.
 */

public class ChoreListViewModel extends ViewModel implements ChoreComponent.Injectable {
    @Inject
    ChoreRepository choreRepository;
    private LiveData<List<Chore>> chores = new MutableLiveData<>();

    @Override
    public void inject(ChoreComponent choreComponent) {
        choreComponent.inject(this);
        chores = choreRepository.getChores();
    }

    public LiveData<List<Chore>> getChores() {
        return chores;
    }

    public void deleteChore(Chore chore) {
        choreRepository.deleteChore(chore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.v("TAG", "onComplete - deleted event");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("TAG", "OnError - deleted event: ", e);
                    }
                });
    }
}
