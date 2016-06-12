package com.pcm.pcmmanager.expert.point;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.PointList;

/**
 * Created by LG on 2016-06-10.
 */
public class PointListViewHolder extends RecyclerView.ViewHolder {

    TextView mileageState, regdate, usePoint;
    PointList pointList;
    public PointListViewHolder(View itemView) {
        super(itemView);
        mileageState = (TextView)itemView.findViewById(R.id.use_point_type);
        regdate = (TextView)itemView.findViewById(R.id.use_regdate);
        usePoint  = (TextView)itemView.findViewById(R.id.use_point);

    }

    public void setPoint(PointList item){
        pointList = item;
        mileageState.setText(pointList.getMileagetype());
        regdate.setText(pointList.getRegdate());
        usePoint.setText(""+pointList.getMileage());
    }
}
