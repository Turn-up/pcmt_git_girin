package com.pcm.pcmmanager.common.notice.event;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.NoticeList;

/**
 * Created by LG on 2016-06-01.
 */
public class NoticeEventViewHolder extends RecyclerView.ViewHolder{

    ImageView image;
    TextView title, date;

    public NoticeEventViewHolder(View itemView) {
        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.notice_event_image);
        title = (TextView)itemView.findViewById(R.id.notice_event_title);
        date = (TextView)itemView.findViewById(R.id.notice_event_date);

    }
    public void setData(NoticeList item){
        Glide.with(itemView.getContext()).load(item.getAttachUrl()).into(image);
        title.setText(item.getTitle());
        date.setText(item.getRegdate());
    }
}
