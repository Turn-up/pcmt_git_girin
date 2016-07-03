package com.pcm.pcmmanager.nomal.qna_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaMyList;

/**
 * Created by LG on 2016-06-30.
 */
public class MyQnaListViewHolder extends RecyclerView.ViewHolder {
    TextView title, regdate, writer, content,reviewCount;
    ImageView lock_icon;
    QnaMyList qnaMyList;
    public interface OnItemClickListener {
        public void OnItemClick(View view, QnaMyList qnaMyList);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyQnaListViewHolder(View itemView) {
        super(itemView);  title = (TextView)itemView.findViewById(R.id.my_qna_list_title);
        regdate = (TextView)itemView.findViewById(R.id.my_qna_list_regdate);
        writer = (TextView)itemView.findViewById(R.id.my_qna_list_writer);
        content = (TextView)itemView.findViewById(R.id.my_qna_list_content);
        reviewCount = (TextView)itemView.findViewById(R.id.my_qna_list_review_count);
        lock_icon = (ImageView)itemView.findViewById(R.id.lock_icon);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.OnItemClick(v,qnaMyList);
                }
            }
        });
    }

    public void setQnaListData(QnaMyList qnaMyList){
        this.qnaMyList = qnaMyList;
        title.setText(qnaMyList.getTitle());
        //비밀글 여부
        if(qnaMyList.getSecretyn()){
            lock_icon.setVisibility(View.VISIBLE);
            content.setText("비밀글 입니다.");
        }else{
            lock_icon.setVisibility(View.INVISIBLE);
            content.setText(qnaMyList.getContent());
        }
        regdate.setText(qnaMyList.getRegdate());
        writer.setText(qnaMyList.getUsername());
        reviewCount.setText(""+qnaMyList.getCommentcount());
    }
}