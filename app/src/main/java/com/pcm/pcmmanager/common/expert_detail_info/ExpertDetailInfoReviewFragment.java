package com.pcm.pcmmanager.common.expert_detail_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailReviewResult;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpertDetailInfoReviewFragment extends Fragment implements ExpertDetailInfoActivity.OnNotifyReceiver {

    RecyclerView recyclerView;
    ReviewAdapter mAdapter;
    ExpertDetailReviewResult expertDetailReviewResult;
    LinearLayout reviewNull;
    public ExpertDetailInfoReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert_detail_info_review, container, false);
        reviewNull = (LinearLayout) view.findViewById(R.id.expert_detail_info_review_null_layout);
        recyclerView = (RecyclerView)view.findViewById(R.id.expert_detail_info_review_rv_list);
        mAdapter = new ReviewAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        onNotify();
        return view;
    }

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
        expertDetailReviewResult = ((ExpertDetailInfoActivity)getActivity()).getExpertDetailReviewResult();
        // update...
        setData();
    }
    public void setData(){
        if (expertDetailReviewResult != null) {
            if(expertDetailReviewResult.getReviewList().size() != 0){
                mAdapter.addAll(expertDetailReviewResult.getReviewList());
                reviewNull.setVisibility(View.GONE);
            }
        }
    };

}
