package com.github.nf1213.choreapp;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import static com.github.nf1213.choreapp.AddEditChoreActivity.KEY_CHORE_ID;

public class ChoreListActivity extends LifecycleActivity implements ChoreAdapter.DeleteListener, ChoreAdapter.ClickListener, ChoreAdapter.CheckListener {

    ChoreListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ChoreListActivity.this, AddEditChoreActivity.class);
            startActivity(intent);
        });

        ChoreAdapter adapter = new ChoreAdapter(new ArrayList<>(), this, this, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new ChoreFactory((ChoreApplication)getApplication())).get(ChoreListViewModel.class);

        viewModel.getChores().observe(this, adapter::setItems);

        viewModel.getChores().observe(this, chores -> {
            Log.d("TAG", "Events Changed:" + chores);
            adapter.setItems(chores);
        });
    }

    @Override
    public void delete(Chore chore) {
        viewModel.deleteChore(chore);
    }

    @Override
    public void onClick(int choreId) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CHORE_ID, choreId);
        Intent intent = new Intent(ChoreListActivity.this, AddEditChoreActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onCheckChanged(Chore chore, boolean isChecked) {
        chore.isChecked = isChecked;
        viewModel.updateChore(chore);
    }
}
