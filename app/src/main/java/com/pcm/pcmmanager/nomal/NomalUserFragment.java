package com.pcm.pcmmanager.nomal;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.condition_search.ConditionSearchFragment;
import com.pcm.pcmmanager.data.MainBidCountResult;
import com.pcm.pcmmanager.data.NomalMainContentResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.estimate_request.EstimateRequestActivity;
import com.pcm.pcmmanager.nomal.main.AccountInfoViewPagerAdpater;
import com.pcm.pcmmanager.nomal.main.NoticeViewPagerAdapter;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class NomalUserFragment extends Fragment implements View.OnClickListener {

    Button btn_estimate_do, btn_condition_search;
    TextView total_auction_count, total_doing_estimate_count;  //총 경매수, 입찰진행중인 수 나중에 정의해
    DotIndicator infoIndicator, noticeIndicator;
    ViewPager accountInfoPager, noticeInfoPager;
    AccountInfoViewPagerAdpater mAIPagerAdapter;
    NoticeViewPagerAdapter mNoticePagerAdapter;

    public NomalUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nomal_user, container, false);
        total_auction_count = (TextView) view.findViewById(R.id.nomal_total_auction_count_value);
        total_doing_estimate_count = (TextView) view.findViewById(R.id.nomal_total_doing_estimate_count);

        infoIndicator = (DotIndicator) view.findViewById(R.id.info_indicator_ad);
        infoIndicator.setSelectedDotColor(Color.parseColor("#dcdcdc"));
        infoIndicator.setUnselectedDotColor(Color.parseColor("#7d7d7d"));

        /*AccountInfoViewPager*/
        accountInfoPager = (ViewPager) view.findViewById(R.id.accountInfoViewPager);
        mAIPagerAdapter = new AccountInfoViewPagerAdpater(getChildFragmentManager());

        setData();
        setBidCount();
        accountInfoPager.setAdapter(mAIPagerAdapter);
        accountInfoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                infoIndicator.setSelectedItem(accountInfoPager.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        accountInfoPager.setCurrentItem(0, true);

        noticeIndicator = (DotIndicator) view.findViewById(R.id.notice_indicator_ad);
        noticeIndicator.setSelectedDotColor(Color.parseColor("#dcdcdc"));
        noticeIndicator.setUnselectedDotColor(Color.parseColor("#7d7d7d"));

        /*NoticeViewPager*/
        noticeInfoPager = (ViewPager) view.findViewById(R.id.noticeViewPager);
        mNoticePagerAdapter = new NoticeViewPagerAdapter(getChildFragmentManager());

        noticeInfoPager.setAdapter(mNoticePagerAdapter);
        noticeInfoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                noticeIndicator.setSelectedItem(noticeInfoPager.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        noticeInfoPager.setCurrentItem(0, true);

        btn_estimate_do = (Button) view.findViewById(R.id.btn_estimate_do);
        btn_estimate_do.setOnClickListener(this);

        btn_condition_search = (Button) view.findViewById(R.id.btn_condition_search);
        btn_condition_search.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == btn_estimate_do) {
            Intent intent = new Intent(getContext(), EstimateRequestActivity.class);
            startActivity(intent);
        } else if (v == btn_condition_search) {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.nomal_parent_container, new ConditionSearchFragment())
                    .commit();
        }
    }

    public void setData() {
        NetworkManager.getInstance().getNomalMainContentList(new NetworkManager.OnResultListener<NomalMainContentResult>() {
            @Override
            public void onSuccess(Request request, NomalMainContentResult result) {
                    mAIPagerAdapter.addAll(result.getItem().getTipList());
                    infoIndicator.setNumberOfItems(mAIPagerAdapter.getCount());
                    mNoticePagerAdapter.addAll(result.getItem().getNoticeList());
                    noticeIndicator.setNumberOfItems(mNoticePagerAdapter.getCount());
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    public void setBidCount() {
        NetworkManager.getInstance().getMainBidCount(new NetworkManager.OnResultListener<MainBidCountResult>() {
            @Override
            public void onSuccess(Request request, MainBidCountResult result) {
                total_auction_count.setText(""+result.getMainBidCount().getBidCount());
                total_doing_estimate_count.setText(""+result.getMainBidCount().getBidEndCount());
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}

