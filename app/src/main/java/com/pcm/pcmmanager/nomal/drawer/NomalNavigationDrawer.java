package com.pcm.pcmmanager.nomal.drawer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.NomalUserMainResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class NomalNavigationDrawer extends Fragment {

    TextView profieName, profileEmail,ask, notice, event, use_way,myEstimate,logout_btn,user_out_btn;
    LinearLayout logout_layout;
    Button logout;

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
        logout = (Button) v.findViewById(R.id.nomal_drawer_logout);
        ask = (TextView) v.findViewById(R.id.nomal_drawer_ask);
        notice = (TextView) v.findViewById(R.id.nomal_drawer_notice);
        event = (TextView) v.findViewById(R.id.nomal_drawer_event);
        myEstimate = (TextView)v.findViewById(R.id.nomal_drawer_my_estimate);
        use_way = (TextView) v.findViewById(R.id.nomal_drawer_use_way);
        profieName = (TextView) v.findViewById(R.id.nomal_drawer_name);
        profileEmail = (TextView) v.findViewById(R.id.nomal_drawer_email);
        user_out_btn = (TextView)v.findViewById(R.id.nomal_drawer_user_out_btn);
        logout_btn = (TextView)v.findViewById(R.id.nomal_drawer_logout_btn);
        logout_layout = (LinearLayout) v.findViewById(R.id.nomal_drawer_logout_layout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logout_layout.getVisibility() == View.VISIBLE) {
                    logout_layout.setVisibility(View.GONE);
                } else
                    logout_layout.setVisibility(View.VISIBLE);
            }
        });
        setUserData();
        return v;
    }

    public void setUserData() {
        NetworkManager.getInstance().getUserMainInfo(new NetworkManager.OnResultListener<NomalUserMainResult>() {
            @Override
            public void onSuccess(Request request, NomalUserMainResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    profieName.setText(result.getNomalUserMain().getUsername());
                    profileEmail.setText(result.getNomalUserMain().getEmail());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
