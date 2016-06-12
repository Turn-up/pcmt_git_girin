package com.pcm.pcmmanager.common.condition_search;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ConditionSearchList;

/**
 * Created by LG on 2016-05-25.
 */

public class ConditionSearchResultViewHolder extends RecyclerView.ViewHolder {

    ConditionSearchList conditionSearchList;

    ImageView profileImage;
    TextView profileName, profileComment, profileOffice, profileCareer;

    public interface OnItemClickListener{
        public void OnItemClick(View view,ConditionSearchList conditionSearchList);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){mListener =listener;}
    public ConditionSearchResultViewHolder(View itemView) {
        super(itemView);
        profileImage = (ImageView)itemView.findViewById(R.id.expert_condition_search_image);
        profileName = (TextView)itemView.findViewById(R.id.expert_condition_search_name);
        profileComment = (TextView)itemView.findViewById(R.id.expert_condition_search_comment);
        profileOffice = (TextView)itemView.findViewById(R.id.expert_condition_search_office);
        profileCareer = (TextView)itemView.findViewById(R.id.expert_condition_search_career);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnItemClick(v,conditionSearchList);
                }
            }
        });
    }

    public void setconditionSearchList(ConditionSearchList item){
        conditionSearchList =item;
        if(TextUtils.isEmpty(item.getPhotoUrl())){
            if(item.getSex().equals("남자")){
                profileImage.setImageResource(R.drawable.semooman_icon);
            }else if(item.getSex().equals("여자")){
                profileImage.setImageResource(R.drawable.semoogirl_icon);
            }
        }else {
            Glide.with(profileImage.getContext()).load(item.getPhotoUrl()).into(profileImage);
        }
        profileName.setText(conditionSearchList.getExpertName());
        profileCareer.setText(conditionSearchList.getAge()+"세, 경력"+conditionSearchList.getCareer()+"년");
        profileOffice.setText(conditionSearchList.getOfficeName());
        profileComment.setText(conditionSearchList.getMainIntroduce());
    }
}
