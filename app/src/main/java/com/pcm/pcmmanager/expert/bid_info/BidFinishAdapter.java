package com.pcm.pcmmanager.expert.bid_info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-22.
 */
public class BidFinishAdapter extends RecyclerView.Adapter<BidFinishViewHolder> {

    List<ExpertBidStatus> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(ExpertBidStatus expertBidStatus) {
        items.add(expertBidStatus);
        notifyDataSetChanged();
    }
    public void addAll(List<ExpertBidStatus> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public BidFinishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_bid_finish_list, null);
        return new BidFinishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BidFinishViewHolder holder, int position) {
        holder.setBidFinishList(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
