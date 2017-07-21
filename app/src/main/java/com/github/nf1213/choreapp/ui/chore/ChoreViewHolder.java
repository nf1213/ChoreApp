package com.github.nf1213.choreapp.ui.chore;

import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.datastorage.Chore;

/**
 * Created by Nicole Felch on 6/24/17.
 */
class ChoreViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView choreNameView;
    private Button editButton;
    private CheckBox checkBox;
    private ChoreAdapter.DeleteListener deleteListener;
    private ChoreAdapter.ClickListener clickListener;
    private ChoreAdapter.CheckListener checkListener;
    private Chore chore;

    ChoreViewHolder(View itemView, ChoreAdapter.ClickListener clickListener, ChoreAdapter.DeleteListener deleteListener, ChoreAdapter.CheckListener checkListener) {
        super(itemView);
        choreNameView = (TextView) itemView.findViewById(R.id.chore_name);
        editButton = (Button) itemView.findViewById(R.id.chore_edit);
        checkBox = (CheckBox) itemView.findViewById(R.id.chore_checkbox);

        this.deleteListener = deleteListener;
        this.clickListener = clickListener;
        this.checkListener = checkListener;

        choreNameView.setOnLongClickListener(ChoreViewHolder.this);
        editButton.setOnClickListener(this);
        choreNameView.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    void bind(Chore chore) {
        this.chore = chore;
        choreNameView.setText(chore.name);
        if (chore.isChecked) {
            choreNameView.setPaintFlags(choreNameView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            choreNameView.setPaintFlags(choreNameView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(chore.isChecked);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(choreNameView.getContext())
                .setTitle(R.string.delete_item)
                .setPositiveButton(R.string.yes_delete, (dialog1, which) -> deleteListener.delete(chore))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create().show();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chore_edit){
            clickListener.onClick(chore.id);
        } else if (v.getId() == R.id.chore_name) {
            checkBox.toggle();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkListener.onCheckChanged(chore, isChecked);
    }
}
