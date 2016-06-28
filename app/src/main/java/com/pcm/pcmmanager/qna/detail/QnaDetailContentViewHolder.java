package com.pcm.pcmmanager.qna.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaDetail;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailContentViewHolder extends RecyclerView.ViewHolder {
    TextView title, content;

    public QnaDetailContentViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.qna_detail_title);
        content = (TextView)itemView.findViewById(R.id.qna_detail_content);
    }
    public void setContentData(QnaDetail qnaDetail){
        title.setText(qnaDetail.getTitle());
        content.setText(qnaDetail.getContent());
    }
}
