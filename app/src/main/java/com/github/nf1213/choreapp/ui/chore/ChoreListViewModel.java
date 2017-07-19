package com.github.nf1213.choreapp.ui.chore;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.github.nf1213.choreapp.ChoreComponent;
import com.github.nf1213.choreapp.datastorage.Chore;
import com.github.nf1213.choreapp.datastorage.ChoreRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

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

    LiveData<List<Chore>> getChores() {
        return chores;
    }

    void deleteChore(Chore chore) {
        choreRepository.deleteChore(chore);
    }

    Completable updateChore(Chore chore) {
        return choreRepository.updateChore(chore);
    }
}
