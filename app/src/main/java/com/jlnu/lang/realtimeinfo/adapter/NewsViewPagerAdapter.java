package com.jlnu.lang.realtimeinfo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qn on 2017/3/6.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> mFragments;

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(String[] mTitles, List<Fragment> mFragments) {
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? null : mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
