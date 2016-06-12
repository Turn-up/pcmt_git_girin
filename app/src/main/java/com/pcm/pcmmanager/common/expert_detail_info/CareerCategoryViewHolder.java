package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-24.
 */
public class CareerCategoryViewHolder extends RecyclerView.ViewHolder {

    ImageView category_image1, category_image2, category_image3;
    TextView category_text1, category_text2, category_text3;
    List<ExpertDetailCategory> expertDetailCategories = new ArrayList<>();

    public CareerCategoryViewHolder(View itemView) {
        super(itemView);

        category_image1 = (ImageView) itemView.findViewById(R.id.expert_career_category_image1);
        category_image2 = (ImageView) itemView.findViewById(R.id.expert_career_category_image2);
        category_image3 = (ImageView) itemView.findViewById(R.id.expert_career_category_image3);

        category_text1 = (TextView) itemView.findViewById(R.id.expert_career_category_text1);
        category_text2 = (TextView) itemView.findViewById(R.id.expert_career_category_text2);
        category_text3 = (TextView) itemView.findViewById(R.id.expert_career_category_text3);
    }

    public void setCategory(List<ExpertDetailCategory> item) {
        if (item.get(0).equals("기장")) category_image1.setImageResource(R.drawable.entry_icon);
        else if (item.get(0).equals("TAX")) category_image1.setImageResource(R.drawable.tax_icon);
        else if (item.get(0).equals("기타")) category_image1.setImageResource(R.drawable.etc_icon);

        if (item.get(1).equals("기장")) category_image1.setImageResource(R.drawable.entry_icon);
        else if (item.get(1).equals("TAX")) category_image1.setImageResource(R.drawable.tax_icon);
        else if (item.get(1).equals("기타")) category_image1.setImageResource(R.drawable.etc_icon);

        if (item.get(2).equals("기장")) category_image1.setImageResource(R.drawable.entry_icon);
        else if (item.get(2).equals("TAX")) category_image1.setImageResource(R.drawable.tax_icon);
        else if (item.get(2).equals("기타")) category_image1.setImageResource(R.drawable.etc_icon);

        category_text1.setText(item.get(0).getMarketType());
        category_text2.setText(item.get(1).getMarketType());
        category_text3.setText(item.get(2).getMarketType());
    }
}
