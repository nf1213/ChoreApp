package com.github.nf1213.choreapp.ui.chore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.datastorage.Chore;

import java.util.List;

/**
 * Created by Nicole Felch on 6/8/17.
 */
class ChoreAdapter extends RecyclerView.Adapter {

    interface DeleteListener {
        void delete(Chore chore);
    }

    interface ClickListener {
        void onClick(int choreId);
    }

    interface CheckListener {
        void onCheckChanged(Chore chore, boolean isChecked);
    }

    private List<Chore> choreList;
    private DeleteListener deleteListener;
    private ClickListener clickListener;
    private CheckListener checkListener;

    ChoreAdapter(List<Chore> choreList, ClickListener clickListener, DeleteListener deleteListener, CheckListener checkListener) {
        this.choreList = choreList;
        this.deleteListener = deleteListener;
        this.clickListener = clickListener;
        this.checkListener = checkListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chore, parent, false);
        return new ChoreViewHolder(v, clickListener, deleteListener, checkListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChoreViewHolder) holder).bind(choreList.get(position));
    }

    @Override
    public int getItemCount() {
        return choreList.size();
    }

    void setItems(List<Chore> chores) {
        choreList = chores;
        notifyDataSetChanged();
    }
}
