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
public class BidIngViewHolder extends RecyclerView.ViewHolder {

    ExpertBidStatus expertBidStatus;

    ImageView Bid_ing_marketType_image;
    TextView Bid_ing_marketType_title, endDate, bidCount;
    TextView Bid_ing_marketType_sub[];

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
        Bid_ing_marketType_sub = new TextView[4];
        Bid_ing_marketType_sub[0] = (TextView) itemView.findViewById(R.id.bid_ing_market_type_sub1);
        Bid_ing_marketType_sub[1] = (TextView) itemView.findViewById(R.id.bid_ing_market_type_sub2);
        Bid_ing_marketType_sub[2] = (TextView) itemView.findViewById(R.id.bid_ing_market_type_sub3);
        Bid_ing_marketType_sub[3] = (TextView) itemView.findViewById(R.id.bid_ing_market_type_sub4);
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
        if (this.expertBidStatus.getMarketType().equals("기장")) {
            Bid_ing_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
            Bid_ing_marketType_image.setImageResource(Utills.entryIcon(expertBidStatus.getMarketSubtype().toString()));
            Bid_ing_marketType_sub[0].setText("매출 " + this.expertBidStatus.getBusinessScale() + ", 종업원" + this.expertBidStatus.getEmployeeCount()+"명");
        } else if (this.expertBidStatus.getMarketType().equals("TAX")) {
            Bid_ing_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
            Bid_ing_marketType_image.setImageResource(R.drawable.tax_icon);
            for (int i = 0; i < this.expertBidStatus.getAssetType().size(); i++) {
                Bid_ing_marketType_sub[i].setVisibility(View.VISIBLE);
                Bid_ing_marketType_sub[i].setText("자산 " + this.expertBidStatus.getAssetType().get(i) + ", " + this.expertBidStatus.getMarketPrice().get(i));
            }
        } else {
            Bid_ing_marketType_image.setImageResource(R.drawable.etc_icon);
            Bid_ing_marketType_title.setText("기타");
            Bid_ing_marketType_sub[0].setText("상세 내용을 확인하세요");
        }
        bidCount.setText("" + this.expertBidStatus.getBidCount());
        endDate.setText("D-" + this.expertBidStatus.getEndDate());
    }
}

