package com.github.nf1213.choreapp;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;

/**
 * Created by Nicole Felch on 6/7/17.
 */

public interface ChoreRepository {

    Completable putChore(Chore chore);

    LiveData<List<Chore>> getChores();

    LiveData<Chore> getChore(int id);

    Completable deleteChore(Chore chore);
}
