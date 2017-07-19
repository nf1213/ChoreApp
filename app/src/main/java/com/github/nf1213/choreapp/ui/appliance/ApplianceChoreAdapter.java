package com.github.nf1213.choreapp.ui.appliance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.networking.ApplianceChore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicole Felch on 7/19/17.
 */

class ApplianceChoreAdapter extends RecyclerView.Adapter {

    private List<ApplianceChore> data;

    ApplianceChoreAdapter() {
        this.data = new ArrayList<>();
    }

    public void setData(List<ApplianceChore> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_appliance_chore, parent, false);
        return new ApplianceChoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ApplianceChoreViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ApplianceChoreViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView frequency;

        ApplianceChoreViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            frequency = (TextView) itemView.findViewById(R.id.frequency);
        }

        void bind(ApplianceChore chore) {
            name.setText(chore.name);
            frequency.setText(Integer.toString(chore.frequency));
        }
    }
}
