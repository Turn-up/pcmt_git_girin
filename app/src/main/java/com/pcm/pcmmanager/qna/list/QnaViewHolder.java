package com.pcm.pcmmanager.qna.list;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaList;

/**
 * Created by LG on 2016-06-23.
 */
public class QnaViewHolder extends RecyclerView.ViewHolder {

    TextView title, regdate, writer, content,reviewCount;
    QnaList qnaList;

    public interface OnItemClickListener {
        public void OnItemClick(View view, QnaList qnaList);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public QnaViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.qna_list_title);
        regdate = (TextView)itemView.findViewById(R.id.qna_list_regdate);
        writer = (TextView)itemView.findViewById(R.id.qna_list_writer);
        content = (TextView)itemView.findViewById(R.id.qna_list_content);
        reviewCount = (TextView)itemView.findViewById(R.id.qna_list_review_count);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnItemClick(v,qnaList);
                }
            }
        });
    }

    public void setQnaListData(QnaList qnaList){
        this.qnaList = qnaList;
        title.setText(qnaList.getTitle());
        //비밀글 여부
        if(qnaList.getSecretyn()){
            Drawable secretIcon= itemView.getContext().getResources().getDrawable(R.drawable.lock_icon);
            title.setCompoundDrawables(null,null,secretIcon,null);
            title.setCompoundDrawablePadding(5);
        }
        regdate.setText(qnaList.getRegdate());
        writer.setText(qnaList.getUsername());
        content.setText(qnaList.getContent());
        reviewCount.setText(qnaList.getCommentcount());
    }
}
