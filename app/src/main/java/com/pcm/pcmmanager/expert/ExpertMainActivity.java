package com.pcm.pcmmanager.expert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.ask.AskActivity;
import com.pcm.pcmmanager.common.faq.FaqActivity;
import com.pcm.pcmmanager.common.notice.event.NoticeEventActivity;
import com.pcm.pcmmanager.common.notice.text.NoticeListActivity;
import com.pcm.pcmmanager.common.use_way.UseWayActivity;
import com.pcm.pcmmanager.data.PointListResult;
import com.pcm.pcmmanager.expert.bid_info.BidFinishFragment;
import com.pcm.pcmmanager.expert.bid_info.BidIngListFragment;
import com.pcm.pcmmanager.expert.bid_info.BidSuccessFragment;
import com.pcm.pcmmanager.expert.drawer.ExpertNavigationDrawerFramgent;
import com.pcm.pcmmanager.expert.info.ExpertInfoEditActivity;
import com.pcm.pcmmanager.expert.point.PointListActivity;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class ExpertMainActivity extends AppCompatActivity implements ExpertNavigationDrawerFramgent.OnMenuClickListener {
    DrawerLayout drawer;

    public static final String PAGESIZE = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ExpertParentFragment())
                    .commit();
        }
    }

    @Override
    public void onMenuClick(int menuId) {
    }

    public void onNavClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.expert_drawer_ask:
                intent = new Intent(this, AskActivity.class);
                startActivity(intent);
                break;
            case R.id.expert_drawer_notice:
                intent = new Intent(this, NoticeListActivity.class);
                startActivity(intent);
                break;
            case R.id.expert_drawer_event:
                intent = new Intent(this, NoticeEventActivity.class);
                startActivity(intent);
                break;
            case R.id.expert_drawer_use_way:
                intent = new Intent(this, UseWayActivity.class);
                startActivity(intent);
                break;
            case R.id.expert_drawer_personal_info_btn:
                intent = new Intent(this, ExpertInfoEditActivity.class);
                startActivity(intent);
                break;
            case R.id.expert_drawer_faq:
                intent = new Intent(this, FaqActivity.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void myEstimateInfoOnClick(View v) {
        switch (v.getId()) {
            //bid status 넘겨줘야함
            case R.id.nav_header_expert_profile_entry_count:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BidIngListFragment())
                        .commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_header_expert_profile_bid_success_count:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BidSuccessFragment())
                        .commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_header_expert_profile_bid_finish_count:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BidFinishFragment())
                        .commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }

    //포인트 조회
    public void onPoint(View v) {
        if (v.getId() == R.id.nav_header_expert_profile_point) {
            NetworkManager.getInstance().getMileageList(PAGESIZE, "0", new NetworkManager.OnResultListener<PointListResult>() {
                @Override
                public void onSuccess(Request request, PointListResult result) {
                    if (result.getResult() == -1) {
                        Toast.makeText(ExpertMainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(ExpertMainActivity.this, PointListActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFail(Request request, IOException exception) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expert_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ExpertParentFragment())
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
