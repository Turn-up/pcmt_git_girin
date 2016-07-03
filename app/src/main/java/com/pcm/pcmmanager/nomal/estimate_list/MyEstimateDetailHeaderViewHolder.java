package com.pcm.pcmmanager.nomal.estimate_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;

/**
 * Created by LG on 2016-05-19.
 */
public class MyEstimateDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView marketType_title, endDate, content, address, bidCount;
    TextView marketType_sub[];
    ExpertEstimateDetail mItem;

    public MyEstimateDetailHeaderViewHolder(View itemView) {
        super(itemView);
        marketType_title = (TextView) itemView.findViewById(R.id.detail_header_title);
        marketType_sub = new TextView[4];
        marketType_sub[0] = (TextView) itemView.findViewById(R.id.detail_header_market_type_sub1);
        marketType_sub[1] = (TextView) itemView.findViewById(R.id.detail_header_market_type_sub2);
        marketType_sub[2] = (TextView) itemView.findViewById(R.id.detail_header_market_type_sub3);
        marketType_sub[3] = (TextView) itemView.findViewById(R.id.detail_header_market_type_sub4);
        endDate = (TextView) itemView.findViewById(R.id.detail_header_end_date);
        address = (TextView) itemView.findViewById(R.id.detail_header_address);
        content = (TextView) itemView.findViewById(R.id.detail_header_content);
        address = (TextView) itemView.findViewById(R.id.detail_header_address);
        bidCount = (TextView) itemView.findViewById(R.id.deail_bid_count);
    }

    public void setHeader(ExpertEstimateDetail item) {
        mItem = item;

        marketType_title.setText(item.getMarketSubType());
        if (item.getMarketType().equals("기타"))
            marketType_title.setText("기타");
        if (item.getMarketType().equals("기장")) {
            marketType_sub[0].setText("매출 " + item.getBusinessScale() + ", 종업원 " + item.getEmployeeCount() + "명");
        } else if (item.getMarketType().equals("TAX")) {
            marketType_title.setText(item.getMarketSubType());
            if (item.getMarketSubType().equals("세무조사")) {
                marketType_sub[0].setText("시가 " + item.getAssetMoney().get(0));
            } else {
                for (int i = 0; i < item.getAssetType().size(); i++) {
                    marketType_sub[i].setVisibility(View.VISIBLE);
                    marketType_sub[i].setText("자산 " + item.getAssetType().get(i) + ", " + item.getAssetMoney().get(i));
                }
            }
        } else if (item.getMarketType().equals("기타")) {
            marketType_sub[0].setText("상세 페이지를 확인하세요");
        }
        endDate.setText("D-" + item.getEnddate());
        address.setText(item.getAddress1() + " " + item.getAddress2());
        content.setText(item.getContent());
        address.setText(item.getAddress1() + " " + item.getAddress2());
        bidCount.setText("" + item.getBidCount());
    }
}