package com.pcm.pcmmanager.common.expert_detail_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailInfo;
import com.pcm.pcmmanager.data.ExpertDetailResult;
import com.pcm.pcmmanager.data.ExpertDetailReviewResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ExpertDetailInfoActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    public static final String PAGE_SIZE = "10";

    ImageView profile_image;
    TextView profile_name, profile_age, profile_career, profile_office, title;
    ExpertDetailInfo expertDetailInfo; //기본정보, 위치
    ExpertDetailReviewResult expertDetailReviewResult; //후기
    String expertSn;
    ViewPager viewPager;
    ExpertDetailInfoViewPager mAdapter;

    public interface OnNotifyReceiver {
        public void onNotify();
    }

    List<OnNotifyReceiver> mReceivers = new ArrayList<>();

    public void registerNotifyReceiver(OnNotifyReceiver receiver) {
        mReceivers.add(receiver);
    }

    public void unregisterNotifyReceiver(OnNotifyReceiver receiver) {
        mReceivers.remove(receiver);
    }

    public void doNotify() {
        for (OnNotifyReceiver r : mReceivers) {
            r.onNotify();
        }
    }

    public ExpertDetailInfo getExportDetailInfo() {
        return expertDetailInfo;
    }

    public ExpertDetailReviewResult getExpertDetailReviewResult() {
        return expertDetailReviewResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_detail_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        profile_image = (ImageView) findViewById(R.id.expert_detail_image_profile);
        profile_name = (TextView) findViewById(R.id.expert_detail_profile_name);
        profile_age = (TextView) findViewById(R.id.expert_detail_profile_age);
        profile_career = (TextView) findViewById(R.id.expert_detail_profile_career);
        profile_office = (TextView) findViewById(R.id.expert_detail_profile_office);
        title = (TextView) findViewById(R.id.expert_detail_title);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.htab_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < -50 && profile_image.getVisibility() == View.VISIBLE) {
                    profile_image.setVisibility(View.INVISIBLE);
                    profile_name.setVisibility(View.INVISIBLE);
                    title.setVisibility(View.VISIBLE);
                } else if (verticalOffset > -50 && profile_image.getVisibility() == View.INVISIBLE) {
                    profile_image.setVisibility(View.VISIBLE);
                    profile_name.setVisibility(View.VISIBLE);
                    title.setVisibility(View.GONE);
                }
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        View tabView1 = getLayoutInflater().inflate(R.layout.view_expert_career_tab, null);
        View tabView2 = getLayoutInflater().inflate(R.layout.view_expert_tab, null);
        View tabView3 = getLayoutInflater().inflate(R.layout.view_expert_tab, null);

        TextView tabTitle1 = (TextView) tabView1.findViewById(R.id.title);
        TextView tabTitle2 = (TextView) tabView2.findViewById(R.id.title);
        TextView tabTitle3 = (TextView) tabView3.findViewById(R.id.title);

        tabTitle1.setText("업무 및 이력");
        tabTitle2.setText("사무실");
        tabTitle3.setText("후기");

        ImageView tabIcon1 = (ImageView) tabView1.findViewById(R.id.icon);
        ImageView tabIcon2 = (ImageView) tabView2.findViewById(R.id.icon);
        ImageView tabIcon3 = (ImageView) tabView3.findViewById(R.id.icon);

        tabIcon1.setImageResource(R.drawable.buleicon_selector);
        tabIcon2.setImageResource(R.drawable.location_selector);
        tabIcon3.setImageResource(R.drawable.review_selector);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        mAdapter = new ExpertDetailInfoViewPager(getSupportFragmentManager(), 3);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setCustomView(tabView1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabView2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabView3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        expertSn = getIntent().getStringExtra("expertSn");

        NetworkManager.getInstance().getExpertDetailResult(expertSn, new NetworkManager.OnResultListener<ExpertDetailResult>() {
            @Override
            public void onSuccess(Request request, ExpertDetailResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(ExpertDetailInfoActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    expertDetailInfo = result.getInfo();
                    if(TextUtils.isEmpty(expertDetailInfo.getPhoto())){
                        if(expertDetailInfo.getSex().equals("남자")){
                            profile_image.setImageResource(R.drawable.semooman_icon);
                        }else if(expertDetailInfo.getSex().equals("여자")){
                            profile_image.setImageResource(R.drawable.semoogirl_icon);
                        }
                    }else {
                        Glide.with(profile_image.getContext()).load(expertDetailInfo.getPhoto()).into(profile_image);
                    }
                    title.setText(expertDetailInfo.getName() + "회계사");
                    title.setVisibility(View.GONE);
                    profile_name.setText(expertDetailInfo.getName() + "회계사");
                    profile_office.setText(expertDetailInfo.getOfficeName());
                    profile_career.setText("경력 " + expertDetailInfo.getCareer() + "년");
                    profile_age.setText(expertDetailInfo.getAge() + "세");
                    doNotify();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
        String marketSn = "0";
        NetworkManager.getInstance().getExpertDetailReviewResult(PAGE_SIZE, marketSn, expertSn, new NetworkManager.OnResultListener<ExpertDetailReviewResult>() {
            @Override
            public void onSuccess(Request request, ExpertDetailReviewResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(ExpertDetailInfoActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    expertDetailReviewResult = result;
                    doNotify();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expert_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            if (MyApplication.getUserType().equals("Experts")) {
                Intent intent = new Intent(ExpertDetailInfoActivity.this, ExpertMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (MyApplication.getUserType().equals("Users")) {
                Intent intent = new Intent(ExpertDetailInfoActivity.this, NomalMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
