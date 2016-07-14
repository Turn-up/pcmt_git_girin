package com.pcm.pcmmanager.nomal.drawer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.UserProfileResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class NomalNavigationDrawer extends Fragment {

    TextView profieName, profileEmail, ask, notice, event, use_way, myEstimate, logout_btn, user_out_btn, myQna;
    LinearLayout logout_layout;
    ImageButton setting_btn;

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

    public NomalNavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nomal_navigation_drawer, container, false);
        ask = (TextView) v.findViewById(R.id.nomal_drawer_ask);
        notice = (TextView) v.findViewById(R.id.nomal_drawer_notice);
        event = (TextView) v.findViewById(R.id.nomal_drawer_event);
        myEstimate = (TextView) v.findViewById(R.id.nomal_drawer_my_estimate);
        use_way = (TextView) v.findViewById(R.id.nomal_drawer_use_way);
        profieName = (TextView) v.findViewById(R.id.nomal_drawer_name);
        profileEmail = (TextView) v.findViewById(R.id.nomal_drawer_email);
        setting_btn = (ImageButton) v.findViewById(R.id.nomal_drawer_setting);
        myQna = (TextView) v.findViewById(R.id.nomal_drawer_my_qna);

        setUserData();
        return v;
    }

    public void setUserData() {
        NetworkManager.getInstance().getUserProfile(new NetworkManager.OnResultListener<UserProfileResult>() {
            @Override
            public void onSuccess(Request request, UserProfileResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    profieName.setText(result.getUserProfile().getUsername());
                    profileEmail.setText(result.getUserProfile().getEmail());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
