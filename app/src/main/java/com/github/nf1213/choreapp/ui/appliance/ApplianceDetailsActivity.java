package com.github.nf1213.choreapp.ui.appliance;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.nf1213.choreapp.ChoreApplication;
import com.github.nf1213.choreapp.ChoreFactory;
import com.github.nf1213.choreapp.R;

/**
 * Created by Nicole Felch on 7/19/17.
 */
public class ApplianceDetailsActivity extends AppCompatActivity {
    static final String KEY_APPLIANCE_ID = "appliance_id";
    static final String KEY_APPLIANCE_NAME = "appliance_name";

    public static Intent getIntent(Context context, int applianceId, String applianceName) {
        Intent intent = new Intent(context, ApplianceDetailsActivity.class);
        intent.putExtra(KEY_APPLIANCE_ID, applianceId);
        intent.putExtra(KEY_APPLIANCE_NAME, applianceName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_details);
    }
}
