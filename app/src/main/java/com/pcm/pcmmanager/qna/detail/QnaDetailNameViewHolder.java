package com.pcm.pcmmanager.qna.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaDetail;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailNameViewHolder extends RecyclerView.ViewHolder {
    TextView name, regdate;
    public QnaDetailNameViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.qna_detail_user_name);
        regdate = (TextView)itemView.findViewById(R.id.qna_detail_regdate);
    }

    public void setNameData(QnaDetail qnaDetail){
        name.setText(qnaDetail.getUsername());
        regdate.setText(qnaDetail.getRegdate());
    }
}
