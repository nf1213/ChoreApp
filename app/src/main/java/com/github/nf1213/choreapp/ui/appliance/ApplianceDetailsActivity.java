package com.github.nf1213.choreapp.ui.appliance;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nf1213.choreapp.ChoreApplication;
import com.github.nf1213.choreapp.ChoreFactory;
import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.networking.Appliance;

/**
 * Created by Nicole Felch on 7/19/17.
 */
public class ApplianceDetailsActivity extends LifecycleActivity {
    private static final String KEY_APPLIANCE_ID = "appliance_id";
    private static final String KEY_APPLIANCE_NAME = "appliance_name";

    ApplianceDetailsViewModel viewModel;
    Appliance data;

    public static Intent getIntent(Context context, int applianceId, String applianceName) {
        Intent intent = new Intent(context, ApplianceDetailsActivity.class);
        intent.putExtra(KEY_APPLIANCE_ID, applianceId);
        intent.putExtra(KEY_APPLIANCE_NAME, applianceName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appliance_details_fragment);
        if (getIntent().getIntExtra(KEY_APPLIANCE_ID, -1) == -1) throw new IllegalArgumentException("Appliance id required");
        int applianceId = getIntent().getIntExtra(KEY_APPLIANCE_ID, -1);
        String applianceName = getIntent().getStringExtra(KEY_APPLIANCE_NAME);
        setTitle(applianceName);
        TextView applianceNameTv = (TextView) findViewById(R.id.name);
        applianceNameTv.setText(applianceName);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chore_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ApplianceChoreAdapter adapter = new ApplianceChoreAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new ChoreFactory((ChoreApplication) getApplication())).get(ApplianceDetailsViewModel.class);
        viewModel.getAppliance(applianceId).observe(this, appliance -> {
            data = appliance;
            applianceNameTv.setText(data.name);
            adapter.setData(data.chores);
        });
    }
}
