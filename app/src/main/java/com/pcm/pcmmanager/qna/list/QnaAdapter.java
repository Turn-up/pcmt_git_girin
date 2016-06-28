package com.pcm.pcmmanager.qna.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-06-23.
 */
public class QnaAdapter extends RecyclerView.Adapter<QnaViewHolder>{
    List<QnaList> list = new ArrayList<>();
    private int totalCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void add(QnaList item){
        list.add(item);
        notifyDataSetChanged();
    }
    public void addAll(List<QnaList> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    QnaViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(QnaViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public QnaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_qna_list,parent,false);
        return new QnaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QnaViewHolder holder, int position) {
        holder.setQnaListData(list.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean isMoreData(){
        return totalCount == 0 ? false : totalCount>list.size()? true :false;
    }
    public int getLastSn(int sn){
        return list.get(sn).getQnasn();
    }
}
