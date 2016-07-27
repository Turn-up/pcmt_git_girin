package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailCategory;
import com.pcm.pcmmanager.utill.Utills;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-24.
 */
public class CareerCategoryViewHolder extends RecyclerView.ViewHolder {

    List<ExpertDetailCategory> expertDetailCategories = new ArrayList<>();
    ImageView category_image[];
    TextView category_text[];

    public CareerCategoryViewHolder(View itemView) {
        super(itemView);

        category_image = new ImageView[3];
        category_text = new TextView[3];

        category_image[0] = (ImageView) itemView.findViewById(R.id.expert_career_category_image1);
        category_image[1] = (ImageView) itemView.findViewById(R.id.expert_career_category_image2);
        category_image[2] = (ImageView) itemView.findViewById(R.id.expert_career_category_image3);

        category_text[0] = (TextView) itemView.findViewById(R.id.expert_career_category_text1);
        category_text[1] = (TextView) itemView.findViewById(R.id.expert_career_category_text2);
        category_text[2] = (TextView) itemView.findViewById(R.id.expert_career_category_text3);
    }

    public void setCategory(List<ExpertDetailCategory> item) {
        for (int i = 0; i < 3; i++) {
            if (item.get(i).getMarketType().equals("기장")) {
               category_image[i].setImageResource(Utills.entryIcon(item.get(i).getMarketSubType()));
            } else if (item.get(i).getMarketType().equals("TAX"))
                category_image[i].setImageResource(R.drawable.tax_icon);
            else if (item.get(i).getMarketType().equals("기타"))
                category_image[i].setImageResource(R.drawable.etc_icon);
            category_text[i].setText(item.get(i).getMarketSubType());
        }
    }
}
