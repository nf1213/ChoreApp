package com.github.nf1213.choreapp.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nf1213.choreapp.R;

/**
 * Created by Nicole Felch on 7/11/17.
 */

public class SearchFragment extends LifecycleFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);

        return rootView;
    }
}
