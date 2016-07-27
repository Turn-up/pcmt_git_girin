package com.pcm.pcmmanager.common.faq;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.FaqList;

/**
 * Created by LG on 2016-07-26.
 */
public class FaqViewHolder extends RecyclerView.ViewHolder {

    TextView title, content;
    FaqList list;

    public interface OnItemClickListener {
        public void OnItemClick(View view, FaqList list);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public FaqViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.faq_title_text);
        content = (TextView) itemView.findViewById(R.id.faq_content_text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, list);
                }
            }
        });

    }

    public void setData(FaqList list) {
        this.list = list;
        title.setText(this.list.getTitle());
        content.setText(this.list.getContent());
    }
}
