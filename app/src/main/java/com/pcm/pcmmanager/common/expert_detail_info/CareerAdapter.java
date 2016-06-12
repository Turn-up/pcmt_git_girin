package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailInfo;

/**
 * Created by LG on 2016-05-24.
 */
public class CareerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_CATEGORY = 2;
    public static final int VIEW_TYPE_RECORD = 3;
    public static final int VIEW_TYPE_ACADEMIC = 4;
    ExpertDetailInfo expertDetailInfo;

    public void setexpertDetailInfo(ExpertDetailInfo result) {
        expertDetailInfo = result;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        position--;
        if (position == 0) {
            return VIEW_TYPE_CATEGORY;
        }
        position--;
        if (position == 0) {
            return VIEW_TYPE_RECORD;
        }
        position--;
        if (position == 0) {
            return VIEW_TYPE_ACADEMIC;
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_career_hearder, parent, false);
                return new CareerHeaderViewHolder(view);
            }
            case VIEW_TYPE_CATEGORY: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_career_category, parent, false);
                return new CareerCategoryViewHolder(view);
            }
            case VIEW_TYPE_RECORD: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_career_record_list, parent, false);
                return new CareerRecordListViewHolder(view);
            }
            case VIEW_TYPE_ACADEMIC: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert_career_academic, parent, false);
                return new CareerAcademicViewHolder(view);

            }

        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            CareerHeaderViewHolder h = (CareerHeaderViewHolder) holder;
            h.setHeader(expertDetailInfo);
            return;
        }
        position--;
        if (position == 0) {
            CareerCategoryViewHolder h = (CareerCategoryViewHolder) holder;
            h.setCategory(expertDetailInfo.getCategory());
            return;
        }
        position--;
        if (position == 0) {
            CareerRecordListViewHolder h = (CareerRecordListViewHolder) holder;
            h.setRecordList(expertDetailInfo.getRecord());
            return;
        }
        position--;
        if (position == 0) {
            CareerAcademicViewHolder h = (CareerAcademicViewHolder) holder;
            h.setList(expertDetailInfo.getAcademic());
            return;
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        if (expertDetailInfo == null) return 0;
        int size = 1;
        if (expertDetailInfo.getCategory().size() > 0) {
            size += 1;
        }
        if (expertDetailInfo.getRecord().size() > 0) {
            size += 1;
        }
        if (expertDetailInfo.getAcademic().size() > 0) {
            size += 1;
        }
        return size;
    }
}
