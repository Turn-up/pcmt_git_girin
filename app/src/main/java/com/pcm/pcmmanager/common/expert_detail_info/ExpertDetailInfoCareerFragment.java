package com.pcm.pcmmanager.common.expert_detail_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpertDetailInfoCareerFragment extends Fragment implements ExpertDetailInfoActivity.OnNotifyReceiver {

    RecyclerView recyclerView;
    CareerAdapter mAdapter;
    ExpertDetailInfo expertDetailInfo;
    public ExpertDetailInfoCareerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_expert_detail_info_career, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.expert_detail_info_career_rv_list);
        mAdapter = new CareerAdapter();
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        onNotify();
        return  view;
    }

    public void setData(){
        if (expertDetailInfo != null) {
            mAdapter.setexpertDetailInfo(expertDetailInfo);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        ((ExpertDetailInfoActivity)getActivity()).registerNotifyReceiver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ExpertDetailInfoActivity)getActivity()).unregisterNotifyReceiver(this);
    }

    @Override
    public void onNotify() {
        expertDetailInfo = ((ExpertDetailInfoActivity)getActivity()).getExportDetailInfo();
        // update...
        setData();
    }
}
