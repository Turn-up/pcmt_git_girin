package com.pcm.pcmmanager.nomal.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pcm.pcmmanager.data.NomalMainContentNoticeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-16.
 */
public class NoticeViewPagerAdapter extends FragmentPagerAdapter {

    List<NomalMainContentNoticeList> nomalMainContentNoticeLists = new ArrayList<>();

    public NoticeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addAll(List<NomalMainContentNoticeList> items){
        nomalMainContentNoticeLists.addAll(items);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return nomalMainContentNoticeLists.size() ;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new NoticeViewPagerFragment();
        Bundle b = new Bundle();
        b.putString("imageUrl",nomalMainContentNoticeLists.get(position).getAttachUrl());  // 서버로 부터 받은 image 출력
        b.putInt("noticeSn",nomalMainContentNoticeLists.get(position).getNoticeSn());
        f.setArguments(b);
        return f;
    }
}
