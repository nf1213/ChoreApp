package com.github.nf1213.choreapp.ui.chore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.nf1213.choreapp.R;

/**
 * Since {@link android.arch.lifecycle.LifecycleActivity} is not appcompat, I'm just embedding a
 * {@link android.arch.lifecycle.LifecycleFragment} into and {@link AppCompatActivity}
 */
public class AddEditChoreActivity extends AppCompatActivity {
    public static final String KEY_CHORE_ID = "chore_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
    }
}
