package com.pcm.pcmmanager.nomal.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pcm.pcmmanager.data.NomalMainContentTipList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-16.
 */
public class AccountInfoViewPagerAdpater extends FragmentPagerAdapter {

    List<NomalMainContentTipList> nomalMainContentTipLists = new ArrayList<>();
    public void addAll(List<NomalMainContentTipList> item){
        nomalMainContentTipLists.addAll(item);
        notifyDataSetChanged();
    }

    public AccountInfoViewPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return nomalMainContentTipLists.size();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new AccountInfoViewPagerFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("ImageUrl",nomalMainContentTipLists.get(position).getAttachUrl());  // 서버로 부터 받은 image 출력
        b.putString("detailUrl",nomalMainContentTipLists.get(position).getLinkUrl());
        f.setArguments(b);
        return f;
    }

}
