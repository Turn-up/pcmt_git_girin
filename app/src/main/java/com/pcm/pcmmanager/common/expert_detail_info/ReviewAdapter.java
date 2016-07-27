package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailReview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-23.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    List<ExpertDetailReview> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    public void add(ExpertDetailReview ExpertDetailReview){
        items.add(ExpertDetailReview);
        notifyDataSetChanged();
    }
    public void addAll(List<ExpertDetailReview> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_detail_review,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.setExpertDetailReview(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
