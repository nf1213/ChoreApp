package com.github.nf1213.choreapp;

import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Nicole Felch on 6/24/17.
 */
class ChoreViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView mTextView;
    private CheckBox mCheckBox;
    private ChoreAdapter.DeleteListener mDeleteListener;
    private ChoreAdapter.ClickListener mClickListener;
    private ChoreAdapter.CheckListener mCheckListener;
    private Chore mChore;

    public ChoreViewHolder(View itemView, ChoreAdapter.ClickListener clickListener, ChoreAdapter.DeleteListener deleteListener, ChoreAdapter.CheckListener checkListener) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.name);
        mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        mDeleteListener = deleteListener;
        mClickListener = clickListener;
        mCheckListener = checkListener;
        mTextView.setOnLongClickListener(ChoreViewHolder.this);
        mTextView.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(this);
    }

    public void bind(Chore chore) {
        mChore = chore;
        mTextView.setText(chore.name);
        if (mChore.isChecked) {
            mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            mTextView.setPaintFlags(mTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        mCheckBox.setChecked(mChore.isChecked);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mCheckListener.onCheckChanged(mChore, isChecked);
    }
}
