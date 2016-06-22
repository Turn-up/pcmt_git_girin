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
public class BidIngAdapter extends RecyclerView.Adapter<BidIngViewHolder> {

    List<ExpertBidStatus> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(ExpertBidStatus bidIngList) {
        items.add(bidIngList);
        notifyDataSetChanged();
    }
    public void addAll(List<ExpertBidStatus> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    BidIngViewHolder.OnItemClickListener mListener;
    public void setOnItmeClickListener(BidIngViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public BidIngViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_bid_ing_list, null);
        return new BidIngViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BidIngViewHolder holder, int position) {
        holder.setBidIngList(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
