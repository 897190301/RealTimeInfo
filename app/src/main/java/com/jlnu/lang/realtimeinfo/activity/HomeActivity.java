package com.jlnu.lang.realtimeinfo.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
        implements NavigationView.OnNavigationItemSelectedListener, MyTabView.onTabSelectListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
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
        //mFragments.add(MyFragment.newInstance("新闻"));
        mFragments.add(MyFragment.newInstance("电影"));
        mFragments.add(MyFragment.newInstance("天气"));
        mFragments.add(MyFragment.newInstance("我"));
    }

    private void initView() {
        initToolbar();
        initDrawer();
        initNavigationView();
        initTabView();
        initViewPager();
    }

    private void initTabView() {
        myTabView.setListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewPager() {
        mViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(),mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setPosition(int position) {
        mViewPager.setCurrentItem(position, false);
    }
}
