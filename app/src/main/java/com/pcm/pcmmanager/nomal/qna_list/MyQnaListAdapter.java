package com.pcm.pcmmanager.nomal.qna_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaMyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-06-30.
 */
public class MyQnaListAdapter extends RecyclerView.Adapter<MyQnaListViewHolder> {
    List<QnaMyList> list = new ArrayList<>();
    private int totalCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void add(QnaMyList item){
        list.add(item);
        notifyDataSetChanged();
    }
    public void addAll(List<QnaMyList> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    MyQnaListViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MyQnaListViewHolder.OnItemClickListener listener){
        mListener = listener;
    }
    @Override
    public MyQnaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_qna_my_list,parent,false);
        return new MyQnaListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyQnaListViewHolder holder, int position) {
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
