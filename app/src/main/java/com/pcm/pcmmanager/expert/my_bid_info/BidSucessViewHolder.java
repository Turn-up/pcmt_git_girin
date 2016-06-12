package com.pcm.pcmmanager.expert.my_bid_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatus;

/**
 * Created by LG on 2016-05-22.
 */
public class BidSucessViewHolder extends RecyclerView.ViewHolder {

    ExpertBidStatus expertBidStatus;

    ImageView Bid_success_marketType_image;
    TextView Bid_success_marketType_title, Bid_success_marketType_sub, endDate, Bid_success_month_money, Bid_success_edit_money, Bid_bids_count, won, persent, bid_success_month_money_type, bid_success_month_money_type2;
    LinearLayout linearLayout;


    public BidSucessViewHolder(View itemView) {
        super(itemView);
        Bid_success_marketType_image = (ImageView) itemView.findViewById(R.id.bid_success_market_type_image);
        Bid_success_marketType_title = (TextView) itemView.findViewById(R.id.bid_success_market_type_title);
        Bid_success_marketType_sub = (TextView) itemView.findViewById(R.id.bid_success_market_type_sub);
        Bid_success_month_money = (TextView) itemView.findViewById(R.id.bid_success_month_money);
        Bid_success_edit_money = (TextView) itemView.findViewById(R.id.bid_success_edit_money);
        Bid_bids_count = (TextView) itemView.findViewById(R.id.bid_success_bid_count);
        endDate = (TextView) itemView.findViewById(R.id.bid_success_end_date);
        won = (TextView) itemView.findViewById(R.id.won);
        persent = (TextView) itemView.findViewById(R.id.persent);
        bid_success_month_money_type = (TextView) itemView.findViewById(R.id.bid_success_month_money_type);
        bid_success_month_money_type2 = (TextView) itemView.findViewById(R.id.bid_success_month_money_type2);

        linearLayout = (LinearLayout) itemView.findViewById(R.id.bid_success_item_onclick);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getVisibility() == View.GONE) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    public interface OnItemClickListener {
        public void OnItemClick(View view);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    public void setBidSuccessList(ExpertBidStatus expertBidStatus) {
        this.expertBidStatus = expertBidStatus;
        Bid_success_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
        if (this.expertBidStatus.getMarketType().equals("기장")) {
            Bid_success_marketType_image.setImageResource(R.drawable.entry_icon);
            Bid_success_marketType_sub.setText("종업원 " + this.expertBidStatus.getEmployeeCount() + "명, 매출" + this.expertBidStatus.getBusinessScale());
            bid_success_month_money_type.setText("월 기장료");
            bid_success_month_money_type2.setText("조정료");
            Bid_success_month_money.setText(""+this.expertBidStatus.getPrice1());
            Bid_success_edit_money.setText(""+this.expertBidStatus.getPrice2());
            persent.setVisibility(View.GONE);
        } else if (this.expertBidStatus.getMarketType().equals("TAX")) {
            Bid_success_marketType_image.setImageResource(R.drawable.tax_icon);
            String temp = "자산 내용 ";
            for (int i = 0; i < this.expertBidStatus.getAssetType().size(); i++)
                temp += this.expertBidStatus.getAssetType().get(i) + " ";
            bid_success_month_money_type.setText("제시금액");
            bid_success_month_money_type2.setText("과세금액");
            Bid_success_marketType_sub.setText(temp + "자산 " + this.expertBidStatus.getMarketPrice());
            Bid_success_month_money.setText(""+this.expertBidStatus.getPrice1());
            Bid_success_edit_money.setText(""+this.expertBidStatus.getPrice2());
            won.setVisibility(View.GONE);

        } else {
            Bid_success_marketType_image.setImageResource(R.drawable.etc_icon);
            Bid_success_marketType_sub.setText("상세 내용을 확인하세요");
            bid_success_month_money_type.setText("제시금액");
            Bid_success_month_money.setText(""+this.expertBidStatus.getPrice1() + "원");
            Bid_success_edit_money.setVisibility(View.GONE);
        }
        Bid_bids_count.setText("" + this.expertBidStatus.getBidCount());
        endDate.setText("D-" + this.expertBidStatus.getEndDate());
    }
}
