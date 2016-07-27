package com.pcm.pcmmanager.nomal.main;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfoViewPagerFragment extends Fragment {

    private int mPageNumber;
    private String imageUrl,detailUrl;

    public AccountInfoViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("position");
        imageUrl = getArguments().getString("ImageUrl");
        detailUrl = getArguments().getString("detailUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_info_view_pager, container, false);
        ImageView accountImageView = (ImageView)view.findViewById(R.id.accountInfoImage);
        Glide.with(getContext()).load(imageUrl).into(accountImageView);
        accountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailUrl));
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;

    }
    public void setData(){

    }
}
