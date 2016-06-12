package com.pcm.pcmmanager.expert.my_bid_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertBidStatusResult;
import com.pcm.pcmmanager.expert.ExpertParentFragment;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFinishFragment extends Fragment {
    RecyclerView recyclerView;
    BidFinishAdapter mAdapter;

    public static final String PAGE_SIZE = "10";
    public static final String STATUS = "110_003";

    public BidFinishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid_finish, container, false);
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
        recyclerView = (RecyclerView) v.findViewById(R.id.bid_finish_rv_list);
        mAdapter = new BidFinishAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
    String last_marketsn="0";
    private void setData() {
        NetworkManager.getInstance().getExpertBidStatus(PAGE_SIZE, last_marketsn, STATUS, new NetworkManager.OnResultListener<ExpertBidStatusResult>() {
            @Override
            public void onSuccess(Request request, ExpertBidStatusResult result) {
                if(result.getResult() == -1){
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else
                    mAdapter.addAll(result.getList());
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
