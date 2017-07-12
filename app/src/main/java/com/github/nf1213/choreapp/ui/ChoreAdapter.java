package com.github.nf1213.choreapp.ui;

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

    private List<Chore> mChoreList;
    private DeleteListener mDeleteListener;
    private ClickListener mClickListener;
    private CheckListener mCheckListenter;

    public ChoreAdapter(List<Chore> choreList, ClickListener clickListener, DeleteListener deleteListener, CheckListener checkListenter) {
        mChoreList = choreList;
        mDeleteListener = deleteListener;
        mClickListener = clickListener;
        mCheckListenter = checkListenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chore, parent, false);
        return new ChoreViewHolder(v, mClickListener, mDeleteListener, mCheckListenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChoreViewHolder) holder).bind(mChoreList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChoreList.size();
    }

    public void setItems(List<Chore> chores) {
        mChoreList = chores;
        notifyDataSetChanged();
    }

    public interface DeleteListener {
        void delete(Chore chore);
    }

    public interface ClickListener {
        void onClick(int choreId);
    }

    public interface CheckListener {
        void onCheckChanged(Chore chore, boolean isChecked);
    }
}
