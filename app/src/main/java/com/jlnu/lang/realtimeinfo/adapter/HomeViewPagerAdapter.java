package com.jlnu.lang.realtimeinfo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lilang on 2017/2/26.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments;

    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
