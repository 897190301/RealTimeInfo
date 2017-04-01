package com.jlnu.lang.realtimeinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.adapter.HomeViewPagerAdapter;
import com.jlnu.lang.realtimeinfo.custom.MyTabView;
import com.jlnu.lang.realtimeinfo.custom.NoSlideViewPager;
import com.jlnu.lang.realtimeinfo.fragment.MyFragment;
import com.jlnu.lang.realtimeinfo.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by lilang on 2017/2/26.
 */

public class HomeActivity extends AppCompatActivity
        implements MyTabView.onTabSelectListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewPager)
    NoSlideViewPager mViewPager;
    @Bind(R.id.myTab)
    MyTabView myTabView;

    private List<Fragment> mFragments;
    private HomeViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new NewsFragment());
        mFragments.add(MyFragment.newInstance("天气"));
        mFragments.add(MyFragment.newInstance("电影"));
        mFragments.add(MyFragment.newInstance("我"));
    }

    private void initView() {
        initToolbar();
        initTabView();
        initViewPager();
    }

    private void initTabView() {
        myTabView.setListener(this);
    }

    private void initToolbar() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_black));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void initViewPager() {
        mViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(),mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPosition(int position) {
        if (position == 2) {
            Intent intent = new Intent(HomeActivity.this, FilmActivity.class);
            startActivity(intent);
            return;
        }
        mViewPager.setCurrentItem(position, false);
    }
}
