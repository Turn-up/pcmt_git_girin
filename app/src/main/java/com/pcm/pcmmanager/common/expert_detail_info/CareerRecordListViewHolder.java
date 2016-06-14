package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailRecord;

import java.util.List;

/**
 * Created by LG on 2016-05-24.
 */
public class CareerRecordListViewHolder extends RecyclerView.ViewHolder {
    TextView year_front1, year_back1, content1;
    TextView year_front2, year_back2, content2;
    TextView year_front3, year_back3, content3;
    LinearLayout record_layout_one, record_layout_two, record_layout_three;
    List<ExpertDetailRecord> list;

    public CareerRecordListViewHolder(View itemView) {
        super(itemView);
        year_front1 = (TextView) itemView.findViewById(R.id.expert_career_record1_year_front);
        year_back1 = (TextView) itemView.findViewById(R.id.expert_career_record1_year_back);
        content1 = (TextView) itemView.findViewById(R.id.expert_career_record1_content);

        year_front2 = (TextView) itemView.findViewById(R.id.expert_career_record2_year_front);
        year_back2 = (TextView) itemView.findViewById(R.id.expert_career_record2_year_back);
        content2 = (TextView) itemView.findViewById(R.id.expert_career_record2_content);

        year_front3 = (TextView) itemView.findViewById(R.id.expert_career_record3_year_front);
        year_back3 = (TextView) itemView.findViewById(R.id.expert_career_record3_year_back);
        content3 = (TextView) itemView.findViewById(R.id.expert_career_record3_content);

        record_layout_one = (LinearLayout) itemView.findViewById(R.id.record_layout1);
        record_layout_two = (LinearLayout) itemView.findViewById(R.id.record_layout2);
        record_layout_three = (LinearLayout) itemView.findViewById(R.id.record_layout3);
    }

    public void setRecordList(List<ExpertDetailRecord> items) {
        list = items;
        if (list.size() == 1) {
            year_front1.setText("" + list.get(0).getStartDate());
            year_back1.setText("" + list.get(0).getEndDate());
            content1.setText(list.get(0).getRecordName());
            record_layout_two.setVisibility(View.GONE);
            record_layout_three.setVisibility(View.GONE);

        } else if (list.size() == 2) {
            year_front1.setText("" + list.get(0).getStartDate());
            year_back1.setText("" + list.get(0).getEndDate());
            content1.setText(list.get(0).getRecordName());
            year_front2.setText("" + list.get(1).getStartDate());
            year_back2.setText("" + list.get(1).getEndDate());
            content2.setText(list.get(1).getRecordName());
            record_layout_three.setVisibility(View.GONE);

        } else if (list.size() > 2) {
            year_front1.setText("" + list.get(0).getStartDate());
            year_back1.setText("" + list.get(0).getEndDate());
            content1.setText(list.get(0).getRecordName());
            year_front2.setText("" + list.get(1).getStartDate());
            year_back2.setText("" + list.get(1).getEndDate());
            content2.setText(list.get(1).getRecordName());
            year_front3.setText("" + list.get(2).getStartDate());
            year_back3.setText("" + list.get(2).getEndDate());
            content3.setText(list.get(2).getRecordName());
        }
    }
}

