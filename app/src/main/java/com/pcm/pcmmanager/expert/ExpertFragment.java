package com.pcm.pcmmanager.expert;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.condition_search.ConditionSearchFragment;
import com.pcm.pcmmanager.data.ExpertEstimateList;
import com.pcm.pcmmanager.data.ExpertEstimateResult;
import com.pcm.pcmmanager.data.MainBidCountResult;
import com.pcm.pcmmanager.expert.auction.AuctionAdapter;
import com.pcm.pcmmanager.expert.auction.AuctionSearchFragment;
import com.pcm.pcmmanager.expert.auction.AuctionViewHolder;
import com.pcm.pcmmanager.expert.bid_do.BidDoActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpertFragment extends Fragment {

    public static final String PAGE_SIZE = "10";

    RecyclerView recyclerView;
    AuctionAdapter mAdapter;

    Button btn_expert_condition_search, btn_expert_estimate_view;
    TextView bidCount, bidEndCount;

    public ExpertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.expert_estimate_rv_list);
        mAdapter = new AuctionAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_expert_estimate_view = (Button) view.findViewById(R.id.btn_expert_estimate_view);

        mAdapter.setOnItemClickListener(new AuctionViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, ExpertEstimateList expertEstimateList) {
                if (PropertyManager.getInstance().getExpertCheck()) {
                    Intent intent = new Intent(getContext(), BidDoActivity.class);
                    intent.putExtra("marketSn", String.valueOf(expertEstimateList.getMarketSn()));
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "심사중입니다. 심사완료후 이용가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //견적찾기 버튼
        btn_expert_estimate_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PropertyManager.getInstance().getExpertCheck()) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.expert_parent_container, new AuctionSearchFragment())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "심사중입니다. 심사완료후 이용가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bidEndCount = (TextView) view.findViewById(R.id.total_auction_count_value);
        bidCount = (TextView) view.findViewById(R.id.total_doing_estimate_count);


        //조건검색 버튼
        btn_expert_condition_search = (Button) view.findViewById(R.id.btn_expert_condition_search);
        btn_expert_condition_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.expert_parent_container, new ConditionSearchFragment())
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        setData();
        super.onResume();
    }

    private void setData() {
        //견적리스트
        NetworkManager.getInstance().getExpertEstimateResult(PAGE_SIZE, "0", new NetworkManager.OnResultListener<ExpertEstimateResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateResult result) {
                mAdapter.clear();
                if(result.getResult() == -1){
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else
                    mAdapter.addAll(result.getEstimateList());

            }

            @Override
            public void onFail(Request request, IOException exception) {
            }
        });
        //총 경매수, 진행수
        NetworkManager.getInstance().getMainBidCount(new NetworkManager.OnResultListener<MainBidCountResult>() {
            @Override
            public void onSuccess(Request request, MainBidCountResult result) {
                bidCount.setText(String.valueOf(result.getMainBidCount().getBidCount()));
                bidEndCount.setText(String.valueOf(result.getMainBidCount().getBidEndCount()));
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });

    }
}
