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

    private List<ApplianceSearchResult> mData;
    private OnItemClickListener mItemClickListener;

    ApplianceSearchAdapter(OnItemClickListener onItemClickListener) {
        mData = new ArrayList<>();
        mItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_appliance_search, parent, false);
        return new ApplianceViewHolder(v, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ApplianceViewHolder) holder).bind(mData.get(position));
    }

    public void setData(List<ApplianceSearchResult> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ApplianceViewHolder extends RecyclerView.ViewHolder {
        private ApplianceSearchResult mModel;
        private TextView mTextView;
        private OnItemClickListener mListener;

        ApplianceViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mTextView = (TextView) itemView;
            mListener = listener;
            mTextView.setOnClickListener(v -> mListener.onItemClicked(mModel));
        }

        void bind(ApplianceSearchResult model) {
            mModel = model;
            mTextView.setText(mModel.name);
        }
    }
}
