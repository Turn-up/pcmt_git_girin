package com.pcm.pcmmanager.expert.bid_info;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatus;
import com.pcm.pcmmanager.data.ExpertBidStatusResult;
import com.pcm.pcmmanager.expert.ExpertParentFragment;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidSuccessFragment extends Fragment {

    RecyclerView recyclerView;
    BidSuccessAdapter mAdapter;

    public static final String PAGESIZE = "10";
    public static final String STATUS = "110_004";
    LinearLayoutManager mLayoutManager;
    Boolean isLast;
    String last_marketSn;

    public BidSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid_success, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.bid_success_rv_list);
        last_marketSn = "0";
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new BidSuccessAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new BidSucessViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, final ExpertBidStatus expertBidStatus) {
                LinearLayout linearLayout  = (LinearLayout)view.findViewById(R.id.bid_success_item_onclick);
                ImageButton callBtn = (ImageButton)view.findViewById(R.id.bid_success_call);
                if (linearLayout.getVisibility() == View.GONE) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                }
                callBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: "+ expertBidStatus.getPhone()));
                        startActivity(intent);
                    }
                });
            }
        });
        //Fragment back 구현
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new ExpertParentFragment())
                            .commit();
                    return true;
                } else {
                    return false;
                }
            }
        });
        isLast = false;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isLast && newState == RecyclerView.SCROLL_STATE_IDLE){
                    getMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalCount = mAdapter.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (totalCount > 0 && lastVisibleItem >= totalCount - 1){
                    isLast =true;
                }else
                    isLast= false;
            }
        });

        setData();
        return v;
    }

    private void setData() {
        mAdapter.clear();
        String last_marketsn = "0";
        NetworkManager.getInstance().getExpertBidStatus(PAGESIZE, last_marketsn, STATUS, new NetworkManager.OnResultListener<ExpertBidStatusResult>() {
            @Override
            public void onSuccess(Request request, ExpertBidStatusResult result) {
                mAdapter.clear();
                mAdapter.addAll(result.getList());
                mAdapter.setTotalCount(result.getTotalcount());
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
    private boolean isMoreData = false;

    public void getMoreData() {
        if (!isMoreData && mAdapter.isMoreData()) {
            isMoreData = true;
            final int sn = mAdapter.getLastSn(mAdapter.getItemCount() - 1);
            try {
                NetworkManager.getInstance().getExpertBidStatus(PAGESIZE, String.valueOf(sn), STATUS, new NetworkManager.OnResultListener<ExpertBidStatusResult>() {
                    @Override
                    public void onSuccess(Request request, ExpertBidStatusResult result) {
                        mAdapter.addAll(result.getList());
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
