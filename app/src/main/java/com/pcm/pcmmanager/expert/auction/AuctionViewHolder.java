package com.pcm.pcmmanager.expert.auction;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateList;

/**
 * Created by LG on 2016-05-22.
 */
public class AuctionViewHolder extends RecyclerView.ViewHolder {
    ExpertEstimateList expertEstimateList;

    TextView marketType_title, marketType_sub, endDate, bidCount;


    public interface OnItemClickListener {
        public void OnItemClick(View view, ExpertEstimateList expertEstimateList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AuctionViewHolder(View itemView) {
        super(itemView);
        marketType_title = (TextView) itemView.findViewById(R.id.expert_estimate_market_type_title);
        marketType_sub = (TextView) itemView.findViewById(R.id.expert_estimate_market_type_sub);
        endDate = (TextView) itemView.findViewById(R.id.expert_estimate_end_date);
        bidCount = (TextView) itemView.findViewById(R.id.expert_estimate_bid_count);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, expertEstimateList);
                }
            }
        });

    }

    public void setAuctionList(ExpertEstimateList expertEstimateList) {
        this.expertEstimateList = expertEstimateList;
        if (expertEstimateList.getMarketType().equals("기장")) {//기장
            marketType_title.setText(this.expertEstimateList.getMarketTypeSub());
            marketType_sub.setText("매출 " + this.expertEstimateList.getBusinessScale()+ ", 종업원 "
                    + this.expertEstimateList.getEmployeeCount()+"명");
        } else if (expertEstimateList.getMarketType().equals("TAX") || expertEstimateList.getMarketType().equals("TEX")) {//기타
            marketType_title.setText(this.expertEstimateList.getMarketTypeSub());
            String temp = ", 자산 내용 ";
            for (int i = 0; i < this.expertEstimateList.getTaxDetail().size(); i++) {
                temp += this.expertEstimateList.getTaxDetail().get(i)+ " ";
            }
            marketType_sub.setText("자산 "+this.expertEstimateList.getMarketPrice()+temp);
        } else {//기타
            marketType_title.setText("기타");
            marketType_sub.setText("상세 페이지를 확인하세요");
        }
        bidCount.setText("" + this.expertEstimateList.getBidscount());
        endDate.setText("D-" + this.expertEstimateList.getEnddate());

    }

}
