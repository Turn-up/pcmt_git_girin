package com.pcm.pcmmanager.common.notice.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.NoticeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-06-01.
 */
public class NoticeEventAdapter extends RecyclerView.Adapter<NoticeEventViewHolder>{

    List<NoticeList> items = new ArrayList<>();
    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    public void add(NoticeList item){
        items.add(item);
        notifyDataSetChanged();
    }
    public void addAll(List<NoticeList> list){
        items.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public NoticeEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notice_event_image,parent, false);
        return new NoticeEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeEventViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
