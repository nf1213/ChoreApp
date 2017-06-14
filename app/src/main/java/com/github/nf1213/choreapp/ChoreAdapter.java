package com.github.nf1213.choreapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nicole Felch on 6/8/17.
 */
class ChoreAdapter extends RecyclerView.Adapter {

    private List<Chore> mChoreList;
    private DeleteListener mDeleteListener;
    private ClickListener mClickListener;

    public ChoreAdapter(List<Chore> choreList, ClickListener clickListener, DeleteListener deleteListener) {
        mChoreList = choreList;
        mDeleteListener = deleteListener;
        mClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chore, parent, false);
        return new ChoreViewHolder(v, mClickListener, mDeleteListener);
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

    private class ChoreViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView mTextView;
        private DeleteListener mDeleteListener;
        ClickListener mClickListener;
        private Chore mChore;

        public ChoreViewHolder(View itemView, ClickListener clickListener, DeleteListener deleteListener) {
            super(itemView);
            mTextView = (TextView) itemView;
            mDeleteListener = deleteListener;
            mClickListener = clickListener;
            mTextView.setOnLongClickListener(ChoreViewHolder.this);
            mTextView.setOnClickListener(this);
        }

        public void bind(Chore chore) {
            mChore = chore;
            mTextView.setText(chore.name);
        }

        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(mTextView.getContext())
                    .setTitle(R.string.delete_item)
                    .setPositiveButton(R.string.yes_delete, (dialog1, which) -> {
                        mDeleteListener.delete(mChore);
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create().show();

            return true;
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClick(mChore.id);
        }
    }

    public interface DeleteListener {
        void delete(Chore chore);
    }

    public interface ClickListener {
        void onClick(int choreId);
    }
}
