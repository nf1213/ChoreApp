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

    private List<ApplianceChore> choreList;

    ApplianceChoreAdapter() {
        this.choreList = new ArrayList<>();
    }

    public void setData(List<ApplianceChore> data) {
        this.choreList = data;
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
        ((ApplianceChoreViewHolder) holder).bind(choreList.get(position));
    }

    @Override
    public int getItemCount() {
        return choreList.size();
    }

    private class ApplianceChoreViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView frequencyView;

        ApplianceChoreViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.appliance_chore_name);
            frequencyView = (TextView) itemView.findViewById(R.id.appliance_chore_frequency);
        }

        void bind(ApplianceChore chore) {
            nameView.setText(chore.name);
            frequencyView.setText(Integer.toString(chore.frequency));
        }
    }
}
