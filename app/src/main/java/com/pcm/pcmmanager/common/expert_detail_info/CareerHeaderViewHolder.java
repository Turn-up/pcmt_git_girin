package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailInfo;

/**
 * Created by LG on 2016-05-21.
 */
public class CareerHeaderViewHolder extends RecyclerView.ViewHolder{

    TextView expert_detail_career_header_main_content, expert_detail_career_header_sub_content;

    public CareerHeaderViewHolder(View itemView) {
        super(itemView);
        expert_detail_career_header_main_content = (TextView) itemView.findViewById(R.id.expert_career_main_content);
        expert_detail_career_header_sub_content = (TextView) itemView.findViewById(R.id.expert_career_sub_content);
    }

    public void setHeader(ExpertDetailInfo item){
        expert_detail_career_header_main_content.setText(item.getMainIntroduce());
        expert_detail_career_header_sub_content.setText(item.getDetailIntroduce());

    }
}
