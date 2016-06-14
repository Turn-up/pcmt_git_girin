package com.pcm.pcmmanager.expert.my_bid_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatus;

/**
 * Created by LG on 2016-05-22.
 */
public class BidFinishViewHolder extends RecyclerView.ViewHolder {

    ImageView Bid_finish_marketType_image;
    TextView Bid_finish_marketType_title, Bid_finish_marketType_sub, endDate, bidCount, bidState;
    ExpertBidStatus expertBidStatus;
    int color;

    public BidFinishViewHolder(View itemView) {
        super(itemView);

        Bid_finish_marketType_image = (ImageView) itemView.findViewById(R.id.bid_finish_market_type_image);
        Bid_finish_marketType_title = (TextView) itemView.findViewById(R.id.bid_finish_market_type_title);
        Bid_finish_marketType_sub = (TextView) itemView.findViewById(R.id.bid_finish_market_type_sub);
        endDate = (TextView) itemView.findViewById(R.id.bid_finish_end_date);
        bidCount = (TextView) itemView.findViewById(R.id.bid_finish_bid_count);
        bidState = (TextView) itemView.findViewById(R.id.bid_finish_bid_status);
        color = itemView.getResources().getColor(R.color.bid_finish);

    }

    public void setBidFinishList(ExpertBidStatus expertBidStatus) {
        this.expertBidStatus = expertBidStatus;
        Bid_finish_marketType_image.setImageResource(R.mipmap.ic_launcher);
        Bid_finish_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
        if (this.expertBidStatus.getMarketType().equals("기장")) {
            Bid_finish_marketType_image.setImageResource(R.drawable.bid_finish_entry_icon);
            Bid_finish_marketType_sub.setText("종업원 " + this.expertBidStatus.getEmployeeCount() + "명, 매출" + this.expertBidStatus.getBusinessScale());
        } else if (this.expertBidStatus.getMarketType().equals("TAX")) {
            Bid_finish_marketType_image.setImageResource(R.drawable.bid_finish_tax_icon);
            String temp = "";
            if (!this.expertBidStatus.getMarketSubtype().equals("세무조사")) {
                temp = "자산 내용";
                for (int i = 0; i < this.expertBidStatus.getAssetType().size(); i++)
                    temp += this.expertBidStatus.getAssetType().get(i) + " ";
            }
            Bid_finish_marketType_sub.setText("자산 " + this.expertBidStatus.getMarketPrice() + temp);
        } else {
            Bid_finish_marketType_image.setImageResource(R.drawable.etc_icon);
            Bid_finish_marketType_title.setText("기타");
            Bid_finish_marketType_sub.setText("상세 내용을 확인하세요");
        }
        endDate.setText("D-" + this.expertBidStatus.getEndDate());
        bidCount.setText("" + this.expertBidStatus.getBidCount());
        if (expertBidStatus.getStatus().equals("입찰마감")) {
            bidState.setText("입찰마감");
            bidCount.setVisibility(View.GONE);
        } else if (expertBidStatus.getStatus().equals("유찰")) {
            Bid_finish_marketType_sub.setText(color);
            endDate.setTextColor(color);
            Bid_finish_marketType_title.setTextColor(color);
            Bid_finish_marketType_image.setBackgroundColor(color);
            bidState.setText("유찰");
            bidCount.setVisibility(View.GONE);
        } else if (expertBidStatus.getStatus().equals("입찰취소")) {
            Bid_finish_marketType_sub.setText(color);
            endDate.setTextColor(color);
            Bid_finish_marketType_title.setTextColor(color);
            Bid_finish_marketType_image.setBackgroundColor(color);
            bidState.setText("입찰취소");
            bidCount.setVisibility(View.GONE);
        }
    }
}

