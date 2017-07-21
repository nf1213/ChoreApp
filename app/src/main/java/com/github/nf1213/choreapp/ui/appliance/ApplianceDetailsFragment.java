package com.github.nf1213.choreapp.ui.appliance;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nf1213.choreapp.ChoreApplication;
import com.github.nf1213.choreapp.ChoreFactory;
import com.github.nf1213.choreapp.R;

import static com.github.nf1213.choreapp.ui.appliance.ApplianceDetailsActivity.KEY_APPLIANCE_ID;
import static com.github.nf1213.choreapp.ui.appliance.ApplianceDetailsActivity.KEY_APPLIANCE_NAME;

/**
 * Created by Nicole Felch on 7/20/17.
 */
public class ApplianceDetailsFragment extends LifecycleFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        if (getActivity().getIntent().getIntExtra(KEY_APPLIANCE_ID, -1) == -1) throw new IllegalArgumentException("Appliance id required");
        int applianceId = getActivity().getIntent().getIntExtra(KEY_APPLIANCE_ID, -1);
        String title = getActivity().getIntent().getStringExtra(KEY_APPLIANCE_NAME);
        getActivity().setTitle(title);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ApplianceChoreAdapter adapter = new ApplianceChoreAdapter();
        recyclerView.setAdapter(adapter);

        ApplianceDetailsViewModel viewModel = ViewModelProviders.of(this, new ChoreFactory((ChoreApplication) getActivity().getApplication())).get(ApplianceDetailsViewModel.class);
        viewModel.getAppliance(applianceId).observe(this, appliance -> {
            adapter.setData(appliance.chores);
        });
        return rootView;
    }
}
