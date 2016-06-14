package com.pcm.pcmmanager.expert.auction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-22.
 */
public class AuctionAdapter extends RecyclerView.Adapter<AuctionViewHolder> {

    List<ExpertEstimateList> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }
    private int totalCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void add(ExpertEstimateList expertEstimateList) {
        items.add(expertEstimateList);
        notifyDataSetChanged();
    }

    public void addAll(List<ExpertEstimateList> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    AuctionViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(AuctionViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public AuctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_estimate_list, parent,false);
        return new AuctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionViewHolder holder, int position) {
        holder.setAuctionList(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean isMoreData(){
        return totalCount == 0? false : (totalCount)>items.size() ? true :false;
    }

    public int getLastSn(int sn) {
        return items.get(sn).getMarketSn();
    }
}
