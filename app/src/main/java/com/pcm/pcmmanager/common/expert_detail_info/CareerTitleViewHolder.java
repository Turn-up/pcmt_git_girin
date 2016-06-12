package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by LG on 2016-05-24.
 */
public class CareerTitleViewHolder extends RecyclerView.ViewHolder {
    
    TextView title;
    
    public CareerTitleViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView;
    }
    
    public void setTitle(String title){
        this.title.setText(title);
    }
}
