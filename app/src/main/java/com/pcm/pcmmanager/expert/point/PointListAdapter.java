package com.pcm.pcmmanager.expert.point;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.PointList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-06-10.
 */
public class PointListAdapter extends RecyclerView.Adapter<PointListViewHolder> {
    List<PointList> items = new ArrayList<>();

    public void clear() {
        items.clear();
    }

    private int totalCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void add(PointList item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<PointList> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public PointListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_point_list, parent, false);
        return new PointListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PointListViewHolder holder, int position) {
        holder.setPoint(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean isMoreData() {
        return totalCount == 0 ? false : (totalCount) > items.size() ? true : false;
    }

    public int getLastSn(int sn) {
        return items.get(sn).getMileagelogsn();
    }
}
