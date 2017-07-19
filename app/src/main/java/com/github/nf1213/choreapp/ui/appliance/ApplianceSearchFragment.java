package com.github.nf1213.choreapp.ui.appliance;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.github.nf1213.choreapp.ChoreApplication;
import com.github.nf1213.choreapp.ChoreFactory;
import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.networking.ApplianceSearchResult;

/**
 * Created by Nicole Felch on 7/11/17.
 */

public class ApplianceSearchFragment extends LifecycleFragment implements View.OnClickListener, android.widget.TextView.OnEditorActionListener, ApplianceSearchAdapter.OnItemClickListener {

    private ApplianceSearchViewModel viewModel;
    private String mKeyword;
    private EditText mSearchInput;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.search_fragment, container, false);

        ApplianceSearchAdapter adapter = new ApplianceSearchAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new ChoreFactory((ChoreApplication) getActivity().getApplication())).get(ApplianceSearchViewModel.class);
        viewModel.getAppliances().observe(this, adapter::setData);
        return recyclerView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_fragment, menu);

        MenuItem searchBarItem = menu.findItem(R.id.search_bar_item);
        View view = searchBarItem.getActionView();

        mSearchInput = (EditText) view.findViewById(R.id.search_input);

        final View btn_clear = view.findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        mSearchInput.setTag(btn_clear);
        mSearchInput.setHint(R.string.search);
        mSearchInput.setOnEditorActionListener(this);
        mSearchInput.addTextChangedListener(mSearchInputWatcher);
    }

    @Override
    public void onClick(View v) {
        mSearchInput.setText("");
        viewModel.applianceSearch("");
    }

    private TextWatcher mSearchInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mKeyword = mSearchInput.getText().toString().trim();
            if (mKeyword.length() >= 2) {
                viewModel.applianceSearch(mKeyword);
            }
        }
    };

    @Override
    public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.applianceSearch(mKeyword);
            return true;
        }
        return false;
    }

    @Override
    public void onItemClicked(ApplianceSearchResult data) {
        startActivity(ApplianceDetailsActivity.getIntent(getActivity(), data.id, data.name));
    }
}
