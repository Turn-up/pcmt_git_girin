package com.pcm.pcmmanager.common.notice.text;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.NoticeList;

/**
 * Created by LG on 2016-05-25.
 */
public class NoticeListViewHolder extends RecyclerView.ViewHolder {

    NoticeList noticeList;
    TextView notice_list_title, notice_list_date;

    public interface OnItemClickListener {
        public void OnItemClick(View view, NoticeList noticeList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public NoticeListViewHolder(View itemView) {
        super(itemView);
        notice_list_date = (TextView) itemView.findViewById(R.id.notice_list_date);
        notice_list_title = (TextView) itemView.findViewById(R.id.notice_list_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, noticeList);
                }
            }
        });
    }

    public void setNoticeList(NoticeList item) {
        noticeList = item;
        notice_list_title.setText(noticeList.getTitle());
        notice_list_date.setText(noticeList.getRegdate());
    }
}
