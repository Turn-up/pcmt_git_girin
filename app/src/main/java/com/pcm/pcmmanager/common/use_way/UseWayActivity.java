package com.pcm.pcmmanager.common.use_way;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.pcm.pcmmanager.R;

public class UseWayActivity extends AppCompatActivity {


    ViewPager useWayViewPagerFragment;
    UseWayViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_way);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        final DotIndicator useWayIndicator = (DotIndicator) findViewById(R.id.use_way_indicator_ad);
        useWayIndicator.setSelectedDotColor(Color.parseColor("#dcdcdc"));
        useWayIndicator.setUnselectedDotColor(Color.parseColor("#7d7d7d"));

        /*NoticeViewPager*/
        useWayViewPagerFragment = (ViewPager) findViewById(R.id.use_way_viewPager);
        mAdapter = new UseWayViewPagerAdapter(getSupportFragmentManager());

        useWayIndicator.setNumberOfItems(mAdapter.getCount());
        useWayViewPagerFragment.setAdapter(mAdapter);
        useWayViewPagerFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                useWayIndicator.setSelectedItem(useWayViewPagerFragment.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        useWayViewPagerFragment.setCurrentItem(0, true);


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
        if(id == android.R.id.home)
            finish();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





