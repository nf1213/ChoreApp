package com.github.nf1213.choreapp.ui.appliance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.github.nf1213.choreapp.ChoreComponent;
import com.github.nf1213.choreapp.networking.Appliance;
import com.github.nf1213.choreapp.networking.RestServiceInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicole Felch on 7/19/17.
 */

public class ApplianceDetailsViewModel extends ViewModel implements ChoreComponent.Injectable {

    @Inject
    RestServiceInterface restService;

    private MutableLiveData<Appliance> appliance = new MutableLiveData<>();

    @Override
    public void inject(ChoreComponent choreComponent) {
        choreComponent.inject(this);
    }

    MutableLiveData<Appliance> getAppliance(int id) {
        restService.getAppliance(id).enqueue(new Callback<Appliance>() {
            @Override
            public void onResponse(@NonNull Call<Appliance> call, @NonNull Response<Appliance> response) {
                appliance.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Appliance> call, @NonNull Throwable t) {

            }
        });
        return appliance;
    }

}
