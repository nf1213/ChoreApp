package com.github.nf1213.choreapp.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.nf1213.choreapp.ChoreApplication;
import com.github.nf1213.choreapp.ChoreFactory;
import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.ReminderTask;
import com.github.nf1213.choreapp.datastorage.Chore;

import java.util.ArrayList;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.github.nf1213.choreapp.ui.AddEditChoreActivity.KEY_CHORE_ID;

/**
 * Created by Nicole Felch on 7/11/17.
 */

public class ChoreListFragment extends LifecycleFragment implements ChoreAdapter.DeleteListener, ChoreAdapter.ClickListener, ChoreAdapter.CheckListener{
    ChoreListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chore_fragment, container, false);

        ChoreAdapter adapter = new ChoreAdapter(new ArrayList<>(), this, this, this);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new ChoreFactory((ChoreApplication) getActivity().getApplication())).get(ChoreListViewModel.class);

        viewModel.getChores().observe(this, adapter::setItems);

        viewModel.getChores().observe(this, chores -> {
            Log.d("TAG", "Events Changed:" + chores);
            adapter.setItems(chores);
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new SearchFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void delete(Chore chore) {
        viewModel.deleteChore(chore);
    }

    @Override
    public void onClick(int choreId) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CHORE_ID, choreId);
        Intent intent = new Intent(getActivity(), AddEditChoreActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onCheckChanged(Chore chore, boolean isChecked) {
        chore.isChecked = isChecked;
        viewModel.updateChore(chore).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.v("TAG", "onComplete - updated chore");
                        if (chore.isChecked) {
                            ReminderTask.cancel(getActivity(), chore);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.v("TAG", "OnError - updated chore: ", e);
                    }
                });
    }
}
