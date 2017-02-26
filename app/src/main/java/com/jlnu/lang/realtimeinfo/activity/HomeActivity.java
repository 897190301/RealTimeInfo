package com.jlnu.lang.realtimeinfo.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jlnu.lang.realtimeinfo.NewsViewPagerAdapter;
import com.jlnu.lang.realtimeinfo.R;
import com.jlnu.lang.realtimeinfo.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.bottom_nav)
    BottomNavigationView mBottomNavView;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private List<Fragment> mFragments;
    private NewsViewPagerAdapter mViewPagerAdapter;
    private MenuItem pre_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        initFragments();
    }

    private void initView() {
        initToolbar();
        initDrawer();
        initNavigationView();
        initViewPager();
        initBottomNavigationView();
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(MyFragment.newInstance("新闻"));
        mFragments.add(MyFragment.newInstance("电影"));
        mFragments.add(MyFragment.newInstance("天气"));
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
        mViewPagerAdapter = new NewsViewPagerAdapter(getSupportFragmentManager(),mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pre_item == null) {
                    pre_item = mBottomNavView.getMenu().getItem(0);
                    pre_item.setChecked(true);
                } else {
                    pre_item.setChecked(false);
                }
                pre_item = mBottomNavView.getMenu().getItem(position);
                pre_item.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomNavigationView() {
        mBottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.film:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.weather:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
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
}
