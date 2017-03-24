package com.jlnu.lang.realtimeinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.adapter.NewsViewPagerAdapter;
import com.jlnu.lang.realtimeinfo.custom.ChildViewPager;
import com.jlnu.lang.realtimeinfo.ui.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qn on 2017/3/6.
 */

public class NewsFragment extends Fragment implements ChildViewPager.OnSingleTouchListener{

    @Bind(R.id.news_vp)
    ViewPager newsVp;
    @Bind(R.id.tab_strip)
    PagerSlidingTabStrip tabStrip;

    private NewsViewPagerAdapter mNewsVpAdapter;
    private String[] keys;
    private List<Fragment> mFragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initData() {
        keys = new String[]{"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
        mFragments = new ArrayList<>();
        for (String key : keys) {
            NewsItemFragment fragment = new NewsItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("news_key", key);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }

    private void initViews() {
        initViewPager();
        initTabStrip();
    }

    private void initViewPager() {
        mNewsVpAdapter = new NewsViewPagerAdapter(getFragmentManager());
        mNewsVpAdapter.setData(keys, mFragments);
        newsVp.setAdapter(mNewsVpAdapter);
        newsVp.setOffscreenPageLimit(1);
    }

    private void initTabStrip() {
        tabStrip.setViewPager(newsVp);
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onSingleTouch() {

    }
}
