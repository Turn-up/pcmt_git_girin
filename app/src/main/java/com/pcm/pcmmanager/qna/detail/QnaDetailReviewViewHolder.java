package com.pcm.pcmmanager.qna.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaDetailReviewList;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailReviewViewHolder extends RecyclerView.ViewHolder {
    TextView name, mainContent, officeName, career,comment;
    ImageView photo;

    QnaDetailReviewList mList;

    public interface OnItemClickListener {
        public void onItemClick(View view, QnaDetailReviewList mList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public QnaDetailReviewViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.qna_detail_expert_name);
        mainContent = (TextView) itemView.findViewById(R.id.qna_detail_expert_content);
        officeName = (TextView)itemView.findViewById(R.id.qna_detail_expert_office);
        career = (TextView)itemView.findViewById(R.id.qna_detail_expert_career);
        comment = (TextView)itemView.findViewById(R.id.qna_detail_expert_comment);
        photo = (ImageView)itemView.findViewById(R.id.qna_detail_expert_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, mList);
                }
            }
        });
    }
    public void setReviewData(QnaDetailReviewList qnaDetailReviewList){
        Glide.with(photo.getContext()).load(qnaDetailReviewList.getPhoto()).into(photo);
        name.setText(qnaDetailReviewList.getExpertname());
        mainContent.setText(qnaDetailReviewList.getMainintroduce());
        officeName.setText(qnaDetailReviewList.getOfficename());
        career.setText(qnaDetailReviewList.getAge()+"세, 경력 "+qnaDetailReviewList.getCareer()+"년");
        comment.setText(qnaDetailReviewList.getContent());
    }
}
