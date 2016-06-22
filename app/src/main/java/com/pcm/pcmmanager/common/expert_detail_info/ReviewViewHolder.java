package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailReview;

/**
 * Created by LG on 2016-05-23.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    ExpertDetailReview expertDetailReview;
    TextView expert_detail_reivew_content,expert_detail_review_userName, expert_detail_review_rngDate;
    RatingBar ratingBar;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        expert_detail_reivew_content = (TextView)itemView.findViewById(R.id.expert_detail_review_content);
        expert_detail_review_userName = (TextView)itemView.findViewById(R.id.expert_detail_review_userName);
        expert_detail_review_rngDate = (TextView)itemView.findViewById(R.id.expert_detail_review_endDate);
        ratingBar = (RatingBar)itemView.findViewById(R.id.expert_detail_review_ratingBar);
    }

    public void setExpertDetailReview(ExpertDetailReview expertDetailReview){
        this.expertDetailReview = expertDetailReview;
        expert_detail_reivew_content.setText(this.expertDetailReview.getContent());
        expert_detail_review_userName.setText(this.expertDetailReview.getUserName()+" ");
        expert_detail_review_rngDate.setText(this.expertDetailReview.getRegDate());
        ratingBar.setIsIndicator(true);
        ratingBar.setRating((float)this.expertDetailReview.getScore());
    }
}
