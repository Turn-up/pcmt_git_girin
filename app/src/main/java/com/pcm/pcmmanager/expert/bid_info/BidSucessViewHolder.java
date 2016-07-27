package com.pcm.pcmmanager.expert.bid_info;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatus;
import com.pcm.pcmmanager.utill.Utills;

import java.text.NumberFormat;

/**
 * Created by LG on 2016-05-22.
 */
public class BidSucessViewHolder extends RecyclerView.ViewHolder {

    ExpertBidStatus expertBidStatus;

    ImageView Bid_success_marketType_image;
    TextView Bid_success_marketType_title, endDate, Bid_success_month_money, Bid_success_edit_money, Bid_bids_count, won, persent, bid_success_month_money_type, bid_success_month_money_type2;
    TextView Bid_success_marketType_sub[];
    LinearLayout linearLayout, money2Layout;
    ImageButton Bid_success_call;


    public BidSucessViewHolder(View itemView) {
        super(itemView);
        Bid_success_marketType_image = (ImageView) itemView.findViewById(R.id.bid_success_market_type_image);
        Bid_success_marketType_title = (TextView) itemView.findViewById(R.id.bid_success_market_type_title);

        Bid_success_marketType_sub = new TextView[4];
        Bid_success_marketType_sub[0] = (TextView) itemView.findViewById(R.id.bid_success_market_type_sub1);
        Bid_success_marketType_sub[1] = (TextView) itemView.findViewById(R.id.bid_success_market_type_sub2);
        Bid_success_marketType_sub[2] = (TextView) itemView.findViewById(R.id.bid_success_market_type_sub3);
        Bid_success_marketType_sub[3] = (TextView) itemView.findViewById(R.id.bid_success_market_type_sub4);

        Bid_success_month_money = (TextView) itemView.findViewById(R.id.bid_success_month_money);
        Bid_success_edit_money = (TextView) itemView.findViewById(R.id.bid_success_edit_money);
        endDate = (TextView) itemView.findViewById(R.id.bid_success_end_date);
        won = (TextView) itemView.findViewById(R.id.won);
        persent = (TextView) itemView.findViewById(R.id.persent);
        bid_success_month_money_type = (TextView) itemView.findViewById(R.id.bid_success_month_money_type);
        bid_success_month_money_type2 = (TextView) itemView.findViewById(R.id.bid_success_month_money_type2);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.bid_success_item_onclick);
        money2Layout = (LinearLayout) itemView.findViewById(R.id.bid_success_money_layout);
        Bid_success_call = (ImageButton) itemView.findViewById(R.id.bid_success_call);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemClick(v, expertBidStatus);
                }
            }
        });

    }

    public interface OnItemClickListener {
        public void OnItemClick(View view, ExpertBidStatus expertBidStatus);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setBidSuccessList(final ExpertBidStatus expertBidStatus) {
        this.expertBidStatus = expertBidStatus;
        NumberFormat nf = NumberFormat.getInstance();
        if (this.expertBidStatus.getMarketType().equals("기장")) {
            Bid_success_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
            Bid_success_marketType_image.setImageResource(Utills.entryIcon(expertBidStatus.getMarketSubtype().toString()));
            Bid_success_marketType_sub[0].setText("매출 " + this.expertBidStatus.getBusinessScale() + ", 종업원" + this.expertBidStatus.getEmployeeCount() + "명");
            for (int i = 1; i < 4; i++)
                Bid_success_marketType_sub[i].setVisibility(View.GONE);
            bid_success_month_money_type.setText("월 기장료");
            bid_success_month_money_type2.setText("조정료");
            Bid_success_month_money.setText(nf.format(this.expertBidStatus.getPrice1()));
            Bid_success_edit_money.setText(nf.format(this.expertBidStatus.getPrice2()));
            persent.setVisibility(View.GONE);
        } else if (this.expertBidStatus.getMarketType().equals("TAX")) {
            Bid_success_marketType_title.setText(this.expertBidStatus.getMarketSubtype());
            Bid_success_marketType_image.setImageResource(R.drawable.tax_icon);
            for (int i = 0; i < this.expertBidStatus.getAssetType().size(); i++) {
                Bid_success_marketType_sub[i].setVisibility(View.VISIBLE);
                Bid_success_marketType_sub[i].setText("자산 " + this.expertBidStatus.getAssetType().get(i) + ", " + this.expertBidStatus.getMarketPrice().get(i));
            }
            bid_success_month_money_type.setText("제시금액");
            bid_success_month_money_type2.setText("과세금액");
            Bid_success_month_money.setText(nf.format(this.expertBidStatus.getPrice1()));
            Bid_success_edit_money.setText(nf.format(this.expertBidStatus.getPrice2()));
            won.setVisibility(View.GONE);

        } else {
            Bid_success_marketType_image.setImageResource(R.drawable.etc_icon);
            Bid_success_marketType_title.setText("기타");
            Bid_success_marketType_sub[0].setText("상세 내용을 확인하세요");
            bid_success_month_money_type.setText("제시금액");
            Bid_success_month_money.setText(nf.format(this.expertBidStatus.getPrice1()));
            money2Layout.setVisibility(View.INVISIBLE);
            for (int i = 1; i < 4; i++)
                Bid_success_marketType_sub[i].setVisibility(View.GONE);

        }
        endDate.setText(this.expertBidStatus.getStringRegdate() + " 마감");
    }
}
