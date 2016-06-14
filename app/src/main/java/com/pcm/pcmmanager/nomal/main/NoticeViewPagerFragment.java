package com.pcm.pcmmanager.nomal.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.notice.event.NoticeEventActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeViewPagerFragment extends Fragment {

    private int mNoticeSn;
    private String imageUrl;

    public NoticeViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoticeSn = getArguments().getInt("noticeSn");  //온클릭시 noticesn으로 이동
        imageUrl = getArguments().getString("imageUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_view_pager, container, false);
        ImageView noticeImageView = (ImageView) view.findViewById(R.id.noticeImage);
        Glide.with(getContext()).load(imageUrl).into(noticeImageView);
        
        noticeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoticeEventActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
