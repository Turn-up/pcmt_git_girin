package com.pcm.pcmmanager.common.use_way;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pcm.pcmmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UseWayViewPagerFragment extends Fragment {

    private int mPageNumber;

    public UseWayViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("position");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_use_way_view_pager, container, false);
        ImageView useWayImageView = (ImageView) view.findViewById(R.id.use_way_image);
        switch (mPageNumber) {
            case 0:
                useWayImageView.setImageResource(R.drawable.use_way_one);
                break;
            case 1:
                useWayImageView.setImageResource(R.drawable.use_way_two);
                break;
            case 2:
                useWayImageView.setImageResource(R.drawable.use_way_three);
                break;
            case 3:
                useWayImageView.setImageResource(R.drawable.use_way_four);
                break;
        }
        return view;
    }

}
