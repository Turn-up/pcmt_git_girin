package com.pcm.pcmmanager.qna.detail;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaDetail;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NAME = 1;
    public static final int VIEW_TYPE_CONTENT = 2;
    public static final int VIEW_TYPE_REVIEW = 3;
    QnaDetail item;
    String qnasn, myList;

    QnaDetailReviewViewHolder.OnItemClickListener mListener;

    public void setOnItemClickLitener(QnaDetailReviewViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public void setQnaDetailData(QnaDetail qnaDetail, String qnasn) {
        item = qnaDetail;
        this.qnasn = qnasn;
        notifyDataSetChanged();
    }

    public void setQnaDetailData(QnaDetail qnaDetail, String qnasn, String myList) {
        item = qnaDetail;
        this.qnasn = qnasn;
        this.myList = myList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_NAME;
        } else if (position == 1) {
            return VIEW_TYPE_CONTENT;
        } else {
            return VIEW_TYPE_REVIEW;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NAME: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_qna_detail_name, parent, false);
                return new QnaDetailNameViewHolder(view);
            }
            case VIEW_TYPE_CONTENT: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_qna_detail_content, parent, false);
                return new QnaDetailContentViewHolder(view);
            }
            case VIEW_TYPE_REVIEW: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_qna_detail_review, parent, false);
                return new QnaDetailReviewViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            QnaDetailNameViewHolder h = (QnaDetailNameViewHolder) holder;
            h.setNameData(item);
            return;
        } else if (position == 1) {
            QnaDetailContentViewHolder h = (QnaDetailContentViewHolder) holder;
            if (TextUtils.isEmpty(myList))
                h.setContentData(item);
            else
                h.setContentData(item, qnasn, myList);
            return;
        } else {
            QnaDetailReviewViewHolder h = (QnaDetailReviewViewHolder) holder;
            h.setReviewData(item.getQnaDetailReviewList().get(position - 2), qnasn);
            h.setOnItemClickListener(mListener);
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (item == null) return 0;
        int size = 2;
        if (item.getQnaDetailReviewList().size() > 0) {
            size += item.getQnaDetailReviewList().size();
        }
        return size;
    }
}
