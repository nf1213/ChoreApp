package com.github.nf1213.choreapp.ui.appliance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.github.nf1213.choreapp.ChoreComponent;
import com.github.nf1213.choreapp.networking.ApplianceSearchResult;
import com.github.nf1213.choreapp.networking.RestServiceInterface;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicole Felch on 7/19/17.
 */

public class ApplianceSearchViewModel extends ViewModel implements ChoreComponent.Injectable {
    @Inject
    RestServiceInterface restService;
    private MutableLiveData<List<ApplianceSearchResult>> appliances = new MutableLiveData<>();
    // todo cleanup
    private Queue callQueue = new LinkedList();

    public LiveData<List<ApplianceSearchResult>> getAppliances() {
        return appliances;
    }

    @Override
    public void inject(ChoreComponent choreComponent) {
        choreComponent.inject(this);
    }

    public void applianceSearch(String searchString) {
        Call<List<ApplianceSearchResult>> call = restService.searchAppliances(searchString);
        call.enqueue(new Callback<List<ApplianceSearchResult>>() {
            @Override
            public void onResponse(@NonNull Call<List<ApplianceSearchResult>> call, @NonNull Response<List<ApplianceSearchResult>> response) {
                if (response.body() != null) appliances.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<ApplianceSearchResult>> call, @NonNull Throwable t) {

            }
        });
        callQueue.add(call);
    }
}
