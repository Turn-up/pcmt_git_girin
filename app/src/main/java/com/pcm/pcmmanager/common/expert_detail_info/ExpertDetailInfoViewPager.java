package com.pcm.pcmmanager.common.expert_detail_info;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by LG on 2016-05-23.
 */
public class ExpertDetailInfoViewPager extends FragmentStatePagerAdapter {

    int tabNumber;

    public ExpertDetailInfoViewPager(FragmentManager fm, int tabNumber) {
        super(fm);
        this.tabNumber = tabNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ExpertDetailInfoCareerFragment tab1 = new ExpertDetailInfoCareerFragment();
                return tab1;
            case 1:
                ExpertDetailInfoOfficeLocationFragment tab2 = new ExpertDetailInfoOfficeLocationFragment();
                return tab2;
            case 2:
                ExpertDetailInfoReviewFragment tab3 = new ExpertDetailInfoReviewFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}