package com.pcm.pcmmanager.expert;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;
    AuctionAdapter mAdapter;

    Button btn_expert_condition_search, btn_expert_estimate_view;
    TextView bidCount, bidEndCount;
    Boolean isLast;
    LinearLayoutManager mLayoutManager;

    ImageView totalCount[], auctionCount[];

    public ExpertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert, container, false);
        mLayoutManager = new LinearLayoutManager(getContext());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.expert_estimate_swipe_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.expert_estimate_rv_list);
        mAdapter = new AuctionAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        btn_expert_estimate_view = (Button) view.findViewById(R.id.btn_expert_estimate_view);

        totalCount = new ImageView[5];
        totalCount[0] = (ImageView) view.findViewById(R.id.total_count_one);
        totalCount[1] = (ImageView) view.findViewById(R.id.total_count_two);
        totalCount[2] = (ImageView) view.findViewById(R.id.total_count_three);
        totalCount[3] = (ImageView) view.findViewById(R.id.total_count_four);
        totalCount[4] = (ImageView) view.findViewById(R.id.total_count_five);

        auctionCount = new ImageView[5];
        auctionCount[0] = (ImageView) view.findViewById(R.id.biding_count_one);
        auctionCount[1] = (ImageView) view.findViewById(R.id.biding_count_two);
        auctionCount[2] = (ImageView) view.findViewById(R.id.biding_count_three);
        auctionCount[3] = (ImageView) view.findViewById(R.id.biding_count_four);
        auctionCount[4] = (ImageView) view.findViewById(R.id.biding_count_five);
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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshItem();
            }
        });
        isLast = false;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLast && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalCount = mAdapter.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (totalCount > 0 && lastVisibleItem >= totalCount - 1) {
                    isLast = true;
                } else
                    isLast = false;
            }
        });
        return view;
    }

    private void setRefreshItem() {
        setData();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
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
                if (result.getResult() == -1) {
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.addAll(result.getEstimateList());
                    mAdapter.setTotalCount(result.getTotalCount());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
            }
        });
        //총 경매수, 진행수
        NetworkManager.getInstance().getMainBidCount(new NetworkManager.OnResultListener<MainBidCountResult>() {
            @Override
            public void onSuccess(Request request, MainBidCountResult result) {
                String total = "" + result.getMainBidCount().getBidEndCount();
                for (int i = total.length() - 1; i >= 0; i--) {
                    switch (total.charAt(i)) {
                        case '0':
                            totalCount[4 - i].setImageResource(R.drawable.count_zero_icon);
                            break;
                        case '1':
                            totalCount[4 - i].setImageResource(R.drawable.count_one_icon);
                            break;
                        case '2':
                            totalCount[4 - i].setImageResource(R.drawable.count_two_icon);
                            break;
                        case '3':
                            totalCount[4 - i].setImageResource(R.drawable.count_three_icon);
                            break;
                        case '4':
                            totalCount[4 - i].setImageResource(R.drawable.count_four_icon);
                            break;
                        case '5':
                            totalCount[4 - i].setImageResource(R.drawable.count_five_icon);
                            break;
                        case '6':
                            totalCount[4 - i].setImageResource(R.drawable.count_six_icon);
                            break;
                        case '7':
                            totalCount[4 - i].setImageResource(R.drawable.count_seven_icon);
                            break;
                        case '8':
                            totalCount[4 - i].setImageResource(R.drawable.count_eight_icon);
                            break;
                        case '9':
                            totalCount[4 - i].setImageResource(R.drawable.count_nine_icon);
                    }
                    break;

                }
                String bidIng = "" + result.getMainBidCount().getBidCount();
                for (int i = bidIng.length() - 1; i >= 0; i--) {
                    switch (bidIng.charAt(i)) {
                        case '0':
                            auctionCount[4 - i].setImageResource(R.drawable.count_zero_icon);
                            break;
                        case '1':
                            auctionCount[4 - i].setImageResource(R.drawable.count_one_icon);
                            break;
                        case '2':
                            auctionCount[4 - i].setImageResource(R.drawable.count_two_icon);
                            break;
                        case '3':
                            auctionCount[4 - i].setImageResource(R.drawable.count_three_icon);
                            break;
                        case '4':
                            auctionCount[4 - i].setImageResource(R.drawable.count_four_icon);
                            break;
                        case '5':
                            auctionCount[4 - i].setImageResource(R.drawable.count_five_icon);
                            break;
                        case '6':
                            auctionCount[4 - i].setImageResource(R.drawable.count_six_icon);
                            break;
                        case '7':
                            auctionCount[4 - i].setImageResource(R.drawable.count_seven_icon);
                            break;
                        case '8':
                            auctionCount[4 - i].setImageResource(R.drawable.count_eight_icon);
                            break;
                        case '9':
                            auctionCount[4 - i].setImageResource(R.drawable.count_nine_icon);
                            break;
                    }
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    private boolean isMoreData = false;
    private void getMoreData() {
        if (!isMoreData && mAdapter.isMoreData()) {
            isMoreData = true;
            final int sn = mAdapter.getLastSn(mAdapter.getItemCount() - 1);
            try {
                NetworkManager.getInstance().getExpertEstimateResult(PAGE_SIZE, String.valueOf(sn), new NetworkManager.OnResultListener<ExpertEstimateResult>() {
                    @Override
                    public void onSuccess(Request request, ExpertEstimateResult result) {
                        mAdapter.addAll(result.getEstimateList());
                        isMoreData = false;
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                isMoreData = false;

            }

        }
    }
}
