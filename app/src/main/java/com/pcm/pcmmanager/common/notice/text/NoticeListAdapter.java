package com.pcm.pcmmanager.common.notice.text;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.NoticeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-25.
 */
public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListViewHolder> {

    List<NoticeList> item = new ArrayList<>();

    public void clear(){
        item.clear();
        notifyDataSetChanged();
    }
    public void add(NoticeList noticeList){
        item.add(noticeList);
        notifyDataSetChanged();
    }
    public void addAll(List<NoticeList> noticeList){
        item.addAll(noticeList);
        notifyDataSetChanged();
    }
    NoticeListViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(NoticeListViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public NoticeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_common_notice_list,parent,false);
        return new NoticeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeListViewHolder holder, int position) {
        holder.setNoticeList(item.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
