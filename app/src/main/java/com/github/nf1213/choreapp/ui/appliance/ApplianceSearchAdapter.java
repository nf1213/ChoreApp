package com.github.nf1213.choreapp.ui.appliance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.networking.ApplianceSearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicole Felch on 7/19/17.
 */
class ApplianceSearchAdapter extends RecyclerView.Adapter {
    interface OnItemClickListener {
        void onItemClicked(ApplianceSearchResult data);
    }

    private List<ApplianceSearchResult> searchResults;
    private OnItemClickListener clickListener;

    ApplianceSearchAdapter(OnItemClickListener clickListener) {
        searchResults = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_appliance_search, parent, false);
        return new ApplianceViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ApplianceViewHolder) holder).bind(searchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void setData(List<ApplianceSearchResult> data) {
        searchResults = data;
        notifyDataSetChanged();
    }

    private class ApplianceViewHolder extends RecyclerView.ViewHolder {
        private ApplianceSearchResult model;
        private TextView applianceNameView;

        ApplianceViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            applianceNameView = (TextView) itemView;
            applianceNameView.setOnClickListener(v -> listener.onItemClicked(model));
        }

        void bind(ApplianceSearchResult model) {
            this.model = model;
            applianceNameView.setText(model.name);
        }
    }
}
