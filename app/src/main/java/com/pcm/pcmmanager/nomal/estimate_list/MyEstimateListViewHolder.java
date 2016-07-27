package com.pcm.pcmmanager.nomal.estimate_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.MyEsitmateList;
import com.pcm.pcmmanager.utill.Utills;

/**
 * Created by LG on 2016-05-18.
 */
public class MyEstimateListViewHolder extends RecyclerView.ViewHolder {

    MyEsitmateList myEsitmateList;
    ImageView marketType_image, bids_count_image;
    TextView marketType_title, endDate, bidCount;
    TextView[] marketType_sub;

    public interface OnItemClickListener {
        public void OnItemClick(View view, MyEsitmateList myEsitmateList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEstimateListViewHolder(final View itemView) {
        super(itemView);
        bids_count_image = (ImageView) itemView.findViewById(R.id.nomal_estimate_bid_count_image);
        marketType_image = (ImageView) itemView.findViewById(R.id.nomal_estimate_market_type_image);
        marketType_title = (TextView) itemView.findViewById(R.id.nomal_estimate_market_type);

        marketType_sub = new TextView[4];
        marketType_sub[0] = (TextView) itemView.findViewById(R.id.nomal_estimate_market_type_sub1);
        marketType_sub[1] = (TextView) itemView.findViewById(R.id.nomal_estimate_market_type_sub2);
        marketType_sub[2] = (TextView) itemView.findViewById(R.id.nomal_estimate_market_type_sub3);
        marketType_sub[3] = (TextView) itemView.findViewById(R.id.nomal_estimate_market_type_sub4);

        endDate = (TextView) itemView.findViewById(R.id.nomal_estimate_end_date);
        bidCount = (TextView) itemView.findViewById(R.id.nomal_estimate_bid_count);

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
            enddate = 24 - (this.myEsitmateList.getRegDate() % (24 * 60 * 60 * 1000)) / (1 * 60 * 60 * 1000);
            endDate.setText(enddate + "시간");
            if (enddate <= 1) {
                endDate.setText("마감 임박");
            }
        } else if (enddate < 0) {
            enddate = 0;
            endDate.setText("종료");
        }

        if (myEsitmateList.getMarketType().equals("기장")) {//기장
            marketType_image.setImageResource(Utills.entryIcon(this.myEsitmateList.getMarketSubType()));
            marketType_title.setText(this.myEsitmateList.getMarketSubType());
            marketType_sub[0].setText("매출 " + this.myEsitmateList.getBusinessScale() + ", 종업원 " + this.myEsitmateList.getEmployeeCount() + "명");
            for (int i = 1; i < 4; i++)
                marketType_sub[i].setVisibility(View.GONE);
        } else if (myEsitmateList.getMarketType().equals("TAX")) {//기타
            marketType_image.setImageResource(R.drawable.tax_icon);
            marketType_title.setText(this.myEsitmateList.getMarketSubType());
            for (int i = 0; i < this.myEsitmateList.getAssetType().size(); i++) {
                marketType_sub[i].setVisibility(View.VISIBLE);
                marketType_sub[i].setText("자산 " + this.myEsitmateList.getAssetType().get(i) + ", " + this.myEsitmateList.getMarketPrice().get(i));
            }
        } else {//기타
            marketType_image.setImageResource(R.drawable.etc_icon);
            marketType_title.setText("기타");
            marketType_sub[0].setText("상세 페이지를 확인하세요");
        }
        bidCount.setText("" + this.myEsitmateList.getOldCnt());
        bids_count_image.setVisibility(View.VISIBLE);
        if (this.myEsitmateList.getStatus().equals("낙찰완료")) {
            bidCount.setText("낙찰완료");
            bids_count_image.setVisibility(View.GONE);
        } else if (this.myEsitmateList.getStatus().equals("입찰전")) {
            bidCount.setText("견적 확인중");
            bids_count_image.setVisibility(View.GONE);
        }
    }
}
