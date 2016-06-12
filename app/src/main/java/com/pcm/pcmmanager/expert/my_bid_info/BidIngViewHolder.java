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
public class BidIngViewHolder extends RecyclerView.ViewHolder {

    ExpertBidStatus expertBidStatus;

    ImageView Bid_ing_marketType_image;
    TextView Bid_ing_marketType_title, Bid_ing_marketType_sub, endDate, bidCount;


    public interface OnItemClickListener {
        public void OnItemClick(View view, ExpertBidStatus expertBidStatus);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BidIngViewHolder(View itemView) {
        super(itemView);
        Bid_ing_marketType_image = (ImageView) itemView.findViewById(R.id.bid_ing_market_type_image);
        Bid_ing_marketType_title = (TextView) itemView.findViewById(R.id.bid_ing_market_type_title);
        Bid_ing_marketType_sub = (TextView) itemView.findViewById(R.id.bid_ing_market_type_sub);
        endDate = (TextView) itemView.findViewById(R.id.bid_ing_end_date);
        bidCount = (TextView) itemView.findViewById(R.id.bid_ing_bid_count);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, expertBidStatus);
                }
            }
        });

    }

    public void setBidIngList(ExpertBidStatus expertBidStatus) {
        this.expertBidStatus = expertBidStatus;
        Bid_ing_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
        if (this.expertBidStatus.getMarketType().equals("기장")) {
            Bid_ing_marketType_image.setImageResource(R.drawable.entry_icon);
            Bid_ing_marketType_sub.setText("종업원 " + this.expertBidStatus.getEmployeeCount() + "명, 매출" + this.expertBidStatus.getBusinessScale());
        } else if (this.expertBidStatus.getMarketType().equals("TAX")) {
            Bid_ing_marketType_image.setImageResource(R.drawable.tax_icon);
            String temp = "자산 내용 ";
            for (int i = 0; i < this.expertBidStatus.getAssetType().size(); i++)
                temp += this.expertBidStatus.getAssetType().get(i) + " ";
            Bid_ing_marketType_sub.setText(temp + "자산 " + this.expertBidStatus.getMarketPrice());
        } else {
            Bid_ing_marketType_image.setImageResource(R.drawable.etc_icon);
            Bid_ing_marketType_sub.setText("금액");
        }
        endDate.setText("D-" + this.expertBidStatus.getEndDate());
        bidCount.setText("" + this.expertBidStatus.getBidCount());
    }
}

