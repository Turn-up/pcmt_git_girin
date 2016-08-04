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
import android.widget.ImageView;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.condition_search.ConditionSearchFragment;
import com.pcm.pcmmanager.data.MainBidCountResult;
import com.pcm.pcmmanager.data.NomalMainContentResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.estimate_request.EstimateRequestActivity;
import com.pcm.pcmmanager.nomal.main.AccountInfoViewPagerAdpater;
import com.pcm.pcmmanager.nomal.main.NoticeViewPagerAdapter;
import com.pcm.pcmmanager.qna.list.QnaListActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class NomalUserFragment extends Fragment implements View.OnClickListener {

    Button btn_estimate_do, btn_condition_search,btn_nomal_qna;
    DotIndicator infoIndicator, noticeIndicator;
    ViewPager accountInfoPager, noticeInfoPager;
    AccountInfoViewPagerAdpater mAIPagerAdapter;
    NoticeViewPagerAdapter mNoticePagerAdapter;

    ImageView totalCount[], auctionCount[];

    public NomalUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nomal_user, container, false);
        infoIndicator = (DotIndicator) view.findViewById(R.id.info_indicator_ad);
        infoIndicator.setSelectedDotColor(Color.parseColor("#dcdcdc"));
        infoIndicator.setUnselectedDotColor(Color.parseColor("#7d7d7d"));

        totalCount = new ImageView[5];
        totalCount[0] = (ImageView) view.findViewById(R.id.total_count_one);
        totalCount[1] = (ImageView) view.findViewById(R.id.total_count_two);
        totalCount[2] = (ImageView) view.findViewById(R.id.total_count_three);
        totalCount[3] = (ImageView) view.findViewById(R.id.total_count_four);
        totalCount[4] = (ImageView) view.findViewById(R.id.total_count_five);

        auctionCount = new ImageView[5];
        auctionCount[0] = (ImageView) view.findViewById(R.id.biding_count_one);
        auctionCount[1] = (ImageView) view.findViewById(R.id.biding_count_two);
        auctionCount[2] = (ImageView) view.findViewById(R.id.biding_count_three);
        auctionCount[3] = (ImageView) view.findViewById(R.id.biding_count_four);
        auctionCount[4] = (ImageView) view.findViewById(R.id.biding_count_five);


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

        btn_nomal_qna = (Button)view.findViewById(R.id.btn_nomal_qna);
        btn_nomal_qna.setOnClickListener(this);

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
        } else if( v== btn_nomal_qna){
            Intent intent = new Intent(getContext(), QnaListActivity.class);
            startActivity(intent);
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
                String total = String.valueOf(result.getMainBidCount().getBidEndCount());
                for (int i = total.length(); 0 < i; i--) {
                    switch (total.charAt(total.length() - i)) {
                        case '0':
                            totalCount[5 - i].setImageResource(R.drawable.count_zero_icon);
                            break;
                        case '1':
                            totalCount[5 - i].setImageResource(R.drawable.count_one_icon);
                            break;
                        case '2':
                            totalCount[5 - i].setImageResource(R.drawable.count_two_icon);
                            break;
                        case '3':
                            totalCount[5 - i].setImageResource(R.drawable.count_three_icon);
                            break;
                        case '4':
                            totalCount[5 - i].setImageResource(R.drawable.count_four_icon);
                            break;
                        case '5':
                            totalCount[5 - i].setImageResource(R.drawable.count_five_icon);
                            break;
                        case '6':
                            totalCount[5 - i].setImageResource(R.drawable.count_six_icon);
                            break;
                        case '7':
                            totalCount[5 - i].setImageResource(R.drawable.count_seven_icon);
                            break;
                        case '8':
                            totalCount[5 - i].setImageResource(R.drawable.count_eight_icon);
                            break;
                        case '9':
                            totalCount[5 - i].setImageResource(R.drawable.count_nine_icon);
                            break;
                    }
                }
                String bidIng = "" + result.getMainBidCount().getBidCount();
                for (int i = bidIng.length(); i > 0; i--) {
                    switch (bidIng.charAt(bidIng.length() - i)) {
                        case '0':
                            auctionCount[5 - i].setImageResource(R.drawable.count_zero_icon);
                            break;
                        case '1':
                            auctionCount[5 - i].setImageResource(R.drawable.count_one_icon);
                            break;
                        case '2':
                            auctionCount[5 - i].setImageResource(R.drawable.count_two_icon);
                            break;
                        case '3':
                            auctionCount[5 - i].setImageResource(R.drawable.count_three_icon);
                            break;
                        case '4':
                            auctionCount[5 - i].setImageResource(R.drawable.count_four_icon);
                            break;
                        case '5':
                            auctionCount[5 - i].setImageResource(R.drawable.count_five_icon);
                            break;
                        case '6':
                            auctionCount[5 - i].setImageResource(R.drawable.count_six_icon);
                            break;
                        case '7':
                            auctionCount[5 - i].setImageResource(R.drawable.count_seven_icon);
                            break;
                        case '8':
                            auctionCount[5 - i].setImageResource(R.drawable.count_eight_icon);
                            break;
                        case '9':
                            auctionCount[5 - i].setImageResource(R.drawable.count_nine_icon);
                            break;
                    }
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}

