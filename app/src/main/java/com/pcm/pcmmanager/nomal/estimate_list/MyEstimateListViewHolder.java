package com.pcm.pcmmanager.nomal.estimate_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.MyEsitmateList;

/**
 * Created by LG on 2016-05-18.
 */
public class MyEstimateListViewHolder extends RecyclerView.ViewHolder {

    MyEsitmateList myEsitmateList;
    ImageView marketType_image,bids_count_image;
    TextView marketType_title, marketType_sub, endDate, bidCount;


    public interface OnItemClickListener {
        public void OnItemClick(View view, MyEsitmateList myEsitmateList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEstimateListViewHolder(final View itemView) {
        super(itemView);
        bids_count_image = (ImageView)itemView.findViewById(R.id.bid_count_image);
        marketType_image = (ImageView) itemView.findViewById(R.id.market_type_image);
        marketType_title = (TextView) itemView.findViewById(R.id.market_type_title);
        marketType_sub = (TextView) itemView.findViewById(R.id.market_type_sub);
        endDate = (TextView) itemView.findViewById(R.id.end_date);
        bidCount = (TextView) itemView.findViewById(R.id.bid_count);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, myEsitmateList);
                }
            }
        });

    }

    public void setMyEsitmateList(MyEsitmateList myEsitmateList) {
        this.myEsitmateList = myEsitmateList;

        long enddate = Long.valueOf(this.myEsitmateList.getEndDate()) - (this.myEsitmateList.getRegDate() / (24 * 60 * 60 * 1000));

        if (enddate > 0) {
            endDate.setText("D-" + enddate);
        } else if (enddate == 0) {
            enddate = 24 - (this.myEsitmateList.getRegDate() % (24 * 60 * 60 * 1000))/(1 * 60 * 60 * 1000);
            endDate.setText(enddate + "시간");
            if(enddate<=1){
                endDate.setText("마감 임박");
            }
        } else if (enddate < 0) {
            enddate = 0;
            endDate.setText("종료");
        }

        if (myEsitmateList.getMarketType().equals("기장")) {//기장
            marketType_image.setImageResource(R.drawable.entry_icon);
            marketType_title.setText(this.myEsitmateList.getMarketSubType());
            marketType_sub.setText("매출 " + this.myEsitmateList.getBusinessScale() + ", 종업원 " + this.myEsitmateList.getEmployeeCount() + "명");
        } else if (myEsitmateList.getMarketType().equals("TAX")) {//기타
            marketType_image.setImageResource(R.drawable.tax_icon);
            marketType_title.setText(this.myEsitmateList.getMarketSubType());
            String temp = ", 자산 내용 ";
            for (int i = 0; i < this.myEsitmateList.getAssetType().size(); i++) {
                temp += this.myEsitmateList.getAssetType().get(i) + " ";
            }
            marketType_sub.setText("자산 " + this.myEsitmateList.getMarketPrice() + temp);
        } else {//기타
            marketType_image.setImageResource(R.drawable.etc_icon);
            marketType_title.setText("기타");
            marketType_sub.setText("상세 페이지를 확인하세요");
        }
        bidCount.setText("" + this.myEsitmateList.getOldCnt());
        if(this.myEsitmateList.getStatus().equals("낙찰완료")){
            bidCount.setText("낙찰완료");
            bids_count_image.setVisibility(View.GONE);
        } else if(this.myEsitmateList.getStatus().equals("입찰전")){
            bidCount.setText("견적 확인중");
            bids_count_image.setVisibility(View.GONE);
        }
    }
}
