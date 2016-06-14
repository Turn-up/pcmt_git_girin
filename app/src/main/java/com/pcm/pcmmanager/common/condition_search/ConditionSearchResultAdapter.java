package com.pcm.pcmmanager.common.condition_search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ConditionSearchList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-25.
 */
public class ConditionSearchResultAdapter extends RecyclerView.Adapter<ConditionSearchResultViewHolder> {

    List<ConditionSearchList> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }
    private int totalCount = 0;

    public int getTotal() {
        return totalCount;
    }

    public void setTotal(int total) {
        this.totalCount = total;
    }

    public void add(ConditionSearchList conditionSearchList) {
        items.add(conditionSearchList);
        notifyDataSetChanged();
    }

    public void addAll(List<ConditionSearchList> conditionSearchLists) {
        items.addAll(conditionSearchLists);
        notifyDataSetChanged();
    }

    ConditionSearchResultViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(ConditionSearchResultViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ConditionSearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_condition_search_list,parent,false);
        return new ConditionSearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConditionSearchResultViewHolder holder, int position) {
        holder.setconditionSearchList(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean isMoreData() {
        return totalCount == 0 ? false : (totalCount) > items.size() ? true : false;
    }

    public int getLastSn(int sn) {
        return items.get(sn).getExpertSn();
    }
}
