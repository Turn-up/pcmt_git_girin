package com.pcm.pcmmanager.nomal.estimate_list;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateDetailBidList;

/**
 * Created by LG on 2016-05-19.
 */
public class MyEstimateDetailBidListsViewHolder extends RecyclerView.ViewHolder {

    ImageView expertIamge;
    TextView expertName, comment, monthMoney, modifyMoney, moneyTypeOne, moneyTypeTwo, persentORwon;
    LinearLayout bidListLayout;

    ExpertEstimateDetailBidList mList;

    public interface OnItemClickListener {
        public void onItemClick(View view, ExpertEstimateDetailBidList mList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEstimateDetailBidListsViewHolder(View itemView) {
        super(itemView);
        expertIamge = (ImageView) itemView.findViewById(R.id.bids_list_expert_image);
        expertName = (TextView) itemView.findViewById(R.id.bids_list_expert_name);
        comment = (TextView) itemView.findViewById(R.id.bids_list_coment);
        monthMoney = (TextView) itemView.findViewById(R.id.bids_list_month_money);
        modifyMoney = (TextView) itemView.findViewById(R.id.bids_list_modify_money);
        moneyTypeOne = (TextView) itemView.findViewById(R.id.bids_list_money_tpye_one);
        moneyTypeTwo = (TextView) itemView.findViewById(R.id.bids_list_money_tpye_two);
        persentORwon = (TextView) itemView.findViewById(R.id.persentORwon);
        bidListLayout = (LinearLayout)itemView.findViewById(R.id.bid_list_layout);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, mList);
                }
            }
        });
    }

    public void setBidsList(ExpertEstimateDetailBidList list, String markettype) {
        mList = list;
        if(markettype.equals("0")){
            bidListLayout.setVisibility(View.GONE);
        }
        expertName.setText(mList.getExpertName());
        if (TextUtils.isEmpty(mList.getPhoto())) {
            if (mList.getSex().equals("남자")) {
                expertIamge.setImageResource(R.drawable.semooman_icon);
            } else if (mList.getSex().equals("여자")) {
                expertIamge.setImageResource(R.drawable.semoogirl_icon);
            }
        } else {
            Glide.with(expertIamge.getContext()).load(mList.getPhoto()).into(expertIamge);
        }
        comment.setText(mList.getComment());
        if (markettype.equals("기장")) {
            moneyTypeOne.setText("제시금액");
            moneyTypeTwo.setText("조정료");
            monthMoney.setText("" + mList.getPrice());
            modifyMoney.setText("" + mList.getPrice2());
        } else if (markettype.equals("TAX")) {
            moneyTypeOne.setText("제시금액");
            moneyTypeTwo.setText("과세금액");
            monthMoney.setText("" + mList.getPrice());
            modifyMoney.setText("" + mList.getPrice2());
            persentORwon.setText("%");
        } else {
            moneyTypeOne.setText("제시금액");
            moneyTypeTwo.setVisibility(View.GONE);
            monthMoney.setText("" + mList.getPrice());
            modifyMoney.setVisibility(View.GONE);
        }
    }
}
