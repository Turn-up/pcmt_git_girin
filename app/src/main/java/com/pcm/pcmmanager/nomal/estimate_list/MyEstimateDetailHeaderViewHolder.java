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

    TextView marketType_title, marketType_sub, endDate, content,address,bidCount;
    ExpertEstimateDetail mItem;

    public MyEstimateDetailHeaderViewHolder(View itemView) {
        super(itemView);
        marketType_title = (TextView) itemView.findViewById(R.id.detail_header_title);
        marketType_sub = (TextView) itemView.findViewById(R.id.detail_header_sub);
        endDate = (TextView) itemView.findViewById(R.id.detail_header_end_date);
        address = (TextView) itemView.findViewById(R.id.detail_header_address);
        content = (TextView) itemView.findViewById(R.id.detail_header_content);
        address = (TextView) itemView.findViewById(R.id.detail_header_address);
        bidCount = (TextView)itemView.findViewById(R.id.deail_bid_count);
    }

    public void setHeader(ExpertEstimateDetail item) {
        mItem = item;

        marketType_title.setText(item.getMarketSubType());
        if(item.getMarketType().equals("기타"))
            marketType_title.setText("기타");
        if (item.getMarketType().equals("기장")) {
            marketType_sub.setText("종업원" + item.getEmployeeCount() + "명, 매출 " + item.getBusinessScale());

        } else if (item.getMarketType().equals("TAX")) {
            String temp="자산내용 ";
            for(int i = 0; i<item.getAsset_type().size(); i++){
                temp += item.getAsset_type().get(i) + " ";
            }
            marketType_sub.setText(temp + ", 자산 " + item.getAssetMoney());
        } else if (item.getMarketType().equals("기타")) {
            marketType_sub.setText("상세 페이지를 확인하세요");
        }
        endDate.setText("D-"+item.getEnddate());
        address.setText(item.getAddress1() + " " + item.getAddress2());
        content.setText(item.getContent());
        address.setText(item.getAddress1()+" "+item.getAddress2());
        bidCount.setText(""+item.getBidCount());

    }
}