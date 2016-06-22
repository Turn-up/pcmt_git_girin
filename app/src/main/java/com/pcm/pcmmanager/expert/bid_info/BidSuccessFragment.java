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

    public static final String PAGE_SIZE = "10";
    public static final String STATUS = "110_004";

    public BidSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid_success, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.bid_success_rv_list);
        mAdapter = new BidSuccessAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        setData();
        return v;
    }

    private void setData() {
        mAdapter.clear();
        String last_marketsn = "0";
        NetworkManager.getInstance().getExpertBidStatus(PAGE_SIZE, last_marketsn, STATUS, new NetworkManager.OnResultListener<ExpertBidStatusResult>() {
            @Override
            public void onSuccess(Request request, ExpertBidStatusResult result) {
                mAdapter.addAll(result.getList());
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
