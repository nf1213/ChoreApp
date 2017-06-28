package com.github.nf1213.choreapp;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by Nicole Felch on 6/7/17.
 */

public class ChoreRepositoryImpl implements ChoreRepository{

    @Inject
    ChoreDatabase choreDatabase;

    public ChoreRepositoryImpl(ChoreDatabase eventDatabase) {
        this.choreDatabase = eventDatabase;
    }

    @Override
    public Completable addChore(Chore chore) {
        return Completable.fromAction(() -> choreDatabase.choreDao().insertChore(chore));
    }

    @Override
    public LiveData<List<Chore>> getChores() {
        //Here is where we would do more complex logic, like getting events from a cache
        //then inserting into the database etc. In this example we just go straight to the dao.
        return choreDatabase.choreDao().getChores();
    }

    @Override
    public LiveData<Chore> getChore(int id) {
        return choreDatabase.choreDao().getChore(id);
    }

    @Override
    public Completable deleteChore(Chore chore) {
        return Completable.fromAction(() -> choreDatabase.choreDao().deleteChore(chore));
    }

    @Override
    public Completable updateChore(Chore chore) {
        return Completable.fromAction(() -> choreDatabase.choreDao().updateChore(chore));
    }
}
