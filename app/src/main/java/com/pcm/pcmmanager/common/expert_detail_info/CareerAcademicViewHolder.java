package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailAcademic;

import java.util.List;

/**
 * Created by LG on 2016-05-24.
 */
public class CareerAcademicViewHolder extends RecyclerView.ViewHolder {

    TextView year_front1, year_back1, content1;
    TextView year_front2, year_back2, content2;
    TextView year_front3, year_back3, content3;
    LinearLayout academic_layout_one, academic_layout_two, academic_layout_three;
    
    List<ExpertDetailAcademic> list;

    public CareerAcademicViewHolder(View itemView) {
        super(itemView);
        year_front1 = (TextView) itemView.findViewById(R.id.expert_career_academic1_year_front);
        year_back1 = (TextView) itemView.findViewById(R.id.expert_career_academic1_year_back);
        content1 = (TextView) itemView.findViewById(R.id.expert_career_academic1_content);

        year_front2 = (TextView) itemView.findViewById(R.id.expert_career_academic2_year_front);
        year_back2 = (TextView) itemView.findViewById(R.id.expert_career_academic2_year_back);
        content2 = (TextView) itemView.findViewById(R.id.expert_career_academic2_content);

        year_front3 = (TextView) itemView.findViewById(R.id.expert_career_academic3_year_front);
        year_back3 = (TextView) itemView.findViewById(R.id.expert_career_academic3_year_back);
        content3 = (TextView) itemView.findViewById(R.id.expert_career_academic3_content);

        academic_layout_one = (LinearLayout) itemView.findViewById(R.id.academic_layout1);
        academic_layout_two = (LinearLayout) itemView.findViewById(R.id.academic_layout2);
        academic_layout_three = (LinearLayout) itemView.findViewById(R.id.academic_layout3);
    }

    public void setList(List<ExpertDetailAcademic> expertDetailAcademic) {
        list = expertDetailAcademic;
        if (list.size() == 1) {
            year_front1.setText("" + list.get(0).getStartDate());
            year_back1.setText("" + list.get(0).getEndDate());
            content1.setText(list.get(0).getAcademyName());
            academic_layout_two.setVisibility(View.GONE);
            academic_layout_three.setVisibility(View.GONE);
        } else if (list.size() == 2) {
            year_front1.setText("" + list.get(0).getStartDate());
            year_back1.setText("" + list.get(0).getEndDate());
            content1.setText(list.get(0).getAcademyName());
            year_front2.setText("" + list.get(1).getStartDate());
            year_back2.setText("" + list.get(1).getEndDate());
            content2.setText(list.get(1).getAcademyName());
            academic_layout_three.setVisibility(View.GONE);
        } else if (list.size() == 3) {
            year_front1.setText("" + list.get(0).getStartDate());
            year_back1.setText("" + list.get(0).getEndDate());
            content1.setText(list.get(0).getAcademyName());
            year_front2.setText("" + list.get(1).getStartDate());
            year_back2.setText("" + list.get(1).getEndDate());
            content2.setText(list.get(1).getAcademyName());
            year_front3.setText("" + list.get(2).getStartDate());
            year_back3.setText("" + list.get(2).getEndDate());
            content3.setText(list.get(2).getAcademyName());
        }
    }
}
