package com.pcm.pcmmanager.nomal.estimate_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;

/**
 * Created by LG on 2016-05-19.
 */
public class MyEstimateDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_BID_LIST = 2;

    ExpertEstimateDetail mItem;

    public void setDetailItem(ExpertEstimateDetail item) {
        mItem = item;
        notifyDataSetChanged();
    }

    MyEstimateDetailBidListsViewHolder.OnItemClickListener mListener;

    public void setOnItemClickLitener(MyEstimateDetailBidListsViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_BID_LIST;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER: {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_nomal_estimate_detail_header, null);
                return new MyEstimateDetailHeaderViewHolder(view1);
            }
            case VIEW_TYPE_BID_LIST: {
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_nomal_estimate_bids_list, parent,false);
                return new MyEstimateDetailBidListsViewHolder(view2);
            }
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            MyEstimateDetailHeaderViewHolder h = (MyEstimateDetailHeaderViewHolder) holder;
            h.setHeader(mItem);
            return;
        }else {
            MyEstimateDetailBidListsViewHolder h = (MyEstimateDetailBidListsViewHolder) holder;
            if(mItem.getSuccess_expertsn() != 0){
                if(position==1) {
                    for (int i = 0; i < mItem.getBids().size(); i++)
                        if (mItem.getBids().get(i).getExpertSn() == mItem.getSuccess_expertsn()) {
                            h.setBidsList(mItem.getBids().get(i), mItem.getMarketType());
                        }
                }else if(position >1){
                    h.setBidsList(mItem.getBids().get(0),"0");
                }
            }else{
                h.setBidsList(mItem.getBids().get(position - 1),mItem.getMarketType());
            }
            h.setOnItemClickListener(mListener);
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (mItem == null) return 0;
        int size = 1;
        if (mItem.getBids().size() > 0) {
            size += mItem.getBids().size();

        }
        return size;
    }
}
