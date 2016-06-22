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
public class BidSuccessAdapter extends RecyclerView.Adapter<BidSucessViewHolder> {

    List<ExpertBidStatus> items = new ArrayList<>();


    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(ExpertBidStatus expertBidStatus) {
        items.add(expertBidStatus);
        notifyDataSetChanged();

    }

    public void addAll(List<ExpertBidStatus> expertBidStatuses) {
        items.addAll(expertBidStatuses);
        notifyDataSetChanged();
    }

    BidSucessViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(BidSucessViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public BidSucessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_bid_success_list, null);
        return new BidSucessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BidSucessViewHolder holder, int position) {
        holder.setBidSuccessList(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
