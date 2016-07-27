package com.pcm.pcmmanager.common.faq;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.FaqList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-07-26.
 */
public class FaqAdapter extends RecyclerView.Adapter<FaqViewHolder> {

    List<FaqList> faqLists = new ArrayList<>();

    public void clear() {
        faqLists.clear();
        notifyDataSetChanged();
    }

    private int totalCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void add(FaqList faqLists) {
        this.faqLists.add(faqLists);
        notifyDataSetChanged();
    }

    public void addAll(List<FaqList> faqLists) {
        this.faqLists.addAll(faqLists);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        faqLists.remove(location);
        notifyDataSetChanged();
    }

    FaqViewHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(FaqViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public FaqViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_faq_list, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FaqViewHolder holder, int position) {
        holder.setData(faqLists.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return faqLists.size();
    }

    public boolean isMoreData() {
        return totalCount == 0 ? false : (totalCount) > faqLists.size() ? true : false;
    }

    public int getLastSn(int sn) {
        return faqLists.get(sn).getFaqsn();
    }

}
