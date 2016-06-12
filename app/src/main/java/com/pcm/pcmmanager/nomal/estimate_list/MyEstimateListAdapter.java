package com.pcm.pcmmanager.nomal.estimate_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.MyEsitmateList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-18.
 */
public class MyEstimateListAdapter extends RecyclerView.Adapter<MyEstimateListViewHolder> {

    List<MyEsitmateList> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(MyEsitmateList myEsitmateList){
        items.add(myEsitmateList);
        notifyDataSetChanged();
    }
    public void addAll(List<MyEsitmateList> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    MyEstimateListViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MyEstimateListViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public MyEstimateListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_nomal_estimate_list,null);
        return new MyEstimateListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyEstimateListViewHolder holder, int position) {
        holder.setMyEsitmateList(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
