package com.pcm.pcmmanager.common.use_way;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by LG on 2016-05-25.
 */
public class UseWayViewPagerAdapter extends FragmentPagerAdapter {

    public static final int ITEM_COUNT = 4;


    public UseWayViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new UseWayViewPagerFragment();
        Bundle b = new Bundle();
        b.putInt("position",position);
        f.setArguments(b);
        return f;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }
}
