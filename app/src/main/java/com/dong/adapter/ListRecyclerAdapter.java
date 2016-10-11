package com.dong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dong.adapterdelegate.AdapterDelegatesManager;

import java.util.List;

/**
 * Created by zengwendong on 16/8/3.
 */
public class ListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AdapterDelegatesManager<List<Object>> adapterDelegatesManager = new AdapterDelegatesManager<>();

    private List<Object> activeDealsEntities;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegatesManager.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapterDelegatesManager.onBindViewHolder(activeDealsEntities,position,holder);
    }

    @Override
    public int getItemCount() {
        return activeDealsEntities.size();
    }

}
