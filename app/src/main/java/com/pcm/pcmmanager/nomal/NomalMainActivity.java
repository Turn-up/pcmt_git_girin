package com.pcm.pcmmanager.nomal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pcm.pcmmanager.BaseActivity;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.ask.AskActivity;
import com.pcm.pcmmanager.common.faq.FaqActivity;
import com.pcm.pcmmanager.common.notice.event.NoticeEventActivity;
import com.pcm.pcmmanager.common.notice.text.NoticeListActivity;
import com.pcm.pcmmanager.common.use_way.UseWayActivity;
import com.pcm.pcmmanager.nomal.drawer.NomalNavigationDrawer;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;
import com.pcm.pcmmanager.nomal.info.NomalUserInfoEditActivity;
import com.pcm.pcmmanager.nomal.qna_list.MyQnaListActivity;

public class NomalMainActivity extends BaseActivity implements NomalNavigationDrawer.OnMenuClickListener {
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    .add(R.id.container, new NomalUserParentFragment())
                    .commit();
        }
    }

    @Override
    public void onMenuClick(int menuId) {
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

    public void onNavClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nomal_drawer_ask:
                intent = new Intent(this, AskActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_notice:
                intent = new Intent(this, NoticeListActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_event:
                intent = new Intent(this, NoticeEventActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_my_estimate:
                intent = new Intent(this, MyEstimateListActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_use_way:
                intent = new Intent(this, UseWayActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_my_qna:
                intent = new Intent(this, MyQnaListActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_setting:
                intent = new Intent(this, NomalUserInfoEditActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_faq:
                intent = new Intent(this, FaqActivity.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}
