package com.pcm.pcmmanager.expert.bid_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatus;
import com.pcm.pcmmanager.utill.Utills;

/**
 * Created by LG on 2016-05-22.
 */
public class BidFinishViewHolder extends RecyclerView.ViewHolder {

    ImageView Bid_finish_marketType_image;
    TextView Bid_finish_marketType_title, endDate, bidCount, bidState;
    TextView Bid_finish_marketType_sub[];
    ExpertBidStatus expertBidStatus;
    int color;

    public BidFinishViewHolder(View itemView) {
        super(itemView);

        Bid_finish_marketType_image = (ImageView) itemView.findViewById(R.id.bid_finish_market_type_image);
        Bid_finish_marketType_title = (TextView) itemView.findViewById(R.id.bid_finish_market_type_title);
        Bid_finish_marketType_sub = new TextView[4];
        Bid_finish_marketType_sub[0] = (TextView) itemView.findViewById(R.id.bid_finish_market_type_sub1);
        Bid_finish_marketType_sub[1] = (TextView) itemView.findViewById(R.id.bid_finish_market_type_sub2);
        Bid_finish_marketType_sub[2] = (TextView) itemView.findViewById(R.id.bid_finish_market_type_sub3);
        Bid_finish_marketType_sub[3] = (TextView) itemView.findViewById(R.id.bid_finish_market_type_sub4);
        endDate = (TextView) itemView.findViewById(R.id.bid_finish_end_date);
        bidCount = (TextView) itemView.findViewById(R.id.bid_finish_bid_count);
        bidState = (TextView) itemView.findViewById(R.id.bid_finish_bid_status);
    }

    public void setBidFinishList(ExpertBidStatus expertBidStatus) {
        this.expertBidStatus = expertBidStatus;
        color = itemView.getResources().getColor(R.color.bid_finish);
        Bid_finish_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
        if (this.expertBidStatus.getMarketType().equals("기장")) {
            Bid_finish_marketType_image.setImageResource(Utills.entryIcon(expertBidStatus.getMarketSubtype().toString()));
            Bid_finish_marketType_sub[0].setText("매출 " + this.expertBidStatus.getBusinessScale() + ", 종업원 " + this.expertBidStatus.getEmployeeCount() + "명");
            for(int i = 1; i<4;i++)
                Bid_finish_marketType_sub[i].setVisibility(View.GONE);
        } else if (this.expertBidStatus.getMarketType().equals("TAX")) {
            Bid_finish_marketType_image.setImageResource(R.drawable.bid_finish_tax_icon);
                for (int i = 0; i < this.expertBidStatus.getAssetType().size(); i++) {
                    Bid_finish_marketType_sub[i].setVisibility(View.VISIBLE);
                    Bid_finish_marketType_sub[i].setText("자산 " + this.expertBidStatus.getAssetType().get(i) + ", " + this.expertBidStatus.getMarketPrice().get(i));
            }
        } else {
            Bid_finish_marketType_image.setImageResource(R.drawable.bid_finish_etc_icon);
            Bid_finish_marketType_title.setText("기타");
            Bid_finish_marketType_sub[0].setText("상세 내용을 확인하세요");
            for(int i = 1; i<4;i++)
                Bid_finish_marketType_sub[i].setVisibility(View.GONE);
        }
        endDate.setText("D-" + this.expertBidStatus.getEndDate());
        bidCount.setText("" + this.expertBidStatus.getBidCount());
        if (expertBidStatus.getStatus().equals("입찰마감")) {
            bidState.setText("입찰마감");
            bidCount.setVisibility(View.GONE);
        } else if (expertBidStatus.getStatus().equals("유찰")) {
            if(this.expertBidStatus.getMarketSubtype().equals("TAX"))
                for(int i = 0; i<this.expertBidStatus.getAssetType().size();i++)
                    Bid_finish_marketType_sub[i].setTextColor(color);
            Bid_finish_marketType_sub[0].setTextColor(color);
            endDate.setTextColor(color);
            Bid_finish_marketType_title.setTextColor(color);
            bidState.setText("유찰");
            bidCount.setVisibility(View.GONE);
        } else if (expertBidStatus.getStatus().equals("입찰취소")) {
            if(this.expertBidStatus.getMarketSubtype().equals("TAX"))
                for(int i = 0; i<this.expertBidStatus.getAssetType().size();i++)
                    Bid_finish_marketType_sub[i].setTextColor(color);
            Bid_finish_marketType_sub[0].setTextColor(color);
            endDate.setTextColor(color);
            Bid_finish_marketType_title.setTextColor(color);
            bidState.setText("입찰취소");
            bidCount.setVisibility(View.GONE);
        }
    }
}

