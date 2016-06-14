package com.pcm.pcmmanager.expert.drawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.expert_detail_info.ExpertDetailInfoActivity;
import com.pcm.pcmmanager.data.ExpertNavInfo;
import com.pcm.pcmmanager.data.ExpertNavInfoResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;

import java.io.IOException;

import okhttp3.Request;


public class ExpertNavigationDrawerFramgent extends Fragment {
    ImageView profileImage, underbar;
    TextView profileName, profileEmail, profilePoint, profileEntryCount, profileBidSuccessCount, Myinfo,
            profileBidFinishCount, ask, notice, event, use_way;
    Button logout;
    LinearLayout layout1, layout_un_confirm, layout, logout_layout;
    FrameLayout layout2;

    public interface OnMenuClickListener {
        public void onMenuClick(int menuId);
    }

    OnMenuClickListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuClickListener) {
            mCallback = (OnMenuClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expert_navigation_drawer_framgent, container, false);
        profileImage = (ImageView) v.findViewById(R.id.nav_header_expert_profile_image);
        profileName = (TextView) v.findViewById(R.id.nav_header_expert_profile_name);
        profileEmail = (TextView) v.findViewById(R.id.nav_header_expert_profile_email);
        profilePoint = (TextView) v.findViewById(R.id.nav_header_expert_profile_point);
        profileEntryCount = (TextView) v.findViewById(R.id.nav_header_expert_profile_entry_count);
        profileBidSuccessCount = (TextView) v.findViewById(R.id.nav_header_expert_profile_bid_success_count);
        profileBidFinishCount = (TextView) v.findViewById(R.id.nav_header_expert_profile_bid_finish_count);
        Myinfo = (TextView) v.findViewById(R.id.expert_drawer_myInfo);
        ask = (TextView) v.findViewById(R.id.expert_drawer_ask);
        notice = (TextView) v.findViewById(R.id.expert_drawer_notice);
        event = (TextView) v.findViewById(R.id.expert_drawer_event);
        use_way = (TextView) v.findViewById(R.id.expert_drawer_use_way);
        logout = (Button) v.findViewById(R.id.expert_drawer_logout);
        underbar = (ImageView) v.findViewById(R.id.textView);
        layout1 = (LinearLayout) v.findViewById(R.id.expert_drawer_layout1);
        layout2 = (FrameLayout) v.findViewById(R.id.expert_drawer_layout2);
        layout_un_confirm = (LinearLayout) v.findViewById(R.id.layout_un_confirm);
        layout = (LinearLayout) v.findViewById(R.id.layout);
        logout_layout = (LinearLayout) v.findViewById(R.id.expert_drawer_logout_layout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logout_layout.getVisibility() == View.VISIBLE) {
                    logout_layout.setVisibility(View.GONE);
                } else
                    logout_layout.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setNavData();
    }

    private void setNavData() {
        NetworkManager.getInstance().getExpertsNavInfo(new NetworkManager.OnResultListener<ExpertNavInfoResult>() {
            @Override
            public void onSuccess(Request request, final ExpertNavInfoResult result) {
                ExpertNavInfo item = result.getExpertNavInfo();
                if (PropertyManager.getInstance().getExpertCheck()) {
                    layout.setBackgroundResource(R.drawable.expert_drawer_background);
                    layout_un_confirm.setVisibility(View.GONE);
                    underbar.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);

                    if (TextUtils.isEmpty(item.getExpertPhoto())) {
                        if (item.getSex().equals("여자"))
                            profileImage.setImageResource(R.drawable.expert_drawer_semoo_girl);
                        else
                            profileImage.setImageResource(R.drawable.expert_drawer_semoo_man);
                    } else {
                        Glide.with(getContext()).load(item.getExpertPhoto()).into(profileImage);
                    }
                    profileName.setText(item.getExpertName());
                    profileEmail.setText(item.getExpertEmail());
                    profilePoint.setText(String.valueOf(item.getMileage()));
                    profileEntryCount.setText(String.valueOf(item.getEntryCount()));
                    profileBidSuccessCount.setText(String.valueOf(item.getBidSuccessCount()));
                    profileBidFinishCount.setText(String.valueOf(item.getBidFinishCount()));
                    Myinfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), ExpertDetailInfoActivity.class);
                            intent.putExtra("expertSn",String.valueOf(result.getExpertNavInfo().getExpertSn()));
                            startActivity(intent);

                        }
                    });
                } else {
                    //승인이 아닐때 보여줄 fragment
                    layout.setBackgroundResource(R.drawable.nomal_nav_backround);
                    layout_un_confirm.setVisibility(View.VISIBLE);
                    underbar.setVisibility(View.GONE);
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
